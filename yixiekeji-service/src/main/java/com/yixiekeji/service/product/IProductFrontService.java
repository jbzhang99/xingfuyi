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
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.util.FeignObjectUtil;

/**
 * 商城所有关于商品的查询
 *                       
 * @Filename: IProductFrontService.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productFront")
@Service(value = "productFrontService")
public interface IProductFrontService {

    /**
     * 根据商品分类查询商品
     * @param cateId 商品分类ID
     * @param mapCondition 传递的参数
     * @param stack 传出参数map集合
     */
    @RequestMapping(value = "getProducts", method = RequestMethod.POST)
    void getProducts(@RequestParam("cateId") Integer cateId,
                     @RequestBody FeignObjectUtil feignObjectUtil);

    /**
     * 根据分类查询列表页推荐的头部信息
     * @param cateId 分类Id
     * @return
     */
    @RequestMapping(value = "getProductListByCateIdTop", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductListByCateIdTop(@RequestParam("cateId") int cateId);

    /**
     * 根据分类查询列表页推荐的左边商品
     * @param cateId 分类Id
     * @return
     */
    @RequestMapping(value = "getProductListByCateIdLeft", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductListByCateIdLeft(@RequestParam("cateId") int cateId);

    /**
     * 查询二级分类下三级分类的商品，默认取5个
     * @param cateId 分类Id
     * @return
     */
    @RequestMapping(value = "getProductListByCateId2", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductListByCateId2(@RequestParam("cateId") int cateId);

    /**
     * 搜索页面推荐商品信息头部
     * @return
     */
    @RequestMapping(value = "getProductSearchByTop", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductSearchByTop();

    /**
     * 搜索页面推荐商品信息左部
     * @return
     */
    @RequestMapping(value = "getProductSearchByLeft", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductSearchByLeft();

    /**
     * 用户中心首页推荐商品
     * @return
     */
    @RequestMapping(value = "getProductMemberByTop", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductMemberByTop(@RequestParam("number") int number);

    /**
     * 根据商家商品分类查询商家列表页 商品信息
     * @param start 
     * @param size
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @param sort 排序  0:默认；1、价格从低到高；2、价格从高到底；3、销量从高到底；4、销量从低到高；5、上架时间新旧；6、上架时间旧新
     * @return
     */
    @RequestMapping(value = "getProductListBySellerCateId", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductListBySellerCateId(@RequestParam("start") int start,
                                                              @RequestParam("cateId") int size,
                                                              @RequestParam("cateId") int cateId,
                                                              @RequestParam("sellerId") int sellerId,
                                                              @RequestParam("sort") int sort);

    /**
     * 根据商家商品分类统计商家商品
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @return
     */
    @RequestMapping(value = "getProductListBySellerCateIdCount", method = RequestMethod.GET)
    ServiceResult<Integer> getProductListBySellerCateIdCount(@RequestParam("cateId") int cateId,
                                                             @RequestParam("sellerId") int sellerId);

    /**
     * 根据品牌查询商品
     * @param brandId 品牌ID
     * @param sort 0:默认；1、销量从大到小；2、评价从多到少；3、价格从低到高；4、价格从高到低
     * @param doSelf 自营非自营
     * @param store 库存
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductsByBrandId", method = RequestMethod.POST)
    ServiceResult<List<Product>> getProductsByBrandId(@RequestParam("brandId") Integer brandId,
                                                      @RequestParam("sort") Integer sort,
                                                      @RequestParam("doself") Integer doself,
                                                      @RequestParam("store") Integer store,
                                                      @RequestBody PagerInfo pager);

    /**
     * 根据品牌查询销量最好的商品
     * @param brandId 品牌ID
     * @return
     */
    @RequestMapping(value = "getProductsByBrandIdTop", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductsByBrandIdTop(@RequestParam("brandId") Integer brandId);

    /**
     * 根据商品分类路径查询商品信息
     * @param productCatePath 分类路径如：/1/2
     * @param sort 排序：0-默认，1-价格升序，2-价格降序，3-销量降序，4-评价降序
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getProductByPath", method = RequestMethod.POST)
    ServiceResult<List<Product>> getProductByPath(@RequestParam("productCatePath") String productCatePath,
                                                  @RequestParam("sort") int sort,
                                                  @RequestBody PagerInfo pager);

    /**
     * 购物车推荐商品（购买了该商品的用户还购买了）
     * @param cateId 分类ID
     * @param number 数量
     * @return
     */
    @RequestMapping(value = "getProductsListCart", method = RequestMethod.GET)
    ServiceResult<List<Product>> getProductsListCart(@RequestParam("cateId") int cateId,
                                                     @RequestParam("number") int number);

    /**
     * 根据分类的名称查询分类
     * @param name
     * @return
     */
    @RequestMapping(value = "getProductCateByName", method = RequestMethod.GET)
    ServiceResult<ProductCate> getProductCateByName(@RequestParam("name") String name);

    /**
     * 根据品牌的名称查询
     * @param name
     * @return
     */
    @RequestMapping(value = "getProductBrandByName", method = RequestMethod.GET)
    ServiceResult<ProductBrand> getProductBrandByName(@RequestParam("name") String name);

    /**
    * 根据id取得商品表（从读库读取）
    * @param productId
    * @return
    */
    @RequestMapping(value = "getProductById", method = RequestMethod.GET)
    ServiceResult<Product> getProductById(@RequestParam("productId") Integer productId);

    /**
     * 根据分类ID查询分类信息
     * @param cateId
     * @return
     */
    @RequestMapping(value = "getProductCateById", method = RequestMethod.GET)
    ServiceResult<ProductCate> getProductCateById(@RequestParam("cateId") Integer cateId);

    /**
     * 取得id的分类下面的子节点信息
     * @param pid
     * @return
     */
    @RequestMapping(value = "getProductCateByPid", method = RequestMethod.GET)
    ServiceResult<List<ProductCate>> getProductCateByPid(@RequestParam("pid") Integer pid);

    /**
     * 根据类型ID查询类型信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getProductTypeById", method = RequestMethod.GET)
    ServiceResult<ProductType> getProductTypeById(@RequestParam("id") Integer id);

    /**
     * 根据品牌的ID集合查询出品牌
     * @param brandIds
     * @return
     */
    @RequestMapping(value = "getProductBrandByIds", method = RequestMethod.GET)
    ServiceResult<List<ProductBrand>> getProductBrandByIds(@RequestParam("brandIds") String brandIds);

    /**
     * 根据类型ID查询类型下关联的查询属性
     * @param productTypeId
     * @return
     */
    @RequestMapping(value = "getByTypeIdAndQuery", method = RequestMethod.GET)
    ServiceResult<List<ProductTypeAttr>> getByTypeIdAndQuery(@RequestParam("productTypeId") Integer productTypeId);

    /**
     * 根据品牌ID查询品牌信息
     * @param brandId
     * @return
     */
    @RequestMapping(value = "getProductBrandById", method = RequestMethod.GET)
    ServiceResult<ProductBrand> getProductBrandById(@RequestParam("brandId") int brandId);
}
