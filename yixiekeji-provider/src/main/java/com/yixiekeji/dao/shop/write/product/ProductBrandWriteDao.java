package com.yixiekeji.dao.shop.write.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.ProductBrand;

/**
 * @Version: 1.0
 * @Author: zhaozhx
 * @Email: zhaozhx@sina.cn
 */
@Mapper
public interface ProductBrandWriteDao {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    ProductBrand getById(Integer id);

    List<ProductBrand> getByIds(@Param("ids") String ids);

    /**
     * 根据name查询ProductBrand，用于检测name是否唯一
     * @param name
     * @return
     */
    Integer getByName(@Param("name") String name);

    /**
     * 保存brand<br/>
     * <li>top 默认1（推荐）、state 默认2（不显示）</li>
     * @param brand
     * @return
     */
    Integer save(ProductBrand brand);

    /**
     * 修改brand<br/>
     * <li>删除功能：修改state状态为 3（删除）</li>
     * <li>审核商家申请品牌：修改state状态为 1（显示）</li>
     * @param brand
     * @return
     */
    Integer update(ProductBrand brand);

}
