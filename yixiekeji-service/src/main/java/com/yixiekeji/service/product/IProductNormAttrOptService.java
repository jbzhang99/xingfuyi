package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductNormAttrOpt;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productNormAttrOpt")
@Service(value = "productNormAttrOptService")
public interface IProductNormAttrOptService {

    /**
     * 根据id取得商品选定的规格属性(保存商品插入，暂时不用)
     * @param  productNormAttrOptId
     * @return
     */
    @RequestMapping(value = "getProductNormAttrOptById", method = RequestMethod.GET)
    ServiceResult<ProductNormAttrOpt> getProductNormAttrOptById(@RequestParam("productNormAttrOptId") Integer productNormAttrOptId);

    /**
     * 保存商品选定的规格属性(保存商品插入，暂时不用)
     * @param  productNormAttrOpt
     * @return
     */
    @RequestMapping(value = "saveProductNormAttrOpt", method = RequestMethod.POST)
    ServiceResult<Integer> saveProductNormAttrOpt(ProductNormAttrOpt productNormAttrOpt);

    /**
    * 更新商品选定的规格属性(保存商品插入，暂时不用)
    * @param  productNormAttrOpt
    * @return
    */
    @RequestMapping(value = "updateProductNormAttrOpt", method = RequestMethod.POST)
    ServiceResult<Integer> updateProductNormAttrOpt(ProductNormAttrOpt productNormAttrOpt);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<ProductNormAttrOpt>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}