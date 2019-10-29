package com.yixiekeji.dao.shop.write.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.vo.product.ProductCateVO;

@Mapper
public interface ProductCateWriteDao {

    Integer insert(ProductCate productCate);

    Integer update(ProductCate productCate);

    Integer del(Integer id);

    ProductCate get(Integer id);

    ProductCate getByTypeId(@Param("typeId") Integer typeId);

    Integer count(Map<String, String> queryMap);

    List<ProductCateVO> page(Map<String, Object> queryMap);

    List<ProductCate> getByPid(Integer pid);

    List<ProductCate> getBySellerId(Integer sellerId);
}
