package com.yixiekeji.dao.analysis.write;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.analysis.BrowseLogMobile;

@Mapper
public interface BrowseLogMobileWriteDao {

    BrowseLogMobile get(java.lang.Integer id);

    Integer insert(BrowseLogMobile browseLog);

    Integer update(BrowseLogMobile browseLog);
}