package com.yixiekeji.dao.shop.write.seller;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerRoles;

@Mapper
public interface SellerRolesWriteDao {

    SellerRoles get(java.lang.Integer id);

    Integer insert(SellerRoles sellerRoles);

    Integer update(SellerRoles sellerRoles);

    Integer delete(java.lang.Integer id);
}