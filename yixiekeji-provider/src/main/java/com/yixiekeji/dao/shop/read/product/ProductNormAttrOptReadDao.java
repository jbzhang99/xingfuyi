package com.yixiekeji.dao.shop.read.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductNormAttrOpt;

@Mapper
public interface ProductNormAttrOptReadDao {

    ProductNormAttrOpt get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<ProductNormAttrOpt> queryList(Map<String, Object> map);

    List<ProductNormAttrOpt> queryNormsByProductId(Integer productId);

}
