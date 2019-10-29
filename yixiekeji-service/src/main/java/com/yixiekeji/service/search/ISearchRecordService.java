package com.yixiekeji.service.search;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.search.SearchRecord;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "searchRecord")
@Service(value = "searchRecordService")
public interface ISearchRecordService {

    /**
     * 根据id取得模糊搜索匹配表
     * @param  searchRecordId
     * @return
     */
    @RequestMapping(value = "getSearchRecordById", method = RequestMethod.GET)
    ServiceResult<SearchRecord> getSearchRecordById(@RequestParam("searchRecordId") Integer searchRecordId);

    /**
     * 保存模糊搜索匹配表
     * @param  searchRecord
     * @return
     */
    @RequestMapping(value = "saveSearchRecord", method = RequestMethod.POST)
    ServiceResult<Integer> saveSearchRecord(SearchRecord searchRecord);

    /**
    * 更新模糊搜索匹配表
    * @param  searchRecord
    * @return
    */
    @RequestMapping(value = "updateSearchRecord", method = RequestMethod.POST)
    ServiceResult<Integer> updateSearchRecord(SearchRecord searchRecord);

    /**
     * 关键字查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSearchRecords", method = RequestMethod.POST)
    ServiceResult<List<SearchRecord>> getSearchRecords(FeignUtil feignUtil);

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @RequestMapping(value = "delSearchRecord", method = RequestMethod.GET)
    ServiceResult<Boolean> delSearchRecord(@RequestParam("id") Integer id);

    /**
     * 前台搜索框动态匹配
     * @param keyword
     * @param number
     * @return
     */
    @RequestMapping(value = "getSearchRecordByKeyword", method = RequestMethod.GET)
    ServiceResult<List<SearchRecord>> getSearchRecordByKeyword(@RequestParam("keyword") String keyword,
                                                               @RequestParam("number") int number);

}