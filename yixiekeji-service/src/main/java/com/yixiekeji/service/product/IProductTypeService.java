package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.util.FeignProjectUtil;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productType")
@Service(value = "productTypeService")
public interface IProductTypeService {
    /**
    * 保存商品类型表
    * @param  productType
    * @return
    */
    @RequestMapping(value = "saveProductType", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductType(ProductType productType);

    /**
     * 保存商品类型
     * @param map key:type attr
     * @return
     */
    @RequestMapping(value = "saveProductTypeMap", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductTypeMap(FeignProjectUtil feignProjectUtil);

    /**
    * 更新商品类型表
    * @param  productType
    * @return
    */
    @RequestMapping(value = "updateProductType", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductType(ProductType productType);

    /**
     * 更新商品类型
     * @param map key:type attr
     * @return
     */
    @RequestMapping(value = "updateProductTypeMap", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductTypeMap(FeignProjectUtil feignProjectUtil);

    /**
    * 删除商品类型表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductType", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductType(@RequestParam("id") Integer id);

    /**
    * 根据id取得商品类型表
    * @param productTypeId
    * @return
    */
    @RequestMapping(value = "getProductTypeById", method = RequestMethod.GET)
    ServiceResult<ProductType> getProductTypeById(@RequestParam("productTypeId") Integer productTypeId);

    /**
     * 根据类型id获取属性信息
     * @param productTypeId
     * @return
     */
    @RequestMapping(value = "getAttrByTypeId", method = RequestMethod.GET)
    ServiceResult<List<ProductTypeAttr>> getAttrByTypeId(@RequestParam("productTypeId") Integer productTypeId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageProductType", method = RequestMethod.POST)
    ServiceResult<List<ProductType>> pageProductType(FeignUtil feignUtil);

}
