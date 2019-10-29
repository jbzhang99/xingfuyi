package com.yixiekeji.web.csrf;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * csrf 工具类
 *                       
 * @Filename: CsrfTokenManager.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public final class CsrfTokenManager {

    /**
     * csrf简单原理：
     * 1 每次请求（form标签使用@form.form才会生效）生成一个键值对，键和值都是UUID；
     * 2 键值对将存入session或者缓存中，如下存入的键值对是：ddd4b5a9-fecd-446c-bd78-63b70bb500a1="245accec-3c12-4642-967f-e476cef558c4"
     * 3 MEM_KEY_NAME保存键存入from标签隐藏域，如CSRFMemKey="ddd4b5a9-fecd-446c-bd78-63b70bb500a1"；
     * 4 CSRF_PARAM_NAME保存值存入from标签隐藏域，如CSRFToken="245accec-3c12-4642-967f-e476cef558c4"；
     * 5 接收到请求之后在拦截器中获取键和值（请求过来的值）：
     *                              request.getParameter(MEM_KEY_NAME);    ==>  "ddd4b5a9-fecd-446c-bd78-63b70bb500a1"
     *                              request.getParameter(CSRF_PARAM_NAME); ==>  "245accec-3c12-4642-967f-e476cef558c4"
     * 6 然后根据request中获取到的键获取session中保存的值：session.getAttribute("ddd4b5a9-fecd-446c-bd78-63b70bb500a1") ==> "245accec-3c12-4642-967f-e476cef558c4"
     * 7 比较5（请求过来的值）和6（session中保存的值）中取到的值是否相等，相等则清除session中的键值对并返回true，不等则抛异常
     */

    /** token建的参数名称（request参数名称） */
    static final String         MEM_KEY_NAME    = "CSRFMemKey";
    /** token值的参数名称（request参数名称） */
    static final String         CSRF_PARAM_NAME = "CSRFToken";

    private final static byte[] sync            = new byte[0];
    // session中csrfToken参数名称
    //    public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CsrfTokenManager.class.getName()
    //                                                                  + ".tokenval";

    private CsrfTokenManager() {
    };

    /**
     * 在session中创建csrfToken
     * @param key
     * @param session
     * @return
     */
    public static String getTokenForSession(String key, HttpSession session) {
        String token = null;
        synchronized (sync) {
            token = (String) session.getAttribute(key);
            if (null == token) {
                token = UUID.randomUUID().toString();
                session.setAttribute(key, token);
            }
        }
        return token;
    }

    /**
     * 删除已经使用的键值
     * @param key
     * @param session
     */
    public static void destroyToken(String key, HttpSession session) {
        session.removeAttribute(key);
    }

    /**
     * 从request中获取键
     * @param request
     * @return
     */
    public static String getMemkeyFromRequest(HttpServletRequest request) {
        return request.getParameter(MEM_KEY_NAME);
    }

    /**
     * 从request中获取值
     * @param request
     * @return
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }
}