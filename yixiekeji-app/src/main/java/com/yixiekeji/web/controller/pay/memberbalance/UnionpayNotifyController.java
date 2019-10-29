package com.yixiekeji.web.controller.pay.memberbalance;

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

import com.unionpay.acp.SDKConstants;
import com.unionpay.acp.SDKUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "memberBalance/pay")
public class UnionpayNotifyController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    /**
     * 银联支付通知
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "unionpayNotify.html", method = { RequestMethod.GET,
                                                              RequestMethod.POST })
    public void unionpayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.debug("BackRcvResponse接收后台通知开始");

            String encoding = request.getParameter(SDKConstants.param_encoding);
            // 获取银联通知服务器发送的后台通知参数
            Map<String, String> reqParam = getAllRequestParam(request);

            Map<String, String> valideData = null;
            if (null != reqParam && !reqParam.isEmpty()) {
                Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
                valideData = new HashMap<String, String>(reqParam.size());
                while (it.hasNext()) {
                    Entry<String, String> e = it.next();
                    String key = e.getKey();
                    String value = e.getValue();
                    valideData.put(key, value);
                }
            }

            for (String key : valideData.keySet()) {
                log.debug("key= " + key + ",value= " + valideData.get(key));
            }

            //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
            if (!SDKUtil.validate(valideData, encoding)) {
                log.debug("验证签名结果[失败].");
            } else {
                log.debug("验证签名结果[成功].");

                String orderId = valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
                //                String respCode = valideData.get("respCode"); //获取应答码，收到后台通知了respCode的值一般是00，可以不需要根据这个应答码判断。
                String settleAmt = valideData.get("settleAmt"); //获取金额，单位为分的字符串。
                String accNo = valideData.get("accNo"); //获取交易卡号

                //交易金额，转换成“元”
                BigDecimal amount = new BigDecimal(settleAmt);
                amount = amount.divide(new BigDecimal(100));

                //记日志，改余额
                ServiceResult<Boolean> result = memberBalancePayLogService.payAfter(orderId,
                    amount.toString(), accNo);
                if (!result.getSuccess() || !result.getResult()) {
                    throw new RuntimeException(result.getMessage());
                }

                response.getWriter().println("ok");
            }
            log.debug("unionpayNotify接收后台通知结束");
        } catch (Exception e) {
            log.error("unionpayNotify异步接口出现异常" + e);
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
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
