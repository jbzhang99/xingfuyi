package com.yixiekeji.dao.shop.write.seller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerUser;

@Mapper
public interface SellerUserWriteDao {

    SellerUser get(java.lang.Integer id);

    Integer insert(SellerUser sellerUser);

    Integer update(SellerUser sellerUser);

    Integer getCountByRoleId(Integer roleId);

    Integer del(Integer id);
}