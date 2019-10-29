package com.yixiekeji.dao.shop.write.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductComments;

@Mapper
public interface ProductCommentsWriteDao {

    ProductComments get(Integer id);

    Integer insert(ProductComments productComments);

    Integer update(ProductComments productComments);

    Integer getProductCommentsCount(@Param("queryMap") Map<String, String> query);

    List<ProductComments> getProductComments(@Param("queryMap") Map<String, String> queryMap,
                                             @Param("start") Integer start,
                                             @Param("size") Integer size);

}
