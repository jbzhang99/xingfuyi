package com.yixiekeji.dao.shop.write.mindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MIndexFloorData;

@Mapper
public interface MIndexFloorDataWriteDao {

    MIndexFloorData get(java.lang.Integer id);

    Integer insert(MIndexFloorData mIndexFloorData);

    Integer update(MIndexFloorData mIndexFloorData);

    Integer delete(java.lang.Integer id);

    Integer deleteByFloorId(java.lang.Integer mIndexFloorId);
}