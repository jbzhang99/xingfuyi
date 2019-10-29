package com.yixiekeji.config;

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

    private String searchSolrUrl;
    private String searchSolrServer;

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

}
