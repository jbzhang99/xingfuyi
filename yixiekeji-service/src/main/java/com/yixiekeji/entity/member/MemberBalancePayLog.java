package com.yixiekeji.entity.member;

import java.io.Serializable;

/**
 * 会员充值记录
 * <p>Table: <strong>member_balance_pay_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>paymentCode</td><td>{@link java.lang.String}</td><td>payment_code</td><td>varchar</td><td>支付方式code：PCUNIONPAY；H5UNIONPAY；PCALIPAY；H5ALIPAY；PCWXPAY；H5WXPAY</td></tr>
 *   <tr><td>paymentName</td><td>{@link java.lang.String}</td><td>payment_name</td><td>varchar</td><td>支付方式名称：PC银联；H5银联；PC支付宝；H5支付宝；PC微信；H5微信</td></tr>
 *   <tr><td>payMoney</td><td>{@link java.math.BigDecimal}</td><td>pay_money</td><td>decimal</td><td>支付金额</td></tr>
 *   <tr><td>payState</td><td>{@link java.lang.Integer}</td><td>pay_state</td><td>tinyint</td><td>支付状态 1-未支付 2-已支付</td></tr>
 *   <tr><td>paySn</td><td>{@link java.lang.String}</td><td>pay_sn</td><td>varchar</td><td>支付号（提交支付的订单号）</td></tr>
 *   <tr><td>tradeSn</td><td>{@link java.lang.String}</td><td>trade_sn</td><td>varchar</td><td>支付交易流水号（银行返回）</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>用户id</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>用户名</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>payFinishTime</td><td>{@link java.util.Date}</td><td>pay_finish_time</td><td>datetime</td><td>支付完成时间</td></tr>
 * </table>
 *
 */
public class MemberBalancePayLog implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = 7338480303336957846L;
    private java.lang.Integer    id;                                     //id
    private java.lang.String     paymentCode;                            //支付方式code：PCUNIONPAY；H5UNIONPAY；PCALIPAY；H5ALIPAY；PCWXPAY；H5WXPAY
    private java.lang.String     paymentName;                            //支付方式名称：PC银联；H5银联；PC支付宝；H5支付宝；PC微信；H5微信
    private java.math.BigDecimal payMoney;                               //支付金额
    private java.lang.Integer    payState;                               //支付状态 1-未支付 2-已支付
    private java.lang.String     paySn;                                  //支付号（提交支付的订单号）
    private java.lang.String     tradeSn;                                //支付交易流水号（银行返回）
    private java.lang.Integer    memberId;                               //用户id
    private java.lang.String     memberName;                             //用户名
    private java.util.Date       createTime;                             //创建时间
    private java.util.Date       payFinishTime;                          //支付完成时间

    public static final int      UN_PAY           = 1;
    public static final int      PAY_SUCCESS      = 2;

    public static final String   PCUNIONPAY       = "PCUNIONPAY";
    public static final String   H5UNIONPAY       = "H5UNIONPAY";

    public static final String   PCALIPAY         = "PCALIPAY";
    public static final String   H5ALIPAY         = "H5ALIPAY";

    public static final String   PCWXPAY          = "PCWXPAY";
    public static final String   H5WXPAY          = "H5WXPAY";

    public static final String   PCUNIONPAY_NAME  = "PC银联";
    public static final String   H5UNIONPAY_NAME  = "H5银联";

    public static final String   PCALIPAY_NAME    = "PC支付宝";
    public static final String   H5ALIPAY_NAME    = "H5支付宝";

    public static final String   PCWXPAY_NAME     = "PC微信";
    public static final String   H5WXPAY_NAME     = "H5微信";

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
     * 获取支付状态 1-未支付 2-已支付
     */
    public java.lang.Integer getPayState() {
        return this.payState;
    }

    /**
     * 设置支付状态 1-未支付 2-已支付
     */
    public void setPayState(java.lang.Integer payState) {
        this.payState = payState;
    }

    /**
     * 获取支付号（提交支付的订单号）
     */
    public java.lang.String getPaySn() {
        return this.paySn;
    }

    /**
     * 设置支付号（提交支付的订单号）
     */
    public void setPaySn(java.lang.String paySn) {
        this.paySn = paySn;
    }

    /**
     * 获取支付交易流水号（银行返回）
     */
    public java.lang.String getTradeSn() {
        return this.tradeSn;
    }

    /**
     * 设置支付交易流水号（银行返回）
     */
    public void setTradeSn(java.lang.String tradeSn) {
        this.tradeSn = tradeSn;
    }

    /**
     * 获取用户id
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户id
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取用户名
     */
    public java.lang.String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置用户名
     */
    public void setMemberName(java.lang.String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取创建时间
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付完成时间
     */
    public java.util.Date getPayFinishTime() {
        return this.payFinishTime;
    }

    /**
     * 设置支付完成时间
     */
    public void setPayFinishTime(java.util.Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }
}