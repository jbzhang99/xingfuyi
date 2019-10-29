package com.yixiekeji.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.dto.ProductDayDto;
import com.yixiekeji.entity.order.OrdersProduct;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "ordersProduct")
@Service(value = "ordersProductService")
public interface IOrdersProductService {

    /**
     * 根据id取得网单表
     * @param  ordersProductId
     * @return
     */
    @RequestMapping(value = "getOrdersProductById", method = RequestMethod.GET)
    ServiceResult<OrdersProduct> getOrdersProductById(@RequestParam("ordersProductId") Integer ordersProductId);

    /**
     * 保存网单表
     * @param  ordersProduct
     * @return
     */
    @RequestMapping(value = "saveOrdersProduct", method = RequestMethod.POST)
    ServiceResult<Integer> saveOrdersProduct(OrdersProduct ordersProduct);

    /**
    * 更新网单表
    * @param  ordersProduct
    * @return
    */
    @RequestMapping(value = "updateOrdersProduct", method = RequestMethod.POST)
    ServiceResult<Integer> updateOrdersProduct(OrdersProduct ordersProduct);

    /**
     * 根据订单号获取对应的网单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "getOrdersProductByOId", method = RequestMethod.GET)
    ServiceResult<List<OrdersProduct>> getOrdersProductByOId(@RequestParam("orderId") Integer orderId);

    /**
     * 根据id 取得网单信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrdersProductWithImgById", method = RequestMethod.GET)
    ServiceResult<OrdersProduct> getOrdersProductWithImgById(@RequestParam("orderProductId") Integer orderProductId);

    /**
     * 统计商品每天销量
     * @param queryMap
     * @return
     */
    @RequestMapping(value = "getProductDayDto", method = RequestMethod.POST)
    ServiceResult<List<ProductDayDto>> getProductDayDto(Map<String, String> queryMap);

}