package com.neko233.skilltree.commons.core.file;


import com.neko233.skilltree.annotation.Out;
import com.neko233.skilltree.commons.core.base.CollectionUtils233;
import com.neko233.skilltree.commons.core.base.DataSizeUtils233;
import com.neko233.skilltree.commons.core.base.RegexUtils233;
import com.neko233.skilltree.commons.core.file.visitor.FileVisitorForAccumulate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.file.Counters;
import org.apache.commons.io.file.PathUtils;
import org.apache.commons.io.file.StandardDeleteOption;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SolarisNeko
 */
@Slf4j
public class FileUtils233 {

    public static final String PROJECT_HOME = "${app.projectHome}";

    public static List<String> readLines(final File file,
                                         final Charset charset) throws IOException {
        try (InputStream inputStream = openInputStream(file)) {
            return readLines(inputStream, chooseCharset(charset));
        }
    }

    public static List<String> readLines(final InputStream input,
                                         final Charset charset) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, chooseCharset(charset));
        return readLines(reader);
    }

    public static List<String> readLines(final Reader reader) throws IOException {
        final BufferedReader bufReader = toBufferedReader(reader);
        final List<String> lineList = new ArrayList<>();
        String line;
        while ((line = bufReader.readLine()) != null) {
            lineList.add(line);
        }
        return lineList;
    }


    // ----------- base ---------------

    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static FileInputStream openInputStream(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        return new FileInputStream(file);
    }

    private static Charset chooseCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * 读取 properties file
     *
     * @param propsFilename 文件名
     * @return Properties
     */
    public static Properties readPropertiesFromFilename(String propsFilename) {
        Properties archProps = new Properties();
        LinkedHashSet<ClassLoader> list = Stream.of(
                        Thread.currentThread().getContextClassLoader(),
                        ClassLoader.getSystemClassLoader(),
                        FileUtils233.class.getClassLoader()
                )
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (ClassLoader loader : list) {
            if (readPropertiesFromClassLoader(propsFilename, archProps, loader)) {
                return archProps;
            }
        }
        log.warn("Failed to load configuration file from classloader: {}", propsFilename);
        return archProps;
    }


    /**
     * ClassLoader 加载 properties 文件
     *
     * @param resourcesPropertyFileName resources/ 目录下的文件名
     * @param properties                属性
     * @param classLoader               类加载器
     * @return 是否加载成功
     */
    private static boolean readPropertiesFromClassLoader(String resourcesPropertyFileName,
                                                         @Out Properties properties,
                                                         ClassLoader classLoader) {
        if (classLoader == null) {
            return false;
        }
        // Load the configuration file from the classLoader
        try {
            List<URL> resources = Collections.list(classLoader.getResources(resourcesPropertyFileName));
            if (resources.isEmpty()) {
                log.debug("No {} file found from ClassLoader {}", resourcesPropertyFileName, classLoader);
                return false;
            }
            if (resources.size() > 1) {
                log.warn("Configuration conflict: there is more than one {} file on the classpath: {}",
                        resourcesPropertyFileName,
                        resources);
            }
            try (InputStream in = resources.get(0).openStream()) {
                if (in != null) {
                    properties.load(in);
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static List<File> showFiles(final File fileOrDir) throws IOException {
        return showFiles(fileOrDir, false, null);
    }

    public static List<File> showFiles(final File fileOrDir,
                                       final boolean isRecursive) throws IOException {
        return showFiles(fileOrDir, isRecursive, null);
    }

    /**
     * 显示文件
     *
     * @param fileOrDir   文件/目录
     * @param isRecursive 是否递归
     * @param suffixes    后缀名称. example = ".txt"
     * @return 纯文件 (不包含 dir)
     * @throws IOException
     */
    public static List<File> showFiles(final File fileOrDir,
                                       final boolean isRecursive,
                                       final List<String> suffixes)
            throws IOException {
        final List<String> suffixList = Optional.ofNullable(suffixes).orElse(new ArrayList<>());

        if (fileOrDir == null) {
            return new ArrayList<>();
        }

        File dir = fileOrDir;
        if (fileOrDir.isFile()) {
            return Collections.singletonList(fileOrDir);
        }

        Path dirPath = dir.toPath();

        // single
        if (!isRecursive) {
            File[] array = dir.listFiles();
            if (array == null) {
                return new ArrayList<>();
            }
            return Arrays.stream(array).filter(File::isFile).collect(Collectors.toList());
        }


        final EnumSet<FileVisitOption> fileVisitOptions = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        final FileVisitorForAccumulate visitor = new FileVisitorForAccumulate();
        Files.walkFileTree(dirPath, fileVisitOptions, Integer.MAX_VALUE, visitor);
        List<File> justFileList = visitor.getJustFileList();
        return Optional.ofNullable(justFileList)
                .orElse(new ArrayList<>())
                .stream()
                .filter(file -> {
                    if (CollectionUtils233.isEmpty(suffixList)) {
                        return true;
                    }
                    // filter suffix
                    for (String fileSuffix : suffixList) {
                        return file.getName().endsWith(fileSuffix);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private static String getFileTreeString(File f,
                                            int level) {
        // 返回一个抽象路径名数组，这些路径名表示此抽象路径名所表示目录中地文件
        File[] childs = f.listFiles();
        StringBuilder fileTreeBuilder = new StringBuilder();
        for (int i = 0; i < childs.length; i++) {
            // 打印前缀
            for (int j = 0; j < level; j++) {
                if (j == 0) {
                    fileTreeBuilder.append("|＿");
                } else {
                    fileTreeBuilder.append("＿");
                }
            }

            if (childs[i].isDirectory()) { //
                fileTreeBuilder.append(childs[i].getName());// 打印子文件地名字
                fileTreeBuilder.append(System.lineSeparator());

                String subFileTree = getFileTreeString(childs[i], level + 1);
                fileTreeBuilder.append(subFileTree);
            } else {
                // 如果是文件把大小也打印出来
                fileTreeBuilder.append(childs[i].getName()).append("\t\t\t\t\t")
                        // size
                        .append(DataSizeUtils233.toHumanFormatByByte(childs[i].length()));
                fileTreeBuilder.append(System.lineSeparator());
            }

        }
        return fileTreeBuilder.toString();
    }


    /**
     * 线性化文件树， Tree -> List
     *
     * @param startDir 需要遍历的文件夹
     * @return file tree -> all file List
     */
    public static List<File> liner(String startDir) {
        List<File> fileList = new ArrayList<>();

        File directory = new File(startDir);
        if (!directory.exists() || !directory.isDirectory()) {
            return fileList;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        for (File file : files) {
            if (file.isDirectory()) {
                // loop
                List<File> liner = liner(file.getPath());
                fileList.addAll(liner);
                continue;
            }
            if (!file.isFile()) {
                continue;
            }
            if (file.length() == 0) {
                continue;
            }
            fileList.add(file);
        }
        return fileList;
    }


    /**
     * 新建文件 if 不存在
     *
     * @param newFile 新文件
     */
    public static boolean createFileIfNotExists(File newFile) throws IOException {

        if (newFile == null) {
            return false;
        }
        createAllParentDir(newFile);

        if (newFile.exists()) {
            return false;
        }
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            log.error("新文件创建失败. filePath = {}", newFile.getAbsolutePath());
            throw new IOException(e);
        }
        return true;
    }

    public static String readAllContent(File file) throws IOException {
        return readAllContent(file, StandardCharsets.UTF_8);
    }

    public static String readAllContent(File file,
                                        Charset charset) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        return readLines(file, charset)
                .stream()
                .collect(Collectors.joining(System.lineSeparator()));
    }


    public static void write(final File file,
                             final CharSequence data) throws IOException {
        write(file, data, StandardCharsets.UTF_8);
    }

    /**
     * default Append !
     *
     * @param file    文本
     * @param data    数据
     * @param charset 编码
     * @throws IOException
     */
    public static void write(final File file,
                             final CharSequence data,
                             final Charset charset) throws IOException {
        write(file, data, charset, true);
    }

    /**
     * Writes a CharSequence to a file creating the file if it does not exist.
     *
     * @param file    the file to write
     * @param data    the content to write to the file
     * @param charset the charset to use, {@code null} means platform default
     * @param append  if {@code true}, then the data will be added to the
     *                end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     */
    public static void write(final File file,
                             final CharSequence data,
                             final Charset charset,
                             final boolean append)
            throws IOException {
        writeStringToFile(file, Objects.toString(data, null), charset, append);
    }


    /**
     * 写入文本到文件
     *
     * @param file
     * @param data
     * @param charset
     * @param append
     * @throws IOException
     */
    public static void writeStringToFile(final File file,
                                         final String data,
                                         final Charset charset,
                                         final boolean append) throws IOException {
        try (OutputStream out = openOutputStream(file, append)) {
            IoUtils233.write(data, out, charset);
        }
    }

    public static FileOutputStream openOutputStream(final File file,
                                                    final boolean append) throws IOException {
        Objects.requireNonNull(file, "file");
        if (file.exists()) {
            requireFile(file, "file");
            requireCanWrite(file, "file");
        } else {
            createParentDirectories(file);
        }
        return new FileOutputStream(file, append);
    }

    /**
     * Requires that the given {@code File} is a file.
     *
     * @param file The {@code File} to check.
     * @param name The parameter name to use in the exception message.
     * @return the given file.
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    private static File requireFile(final File file,
                                    final String name) {
        Objects.requireNonNull(file, name);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Parameter '" + name + "' is not a file: " + file);
        }
        return file;
    }

    /**
     * 文件必须可写入
     *
     * @param file The file to test.
     * @param name The parameter name to use in the exception message.
     * @throws NullPointerException     if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the file is not writable.
     */
    private static void requireCanWrite(final File file,
                                        final String name) {
        Objects.requireNonNull(file, "file");
        if (!file.canWrite()) {
            throw new IllegalArgumentException("File parameter '" + name + " is not writable: '" + file + "'");
        }
    }

    /**
     * Creates all parent directories for a File object.
     *
     * @param file the File that may need parents, may be null.
     * @return The parent directory, or {@code null} if the given file does not name a parent
     * @throws IOException if the directory was not created along with all its parent directories.
     * @throws IOException if the given file object is not null and not a directory.
     * @since 2.9.0
     */
    public static File createParentDirectories(final File file) throws IOException {
        return createAllParentDir(getParentFile(file));
    }

    private static File getParentFile(final File file) {
        return file == null ? null : file.getParentFile();
    }

    /**
     * Calls {@link File#mkdirs()} and throws an exception on failure.
     *
     * @param directory the receiver for {@code mkdirs()}, may be null.
     * @return the given file, may be null.
     * @throws IOException       if the directory was not created along with all its parent directories.
     * @throws IOException       if the given file object is not a directory.
     * @throws SecurityException See {@link File#mkdirs()}.
     * @see File#mkdirs()
     */
    private static File createAllParentDir(final File directory) throws IOException {
        if ((directory != null)
                && (!directory.mkdirs()
                && !directory.isDirectory())) {
            throw new IOException("Cannot create directory '" + directory + "'.");
        }
        return directory;
    }


    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The default VM encoding and the default line ending will be used.
     *
     * @param file  the file to write to
     * @param lines the lines to write, {@code null} entries produce blank lines
     * @throws IOException in case of an I/O error
     * @since 1.3
     */
    public static void writeLines(final File file,
                                  final Collection<?> lines) throws IOException {
        writeLines(file, null, lines, null, false);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The default VM encoding and the default line ending will be used.
     *
     * @param file   the file to write to
     * @param lines  the lines to write, {@code null} entries produce blank lines
     * @param append if {@code true}, then the lines will be added to the
     *               end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeLines(final File file,
                                  final Collection<?> lines,
                                  final boolean append) throws IOException {
        writeLines(file, null, lines, null, append);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The default VM encoding and the specified line ending will be used.
     *
     * @param file       the file to write to
     * @param lines      the lines to write, {@code null} entries produce blank lines
     * @param lineEnding the line separator to use, {@code null} is system default
     * @throws IOException in case of an I/O error
     * @since 1.3
     */
    public static void writeLines(final File file,
                                  final Collection<?> lines,
                                  final String lineEnding)
            throws IOException {
        writeLines(file, null, lines, lineEnding, false);
    }


    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The default VM encoding and the specified line ending will be used.
     *
     * @param file       the file to write to
     * @param lines      the lines to write, {@code null} entries produce blank lines
     * @param lineEnding the line separator to use, {@code null} is system default
     * @param append     if {@code true}, then the lines will be added to the
     *                   end of the file rather than overwriting
     * @throws IOException in case of an I/O error
     * @since 2.1
     */
    public static void writeLines(final File file,
                                  final Collection<?> lines,
                                  final String lineEnding,
                                  final boolean append) throws IOException {
        writeLines(file, null, lines, lineEnding, append);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the default line ending will be used.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     * </p>
     *
     * @param file        the file to write to
     * @param charsetName the name of the requested charset, {@code null} means platform default
     * @param lines       the lines to write, {@code null} entries produce blank lines
     * @throws IOException                  in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 1.1
     */
    public static void writeLines(final File file,
                                  final String charsetName,
                                  final Collection<?> lines)
            throws IOException {
        writeLines(file, charsetName, lines, null, false);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line, optionally appending.
     * The specified character encoding and the default line ending will be used.
     *
     * @param file        the file to write to
     * @param charsetName the name of the requested charset, {@code null} means platform default
     * @param lines       the lines to write, {@code null} entries produce blank lines
     * @param append      if {@code true}, then the lines will be added to the
     *                    end of the file rather than overwriting
     * @throws IOException                  in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static void writeLines(final File file,
                                  final String charsetName,
                                  final Collection<?> lines,
                                  final boolean append) throws IOException {
        writeLines(file, charsetName, lines, null, append);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the line ending will be used.
     * <p>
     * NOTE: As from v1.3, the parent directories of the file will be created
     * if they do not exist.
     * </p>
     *
     * @param file        the file to write to
     * @param charsetName the name of the requested charset, {@code null} means platform default
     * @param lines       the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @throws IOException                  in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 1.1
     */
    public static void writeLines(final File file,
                                  final String charsetName,
                                  final Collection<?> lines,
                                  final String lineEnding) throws IOException {
        writeLines(file, charsetName, lines, lineEnding, false);
    }

    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the line ending will be used.
     *
     * @param file        the file to write to
     * @param charsetName the name of the requested charset, {@code null} means platform default
     * @param lines       the lines to write, {@code null} entries produce blank lines
     * @param lineEnding  the line separator to use, {@code null} is system default
     * @param append      if {@code true}, then the lines will be added to the
     *                    end of the file rather than overwriting
     * @throws IOException                  in case of an I/O error
     * @throws UnsupportedEncodingException if the encoding is not supported by the VM
     */
    public static void writeLines(final File file,
                                  final String charsetName,
                                  final Collection<?> lines,
                                  final String lineEnding,
                                  final boolean append
    ) throws IOException {
        try (OutputStream out = new BufferedOutputStream(openOutputStream(file, append))) {
            IOUtils.writeLines(lines, lineEnding, out, charsetName);
        }
    }

    public static boolean deleteQuietly(final File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                cleanDirectory(file);
            }
        } catch (final Exception ignored) {
            // ignore
        }

        try {
            return file.delete();
        } catch (final Exception ignored) {
            return false;
        }
    }

    public static void cleanDirectory(final File directory) throws IOException {
        List<File> files = showFiles(directory, true);

        final List<Exception> causeList = new ArrayList<>();
        for (final File file : files) {
            try {
                forceDelete(file);
            } catch (final IOException ioe) {
                causeList.add(ioe);
            }
        }

        if (!causeList.isEmpty()) {
            throw new IOExceptionList(directory.toString(), causeList);
        }
    }

    public static void forceDelete(final File file) throws IOException {
        Objects.requireNonNull(file, "file");
        final Counters.PathCounters deleteCounters;
        try {
            deleteCounters = PathUtils.delete(file.toPath(), PathUtils.EMPTY_LINK_OPTION_ARRAY,
                    StandardDeleteOption.OVERRIDE_READ_ONLY);
        } catch (final IOException e) {
            throw new IOException("Cannot delete file: " + file, e);
        }

        if (deleteCounters.getFileCounter().get() < 1 && deleteCounters.getDirectoryCounter().get() < 1) {
            // didn't find a file to delete.
            throw new FileNotFoundException("File does not exist: " + file);
        }
    }

    public static void iterateLines(File file,
                                    Consumer<String> lineConsumer) throws IOException {
        //创建类进行文件的读取，并指定编码格式为utf-8
        InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(read);//可用于读取指定文件
        String line = null;

        while ((line = in.readLine()) != null) {
            lineConsumer.accept(line);
        }
        in.close();
    }

    /**
     * 文件是否空白 = not exists / blank
     *
     * @param fileAbsolutePath 目标文件全路径
     * @return isBlank
     */
    public static boolean isFileBlank(String fileAbsolutePath) {
        File file = new File(fileAbsolutePath);
        if (!file.exists()) {
            return true;
        }
        if (file.length() == 0) {
            return true;
        }
        return false;
    }


    public static File createFileX(String pathGrammar) throws IOException {
        File file = newFileX(pathGrammar);
        if (file == null) {
            return null;
        }
        if (file.exists()) {
            return file;
        }
        createAllParentDir(file);
        file.createNewFile();
        return file;
    }

    /**
     * 创建文件 | 使用 [neko233 自定义语法, Like linux-style] 做了 File 操作增强
     *
     * @param pathGrammar 路径语法
     * @return 文件
     */
    public static File newFileX(String pathGrammar) {
        String path = pathGrammar;


        // "~/" == 为家目录
        if (path.contains("~")) {
            // 获取用户的家目录
            String userHome = System.getProperty("user.home");
            String unixPath = userHome.replaceAll("\\\\", "/");
            path = path.replaceFirst("^~", unixPath);
        }

        // ${app.projectHome} == 项目目录
        if (path.contains(PROJECT_HOME)) {
            String projectHome = System.getProperty("user.dir");
            String unixPath = projectHome.replaceAll("\\\\", "/");
            String regexText = RegexUtils233.convertTextForNotRegex(PROJECT_HOME);
            path = path.replaceFirst(regexText, unixPath);
        }


        // 创建文件对象
        return new File(path);
    }


    /**
     * 获取文件的相对路径
     *
     * @param fileAbsolutePath 文件完整路径
     * @param parentPath       父级路径
     * @return 相对路径
     */
    public static String getFileRelativePathList(String fileAbsolutePath,
                                                 String parentPath) {
        fileAbsolutePath = fileAbsolutePath.replaceAll("\\\\", "/");
        parentPath = parentPath.replaceAll("\\\\", "/");

        if (fileAbsolutePath.startsWith(parentPath)) {
            return fileAbsolutePath.substring(parentPath.length() + 1);
        }
        return fileAbsolutePath;
    }

}
