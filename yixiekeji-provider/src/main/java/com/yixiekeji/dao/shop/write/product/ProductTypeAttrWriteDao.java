package com.yixiekeji.dao.shop.write.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.vo.product.ProductTypeAttrVO;

@Mapper
public interface ProductTypeAttrWriteDao {

    Integer insert(ProductTypeAttr productTypeAttr);

    Integer update(ProductTypeAttr productTypeAttr);

    Integer del(Integer id);

    Integer delByTypeId(Integer id);

    ProductTypeAttr get(Integer id);

    List<ProductTypeAttr> getByTypeId(Integer id);

    Integer count(@Param("param1") Map<String, String> queryMap);

    List<ProductTypeAttrVO> page(@Param("param1") Map<String, String> queryMap,
                                 @Param("start") Integer start, @Param("size") Integer size);
}
