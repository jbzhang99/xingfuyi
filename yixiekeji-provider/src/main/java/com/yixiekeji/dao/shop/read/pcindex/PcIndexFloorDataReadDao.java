package com.yixiekeji.dao.shop.read.pcindex;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloorData;

@Mapper
public interface PcIndexFloorDataReadDao {

    PcIndexFloorData get(java.lang.Integer id);

    Integer getPcIndexFloorDatasCount(@Param("queryMap") Map<String, String> queryMap);

    List<PcIndexFloorData> getPcIndexFloorDatas(@Param("queryMap") Map<String, String> queryMap,
                                                @Param("start") Integer start,
                                                @Param("size") Integer size);

    /**
     * 根据楼层分类ID取分类
     * @param floorClassId
     * @return
     */
    List<PcIndexFloorData> getPcIndexFloorDatasForView(@Param("floorClassId") Integer floorClassId);
}