package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloorData;

@Mapper
public interface PcIndexFloorDataWriteDao {

    PcIndexFloorData get(java.lang.Integer id);

    Integer insert(PcIndexFloorData pcIndexFloorData);

    Integer update(PcIndexFloorData pcIndexFloorData);

    Integer delete(Integer id);

    Integer deleteByFloorClassId(Integer floorClassId);
}