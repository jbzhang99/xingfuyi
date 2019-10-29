package com.yixiekeji.service.impl.search;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.search.SearchLogs;
import com.yixiekeji.model.search.SearchLogsModel;
import com.yixiekeji.service.search.ISearchLogsService;

@RestController
public class SearchLogsServiceImpl implements ISearchLogsService {

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory
        .getLogger(SearchLogsServiceImpl.class);

    @Resource
    private SearchLogsModel         searchLogsModel;

    /**
    * 根据id取得搜索历史记录表
    * @param  searchLogsId
    * @return
    * @see com.yixiekeji.service.SearchLogsService#getSearchLogsById(java.lang.Integer)
    */
    @Override
    public ServiceResult<SearchLogs> getSearchLogsById(@RequestParam("searchLogsId") Integer searchLogsId) {
        ServiceResult<SearchLogs> result = new ServiceResult<SearchLogs>();
        try {
            result.setResult(searchLogsModel.getSearchLogsById(searchLogsId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchLogsServiceImpl][getSearchLogsById]根据id[" + searchLogsId
                      + "]取得搜索历史记录表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchLogsServiceImpl][getSearchLogsById]根据id[" + searchLogsId
                      + "]取得搜索历史记录表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchLogsServiceImpl][getSearchLogsById]根据id[" + searchLogsId
                              + "]取得搜索历史记录表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存搜索历史记录表
     * @param  searchLogs
     * @return
     * @see com.yixiekeji.service.SearchLogsService#saveSearchLogs(SearchLogs)
     */
    @Override
    public ServiceResult<Integer> saveSearchLogs(@RequestBody SearchLogs searchLogs) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchLogsModel.saveSearchLogs(searchLogs));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchLogsServiceImpl][saveSearchLogs]保存搜索历史记录表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchLogsServiceImpl][saveSearchLogs]保存搜索历史记录表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchLogsServiceImpl][saveSearchLogs]保存搜索历史记录表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新搜索历史记录表
    * @param  searchLogs
    * @return
    * @see com.yixiekeji.service.SearchLogsService#updateSearchLogs(SearchLogs)
    */
    @Override
    public ServiceResult<Integer> updateSearchLogs(@RequestBody SearchLogs searchLogs) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(searchLogsModel.updateSearchLogs(searchLogs));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[SearchLogsServiceImpl][updateSearchLogs]更新搜索历史记录表时出现未知异常：" + be);
        } catch (Exception e) {
            log.error("[SearchLogsServiceImpl][updateSearchLogs]更新搜索历史记录表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[SearchLogsServiceImpl][updateSearchLogs]更新搜索历史记录表时出现未知异常");
        }
        return result;
    }

    /**
     * 根据cookie获取搜索最近N条记录
     * @param cookieValue
     * @return
     * @see com.yixiekeji.service.search.ISearchLogsService#getSearchLogsByCookie(java.lang.String)
     */
    @Override
    public ServiceResult<List<SearchLogs>> getSearchLogsByCookie(@RequestParam("cookieValue") String cookieValue,
                                                                 @RequestParam("number") int number) {
        ServiceResult<List<SearchLogs>> result = new ServiceResult<List<SearchLogs>>();
        try {
            result.setResult(searchLogsModel.getSearchLogsByCookie(cookieValue, number));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[SearchLogsServiceImpl][getSearchLogsByCookie]根据cookie获取搜索最近N条记录出现业务异常：" + be);
        } catch (Exception e) {
            log.error(
                "[SearchLogsServiceImpl][getSearchLogsByCookie]根据cookie获取搜索最近N条记录时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[SearchLogsServiceImpl][getSearchLogsByCookie]根据cookie获取搜索最近N条记录出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SearchLogs>> getSearchLogss(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SearchLogs>> serviceResult = new ServiceResult<List<SearchLogs>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(searchLogsModel.getSearchLogss(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SearchLogsServiceImpl][getSearchLogss] exception:", e);
        }
        return serviceResult;
    }
}