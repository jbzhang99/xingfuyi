package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerCate;

@Mapper
public interface SellerCateReadDao {

    SellerCate get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<SellerCate> queryList(Map<String, Object> map);

    List<SellerCate> getByPid(@Param("pid") Integer pid, @Param("sellerId") Integer sellerId);

}
