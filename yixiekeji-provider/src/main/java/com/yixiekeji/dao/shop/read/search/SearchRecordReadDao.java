package com.yixiekeji.dao.shop.read.search;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchRecord;

@Mapper
public interface SearchRecordReadDao {

    SearchRecord get(java.lang.Integer id);

    int count(@Param("param1") Map<String, String> queryMap);

    List<SearchRecord> getSearchRecords(@Param("param1") Map<String, String> queryMap,
                                        @Param("start") Integer start, @Param("size") Integer size);

    List<SearchRecord> getSearchRecordByKeyword(@Param("keyword") String keyword,
                                                @Param("number") int number);

}