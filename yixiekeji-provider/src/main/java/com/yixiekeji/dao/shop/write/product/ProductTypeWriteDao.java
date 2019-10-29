package com.yixiekeji.dao.shop.write.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductType;

@Mapper
public interface ProductTypeWriteDao {

    Integer insert(ProductType productType);

    Integer update(ProductType productType);

    Integer del(Integer id);

    ProductType get(Integer id);

    Integer count(@Param("param1") Map<String, String> queryMap);

    List<ProductType> page(@Param("param1") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);
    List<ProductType> getByNormId(@Param("normId") Integer normId);
    List<ProductType> getByBrandId(@Param("brandId") Integer brandId);
}
