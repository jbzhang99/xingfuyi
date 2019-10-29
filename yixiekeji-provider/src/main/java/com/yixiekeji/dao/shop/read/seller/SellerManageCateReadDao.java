package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerManageCate;

@Mapper
public interface SellerManageCateReadDao {

    SellerManageCate get(Integer id);

    Integer chkCate(@Param("cateId") Integer cateId, @Param("seller") Integer seller);

    Integer count(@Param("param1") Map<String, String> queryMap);

    List<SellerManageCate> page(@Param("param1") Map<String, String> queryMap,
                                @Param("start") Integer start, @Param("size") Integer size);

    Integer countByProductCateId(@Param("productCateId") Integer productCateId);
}
