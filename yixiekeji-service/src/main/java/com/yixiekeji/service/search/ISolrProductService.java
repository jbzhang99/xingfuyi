package com.yixiekeji.service.search;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;

/**
 * solr相关的操作
 *                       
 * @Filename: ISolrProductService.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "solrProduct")
@Service(value = "solrProductService")
public interface ISolrProductService {

    /**
     * 创建索引
     */
    @RequestMapping(value = "jobCreateIndexesSolr", method = RequestMethod.GET)
    ServiceResult<Boolean> jobCreateIndexesSolr(@RequestParam("solrUrl") String solrUrl,
                                                @RequestParam("solrServer") String solrServer);

    /**
     * 更新敏感词的索引值
     * @param id
     * @return
     */
    @RequestMapping(value = "jobUpdateSearchRecordIndex", method = RequestMethod.GET)
    ServiceResult<Boolean> jobUpdateSearchRecordIndex(@RequestParam("solrUrl") String solrUrl,
                                                      @RequestParam("solrServer") String solrServer);
}
