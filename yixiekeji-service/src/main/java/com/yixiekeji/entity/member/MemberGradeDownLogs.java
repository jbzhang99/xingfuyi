package com.yixiekeji.entity.member;

import java.io.Serializable;

/**
 * 会员经验值年度递减日志表（每天执行完定时任务后记录）
 * <p>Table: <strong>member_grade_down_logs</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>excuteTime</td><td>{@link java.lang.String}</td><td>excute_time</td><td>String</td><td>递减日期，yyyy-MM-dd（记录某年减少的某月某日注册的会员经验值）</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 * </table>
 *
 */
public class MemberGradeDownLogs implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2238638058725277448L;

    private java.lang.Integer id;
    private java.lang.String  excuteTime;
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
     * 获取递减日期，yyyy-MM-dd（记录某年减少的某月某日注册的会员经验值）
     */
    public java.lang.String getExcuteTime() {
        return this.excuteTime;
    }

    /**
     * 设置递减日期，yyyy-MM-dd（记录某年减少的某月某日注册的会员经验值）
     */
    public void setExcuteTime(java.lang.String excuteTime) {
        this.excuteTime = excuteTime;
    }

    /**
     * 获取创建日期
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建日期
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }
}