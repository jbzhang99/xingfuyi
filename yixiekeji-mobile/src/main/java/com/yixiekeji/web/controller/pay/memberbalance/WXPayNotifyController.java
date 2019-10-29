package com.yixiekeji.web.controller.pay.memberbalance;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wxpay.util.CommonTools;
import com.wxpay.util.RequestHandler;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.XmlUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "memberBalance/pay")
public class WXPayNotifyController extends BaseController {
    // 微信返回  fail 失败，success 成功
    private static final String         STATUC_SUCCESS = "SUCCESS";
    private static final String         STATUC_FAIL    = "FAIL";
    private static final String         VALIDATE_FAIL  = "验签失败";
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    @RequestMapping(value = "wxNotify.html", method = { RequestMethod.GET, RequestMethod.POST })
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {

        ServletInputStream in = null;
        try {
            // post 过来的xml
            in = request.getInputStream();
            // 转换微信post过来的xml内容
            String xmlMsg = CommonTools.inputStream2String(in);
            System.out.println("微信回传xml：" + xmlMsg);
            Map<String, String> xml2map = objToString(XmlUtil.xml2map(xmlMsg, false));

            if (!"SUCCESS".equals(xml2map.get("return_code"))) {
                throw new BusinessException(VALIDATE_FAIL);
            }

            //安全校验
            validateSign(xml2map);

            BigDecimal totalFee = new BigDecimal(xml2map.get("total_fee"));
            totalFee = totalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

            //更改支付状态
            ServiceResult<Boolean> result = memberBalancePayLogService.payAfter(
                xml2map.get("out_trade_no"), totalFee.toString(), xml2map.get("transaction_id"));
            if (!result.getSuccess() || !result.getResult()) {
                throw new RuntimeException(result.getMessage());
            }

            sendMsg(response, STATUC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            sendMsg(response, STATUC_FAIL);
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
            throw new BusinessException(VALIDATE_FAIL);
        }
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
