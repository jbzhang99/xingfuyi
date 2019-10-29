package com.yixiekeji.service.impl.search;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.search.SearchKeyword;
import com.yixiekeji.entity.search.SearchSetting;
import com.yixiekeji.model.search.SearchSettingModel;
import com.yixiekeji.service.search.ISearchSettingService;

/**
 * 搜索相关设置
 *                       
 * @Filename: SearchSettingServiceImpl.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@RestController
public class SearchSettingServiceImpl implements ISearchSettingService {

    private static Logger      log = LoggerFactory.getLogger(SearchSettingServiceImpl.class);

    @Resource
    private SearchSettingModel searchSettingModel;

    /**
    * 根据id取得search_setting对象
    * @param  searchSettingId
    * @return
    * @see com.yixiekeji.search.service.SearchSettingService#getSearchSettingById(java.lang.Integer)
    */
    @Override
    public ServiceResult<SearchSetting> getSearchSettingById(@RequestParam("searchSettingId") Integer searchSettingId) {
        ServiceResult<SearchSetting> result = new ServiceResult<SearchSetting>();
        try {
            result.setResult(searchSettingModel.getSearchSettingById(searchSettingId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][getSearchSettingById]根据id[" + searchSettingId
                      + "]取得search_setting对象时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchSettingServiceImpl][getSearchSettingById]根据id[" + searchSettingId
                      + "]取得search_setting对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][getSearchSettingById]根据id["
                              + searchSettingId + "]取得search_setting对象时出现未知异常");
        }
        return result;
    }

    /**
     * 保存search_setting对象
     * @param  searchSetting
     * @return
     * @see com.yixiekeji.search.service.SearchSettingService#saveSearchSetting(SearchSetting)
     */
    @Override
    public ServiceResult<Integer> saveSearchSetting(@RequestBody SearchSetting searchSetting) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchSettingModel.saveSearchSetting(searchSetting));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[SearchSettingServiceImpl][saveSearchSetting]保存search_setting对象时出现未知异常：" + be);
        } catch (Exception e) {
            log.error(
                "[SearchSettingServiceImpl][saveSearchSetting]保存search_setting对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[SearchSettingServiceImpl][saveSearchSetting]保存search_setting对象时出现未知异常");
        }
        return result;
    }

    /**
    * 更新search_setting对象
    * @param  searchSetting
    * @return
    * @see com.yixiekeji.search.service.SearchSettingService#updateSearchSetting(SearchSetting)
    */
    @Override
    public ServiceResult<Integer> updateSearchSetting(@RequestBody SearchSetting searchSetting) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchSettingModel.updateSearchSetting(searchSetting));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[SearchSettingServiceImpl][updateSearchSetting]更新search_setting对象时出现未知异常：" + be);
        } catch (Exception e) {
            log.error(
                "[SearchSettingServiceImpl][updateSearchSetting]更新search_setting对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[SearchSettingServiceImpl][updateSearchSetting]更新search_setting对象时出现未知异常");
        }
        return result;
    }

    /**
     * 更新关键词
     * @param id
     * @param keyword
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#updateKeyword(int, java.lang.String)
     */
    @Override
    public ServiceResult<Integer> updateKeyword(@RequestParam("searchsettingid") int searchsettingid,
                                                @RequestParam("keyword") String keyword) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchSettingModel.updateKeyword(searchsettingid, keyword));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][updateKeyword]更新关键词出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchSettingServiceImpl][updateKeyword]更新关键词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][updateKeyword]更新关键词出现未知异常");
        }
        return result;
    }

    /**
     * 更新查询关键字
     * @param id
     * @param keywordFilter
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#updateKeywordFilter(int, int)
     */
    @Override
    public ServiceResult<Integer> updateKeywordFilter(@RequestParam("searchsettingid") int searchsettingid,
                                                      @RequestParam("keywordFilter") int keywordFilter) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result
                .setResult(searchSettingModel.updateKeywordFilter(searchsettingid, keywordFilter));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][updateKeywordFilter]更新查询关键字时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchSettingServiceImpl][updateKeywordFilter]更新查询关键字时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][updateKeywordFilter]更新查询关键字时出现未知异常");
        }
        return result;
    }

    /**
     * 关键字查询
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#getSearchKeywords(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<SearchKeyword>> getSearchKeywords(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<SearchKeyword>> serviceResult = new ServiceResult<List<SearchKeyword>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(searchSettingModel.getSearchKeywords(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SearchSettingServiceImpl][getSearchKeywords] exception:", e);
        }
        return serviceResult;
    }

    /**
     * 插入敏感词
     * @param searchKeyword
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#createSearchKeyword(com.yixiekeji.entity.search.SearchKeyword)
     */
    @Override
    public ServiceResult<Integer> createSearchKeyword(@RequestBody SearchKeyword searchKeyword) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchSettingModel.createSearchKeyword(searchKeyword));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][createSearchKeyword]插入敏感词出现未知异常：" + be);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SearchSettingServiceImpl][createSearchKeyword]插入敏感词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][createSearchKeyword]插入敏感词出现未知异常");
        }
        return result;
    }

    /**
     * 根据ID获取敏感词
     * @param id
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#getSearchKeywordById(java.lang.Integer)
     */
    @Override
    public ServiceResult<SearchKeyword> getSearchKeywordById(@RequestParam("id") Integer id) {
        ServiceResult<SearchKeyword> result = new ServiceResult<SearchKeyword>();
        try {
            result.setResult(searchSettingModel.getSearchKeywordById(id));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][getSearchKeywordById]根据ID获取敏感词出现未知异常：" + be);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SearchSettingServiceImpl][getSearchKeywordById]根据ID获取敏感词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][getSearchKeywordById]根据ID获取敏感词出现未知异常");
        }
        return result;
    }

    /**
     * 更新敏感词
     * @param searchKeyword
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#updateSearchKeyword(com.yixiekeji.entity.search.SearchKeyword)
     */
    @Override
    public ServiceResult<Boolean> updateSearchKeyword(@RequestBody SearchKeyword searchKeyword) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(searchSettingModel.updateSearchKeyword(searchKeyword) > 0);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][updateSearchKeyword]更新敏感词出现未知异常：" + be);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SearchSettingServiceImpl][updateSearchKeyword]更新敏感词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][updateSearchKeyword]更新敏感词出现未知异常");
        }
        return result;
    }

    /**
     * 删除敏感词
     * @param id
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#delSearchKeyword(java.lang.Integer)
     */
    @Override
    public ServiceResult<Boolean> delSearchKeyword(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(searchSettingModel.delSearchKeyword(id) > 0);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchSettingServiceImpl][delSearchKeyword]删除敏感词出现未知异常：" + be);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SearchSettingServiceImpl][delSearchKeyword]删除敏感词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][delSearchKeyword]删除敏感词出现未知异常");
        }
        return result;
    }

    /**
     * 根据查询词，查询敏感词
     * @param keyword
     * @return
     * @see com.yixiekeji.service.search.ISearchSettingService#getSearchKeywordsByKeyword(java.lang.String)
     */
    @Override
    public ServiceResult<Integer> getSearchKeywordsByKeyword(@RequestParam("keyword") String keyword) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchSettingModel.getSearchKeywordsByKeyword(keyword));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[SearchSettingServiceImpl][getSearchKeywordsByKeyword]根据查询词，查询敏感词出现未知异常：" + be);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(
                "[SearchSettingServiceImpl][getSearchKeywordsByKeyword]根据查询词，查询敏感词出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[SearchSettingServiceImpl][getSearchKeywordsByKeyword]根据查询词，查询敏感词出现未知异常");
        }
        return result;
    }

}