package com.yixiekeji.core.sms.bean;

/**
 * 验证码
 *                       
 * @Filename: VerifyCodeResult.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class VerifyCodeResult {
    private Integer remainpoint;
    //成功条数
    private String  successCounts;
    //返回消息
    private String  message;
    //状态码
    private String  returnstatus;
    private String  taskID;
    private String  verifyCode;

    public Integer getRemainpoint() {
        return remainpoint;
    }

    public void setRemainpoint(Integer remainpoint) {
        this.remainpoint = remainpoint;
    }

    public String getSuccessCounts() {
        return successCounts;
    }

    public void setSuccessCounts(String successCounts) {
        this.successCounts = successCounts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReturnstatus() {
        return returnstatus;
    }

    public void setReturnstatus(String returnstatus) {
        this.returnstatus = returnstatus;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "VerifyCodeResult [remainpoint=" + remainpoint + ", successCounts=" + successCounts
               + ", message=" + message + ", returnstatus=" + returnstatus + ", taskID=" + taskID
               + ", verifyCode=" + verifyCode + "]";
    }

}
