package com.yixiekeji.service.product;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productGoods")
@Service(value = "productGoodsService")
public interface IProductGoodsService {

    /**
     * 根据商品ID取其下所有货品
     * @param productId
     * @return
     */
    @RequestMapping(value = "getGoodSByProductId", method = RequestMethod.GET)
    ServiceResult<List<ProductGoods>> getGoodSByProductId(@RequestParam("productId") Integer productId);

    /**
    * 保存货品表
    * @param  productGoods
    * @return
    */
    @RequestMapping(value = "saveProductGoods", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductGoods(ProductGoods productGoods);

    /**
    * 更新货品表
    * @param  productGoods
    * @return
    */
    @RequestMapping(value = "updateProductGoods", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductGoods(ProductGoods productGoods);

    /**
    * 删除货品表
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductGoods", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductGoods(@RequestParam("id") Integer id);

    /**
    * 根据id取得货品表
    * @param productGoodsId
    * @return
    */
    @RequestMapping(value = "getProductGoodsById", method = RequestMethod.GET)
    ServiceResult<ProductGoods> getProductGoodsById(@RequestParam("productGoodsId") Integer productGoodsId);

    /**
     * 分页
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "pageProductGoods", method = RequestMethod.POST)
    ServiceResult<List<ProductGoods>> pageProductGoods(FeignUtil feignUtil);

    @RequestMapping(value = "getProductGoodsByCondition", method = RequestMethod.POST)
    ServiceResult<ProductGoods> getProductGoodsByCondition(Map<String, String> queryMap);

    /**
     * 以给定的货品信息批量更新这些货品<br>
     * @return
     */
    @RequestMapping(value = "updateProductGoodsAll", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductGoodsAll(List<ProductGoods> productgoods);

    /**
     * 新增商品是货品SKU唯一校验
     * @param sellerId
     * @param sku
     * @return
     */
    @RequestMapping(value = "isUnique", method = RequestMethod.GET)
    ServiceResult<Boolean> isUnique(@RequestParam("sellerId") Integer sellerId,
                                    @RequestParam("sku") String sku);

    @RequestMapping(value = "updateProductAndGoods", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductAndGoods(Product product);
}