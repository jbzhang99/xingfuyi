package com.yixiekeji.dao.shop.read.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductAttr;

@Mapper
public interface ProductAttrReadDao {

    ProductAttr get(Integer id);

    List<ProductAttr> getByProductId(Integer productId);

    /**
     * 根据商品id获取ProductAttr
     * @param productId
     * @return
     */
    List<ProductAttr> getQueryAttrByProductId(@Param("productId") Integer productId);
}
