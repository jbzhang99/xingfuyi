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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;
import com.unionpay.acp.LogUtil;
import com.unionpay.acp.SDKConstants;
import com.unionpay.acp.SDKUtil;

@Controller
@RequestMapping(value = "member/balance/pay")
public class MBUnionpayReturnUrlController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    @RequestMapping(value = "unionpayResult.html", method = { RequestMethod.GET,
                                                              RequestMethod.POST })
    public String alipay_result(HttpServletRequest req, HttpServletResponse response,
                                ModelMap dataMap) {
        try {
            String encoding = req.getParameter(SDKConstants.param_encoding);
            Map<String, String> respParam = getAllRequestParam(req);

            LogUtil.printRequestLog(respParam);

            Map<String, String> valideData = null;
            if (null != respParam && !respParam.isEmpty()) {
                Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
                valideData = new HashMap<String, String>(respParam.size());
                while (it.hasNext()) {
                    Entry<String, String> e = it.next();
                    String key = e.getKey();
                    String value = e.getValue();
                    valideData.put(key, value);
                }
            }
            if (!SDKUtil.validate(valideData, encoding)) {
                log.error("验证签名结果[失败].");
                dataMap.put("success", false);
                dataMap.put("info", "充值失败");
            } else {
                log.debug("验证签名结果[成功].");

                String orderId = valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
                String settleAmt = valideData.get("settleAmt"); //获取金额，单位为分的字符串。
                String accNo = valideData.get("accNo"); //交易流水号

                //交易金额，转换成“元”
                BigDecimal amount = new BigDecimal(settleAmt);
                amount = amount.divide(new BigDecimal(100));

                ServiceResult<Boolean> result = memberBalancePayLogService.payAfter(orderId,
                    amount.toString(), accNo);
                if (!result.getSuccess() || !result.getResult()) {
                    throw new RuntimeException(result.getMessage());
                }
                dataMap.put("success", true);
                dataMap.put("info", "充值成功！");
            }

        } catch (Exception e) {
            if (e instanceof BusinessException) {
                dataMap.put("info", "充值失败:" + e.getMessage());
            } else {
                e.printStackTrace();
                dataMap.put("info", "充值失败");
            }
            dataMap.put("success", false);
            log.error("unionpay同步接口出现异常" + e);
        }

        return "h5/member/balancepay/payresult";
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
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
