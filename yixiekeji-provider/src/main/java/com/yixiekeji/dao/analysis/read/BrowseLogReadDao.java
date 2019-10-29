package com.yixiekeji.dao.analysis.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.dto.PVDto;
import com.yixiekeji.entity.analysis.BrowseLog;

@Mapper
public interface BrowseLogReadDao {

    BrowseLog get(java.lang.Integer id);

    List<PVDto> getProductPV(Map<String, String> queryMap);
}