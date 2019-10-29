package com.yixiekeji.core.wx;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import com.yixiekeji.core.MD5Util;

public class WxUtil {

    public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 10; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }

    public static String createSign(SortedMap<Object, Object> parameters, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
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

        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();

        return sign;
    }

    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
                || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static String getRequestXmlForXCX(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            //            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
            //                || "sign".equalsIgnoreCase(k)) {
            //                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            //            } else {
            sb.append("<" + k + ">" + v + "</" + k + ">");
            //            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    //    public static Map<String, Object> sendRefundRequest(String reuqestXml) throws Exception {
    //        KeyStore keyStore;
    //        FileInputStream instream = null;
    //        CloseableHttpClient httpclient = null;
    //        CloseableHttpResponse response = null;
    //        Map<String, Object> map = null;
    //        try {
    //            keyStore = KeyStore.getInstance("PKCS12");
    //            instream = new FileInputStream(new File(ThirdPartyConfig.WXPAY_REFUND_PATH));//放退款证书的路径
    //            keyStore.load(instream, ThirdPartyConfig.WXPAY_PARTNER.toCharArray());
    //            SSLContext sslcontext = SSLContexts.custom()
    //                .loadKeyMaterial(keyStore, ThirdPartyConfig.WXPAY_PARTNER.toCharArray()).build();
    //            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
    //                new String[] { "TLSv1" }, null,
    //                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    //            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    //            HttpPost httpPost = new HttpPost(ThirdPartyConfig.WXPAY_REFUDN_URL);//退款接口
    //
    //            System.out.println("executing request" + httpPost.getRequestLine());
    //            StringEntity reqEntity = new StringEntity(reuqestXml);
    //            // 设置类型 
    //            reqEntity.setContentType("application/x-www-form-urlencoded");
    //            httpPost.setEntity(reqEntity);
    //            response = httpclient.execute(httpPost);
    //            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
    //            map = doXMLParse(jsonStr);
    //
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        } finally {
    //            instream.close();
    //            response.close();
    //            httpclient.close();
    //        }
    //        return map;
    //    }
    //
    //    /**
    //     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
    //     * @param strxml
    //     * @return
    //     * @throws JDOMException
    //     * @throws IOException
    //     */
    //    @SuppressWarnings("rawtypes")
    //    public static Map doXMLParse(String strXml) throws Exception {
    //        if (null == strXml || "".equals(strXml)) {
    //            return null;
    //        }
    //
    //        Map<String, Object> map = new HashMap<String, Object>();
    //
    //        SAXBuilder builder = new SAXBuilder();
    //
    //        InputStream in = new ByteArrayInputStream(strXml.getBytes());
    //        Document doc = builder.build(in);
    //        Element root = doc.getRootElement();
    //        List list = root.getChildren();
    //        Iterator it = list.iterator();
    //
    //        while (it.hasNext()) {
    //            Element e = (Element) it.next();
    //            String k = e.getName();
    //
    //            List children = e.getChildren();
    //
    //            String v = "";
    //            if (children.isEmpty()) {
    //                v = e.getTextNormalize();
    //            } else {
    //                v = getChildrenText(children);
    //            }
    //
    //            map.put(k, v);
    //        }
    //        //关闭流
    //        in.close();
    //
    //        return map;
    //    }
    //
    //    /**
    //     * 获取子结点的xml
    //     * @param children
    //     * @return String
    //     */
    //    @SuppressWarnings("rawtypes")
    //    public static String getChildrenText(List children) {
    //        StringBuffer sb = new StringBuffer();
    //        if (!children.isEmpty()) {
    //            Iterator it = children.iterator();
    //            while (it.hasNext()) {
    //                Element e = (Element) it.next();
    //                String name = e.getName();
    //                String value = e.getTextNormalize();
    //                List list = e.getChildren();
    //                sb.append("<" + name + ">");
    //                if (!list.isEmpty()) {
    //                    sb.append(getChildrenText(list));
    //                }
    //                sb.append(value);
    //                sb.append("</" + name + ">");
    //            }
    //        }
    //
    //        return sb.toString();
    //    }

}
