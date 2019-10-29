package com.yixiekeji.dao.shop.write.mseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexBanner;

@Mapper
public interface MSellerIndexBannerWriteDao {

    MSellerIndexBanner get(java.lang.Integer id);

    Integer insert(MSellerIndexBanner mIndexBanner);

    Integer update(MSellerIndexBanner mIndexBanner);

    Integer delete(Integer id);
}