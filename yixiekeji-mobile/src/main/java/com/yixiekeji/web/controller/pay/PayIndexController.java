package com.yixiekeji.web.controller.pay;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.unionpay.acp.SDKConfig;
import com.unionpay.acp.SDKUtil;
import com.yixiekeji.core.*;
import com.yixiekeji.core.wx.GetWxOrderno;
import com.yixiekeji.core.wx.WxUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.vo.order.OrderSuccessVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 跳转到支付
 *                       
 * @Filename: PayIndexController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class PayIndexController extends BaseController {

    @Resource
    private IOrdersService ordersService;
    @Resource
    private DomainUrlUtil  domainUrlUtil;

    @RequestMapping(value = "/payindex.html", method = RequestMethod.GET)
    public String payindex(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap) {

        String optionsRadios = request.getParameter("optionsRadios");

        if (StringUtil.isEmpty(optionsRadios, true)) {
            dataMap.put("info", "请选择要支付的订单，谢谢！");
            return "h5/commons/error";
        }
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            dataMap.put("info", "用户Session过期，请重新登录");
            return "h5/commons/error";
        }

        String selectOrderBalance = request.getParameter("selectOrderBalance");
        String balancePassword = request.getParameter("balancePassword");
        boolean isBalancePay = "on".equals(selectOrderBalance) ? true : false;

        String orderPaySn = request.getParameter("orderSn");
        // 调用订单支付接口
        ServiceResult<OrderSuccessVO> orderPayResult = ordersService.orderPayBefore(orderPaySn,
            isBalancePay, balancePassword, member);
        if (!orderPayResult.getSuccess()) {
            dataMap.put("info", orderPayResult.getMessage());
            return "h5/commons/error";
        }

        OrderSuccessVO orderSuccessVO = orderPayResult.getResult();
        System.out.println("payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();: "
                           + orderSuccessVO.getPayOrderAllsVO());

        //需要支付的总金额
        BigDecimal payOrderAllsVO;
        if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_2) { //余额支付够付款，扣除余额，更改订单状态，跳转到支付成功页面
            return "h5/order/linepay";
        } else if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_3) {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO()
                .subtract(orderSuccessVO.getBanlancePayMoneyVO());
        } else {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();
        }

        System.out.println("payOrderAllsVO:" + payOrderAllsVO);
        System.out.println("orderPaySn:" + orderPaySn);

        if ("alipay".equals(optionsRadios)) {//支付宝付款
            System.out.println("-----1-------");
            try {
                //支付类型
                String payment_type = "1";
                //必填，不能修改
                //服务器异步通知页面路径
                String notify_url = domainUrlUtil.getUrlResources() + "/alipay_result_notify.html";
                //需http://格式的完整路径，不能加?id=123这类自定义参数

                //页面跳转同步通知页面路径
                String return_url = domainUrlUtil.getUrlResources() + "/alipay_result.html";

                //商户订单号
                String out_trade_no = orderPaySn;
                //                String out_trade_no = new Date().getTime() + "";
                //商户网站订单系统中唯一订单号，必填

                //订单名称
                String subject = EjavashopConfig.ALIPAY_ALL_SUBJECT;
                //必填

                // 付款金额
                // TODO 测试支付，生产环境删掉测试的total_fee
                String total_fee = "0.01";
                // 生产环境支付金额
                //                String total_fee = payOrderAllsVO.toString();

                //必填

                //商品展示地址
                String show_url = domainUrlUtil.getUrlResources() + "/index.html";
                //必填，需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

                //订单描述
                String body = "";
                //选填

                //超时时间
                String it_b_pay = "";
                //选填

                //钱包token
                String extern_token = "";
                //选填

                //////////////////////////////////////////////////////////////////////////////////
                System.out.println("-----2-------");
                //把请求参数打包成数组
                Map<String, String> sParaTemp = new HashMap<String, String>();
                sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
                sParaTemp.put("partner", AlipayConfig.partner);
                sParaTemp.put("seller_id", AlipayConfig.seller_id);
                sParaTemp.put("_input_charset", AlipayConfig.input_charset);
                sParaTemp.put("payment_type", payment_type);
                sParaTemp.put("notify_url", notify_url);
                sParaTemp.put("return_url", return_url);
                sParaTemp.put("out_trade_no", out_trade_no);
                sParaTemp.put("subject", subject);
                sParaTemp.put("total_fee", total_fee);
                sParaTemp.put("show_url", show_url);
                sParaTemp.put("body", body);
                sParaTemp.put("it_b_pay", it_b_pay);
                sParaTemp.put("extern_token", extern_token);
                sParaTemp.put("app_pay", "Y");
                System.out.println("-----3-------");
                //建立请求
                String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
                System.out.println(sHtmlText);
                System.out.println("-----4-------");
                dataMap.put("paytext", sHtmlText);
                return "h5/order/alipay";
            } catch (Exception e) {
            }
        } else if ("unionpay".equals(optionsRadios)) { //银联支付
            try {
                String merId = EjavashopConfig.UNIONPAY_MERID; //商家ID

                payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
                String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

                Map<String, String> requestData = new HashMap<String, String>();

                /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
                requestData.put("version", SDKUtil.version); //版本号，全渠道默认值
                requestData.put("encoding", SDKUtil.encoding_UTF8); //字符集编码，可以使用UTF-8,GBK两种方式
                requestData.put("signMethod", "01"); //签名方法，只支持 01：RSA方式证书加密
                requestData.put("txnType", "01"); //交易类型 ，01：消费
                requestData.put("txnSubType", "01"); //交易子类型， 01：自助消费
                requestData.put("bizType", "000201"); //业务类型，B2C网关支付，手机wap支付
                requestData.put("channelType", "07"); //渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机

                /***商户接入参数***/
                requestData.put("merId", merId); //商户号码，请改成自己申请的正式商户号或者open上注册得来的777测试商户号
                requestData.put("accessType", "0"); //接入类型，0：直连商户 

                requestData.put("orderId", orderPaySn); //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则     
                requestData.put("txnTime", TimeUtil.getCurrentTime()); //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
                requestData.put("currencyCode", "156"); //交易币种（境内商户一般是156 人民币）        
                requestData.put("txnAmt", txnAmt); //交易金额，单位分，不要带小数点
                requestData.put("reqReserved", EjavashopConfig.UNIONPAY_REQRESERVED); //请求方保留域，透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节     

                //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
                //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
                //异步通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
                requestData.put("frontUrl",
                    domainUrlUtil.getUrlResources() + "/unionpay_result.html");

                //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
                //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
                //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码 
                //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
                //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
                requestData.put("backUrl",
                    domainUrlUtil.getUrlResources() + "/unionpay_result_notify.html");

                //////////////////////////////////////////////////
                //
                //       报文中特殊用法请查看 PCwap网关跳转支付特殊用法.txt
                //
                //////////////////////////////////////////////////

                /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
                Map<String, String> submitFromData = SDKUtil.signData(requestData,
                    SDKUtil.encoding_UTF8); //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

                String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl(); //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
                System.out.println("requestFrontUrl:" + requestFrontUrl);
                String html = SDKUtil.createAutoFormHtml(requestFrontUrl, submitFromData,
                    SDKUtil.encoding_UTF8); //生成自动跳转的Html表单

                System.out.println("打印请求HTML，此为请求报文，为联调排查问题的依据：" + html);
                //将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
                //                response.getWriter().write(html);
                dataMap.put("paytext", html);
                return "h5/order/alipay";
            } catch (Exception e) {
                log.error("unionpay支付出现异常" + e);
            }
        } else if ("wxpay".equals(optionsRadios)) {
            payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
            String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉
            //微信支付
            //共账号及商户相关参数
            String appid = EjavashopConfig.WXPAY_APPID;
            String backUri = domainUrlUtil.getUrlResources() + "/wxpay/topay";

            backUri = backUri + "?orderPaySn=" + orderPaySn + "&describe=齐驱科技订单&money=" + txnAmt;
            //URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
            try {
                backUri = URLEncoder.encode(backUri, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
            String url = EjavashopConfig.WXPAY_OAUTH2_URL;
            url = url.replace("APPID", appid).replace("REDIRECT_URI", backUri)
                .replace("SCOPE", EjavashopConfig.WXPAY_SCOPE_BASE)
                .replace("STATE", EjavashopConfig.WXPAY_STATE);
            return "redirect:" + url;

        } else if ("wxh5".equals(optionsRadios)) {
            System.out.println("--------------------------微信H5支付start--------------------");

            String attach = orderPaySn; //附加信息
            StringBuffer referer = request.getRequestURL();
            System.out.println("查看referer：" + referer.toString());

            BigDecimal payOrderAlls = payOrderAllsVO;//显示的价钱

            payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
            String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

            String noncestr = WxUtil.CreateNoncestr();

            //微信订单查询
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
            parameters.put("appid", EjavashopConfig.WXPAY_APPID);//appID
            parameters.put("mch_id", EjavashopConfig.WXPAY_PARTNER);//商户号
            parameters.put("nonce_str", noncestr);//随机字符串
            parameters.put("attach", attach);//附加信息
            parameters.put("body", "ejavashop商品");//商品描述
            parameters.put("out_trade_no", orderPaySn);//商户订单号
            parameters.put("total_fee", txnAmt);//标价金额
            parameters.put("spbill_create_ip", WebUtil.getIpAddr(request));//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
            parameters.put("notify_url", domainUrlUtil.getUrlResources() + "/wxpay/notify.html");//异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            parameters.put("trade_type", "MWEB");//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。

            System.out.println("微信h5支付请求参数验签前:" + parameters.toString());

            String sign = WxUtil.createSign(parameters, EjavashopConfig.WXPAY_PARTNER_KEY);
            parameters.put("sign", sign);
            System.out.println("微信h5支付请求参数验签后:" + parameters.toString());

            String reuqestXml = WxUtil.getRequestXml(parameters);
            System.out.println("微信h5支付请求xml:" + reuqestXml);

            Map<String, Object> map = null;

            //发送查询请求
            try {
                map = GetWxOrderno.getPayNo(EjavashopConfig.WXPAY_CREATE_ORDER_URL, reuqestXml);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("微信H5支付失败，错误信息：" + e.getMessage());
            }
            if (null == map) {
                System.out.println("微信H5支付失败，返回信息为空");
            }

            //安全校验
            System.out.println("微信H5支付接口同步信息处理：" + map.toString());
            String state = "fail";
            String info = "支付失败";

            //判断返回结果
            if (map.get("return_code").equals("SUCCESS")) {
                if (map.get("result_code").equals("SUCCESS")) {
                    System.out.println("微信H5支付请求成功。");

                    //获取预支付交易会话标识(微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时)
                    String prepayId = (String) map.get("prepay_id");

                    if (StringUtil.isEmpty(prepayId)) {
                        System.out.println("微信H5支付预支付交易会话标识失败，交易单号：【" + orderPaySn + "】");
                    }
                    state = "success";
                    info = "支付成功";
                    //存入redis,redis标识+支付单号,时间两小时 
                    //                    JedisClient.set(RedisConstants.WX_H5_PREPAY_ID + orderPaySn, prepayId,
                    //                        RedisConstants.EXPIRE_2_HOURS);

                    //支付跳转URL
                    String mwebUrl = (String) map.get("mweb_url");
                    //拼接请求参数

                    String redirectUrl = "";
                    try {
                        redirectUrl = URLEncoder.encode(
                            domainUrlUtil.getUrlResources() + "/member/order.html", "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("微信H5支付成功：返回地址为【" + mwebUrl + "】");
                    dataMap.put("mwebUrl", mwebUrl + "&redirect_url=" + redirectUrl);
                    System.out.println("微信H5支付成功：返回地址为【" + redirectUrl + "】");
                    //                    dataMap.put("productName", ordersProductsInfoList.get(0).getProductName());
                    dataMap.put("orderSn", orderPaySn);
                    dataMap.put("payAmount", payOrderAlls + "");
                    System.out.println("微信H5支付成功：请求地址为【" + dataMap.get("mwebUrl") + "】");

                    System.out.println("--------------------------微信H5支付end------------------");

                    return "h5/order/wxh5pay";

                } else {
                    info = (String) map.get("err_code_des");
                    System.out.println("微信H5支付失败：错误代码【" + map.get("err_code") + "】,错误代码描述：【"
                                       + map.get("err_code_des") + "】");
                }
            } else {
                info = (String) map.get("return_msg");
                System.out.println("微信H5支付失败：" + map.get("return_msg"));
            }

            System.out.println("--------------------------微信H5支付end------------------");
            dataMap.put("state", state);
            dataMap.put("info", info);

            return "h5/order/wxh5pay";

            //            return "h5/order/payresult";
        } else {//其他支付在这添加

        }
        return "";
    }

}
