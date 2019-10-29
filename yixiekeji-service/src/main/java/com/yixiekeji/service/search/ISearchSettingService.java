package com.yixiekeji.service.search;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.search.SearchKeyword;
import com.yixiekeji.entity.search.SearchSetting;

/**
 * 搜索相关设置
 *                       
 * @Filename: ISearchSettingService.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "searchSetting")
@Service(value = "searchSettingService")
public interface ISearchSettingService {

    /**
     * 根据id取得search_setting对象
     * @param  searchSettingId
     * @return
     */
    @RequestMapping(value = "getSearchSettingById", method = RequestMethod.GET)
    ServiceResult<SearchSetting> getSearchSettingById(@RequestParam("searchSettingId") Integer searchSettingId);

    /**
     * 保存search_setting对象
     * @param  searchSetting
     * @return
     */
    @RequestMapping(value = "saveSearchSetting", method = RequestMethod.POST)
    ServiceResult<Integer> saveSearchSetting(SearchSetting searchSetting);

    /**
    * 更新search_setting对象
    * @param  searchSetting
    * @return
    */
    @RequestMapping(value = "updateSearchSetting", method = RequestMethod.POST)
    ServiceResult<Integer> updateSearchSetting(SearchSetting searchSetting);

    /**
     * 更新关键词
     * @param searchsettingid
     * @param keyword
     * @return
     */
    @RequestMapping(value = "updateKeyword", method = RequestMethod.GET)
    ServiceResult<Integer> updateKeyword(@RequestParam("searchsettingid") int searchsettingid,
                                         @RequestParam("keyword") String keyword);

    /**
     * 更新过滤的敏感词
     * @param searchsettingid
     * @param keywordFilter
     * @return
     */
    @RequestMapping(value = "updateKeywordFilter", method = RequestMethod.GET)
    ServiceResult<Integer> updateKeywordFilter(@RequestParam("searchsettingid") int searchsettingid,
                                               @RequestParam("keywordFilter") int keywordFilter);

    /**
     * 关键字查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSearchKeywords", method = RequestMethod.POST)
    ServiceResult<List<SearchKeyword>> getSearchKeywords(FeignUtil feignUtil);

    /**
     * 插入敏感词
     * @param searchKeyword
     * @return
     */
    @RequestMapping(value = "createSearchKeyword", method = RequestMethod.POST)
    ServiceResult<Integer> createSearchKeyword(SearchKeyword searchKeyword);

    /**
     * 根据ID查询敏感词
     * @param id
     * @return
     */
    @RequestMapping(value = "getSearchKeywordById", method = RequestMethod.GET)
    ServiceResult<SearchKeyword> getSearchKeywordById(@RequestParam("id") Integer id);

    /**
     * 更新敏感词
     * @param searchKeyword
     * @return
     */
    @RequestMapping(value = "updateSearchKeyword", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSearchKeyword(SearchKeyword searchKeyword);

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @RequestMapping(value = "delSearchKeyword", method = RequestMethod.GET)
    ServiceResult<Boolean> delSearchKeyword(@RequestParam("id") Integer id);

    /**
     * 根据查询词，查询敏感词
     * @param keyword
     * @return
     */
    @RequestMapping(value = "getSearchKeywordsByKeyword", method = RequestMethod.GET)
    ServiceResult<Integer> getSearchKeywordsByKeyword(@RequestParam("keyword") String keyword);

}