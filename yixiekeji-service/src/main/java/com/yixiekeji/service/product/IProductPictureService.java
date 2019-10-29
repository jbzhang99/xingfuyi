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

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productPicture")
@Service(value = "productPictureService")
public interface IProductPictureService {
    /**
    * 保存商品对应图片表
    * @param  productPicture
    * @return
    */
    @RequestMapping(value = "saveProductPicture", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductPicture(ProductPicture productPicture);

    /**
    * 更新商品对应图片表
    * @param  productPicture
    * @return
    */
    @RequestMapping(value = "updateProductPicture", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductPicture(ProductPicture productPicture);

    /**
    * 删除商品对应图片表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductPicture", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductPicture(@RequestParam("id") Integer id);

    /**
    * 根据id取得商品对应图片表
    * @param productPictureId
    * @return
    */
    @RequestMapping(value = "getProductPictureById", method = RequestMethod.GET)
    ServiceResult<ProductPicture> getProductPictureById(@RequestParam("productPictureId") Integer productPictureId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageProductPicture", method = RequestMethod.POST)
    ServiceResult<List<ProductPicture>> pageProductPicture(FeignUtil feignUtil);

    @RequestMapping(value = "getProductPictureByProductId", method = RequestMethod.GET)
    ServiceResult<List<ProductPicture>> getProductPictureByProductId(@RequestParam("productId") Integer productId);
}