package com.yixiekeji.entity.sale;

import java.io.Serializable;

/**
 * 用户推广表
 * <p>Table: <strong>sale_member</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link java.lang.Integer}</td><td>id</td><td>int</td><td>id</td></tr>
 *   <tr><td>memberId</td><td>{@link java.lang.Integer}</td><td>member_id</td><td>int</td><td>用户ID</td></tr>
 *   <tr><td>memberName</td><td>{@link java.lang.String}</td><td>member_name</td><td>varchar</td><td>用户姓名</td></tr>
 *   <tr><td>certificateType</td><td>{@link java.lang.Integer}</td><td>certificate_type</td><td>tinyint</td><td>1、身份证</td></tr>
 *   <tr><td>certificateCode</td><td>{@link java.lang.String}</td><td>certificate_code</td><td>varchar</td><td>证件号码</td></tr>
 *   <tr><td>bankType</td><td>{@link java.lang.String}</td><td>bank_type</td><td>varchar</td><td>开户行</td></tr>
 *   <tr><td>bankCode</td><td>{@link java.lang.String}</td><td>bank_code</td><td>varchar</td><td>银行卡号</td></tr>
 *   <tr><td>bankName</td><td>{@link java.lang.String}</td><td>bank_name</td><td>varchar</td><td>开户人姓名</td></tr>
 *   <tr><td>bankAdd</td><td>{@link java.lang.String}</td><td>bank_add</td><td>varchar</td><td>开户行地址</td></tr>
 *   <tr><td>state</td><td>{@link java.lang.Integer}</td><td>state</td><td>tinyint</td><td>财务审核状态：1、刚注册；2、提交申请；3、审核通过；4、审核失败</td></tr>
 *   <tr><td>isSale</td><td>{@link java.lang.Integer}</td><td>is_sale</td><td>tinyint</td><td>是否为推广员：0、不是；1、是</td></tr>
 *   <tr><td>referrerId</td><td>{@link java.lang.Integer}</td><td>referrer_id</td><td>int</td><td>推荐人ID，为零没有推荐人</td></tr>
 *   <tr><td>referrerName</td><td>{@link java.lang.String}</td><td>referrer_name</td><td>varchar</td><td>推荐人姓名</td></tr>
 *   <tr><td>referrerCode</td><td>{@link java.lang.String}</td><td>referrer_code</td><td>varchar</td><td>推荐人的推荐码</td></tr>
 *   <tr><td>referrerPid</td><td>{@link java.lang.Integer}</td><td>referrer_pid</td><td>int</td><td>推荐人的推荐人的ID，第二级推荐人为0</td></tr>
 *   <tr><td>referrerPname</td><td>{@link java.lang.String}</td><td>referrer_pname</td><td>varchar</td><td>推荐人的推荐人的姓名</td></tr>
 *   <tr><td>referrerPath</td><td>{@link java.lang.Integer}</td><td>referrer_path</td><td>int</td><td>path路径，存储格式/1/2/，存储“/”为第一级分销商</td></tr>
 *   <tr><td>grade</td><td>{@link java.lang.Integer}</td><td>grade</td><td>tinyint</td><td>刚进入推荐人等级：1、第一级，自己申请；2、第二级数，由一级推广；3、第三级，由二级分销而来</td></tr>
 *   <tr><td>saleCode</td><td>{@link java.lang.String}</td><td>sale_code</td><td>varchar</td><td>我的推广码，用来生成推广的URL</td></tr>
 *   <tr><td>audit</td><td>{@link java.lang.String}</td><td>audit</td><td>varchar</td><td>审核意见</td></tr>
 *   <tr><td>auditId</td><td>{@link java.lang.Integer}</td><td>audit_id</td><td>int</td><td>审核人ID</td></tr>
 *   <tr><td>auditName</td><td>{@link java.lang.String}</td><td>audit_name</td><td>varchar</td><td>审核人姓名</td></tr>
 *   <tr><td>createTime</td><td>{@link java.util.Date}</td><td>create_time</td><td>datetime</td><td>createTime</td></tr>
 *   <tr><td>updateTime</td><td>{@link java.util.Date}</td><td>update_time</td><td>timestamp</td><td>updateTime</td></tr>
 * </table>
 *
 */
public class SaleMember implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID     = 2456924271488616175L;
    private java.lang.Integer  id;
    private java.lang.Integer  memberId;
    private java.lang.String   memberName;
    private java.lang.Integer  certificateType;
    private java.lang.String   certificateCode;
    private java.lang.String   bankType;
    private java.lang.String   bankCode;
    private java.lang.String   bankName;
    private java.lang.String   bankAdd;
    private java.lang.Integer  state;
    private java.lang.Integer  isSale;
    private java.lang.Integer  referrerId;
    private java.lang.String   referrerName;
    private java.lang.String   referrerCode;
    private java.lang.Integer  referrerPid;
    private java.lang.String   referrerPname;
    private java.lang.Integer  referrerPath;
    private java.lang.Integer  grade;
    private java.lang.String   saleCode;
    private java.lang.String   audit;
    private java.lang.Integer  auditId;
    private java.lang.String   auditName;
    private java.util.Date     createTime;
    private java.util.Date     updateTime;

    //新增字段
    private java.lang.Integer   authType;
    private java.lang.String    cardPath;
    private java.lang.String    disabilityGrade;
    private java.lang.String    disabilityCard;
    private java.lang.String    detailDress;
    private java.lang.Integer   provinceId;
    private java.lang.Integer   cityId;
    private java.lang.Integer   areaId;
    private java.lang.Integer   bindingState;
    private java.lang.Integer   disabilityState;


    public final static int    CERTIFICATETYPE_CODE = 1;

    /**
     * cookie 埋点的名称
     */
    public final static String EJS_SALECODE         = "ejs_salecode";
    /**
     * 财务审核状态：1、刚注册；
     */
    public final static int    STATE_1              = 1;
    /**
     * 财务审核状态：2、提交申请；
     */
    public final static int    STATE_2              = 2;
    /**
     * 财务审核状态：3、审核通过；
     */
    public final static int    STATE_3              = 3;
    /**
     * 财务审核状态：4、审核失败
     */
    public final static int    STATE_4              = 4;

    /**
     * 绑定状态：5、未绑定
     */
    public final static int    STATE_5              = 5;

    /**
     * 绑定状态：6、已绑定
     */
    public final static int    STATE_6              = 6;


    /**
     * 刚进入推荐人等级：1、第一级，自己申请；
     */
    public final static int    GRADE_1              = 1;
    /**
     * 刚进入推荐人等级：2、第二级数，由一级推广；
     */
    public final static int    GRADE_2              = 2;
    /**
     * 刚进入推荐人等级：3、第三级，由二级分销而来
     */
    public final static int    GRADE_3              = 3;

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
     * 获取用户ID
     */
    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置用户ID
     */
    public void setMemberId(java.lang.Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取用户姓名
     */
    public java.lang.String getMemberName() {
        return this.memberName;
    }

    /**
     * 设置用户姓名
     */
    public void setMemberName(java.lang.String memberName) {
        this.memberName = memberName;
    }

    /**
     * 获取1、身份证
     */
    public java.lang.Integer getCertificateType() {
        return this.certificateType;
    }

    /**
     * 设置1、身份证
     */
    public void setCertificateType(java.lang.Integer certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * 获取证件号码
     */
    public java.lang.String getCertificateCode() {
        return this.certificateCode;
    }

    /**
     * 设置证件号码
     */
    public void setCertificateCode(java.lang.String certificateCode) {
        this.certificateCode = certificateCode;
    }

    /**
     * 获取开户行
     */
    public java.lang.String getBankType() {
        return this.bankType;
    }

    /**
     * 设置开户行
     */
    public void setBankType(java.lang.String bankType) {
        this.bankType = bankType;
    }

    /**
     * 获取银行卡号
     */
    public java.lang.String getBankCode() {
        return this.bankCode;
    }

    /**
     * 设置银行卡号
     */
    public void setBankCode(java.lang.String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取开户人姓名
     */
    public java.lang.String getBankName() {
        return this.bankName;
    }

    /**
     * 设置开户人姓名
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取开户行地址
     */
    public java.lang.String getBankAdd() {
        return this.bankAdd;
    }

    /**
     * 设置开户行地址
     */
    public void setBankAdd(java.lang.String bankAdd) {
        this.bankAdd = bankAdd;
    }

    /**
     * 获取财务审核状态：1、刚注册；2、提交申请；3、审核通过；4、审核失败
     */
    public java.lang.Integer getState() {
        return this.state;
    }

    /**
     * 设置财务审核状态：1、刚注册；2、提交申请；3、审核通过；4、审核失败
     */
    public void setState(java.lang.Integer state) {
        this.state = state;
    }

    /**
     * 获取是否为推广员：0、不是；1、是
     */
    public java.lang.Integer getIsSale() {
        return this.isSale;
    }

    /**
     * 设置是否为推广员：0、不是；1、是
     */
    public void setIsSale(java.lang.Integer isSale) {
        this.isSale = isSale;
    }

    /**
     * 获取推荐人ID，为零没有推荐人
     */
    public java.lang.Integer getReferrerId() {
        return this.referrerId;
    }

    /**
     * 设置推荐人ID，为零没有推荐人
     */
    public void setReferrerId(java.lang.Integer referrerId) {
        this.referrerId = referrerId;
    }

    /**
     * 获取推荐人姓名
     */
    public java.lang.String getReferrerName() {
        return this.referrerName;
    }

    /**
     * 设置推荐人姓名
     */
    public void setReferrerName(java.lang.String referrerName) {
        this.referrerName = referrerName;
    }

    /**
     * 获取推荐人的推荐码
     */
    public java.lang.String getReferrerCode() {
        return this.referrerCode;
    }

    /**
     * 设置推荐人的推荐码
     */
    public void setReferrerCode(java.lang.String referrerCode) {
        this.referrerCode = referrerCode;
    }

    /**
     * 获取推荐人的推荐人的ID，第二级推荐人为0
     */
    public java.lang.Integer getReferrerPid() {
        return this.referrerPid;
    }

    /**
     * 设置推荐人的推荐人的ID，第二级推荐人为0
     */
    public void setReferrerPid(java.lang.Integer referrerPid) {
        this.referrerPid = referrerPid;
    }

    /**
     * 获取推荐人的推荐人的姓名
     */
    public java.lang.String getReferrerPname() {
        return this.referrerPname;
    }

    /**
     * 设置推荐人的推荐人的姓名
     */
    public void setReferrerPname(java.lang.String referrerPname) {
        this.referrerPname = referrerPname;
    }

    /**
     * 获取path路径，存储格式/1/2/，存储“/”为第一级分销商
     */
    public java.lang.Integer getReferrerPath() {
        return this.referrerPath;
    }

    /**
     * 设置path路径，存储格式/1/2/，存储“/”为第一级分销商
     */
    public void setReferrerPath(java.lang.Integer referrerPath) {
        this.referrerPath = referrerPath;
    }

    /**
     * 获取刚进入推荐人等级：1、第一级，自己申请；1、第二级数，由一级推广；3、第三级，由二级分销而来
     */
    public java.lang.Integer getGrade() {
        return this.grade;
    }

    /**
     * 设置刚进入推荐人等级：1、第一级，自己申请；1、第二级数，由一级推广；3、第三级，由二级分销而来
     */
    public void setGrade(java.lang.Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取我的推广码，用来生成推广的URL
     */
    public java.lang.String getSaleCode() {
        return this.saleCode;
    }

    /**
     * 设置我的推广码，用来生成推广的URL
     */
    public void setSaleCode(java.lang.String saleCode) {
        this.saleCode = saleCode;
    }

    /**
     * 获取审核意见
     */
    public java.lang.String getAudit() {
        return this.audit;
    }

    /**
     * 设置审核意见
     */
    public void setAudit(java.lang.String audit) {
        this.audit = audit;
    }

    /**
     * 获取审核人ID
     */
    public java.lang.Integer getAuditId() {
        return this.auditId;
    }

    /**
     * 设置审核人ID
     */
    public void setAuditId(java.lang.Integer auditId) {
        this.auditId = auditId;
    }

    /**
     * 获取审核人姓名
     */
    public java.lang.String getAuditName() {
        return this.auditName;
    }

    /**
     * 设置审核人姓名
     */
    public void setAuditName(java.lang.String auditName) {
        this.auditName = auditName;
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
     *认证类别 0 监护人 1 残疾人
     */
    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    /**
     *身份证图片路径
     */
    public String getCardPath() {
        return cardPath;
    }

    public void setCardPath(String cardPath) {
        this.cardPath = cardPath;
    }


    /**
     *残疾证等级
     */
    public String getDisabilityGrade() {
        return disabilityGrade;
    }

    public void setDisabilityGrade(String disabilityGrade) {
        this.disabilityGrade = disabilityGrade;
    }

    /**
     *残疾证号
     */
    public String getDisabilityCard() {
        return disabilityCard;
    }

    public void setDisabilityCard(String disabilityCard) {
        this.disabilityCard = disabilityCard;
    }
    /**
     *详细地址
     */
    public String getDetailDress() {
        return detailDress;
    }

    public void setDetailDress(String detailDress) {
        this.detailDress = detailDress;
    }
    /**
     *省id
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
     *市id
     */
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
    /**
     *区id
     */
    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
    /**
     *绑定状态0未绑定1已绑定
     */
    public Integer getBindingState() {
        return bindingState;
    }

    public void setBindingState(Integer bindingState) {
        this.bindingState = bindingState;
    }
    /**
     *残疾证状态 0未认证 1提交审核 2审核通过 3审核失败
     */
    public Integer getDisabilityState() {
        return disabilityState;
    }

    public void setDisabilityState(Integer disabilityState) {
        this.disabilityState = disabilityState;
    }

}