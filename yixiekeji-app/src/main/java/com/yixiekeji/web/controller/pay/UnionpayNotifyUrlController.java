package com.yixiekeji.web.controller.pay;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.web.controller.BaseController;
import com.unionpay.acp.LogUtil;
import com.unionpay.acp.SDKConstants;
import com.unionpay.acp.SDKUtil;

@Controller
public class UnionpayNotifyUrlController extends BaseController {

    @Resource
    private IOrdersService ordersService;

    @RequestMapping(value = "/unionpay_result_notify.html", method = RequestMethod.POST)
    public void alipay_result_notify(HttpServletRequest req, HttpServletResponse resp,
                                     Map<String, Object> stack) {
        try {
            System.out.println("BackRcvResponse接收后台通知开始");

            //            req.setCharacterEncoding("ISO-8859-1");
            String encoding = req.getParameter(SDKConstants.param_encoding);
            // 获取银联通知服务器发送的后台通知参数
            Map<String, String> reqParam = getAllRequestParam(req);

            LogUtil.printRequestLog(reqParam);

            Map<String, String> valideData = null;
            if (null != reqParam && !reqParam.isEmpty()) {
                Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
                valideData = new HashMap<String, String>(reqParam.size());
                while (it.hasNext()) {
                    Entry<String, String> e = it.next();
                    String key = (String) e.getKey();
                    String value = (String) e.getValue();
                    //                    value = new String(value.getBytes("ISO-8859-1"), encoding);
                    valideData.put(key, value);
                }
            }

            for (String key : valideData.keySet()) {
                System.out.println("notify key= " + key + " and value= " + valideData.get(key));
            }

            //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
            if (!SDKUtil.validate(valideData, encoding)) {
                System.out.println("验证签名结果[失败].");
                //验签失败，需解决验签问题

            } else {
                System.out.println("验证签名结果[成功].");
                //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

                String orderId = valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
                //                String respCode = valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
                String settleAmt = valideData.get("settleAmt"); //获取金额，单位为分的字符串。
                String accNo = valideData.get("accNo"); //获取交易卡号

                BigDecimal settleAmtB = new BigDecimal(settleAmt);
                settleAmtB = settleAmtB.divide(new BigDecimal(100));

                ServiceResult<Boolean> orderPayResult = ordersService.orderPayAfter(orderId,
                    settleAmtB.toString(), Orders.PAYMENT_CODE_H5UNIONPAY,
                    Orders.PAYMENT_NAME_H5UNIONPAY, accNo, valideData.toString());
                if (!orderPayResult.getSuccess()) {
                    resp.getWriter().println("fail");
                } else {
                    resp.getWriter().println("ok"); //请不要修改或删除
                }

            }
            System.out.println("BackRcvResponse接收后台通知结束");
            //返回给银联服务器http 200  状态码
            //            resp.getWriter().print("ok");
        } catch (Exception e) {
            log.error("unionpay异步接口出现异常" + e);
        }
    }

    /**
     * 获取请求参数中所有的信息
     * 
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                //System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

}
