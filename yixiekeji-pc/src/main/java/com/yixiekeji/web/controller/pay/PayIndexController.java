package com.yixiekeji.web.controller.pay;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.unionpay.acp.SDKConfig;
import com.unionpay.acp.SDKUtil;
import com.weixin.CommonConstants;
import com.weixin.GetWxOrderno;
import com.weixin.MD5Util;
import com.yixiekeji.core.*;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
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
import java.math.BigDecimal;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PayIndexController extends BaseController {

    @Resource
    private IOrdersService       ordersService;
    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private DomainUrlUtil        domainUrlUtil;

    @RequestMapping(value = "/payindex.html", method = RequestMethod.GET)
    public String payindex(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap) {

        String optionsRadios = request.getParameter("optionsRadios");

        if (StringUtil.isEmpty(optionsRadios, true)) {
            dataMap.put("info", "请选择要支付的订单，谢谢！");
            return "front/commons/error";
        }
        Member member = WebFrontSession.getLoginedUser(request,response);
        if (member == null) {
            dataMap.put("info", "用户Session过期，请重新登录");
            return "front/commons/error";
        }

        String selectOrderBalance = request.getParameter("selectOrderBalance");
        String balancePassword = request.getParameter("balancePassword");

        boolean isBalancePay = "on".equals(selectOrderBalance) ? true : false;
        if(StringUtil.isEmpty(balancePassword)&&isBalancePay==false)
            balancePassword="没有密码";

        // 订单号
        String orderPaySn = request.getParameter("orderSn");

        // 调用订单支付接口
        ServiceResult<OrderSuccessVO> orderPayResult = ordersService.orderPayBefore(orderPaySn,
            isBalancePay, balancePassword, member);
        if (!orderPayResult.getSuccess()) {
            dataMap.put("info", orderPayResult.getMessage());
            return "front/commons/error";
        }

        OrderSuccessVO orderSuccessVO = orderPayResult.getResult();

        //需要支付的总金额
        BigDecimal payOrderAllsVO;
        if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_2) { //余额支付够付款，扣除余额，更改订单状态，跳转到支付成功页面

            ServiceResult<List<Product>> resultProduct = productFrontService.getProductsListCart(0,
                10);
            dataMap.put("products", resultProduct.getResult());
            return "front/order/linepay";
        } else if (orderSuccessVO.getBanlancePayVO() == OrderSuccessVO.BANLANCEPAYVO_3) {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO()
                .subtract(orderSuccessVO.getBanlancePayMoneyVO());
        } else {
            payOrderAllsVO = orderSuccessVO.getPayOrderAllsVO();
        }

        if ("alipay".equals(optionsRadios)) {//支付宝付款
            try {
                //支付类型
                String payment_type = "1";
                //必填，不能修改
                //服务器异步通知页面路径
                String notify_url = domainUrlUtil.getUrlResources() + "/alipay_result_notify.html";
                //需http://格式的完整路径，不能加?id=123这类自定义参数

                //页面跳转同步通知页面路径
                String return_url = domainUrlUtil.getUrlResources() + "/alipay_result.html";
                //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

                //商户订单号
                String out_trade_no = orderPaySn;
                //商户网站订单系统中唯一订单号，必填

                //订单名称
                String subject = EjavashopConfig.ALIPAY_ALL_SUBJECT;
                //必填

                // 付款金额
                // 生产环境支付金额
                String total_fee = payOrderAllsVO.toString();
                //必填

                //订单描述
                String body = "";
                //商品展示地址
                String show_url = domainUrlUtil.getUrlResources();
                //需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html

                //防钓鱼时间戳
                String anti_phishing_key = "";
                //若要使用请调用类文件submit中的query_timestamp函数

                //客户端的IP地址
                String exter_invoke_ip = WebUtil.getIpAddr(request);

                //把请求参数打包成数组
                Map<String, String> sParaTemp = new HashMap<String, String>();
                sParaTemp.put("service", "create_direct_pay_by_user");
                sParaTemp.put("partner", AlipayConfig.partner);
                sParaTemp.put("seller_email", AlipayConfig.seller_email);
                sParaTemp.put("_input_charset", AlipayConfig.input_charset);
                sParaTemp.put("payment_type", payment_type);
                sParaTemp.put("notify_url", notify_url);
                sParaTemp.put("return_url", return_url);
                sParaTemp.put("out_trade_no", out_trade_no);
                sParaTemp.put("subject", subject);
                sParaTemp.put("total_fee", total_fee);
                sParaTemp.put("body", body);
                sParaTemp.put("show_url", show_url);
                sParaTemp.put("anti_phishing_key", anti_phishing_key);
                sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

                //建立请求
                String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
                //                response.getWriter().println(sHtmlText);
                dataMap.put("paytext", sHtmlText);
                return "front/order/alipay";
            } catch (Exception e) {
                log.error("alipay支付出现异常" + e);
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
                return "front/order/alipay";
            } catch (Exception e) {
                log.error("unionpay支付出现异常" + e);
            }
        } else if ("weixin".equals(optionsRadios)) {
            String showWeiXinMoney = payOrderAllsVO.toString();
            payOrderAllsVO = payOrderAllsVO.multiply(new BigDecimal(100));
            String txnAmt = payOrderAllsVO.toString().split("\\.")[0]; //付款金额，单位为分，不能有小数点，去掉

            // 32位随机字符串
            String nonce_str = StringUtil.getRandomString(32);
            // 获取本机Ip
            String ip = localIp();

            //建立时间格式化对象：
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            //获取当前时间，作为订单开始时间获取到的时间类型是long类型的，单位是毫秒
            long currentTime = System.currentTimeMillis();
            String currentTimeStr = dateFormat.format(new Date(currentTime));
            //在这个基础上加上5分钟：作为订单失效时间
            long currentTimeAddFive = currentTime + 5 * 60 * 1000;
            String currentTimeAddFiveStr = dateFormat.format(new Date(currentTimeAddFive));

            // 加密串
            SortedMap<String, String> packageParams = new TreeMap<String, String>();

            packageParams.put("appid", CommonConstants.APPID);
            packageParams.put("mch_id", CommonConstants.MCHID);
            packageParams.put("product_id", orderPaySn); // 商户根据自己业务传递的参数 必填
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", EjavashopConfig.WEIXIN_ORDER_NAME);
            packageParams.put("out_trade_no", orderPaySn);
            packageParams.put("total_fee", txnAmt); //支付金额,精确到分 txnAmt
            packageParams.put("spbill_create_ip", ip);
            packageParams.put("notify_url", domainUrlUtil.getUrlResources() + "/wx/notify.html");
            packageParams.put("trade_type", "NATIVE");
            packageParams.put("time_start", currentTimeStr);
            packageParams.put("time_expire", currentTimeAddFiveStr);
            String sign = createSign(packageParams, CommonConstants.KEY);
            packageParams.put("sign", sign);

            // xml传输数据
            String xml = "<xml>" + "<appid>" + CommonConstants.APPID + "</appid>" + "<mch_id>"
                         + CommonConstants.MCHID + "</mch_id>" + "<product_id>" + orderPaySn
                         + "</product_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<body>"
                         + EjavashopConfig.WEIXIN_ORDER_NAME + "</body>" + "<out_trade_no>"
                         + orderPaySn + "</out_trade_no>" + "<total_fee>" + txnAmt + "</total_fee>"
                         + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<notify_url>"
                         + domainUrlUtil.getUrlResources() + "/wx/notify.html" + "</notify_url>"
                         + "<trade_type>NATIVE</trade_type>" + "<time_start>" + currentTimeStr
                         + "</time_start>" + "<time_expire>" + currentTimeAddFiveStr
                         + "</time_expire>" + "<sign>" + sign + "</sign>" + "</xml>";

            Map<String, Object> map = GetWxOrderno.getPayNo(CommonConstants.CREATEORDERURL, xml);

            if (null == map) {
                dataMap.put("info", "系统维护稍后重试");
                return "front/commons/error";
            }

            String returnCode = (String) map.get("return_code");

            if (!"SUCCESS".equals(returnCode)) {
                dataMap.put("info", "系统维护稍后重试");
                return "front/commons/error";
            }

            String resultCode = (String) map.get("result_code");
            if (!"SUCCESS".equals(resultCode)) {
                dataMap.put("info", "系统维护稍后重试");
                return "front/commons/error";
            }

            // 获取生成二维码的参数
            String codeUrl = (String) map.get("code_url");
            if (codeUrl == null || codeUrl.length() == 0) {
                dataMap.put("info", "系统维护稍后重试");
                return "front/commons/error";
            }

            dataMap.put("showWeiXinorderNo", orderPaySn);
            dataMap.put("showWeiXinMoney", showWeiXinMoney);
            dataMap.put("codeUrl", codeUrl);

            return "front/order/weixinpay";
        } else {

        }

        return "";
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    @SuppressWarnings("rawtypes")
    public String createSign(SortedMap<String, String> packageParams, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);

        log.info("WXPay md5 sb:" + sb + "key=" + key);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        log.info("WXPay packge签名:" + sign);

        return sign;
    }

    /**
       * 获取本机Ip 
       *  
       *  通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
       *  获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
       * @return
    */
    @SuppressWarnings("rawtypes")
    private String localIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();

                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }

            }
        } catch (SocketException e) {
            log.warn("获取本机Ip失败:异常信息:" + e.getMessage());
        }
        return ip;
    }
}
