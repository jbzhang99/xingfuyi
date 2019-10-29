package com.yixiekeji.web.controller.pay;

import com.wxpay.util.HttpUtil;
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
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
 * 支付相关
 * @Filename: AppPayController.java
 * @Version: 1.0
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
    @RequestMapping(value = "/wxpayindex.html", method = RequestMethod.POST)
    public HttpJsonResult<Object> payindex(HttpServletRequest httpRequest, String orderPaySn,
                                           String optionsRadios, Integer memberId, String code) {
        HttpJsonResult<Object> serviceResult = new HttpJsonResult<Object>();
        if (StringUtil.isEmpty(optionsRadios, true)) {
            serviceResult.setMessage("请选择支付方式！");
            return serviceResult;
        }
        if (StringUtil.isEmpty(code, true)) {
            serviceResult.setMessage("登陆凭证无效！");
            return serviceResult;
        }
        //        ServiceResult<Member> result = memberService.getMemberById(memberId);
        //        if (!result.getSuccess() || result.getResult() == null) {
        //            serviceResult.setMessage("请先登录！");
        //            return serviceResult;
        //        }
        //        Member member = result.getResult();
        Member member = WebFrontSession.getLoginedUser(httpRequest);
        if (member == null) {
            serviceResult.setMessage("亲爱的用户，请先登录后再操作。");
            return serviceResult;
        }

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

        if ("appwxpay".equals(optionsRadios)) {
            System.out.println("--------------------------微信APP支付start--------------------");

            String attach = orderPaySn; //附加信息
            StringBuffer referer = httpRequest.getRequestURL();
            System.out.println("查看referer：" + referer.toString());

            payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
            String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

            // 测试1分钱，生产环境删掉测试的txnAmt
            txnAmt = "1";

            String noncestr = WxUtil.CreateNoncestr();

            String openId = "";
            String URL = EjavashopConfig.WXPAY_OAUTH2_TOKEN_XCX;
            URL = URL.replace("APPID", EjavashopConfig.WXPAY_APPID_XCX)
                .replace("SECRET", EjavashopConfig.WXPAY_APPSECRET_XCX).replace("JSCODE", code);
            JSONObject jsonObject = HttpUtil.httpsRequest(URL, "GET", null);
            if (null != jsonObject) {
                System.out.println("jsonObject=========" + jsonObject.toString());
                openId = jsonObject.getString("openid");
            }
            //微信订单查询
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", EjavashopConfig.WXPAY_APPID_XCX);//appID
            parameters.put("mch_id", EjavashopConfig.WXPAY_PARTNER_XCX);//商户号
            parameters.put("nonce_str", noncestr);//随机字符串
            //parameters.put("attach", attach);//附加信息
            parameters.put("body", "齐驱科技");//商品描述
            parameters.put("out_trade_no", orderPaySn);//商户订单号
            parameters.put("total_fee", txnAmt);//标价金额
            parameters.put("spbill_create_ip", WebUtil.getIpAddr(httpRequest));//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            parameters.put("notify_url", domainUrlUtil.getUrlResources() + "/wxpay/notify");//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            parameters.put("trade_type", "JSAPI");//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。小程序 JSAPI
            parameters.put("openid", openId);

            System.out.println("微信app支付请求参数验签前:" + parameters.toString());
            String sign = WxUtil.createSign(parameters, EjavashopConfig.WXPAY_PARTNER_KEY_XCX);
            parameters.put("sign", sign);
            System.out.println("微信app支付请求参数验签后:" + parameters.toString());

            String reuqestXml = WxUtil.getRequestXmlForXCX(parameters);
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
                    paramesign.put("appId", EjavashopConfig.WXPAY_APPID_XCX);
                    paramesign.put("nonceStr", map.get("nonce_str").toString());
                    paramesign.put("package", "prepay_id=" + prepayId);
                    paramesign.put("signType", "MD5");
                    paramesign.put("timeStamp", seconds1);

                    String signnew = WxUtil.createSign(paramesign,
                        EjavashopConfig.WXPAY_PARTNER_KEY_XCX);
                    System.out.println(
                        "signnew=====================================================" + signnew);

                    Map<String, String> params = new HashMap<>();
                    params.put("timeStamp", seconds1);
                    params.put("nonceStr", map.get("nonce_str").toString());
                    params.put("package", "prepay_id=" + prepayId);
                    params.put("signType", "MD5");
                    params.put("paySign", signnew);

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
    @RequestMapping(value = "/wxpaybalance.html", method = RequestMethod.POST)
    @ResponseBody
    public HttpJsonResult<Object> payBalance(HttpServletRequest request,
                                             HttpServletResponse response,
                                             @RequestParam(value = "orderPaySn", required = true) String orderPaySn,
                                             @RequestParam(value = "selectOrderBalance", required = true) String selectOrderBalance,
                                             @RequestParam(value = "balancePwd", required = true) String balancePwd) {
        HttpJsonResult<Object> serviceResult = new HttpJsonResult<Object>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            serviceResult.setMessage("亲爱的用户，请先登录后再操作。");
            return serviceResult;
        }

        boolean isBalancePay = "on".equals(selectOrderBalance) ? true : false;

        // 调用订单支付接口
        ServiceResult<OrderSuccessVO> orderPayResult = ordersService.orderPayBefore(orderPaySn,
            isBalancePay, balancePwd, member);
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
