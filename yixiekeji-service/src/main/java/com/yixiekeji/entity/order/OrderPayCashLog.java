package com.yixiekeji.entity.order;

import java.io.Serializable;

/**
 * 订单第三方支付日志表
 * <p>Table: <strong>order_pay_cash_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>paySn</td><td>{@link java.lang.String}</td><td>pay_sn</td><td>varchar</td><td>支付订单号，第三方支付时传给第三方的订单号</td></tr>
 *   <tr><td>tradeSn</td><td>{@link java.lang.String}</td><td>trade_sn</td><td>varchar</td><td>第三方支付交易流水号</td></tr>
 *   <tr><td>paymentCode</td><td>{@link java.lang.String}</td><td>payment_code</td><td>varchar</td><td>支付方式code：PCUNIONPAY；H5UNIONPAY；PCALIPAY；H5ALIPAY；PCWXPAY；H5WXPAY</td></tr>
 *   <tr><td>paymentName</td><td>{@link java.lang.String}</td><td>payment_name</td><td>varchar</td><td>支付方式名称：PC银联；H5银联；PC支付宝；H5支付宝；PC微信；H5微信</td></tr>
 *   <tr><td>payMoney</td><td>{@link java.math.BigDecimal}</td><td>pay_money</td><td>decimal</td><td>支付金额</td></tr>
 *   <tr><td>payOrderSn</td><td>{@link java.lang.String}</td><td>pay_order_sn</td><td>text</td><td>支付的所有订单号以逗号分隔</td></tr>
 *   <tr><td>payContent</td><td>{@link java.lang.String}</td><td>pay_content</td><td>text</td><td>支付返回的代码</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>memberId</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>memberName</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>支付时间</td></tr>
 * </table>
 *
 */
public class OrderPayCashLog implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = 3167040709163669191L;

    private java.lang.Integer    id;
    private java.lang.String     paySn;
    private java.lang.String     tradeSn;
    private java.lang.String     paymentCode;
    private java.lang.String     paymentName;
    private java.math.BigDecimal payMoney;
    private java.lang.String     payOrderSn;
    private java.lang.String     payContent;
    private java.lang.Integer    memberId;
    private java.lang.String     memberName;
    private java.util.Date       createTime;

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
     * 获取支付订单号，第三方支付时传给第三方的订单号
     */
    public java.lang.String getPaySn() {
        return this.paySn;
    }

    /**
     * 设置支付订单号，第三方支付时传给第三方的订单号
     */
    public void setPaySn(java.lang.String paySn) {
        this.paySn = paySn;
    }

    /**
     * 获取第三方支付交易流水号
     */
    public java.lang.String getTradeSn() {
        return this.tradeSn;
    }

    /**
     * 设置第三方支付交易流水号
     */
    public void setTradeSn(java.lang.String tradeSn) {
        this.tradeSn = tradeSn;
    }

    /**
     * 获取支付方式code：PCUNIONPAY；H5UNIONPAY；PCALIPAY；H5ALIPAY；PCWXPAY；H5WXPAY
     */
    public java.lang.String getPaymentCode() {
        return this.paymentCode;
    }

    /**
     * 设置支付方式code：PCUNIONPAY；H5UNIONPAY；PCALIPAY；H5ALIPAY；PCWXPAY；H5WXPAY
     */
    public void setPaymentCode(java.lang.String paymentCode) {
        this.paymentCode = paymentCode;
    }

    /**
     * 获取支付方式名称：PC银联；H5银联；PC支付宝；H5支付宝；PC微信；H5微信
     */
    public java.lang.String getPaymentName() {
        return this.paymentName;
    }

    /**
     * 设置支付方式名称：PC银联；H5银联；PC支付宝；H5支付宝；PC微信；H5微信
     */
    public void setPaymentName(java.lang.String paymentName) {
        this.paymentName = paymentName;
    }

    /**
     * 获取支付金额
     */
    public java.math.BigDecimal getPayMoney() {
        return this.payMoney;
    }

    /**
     * 设置支付金额
     */
    public void setPayMoney(java.math.BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 获取支付的所有订单号以逗号分隔
     */
    public java.lang.String getPayOrderSn() {
        return this.payOrderSn;
    }

    /**
     * 设置支付的所有订单号以逗号分隔
     */
    public void setPayOrderSn(java.lang.String payOrderSn) {
        this.payOrderSn = payOrderSn;
    }

    /**
     * 获取支付返回的代码
     */
    public java.lang.String getPayContent() {
        return this.payContent;
    }

    /**
     * 设置支付返回的代码
     */
    public void setPayContent(java.lang.String payContent) {
        this.payContent = payContent;
    }

    /**
     * 获取memberId
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置memberId
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取memberName
     */
    public java.lang.String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置memberName
     */
    public void setMemberName(java.lang.String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取支付时间
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置支付时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
}