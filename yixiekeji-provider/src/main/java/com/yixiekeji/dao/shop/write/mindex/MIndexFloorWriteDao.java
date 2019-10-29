package com.yixiekeji.dao.shop.write.mindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MIndexFloor;

@Mapper
public interface MIndexFloorWriteDao {

    MIndexFloor get(java.lang.Integer id);

    Integer insert(MIndexFloor mIndexFloor);

    Integer update(MIndexFloor mIndexFloor);

    Integer delete(java.lang.Integer id);
}