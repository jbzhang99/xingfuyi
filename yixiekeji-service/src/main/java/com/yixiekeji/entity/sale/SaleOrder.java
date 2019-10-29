package com.yixiekeji.entity.sale;

import java.io.Serializable;

/**
 * 收入流水表
 * <p>Table: <strong>sale_order</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>buyId</td><td>{@link java.lang.Integer}</td><td>buy_id</td><td>int</td><td>购买人ID</td></tr>
 *   <tr><td>buyName</td><td>{@link java.lang.String}</td><td>buy_name</td><td>varchar</td><td>购买人Name</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>拿分佣用户ID</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>拿分佣用户Name</td></tr>
 *   <tr><td>productId</td><td>{@link java.lang.Integer}</td><td>product_id</td><td>int</td><td>商品ID</td></tr>
 *   <tr><td>productName</td><td>{@link java.lang.String}</td><td>product_name</td><td>varchar</td><td>商品名称</td></tr>
 *   <tr><td>sellerId</td><td>{@link java.lang.Integer}</td><td>seller_id</td><td>int</td><td>商家ID</td></tr>
 *   <tr><td>sellerName</td><td>{@link java.lang.String}</td><td>seller_name</td><td>varchar</td><td>商家名称</td></tr>
 *   <tr><td>orderId</td><td>{@link java.lang.Integer}</td><td>order_id</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>orderSn</td><td>{@link java.lang.String}</td><td>order_sn</td><td>varchar</td><td>订单号</td></tr>
 *   <tr><td>ordersProductId</td><td>{@link java.lang.Integer}</td><td>orders_product_id</td><td>int</td><td>网单ID</td></tr>
 *   <tr><td>orderTime</td><td>{@link java.util.Date}</td><td>order_time</td><td>datetime</td><td>下单日期</td></tr>
 *   <tr><td>moneyAll</td><td>{@link java.math.BigDecimal}</td><td>money_all</td><td>decimal</td><td>总金额</td></tr>
 *   <tr><td>actMoney</td><td>{@link java.math.BigDecimal}</td><td>act_money</td><td>decimal</td><td>优惠金额，单品立减+订单其他优惠金额分摊之和</td></tr>
 *   <tr><td>money</td><td>{@link java.math.BigDecimal}</td><td>money</td><td>decimal</td><td>单价</td></tr>
 *   <tr><td>number</td><td>{@link java.lang.Integer}</td><td>number</td><td>int</td><td>购买数量</td></tr>
 *   <tr><td>saleMoney</td><td>{@link java.math.BigDecimal}</td><td>sale_money</td><td>decimal</td><td>佣金金额，（总金额-优惠金额-退款金额）*佣金比例</td></tr>
 *   <tr><td>saleScale</td><td>{@link java.math.BigDecimal}</td><td>sale_scale</td><td>decimal</td><td>佣金比例，生成记录时候记录的值</td></tr>
 *   <tr><td>saleState</td><td>{@link java.lang.Integer}</td><td>sale_state</td><td>tinyint</td><td>佣金状态：1、预计收益；2、可以提现；3、提现中；4、提现完成</td></tr>
 *   <tr><td>saleType</td><td>{@link java.lang.Integer}</td><td>sale_type</td><td>tinyint</td><td>奖励类型：1、订单消费</td></tr>
 *   <tr><td>saleGrade</td><td>{@link java.lang.Integer}</td><td>sale_grade</td><td>tinyint</td><td>分佣级数：1、第一级分销；2、第二级分销；</td></tr>
 *   <tr><td>backNumber</td><td>{@link java.lang.Integer}</td><td>back_number</td><td>int</td><td>退货数量</td></tr>
 *   <tr><td>backMoney</td><td>{@link java.math.BigDecimal}</td><td>back_money</td><td>decimal</td><td>退款金额</td></tr>
 *   <tr><td>backIds</td><td>{@link java.lang.String}</td><td>back_ids</td><td>varchar</td><td>退货ID，如果发生多次退货，用逗号分隔开</td></tr>
 *   <tr><td>saleApplyMoneyId</td><td>{@link java.lang.Integer}</td><td>sale_apply_money_id</td><td>int</td><td>提款的ID</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>timestamp</td><td>updateTime</td></tr>
 * </table>
 *
 */
public class SaleOrder implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = -2116103574811493634L;
    private java.lang.Integer    id;
    private java.lang.Integer    buyId;
    private java.lang.String     buyName;
    private java.lang.Integer    memberId;
    private java.lang.String     memberName;
    private java.lang.Integer    productId;
    private java.lang.String     productName;
    private java.lang.Integer    sellerId;
    private java.lang.String     sellerName;
    private java.lang.Integer    orderId;
    private java.lang.String     orderSn;
    private java.lang.Integer    ordersProductId;
    private java.util.Date       orderTime;
    private java.math.BigDecimal moneyAll;
    private java.math.BigDecimal actMoney;
    private java.math.BigDecimal money;
    private java.lang.Integer    number;
    private java.math.BigDecimal saleMoney;
    private java.math.BigDecimal saleScale;
    private java.lang.Integer    saleState;
    private java.lang.Integer    saleType;
    private java.lang.Integer    saleGrade;
    private java.lang.Integer    backNumber;
    private java.math.BigDecimal backMoney;
    private java.lang.String     backIds;
    private java.lang.Integer    saleApplyMoneyId;
    private java.util.Date       createTime;
    private java.util.Date       updateTime;

    /**
     * 佣金状态：1、预计收益；
     */
    public final static int      SALE_STATE_1     = 1;
    /**
     * 佣金状态：2、可以提现；
     */
    public final static int      SALE_STATE_2     = 2;
    /**
     * 佣金状态：3、提现中；
     */
    public final static int      SALE_STATE_3     = 3;
    /**
     * 佣金状态：4、提现完成
     */
    public final static int      SALE_STATE_4     = 4;

    /**
     * 奖励类型：1、订单消费
     */
    public final static int      SALE_TYPE        = 1;

    /**
     * 分佣级数：1、第一级分销；
     */
    public final static int      SALE_GRADE_1     = 1;
    /**
     * 分佣级数：2、第二级分销；
     */
    public final static int      SALE_GRADE_2     = 2;

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
     * 获取购买人ID
     */
    public java.lang.Integer getBuyId() {
        return this.buyId;
    }

    /**
     * 设置购买人ID
     */
    public void setBuyId(java.lang.Integer buyId) {
        this.buyId = buyId;
    }

    /**
     * 获取购买人Name
     */
    public java.lang.String getBuyName() {
        return this.buyName;
    }

    /**
     * 设置购买人Name
     */
    public void setBuyName(java.lang.String buyName) {
        this.buyName = buyName;
    }

    /**
     * 获取拿分佣用户ID
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置拿分佣用户ID
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取拿分佣用户Name
     */
    public java.lang.String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置拿分佣用户Name
     */
    public void setMemberName(java.lang.String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取商品ID
     */
    public java.lang.Integer getProductId() {
        return this.productId;
    }

    /**
     * 设置商品ID
     */
    public void setProductId(java.lang.Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取商品名称
     */
    public java.lang.String getProductName() {
        return this.productName;
    }

    /**
     * 设置商品名称
     */
    public void setProductName(java.lang.String productName) {
        this.productName = productName;
    }

    /**
     * 获取订单ID
     */
    public java.lang.Integer getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单ID
     */
    public void setOrderId(java.lang.Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单号
     */
    public java.lang.String getOrderSn() {
        return this.orderSn;
    }

    /**
     * 设置订单号
     */
    public void setOrderSn(java.lang.String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 获取网单ID
     */
    public java.lang.Integer getOrdersProductId() {
        return this.ordersProductId;
    }

    /**
     * 设置网单ID
     */
    public void setOrdersProductId(java.lang.Integer ordersProductId) {
        this.ordersProductId = ordersProductId;
    }

    /**
     * 获取下单日期
     */
    public java.util.Date getOrderTime() {
        return this.orderTime;
    }

    /**
     * 设置下单日期
     */
    public void setOrderTime(java.util.Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 获取总金额
     */
    public java.math.BigDecimal getMoneyAll() {
        return this.moneyAll;
    }

    /**
     * 设置总金额
     */
    public void setMoneyAll(java.math.BigDecimal moneyAll) {
        this.moneyAll = moneyAll;
    }

    /**
     * 获取优惠金额，单品立减+订单其他优惠金额分摊之和
     */
    public java.math.BigDecimal getActMoney() {
        return this.actMoney;
    }

    /**
     * 设置优惠金额，单品立减+订单其他优惠金额分摊之和
     */
    public void setActMoney(java.math.BigDecimal actMoney) {
        this.actMoney = actMoney;
    }

    /**
     * 获取单价
     */
    public java.math.BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置单价
     */
    public void setMoney(java.math.BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取购买数量
     */
    public java.lang.Integer getNumber() {
        return this.number;
    }

    /**
     * 设置购买数量
     */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
    }

    /**
     * 获取佣金金额，（总金额-优惠金额-退款金额）*佣金比例
     */
    public java.math.BigDecimal getSaleMoney() {
        return this.saleMoney;
    }

    /**
     * 设置佣金金额，（总金额-优惠金额-退款金额）*佣金比例
     */
    public void setSaleMoney(java.math.BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    /**
     * 获取佣金比例，生成记录时候记录的值
     */
    public java.math.BigDecimal getSaleScale() {
        return this.saleScale;
    }

    /**
     * 设置佣金比例，生成记录时候记录的值
     */
    public void setSaleScale(java.math.BigDecimal saleScale) {
        this.saleScale = saleScale;
    }

    /**
     * 获取佣金状态：1、预计收益；2、可以提现；3、提现中；4、提现完成
     */
    public java.lang.Integer getSaleState() {
        return this.saleState;
    }

    /**
     * 设置佣金状态：1、预计收益；2、可以提现；3、提现中；4、提现完成
     */
    public void setSaleState(java.lang.Integer saleState) {
        this.saleState = saleState;
    }

    /**
     * 获取奖励类型：1、订单消费
     */
    public java.lang.Integer getSaleType() {
        return this.saleType;
    }

    /**
     * 设置奖励类型：1、订单消费
     */
    public void setSaleType(java.lang.Integer saleType) {
        this.saleType = saleType;
    }

    /**
     * 获取分佣级数：1、第一级分销；2、第二级分销；
     */
    public java.lang.Integer getSaleGrade() {
        return this.saleGrade;
    }

    /**
     * 设置分佣级数：1、第一级分销；2、第二级分销；
     */
    public void setSaleGrade(java.lang.Integer saleGrade) {
        this.saleGrade = saleGrade;
    }

    /**
     * 获取退货数量
     */
    public java.lang.Integer getBackNumber() {
        return this.backNumber;
    }

    /**
     * 设置退货数量
     */
    public void setBackNumber(java.lang.Integer backNumber) {
        this.backNumber = backNumber;
    }

    /**
     * 获取退款金额
     */
    public java.math.BigDecimal getBackMoney() {
        return this.backMoney;
    }

    /**
     * 设置退款金额
     */
    public void setBackMoney(java.math.BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    /**
     * 获取退货ID，如果发生多次退货，用逗号分隔开
     */
    public java.lang.String getBackIds() {
        return this.backIds;
    }

    /**
     * 设置退货ID，如果发生多次退货，用逗号分隔开
     */
    public void setBackIds(java.lang.String backIds) {
        this.backIds = backIds;
    }

    /**
     * 获取提款的ID
     */
    public java.lang.Integer getSaleApplyMoneyId() {
        return this.saleApplyMoneyId;
    }

    /**
     * 设置提款的ID
     */
    public void setSaleApplyMoneyId(java.lang.Integer saleApplyMoneyId) {
        this.saleApplyMoneyId = saleApplyMoneyId;
    }

    /**
     * 获取createTime
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置createTime
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取updateTime
     */
    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置updateTime
     */
    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    public java.lang.Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(java.lang.Integer sellerId) {
        this.sellerId = sellerId;
    }

    public java.lang.String getSellerName() {
        return sellerName;
    }

    public void setSellerName(java.lang.String sellerName) {
        this.sellerName = sellerName;
    }

}