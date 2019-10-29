package com.weixin.bean;

import java.math.BigDecimal;

/**
 * 微信post过来的xml转换成bean
 *                       
 * @Filename: WeChatBuyPost.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class WeChatBuyPost {
    private String     openid;        // 支付该笔订单的用户 ID
    private String     appid;         // 公众号 id
    private String     is_subscribe;  // 用户是否关注了公众号。1 为关注，0 为未关注
    private Long       time_end;      // 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
    private String     nonce_str;     // 随机字符串；字段来源：商户生成的随机字符
    private String     attach;
    private String     bank_type;
    private String     cash_fee;
    private String     mch_id;
    private String     out_trade_no;
    private String     result_code;
    private String     return_code;
    private String     sign;
    private BigDecimal total_fee;
    private String     transaction_id;
    private String     fee_type;
    private String     trade_type;

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public Long getTime_end() {
        return time_end;
    }

    public void setTime_end(Long time_end) {
        this.time_end = time_end;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    @Override
    public String toString() {
        return "WeChatBuyPost [openid=" + openid + ", appid=" + appid + ", is_subscribe="
               + is_subscribe + ", time_end=" + time_end + ", nonce_str=" + nonce_str + ", attach="
               + attach + ", bank_type=" + bank_type + ", cash_fee=" + cash_fee + ", mch_id="
               + mch_id + ", out_trade_no=" + out_trade_no + ", result_code=" + result_code
               + ", return_code=" + return_code + ", sign=" + sign + ", total_fee=" + total_fee
               + ", transaction_id=" + transaction_id + ", fee_type=" + fee_type + ", trade_type="
               + trade_type + "]";
    }

}