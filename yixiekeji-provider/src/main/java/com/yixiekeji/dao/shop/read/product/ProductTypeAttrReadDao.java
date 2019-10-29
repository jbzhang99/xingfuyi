package com.yixiekeji.dao.shop.read.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductTypeAttr;

@Mapper
public interface ProductTypeAttrReadDao {

    ProductTypeAttr get(@Param("id") Integer id);

    /**
     * 根据类型查询出类型下面所有的检索属性
     * @param id
     * @return
     */
    List<ProductTypeAttr> getByTypeIdAndQuery(@Param("id") Integer id);

}
