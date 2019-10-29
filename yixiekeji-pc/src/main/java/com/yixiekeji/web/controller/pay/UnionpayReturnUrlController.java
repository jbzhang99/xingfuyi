package com.yixiekeji.web.controller.pay;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.web.controller.BaseController;
import com.unionpay.acp.LogUtil;
import com.unionpay.acp.SDKConstants;
import com.unionpay.acp.SDKUtil;

@Controller
public class UnionpayReturnUrlController extends BaseController {
    @Resource
    private IOrdersService       ordersService;
    @Resource
    private IProductFrontService productFrontService;

    @RequestMapping(value = "/unionpay_result.html", method = RequestMethod.POST)
    public String alipay_result(HttpServletRequest req, HttpServletResponse response,
                                Map<String, Object> stack) {
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
                    String key = (String) e.getKey();
                    String value = (String) e.getValue();
                    valideData.put(key, value);
                }
            }
            if (!SDKUtil.validate(valideData, encoding)) {
                log.error("验证签名结果[失败].");
            } else {
                System.out.println("验证签名结果[成功].");
            }

        } catch (Exception e) {
            log.error("unionpay同步接口出现异常" + e);
        }

        ServiceResult<List<Product>> resultProduct = productFrontService.getProductsListCart(0, 10);
        stack.put("products", resultProduct.getResult());

        return "front/order/linepay";
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
