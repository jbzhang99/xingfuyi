package com.yixiekeji.dao.shop.write.seller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerUserLoginLog;

@Mapper
public interface SellerUserLoginLogWriteDao {

    SellerUserLoginLog get(java.lang.Integer id);

    Integer insert(SellerUserLoginLog sellerUserLoginLog);

    Integer update(SellerUserLoginLog sellerUserLoginLog);
}