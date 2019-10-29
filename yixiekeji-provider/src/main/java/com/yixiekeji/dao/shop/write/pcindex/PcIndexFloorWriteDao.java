package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloor;

@Mapper
public interface PcIndexFloorWriteDao {

    PcIndexFloor get(java.lang.Integer id);

    Integer insert(PcIndexFloor pcIndexFloor);

    Integer update(PcIndexFloor pcIndexFloor);

    Integer delete(Integer id);
}