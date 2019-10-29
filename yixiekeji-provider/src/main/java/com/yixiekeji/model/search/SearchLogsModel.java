package com.yixiekeji.model.search;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.dao.shop.read.search.SearchLogsReadDao;
import com.yixiekeji.dao.shop.write.search.SearchLogsWriteDao;
import com.yixiekeji.entity.search.SearchLogs;

@Component
public class SearchLogsModel {

    @Resource
    private SearchLogsWriteDao searchLogsWriteDao;

    @Resource
    private SearchLogsReadDao  searchLogsReadDao;

    /**
     * 根据id取得搜索历史记录表
     * @param  searchLogsId
     * @return
     */
    public SearchLogs getSearchLogsById(Integer searchLogsId) {
        return searchLogsReadDao.get(searchLogsId);
    }

    /**
     * 保存搜索历史记录表
     * @param  searchLogs
     * @return
     */
    public Integer saveSearchLogs(SearchLogs searchLogs) {
        this.dbConstrains(searchLogs);
        //同一个用户历史记录只是保存一次，更新创建时间
        int count = searchLogsWriteDao.countByKeyWordCookie(searchLogs.getKeyword(),
            searchLogs.getSiteCookie());
        if (count == 0) {
            return searchLogsWriteDao.insert(searchLogs);
        } else {
            return searchLogsWriteDao.updateByKeyWordCookie(searchLogs.getKeyword(),
                searchLogs.getSiteCookie());
        }
    }

    /**
    * 更新搜索历史记录表
    * @param  searchLogs
    * @return
    */
    public Integer updateSearchLogs(SearchLogs searchLogs) {
        this.dbConstrains(searchLogs);
        return searchLogsWriteDao.update(searchLogs);
    }

    private void dbConstrains(SearchLogs searchLogs) {
        searchLogs.setKeyword(StringUtil.dbSafeString(searchLogs.getKeyword(), false, 50));
        searchLogs.setIp(StringUtil.dbSafeString(searchLogs.getIp(), false, 20));
        searchLogs.setSiteCookie(StringUtil.dbSafeString(searchLogs.getSiteCookie(), false, 50));
    }

    /**
     * 根据cookie获取搜索最近N条记录
     * @param cookieValue
     * @return
     */
    public List<SearchLogs> getSearchLogsByCookie(String cookieValue, int number) {
        if (cookieValue == null || "".equals(cookieValue)) {
            return null;
        }
        return searchLogsReadDao.getSearchLogsByCookie(cookieValue, number);
    }

    /**
     * 查询
     * @param queryMap
     * @param pager
     * @return
     */
    public List<SearchLogs> getSearchLogss(Map<String, String> queryMap, PagerInfo pager) {
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(searchLogsReadDao.count(queryMap));
            start = pager.getStart();
            size = pager.getPageSize();
        }

        List<SearchLogs> list = searchLogsReadDao.getSearchKeywords(queryMap, start, size);
        return list;
    }
}