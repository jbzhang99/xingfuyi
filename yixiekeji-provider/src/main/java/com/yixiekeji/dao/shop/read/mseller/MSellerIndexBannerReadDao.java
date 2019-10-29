package com.yixiekeji.dao.shop.read.mseller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexBanner;

@Mapper
public interface MSellerIndexBannerReadDao {

    MSellerIndexBanner get(java.lang.Integer id);

    Integer getMSellerIndexBannersCount(@Param("queryMap") Map<String, String> queryMap);

    List<MSellerIndexBanner> getMSellerIndexBanners(@Param("queryMap") Map<String, String> queryMap,
                                                    @Param("start") Integer start,
                                                    @Param("size") Integer size);

    /**
     * 为展示页面取数据，数据order by `order_no`
     * 
     * @param queryMap
     * @return
     */
    List<MSellerIndexBanner> getMSellerIndexBannersForView(@Param("queryMap") Map<String, String> queryMap);
}