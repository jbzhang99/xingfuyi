package com.yixiekeji.dao.shop.write.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductPicture;

@Mapper
public interface ProductPictureWriteDao {

    Integer insert(ProductPicture productPicture);

    Integer update(ProductPicture productPicture);

    Integer del(Integer id);

    ProductPicture get(Integer id);

    Integer count(@Param("param1") Map<String, String> queryMap);

    List<ProductPicture> page(@Param("param1") Map<String, String> queryMap,
                              @Param("start") Integer start, @Param("size") Integer size);

    List<ProductPicture> getByProductId(@Param("productId") Integer productId);

    Integer delByProductId(Integer productId);

    ProductPicture getproductLead(@Param("productId") Integer productId);

}
