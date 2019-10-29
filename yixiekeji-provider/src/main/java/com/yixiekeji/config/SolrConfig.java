package com.yixiekeji.config;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {
    @Resource
    private DomainUrlUtil domainUrlUtil;

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient(domainUrlUtil.getSearchSolrUrl());
    }

}
