package com.yixiekeji.web.controller.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wxpay.util.CommonTools;
import com.wxpay.util.HttpUtil;
import com.wxpay.util.RequestHandler;
import com.wxpay.util.SignUtil;
import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

import net.sf.json.JSONObject;

/**
 * 微信支付Controller
 *                       
 * @Filename: WxpayController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Controller
@RequestMapping(value = "/wxpay")
public class WxpayController extends BaseController {
    @Resource
    private DomainUrlUtil domainUrlUtil;

    /**
     * 微信认证方法
     * @param request
     * @param response
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     */
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public void auth(HttpServletRequest request, HttpServletResponse response, String signature,
                     String timestamp, String nonce, String echostr) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.sha1(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    /**
     * 跳转至支付页面
     * @param request
     * @param response
     * <table>
     *  <tr>     
     *      <td>  
             * @param userId
             * @param orderNo
             * @param money
             * @param code
     *  </td>
     *  <td>网页授权后获取传递的参数</td>
     * </tr>
     * </table>
    
     * @return
     */
    @RequestMapping(value = "topay")
    public String topay(HttpServletRequest request, ModelMap dataMap, HttpServletResponse response,
                        String orderPaySn, String money, String code, String describe) {
        System.out.println("=====================参数bg=====================");
        System.out.println("orderPaySn:" + orderPaySn);
        System.out.println("money:" + money);
        System.out.println("code:" + code);
        System.out.println("describe" + describe);
        System.out.println("=====================参数ed=====================");
        try {
            if (!money.matches("[0-9]+")) {
                throw new BusinessException("订单金额不允许出现小数或非数字");
            }

            //总金额以分为单位，不带小数点
            int total_fee = Integer.parseInt(money);

            //商户相关资料 
            String appid = EjavashopConfig.WXPAY_APPID;
            String appsecret = EjavashopConfig.WXPAY_APPSECRET;
            String partner = EjavashopConfig.WXPAY_PARTNER;
            String partnerkey = EjavashopConfig.WXPAY_PARTNER_KEY;

            String openId = "";
            String URL = EjavashopConfig.WXPAY_OAUTH2_TOKEN;
            URL = URL.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);

            JSONObject jsonObject = HttpUtil.httpsRequest(URL, "GET", null);
            if (null != jsonObject) {
                System.out.println("jsonObject=========" + jsonObject.toString());
                openId = jsonObject.getString("openid");
            }

            //获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
            String currTime = CommonTools.getCurrTime();
            //8位日期
            String strTime = currTime.substring(8, currTime.length());
            //四位随机数
            String strRandom = CommonTools.buildRandom(4) + "";
            //10位序列号,可以自行调整。
            String strReq = strTime + strRandom;

            //商户号
            String mch_id = partner;
            //子商户号  非必输
            //String sub_mch_id="";
            //随机数 
            String nonce_str = strReq;
            //商品描述
            //String body = describe;

            //商品描述根据情况修改
            String body = describe;
            //附加数据
            String attach = orderPaySn;
            //商户订单号
            String out_trade_no = orderPaySn;

            //订单生成的机器 IP
            String spbill_create_ip = request.getRemoteAddr();

            //这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
            String notify_url = domainUrlUtil.getUrlResources() + "/wxpay/notify.html";

            String trade_type = "JSAPI";
            String openid = openId;
            //非必输
            //              String product_id = "";
            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            packageParams.put("appid", appid);
            packageParams.put("mch_id", mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("attach", attach);
            packageParams.put("out_trade_no", out_trade_no);

            //这里写的金额为1 分到时修改
            packageParams.put("total_fee", total_fee + "");
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notify_url);

            packageParams.put("trade_type", trade_type);
            packageParams.put("openid", openid);

            RequestHandler reqHandler = new RequestHandler(request, response);
            reqHandler.init(appid, appsecret, partnerkey);

            String sign = reqHandler.createSign(packageParams);
            String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id
                         + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>"
                         + sign + "</sign>" + "<body><![CDATA[" + body + "]]></body>" + "<attach>"
                         + attach + "</attach>" + "<out_trade_no>" + out_trade_no
                         + "</out_trade_no>" + "<total_fee>" + total_fee + "</total_fee>"
                         + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                         + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>"
                         + trade_type + "</trade_type>" + "<openid>" + openid + "</openid>"
                         + "</xml>";
            String createOrderURL = EjavashopConfig.WXPAY_CREATE_ORDER_URL;
            String prepay_id = "";
            prepay_id = HttpUtil.getPayNo(createOrderURL, xml);

            SortedMap<String, String> finalpackage = new TreeMap<String, String>();
            String appid2 = appid;
            String timestamp = CommonTools.getTimeStamp();
            String nonceStr2 = nonce_str;
            String prepay_id2 = "prepay_id=" + prepay_id;
            String packages = prepay_id2;
            finalpackage.put("appId", appid2);
            finalpackage.put("timeStamp", timestamp);
            finalpackage.put("nonceStr", nonceStr2);
            finalpackage.put("package", packages);
            finalpackage.put("signType", "MD5");
            String finalsign = reqHandler.createSign(finalpackage);

            dataMap.put("appid", appid2);
            dataMap.put("timeStamp", timestamp);
            dataMap.put("nonceStr", nonceStr2);
            dataMap.put("package", packages);
            dataMap.put("sign", finalsign);
        } catch (BusinessException e) {
            dataMap.put("info", e.getMessage());
            return "h5/commons/error";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "h5/order/wxpay";
    }
}
