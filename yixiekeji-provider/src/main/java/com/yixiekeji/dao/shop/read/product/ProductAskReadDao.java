package com.yixiekeji.dao.shop.read.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductAsk;

/**
 * 咨询管理
 *                       
 * @Filename: ProductAskReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface ProductAskReadDao {

    ProductAsk get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<ProductAsk> queryList(Map<String, Object> map);

    Integer getProductAsksCount(@Param("queryMap") Map<String, String> query);

    List<ProductAsk> getProductAsks(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);

}
