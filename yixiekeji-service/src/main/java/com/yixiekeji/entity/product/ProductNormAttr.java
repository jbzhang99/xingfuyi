package com.yixiekeji.entity.product;

import java.io.Serializable;
import java.util.Date;

/**
 * 规格属性表
 * <p>Table: <strong>product_norm_attr</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>name</td><td>{@link java.lang.String}</td><td>name</td><td>varchar</td><td>属性名称</td></tr>
 *   <tr><td>productNormId</td><td>{@link java.lang.Integer}</td><td>product_norm_id</td><td>int</td><td>规格ID</td></tr>
 *   <tr><td>sort</td><td>{@link java.lang.Integer}</td><td>sort</td><td>int</td><td>排序</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>创建人</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>image</td><td>{@link java.lang.String}</td><td>image</td><td>varchar</td><td>默认图片</td></tr>
 * </table>
 *
 */
public class ProductNormAttr implements Serializable {
    private static final long serialVersionUID = -4733220964574900474L;

    private Integer           id;                                      //id
    private String            name;                                    //属性名称
    private Integer           productNormId;                           //规格ID
    private Integer           sort;                                    //排序
    private Integer           createId;                                //创建人
    private Date              createTime;                              //创建时间
    private String            image;                                   //默认图片
    private String            productNormName;                         //规格名称

    //颜色规格图片
    private String            url;
    //1、商城属性 2、自定义属性
    private Integer           typeAttr;

    private Boolean           selected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductNormId() {
        return productNormId;
    }

    public void setProductNormId(Integer productNormId) {
        this.productNormId = productNormId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductNormName() {
        return productNormName;
    }

    public void setProductNormName(String productNormName) {
        this.productNormName = productNormName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTypeAttr() {
        return typeAttr;
    }

    public void setTypeAttr(Integer typeAttr) {
        this.typeAttr = typeAttr;
    }

}
