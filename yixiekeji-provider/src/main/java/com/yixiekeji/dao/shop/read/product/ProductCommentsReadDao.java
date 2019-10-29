package com.yixiekeji.dao.shop.read.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.dto.CommentsDto;
import com.yixiekeji.entity.product.ProductComments;

/**
 * 商品评价
 *                       
 * @Filename: ProductCommentsReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface ProductCommentsReadDao {

    ProductComments get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<ProductComments> queryList(Map<String, Object> map);

    /**
     * 根据商家ID获取商家商品各个评分的和
     * @param sellerId
     * @return
     */
    CommentsDto getSellerScoreSum(java.lang.Integer sellerId);

    Integer getProductCommentsCount(@Param("queryMap") Map<String, String> query);

    List<ProductComments> getProductComments(@Param("queryMap") Map<String, String> queryMap,
                                             @Param("start") Integer start,
                                             @Param("size") Integer size);
}
