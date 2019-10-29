package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductAttr;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productAttr")
@Service(value = "productAttrService")
public interface IProductAttrService {
    /**
    * 保存商品对应属性表
    * @param  productAttr
    * @return
    */
    @RequestMapping(value = "saveProductAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductAttr(ProductAttr productAttr);

    /**
    * 更新商品对应属性表
    * @param  productAttr
    * @return
    */
    @RequestMapping(value = "updateProductAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductAttr(ProductAttr productAttr);

    /**
    * 删除商品对应属性表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductAttr", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductAttr(@RequestParam("id") Integer id);

    /**
    * 根据id取得商品对应属性表
    * @param productAttrId
    * @return
    */
    @RequestMapping(value = "getProductAttrById", method = RequestMethod.GET)
    ServiceResult<ProductAttr> getProductAttrById(@RequestParam("productAttrId") Integer productAttrId);

    /**
     * 根据商品id查询商品属性
     * @param productId
     * @return
     */
    @RequestMapping(value = "getProductAttrByProductId", method = RequestMethod.GET)
    ServiceResult<List<ProductAttr>> getProductAttrByProductId(@RequestParam("productId") Integer productId);

}