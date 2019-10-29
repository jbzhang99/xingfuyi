package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerRoles;

@Mapper
public interface SellerRolesReadDao {

    SellerRoles get(java.lang.Integer id);

    Integer getSellerRolesCount(@Param("queryMap") Map<String, String> queryMap);

    List<SellerRoles> getSellerRoles(@Param("queryMap") Map<String, String> queryMap,
                                     @Param("start") Integer start, @Param("size") Integer size);
}