package com.yixiekeji.web.csrf;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;

/**
 * <p>
 *    csrf 拦截器
 *    <ul>
 *         <li>只是针对post请求的数据做检查，get请求方式不检查，表单提交必须用spring mvc标签</li>
 *         <li><font color="red">注意：现在基于session机制实现，在做集群中要修改为依赖memcached、Redis等分布式缓存</font></li>
 *    </ul>
 * </p>
 * @Filename: CsrfInterceptor.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {

    private final static Set<String> DIRECT_URLS = new HashSet<String>();

    static {
        DIRECT_URLS.add("/order/ordercommit.html");
        DIRECT_URLS.add("/order/ordercommitforgroup.html");
        DIRECT_URLS.add("/order/ordercommitforintegral.html");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 考虑性能及用户体验，只在一些特定的请求做拦截
        if ("POST".equalsIgnoreCase(request.getMethod())
            && DIRECT_URLS.contains(request.getRequestURI())) {

            String memKey = CsrfTokenManager.getMemkeyFromRequest(request);

            if (StringUtil.isEmpty(memKey)) {
                throw new BusinessException("请使用正常方式提交，不要进行重复提交操作!");
            }
            String memToken = CsrfTokenManager.getTokenForSession(memKey, request.getSession());
            String requestToken = CsrfTokenManager.getTokenFromRequest(request);

            if (memToken.equals(requestToken)) {
                CsrfTokenManager.destroyToken(memKey, request.getSession());
                return true;
            } else {
                throw new BusinessException("请使用正常方式提交，不要进行重复提交操作!");
            }
        }
        return true;
    }

}
