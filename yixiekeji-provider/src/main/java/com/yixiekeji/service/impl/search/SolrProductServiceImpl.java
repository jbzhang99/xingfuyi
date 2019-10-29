package com.yixiekeji.service.impl.search;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.model.search.SolrProductModel;
import com.yixiekeji.service.search.ISolrProductService;

/**
 * solr相关的操作
 *                       
 * @Filename: SolrProductServiceImpl.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@RestController
public class SolrProductServiceImpl implements ISolrProductService {

    private static Logger    log = LoggerFactory.getLogger(SolrProductServiceImpl.class);

    @Resource
    private SolrProductModel solrProductModel;

    /**
     * 创建索引
     * @param solrUrl
     * @param solrServer
     * @return
     * @see com.yixiekeji.service.search.ISolrProductService#jobCreateIndexesSolr(java.lang.String, java.lang.String)
     */
    @Override
    public ServiceResult<Boolean> jobCreateIndexesSolr(@RequestParam("solrUrl") String solrUrl,
                                                       @RequestParam("solrServer") String solrServer) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(solrProductModel.jobCreateIndexesSolr(solrUrl, solrServer));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[SolrProductServiceImpl][jobCreateIndexesSolr]系统定时创建索引出现未知异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SolrProductServiceImpl][jobCreateIndexesSolr]系统定时创建索引出现未知异常:", e);
        }
        return result;
    }

    /**
     * 更新敏感词的索引值
     * @return
     * @see com.yixiekeji.service.search.ISolrProductService#jobUpdateSearchRecordIndex()
     */
    @Override
    public ServiceResult<Boolean> jobUpdateSearchRecordIndex(@RequestParam("solrUrl") String solrUrl,
                                                             @RequestParam("solrServer") String solrServer) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(solrProductModel.jobUpdateSearchRecordIndex(solrUrl, solrServer));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SolrProductServiceImpl][jobUpdateSearchRecordIndex]更新敏感词的索引值出现未知异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SolrProductServiceImpl][jobUpdateSearchRecordIndex]更新敏感词的索引值出现未知异常:", e);
        }
        return result;
    }

}
