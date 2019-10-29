package com.yixiekeji.dao.shop.write.mseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexFloorData;

@Mapper
public interface MSellerIndexFloorDataWriteDao {

    MSellerIndexFloorData get(java.lang.Integer id);

    Integer insert(MSellerIndexFloorData mIndexFloorData);

    Integer update(MSellerIndexFloorData mIndexFloorData);

    Integer delete(java.lang.Integer id);

    Integer deleteByFloorId(java.lang.Integer mIndexFloorId);
}