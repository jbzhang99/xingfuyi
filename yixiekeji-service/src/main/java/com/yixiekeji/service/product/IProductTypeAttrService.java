package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.vo.product.ProductTypeAttrVO;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productTypeAttr")
@Service(value = "productTypeAttrService")
public interface IProductTypeAttrService {
    /**
    * 保存商品分类属性表
    * @param  productTypeAttr
    * @return
    */
    @RequestMapping(value = "saveProductTypeAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductTypeAttr(ProductTypeAttr productTypeAttr);

    /**
    * 更新商品分类属性表
    * @param  productTypeAttr
    * @return
    */
    @RequestMapping(value = "updateProductTypeAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductTypeAttr(ProductTypeAttr productTypeAttr);

    /**
    * 删除商品分类属性表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductTypeAttr", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductTypeAttr(@RequestParam("id") Integer id);

    /**
    * 根据id取得商品分类属性表
    * @param productTypeAttrId
    * @return
    */
    @RequestMapping(value = "getProductTypeAttrById", method = RequestMethod.GET)
    ServiceResult<ProductTypeAttr> getProductTypeAttrById(@RequestParam("productTypeAttrId") Integer productTypeAttrId);

    /**
     * 根据商品类型id查询商品类型属性
      * @param productTypeId
     * @return
     */
    @RequestMapping(value = "getProductTypeAttrByTypeId", method = RequestMethod.GET)
    ServiceResult<List<ProductTypeAttr>> getProductTypeAttrByTypeId(@RequestParam("productTypeId") Integer productTypeId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageProductTypeAttr", method = RequestMethod.POST)
    ServiceResult<List<ProductTypeAttrVO>> pageProductTypeAttr(FeignUtil feignUtil);
}