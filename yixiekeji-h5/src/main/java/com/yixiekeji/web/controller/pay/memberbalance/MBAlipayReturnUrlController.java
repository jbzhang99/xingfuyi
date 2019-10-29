package com.yixiekeji.web.controller.pay.memberbalance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.util.AlipayNotify;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "member/balance/pay")
public class MBAlipayReturnUrlController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    @RequestMapping(value = "alipayResult.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String alipay_result(HttpServletRequest request, HttpServletResponse response,
                                ModelMap dataMap) {

        try {
            //获取支付宝GET过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map<?, ?> requestParams = request.getParameterMap();
            for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
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

            //商户订单号
            String out_trade_no = new String(
                request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
                "UTF-8");

            //交易状态
            String trade_status = new String(
                request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            //交易金额
            String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"),
                "UTF-8");

            boolean verify_result = AlipayNotify.verify(params);

            if (verify_result) {//验证成功
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    // TODO 生产环境请注释以下调用，生产环境使用异步回调接口

                    ServiceResult<Boolean> result = memberBalancePayLogService
                        .payAfter(out_trade_no, total_fee, trade_no);
                    if (!result.getSuccess() || !result.getResult()) {
                        throw new RuntimeException(result.getMessage());
                    }
                    dataMap.put("success", true);
                    dataMap.put("info", "充值成功！");
                }
            } else {
                dataMap.put("success", false);
                dataMap.put("info", "充值失败");
            }
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                dataMap.put("info", "充值失败:" + e.getMessage());
            } else {
                e.printStackTrace();
                dataMap.put("info", "充值失败");
            }
            dataMap.put("success", false);
        }

        return "h5/member/balancepay/payresult";
    }
}
