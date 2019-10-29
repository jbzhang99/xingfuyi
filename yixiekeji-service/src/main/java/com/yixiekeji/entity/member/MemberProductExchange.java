package com.yixiekeji.entity.member;

import java.io.Serializable;

import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;

/**
 * 用户换货
 * <p>Table: <strong>member_product_exchange</strong>
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
 *   <tr><td>changeName</td><td>{@link java.lang.String}</td><td>change_name</td><td>char</td><td>用户收货人</td></tr>
 *   <tr><td>phone</td><td>{@link java.lang.String}</td><td>phone</td><td>varchar</td><td>用户收货人电话</td></tr>
 *   <tr><td>logisticsId</td><td>{@link java.lang.Integer}</td><td>logistics_id</td><td>int</td><td>换件物流公司ID</td></tr>
 *   <tr><td>logisticsName</td><td>{@link java.lang.String}</td><td>logistics_name</td><td>varchar</td><td>换件物流公司</td></tr>
 *   <tr><td>logisticsMark</td><td>{@link java.lang.String}</td><td>logistics_mark</td><td>varchar</td><td>换件物流公司快递代码</td></tr>
 *   <tr><td>logisticsNumber</td><td>{@link java.lang.String}</td><td>logistics_number</td><td>varchar</td><td>换件快递单号</td></tr>
 *   <tr><td>provinceId2</td><td>{@link java.lang.Integer}</td><td>province_id2</td><td>int</td><td>Province</td></tr>
 *   <tr><td>cityId2</td><td>{@link java.lang.Integer}</td><td>city_id2</td><td>int</td><td>city</td></tr>
 *   <tr><td>areaId2</td><td>{@link java.lang.Integer}</td><td>area_id2</td><td>int</td><td>area</td></tr>
 *   <tr><td>addressAll2</td><td>{@link java.lang.String}</td><td>address_all2</td><td>varchar</td><td>省市区组合</td></tr>
 *   <tr><td>addressInfo2</td><td>{@link java.lang.String}</td><td>address_info2</td><td>varchar</td><td>详细地址</td></tr>
 *   <tr><td>zipCode2</td><td>{@link java.lang.String}</td><td>zip_code2</td><td>varchar</td><td>邮编</td></tr>
 *   <tr><td>changeName2</td><td>{@link java.lang.String}</td><td>change_name2</td><td>varchar</td><td>商家收货人</td></tr>
 *   <tr><td>phone2</td><td>{@link java.lang.String}</td><td>phone2</td><td>varchar</td><td>商家收货人电话</td></tr>
 *   <tr><td>logisticsId2</td><td>{@link java.lang.Integer}</td><td>logistics_id2</td><td>int</td><td>退件物流公司ID</td></tr>
 *   <tr><td>logisticsName2</td><td>{@link java.lang.String}</td><td>logistics_name2</td><td>varchar</td><td>退件物流公司</td></tr>
 *   <tr><td>logisticsMark2</td><td>{@link java.lang.String}</td><td>logistics_mark2</td><td>varchar</td><td>退件物流公司快递代码</td></tr>
 *   <tr><td>logisticsNumber2</td><td>{@link java.lang.String}</td><td>logistics_number2</td><td>varchar</td><td>退件快递单号</td></tr>
 *   <tr><td>number</td><td>{@link java.lang.Integer}</td><td>number</td><td>int</td><td>换货数量</td></tr>
 *   <tr><td>question</td><td>{@link java.lang.String}</td><td>question</td><td>varchar</td><td>问题描述</td></tr>
 *   <tr><td>image</td><td>{@link java.lang.String}</td><td>image</td><td>varchar</td><td>图片</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>联系人姓名</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理</td></tr>
 *   <tr><td>optId</td><td>{@link java.lang.Integer}</td><td>opt_id</td><td>int</td><td>处理人</td></tr>
 *   <tr><td>optName</td><td>{@link java.lang.String}</td><td>opt_name</td><td>varchar</td><td>处理人名称</td></tr>
 *   <tr><td>remark</td><td>{@link java.lang.String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>datetime</td><td>updateTime</td></tr>
 * </table>
 *
 */
public class MemberProductExchange implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1434128168892641179L;

    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理 */
    public final static int   STATE_1          = 1;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_2          = 2;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_3          = 3;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_4          = 4;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_5          = 5;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_6          = 6;
    /** 用户换货表  state   换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理；*/
    public final static int   STATE_7          = 7;

    private java.lang.Integer id;
    private java.lang.Integer sellerId;
    private java.lang.String  sellerName;
    private java.lang.Integer orderId;
    private java.lang.String  orderSn;
    private java.lang.Integer orderProductId;
    private java.lang.Integer productId;
    private java.lang.String  productName;
    private java.lang.Integer memberId;
    private java.lang.String  memberName;
    private java.lang.Integer provinceId;
    private java.lang.Integer cityId;
    private java.lang.Integer areaId;
    private java.lang.String  addressAll;
    private java.lang.String  addressInfo;
    private java.lang.String  zipCode;
    private java.lang.String  changeName;
    private java.lang.String  phone;
    private java.lang.Integer logisticsId;
    private java.lang.String  logisticsName;
    private java.lang.String  logisticsMark;
    private java.lang.String  logisticsNumber;
    private java.lang.Integer provinceId2;
    private java.lang.Integer cityId2;
    private java.lang.Integer areaId2;
    private java.lang.String  addressAll2;
    private java.lang.String  addressInfo2;
    private java.lang.String  zipCode2;
    private java.lang.String  changeName2;
    private java.lang.String  phone2;
    private java.lang.Integer logisticsId2;
    private java.lang.String  logisticsName2;
    private java.lang.String  logisticsMark2;
    private java.lang.String  logisticsNumber2;
    private java.lang.Integer number;
    private java.lang.String  question;
    private java.lang.String  image;
    private java.lang.String  name;
    private java.lang.Integer state;
    private java.lang.Integer optId;
    private java.lang.String  optName;
    private java.lang.String  remark;
    private java.util.Date    createTime;
    private java.util.Date    updateTime;

    // --------额外属性（entity对应表结构之外的属性） start------------------------------
    private Product           product;                                // 商品
    private OrdersProduct     ordersProduct;                          // 网单
    // --------额外属性（entity对应表结构之外的属性） end--------------------------------

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
     * 获取商家收货人
     */
    public java.lang.String getChangeName() {
        return this.changeName;
    }

    /**
     * 设置商家收货人
     */
    public void setChangeName(java.lang.String changeName) {
        this.changeName = changeName;
    }

    /**
     * 获取商家收货人电话
     */
    public java.lang.String getPhone() {
        return this.phone;
    }

    /**
     * 设置商家收货人电话
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    /**
     * 获取换件物流公司ID
     */
    public java.lang.Integer getLogisticsId() {
        return this.logisticsId;
    }

    /**
     * 设置换件物流公司ID
     */
    public void setLogisticsId(java.lang.Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    /**
     * 获取换件物流公司
     */
    public java.lang.String getLogisticsName() {
        return this.logisticsName;
    }

    /**
     * 设置换件物流公司
     */
    public void setLogisticsName(java.lang.String logisticsName) {
        this.logisticsName = logisticsName;
    }

    /**
     * 获取换件物流公司快递代码
     */
    public java.lang.String getLogisticsMark() {
        return this.logisticsMark;
    }

    /**
     * 设置换件物流公司快递代码
     */
    public void setLogisticsMark(java.lang.String logisticsMark) {
        this.logisticsMark = logisticsMark;
    }

    /**
     * 获取换件快递单号
     */
    public java.lang.String getLogisticsNumber() {
        return this.logisticsNumber;
    }

    /**
     * 设置换件快递单号
     */
    public void setLogisticsNumber(java.lang.String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    /**
     * 获取Province
     */
    public java.lang.Integer getProvinceId2() {
        return this.provinceId2;
    }

    /**
     * 设置Province
     */
    public void setProvinceId2(java.lang.Integer provinceId2) {
        this.provinceId2 = provinceId2;
    }

    /**
     * 获取city
     */
    public java.lang.Integer getCityId2() {
        return this.cityId2;
    }

    /**
     * 设置city
     */
    public void setCityId2(java.lang.Integer cityId2) {
        this.cityId2 = cityId2;
    }

    /**
     * 获取area
     */
    public java.lang.Integer getAreaId2() {
        return this.areaId2;
    }

    /**
     * 设置area
     */
    public void setAreaId2(java.lang.Integer areaId2) {
        this.areaId2 = areaId2;
    }

    /**
     * 获取省市区组合
     */
    public java.lang.String getAddressAll2() {
        return this.addressAll2;
    }

    /**
     * 设置省市区组合
     */
    public void setAddressAll2(java.lang.String addressAll2) {
        this.addressAll2 = addressAll2;
    }

    /**
     * 获取详细地址
     */
    public java.lang.String getAddressInfo2() {
        return this.addressInfo2;
    }

    /**
     * 设置详细地址
     */
    public void setAddressInfo2(java.lang.String addressInfo2) {
        this.addressInfo2 = addressInfo2;
    }

    /**
     * 获取邮编
     */
    public java.lang.String getZipCode2() {
        return this.zipCode2;
    }

    /**
     * 设置邮编
     */
    public void setZipCode2(java.lang.String zipCode2) {
        this.zipCode2 = zipCode2;
    }

    /**
     * 获取用户收货人
     */
    public java.lang.String getChangeName2() {
        return this.changeName2;
    }

    /**
     * 设置用户收货人
     */
    public void setChangeName2(java.lang.String changeName2) {
        this.changeName2 = changeName2;
    }

    /**
     * 获取用户收货人电话
     */
    public java.lang.String getPhone2() {
        return this.phone2;
    }

    /**
     * 设置用户收货人电话
     */
    public void setPhone2(java.lang.String phone2) {
        this.phone2 = phone2;
    }

    /**
     * 获取退件物流公司ID
     */
    public java.lang.Integer getLogisticsId2() {
        return this.logisticsId2;
    }

    /**
     * 设置退件物流公司ID
     */
    public void setLogisticsId2(java.lang.Integer logisticsId2) {
        this.logisticsId2 = logisticsId2;
    }

    /**
     * 获取退件物流公司
     */
    public java.lang.String getLogisticsName2() {
        return this.logisticsName2;
    }

    /**
     * 设置退件物流公司
     */
    public void setLogisticsName2(java.lang.String logisticsName2) {
        this.logisticsName2 = logisticsName2;
    }

    /**
     * 获取退件物流公司快递代码
     */
    public java.lang.String getLogisticsMark2() {
        return this.logisticsMark2;
    }

    /**
     * 设置退件物流公司快递代码
     */
    public void setLogisticsMark2(java.lang.String logisticsMark2) {
        this.logisticsMark2 = logisticsMark2;
    }

    /**
     * 获取退件快递单号
     */
    public java.lang.String getLogisticsNumber2() {
        return this.logisticsNumber2;
    }

    /**
     * 设置退件快递单号
     */
    public void setLogisticsNumber2(java.lang.String logisticsNumber2) {
        this.logisticsNumber2 = logisticsNumber2;
    }

    /**
     * 获取换货数量
     */
    public java.lang.Integer getNumber() {
        return this.number;
    }

    /**
     * 设置换货数量
     */
    public void setNumber(java.lang.Integer number) {
        this.number = number;
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
     * 获取联系人姓名
     */
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 设置联系人姓名
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }

    /**
     * 获取换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理
     */
    public java.lang.Integer getState() {
        return this.state;
    }

    /**
     * 设置换货状态：1、未处理；2、审核通过；3、用户发回退件；4、商家收到退件；5、商家发出换件；6、原件退还；7、不处理
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
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

}