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
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.vo.product.FrontProductCateVO;
import com.yixiekeji.vo.product.ProductCateVO;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productCate")
@Service(value = "productCateService")
public interface IProductCateService {
    /**
    * 保存商品分类
    * @param  productCate
    * @return
    */
    @RequestMapping(value = "saveProductCate", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductCate(ProductCate productCate);

    /**
    * 更新商品分类
    * @param  productCate
    * @return
    */
    @RequestMapping(value = "updateProductCate", method = RequestMethod.POST)
    ServiceResult<Boolean> updateProductCate(ProductCate productCate);

    /**
    * 删除商品分类
    * @param  id
    * @return
    */
    @RequestMapping(value = "delProductCate", method = RequestMethod.GET)
    ServiceResult<Boolean> delProductCate(@RequestParam("id") Integer id);

    /**
    * 根据id取得商品分类
    * @param productCateId
    * @return
    */
    @RequestMapping(value = "getProductCateById", method = RequestMethod.GET)
    ServiceResult<ProductCate> getProductCateById(@RequestParam("productCateId") Integer productCateId);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "pageProductCate", method = RequestMethod.POST)
    ServiceResult<List<ProductCateVO>> pageProductCate(FeignUtil feignUtil);

    /**
     * 根据pid查询商品分类信息
     * @param pid
     * @return
     */
    @RequestMapping(value = "getByPid", method = RequestMethod.GET)
    ServiceResult<List<ProductCate>> getByPid(@RequestParam("pid") Integer pid);

    /**
     * 商家发布商品时根据商家id获取商家一级分类信息
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "getCateBySellerId", method = RequestMethod.GET)
    ServiceResult<List<ProductCate>> getCateBySellerId(@RequestParam("sellerId") Integer sellerId);

    /**
     * 商家发布商品时根据商家id和上级分类id获取商家下级分类信息
     * @param sellerId
     * @param pid
     * @return
     */
    @RequestMapping(value = "getCateBySellerIdAndPid", method = RequestMethod.GET)
    ServiceResult<List<ProductCate>> getCateBySellerIdAndPid(@RequestParam("sellerId") Integer sellerId,
                                                             @RequestParam("pid") Integer pid);

    /**
     * 根据商品分类id，获取分类路径名称 例如：数码办公 >电脑整机 >平板电脑
     * @param productCateId
     * @return
     */
    @RequestMapping(value = "getCatePathStrById", method = RequestMethod.GET)
    ServiceResult<String> getCatePathStrById(@RequestParam("productCateId") Integer productCateId);

    /**
     * 将该商家申请置为审核通过
     * @param id
     */
    @RequestMapping(value = "getSellerManageCate", method = RequestMethod.GET)
    SellerManageCate getSellerManageCate(@RequestParam("id") Integer id);

    @RequestMapping(value = "updateSellerManageCate", method = RequestMethod.POST)
    Boolean updateSellerManageCate(SellerManageCate cate);

    //
    /**
     * 根据pid查询商品分类信息
     * @param pid
     * @param seller
     * @return
     */
    @RequestMapping(value = "getByPidAndSeller", method = RequestMethod.GET)
    ServiceResult<List<ProductCateVO>> getByPidAndSeller(@RequestParam("pid") Integer pid,
                                                         @RequestParam("seller") Integer seller);

    /**
     * 取得所有显示状态的商品分类
     * @param  map
     * @return
     */
    @RequestMapping(value = "getProductCateList", method = RequestMethod.POST)
    ServiceResult<List<FrontProductCateVO>> getProductCateList(Map<String, Object> queryMap);

    @RequestMapping(value = "getProductCate", method = RequestMethod.POST)
    ServiceResult<List<ProductCate>> getProductCate(Map<String, Object> param);
}