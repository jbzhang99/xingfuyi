package com.yixiekeji.dao.shop.write.pcseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcseller.PcSellerIndexBanner;

@Mapper
public interface PcSellerIndexBannerWriteDao {

    PcSellerIndexBanner get(java.lang.Integer id);

    Integer insert(PcSellerIndexBanner pcSellerIndexBanner);

    Integer update(PcSellerIndexBanner pcSellerIndexBanner);

    Integer delete(Integer id);
}