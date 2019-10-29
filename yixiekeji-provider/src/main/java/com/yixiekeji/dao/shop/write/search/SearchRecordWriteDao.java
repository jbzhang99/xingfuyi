package com.yixiekeji.dao.shop.write.search;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchRecord;

@Mapper
public interface SearchRecordWriteDao {

    SearchRecord get(java.lang.Integer id);

    Integer insert(SearchRecord searchRecord);

    Integer update(SearchRecord searchRecord);

    int del(Integer id);

    List<SearchRecord> getSearchRecordsAll();
}