package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcRecommend;

@Mapper
public interface PcRecommendWriteDao {

    PcRecommend get(java.lang.Integer id);

    Integer insert(PcRecommend pcRecommend);

    Integer update(PcRecommend pcRecommend);

    Integer delete(Integer id);
}