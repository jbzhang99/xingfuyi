package com.yixiekeji.dao.shop.read.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductCate;

@Mapper
public interface ProductCateReadDao {

    ProductCate get(Integer id);

    Integer queryCount(Map<String, Object> map);

    List<ProductCate> queryList(Map<String, Object> map);

    /**
     * 根据Pid获取分类列表
     * @param pid
     * @return
     */
    List<ProductCate> getByPid(Integer pid);

    /**
     * 根据分类的名称查询分类
     * @param name
     * @return
     */
    ProductCate getProductCateByName(@Param("name") String name);

}
