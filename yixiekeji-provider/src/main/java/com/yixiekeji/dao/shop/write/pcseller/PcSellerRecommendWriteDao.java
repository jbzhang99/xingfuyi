package com.yixiekeji.dao.shop.write.pcseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcseller.PcSellerRecommend;

@Mapper
public interface PcSellerRecommendWriteDao {

    PcSellerRecommend get(java.lang.Integer id);

    Integer insert(PcSellerRecommend pcSellerRecommend);

    Integer update(PcSellerRecommend pcSellerRecommend);

    Integer delete(Integer id);
}