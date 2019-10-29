package com.yixiekeji.dao.shop.write.pcseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcseller.PcSellerRecommendData;

@Mapper
public interface PcSellerRecommendDataWriteDao {

    PcSellerRecommendData get(java.lang.Integer id);

    Integer insert(PcSellerRecommendData pcSellerRecommendData);

    Integer update(PcSellerRecommendData pcSellerRecommendData);

    Integer delete(Integer id);

    Integer deleteByRecommendId(Integer recommendId);
}