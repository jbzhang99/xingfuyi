package com.yixiekeji.dao.shop.write.mindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MIndexBanner;

@Mapper
public interface MIndexBannerWriteDao {

    MIndexBanner get(java.lang.Integer id);

    Integer insert(MIndexBanner mIndexBanner);

    Integer update(MIndexBanner mIndexBanner);

    Integer delete(Integer id);
}