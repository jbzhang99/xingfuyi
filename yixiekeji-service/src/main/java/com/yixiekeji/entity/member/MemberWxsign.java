package com.yixiekeji.entity.member;

import java.io.Serializable;

/**
 * 微信联合登录
 * <p>Table: <strong>member_wxsign</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>openid</td><td>{@link java.lang.String}</td><td>openid</td><td>varchar</td><td>微信用户标识</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>用户id</td></tr>
 * </table>
 *
 */
public class MemberWxsign implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6018972346955411917L;
    private java.lang.Integer id;                                      //id
    private java.lang.String  openid;                                  //微信用户标识
    private java.lang.Integer memberId;                                //用户id

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
     * 获取微信用户标识
     */
    public java.lang.String getOpenid() {
        return this.openid;
    }

    /**
     * 设置微信用户标识
     */
    public void setOpenid(java.lang.String openid) {
        this.openid = openid;
    }

    /**
     * 获取用户id
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户id
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }
}