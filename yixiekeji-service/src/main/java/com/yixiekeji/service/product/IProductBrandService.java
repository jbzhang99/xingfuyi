package com.yixiekeji.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductBrand;

/**
 * 品牌管理
 * 
 * @Version: 1.0
 * @Author: zhaozhx
 * @Email: zhaozhx@sina.cn
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productBrand")
@Service(value = "productBrandService")
public interface IProductBrandService {

    /**
     * 保存品牌信息
     * 用途：商家中心添加品牌信息、平台添加品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Boolean> save(ProductBrand brand);

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.GET)
    ServiceResult<ProductBrand> getById(@RequestParam("id") Integer id);

    /**
     * 根据id查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getByIds", method = RequestMethod.GET)
    ServiceResult<List<ProductBrand>> getByIds(@RequestParam("id") String id);

    /**
     * 分页查询品牌信息
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<ProductBrand>> page(FeignUtil feignUtil);

    /**
     * 根据productTypeId获取该类型下所有的品牌
     * @param typeId
     * @return
     */
    @RequestMapping(value = "getBrandByTypeId", method = RequestMethod.GET)
    ServiceResult<List<ProductBrand>> getBrandByTypeId(@RequestParam("typeId") Integer typeId);

    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    ServiceResult<Boolean> update(@RequestBody ProductBrand brand);

    /**
     * 审核商家提交的品牌信息，修改 statue 字段
     * @param id
     * @param state
     * @return
     */
    @RequestMapping(value = "audit", method = RequestMethod.GET)
    ServiceResult<Boolean> audit(@RequestParam("id") Integer id,
                                 @RequestParam("state") Integer state,
                                 @RequestParam("userId") Integer userId);

    /**
     * 删除品牌信息，逻辑删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    /**
     * 取出所有可用的品牌
     * @return
     */
    @RequestMapping(value = "listNoPage", method = RequestMethod.GET)
    ServiceResult<List<ProductBrand>> listNoPage();

    /**
     * 获取推荐的品牌
     * @return
     */
    @RequestMapping(value = "getHotBrands", method = RequestMethod.GET)
    ServiceResult<List<ProductBrand>> getHotBrands();

    /**
     * 获取所有品牌，按首字母分组
     * @return
     */
    @RequestMapping(value = "getBrandsLetterGrouping", method = RequestMethod.GET)
    ServiceResult<Map<String, List<ProductBrand>>> getBrandsLetterGrouping();

    /**
     * 当天未审核品牌数
     * @return
     */
    @RequestMapping(value = "getUndoBrand", method = RequestMethod.GET)
    ServiceResult<Integer> getUndoBrand();

}
