package com.yixiekeji.web.controller.pay.memberbalance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.config.AlipayConfig;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "memberBalance/pay")
public class AlipayNotifyController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    @RequestMapping(value = "alipayNotify.html", method = { RequestMethod.GET, RequestMethod.POST })
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {

        try {
            //获取支付宝POST过来反馈信息
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
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
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
            String total_fee = new String(
                request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            boolean flag = AlipaySignature.rsaCheckV1(params, EjavashopConfig.ALIPAY_APP_PUBLIC_KEY,
                AlipayConfig.input_charset, AlipayConfig.sign_type_rsa2);

            //if (AlipayNotify.verify(params)) {//验证成功
            if (flag) {
                if (trade_status.equals("TRADE_SUCCESS")) {
                    log.debug("支付成功");
                    ServiceResult<Boolean> result = memberBalancePayLogService
                        .payAfter(out_trade_no, total_fee, trade_no);
                    if (!result.getSuccess() || !result.getResult()) {
                        throw new RuntimeException(result.getMessage());
                    }
                } else {
                    response.getWriter().println("fail");
                }
                response.getWriter().println("success");
            } else {
                response.getWriter().println("fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().println("fail");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
