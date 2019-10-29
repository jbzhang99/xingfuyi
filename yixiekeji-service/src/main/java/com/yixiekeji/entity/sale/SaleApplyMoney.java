package com.yixiekeji.entity.sale;

import java.io.Serializable;

/**
 * 提款申请表
 * <p>Table: <strong>sale_apply_money</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>用户ID</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>用户Name</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>0、未付款；1、已付款</td></tr>
 *   <tr><td>certificateCode</td><td>{@link java.lang.String}</td><td>certificate_code</td><td>varchar</td><td>证件号码</td></tr>
 *   <tr><td>bankType</td><td>{@link java.lang.String}</td><td>bank_type</td><td>varchar</td><td>开户行</td></tr>
 *   <tr><td>bankCode</td><td>{@link java.lang.String}</td><td>bank_code</td><td>varchar</td><td>银行卡号</td></tr>
 *   <tr><td>bankName</td><td>{@link java.lang.String}</td><td>bank_name</td><td>varchar</td><td>开户人姓名</td></tr>
 *   <tr><td>bankAdd</td><td>{@link java.lang.String}</td><td>bank_add</td><td>varchar</td><td>开户行地址</td></tr>
 *   <tr><td>money</td><td>{@link java.math.BigDecimal}</td><td>money</td><td>decimal</td><td>金额</td></tr>
 *   <tr><td>bake</td><td>{@link java.lang.String}</td><td>bake</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>取款时间</td></tr>
 *   <tr><td>updateId</td><td>{@link java.lang.Integer}</td><td>update_id</td><td>int</td><td>汇款人ID</td></tr>
 *   <tr><td>updateName</td><td>{@link java.lang.String}</td><td>update_name</td><td>varchar</td><td>汇款人姓名</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>datetime</td><td>汇款时间</td></tr>
 * </table>
 *
 */
public class SaleApplyMoney implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = 4611689982292832728L;
    private java.lang.Integer    id;
    private java.lang.Integer    memberId;
    private java.lang.String     memberName;
    private java.lang.Integer    state;
    private java.lang.String     certificateCode;
    private java.lang.String     bankType;
    private java.lang.String     bankCode;
    private java.lang.String     bankName;
    private java.lang.String     bankAdd;
    private java.math.BigDecimal money;
    private java.lang.String     bake;
    private java.util.Date       createTime;
    private java.lang.Integer    updateId;
    private java.lang.String     updateName;
    private java.util.Date       updateTime;

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
     * 获取用户ID
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户ID
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取用户Name
     */
    public java.lang.String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置用户Name
     */
    public void setMemberName(java.lang.String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取0、未付款；1、已付款
     */
    public java.lang.Integer getState() {
        return this.state;
    }

    /**
     * 设置0、未付款；1、已付款
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    /**
     * 获取证件号码
     */
    public java.lang.String getCertificateCode() {
        return this.certificateCode;
    }

    /**
     * 设置证件号码
     */
    public void setCertificateCode(java.lang.String certificateCode) {
        this.certificateCode = certificateCode;
    }

    /**
     * 获取开户行
     */
    public java.lang.String getBankType() {
        return this.bankType;
    }

    /**
     * 设置开户行
     */
    public void setBankType(java.lang.String bankType) {
        this.bankType = bankType;
    }

    /**
     * 获取银行卡号
     */
    public java.lang.String getBankCode() {
        return this.bankCode;
    }

    /**
     * 设置银行卡号
     */
    public void setBankCode(java.lang.String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取开户人姓名
     */
    public java.lang.String getBankName() {
        return this.bankName;
    }

    /**
     * 设置开户人姓名
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取开户行地址
     */
    public java.lang.String getBankAdd() {
        return this.bankAdd;
    }

    /**
     * 设置开户行地址
     */
    public void setBankAdd(java.lang.String bankAdd) {
        this.bankAdd = bankAdd;
    }

    /**
     * 获取金额
     */
    public java.math.BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置金额
     */
    public void setMoney(java.math.BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取备注
     */
    public java.lang.String getBake() {
        return this.bake;
    }

    /**
     * 设置备注
     */
    public void setBake(java.lang.String bake) {
        this.bake = bake;
    }

    /**
     * 获取取款时间
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置取款时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取汇款人ID
     */
    public java.lang.Integer getUpdateId() {
        return this.updateId;
    }

    /**
     * 设置汇款人ID
     */
    public void setUpdateId(java.lang.Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取汇款人姓名
     */
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 设置汇款人姓名
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
    }

    /**
     * 获取汇款时间
     */
    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置汇款时间
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }
}