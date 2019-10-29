package com.yixiekeji.service.search;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.search.SearchLogs;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "searchLogs")
@Service(value = "searchLogsService")
public interface ISearchLogsService {

    /**
     * 根据id取得搜索历史记录表
     * @param  searchLogsId
     * @return
     */
    @RequestMapping(value = "getSearchLogsById", method = RequestMethod.GET)
    ServiceResult<SearchLogs> getSearchLogsById(@RequestParam("searchLogsId") Integer searchLogsId);

    /**
     * 保存搜索历史记录表
     * @param  searchLogs
     * @return
     */
    @RequestMapping(value = "saveSearchLogs", method = RequestMethod.POST)
    ServiceResult<Integer> saveSearchLogs(SearchLogs searchLogs);

    /**
    * 更新搜索历史记录表
    * @param  searchLogs
    * @return
    */
    @RequestMapping(value = "updateSearchLogs", method = RequestMethod.POST)
    ServiceResult<Integer> updateSearchLogs(SearchLogs searchLogs);

    /**
     * 根据 cookieValue 取得前5条记录用户页面展现
     * @param cookieValue
     * @return
     */
    @RequestMapping(value = "getSearchLogsByCookie", method = RequestMethod.GET)
    ServiceResult<List<SearchLogs>> getSearchLogsByCookie(@RequestParam("cookieValue") String cookieValue,
                                                          @RequestParam("number") int number);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSearchLogss", method = RequestMethod.POST)
    ServiceResult<List<SearchLogs>> getSearchLogss(FeignUtil feignUtil);
}