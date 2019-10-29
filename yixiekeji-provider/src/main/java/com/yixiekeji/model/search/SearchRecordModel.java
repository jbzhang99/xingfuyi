package com.yixiekeji.model.search;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.dao.shop.read.search.SearchRecordReadDao;
import com.yixiekeji.dao.shop.write.search.SearchRecordWriteDao;
import com.yixiekeji.entity.search.SearchRecord;

@Component
public class SearchRecordModel {

    @Resource
    private SearchRecordWriteDao searchRecordWriteDao;

    @Resource
    private SearchRecordReadDao  searchRecordReadDao;

    /**
     * 根据id取得模糊搜索匹配表
     * @param  searchRecordId
     * @return
     */
    public SearchRecord getSearchRecordById(Integer searchRecordId) {
        return searchRecordReadDao.get(searchRecordId);
    }

    /**
     * 保存模糊搜索匹配表
     * @param  searchRecord
     * @return
     */
    public Integer saveSearchRecord(SearchRecord searchRecord) {
        this.dbConstrains(searchRecord);
        return searchRecordWriteDao.insert(searchRecord);
    }

    /**
    * 更新模糊搜索匹配表
    * @param  searchRecord
    * @return
    */
    public Integer updateSearchRecord(SearchRecord searchRecord) {
        this.dbConstrains(searchRecord);
        return searchRecordWriteDao.update(searchRecord);
    }

    private void dbConstrains(SearchRecord searchRecord) {
        searchRecord.setKeyword(StringUtil.dbSafeString(searchRecord.getKeyword(), false, 50));
        searchRecord
            .setKeywordPinyin(StringUtil.dbSafeString(searchRecord.getKeywordPinyin(), false, 200));
        searchRecord
            .setCreateName(StringUtil.dbSafeString(searchRecord.getCreateName(), false, 50));
    }

    public List<SearchRecord> getSearchRecords(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(searchRecordReadDao.count(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }

        List<SearchRecord> list = searchRecordReadDao.getSearchRecords(queryMap, start, size);
        return list;
    }

    public int delSearchRecord(Integer id) {
        return searchRecordWriteDao.del(id);
    }

    /**
     * 前台搜索框动态匹配
     * @param keyword
     * @param number
     * @return
     */
    public List<SearchRecord> getSearchRecordByKeyword(String keyword, int number) {
        return searchRecordReadDao.getSearchRecordByKeyword(keyword, number);
    }

}