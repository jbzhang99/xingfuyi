package com.yixiekeji.dao.shop.write.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductAttr;

@Mapper
public interface ProductAttrWriteDao {

    Integer insert(ProductAttr productAttr);

    Integer update(ProductAttr productAttr);

    Integer del(Integer id);

    ProductAttr get(Integer id);

    List<ProductAttr> getByProductId(@Param("productId") Integer productId);

    //    Integer count(@Param("param1") Map<String, String> queryMap);
    //
    //    List<ProductAttr> page(@Param("param1") Map<String, String> queryMap,
    //                           @Param("start") Integer start, @Param("size") Integer size);

    Integer delByProductId(Integer productId);
}
