package com.yixiekeji.dao.shop.read.seller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerUserLoginLog;

@Mapper
public interface SellerUserLoginLogReadDao {

    SellerUserLoginLog get(java.lang.Integer id);

}