package com.yixiekeji.web.controller.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.search.SearchSetting;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.search.ISearchSettingService;
import com.yixiekeji.service.search.ISolrProductService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 索引初始化
 *                       
 * @Filename: SearchIndexesController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "admin/searchIndexes", produces = "application/json;charset=UTF-8")
public class SearchIndexesController extends BaseController {

    @Resource
    private ISearchSettingService searchSettingService;

    @Resource
    private IProductService       productService;

    @Resource
    private ISolrProductService   solrProductService;

    @Resource
    private DomainUrlUtil         domainUrlUtil;

    /**
     * 每次处理索引的最大行数
     */
    private static final int      INDEX_NUMBER = 1000;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        return "admin/search/searchIndexes";
    }

    @RequestMapping(value = "operation", method = { RequestMethod.POST })
    public String doAdd(Map<String, Object> dataMap) throws Exception {
        ServiceResult<SearchSetting> serviceResult = searchSettingService
            .getSearchSettingById(ConstantsEJS.SEARCHSETTINGID);
        SearchSetting searchSetting = serviceResult.getResult();

        searchSetting.setIndexProductId(SearchSetting.INDEX_PRODUCT_ID_0);
        ServiceResult<Integer> serviceResultUpdate = searchSettingService
            .updateSearchSetting(searchSetting);
        if (!serviceResultUpdate.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResultUpdate.getCode())) {
                throw new RuntimeException(serviceResultUpdate.getMessage());
            } else {
                throw new BusinessException(serviceResultUpdate.getMessage());
            }
        }

        ServiceResult<Product> resultMax = productService.getProductByMax();
        if (!resultMax.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultMax.getCode())) {
                throw new RuntimeException(resultMax.getMessage());
            } else {
                throw new BusinessException(resultMax.getMessage());
            }
        }

        Product product = resultMax.getResult();
        Integer maxId = product == null ? 200 : (product.getId() + 200);
        deleteByQuery(maxId);

        // 重新把索引生成
        ServiceResult<Boolean> createResult = solrProductService.jobCreateIndexesSolr(
            domainUrlUtil.getSearchSolrUrl(), domainUrlUtil.getSearchSolrServer());
        if (!createResult.getSuccess()) {
            dataMap.put("message", createResult.getMessage());
        } else {
            dataMap.put("message", "初始化成功");
        }

        return "admin/search/searchIndexes";
    }

    public SolrClient getSolrClient() {
        return new HttpSolrClient(
            domainUrlUtil.getSearchSolrUrl() + "/" + domainUrlUtil.getSearchSolrServer());
    }

    /**
     * 删除索引
     */
    public void deleteByQuery(int maxId) {
        SolrClient client = getSolrClient();

        int num = maxId / INDEX_NUMBER;
        try {
            List<String> list = null;
            for (int i = 0; i < num; i++) {
                list = new ArrayList<String>();
                for (int j = (i * INDEX_NUMBER + 1); j <= ((i + 1) * INDEX_NUMBER); j++) {
                    list.add(j + "");
                }

                client.deleteById(list);
                client.commit();
            }

            list = new ArrayList<String>();
            for (int i = (num * INDEX_NUMBER + 1); i <= maxId; i++) {
                list.add(i + "");
            }

            client.deleteById(list);
            client.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
