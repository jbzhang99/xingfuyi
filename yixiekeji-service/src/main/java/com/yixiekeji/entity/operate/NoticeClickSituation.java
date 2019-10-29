package com.yixiekeji.entity.operate;

import java.io.Serializable;

/**
 * 商家公告查看情况
 * <p>Table: <strong>notice_click_situation</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>sellerId</td><td>{@link java.lang.Integer}</td><td>seller_id</td><td>int</td><td>商家id</td></tr>
 *   <tr><td>noticeId</td><td>{@link java.lang.Integer}</td><td>notice_id</td><td>int</td><td>公告id</td></tr>
 *   <tr><td>viewTime</td><td>{@link java.util.Date}</td><td>view_time</td><td>timestamp</td><td>查看时间</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>状态 0-未读 1-已读（可标记为未读）</td></tr>
 * </table>
 *
 */
public class NoticeClickSituation implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -1186397667106338810L;
    private java.lang.Integer id;                                      //id
    private java.lang.Integer sellerId;                                //商家id
    private java.lang.Integer noticeId;                                //公告id
    private java.util.Date    viewTime;                                //查看时间
    private java.lang.Integer state;                                   //状态 0-未读 1-已读（可标记为未读）
    public static final int   STATE_READ       = 1;
    public static final int   STATE_UNREAD     = 0;

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
     * 获取商家id
     */
    public java.lang.Integer getSellerId() {
        return this.sellerId;
    }

    /**
     * 设置商家id
     */
    public void setSellerId(java.lang.Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * 获取公告id
     */
    public java.lang.Integer getNoticeId() {
        return this.noticeId;
    }

    /**
     * 设置公告id
     */
    public void setNoticeId(java.lang.Integer noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 获取查看时间
     */
    public java.util.Date getViewTime() {
        return this.viewTime;
    }

    /**
     * 设置查看时间
     */
    public void setViewTime(java.util.Date viewTime) {
        this.viewTime = viewTime;
    }

    /**
     * 获取状态 0-未读 1-已读（可标记为未读）
     */
    public java.lang.Integer getState() {
        return this.state;
    }

    /**
     * 设置状态 0-未读 1-已读（可标记为未读）
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }
}