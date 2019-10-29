package com.yixiekeji.dao.analysis.write;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.analysis.BrowseLog;

@Mapper
public interface BrowseLogWriteDao {

    BrowseLog get(java.lang.Integer id);

    Integer insert(BrowseLog browseLog);

    Integer update(BrowseLog browseLog);
}