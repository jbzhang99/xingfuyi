package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.util.FeignProjectUtil;

/**
 * 商品服务
 *                       
 * @Filename: IProductService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "product")
@Service(value = "productService")
public interface IProductService {

    /**
     * 获取cateId分类下的推荐商品
     * @param cateId
     * @return
     */
    @RequestMapping(value = "getByCateIdTop", method = RequestMethod.GET)
    ServiceResult<List<Product>> getByCateIdTop(@RequestParam("cateId") Integer cateId,
                                                @RequestParam("limit") Integer limit);

    /**
     * 获取size个商家推荐商品
     * @param sellerId 商家ID
     * @param size 获取条数
     * @return
     */
    @RequestMapping(value = "getSellerRecommendProducts", method = RequestMethod.GET)
    ServiceResult<List<Product>> getSellerRecommendProducts(@RequestParam("sellerId") Integer sellerId,
                                                            @RequestParam("size") Integer size);

    /**
     * 获取size个商家新品
     * @param sellerId 商家ID
     * @param size 获取条数
     * @return
     */
    @RequestMapping(value = "getSellerNewProducts", method = RequestMethod.GET)
    ServiceResult<List<Product>> getSellerNewProducts(@RequestParam("sellerId") Integer sellerId,
                                                      @RequestParam("size") Integer size);

    /**
     * 查询商家所有在售商品（商家列表页）
     * @param sellerId
     * @param sort 0:默认；1、销量从大到小；2、评价从多到少；3、价格从低到高；4、价格从高到低
     * @param sellerCateId
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductForSellerList", method = RequestMethod.POST)
    ServiceResult<List<Product>> getProductForSellerList(@RequestParam("sellerId") Integer sellerId,
                                                         @RequestParam("size") Integer sort,
                                                         @RequestParam("sellerCateId") Integer sellerCateId,
                                                         @RequestBody PagerInfo pager);

    /**
     * 获取size个推荐商品
     * @param size
     * @return
     */
    @RequestMapping(value = "getRecommendProducts", method = RequestMethod.GET)
    ServiceResult<List<Product>> getRecommendProducts(@RequestParam("size") Integer size);

    /**
     * 保存商品信息
     * @param map 
     * @param product 商品
     * @param productPictureList 商品图片
     * @param productAttrList 商品属性
     * @return
     */
    @RequestMapping(value = "saveProduct", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProduct(FeignProjectUtil feignProjectUtil);

    /**
    * 更新商品表
    * @param  product
    * @return
    */
    @RequestMapping(value = "updateProduct", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProduct(FeignProjectUtil feignProjectUtil);

    /**
    * 删除商品表
    * @param  id
     * @param integer 
    * @return
    */
    @RequestMapping(value = "delProduct", method = RequestMethod.GET)
    ServiceResult<Boolean> delProduct(@RequestParam("id") Integer id,
                                      @RequestParam("sellerId") Integer sellerId);

    /**
    * 根据id取得商品表（从写库读取）
    * @param productId
    * @return
    */
    @RequestMapping(value = "getProductById", method = RequestMethod.GET)
    ServiceResult<Product> getProductById(@RequestParam("productId") Integer productId);

    /**
    * 分页
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageProduct", method = RequestMethod.POST)
    ServiceResult<List<Product>> pageProduct(FeignUtil feignUtil);

    /**
     * 以商家id获取所有商品
     * @param integer
     * @return
     */
    @RequestMapping(value = "getProductsBySellerId", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductsBySellerId(@RequestParam("sellerid") Integer sellerid);

    @RequestMapping(value = "updateByIds", method = RequestMethod.POST)
    ServiceResult<Integer> updateByIds(FeignProjectUtil feignProjectUtil);

    /**
     * 根据商品ID修改状态
     * @param id
     * @param state
     * @return
     */
    @RequestMapping(value = "updateProductState", method = RequestMethod.GET)
    ServiceResult<Boolean> updateProductState(@RequestParam("id") Integer id,
                                              @RequestParam("state") Integer state);

    /**
     * 根据商品ID修改推荐状态
     * @param id
     * @param isTop
     * @return
     */
    @RequestMapping(value = "updateProductRecommend", method = RequestMethod.GET)
    ServiceResult<Boolean> updateProductRecommend(@RequestParam("id") Integer id,
                                                  @RequestParam("id") Integer isTop);

    /**
     * 以商品id字符串获取商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "getProductsByIds", method = RequestMethod.POST)
    ServiceResult<List<Product>> getProductsByIds(@RequestBody List<Integer> ids);

    /**
     * 查询最大的商品
     * @return
     */
    @RequestMapping(value = "getProductByMax", method = RequestMethod.GET)
    ServiceResult<Product> getProductByMax();

    /**
     * 新增商品时唯一性校验
     * @param sellerId
     * @param productCode
     * @param productId 商品ID，新增时传0，修改时传实际ID
     * @return
     */
    @RequestMapping(value = "isUnique", method = RequestMethod.GET)
    ServiceResult<Boolean> isUnique(@RequestParam("sellerId") Integer sellerId,
                                    @RequestParam("productCode") String productCode,
                                    @RequestParam("productId") Integer productId);

    /**
     * 获取销量最好的size个商品
     * @param size
     * @return
     */
    @RequestMapping(value = "getSalesProducts", method = RequestMethod.GET)
    ServiceResult<List<Product>> getSalesProducts(@RequestParam("size") Integer size);
}