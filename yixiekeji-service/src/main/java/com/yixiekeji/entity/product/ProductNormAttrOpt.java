package com.yixiekeji.entity.product;

import java.io.Serializable;

/**
 * 商品选定的颜色规格
 * <p>Table: <strong>product_norm_attr_opt1</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>productNormId</td><td>{@link java.lang.Integer}</td><td>product_norm_id</td><td>int</td><td>规格ID</td></tr>
 *   <tr><td>productNormName</td><td>{@link java.lang.String}</td><td>product_norm_name</td><td>varchar</td><td>规格名称</td></tr>
 *   <tr><td>productId</td><td>{@link java.lang.Integer}</td><td>product_id</td><td>int</td><td>商品ID</td></tr>
 *   <tr><td>sellerId</td><td>{@link java.lang.Integer}</td><td>seller_id</td><td>int</td><td>商家ID</td></tr>
 *   <tr><td>typeAttr</td><td>{@link java.lang.Integer}</td><td>type_attr</td><td>tinyint</td><td>1、商城属性 2、自定义属性</td></tr>
 *   <tr><td>type</td><td>{@link java.lang.Integer}</td><td>type</td><td>tinyint</td><td>1、文字；2、图片</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>属性名称</td></tr>
 *   <tr><td>image</td><td>{@link java.lang.String}</td><td>image</td><td>varchar</td><td>图片</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>创建人</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>attrId</td><td>{@link java.lang.Integer}</td><td>attr_id</td><td>int</td><td>属性id</td></tr>
 * </table>
 *
 */
public class ProductNormAttrOpt implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5029221600474155417L;
    private java.lang.Integer id;                                     //id
    private java.lang.Integer productNormId;                          //规格ID
    private java.lang.String  productNormName;                        //规格名称
    private java.lang.Integer productId;                              //商品ID
    private java.lang.Integer sellerId;                               //商家ID
    private java.lang.Integer typeAttr;                               //1、商城属性 2、自定义属性
    private java.lang.Integer type;                                   //1、文字；2、图片
    private java.lang.String  name;                                   //属性名称
    private java.lang.String  image;                                  //图片
    private java.lang.Integer createId;                               //创建人
    private java.util.Date    createTime;                             //创建时间
    private java.lang.Integer attrId;                                 //属性id

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
     * 获取规格ID
     */
    public java.lang.Integer getProductNormId() {
        return this.productNormId;
    }

    /**
     * 设置规格ID
     */
    public void setProductNormId(java.lang.Integer productNormId) {
        this.productNormId = productNormId;
    }

    /**
     * 获取规格名称
     */
    public java.lang.String getProductNormName() {
        return this.productNormName;
    }

    /**
     * 设置规格名称
     */
    public void setProductNormName(java.lang.String productNormName) {
        this.productNormName = productNormName;
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
     * 获取商家ID
     */
    public java.lang.Integer getSellerId() {
        return this.sellerId;
    }

    /**
     * 设置商家ID
     */
    public void setSellerId(java.lang.Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取1、商城属性 2、自定义属性
     */
    public java.lang.Integer getTypeAttr() {
        return this.typeAttr;
    }

    /**
     * 设置1、商城属性 2、自定义属性
     */
    public void setTypeAttr(java.lang.Integer typeAttr) {
        this.typeAttr = typeAttr;
    }

    /**
     * 获取1、文字；2、图片
     */
    public java.lang.Integer getType() {
        return this.type;
    }

    /**
     * 设置1、文字；2、图片
     */
    public void setType(java.lang.Integer type) {
        this.type = type;
    }

    /**
     * 获取属性名称
     */
    public java.lang.String getName() {
        return this.name;
    }

    /**
     * 设置属性名称
     */
    public void setName(java.lang.String name) {
        this.name = name;
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
     * 获取创建人
     */
    public java.lang.Integer getCreateId() {
        return this.createId;
    }

    /**
     * 设置创建人
     */
    public void setCreateId(java.lang.Integer createId) {
        this.createId = createId;
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
     * 获取属性id
     */
    public java.lang.Integer getAttrId() {
        return this.attrId;
    }

    /**
     * 设置属性id
     */
    public void setAttrId(java.lang.Integer attrId) {
        this.attrId = attrId;
    }
}