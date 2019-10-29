package com.yixiekeji.dao.shop.write.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchKeyword;

@Mapper
public interface SearchKeywordWriteDao {

    SearchKeyword get(java.lang.Integer id);

    Integer insert(SearchKeyword searchKeyword);

    Integer update(SearchKeyword searchKeyword);

    int count(@Param("param1") Map<String, String> queryMap);

    List<SearchKeyword> getSearchKeywords(@Param("param1") Map<String, String> queryMap,
                                          @Param("start") Integer start, @Param("size") Integer size);

    int del(Integer id);
}