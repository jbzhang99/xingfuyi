package com.yixiekeji.vo.member;

import java.io.Serializable;

public class FrontCheckPwdVO implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7462895718240513324L;
    private boolean           correct;                                 //支付密码是否正确
    private Integer           pwdErrCount;                             //支付密码输错个数

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Integer getPwdErrCount() {
        return pwdErrCount;
    }

    public void setPwdErrCount(Integer pwdErrCount) {
        this.pwdErrCount = pwdErrCount;
    }

}
