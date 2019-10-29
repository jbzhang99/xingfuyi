package com.yixiekeji.dao.analysis.read;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.analysis.BrowseLogMobile;

@Mapper
public interface BrowseLogMobileReadDao {

    BrowseLogMobile get(java.lang.Integer id);

}