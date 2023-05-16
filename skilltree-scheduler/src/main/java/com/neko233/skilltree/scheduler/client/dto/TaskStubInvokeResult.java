package com.neko233.skilltree.scheduler.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果都会转为 String, 建议 @Override toString 方法, 提供自己需要的序列化
 *
 * @author SolarisNeko on 2023-05-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskStubInvokeResult {

    public static final int FAIL_CODE = 0;
    public static final int SUCCESS_CODE = 1;

    /**
     * 结果码. 0 = 通用失败, 1 = 通用成功
     */
    private int code;
    private long startMs;
    private long endMs;
    private String returnValue;


    public static TaskStubInvokeResult create() {
        TaskStubInvokeResult taskStubInvokeResult = new TaskStubInvokeResult();
        taskStubInvokeResult.startMs = System.currentTimeMillis();
        return taskStubInvokeResult;
    }

    public TaskStubInvokeResult success(String returnValue) {
        finish(SUCCESS_CODE);
        this.returnValue = returnValue;
        return this;
    }

    private void finish(int successCode) {
        this.endMs = System.currentTimeMillis();
        this.code = successCode;
    }

    public TaskStubInvokeResult failure(String returnValue) {
        finish(FAIL_CODE);
        this.returnValue = returnValue;
        return this;
    }

    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }
}
