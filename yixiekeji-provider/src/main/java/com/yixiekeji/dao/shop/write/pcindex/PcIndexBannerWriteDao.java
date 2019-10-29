package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexBanner;

@Mapper
public interface PcIndexBannerWriteDao {

    PcIndexBanner get(java.lang.Integer id);

    Integer insert(PcIndexBanner pcIndexBanner);

    Integer update(PcIndexBanner pcIndexBanner);

    Integer delete(Integer id);
}