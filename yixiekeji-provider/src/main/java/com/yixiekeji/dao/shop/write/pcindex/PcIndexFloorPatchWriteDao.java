package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloorPatch;

@Mapper
public interface PcIndexFloorPatchWriteDao {

    PcIndexFloorPatch get(java.lang.Integer id);

    Integer insert(PcIndexFloorPatch pcIndexFloorPatch);

    Integer update(PcIndexFloorPatch pcIndexFloorPatch);

    Integer delete(Integer id);
}