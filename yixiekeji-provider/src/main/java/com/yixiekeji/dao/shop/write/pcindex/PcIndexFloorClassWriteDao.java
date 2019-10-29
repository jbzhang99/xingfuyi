package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloorClass;

@Mapper
public interface PcIndexFloorClassWriteDao {

    PcIndexFloorClass get(java.lang.Integer id);

    Integer insert(PcIndexFloorClass pcIndexFloorClass);

    Integer update(PcIndexFloorClass pcIndexFloorClass);

    Integer delete(Integer id);
}