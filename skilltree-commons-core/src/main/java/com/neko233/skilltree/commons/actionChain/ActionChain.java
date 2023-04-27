package com.neko233.skilltree.commons.actionChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author SolarisNeko on 2022-09-30
 **/
public class ActionChain {

    private static final Logger log = LoggerFactory.getLogger(ActionChain.class);
    /**
     * 最大重试次数
     */
    private final int maxRetryTime = 100_0000;
    private final List<ExecuteAction> actionList = new LinkedList<>();
    private final List<ExceptionAction> handleExceptionChain = new LinkedList<>();
    private final List<FinallyAction> finallyActionChain = new LinkedList<>();
    private final List<Throwable> rememberThrowableList = new ArrayList<>();
    private boolean isHandleException = false;
    private int retryCount = 0;
    private int retryIntervalSecond = 1;

    private ActionChain() {

    }

    public static ActionChain create() {
        return new ActionChain();
    }

    /**
     * 获取抛出的所有异常
     *
     * @return 处理过的异常列表
     */
    public List<Throwable> getRememberThrowableList() {
        return rememberThrowableList;
    }

    public ActionChain success(ExecuteAction action) {
        actionList.add(action);
        return this;
    }

    public ActionChain exception(ExceptionAction action) {
        isHandleException = true;
        handleExceptionChain.add(action);
        return this;
    }

    public ActionChain doFinally(FinallyAction action) {
        finallyActionChain.add(action);
        return this;
    }

    public ActionChain retry(int count) {
        retryCount = count;
        return this;
    }

    public ActionChain retry(int count, int retryIntervalSecond) {
        retryCount = Math.min(count, maxRetryTime);
        this.retryIntervalSecond = retryIntervalSecond;
        return this;
    }

    /**
     * 配置 closable field
     *
     * @return this
     */
    public ActionChain closable(Closeable closeable) {
        if (closeable == null) {
            return this;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            handleExceptionInChain("closable", e);
        }
        return this;
    }


    /**
     * 链式处理 Exception
     *
     * @param e 异常
     */
    private void handleExceptionInChain(String stage, Throwable e) {
        // record
        rememberThrowableList.add(e);

        log.debug("[ActionChain] stage = [ {} ], handle exception.", stage);
        for (ExceptionAction action : handleExceptionChain) {
            if (action == null) {
                continue;
            }
            action.handleException(e);
        }
    }

    public void execute() {
        if (actionList.size() == 0) {
            return;
        }

        boolean isOk = true;
        try {
            executeActionChainWithRetry(isOk);
        } catch (Exception e) {
            if (!isHandleException) {
                return;
            }
            handleExceptionInChain("execute", e);
        } finally {
            for (FinallyAction finallyAction : finallyActionChain) {
                try {
                    finallyAction.doFinally();
                } catch (Exception e) {
                    handleExceptionInChain("finally", e);
                }
            }
        }
    }

    private void executeActionChainWithRetry(boolean isOk) throws Exception {
        for (int count = 0; count < retryCount; count++) {
            if (count > 0) {
                log.debug("[ActionChain] actions execute error. Retry times = {}, timeUnit = {} second", count, retryIntervalSecond);
            }

            try {
                for (ExecuteAction action : actionList) {
                    if (action == null) {
                        continue;
                    }
                    action.execute();
                }
            } catch (Exception e) {
                isOk = false;
                TimeUnit.SECONDS.sleep(retryIntervalSecond);
            }

            if (isOk) {
                break;
            }
        }
    }

}
