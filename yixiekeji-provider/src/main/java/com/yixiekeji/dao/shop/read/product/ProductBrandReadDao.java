package com.yixiekeji.dao.shop.read.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductBrand;

/**
 * 品牌的接口
 *                       
 * @Filename: ProductBrandReadDao.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Mapper
public interface ProductBrandReadDao {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProductBrand getById(@Param("id") Integer id);

    /**
     * 根据ID的集合统计品牌
     * @param ids
     * @return
     */
    List<ProductBrand> getByIds(@Param("ids") String ids);

    /**
     * 获取推荐品牌
     * @return
     */
    List<ProductBrand> getHotBrands();

    /**
     * 获取所有显示中的品牌
     * @return
     */
    List<ProductBrand> getAllEffectBrands();

    /**
     * 根据品牌的名称查询
     * @param name
     * @return
     */
    ProductBrand getProductBrandByName(@Param("name") String name);

    /**
       * 根据条件查询brand的count
       * @param queryMap
       * @return
       */
    Integer count(@Param("param1") Map<String, String> queryMap);

    /**
     * 分页查询
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<ProductBrand> page(@Param("param1") Map<String, String> queryMap,
                            @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 取出所有的品牌
     * @return
     */
    List<ProductBrand> listNoPage();
}
