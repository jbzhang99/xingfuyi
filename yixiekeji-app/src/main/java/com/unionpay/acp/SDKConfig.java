/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       MPI基本参数工具类
 * =============================================================================
 */
package com.unionpay.acp;

import com.yixiekeji.core.EjavashopConfig;

/**
 * 软件开发工具包 配制
 * 
 * @author xuyaowen
 * 
 */
public class SDKConfig {

    /** 前台请求URL. */
    private String           frontRequestUrl      = EjavashopConfig.SDK_FRONT_URL;
    /** 后台请求URL. */
    private String           backRequestUrl       = EjavashopConfig.SDK_BACK_URL;
    /** 单笔查询 */
    private String           singleQueryUrl       = EjavashopConfig.SDK_SIGNQ_URL;
    /** 批量查询 */
    private String           batchQueryUrl        = EjavashopConfig.SDK_SIGNQ_URL;
    /** 批量交易 */
    private String           batchTransUrl        = EjavashopConfig.SDK_BATTRANS_URL;
    /** 文件传输 */
    private String           fileTransUrl         = EjavashopConfig.SDK_FILETRANS_URL;
    /** 签名证书路径. */
    private String           signCertPath         = EjavashopConfig.SDK_SIGNCERT_PATH;
    /** 签名证书密码. */
    private String           signCertPwd          = EjavashopConfig.SDK_SIGNCERT_PWD;
    /** 签名证书类型. */
    private String           signCertType         = EjavashopConfig.SDK_SIGNCERT_TYPE;
    /** 加密公钥证书路径. */
    private String           encryptCertPath      = EjavashopConfig.SDK_ENCRYPTCERT_PATH;
    /** 验证签名公钥证书目录. */
    private String           validateCertDir      = EjavashopConfig.SDK_VALIDATECERT_DIR;
    /** 按照商户代码读取指定签名证书目录. */
    private String           signCertDir          = "";
    /** 磁道加密证书路径. */
    private String           encryptTrackCertPath = EjavashopConfig.SDK_ENCRYPTTRACKCERT_PATH;
    /** 有卡交易. */
    private String           cardRequestUrl       = EjavashopConfig.SDK_CARD_URL;
    /** app交易 */
    private String           appRequestUrl        = EjavashopConfig.SDK_APP_URL;
    /** 证书使用模式(单证书/多证书) 默认单证书*/
    private String           singleMode           = SDKConstants.TRUE_STRING;

    private static SDKConfig config;

    public static SDKConfig getConfig() {
        if (null == config) {
            config = new SDKConfig();
        }
        return config;
    }

    public String getFrontRequestUrl() {
        return frontRequestUrl;
    }

    public void setFrontRequestUrl(String frontRequestUrl) {
        this.frontRequestUrl = frontRequestUrl;
    }

    public String getBackRequestUrl() {
        return backRequestUrl;
    }

    public void setBackRequestUrl(String backRequestUrl) {
        this.backRequestUrl = backRequestUrl;
    }

    public String getSingleQueryUrl() {
        return singleQueryUrl;
    }

    public void setSingleQueryUrl(String singleQueryUrl) {
        this.singleQueryUrl = singleQueryUrl;
    }

    public String getBatchQueryUrl() {
        return batchQueryUrl;
    }

    public void setBatchQueryUrl(String batchQueryUrl) {
        this.batchQueryUrl = batchQueryUrl;
    }

    public String getBatchTransUrl() {
        return batchTransUrl;
    }

    public void setBatchTransUrl(String batchTransUrl) {
        this.batchTransUrl = batchTransUrl;
    }

    public String getFileTransUrl() {
        return fileTransUrl;
    }

    public void setFileTransUrl(String fileTransUrl) {
        this.fileTransUrl = fileTransUrl;
    }

    public String getSignCertPath() {
        return signCertPath;
    }

    public void setSignCertPath(String signCertPath) {
        this.signCertPath = signCertPath;
    }

    public String getSignCertPwd() {
        return signCertPwd;
    }

    public void setSignCertPwd(String signCertPwd) {
        this.signCertPwd = signCertPwd;
    }

    public String getSignCertType() {
        return signCertType;
    }

    public void setSignCertType(String signCertType) {
        this.signCertType = signCertType;
    }

    public String getEncryptCertPath() {
        return encryptCertPath;
    }

    public void setEncryptCertPath(String encryptCertPath) {
        this.encryptCertPath = encryptCertPath;
    }

    public String getValidateCertDir() {
        return validateCertDir;
    }

    public void setValidateCertDir(String validateCertDir) {
        this.validateCertDir = validateCertDir;
    }

    public String getSignCertDir() {
        return signCertDir;
    }

    public void setSignCertDir(String signCertDir) {
        this.signCertDir = signCertDir;
    }

    public String getEncryptTrackCertPath() {
        return encryptTrackCertPath;
    }

    public void setEncryptTrackCertPath(String encryptTrackCertPath) {
        this.encryptTrackCertPath = encryptTrackCertPath;
    }

    public String getCardRequestUrl() {
        return cardRequestUrl;
    }

    public void setCardRequestUrl(String cardRequestUrl) {
        this.cardRequestUrl = cardRequestUrl;
    }

    public String getAppRequestUrl() {
        return appRequestUrl;
    }

    public void setAppRequestUrl(String appRequestUrl) {
        this.appRequestUrl = appRequestUrl;
    }

    public String getSingleMode() {
        return singleMode;
    }

    public void setSingleMode(String singleMode) {
        this.singleMode = singleMode;
    }

}
