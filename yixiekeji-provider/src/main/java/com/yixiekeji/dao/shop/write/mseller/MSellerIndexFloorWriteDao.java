package com.yixiekeji.dao.shop.write.mseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexFloor;

@Mapper
public interface MSellerIndexFloorWriteDao {

    MSellerIndexFloor get(java.lang.Integer id);

    Integer insert(MSellerIndexFloor mIndexFloor);

    Integer update(MSellerIndexFloor mIndexFloor);

    Integer delete(java.lang.Integer id);
}