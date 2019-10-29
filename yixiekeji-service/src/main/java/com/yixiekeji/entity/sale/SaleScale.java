package com.yixiekeji.entity.sale;

import java.io.Serializable;

/**
 * 商家总体分佣表
 * <p>Table: <strong>sale_scale</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>sellerId</td><td>{@link java.lang.Integer}</td><td>seller_id</td><td>int</td><td>商家ID</td></tr>
 *   <tr><td>saleScale1</td><td>{@link java.math.BigDecimal}</td><td>sale_scale1</td><td>decimal</td><td>一级分销比例</td></tr>
 *   <tr><td>saleScale2</td><td>{@link java.math.BigDecimal}</td><td>sale_scale2</td><td>decimal</td><td>二级分销比例</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>商家分销状态是否启用：0，未启用；1、启用</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>createId</td></tr>
 *   <tr><td>createName</td><td>{@link java.lang.String}</td><td>create_name</td><td>varchar</td><td>createName</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>updateId</td><td>{@link java.lang.Integer}</td><td>update_id</td><td>int</td><td>updateId</td></tr>
 *   <tr><td>updateName</td><td>{@link java.lang.String}</td><td>update_name</td><td>varchar</td><td>updateName</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>timestamp</td><td>updateTime</td></tr>
 * </table>
 *
 */
public class SaleScale implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long    serialVersionUID = 3101927717861604491L;
    private java.lang.Integer    id;
    private java.lang.Integer    sellerId;
    private java.math.BigDecimal saleScale1;
    private java.math.BigDecimal saleScale2;
    private java.lang.Integer    state;
    private java.lang.Integer    createId;
    private java.lang.String     createName;
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
     * 获取一级分销比例
     */
    public java.math.BigDecimal getSaleScale1() {
        return this.saleScale1;
    }

    /**
     * 设置一级分销比例
     */
    public void setSaleScale1(java.math.BigDecimal saleScale1) {
        this.saleScale1 = saleScale1;
    }

    /**
     * 获取二级分销比例
     */
    public java.math.BigDecimal getSaleScale2() {
        return this.saleScale2;
    }

    /**
     * 设置二级分销比例
     */
    public void setSaleScale2(java.math.BigDecimal saleScale2) {
        this.saleScale2 = saleScale2;
    }

    /**
     * 获取商家分销状态是否启用：0，未启用；1、启用
     */
    public java.lang.Integer getState() {
        return this.state;
    }

    /**
     * 设置商家分销状态是否启用：0，未启用；1、启用
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    /**
     * 获取createId
     */
    public java.lang.Integer getCreateId() {
        return this.createId;
    }

    /**
     * 设置createId
     */
    public void setCreateId(java.lang.Integer createId) {
        this.createId = createId;
    }

    /**
     * 获取createName
     */
    public java.lang.String getCreateName() {
        return this.createName;
    }

    /**
     * 设置createName
     */
    public void setCreateName(java.lang.String createName) {
        this.createName = createName;
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
     * 获取updateId
     */
    public java.lang.Integer getUpdateId() {
        return this.updateId;
    }

    /**
     * 设置updateId
     */
    public void setUpdateId(java.lang.Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取updateName
     */
    public java.lang.String getUpdateName() {
        return this.updateName;
    }

    /**
     * 设置updateName
     */
    public void setUpdateName(java.lang.String updateName) {
        this.updateName = updateName;
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
}