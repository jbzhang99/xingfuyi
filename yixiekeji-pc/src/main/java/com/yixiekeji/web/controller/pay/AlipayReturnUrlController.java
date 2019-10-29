package com.yixiekeji.web.controller.pay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.util.AlipayNotify;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.web.controller.BaseController;

@Controller
public class AlipayReturnUrlController extends BaseController {

    @Resource
    private IOrdersService       ordersService;

    @Resource
    private IProductFrontService productFrontService;

    @RequestMapping(value = "/alipay_result.html", method = RequestMethod.GET)
    public String alipay_result(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> stack) {

        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //            for (Map.Entry<String, String> entry : params.entrySet()) {
            //                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
            //            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号
            String out_trade_no = new String(
                request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //            System.out.println("---out_trade_no---" + out_trade_no);

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
                "UTF-8");
                //            System.out.println("---trade_no---" + trade_no);

            //交易状态
            String trade_status = new String(
                request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                //            System.out.println("---trade_status---" + trade_status);

            //交易金额
            String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),
                "UTF-8");
                //            System.out.println("---total_fee---" + total_fee);

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    // 调用订单支付接口
                    // TODO 生产环境请注释以下调用，生产环境使用异步回调接口
                    ServiceResult<Boolean> orderPayResult = ordersService.orderPayAfter(
                        out_trade_no, total_fee, Orders.PAYMENT_CODE_PCALIPAY,
                        Orders.PAYMENT_NAME_PCALIPAY, trade_no, params.toString());
                }

                //该页面可做页面美工编辑
                //                response.getWriter().println("验证成功");
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //该页面可做页面美工编辑
                //                response.getWriter().println("验证失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServiceResult<List<Product>> resultProduct = productFrontService.getProductsListCart(0, 10);
        stack.put("products", resultProduct.getResult());

        return "front/order/linepay";
    }
}
