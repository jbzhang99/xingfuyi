package com.yixiekeji.web.controller.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.weixin.CommonConstants;
import com.weixin.CommonTools;
import com.weixin.RequestHandler;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.XmlUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.core.matrix.MatrixToImageWriter;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "/wx")
public class WXPayNotifyUrlController extends BaseController {
    @Resource
    private IOrdersService      ordersService;

    // 微信返回  fail 失败，success 成功
    private static final String STATUC_SUCCESS = "SUCCESS";
    private static final String STATUC_FAIL    = "FAIL";
    private static final String VALIDATE_FAIL  = "验签失败";

    /**
     * 微信支付成功后通知
     * @param req
     * @param resp
     */
    @RequestMapping(value = "/notify.html", method = RequestMethod.POST)
    public void notify(HttpServletRequest req, HttpServletResponse resp) {
        ServletInputStream in = null;
        try {
            log.debug("通知开始....");
            // post 过来的xml
            in = req.getInputStream();
            // 转换微信post过来的xml内容
            String xmlMsg = CommonTools.inputStream2String(in);

            @SuppressWarnings("unchecked")
            Map<String, String> xml2map = objToString(XmlUtil.xml2map(xmlMsg, false));

            if (!"SUCCESS".equals(xml2map.get("return_code"))
                || StringUtils.isEmpty(xml2map.get("out_trade_no"))) {
                System.out
                    .println("-------------------微信支付异步回调验签失败-------------------------------");
                throw new BusinessException(VALIDATE_FAIL);
            }

            //安全校验
            validateSign(xml2map);

            BigDecimal totalFee = new BigDecimal(xml2map.get("total_fee"));
            totalFee = totalFee.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

            //更改订单状态
            ServiceResult<Boolean> orderPayResult = ordersService.orderPayAfter(
                xml2map.get("out_trade_no"), totalFee.toString(), Orders.PAYMENT_CODE_PCWXPAY,
                Orders.PAYMENT_NAME_PCWXPAY, xml2map.get("transaction_id"), xmlMsg);
            if (!orderPayResult.getResult()) {
                log.info("更改订单状态失败,支付失败");
                throw new BusinessException("更改订单状态失败,支付失败");
            }

            sendMsg(resp, STATUC_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            sendMsg(resp, STATUC_FAIL);
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
        reqHandler.init(postData.get("appid"), CommonConstants.APPSECRET, CommonConstants.KEY);

        String sign = reqHandler.createSign(parameters);
        if (!sign.equals(postData.get("sign"))) {
            throw new BusinessException(VALIDATE_FAIL);
        } else {
            log.debug("签名成功!");
        }
    }

    /**
     * 生成支付二维码
     * 
     * @param response
     * @return
     * @throws ServletRequestBindingException
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping(value = "/createTDCode.html")
    public void paySuccess(HttpServletResponse response,
                           HttpServletRequest request) throws ServletRequestBindingException,
                                                       IOException, WriterException {
        String codeUrl = request.getParameter("codeUrl");
        log.debug("-------" + codeUrl);

        OutputStream os = response.getOutputStream();

        response.reset();
        // 设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpg");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);

        @SuppressWarnings("rawtypes")
        Hashtable hints = new Hashtable();

        // 内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeUrl, BarcodeFormat.QR_CODE, 300,
            300, hints);
        // 生成二维码
        MatrixToImageWriter.writeToStream(bitMatrix, "jpg", os);
        os.close();
    }

    /**
     * @param url
     * @param param
     * @return
     */
    public static String SendGET(String url, String param) {
        String result = "";// 访问返回结果
        BufferedReader read = null;// 读取访问结果

        try {
            // 创建url
            URL realurl = new URL(url + "?" + param);
            // 打开连接
            URLConnection connection = realurl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            read = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;// 循环读取
            while ((line = read.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (read != null) {// 关闭流
                try {
                    read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
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
