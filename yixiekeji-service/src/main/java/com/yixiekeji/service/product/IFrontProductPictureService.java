package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductPicture;

/**
 * 商城图片处理
 *                       
 * @Filename: IFrontProductPictureService.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "frontProductPicture")
@Service(value = "frontProductPictureService")
public interface IFrontProductPictureService {

    /**
     * 根据id取得商品对应图片表
     * @param  productPictureId
     * @return
     */
    @RequestMapping(value = "getProductPictureById", method = RequestMethod.GET)
    ServiceResult<ProductPicture> getProductPictureById(@RequestParam("productPictureId") Integer productPictureId);

    /**
     * 根据商品ID取得商品下面所有的图片
     * @param productId
     * @return
     */
    @RequestMapping(value = "getByProductIds", method = RequestMethod.GET)
    ServiceResult<List<ProductPicture>> getByProductIds(@RequestParam("productId") Integer productId);

    /**
     * 根据商品ID取得获取商品的主图
     * @param productId
     * @return
     */
    @RequestMapping(value = "getproductLead", method = RequestMethod.GET)
    ServiceResult<ProductPicture> getproductLead(@RequestParam("productId") Integer productId);

}