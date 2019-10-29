package com.yixiekeji.entity.sale;

import java.io.Serializable;

/**
 * 分销设置表
 * <p>Table: <strong>sale_setting</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>saleSet</td><td>{@link java.lang.Integer}</td><td>sale_set</td><td>tinyint</td><td>1、平台承担；2、商家承担</td></tr>
 *   <tr><td>saleOrderTime</td><td>{@link java.lang.Date}</td><td>sale_order_time</td><td>datetime</td><td>定时器生成分销记录的时间</td></tr>
 *   <tr><td>createId</td><td>{@link java.lang.Integer}</td><td>create_id</td><td>int</td><td>createId</td></tr>
 *   <tr><td>createName</td><td>{@link java.lang.String}</td><td>create_name</td><td>varchar</td><td>createName</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 * </table>
 *
 */
public class SaleSetting implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5181922335270228675L;
    private java.lang.Integer id;
    private java.lang.Integer saleSet;
    private java.util.Date    saleOrderTime;
    private java.lang.Integer createId;
    private java.lang.String  createName;
    private java.util.Date    createTime;

    /**
     * 平台承担分销金额
     */
    public final static int   SALESET_ADMIN    = 1;

    /**
     * 商家承担分销金额
     */
    public final static int   SALESET_SELLER   = 2;

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
     * 获取1、平台承担；2、商家承担
     */
    public java.lang.Integer getSaleSet() {
        return this.saleSet;
    }

    /**
     * 设置1、平台承担；2、商家承担
     */
    public void setSaleSet(java.lang.Integer saleSet) {
        this.saleSet = saleSet;
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

    public java.util.Date getSaleOrderTime() {
        return saleOrderTime;
    }

    public void setSaleOrderTime(java.util.Date saleOrderTime) {
        this.saleOrderTime = saleOrderTime;
    }

}