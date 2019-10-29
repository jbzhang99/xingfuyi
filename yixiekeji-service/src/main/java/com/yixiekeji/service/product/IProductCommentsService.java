package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductComments;

/**
 * 商品评论管理接口
 * 
 * @Filename: IProductCommentsService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productComments")
@Service(value = "productCommentsService")
public interface IProductCommentsService {

    /**
     * 根据id取得商品评论管理
     * @param  productCommentsId
     * @return
     */
    @RequestMapping(value = "getProductCommentsById", method = RequestMethod.GET)
    ServiceResult<ProductComments> getProductCommentsById(@RequestParam("productCommentsId") Integer productCommentsId);

    /**
     * 审核商品评论
     * @param id
     * @param state
     * @return
     */
    @RequestMapping(value = "auditProductComments", method = RequestMethod.GET)
    public ServiceResult<Boolean> auditProductComments(@RequestParam("id") Integer id,
                                                       @RequestParam("state") Integer state);

    /**
     * 根据查询条件查询商品评论
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductComments", method = RequestMethod.POST)
    public ServiceResult<List<ProductComments>> getProductComments(FeignUtil feignUtil);

    /**
     * 根据查询条件查询商品评论，附加查询了商品名称和所属商家名称
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getProductCommentsWithInfo", method = RequestMethod.POST)
    public ServiceResult<List<ProductComments>> getProductCommentsWithInfo(FeignUtil feignUtil);

    /**
     * 根据订单编号及产品id,货品ID   取得商品评论  1个订单可以有多个产品评论
     * @param orderSn
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "getProductCommentsByOrderSn", method = RequestMethod.GET)
    public ServiceResult<ProductComments> getProductCommentsByOrderSn(@RequestParam("orderSn") String orderSn,
                                                                      @RequestParam("productId") String productId,
                                                                      @RequestParam("productGoodsId") String productGoodsId,
                                                                      @RequestParam("memberId") Integer memberId);

    /**
     * 保存商品评论管理
     * @param productComments
     * @param ordersProductId 
     * @param request
     * @return
     */
    @RequestMapping(value = "saveProductComments", method = RequestMethod.POST)
    ServiceResult<Boolean> saveProductComments(@RequestBody ProductComments productComments,
                                               @RequestParam("ordersProductId") Integer ordersProductId);

    /**
    * 更新商品评论管理
    * @param  productComments
    * @return
    */
    @RequestMapping(value = "updateProductComments", method = RequestMethod.POST)
    ServiceResult<Integer> updateProductComments(@RequestBody ProductComments productComments);

    /**
     * 根据查询条件取所有的评论 单品页使用 
     * @param request
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCommentsByCondition", method = RequestMethod.POST)
    public ServiceResult<List<ProductComments>> getCommentsByCondition(FeignUtil feignUtil);
}