package com.yixiekeji.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 系统资源
 *                       
 * @Filename: DomainUrlUtil.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Configuration
@ConfigurationProperties(prefix = "yxkj")
public class DomainUrlUtil {

    private String urlResources;
    private String staticResources;
    private String imageResources;
    private String cookieDomain;
    private String cookieName;
    private String frontUrl;
    private String h5Url;
    private String searchSolrUrl;
    private String searchSolrServer;
    private String pomLogFile;
    private String pomLogLevel;

    public String getUrlResources() {
        return urlResources;
    }

    public void setUrlResources(String urlResources) {
        this.urlResources = urlResources;
    }

    public String getStaticResources() {
        return staticResources;
    }

    public void setStaticResources(String staticResources) {
        this.staticResources = staticResources;
    }

    public String getImageResources() {
        return imageResources;
    }

    public void setImageResources(String imageResources) {
        this.imageResources = imageResources;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getSearchSolrUrl() {
        return searchSolrUrl;
    }

    public void setSearchSolrUrl(String searchSolrUrl) {
        this.searchSolrUrl = searchSolrUrl;
    }

    public String getSearchSolrServer() {
        return searchSolrServer;
    }

    public void setSearchSolrServer(String searchSolrServer) {
        this.searchSolrServer = searchSolrServer;
    }

    public String getPomLogFile() {
        return pomLogFile;
    }

    public void setPomLogFile(String pomLogFile) {
        this.pomLogFile = pomLogFile;
    }

    public String getPomLogLevel() {
        return pomLogLevel;
    }

    public void setPomLogLevel(String pomLogLevel) {
        this.pomLogLevel = pomLogLevel;
    }

}
