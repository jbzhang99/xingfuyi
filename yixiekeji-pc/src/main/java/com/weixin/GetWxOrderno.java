package com.weixin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class GetWxOrderno {

    public static DefaultHttpClient httpclient;
    static {
        httpclient = new DefaultHttpClient();
        //httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
    }

    private static final Logger     LOGGER = LoggerFactory.getLogger(GetWxOrderno.class);

    @SuppressWarnings({ "unchecked", "resource" })
    public static Map<String, Object> getPayNo(String url, String xmlParam) {
        LOGGER.info("xmlParam:" + xmlParam);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        try {
            HttpPost pmethod = new HttpPost(url); // 设置响应头信息
            pmethod.addHeader("Connection", "keep-alive");
            pmethod.addHeader("Accept", "*/*");
            pmethod.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            pmethod.addHeader("Host", "api.mch.weixin.qq.com");
            pmethod.addHeader("X-Requested-With", "XMLHttpRequest");
            pmethod.addHeader("Cache-Control", "max-age=0");
            pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            pmethod.setEntity(new StringEntity(xmlParam, "UTF-8"));

            HttpResponse response = httpclient.execute(pmethod);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            LOGGER.info("wexinPay getPrepayId json:" + jsonStr);

            Map<String, Object> map = doXMLParse(jsonStr);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    public static Map doXMLParse(String strXml) throws Exception {
        if (null == strXml || "".equals(strXml)) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        SAXBuilder builder = new SAXBuilder();

        InputStream in = new ByteArrayInputStream(strXml.getBytes());
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();

        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();

            List children = e.getChildren();

            String v = "";
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }

            map.put(k, v);
        }
        //关闭流
        in.close();

        return map;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 获取SSL验证的HttpClient
     * @param httpClient
     * @return
     */
    public static HttpClient getSSLInstance(HttpClient httpClient) {
        ClientConnectionManager ccm = httpClient.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", MySSLSocketFactory.getInstance(), 443));
        httpClient = new DefaultHttpClient(ccm, httpClient.getParams());
        return httpClient;
    }

    public static InputStream String2Inputstream(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

}