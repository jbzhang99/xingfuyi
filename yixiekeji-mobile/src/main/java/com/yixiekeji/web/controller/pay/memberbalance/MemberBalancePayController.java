package com.yixiekeji.web.controller.pay.memberbalance;

import com.wxpay.util.HttpUtil;
import com.yixiekeji.core.*;
import com.yixiekeji.core.wx.GetWxOrderno;
import com.yixiekeji.core.wx.WxUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import net.sf.json.JSONObject;
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

@Controller
@RequestMapping(value = "member/balance/pay")
public class MemberBalancePayController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;
    @Resource
    private DomainUrlUtil               domainUrlUtil;

    @RequestMapping(value = "pay.html", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> pay(HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    String optionsRadios, String amount,
                                                    Map<String, Object> dataMap, String code) {

        HttpJsonResult<Object> serviceResult = new HttpJsonResult<Object>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            serviceResult.setMessage("亲爱的用户，请先登录后再操作。");
            return serviceResult;
        }
        if (isNull(amount)) {
            serviceResult.setMessage("充值金额错误！");
            return serviceResult;
        }
        if (StringUtil.isEmpty(code, true)) {
            serviceResult.setMessage("登陆凭证无效！");
            return serviceResult;
        }
        String orderPaySn = RandomUtil.getOrderSn();

        ServiceResult<Boolean> paybefore = memberBalancePayLogService.payBefore(optionsRadios,
            amount, orderPaySn, member);
        if (!paybefore.getSuccess()) {
            serviceResult.setMessage(paybefore.getMessage());
            return serviceResult;
        }

        //TODO 测试金额 1分钱，正式环境去掉此行
        amount = "0.01";

        if ("h5alipay".equals(optionsRadios)) {//支付宝付款

        } else if ("h5weixin".equals(optionsRadios)) {
            StringBuffer referer = request.getRequestURL();
            System.out.println("查看referer：" + referer.toString());

            amount = new BigDecimal(amount).multiply(new BigDecimal(100)).toString();
            String txnAmt = amount.split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

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
            parameters.put("spbill_create_ip", WebUtil.getIpAddr(request));//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            parameters.put("notify_url",
                domainUrlUtil.getUrlResources() + "/memberBalance/pay/wxNotify.html");//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
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

}
