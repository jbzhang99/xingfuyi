package com.yixiekeji.dao.shop.write.pcseller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcseller.PcSellerIndex;

@Mapper
public interface PcSellerIndexWriteDao {

    PcSellerIndex get(java.lang.Integer id);

    Integer insert(PcSellerIndex pcSellerIndex);

    Integer update(PcSellerIndex pcSellerIndex);

    Integer delete(Integer id);
}