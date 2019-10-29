package com.yixiekeji.web.controller.pay;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "/wx")
public class WXPayReturnUrlController extends BaseController {

    @Resource
    private IOrdersService ordersService;

    @RequestMapping(value = "/returnUrl.html")
    public @ResponseBody HttpJsonResult<Boolean> returnUrl(HttpServletResponse response,
                                                           HttpServletRequest request,
                                                           String orderNo) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (StringUtil.isEmpty(orderNo, true)) {
            log.error("微信支付，订单号为空");
            jsonResult.setData(false);
            return jsonResult;
        }
        Member member = WebFrontSession.getLoginedUser(request,response);
        if (member == null) {
            log.error("用户session过期");
            jsonResult.setData(false);
            return jsonResult;
        }

        ServiceResult<List<Orders>> orderResult = ordersService.getOrdersByOrderPsn(orderNo);
        if (!orderResult.getSuccess()) {
            log.error("获取订单失败");
            jsonResult.setData(false);
            return jsonResult;
        }

        List<Orders> orderList = orderResult.getResult();
        if (null == orderList || orderList.size() == 0) {
            log.error("获取订单失败");
            jsonResult.setData(false);
            return jsonResult;
        }
        for (Orders order : orderList) {
            if (!order.getMemberId().equals(member.getId())) {
                log.error("不能查看别人的订单");
                jsonResult.setData(false);
                return jsonResult;
            }
            if (order.getPaymentStatus().intValue() == Orders.PAYMENT_STATUS_1) {
                log.error("订单支付成功");
                jsonResult.setData(true);
                return jsonResult;
            }
        }

        jsonResult.setData(false);
        return jsonResult;
    }

    @RequestMapping(value = "/returnUrlSuccess.html")
    public String returnUrl() {
        return "front/order/linepay";
    }

}
