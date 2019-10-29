package com.yixiekeji.entity.member;

import java.io.Serializable;

/**
 * 退货日志表
 * <p>Table: <strong>member_product_back_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>operatingId</td><td>{@link java.lang.Integer}</td><td>operating_id</td><td>int</td><td>操作人</td></tr>
 *   <tr><td>operatingName</td><td>{@link java.lang.String}</td><td>operating_name</td><td>varchar</td><td>operatingName</td></tr>
 *   <tr><td>memberProductBackId</td><td>{@link java.lang.Integer}</td><td>member_product_back_id</td><td>int</td><td>memberProductBackId</td></tr>
 *   <tr><td>content</td><td>{@link java.lang.String}</td><td>content</td><td>varchar</td><td>内容</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 * </table>
 *
 */
public class MemberProductBackLog implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -438963978400776932L;
    private java.lang.Integer id;
    private java.lang.Integer operatingId;
    private java.lang.String  operatingName;
    private java.lang.Integer memberProductBackId;
    private java.lang.String  content;
    private java.util.Date    createTime;

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
     * 获取操作人
     */
    public java.lang.Integer getOperatingId() {
        return this.operatingId;
    }

    /**
     * 设置操作人
     */
    public void setOperatingId(java.lang.Integer operatingId) {
        this.operatingId = operatingId;
    }

    /**
     * 获取operatingName
     */
    public java.lang.String getOperatingName() {
        return this.operatingName;
    }

    /**
     * 设置operatingName
     */
    public void setOperatingName(java.lang.String operatingName) {
        this.operatingName = operatingName;
    }

    /**
     * 获取memberProductBackId
     */
    public java.lang.Integer getMemberProductBackId() {
        return this.memberProductBackId;
    }

    /**
     * 设置memberProductBackId
     */
    public void setMemberProductBackId(java.lang.Integer memberProductBackId) {
        this.memberProductBackId = memberProductBackId;
    }

    /**
     * 获取内容
     */
    public java.lang.String getContent() {
        return this.content;
    }

    /**
     * 设置内容
     */
    public void setContent(java.lang.String content) {
        this.content = content;
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
}