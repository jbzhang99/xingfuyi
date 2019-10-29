package com.yixiekeji.web.controller.order;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 网单
 *
 */
@Controller
@RequestMapping(value = "admin/order/ordersProduct", produces = "application/json;charset=UTF-8")
public class AdminOrdersProductController extends BaseController {
    @Resource
    private IOrdersProductService ordersProductService;

    @RequestMapping(value = "getOrdersProduct", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<OrdersProduct>> getOrdersProduct(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              Integer orderId) {
        ServiceResult<List<OrdersProduct>> res = ordersProductService
            .getOrdersProductByOId(orderId);
        HttpJsonResult<List<OrdersProduct>> json = new HttpJsonResult<List<OrdersProduct>>();
        json.setRows(res.getResult());
        json.setTotal(res.getResult().size());
        return json;
    }
}
