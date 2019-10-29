package com.yixiekeji.entity.product;

import java.io.Serializable;

/**
 * 商品类型属性表
 * <p>Table: <strong>product_type_attr</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>属性名称</td></tr>
 *   <tr><td>value</td><td>{@link java.lang.String}</td><td>value</td><td>text</td><td>属性值，用逗号隔开</td></tr>
 *   <tr><td>productTypeId</td><td>{@link java.lang.Integer}</td><td>product_type_id</td><td>int</td><td>属性分类id</td></tr>
 *   <tr><td>type</td><td>{@link java.lang.Integer}</td><td>type</td><td>tinyint</td><td>1、检索属性；2、自定义属性</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>创建人ID</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>sort</td><td>{@link java.lang.Integer}</td><td>sort</td><td>int</td><td>排序0到255，越大越靠前展示</td></tr>
 * </table>
 *
 */
public class ProductTypeAttr implements Serializable {

    /**
    *Comment for <code>serialVersionUID</code>
    */
    private static final long serialVersionUID = -264584636750741280L;

    private Integer           id;

    private String            name;                                   //属性名称

    private String            value;                                  //属性值，用逗号隔开

    private Integer           productTypeId;                          //所属分类

    private Integer           type;                                   //1、检索属性；2、自定义属性

    private Integer           createId;                               //创建人ID

    private java.util.Date    createTime;                             //创建时间

    private Integer           sort;

    /**
    * 获取id
    */
    public Integer getId() {
        return this.id;
    }

    /**
    * 设置id
    */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
    * 获取属性名称
    */
    public String getName() {
        return this.name;
    }

    /**
    * 设置属性名称
    */
    public void setName(String name) {
        this.name = name;
    }

    /**
    * 获取属性值，用逗号隔开
    */
    public String getValue() {
        return this.value;
    }

    /**
    * 设置属性值，用逗号隔开
    */
    public void setValue(String value) {
        this.value = value;
    }

    /**
    * 获取所属分类
    */
    public Integer getProductTypeId() {
        return this.productTypeId;
    }

    /**
    * 设置所属分类
    */
    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    /**
    * 获取1、检索属性；2、自定义属性
    */
    public Integer getType() {
        return this.type;
    }

    /**
    * 设置1、检索属性；2、自定义属性
    */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
    * 获取创建人ID
    */
    public Integer getCreateId() {
        return this.createId;
    }

    /**
    * 设置创建人ID
    */
    public void setCreateId(Integer createId) {
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}