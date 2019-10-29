package com.yixiekeji.dao.shop.write.integral;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegralBanner;

@Mapper
public interface ActIntegralBannerWriteDao {

    ActIntegralBanner get(java.lang.Integer id);

    Integer insert(ActIntegralBanner actIntegralBanner);

    Integer update(ActIntegralBanner actIntegralBanner);

    Integer delete(Integer id);
}