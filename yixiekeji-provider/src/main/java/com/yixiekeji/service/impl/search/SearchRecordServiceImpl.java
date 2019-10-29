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
import com.yixiekeji.entity.search.SearchRecord;
import com.yixiekeji.model.search.SearchRecordModel;
import com.yixiekeji.service.search.ISearchRecordService;

@RestController
public class SearchRecordServiceImpl implements ISearchRecordService {

    private static Logger     log = LoggerFactory.getLogger(SearchRecordServiceImpl.class);

    @Resource
    private SearchRecordModel searchRecordModel;

    /**
    * 根据id取得模糊搜索匹配表
    * @param  searchRecordId
    * @return
    * @see com.yixiekeji.service.SearchRecordService#getSearchRecordById(java.lang.Integer)
    */
    @Override
    public ServiceResult<SearchRecord> getSearchRecordById(@RequestParam("searchRecordId") Integer searchRecordId) {
        ServiceResult<SearchRecord> result = new ServiceResult<SearchRecord>();
        try {
            result.setResult(searchRecordModel.getSearchRecordById(searchRecordId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchRecordServiceImpl][getSearchRecordById]根据id[" + searchRecordId
                      + "]取得模糊搜索匹配表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchRecordServiceImpl][getSearchRecordById]根据id[" + searchRecordId
                      + "]取得模糊搜索匹配表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchRecordServiceImpl][getSearchRecordById]根据id[" + searchRecordId
                              + "]取得模糊搜索匹配表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存模糊搜索匹配表
     * @param  searchRecord
     * @return
     * @see com.yixiekeji.service.SearchRecordService#saveSearchRecord(SearchRecord)
     */
    @Override
    public ServiceResult<Integer> saveSearchRecord(@RequestBody SearchRecord searchRecord) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchRecordModel.saveSearchRecord(searchRecord));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchRecordServiceImpl][saveSearchRecord]保存模糊搜索匹配表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchRecordServiceImpl][saveSearchRecord]保存模糊搜索匹配表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchRecordServiceImpl][saveSearchRecord]保存模糊搜索匹配表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新模糊搜索匹配表
    * @param  searchRecord
    * @return
    * @see com.yixiekeji.service.SearchRecordService#updateSearchRecord(SearchRecord)
    */
    @Override
    public ServiceResult<Integer> updateSearchRecord(@RequestBody SearchRecord searchRecord) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchRecordModel.updateSearchRecord(searchRecord));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchRecordServiceImpl][updateSearchRecord]更新模糊搜索匹配表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchRecordServiceImpl][updateSearchRecord]更新模糊搜索匹配表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchRecordServiceImpl][updateSearchRecord]更新模糊搜索匹配表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SearchRecord>> getSearchRecords(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SearchRecord>> serviceResult = new ServiceResult<List<SearchRecord>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(searchRecordModel.getSearchRecords(queryMap, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SearchSettingServiceImpl][getSearchRecords] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> delSearchRecord(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(searchRecordModel.delSearchRecord(id) > 0);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[SearchSettingServiceImpl][delSearchRecord]删除模糊搜索出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchSettingServiceImpl][delSearchRecord]删除模糊搜索现未知异常");
        }
        return result;
    }

    /**
     * 前台搜索框动态匹配
     * @param keyword
     * @param number
     * @return
     * @see com.yixiekeji.service.search.ISearchRecordService#getSearchRecordByKeyword(java.lang.String, int)
     */
    @Override
    public ServiceResult<List<SearchRecord>> getSearchRecordByKeyword(@RequestParam("keyword") String keyword,
                                                                      @RequestParam("number") int number) {
        ServiceResult<List<SearchRecord>> serviceResult = new ServiceResult<List<SearchRecord>>();
        try {
            serviceResult.setResult(searchRecordModel.getSearchRecordByKeyword(keyword, number));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SearchSettingServiceImpl][getSearchRecordByKeyword] exception:", e);
        }
        return serviceResult;
    }
}