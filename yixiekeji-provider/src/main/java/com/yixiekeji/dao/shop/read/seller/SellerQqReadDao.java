package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerQq;

@Mapper
public interface SellerQqReadDao {

    SellerQq get(java.lang.Integer id);

    Integer getCount(@Param("param1") Map<String, Object> queryMap);

    List<SellerQq> page(@Param("param1") Map<String, Object> queryMap,
                        @Param("start") Integer start, @Param("size") Integer size);

    List<SellerQq> list();

}
