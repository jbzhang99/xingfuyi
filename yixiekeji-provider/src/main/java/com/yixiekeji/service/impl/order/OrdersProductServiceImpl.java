package com.yixiekeji.service.impl.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dto.ProductDayDto;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.model.order.OrdersProductModel;
import com.yixiekeji.service.order.IOrdersProductService;

@RestController
public class OrdersProductServiceImpl implements IOrdersProductService {
    private static Logger      log = LoggerFactory.getLogger(OrdersProductServiceImpl.class);

    @Resource(name = "ordersProductModel")
    private OrdersProductModel ordersProductModel;

    /**
    * 根据id取得网单表
    * @param  ordersProductId
    * @return
    */
    @Override
    public ServiceResult<OrdersProduct> getOrdersProductById(@RequestParam("ordersProductId") Integer ordersProductId) {
        ServiceResult<OrdersProduct> result = new ServiceResult<OrdersProduct>();
        try {
            result.setResult(ordersProductModel.getOrdersProductById(ordersProductId));
        } catch (Exception e) {
            log.error("根据id[" + ordersProductId + "]取得网单表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + ordersProductId + "]取得网单表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存网单表
     * @param  ordersProduct
     * @return
     */
    @Override
    public ServiceResult<Integer> saveOrdersProduct(@RequestBody OrdersProduct ordersProduct) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(ordersProductModel.saveOrdersProduct(ordersProduct));
        } catch (Exception e) {
            log.error("保存网单表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存网单表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新网单表
    * @param  ordersProduct
    * @return
    */
    @Override
    public ServiceResult<Integer> updateOrdersProduct(@RequestBody OrdersProduct ordersProduct) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(ordersProductModel.updateOrdersProduct(ordersProduct));
        } catch (Exception e) {
            log.error("更新网单表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新网单表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<OrdersProduct>> getOrdersProductByOId(@RequestParam("orderId") Integer orderId) {
        ServiceResult<List<OrdersProduct>> serviceResult = new ServiceResult<List<OrdersProduct>>();
        try {
            serviceResult.setResult(ordersProductModel.getOrdersProductByOId(orderId));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error(
                "[OrdersProductService][getOrdersProductByOId]根据订单号获取对应的网单:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[OrdersProductService][getOrdersProductByOId]根据订单号获取对应的网单:", e);
        }
        return serviceResult;

    }

    /**
     * 根据id 取得网单信息
     * @param request
     * @return
     */
    @Override
    public ServiceResult<OrdersProduct> getOrdersProductWithImgById(@RequestParam("orderProductId") Integer orderProductId) {
        ServiceResult<OrdersProduct> serviceResult = new ServiceResult<OrdersProduct>();
        try {
            serviceResult.setResult(ordersProductModel.getOrdersProductWithImgById(orderProductId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[ordersProductService][getOrdersProductWithImgById]获取网单信息时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ordersProductService][getOrdersProductWithImgById]获取网单信息时发生异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<List<ProductDayDto>> getProductDayDto(@RequestBody Map<String, String> queryMap) {
        ServiceResult<List<ProductDayDto>> serviceResult = new ServiceResult<List<ProductDayDto>>();
        try {
            serviceResult.setResult(ordersProductModel.getProductDayDto(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[ordersProductService][getProductDayDto]统计商品每日销量时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ordersProductService][getProductDayDto]统计商品每日销量时发生异常:", e);
        }
        return serviceResult;
    }
}