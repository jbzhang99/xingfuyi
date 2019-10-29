package com.yixiekeji.entity.member;

import java.io.Serializable;

import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;

/**
 * 用户退货
 * <p>Table: <strong>member_product_back</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>sellerId</td><td>{@link java.lang.Integer}</td><td>seller_id</td><td>int</td><td>所属商家</td></tr>
 *   <tr><td>sellerName</td><td>{@link java.lang.String}</td><td>seller_name</td><td>varchar</td><td>商家名称</td></tr>
 *   <tr><td>orderId</td><td>{@link java.lang.Integer}</td><td>order_id</td><td>int</td><td>订单ID</td></tr>
 *   <tr><td>orderSn</td><td>{@link java.lang.String}</td><td>order_sn</td><td>varchar</td><td>订单号</td></tr>
 *   <tr><td>orderProductId</td><td>{@link java.lang.Integer}</td><td>order_product_id</td><td>int</td><td>网单ID</td></tr>
 *   <tr><td>productId</td><td>{@link java.lang.Integer}</td><td>product_id</td><td>int</td><td>商品ID</td></tr>
 *   <tr><td>productName</td><td>{@link java.lang.String}</td><td>product_name</td><td>varchar</td><td>商品名称</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>用户ID</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>用户Name</td></tr>
 *   <tr><td>provinceId</td><td>{@link java.lang.Integer}</td><td>province_id</td><td>int</td><td>Province</td></tr>
 *   <tr><td>cityId</td><td>{@link java.lang.Integer}</td><td>city_id</td><td>int</td><td>city</td></tr>
 *   <tr><td>areaId</td><td>{@link java.lang.Integer}</td><td>area_id</td><td>int</td><td>area</td></tr>
 *   <tr><td>addressAll</td><td>{@link java.lang.String}</td><td>address_all</td><td>varchar</td><td>省市区组合</td></tr>
 *   <tr><td>addressInfo</td><td>{@link java.lang.String}</td><td>address_info</td><td>varchar</td><td>详细地址</td></tr>
 *   <tr><td>zipCode</td><td>{@link java.lang.String}</td><td>zip_code</td><td>varchar</td><td>邮编</td></tr>
 *   <tr><td>contactPhone</td><td>{@link java.lang.String}</td><td>contact_phone</td><td>varchar</td><td>商家联系人手机</td></tr>
 *   <tr><td>contactName</td><td>{@link java.lang.String}</td><td>contact_name</td><td>varchar</td><td>商家联系人姓名</td></tr>
 *   <tr><td>logisticsId</td><td>{@link java.lang.Integer}</td><td>logistics_id</td><td>int</td><td>物流公司ID</td></tr>
 *   <tr><td>logisticsName</td><td>{@link java.lang.String}</td><td>logistics_name</td><td>varchar</td><td>物流公司</td></tr>
 *   <tr><td>logisticsMark</td><td>{@link java.lang.String}</td><td>logistics_mark</td><td>varchar</td><td>物流公司快递代码</td></tr>
 *   <tr><td>logisticsNumber</td><td>{@link java.lang.String}</td><td>logistics_number</td><td>varchar</td><td>快递单号</td></tr>
 *   <tr><td>question</td><td>{@link java.lang.String}</td><td>question</td><td>text</td><td>问题描述</td></tr>
 *   <tr><td>image</td><td>{@link java.lang.String}</td><td>image</td><td>varchar</td><td>图片</td></tr>
 *   <tr><td>stateReturn</td><td>{@link java.lang.Integer}</td><td>state_return</td><td>tinyint</td><td>退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理</td></tr>
 *   <tr><td>stateMoney</td><td>{@link java.lang.Integer}</td><td>state_money</td><td>tinyint</td><td>退款状态：1、未退款；2、退款到账户；3、退款到银行</td></tr>
 *   <tr><td>number</td><td>{@link java.lang.Integer}</td><td>number</td><td>int</td><td>退货数量</td></tr>
 *   <tr><td>backMoney</td><td>{@link java.math.BigDecimal}</td><td>back_money</td><td>decimal</td><td>退款金额</td></tr>
 *   <tr><td>backIntegral</td><td>{@link java.lang.Integer}</td><td>back_integral</td><td>int</td><td>退回积分</td></tr>
 *   <tr><td>backIntegralMoney</td><td>{@link java.math.BigDecimal}</td><td>back_integral_money</td><td>decimal</td><td>退回积分金额（退回积分转换成金额）</td></tr>
 *   <tr><td>backCouponUserId</td><td>{@link java.lang.Integer}</td><td>back_coupon_user_id</td><td>int</td><td>退回优惠券ID，0表示没有</td></tr>
 *   <tr><td>backMoneyTime</td><td>{@link java.util.Date}</td><td>back_money_time</td><td>datetime</td><td>退款时间</td></tr>
 *   <tr><td>optId</td><td>{@link java.lang.Integer}</td><td>opt_id</td><td>int</td><td>处理人</td></tr>
 *   <tr><td>optName</td><td>{@link java.lang.String}</td><td>opt_name</td><td>varchar</td><td>处理人名称</td></tr>
 *   <tr><td>remark</td><td>{@link java.lang.String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>datetime</td><td>updateTime</td></tr>
 * </table>
 *
 */
public class MemberProductBack implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = 2408716797440619962L;

    /** 用户退货表  state_return   退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理 */
    public final static int      STATE_RETURN_1   = 1;
    /** 用户退货表  state_return   退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理；*/
    public final static int      STATE_RETURN_2   = 2;
    /** 用户退货表  state_return   退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理；*/
    public final static int      STATE_RETURN_3   = 3;
    /** 用户退货表  state_return   退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理；*/
    public final static int      STATE_RETURN_4   = 4;
    /** 用户退货表  state_return   退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理；*/
    public final static int      STATE_RETURN_5   = 5;

    //    /** 用户退货表  state_return   退货状态：1、未处理；*/
    //    public final static int      STATE_RETURN_1   = 1;
    //    /** 用户退货表  state_return   退货状态：2、审核通过待收货；*/
    //    public final static int      STATE_RETURN_2   = 2;
    //    /** 用户退货表  state_return   退货状态：3、已经收货；*/
    //    public final static int      STATE_RETURN_3   = 3;
    //    /** 用户退货表  state_return   退货状态：4、不予处理；*/
    //    public final static int      STATE_RETURN_4   = 4;

    /** 用户退货表  state_money   退款状态：1、未退款；*/
    public final static int      STATE_MONEY_1    = 1;
    /** 用户退货表  state_money   退款状态：2、退款到账户；*/
    public final static int      STATE_MONEY_2    = 2;
    /** 用户退货表  state_money   退款状态：3、退款到银行*/
    public final static int      STATE_MONEY_3    = 3;

    private java.lang.Integer    id;
    private java.lang.Integer    sellerId;
    private java.lang.String     sellerName;
    private java.lang.Integer    orderId;
    private java.lang.String     orderSn;
    private java.lang.Integer    orderProductId;
    private java.lang.Integer    productId;
    private java.lang.String     productName;
    private java.lang.Integer    memberId;
    private java.lang.String     memberName;
    private java.lang.Integer    provinceId;
    private java.lang.Integer    cityId;
    private java.lang.Integer    areaId;
    private java.lang.String     addressAll;
    private java.lang.String     addressInfo;
    private java.lang.String     zipCode;
    private java.lang.String     contactPhone;
    private java.lang.String     contactName;
    private java.lang.Integer    logisticsId;
    private java.lang.String     logisticsName;
    private java.lang.String     logisticsMark;
    private java.lang.String     logisticsNumber;
    private java.lang.String     question;
    private java.lang.String     image;
    private java.lang.Integer    stateReturn;
    private java.lang.Integer    stateMoney;
    private java.lang.Integer    number;
    private java.math.BigDecimal backMoney;
    private java.lang.Integer    backIntegral;
    private java.math.BigDecimal backIntegralMoney;
    private java.lang.Integer    backCouponUserId;
    private java.util.Date       backMoneyTime;
    private java.lang.Integer    optId;
    private java.lang.String     optName;
    private java.lang.String     remark;
    private java.util.Date       createTime;
    private java.util.Date       updateTime;

    // --------额外属性（entity对应表结构之外的属性） start------------------------------

    private Product              product;                                // 商品
    private OrdersProduct        ordersProduct;                          // 网单
    private CouponUser           couponUser;                             // 退回优惠券
    // --------额外属性（entity对应表结构之外的属性） end------------------------------

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
     * 获取所属商家
     */
    public java.lang.Integer getSellerId() {
        return this.sellerId;
    }

    /**
     * 设置所属商家
     */
    public void setSellerId(java.lang.Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取商家名称
     */
    public java.lang.String getSellerName() {
        return this.sellerName;
    }

    /**
     * 设置商家名称
     */
    public void setSellerName(java.lang.String sellerName) {
        this.sellerName = sellerName;
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
    public java.lang.Integer getOrderProductId() {
        return this.orderProductId;
    }

    /**
     * 设置网单ID
     */
    public void setOrderProductId(java.lang.Integer orderProductId) {
        this.orderProductId = orderProductId;
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
     * 获取Province
     */
    public java.lang.Integer getProvinceId() {
        return this.provinceId;
    }

    /**
     * 设置Province
     */
    public void setProvinceId(java.lang.Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     * 获取city
     */
    public java.lang.Integer getCityId() {
        return this.cityId;
    }

    /**
     * 设置city
     */
    public void setCityId(java.lang.Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * 获取area
     */
    public java.lang.Integer getAreaId() {
        return this.areaId;
    }

    /**
     * 设置area
     */
    public void setAreaId(java.lang.Integer areaId) {
        this.areaId = areaId;
    }

    /**
     * 获取省市区组合
     */
    public java.lang.String getAddressAll() {
        return this.addressAll;
    }

    /**
     * 设置省市区组合
     */
    public void setAddressAll(java.lang.String addressAll) {
        this.addressAll = addressAll;
    }

    /**
     * 获取详细地址
     */
    public java.lang.String getAddressInfo() {
        return this.addressInfo;
    }

    /**
     * 设置详细地址
     */
    public void setAddressInfo(java.lang.String addressInfo) {
        this.addressInfo = addressInfo;
    }

    /**
     * 获取邮编
     */
    public java.lang.String getZipCode() {
        return this.zipCode;
    }

    /**
     * 设置邮编
     */
    public void setZipCode(java.lang.String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取商家联系人手机
     */
    public java.lang.String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 设置商家联系人手机
     */
    public void setContactPhone(java.lang.String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取商家联系人姓名
     */
    public java.lang.String getContactName() {
        return this.contactName;
    }

    /**
     * 设置商家联系人姓名
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取物流公司ID
     */
    public java.lang.Integer getLogisticsId() {
        return this.logisticsId;
    }

    /**
     * 设置物流公司ID
     */
    public void setLogisticsId(java.lang.Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    /**
     * 获取物流公司
     */
    public java.lang.String getLogisticsName() {
        return this.logisticsName;
    }

    /**
     * 设置物流公司
     */
    public void setLogisticsName(java.lang.String logisticsName) {
        this.logisticsName = logisticsName;
    }

    /**
     * 获取物流公司快递代码
     */
    public java.lang.String getLogisticsMark() {
        return this.logisticsMark;
    }

    /**
     * 设置物流公司快递代码
     */
    public void setLogisticsMark(java.lang.String logisticsMark) {
        this.logisticsMark = logisticsMark;
    }

    /**
     * 获取快递单号
     */
    public java.lang.String getLogisticsNumber() {
        return this.logisticsNumber;
    }

    /**
     * 设置快递单号
     */
    public void setLogisticsNumber(java.lang.String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    /**
     * 获取问题描述
     */
    public java.lang.String getQuestion() {
        return this.question;
    }

    /**
     * 设置问题描述
     */
    public void setQuestion(java.lang.String question) {
        this.question = question;
    }

    /**
     * 获取图片
     */
    public java.lang.String getImage() {
        return this.image;
    }

    /**
     * 设置图片
     */
    public void setImage(java.lang.String image) {
        this.image = image;
    }

    /**
     * 获取退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理
     */
    public java.lang.Integer getStateReturn() {
        return this.stateReturn;
    }

    /**
     * 设置退货状态：1、未处理；2、审核通过；3、用户发货，4、店铺收货；5、不予处理
     */
    public void setStateReturn(java.lang.Integer stateReturn) {
        this.stateReturn = stateReturn;
    }

    /**
     * 获取退款状态：1、未退款；2、退款到账户；3、退款到银行
     */
    public java.lang.Integer getStateMoney() {
        return this.stateMoney;
    }

    /**
     * 设置退款状态：1、未退款；2、退款到账户；3、退款到银行
     */
    public void setStateMoney(java.lang.Integer stateMoney) {
        this.stateMoney = stateMoney;
    }

    /**
     * 获取退货数量
     */
    public java.lang.Integer getNumber() {
        return this.number;
    }

    /**
     * 设置退货数量
     */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
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
     * 获取退回积分
     */
    public java.lang.Integer getBackIntegral() {
        return this.backIntegral;
    }

    /**
     * 设置退回积分
     */
    public void setBackIntegral(java.lang.Integer backIntegral) {
        this.backIntegral = backIntegral;
    }

    /**
     * 获取退回积分金额（退回积分转换成金额）
     */
    public java.math.BigDecimal getBackIntegralMoney() {
        return this.backIntegralMoney;
    }

    /**
     * 设置退回积分金额（退回积分转换成金额）
     */
    public void setBackIntegralMoney(java.math.BigDecimal backIntegralMoney) {
        this.backIntegralMoney = backIntegralMoney;
    }

    /**
     * 获取退回优惠券ID，0表示没有
     */
    public java.lang.Integer getBackCouponUserId() {
        return this.backCouponUserId;
    }

    /**
     * 设置退回优惠券ID，0表示没有
     */
    public void setBackCouponUserId(java.lang.Integer backCouponUserId) {
        this.backCouponUserId = backCouponUserId;
    }

    /**
     * 获取退款时间
     */
    public java.util.Date getBackMoneyTime() {
        return this.backMoneyTime;
    }

    /**
     * 设置退款时间
     */
    public void setBackMoneyTime(java.util.Date backMoneyTime) {
        this.backMoneyTime = backMoneyTime;
    }

    /**
     * 获取处理人
     */
    public java.lang.Integer getOptId() {
        return this.optId;
    }

    /**
     * 设置处理人
     */
    public void setOptId(java.lang.Integer optId) {
        this.optId = optId;
    }

    /**
     * 获取处理人名称
     */
    public java.lang.String getOptName() {
        return this.optName;
    }

    /**
     * 设置处理人名称
     */
    public void setOptName(java.lang.String optName) {
        this.optName = optName;
    }

    /**
     * 获取备注
     */
    public java.lang.String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     */
    public void setRemark(java.lang.String remark) {
        this.remark = remark;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrdersProduct getOrdersProduct() {
        return ordersProduct;
    }

    public void setOrdersProduct(OrdersProduct ordersProduct) {
        this.ordersProduct = ordersProduct;
    }

    public CouponUser getCouponUser() {
        return couponUser;
    }

    public void setCouponUser(CouponUser couponUser) {
        this.couponUser = couponUser;
    }

}