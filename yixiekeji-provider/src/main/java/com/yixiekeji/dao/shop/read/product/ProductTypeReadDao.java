package com.yixiekeji.dao.shop.read.product;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductType;

@Mapper
public interface ProductTypeReadDao {

    ProductType get(Integer id);

}
