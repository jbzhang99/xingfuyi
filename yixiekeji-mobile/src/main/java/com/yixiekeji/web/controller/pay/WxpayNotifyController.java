package com.yixiekeji.web.controller.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wxpay.util.CommonTools;
import com.wxpay.util.HttpUtil;
import com.wxpay.util.RequestHandler;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.XmlUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 微信支付通知结果Controller
 *                       
 * @Filename: WxpayNotifyController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Controller
@RequestMapping(value = "/wxpay")
public class WxpayNotifyController extends BaseController {
    // 微信返回  fail 失败，success 成功
    private static final String STATUC_SUCCESS = "SUCCESS";
    private static final String STATUC_FAIL    = "FAIL";
    private static final String VALIDATE_FAIL  = "验签失败";

    @Resource
    private IOrdersService      ordersService;

    /**
     * 支付成功后台通知
     * @param request
     * @param response
     */
    @RequestMapping(value = "notify", method = { RequestMethod.GET, RequestMethod.POST })
    public void wxNotify(HttpServletRequest req, HttpServletResponse resp) {
        ServletInputStream in = null;
        try {
            // post 过来的xml
            in = req.getInputStream();
            // 转换微信post过来的xml内容
            String xmlMsg = CommonTools.inputStream2String(in);
            System.out.println("微信回传xml：" + xmlMsg);

            Map<String, String> xml2map = objToString(XmlUtil.xml2map(xmlMsg, false));

            if (!"SUCCESS".equals(xml2map.get("return_code"))) {
                log.error("[WxpayNotifyController][wxNotify]" + VALIDATE_FAIL);
                throw new BusinessException(VALIDATE_FAIL);
            }

            //安全校验
            validateSign(xml2map);

            BigDecimal totalFee = new BigDecimal(xml2map.get("total_fee"));
            totalFee = totalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

            //更改订单状态
            ServiceResult<Boolean> orderPayResult = ordersService.orderPayAfter(
                xml2map.get("out_trade_no"), totalFee.toString(), Orders.PAYMENT_CODE_XCXWXPAY,
                Orders.PAYMENT_NAME_XCXWXPAY, xml2map.get("transaction_id"), xmlMsg);
            if (!orderPayResult.getResult()) {
                log.error("更改订单状态失败,支付失败");
                throw new BusinessException("更改订单状态失败,支付失败");
            }

            String accessToken = (String) req.getServletContext()
                .getAttribute(ConstantsEJS.WX_ACCESS_TOKEN);
            // 发送公众号客服消息
            // TODO 如果用户量比较大，这里token获取应使用定时任务，如果不需要公众号通知或其它微信开发，此通知代码可去除
            HttpUtil.message.sendText(accessToken, xml2map.get("openid"),
                EjavashopConfig.WXPAY_PAYSUCCESS_MESSAGE
                    .replace("ORERID", xml2map.get("out_trade_no")).replace("SUFFIX", "^_^"));
            sendMsg(resp, STATUC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            sendMsg(resp, STATUC_FAIL);
        }
    }

    /**
     * 通知校验<br>
     * 所有接收到的参数,组成集合做签名
     * @param postData
     */
    private void validateSign(Map<String, String> postData) throws Exception {
        SortedMap<String, String> parameters = new TreeMap<String, String>(postData);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(postData.get("appid"), EjavashopConfig.WXPAY_APPSECRET_XCX,
            EjavashopConfig.WXPAY_PARTNER_KEY_XCX);

        String sign = reqHandler.createSign(parameters);
        if (!sign.equals(postData.get("sign"))) {
            log.error("[WxpayNotifyController][wxNotify]" + VALIDATE_FAIL);
            throw new BusinessException(VALIDATE_FAIL);
        }

    }

    public static void sendMsg(HttpServletResponse resp, String return_code) {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.print(return_code);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response, ModelMap dataMap,
                       String res, Integer state) {
        return "h5/order/test";
    }

    /**
     * 前台支付成功页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "payresult", method = RequestMethod.GET)
    public String paysuccess(HttpServletRequest request, HttpServletResponse response,
                             ModelMap dataMap, String res, Integer state) {
        try {
            res = URLDecoder.decode(res, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dataMap.put("state", state != null && state == 1 ? "success" : "fail");
        dataMap.put("info", res);
        return "h5/order/payresult";
    }

    private Map<String, String> objToString(Map<String, Object> object) {
        Map<String, String> map = new HashMap<String, String>();
        if (object != null) {
            Iterator<String> iterator = object.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = object.get(key) == null ? "" : object.get(key).toString();
                map.put(key, value);
            }
        }

        return map;
    }

}
