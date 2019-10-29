package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.util.FeignObjectUtil;

/**
 * 商品咨询管理接口
 * 
 * @Filename: IProductAskService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productAsk")
@Service(value = "productAskService")
public interface IProductAskService {

    /**
     * 根据id取得商品咨询管理
     * @param  productAskId
     * @return
     */
    @RequestMapping(value = "getProductAskById", method = RequestMethod.GET)
    ServiceResult<ProductAsk> getProductAskById(@RequestParam("productAskId") Integer productAskId);

    /**
    * 更新商品咨询管理
    * @param  productAsk
    * @return
    */
    @RequestMapping(value = "updateProductAsk", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductAsk(ProductAsk productAsk);

    /**
     * 根据查询条件取所有的咨询，只返回评论表内容
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductAsks", method = RequestMethod.POST)
    public ServiceResult<List<ProductAsk>> getProductAsks(FeignUtil feignUtil);

    /**
     * 根据查询条件取所有的咨询，一起返回该商品的名称、所属商家名称
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductAsksWithInfo", method = RequestMethod.POST)
    public ServiceResult<List<ProductAsk>> getProductAsksWithInfo(FeignUtil feignUtil);

    /**
     * 保存商品咨询管理
     * @param productAsk
     * @param request
     * @return
     */
    @RequestMapping(value = "saveProductAsk", method = RequestMethod.POST)
    ServiceResult<ProductAsk> saveProductAsk(FeignObjectUtil feignObjectUtil);

}