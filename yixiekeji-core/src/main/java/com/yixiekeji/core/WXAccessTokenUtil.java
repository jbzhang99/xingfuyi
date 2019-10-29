package com.yixiekeji.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * 网络相关操作工具
 *                       
 * @Filename: HttpUtil.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class WXAccessTokenUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取access_token，此方法返回下列认证数据
     * <ul>
     * <li>access_token:网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同</li>
     * <li>expires_in:access_token接口调用凭证超时时间，单位（秒）</li>
     * <li>refresh_token:用户刷新access_token</li>
     * <li>openid:用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID</li>
     * <li>scope:用户授权的作用域，使用逗号（,）分隔</li>
     * </ul>
     * @return
     * @throws Exception
     */
    public static Map<String, Object> oauth2(String code) throws Exception {
        String url = EjavashopConfig.WXPAY_OAUTH2_TOKEN
            .replace("APPID", EjavashopConfig.WXPAY_APPID)
            .replace("SECRET", EjavashopConfig.WXPAY_APPSECRET).replace("CODE", code);
        String jsonStr = get(url);
        Map<String, Object> map = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
        return map;
    }

    /**
     * 获取微信用户信息，此方法返回下列用户数据
     * <ul>
     * <li>openid:用户的唯一标识</li>
     * <li>nickname:用户昵称</li>
     * <li>sex:用户的性别，值为1时是男性，值为2时是女性，值为0时是未知</li>
     * <li>openid:</li>
     * <li>province:用户个人资料填写的省份</li>
     * <li>city:普通用户个人资料填写的城市</li>
     * <li>country:国家，如中国为CN</li>
     * <li>headimgurl:用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
     *      用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。</li>
     * <li>privilege:用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）</li>
     * <li>unionid:只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）</li>
     * </ul>
     * @param openid
     * @param accessToken
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getUserinfo(String openid,
                                                  String accessToken) throws Exception {
        String url = EjavashopConfig.WXPAY_USER_INFO.replace("ACCESS_TOKEN", accessToken)
            .replace("OPENID", openid);
        String jsonStr = get(url);
        Map<String, Object> map = com.alibaba.fastjson.JSONObject.parseObject(jsonStr);
        return map;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params,
                             Map<String, String> headers) throws IOException, ExecutionException,
                                                          InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParameter(key, params.get(key));
            }
        }

        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException,
                                         NoSuchProviderException, UnsupportedEncodingException,
                                         IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url,
                             Map<String, String> params) throws KeyManagementException,
                                                         NoSuchAlgorithmException,
                                                         NoSuchProviderException,
                                                         UnsupportedEncodingException, IOException,
                                                         ExecutionException, InterruptedException {
        return get(url, params, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params) throws IOException,
                                                                      ExecutionException,
                                                                      InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

    public static String post(String url, String s) throws IOException, ExecutionException,
                                                    InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        return body;
    }

}
