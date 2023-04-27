package com.neko233.skilltree.commons.storage.wal;

import com.alibaba.fastjson2.JSON;
import com.neko233.skilltree.commons.core.annotation.Experimental;
import com.neko233.skilltree.commons.core.annotation.NotNull;
import com.neko233.skilltree.commons.core.base.*;
import com.neko233.skilltree.commons.core.file.FileUtils233;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * WAL = Write Ahead Log
 * write data to WAL, used WAL to implementation a Log record. then use Consumer to handle data flush disk.
 * 写入 WAL 之前, 需要确保你的数据经过 validate and must right, 并保证写入的顺序性.
 * 1. WAL 提供一个高速持久化能力, 同时将刷盘从 single write -> batch write
 * 2. 提供了异步顺写的机制 see {@link Consumer<Collection> asyncFlushDataConsumer}
 *
 * @author SolarisNeko
 * Date on 2023-01-28
 */
@Experimental(comment = "Async WAL and default 100ms/flush by your provider consumer API")
@Slf4j
public abstract class AbstractWalV1<D> {

    // ASA batch size
    public static final int DEFAULT_CONSUMER_SIZE = 4000;
    public static final long DEFAULT_KEEP_WAL_FILE_MS_1_S = TimeUnit.SECONDS.toMillis(1);
    public static final long DEFAULT_CONSUMER_DISPATCHER_INTERVAL_100_MS = TimeUnit.MILLISECONDS.toMillis(100);
    public static final int DEFAULT_MAX_WAL_FILE_COUNT_10 = 10;


    public static final String WAL_METADATA_FILE_SUFFIX = ".wal.metadata";

    // lock
    private final Lock lock = new ReentrantLock();
    private final AtomicBoolean isClose = new AtomicBoolean(false);
    private final Class<D> dataSchema;
    private final Map<String, String> configMap;

    /**
     * use this template for : /path/to/file -> /path/to/file.${millisSeconds}
     */
    private final String fileTemplate;
    private final File currentMetadataFile;
    private final String fileTemplateName;
    private volatile WalMetadataV1 metadata;

    // async
    private Thread walThread;
    private Thread consumerDispatcherThread;
    private final ThreadPoolExecutor consumerThreadPool;

    // retention policy
    private final long fileRollingByteSize; // 文件大小滚动
    private final long keepWalFileMs; // 日志保存毫秒数
    private final int keepWalFileCount; // 保留多少个 WAL 文件
    private int consumerDispatcherThreadIntervalMs; // 单条线程最多处理多少个数据
    private int singleThreadConsumerDataSize; // 单条线程最多处理多少个数据
    // thread
    private final int consumerIntervalMs; // 消费间隔

    // liner Queue, 2 Q for speed
    private final BlockingQueue<D> producerQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<D> consumerQueue = new LinkedBlockingQueue<>();

    /**
     * @param templateFilePath 模板文件 full path, 不需要存在, 只是作为前缀
     * @param dataSchema       数据结构
     * @param configMap        配置参数 if null use default
     * @throws Exception 参数检查异常
     */
    public AbstractWalV1(final File templateFilePath,
                         final Class<D> dataSchema,
                         final Map<String, String> configMap
    ) throws Exception {
        if (templateFilePath == null) {
            throw new IllegalArgumentException("file can not null");
        }
        if (dataSchema == null) {
            throw new IllegalArgumentException("WAL Data class can not null");
        }

        this.configMap = Optional.ofNullable(configMap).orElse(new HashMap<>());


        this.dataSchema = dataSchema;
        this.fileTemplate = templateFilePath.getAbsolutePath();
        this.fileTemplateName = templateFilePath.getName();

        // metadata
        this.currentMetadataFile = new File(templateFilePath.getAbsolutePath() + WAL_METADATA_FILE_SUFFIX);

        // diy Config
        this.fileRollingByteSize = Long.parseLong(this.configMap.getOrDefault("fileRollingByteSize", "1024"));
        this.keepWalFileMs = Long.parseLong(this.configMap.getOrDefault("keepWalFileMs", String.valueOf(DEFAULT_KEEP_WAL_FILE_MS_1_S)));
        this.keepWalFileCount = Integer.parseInt(this.configMap.getOrDefault("keepWalFileCount", String.valueOf(DEFAULT_MAX_WAL_FILE_COUNT_10)));
        this.consumerIntervalMs = Integer.parseInt(this.configMap.getOrDefault("workerIntervalMs", String.valueOf(100)));
        this.singleThreadConsumerDataSize = Integer.parseInt(this.configMap.getOrDefault("singleThreadConsumerDataSize", String.valueOf(DEFAULT_CONSUMER_SIZE)));
        this.consumerDispatcherThreadIntervalMs = Integer.parseInt(this.configMap.getOrDefault("consumerDispatcherThreadIntervalMs", String.valueOf(DEFAULT_CONSUMER_DISPATCHER_INTERVAL_100_MS)));
        // tp
        this.consumerThreadPool = ThreadPoolHelper233.ioThreadPool("WAL-consumer-" + templateFilePath.getName(), new ThreadPoolExecutor.CallerRunsPolicy());

        initAndCheck();
    }

    void initAndCheck() throws IOException {
        restoreMetadataFromFile(this.currentMetadataFile);

        // ------------- init finish --------------

        // check previous file
        restoreHistoryMemoryData();


        // WAL thread
        this.consumerDispatcherThread = createConsumerDispatcherThread();
        consumerDispatcherThread.start();
        this.walThread = createProducerThread();
        walThread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.close();
        }));
    }

    private void restoreMetadataFromFile(File metadataFile) throws IOException {
        boolean isNewCreate = FileUtils233.createFileIfNotExists(metadataFile);
        if (isNewCreate) {
            metadata = WalMetadataV1.createFirst(this.fileTemplate);
        }
        final String content = FileUtils233.readAllContent(metadataFile);
        boolean isFirstInit = false;
        if (StringUtils233.isBlank(content)) {
            this.metadata = WalMetadataV1.createFirst(this.fileTemplate);
            isFirstInit = true;
        }

        if (!isFirstInit) {
            this.metadata = JSON.parseObject(content, WalMetadataV1.class);
        }
        PreconditionUtils233.checkNotNull(this.metadata, "your metadata is null!");

        flushMetaData();
    }

    private Thread createProducerThread() {
        return ThreadUtils233.newThread("WAL-consumer-flush-thread-" + fileTemplateName,
                () -> {
                    while (true) {
                        try {
                            batchWriteToWalFile();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }


    private List<File> getCurrentDirFiles() throws IllegalAccessException {
        File dir = new File(fileTemplate).getParentFile();
        try {
            return FileUtils233.showFiles(dir);
        } catch (IOException e) {
            throw new IllegalAccessException("can not visit file/dir = " + dir.getAbsolutePath());
        }
    }

    /**
     * 恢复历史未刷盘的内存数据
     *
     * @throws IOException IO 异常
     */
    private void restoreHistoryMemoryData() throws IOException {
        // recover data
        final String historyWalFileAbsPath = this.metadata.buildDataFileName(this.metadata.getCurrentSequenceId());
        final File historyWalFile = new File(historyWalFileAbsPath);
        this.metadata.resetCurrentWalFile(historyWalFile);

        final List<String> jsonLines = FileUtils233.readLines(historyWalFile, StandardCharsets.UTF_8);
        final List<WalDumpData> collect = Optional.of(jsonLines).orElse(new ArrayList<>())
                .stream()
                .map(str -> JSON.parseObject(str, WalDumpData.class))
                .collect(Collectors.toList());

        for (WalDumpData obj : collect) {
            String data = obj.getDataJson();
            D d = JSON.parseObject(data, dataSchema);
            if (d == null) {
                log.error("parse JSON object to class = {} error. json = {}", dataSchema, data);
                continue;
            }

            // produce
            addDataToMemory(d);
        }
    }


    /**
     * WAL Thread
     *
     * @return WAL 线程, not start
     */
    @NotNull
    private Thread createConsumerDispatcherThread() {
        return ThreadUtils233.newThread("WAL-consumer-thread-" + fileTemplateName,
                () -> {
                    final AbstractWalV1<D> wal = this;
                    // never error
                    while (true) {
                        // core
                        lock.lock();
                        try {
                            dispatcherDataToWorker();

                            wal.cleanHistoryDataFile();

                            wal.flushMetaData();
                        } catch (Throwable e) {
                            log.error("WAL flush happen error. please check!", e);
                        } finally {
                            lock.unlock();
                        }
                        // 独立的时间
                        try {
                            TimeUnit.MILLISECONDS.sleep(this.consumerDispatcherThreadIntervalMs);
                        } catch (Throwable e) {
                            log.error("WAL async thread happen interrupted! but you can't interrupted it!", e);
                        }
                    }
                });
    }

    private void batchWriteToWalFile() {
        final List<D> dataList = new ArrayList<>();
        producerQueue.drainTo(dataList, singleThreadConsumerDataSize);

        // wal
        final File currentWalFile = this.metadata.getCurrentWalFile();
        if (currentWalFile == null) {
            log.error("please check your WAL data file why null.");
            return;
        }
        final List<WalDumpData> bufferDumpData = dataList.stream()
                .map(JSON::toJSONString)
                .map(dataJson -> WalDumpData.builder()
                        .sequenceId(this.metadata.getCurrentSequenceId())
                        .timestampMs(System.currentTimeMillis())
                        .dataJson(dataJson)
                        .build())
                .collect(Collectors.toList());
        try {
            final List<String> dumpJsonList = bufferDumpData.stream()
                    .map(JSON::toJSONString)
                    .collect(Collectors.toList());
            FileUtils233.writeLines(currentWalFile, dumpJsonList, true);
        } catch (IOException e) {
            log.error("WAL happen error");
        }
    }

    /**
     * flush Data
     */
    private void dispatcherDataToWorker() {
        // double lock for concurrent
        lock.lock();
        try {
            if (!isNeedRollingNextWalFile()) {
                return;
            }

            this.asyncFlushDisk();

            this.nextSequenceAndUpdate();

        } catch (Throwable e) {
            log.error("WAL flush happen error. please check!", e);
        } finally {
            lock.unlock();
        }


    }

    /**
     * 清理历史数据
     */
    private synchronized void cleanHistoryDataFile() {
        // just keep current file template sequence id in range File
        final Long currentSeqId = this.metadata.getCurrentSequenceId();
        // to keep files
        final List<String> keepSeqFileAbsolutePath = new ArrayList<>();
        for (long i = 0; i < keepWalFileCount; i++) {
            // for loop | + 1 is because zero not use
            long keepSeqId = currentSeqId - i < 0 ? WalMetadataV1.getMaxSequenceId() + currentSeqId - i + 1 : currentSeqId - i;
            final String dataFileName = this.metadata.buildDataFileName(keepSeqId);
            keepSeqFileAbsolutePath.add(dataFileName);
        }
        final List<File> currentDirFiles = new ArrayList<>();
        try {
            currentDirFiles.addAll(getCurrentDirFiles());
        } catch (IllegalAccessException e) {
            log.error("get all file from dir error. file dir = {} ", fileTemplate, e);
        }

        final List<File> targetFiles = currentDirFiles.stream()
                .filter(file -> {
                    if (file.getName().contains(WAL_METADATA_FILE_SUFFIX)) {
                        return false;
                    }
                    return file.getAbsolutePath().contains(fileTemplate);
                })
                .collect(Collectors.toList());
        for (File currentDirFile : targetFiles) {
            String absolutePath = currentDirFile.getAbsolutePath();
            if (keepSeqFileAbsolutePath.contains(absolutePath)) {
                continue;
            }

            deleteHistoryWalFile(currentDirFile);
        }

    }


    /**
     * 删除历史 WAL 文件
     *
     * @param toDeleteFile
     */
    private void deleteHistoryWalFile(File toDeleteFile) {
        FileUtils233.deleteQuietly(toDeleteFile);
    }

    private void flushMetaData() {
        this.metadata.flushMetadataToFile(this.currentMetadataFile);
    }

    /**
     * it will clear all memory Data in Queue, then trigger Consumer
     */
    public void asyncFlushDisk() {
        // async callback
        this.consumerThreadPool.execute(() -> {
            lock.lock();
            try {
                if (consumerQueue.isEmpty()) {
                    return;
                }

                final List<D> objects = new ArrayList<>();
                consumerQueue.drainTo(objects, singleThreadConsumerDataSize);
                if (CollectionUtils233.isEmpty(objects)) {
                    return;
                }
                List<D> toConsumeDataList = Collections.unmodifiableList(objects);

                try {
                    flush(toConsumeDataList);
                } catch (Exception e) {
                    log.error("flush data error. please check! json = {} ", JSON.toJSONString(toConsumeDataList), e);
                }

            } finally {
                lock.unlock();
            }
        });
    }


    /**
     * 外部获取 buffer 中的数据. 相当于 Linux Dirty Page Data
     *
     * @return 内存未刷盘数据
     */
    public List<D> getMemoryData() {
        return Collections.unmodifiableList(new ArrayList<>(consumerQueue));
    }


    public void close() {
        isClose.compareAndSet(false, true);
        long doubleMs = consumerIntervalMs * 2L;
        log.info("WAL close waiting ms = {}", doubleMs);
        try {
            TimeUnit.MILLISECONDS.sleep(doubleMs);
        } catch (Throwable e) {
            log.error("WAL async thread happen interrupted! but you can't interrupted it!", e);
        }
        log.info("WAL close done");
    }


    public long getCurrentSequenceId() {
        lock.lock();
        try {
            return this.metadata.getCurrentSequenceId();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新 metadata
     *
     * @return next sequence Id
     */
    private long nextSequenceAndUpdate() {
        // not modify when close
        Long notChangeSeqId = this.metadata.getCurrentSequenceId();
        if (isClose.get()) {
            return notChangeSeqId;
        }

        // if
        try {
            List<String> lines = FileUtils233.readLines(this.metadata.getCurrentWalFile(), StandardCharsets.UTF_8);
            if (CollectionUtils233.isEmpty(lines)) {
                return notChangeSeqId;
            }
        } catch (IOException e) {
            log.error("read WAL file error!", e);
            return notChangeSeqId;
        }

        // update to next
        try {
            return this.metadata.nextSequence().getCurrentSequenceId();
        } catch (IOException e) {
            log.error("next Sequence Id and data file error", e);
            return notChangeSeqId;
        }

    }

    public boolean inputData(Collection<D> dataList) throws IllegalAccessException {
        for (D data : dataList) {
            inputData(data);
        }
        return true;
    }

    /**
     * 写入数据
     *
     * @param data 数据
     * @throws IllegalAccessException 非法访问异常 (系统结束)
     */
    public boolean inputData(D data) throws IllegalAccessException {
        if (isClose.get()) {
            throw new IllegalAccessException("System is going to close, you can't write data to WAL");
        }
        if (data == null) {
            return false;
        }

        // produce
        addDataToMemory(data);

        return true;
    }

    private void addDataToMemory(D data) {
        consumerQueue.add(data);
        producerQueue.add(data);
    }

    /**
     * 是否需要滚动
     *
     * @return true = rolling
     */
    private boolean isNeedRollingNextWalFile() {
        // time
        boolean isNeedRollingByTimeMs = this.metadata.isNeedRollingByTimeMsAndHaveData(keepWalFileMs);
        if (isNeedRollingByTimeMs) {
            return true;
        }
        // byte size
        boolean isRollingByByteSize = this.metadata.isNeedRollingByBytes(fileRollingByteSize);
        if (isRollingByByteSize) {
            return true;
        }

        return false;
    }

    // -------------------- API -----------------------


    public abstract void flush(List<D> toConsumeDataList) throws Exception;


    // -------------------- /API -----------------------

}