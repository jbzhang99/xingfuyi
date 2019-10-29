package com.yixiekeji.dao.shop.read.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchKeyword;

@Mapper
public interface SearchKeywordReadDao {

    SearchKeyword get(java.lang.Integer id);

    Integer countByKeyword(String keyword);

    int count(@Param("param1") Map<String, String> queryMap);

    List<SearchKeyword> getSearchKeywords(@Param("param1") Map<String, String> queryMap,
                                          @Param("start") Integer start, @Param("size") Integer size);
}