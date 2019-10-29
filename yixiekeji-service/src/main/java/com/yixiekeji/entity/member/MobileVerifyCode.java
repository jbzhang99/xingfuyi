package com.yixiekeji.entity.member;

import java.io.Serializable;

/**
 * 小程序验证码表
 * <p>Table: <strong>mobile_verify_code</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>uid</td><td>{@link java.lang.String}</td><td>uid</td><td>varchar</td><td>uid</td></tr>
 *   <tr><td>code</td><td>{@link java.lang.String}</td><td>code</td><td>varchar</td><td>验证码</td></tr>
 * </table>
 *
 */
public class MobileVerifyCode implements Serializable {

    private java.lang.Integer id;
    private java.lang.String  uid;
    private java.lang.String  code;
    private java.lang.String  phone;
    private java.lang.Integer sendNum;
    private java.util.Date    createTime;

    /**
     * 获取id
     */
    public java.lang.Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * 获取uid
     */
    public java.lang.String getUid() {
        return this.uid;
    }

    /**
     * 设置uid
     */
    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }

    /**
     * 获取验证码
     */
    public java.lang.String getCode() {
        return this.code;
    }

    /**
     * 设置验证码
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }

    public java.lang.String getPhone() {
        return phone;
    }

    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    public java.lang.Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(java.lang.Integer sendNum) {
        this.sendNum = sendNum;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

}