package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerApplyBrand;

@Mapper
public interface SellerApplyBrandReadDao {

    SellerApplyBrand get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<SellerApplyBrand> queryList(Map<String, Object> map);

}
