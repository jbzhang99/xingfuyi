package com.yixiekeji.entity.operate;

import java.io.Serializable;

/**
 * 商城公告
 * <p>Table: <strong>system_notice</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>title</td><td>{@link java.lang.String}</td><td>title</td><td>varchar</td><td>标题</td></tr>
 *   <tr><td>describe</td><td>{@link java.lang.String}</td><td>describe</td><td>varchar</td><td>描述</td></tr>
 *   <tr><td>content</td><td>{@link java.lang.String}</td><td>content</td><td>text</td><td>内容</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>createUserId</td><td>{@link java.lang.Integer}</td><td>create_user_id</td><td>int</td><td>创建人（管理员id）</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>timestamp</td><td>updateTime</td></tr>
 *   <tr><td>updateUserId</td><td>{@link java.lang.Integer}</td><td>update_user_id</td><td>int</td><td>更新人（管理员id）</td></tr>
 *   <tr><td>isTop</td><td>{@link java.lang.Integer}</td><td>is_top</td><td>tinyint</td><td>是否置顶 0-否 1-是</td></tr>
 *   <tr><td>sort</td><td>{@link java.lang.Integer}</td><td>sort</td><td>int</td><td>排序号 越小越靠前</td></tr>
 * </table>
 *
 */
public class SystemNotice implements Serializable {

    public static final int   IS_TOP_YES       = 1;
    public static final int   IS_TOP_NO        = 0;
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1560660013325124510L;
    private java.lang.Integer id;
    private java.lang.String  title;
    private java.lang.String  describe;
    private java.lang.String  content;
    private java.util.Date    createTime;
    private java.lang.Integer createUserId;
    private java.util.Date    updateTime;
    private java.lang.Integer updateUserId;
    private java.lang.Integer isTop;
    private java.lang.Integer sort;
    private String            createUserName;

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
     * 获取标题
     */
    public java.lang.String getTitle() {
        return this.title;
    }

    /**
     * 设置标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    /**
     * 获取描述
     */
    public java.lang.String getDescribe() {
        return this.describe;
    }

    /**
     * 设置描述
     */
    public void setDescribe(java.lang.String describe) {
        this.describe = describe;
    }

    /**
     * 获取内容
     */
    public java.lang.String getContent() {
        this.content = this.content.replaceAll("\n", "<br/>").replaceAll("\r", "&nbsp;&nbsp;")
            .replaceAll("\"", "\'");
        return this.content;
    }

    /**
     * 设置内容
     */
    public void setContent(java.lang.String content) {
        this.content = content;
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
     * 获取创建人（管理员id）
     */
    public java.lang.Integer getCreateUserId() {
        return this.createUserId;
    }

    /**
     * 设置创建人（管理员id）
     */
    public void setCreateUserId(java.lang.Integer createUserId) {
        this.createUserId = createUserId;
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

    /**
     * 获取更新人（管理员id）
     */
    public java.lang.Integer getUpdateUserId() {
        return this.updateUserId;
    }

    /**
     * 设置更新人（管理员id）
     */
    public void setUpdateUserId(java.lang.Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取是否置顶 0-否 1-是
     */
    public java.lang.Integer getIsTop() {
        return this.isTop;
    }

    /**
     * 设置是否置顶 0-否 1-是
     */
    public void setIsTop(java.lang.Integer isTop) {
        this.isTop = isTop;
    }

    /**
     * 获取排序号 越小越靠前
     */
    public java.lang.Integer getSort() {
        return this.sort;
    }

    /**
     * 设置排序号 越小越靠前
     */
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}