package com.yixiekeji.dao.shop.read.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchLogs;

@Mapper
public interface SearchLogsReadDao {

    SearchLogs get(java.lang.Integer id);

    List<SearchLogs> getSearchLogsByCookie(@Param("cookieValue") String cookieValue,
                                           @Param("number") int number);

    int count(@Param("param1") Map<String, String> queryMap);

    List<SearchLogs> getSearchKeywords(@Param("param1") Map<String, String> queryMap,
                                       @Param("start") Integer start, @Param("size") Integer size);

}