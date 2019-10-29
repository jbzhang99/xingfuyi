package com.yixiekeji.dao.shop.write.mindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MRecommend;

@Mapper
public interface MRecommendWriteDao {

    MRecommend get(java.lang.Integer id);

    Integer insert(MRecommend pcRecommend);

    Integer update(MRecommend pcRecommend);

    Integer delete(Integer id);
}