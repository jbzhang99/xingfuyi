package com.yixiekeji.dao.shop.write.search;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchLogs;

@Mapper
public interface SearchLogsWriteDao {

    SearchLogs get(java.lang.Integer id);

    Integer insert(SearchLogs searchLogs);

    Integer update(SearchLogs searchLogs);

    Integer countByKeyWordCookie(@Param("keyword") String keyword,
                                 @Param("siteCookie") String siteCookie);

    Integer updateByKeyWordCookie(@Param("keyword") String keyword,
                                  @Param("siteCookie") String siteCookie);
}