package com.yixiekeji.web.controller.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.config.AlipayConfig;
import com.yixiekeji.core.*;
import com.yixiekeji.core.wx.GetWxOrderno;
import com.yixiekeji.core.wx.WxUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.vo.order.OrderSuccessVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * app支付相关
 * @Filename: AppPayController.java
 * @Version: 1.0
 * @Author: 李斌
 * @Email: libin@ejavashop.com
 *
 */
@Controller
public class AppPayController extends BaseController {

    @Resource
    private IOrdersService ordersService;
    @Resource
    private IMemberService memberService;
    @Resource
    private DomainUrlUtil  domainUrlUtil;

    @ResponseBody
    @RequestMapping("/payindex.html")
    public HttpJsonResult<Object> payindex(HttpServletRequest httpRequest, String orderPaySn,
                                           String optionsRadios, Integer memberId) {
        HttpJsonResult<Object> serviceResult = new HttpJsonResult<Object>();
        if (StringUtil.isEmpty(optionsRadios, true)) {
            serviceResult.setMessage("请选择支付方式！");
            return serviceResult;
        }
        ServiceResult<Member> result = memberService.getMemberById(memberId);
        if (!result.getSuccess() || result.getResult() == null) {
            serviceResult.setMessage("请先登录！");
            return serviceResult;
        }
        Member member = result.getResult();

        // 调用订单支付接口
        ServiceResult<OrderSuccessVO> orderPayResult = ordersService.orderPayBefore(orderPaySn,
            false, "", member);
        if (!orderPayResult.getSuccess()) {
            serviceResult.setMessage(orderPayResult.getMessage());
            return serviceResult;
        }

        OrderSuccessVO orderSuccessVO = orderPayResult.getResult();
        System.out.println("payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();: "
                           + orderSuccessVO.getPayOrderAllsVO());

        //需要支付的总金额
        BigDecimal payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();
        System.out.println("payOrderAllsVO:" + payOrderAllsVO);
        System.out.println("orderPaySn:" + orderPaySn);

        if ("appalipay".equals(optionsRadios)) {//支付宝付款

            //          // 生产环境支付金额
            String total_fee = payOrderAllsVO.toString();

            //实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(EjavashopConfig.ALIPAY_APP_URL,
                EjavashopConfig.ALIPAY_APP_ID, EjavashopConfig.ALIPAY_APP_PRIVATE_KEY, "json",
                AlipayConfig.input_charset, EjavashopConfig.ALIPAY_APP_PUBLIC_KEY,
                AlipayConfig.sign_type_rsa2);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //  model.setBody("我是测试数据");
            model.setSubject("齐驱科技");
            model.setOutTradeNo(orderPaySn);
            // model.setTimeoutExpress("30m");
            model.setTotalAmount(total_fee);
            //  model.setProductCode("QUICK_MSECURITY_PAY");
            request.setBizModel(model);
            request.setNotifyUrl(domainUrlUtil.getUrlResources() + "/alipay_result_notify.html");
            try {
                //这里和普通的接口调用不同，使用的是sdkExecute
                AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
                System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
                serviceResult.setData(response.getBody());

            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } else if ("appwxpay".equals(optionsRadios)) {
            System.out.println("--------------------------微信APP支付start--------------------");

            String attach = orderPaySn; //附加信息
            StringBuffer referer = httpRequest.getRequestURL();
            System.out.println("查看referer：" + referer.toString());

            payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
            String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

            // 测试1分钱，生产环境删掉测试的txnAmt
            txnAmt = "1";

            String noncestr = WxUtil.CreateNoncestr();

            //微信订单查询
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", EjavashopConfig.WXPAY_APPID_APP);//appID
            parameters.put("mch_id", EjavashopConfig.WXPAY_PARTNER_APP);//商户号
            parameters.put("nonce_str", noncestr);//随机字符串
            parameters.put("attach", attach);//附加信息
            parameters.put("body", "齐驱科技");//商品描述
            parameters.put("out_trade_no", orderPaySn);//商户订单号
            parameters.put("total_fee", txnAmt);//标价金额
            parameters.put("spbill_create_ip", WebUtil.getIpAddr(httpRequest));//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            parameters.put("notify_url", domainUrlUtil.getUrlResources() + "/wxpay/notify");//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            parameters.put("trade_type", "APP");//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。

            System.out.println("微信app支付请求参数验签前:" + parameters.toString());
            String sign = WxUtil.createSign(parameters, EjavashopConfig.WXPAY_PARTNER_KEY_APP);
            parameters.put("sign", sign);
            System.out.println("微信app支付请求参数验签后:" + parameters.toString());

            String reuqestXml = WxUtil.getRequestXml(parameters);
            System.out.println("微信app支付请求xml:" + reuqestXml);

            Map<String, Object> map = null;

            //发送查询请求
            try {
                map = GetWxOrderno.getPayNo(EjavashopConfig.WXPAY_CREATE_ORDER_URL, reuqestXml);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("微信APP支付失败，错误信息：" + e.getMessage());
            }
            if (null == map) {
                System.out.println("微信APP支付失败，返回信息为空");
            }

            //安全校验
            System.out.println("微信APP支付接口同步信息处理：" + map.toString());
            //判断返回结果
            if (map.get("return_code").equals("SUCCESS")) {
                if (map.get("result_code").equals("SUCCESS")) {
                    System.out.println("微信APP支付请求成功。");

                    //获取预支付交易会话标识(微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时)
                    String prepayId = (String) map.get("prepay_id");

                    if (StringUtil.isEmpty(prepayId)) {
                        System.out.println("微信APP支付预支付交易会话标识失败，交易单号：【" + orderPaySn + "】");
                    }

                    //拼接请求参数

                    System.out.println("--------------------------微信APP支付end------------------");

                    long currentTimeMillis1 = System.currentTimeMillis();//生成时间戳
                    long second1 = currentTimeMillis1 / 1000L;//（转换成秒）
                    String seconds1 = String.valueOf(second1).substring(0, 10);//（截取前10位）
                    // 再次生成签名
                    SortedMap<Object, Object> paramesign = new TreeMap<Object, Object>();
                    paramesign.put("appid", EjavashopConfig.WXPAY_APPID_APP);
                    paramesign.put("partnerid", EjavashopConfig.WXPAY_PARTNER_APP);
                    paramesign.put("prepayid", prepayId);
                    paramesign.put("package", "Sign=WXPay");
                    paramesign.put("noncestr", map.get("nonce_str").toString());
                    paramesign.put("timestamp", seconds1);
                    String signnew = WxUtil.createSign(paramesign,
                        EjavashopConfig.WXPAY_PARTNER_KEY_APP);
                    System.out.println(
                        "signnew=====================================================" + signnew);

                    Map<String, String> params = new HashMap<>();
                    params.put("sign", signnew);
                    params.put("appid", EjavashopConfig.WXPAY_APPID_APP);
                    params.put("retcode", "0");
                    params.put("partnerid", EjavashopConfig.WXPAY_PARTNER_APP);
                    params.put("prepayid", prepayId);
                    params.put("packages", "Sign=WXPay");
                    params.put("noncestr", map.get("nonce_str").toString());
                    params.put("timestamp", seconds1);

                    String json = JsonUtil.toJson(params);
                    System.out.println("json返回前端的数据" + json);
                    serviceResult.setData(params);
                }

            }
        }

        return serviceResult;
    }

    /**
     * 余额支付
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/paybalance.html", method = RequestMethod.GET)
    @ResponseBody
    public HttpJsonResult<Object> payBalance(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Map<String, Object> dataMap) {
        HttpJsonResult<Object> serviceResult = new HttpJsonResult<Object>();
        Member member = WebFrontSession.getLoginedUser(request,response);
        if (member == null) {
            serviceResult.setMessage("请先登录！");
            return serviceResult;
        }

        String selectOrderBalance = request.getParameter("selectOrderBalance");
        String balancePassword = StringUtil.nullSafeString(request.getParameter("balancePassword"));
        boolean isBalancePay = "on".equals(selectOrderBalance) ? true : false;

        String orderPaySn = request.getParameter("orderSn");
        // 调用订单支付接口
        ServiceResult<OrderSuccessVO> orderPayResult = ordersService.orderPayBefore(orderPaySn,
            isBalancePay, balancePassword, member);
        if (!orderPayResult.getSuccess()) {
            serviceResult.setMessage(orderPayResult.getMessage());
            return serviceResult;
        }

        OrderSuccessVO orderSuccessVO = orderPayResult.getResult();
        System.out.println("payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();: "
                           + orderSuccessVO.getPayOrderAllsVO());

        //需要支付的总金额
        BigDecimal payOrderAllsVO = BigDecimal.ZERO;
        if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_2) { //余额支付够付款，扣除余额，更改订单状态，跳转到支付成功页面
            serviceResult.setBackUrl("h5/order/linepay");
            return serviceResult;
        } else if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_3) {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO()
                .subtract(orderSuccessVO.getBanlancePayMoneyVO());
        } else {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();
        }

        serviceResult.setData(payOrderAllsVO);
        return serviceResult;
    }

    @RequestMapping(value = "/paybalsuc.html", method = RequestMethod.GET)
    public String payBalanceSuc(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap) {
        return "h5/order/linepay";
    }

}
