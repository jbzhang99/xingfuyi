package com.yixiekeji.dao.shop.read.integral;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegralBanner;

@Mapper
public interface ActIntegralBannerReadDao {

    ActIntegralBanner get(java.lang.Integer id);

    Integer getActIntegralBannersCount(@Param("queryMap") Map<String, String> queryMap);

    List<ActIntegralBanner> getActIntegralBanners(@Param("queryMap") Map<String, String> queryMap,
                                                  @Param("start") Integer start,
                                                  @Param("size") Integer size);

    /**
     * 查询积分商城首页正在使用的轮播图
     * @param pcMobile
     * @return
     */
    List<ActIntegralBanner> getActIntegralBannersPcMobile(@Param("pcMobile") int pcMobile);

}