package com.yixiekeji.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.CookieHelper;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.util.MemberSession;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 访问过滤器
 *                       
 * @Filename: AccessFilter.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wangpeng@yixiekeji.com
 *
 */
@WebFilter(urlPatterns = { "/cart/*", "/order/*", "/storeregister/*" }, filterName = "accessFilter")
public class AccessFilter implements Filter {

    protected static Logger log = LoggerFactory.getLogger(Filter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 请求的URI
        String uri = request.getRequestURI();
        // Referer从哪个页面链接过来的
        String referer = request.getHeader("Referer");
        log.debug("AccessFilter-getRequestURI:" + request.getRequestURI());
        log.debug("AccessFilter-referer:" + referer);

        try {
            MemberSession memberSession = WebFrontSession.getMemberSession(request);
            // 用户已经登录
            if (memberSession != null && memberSession.getMember() != null) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } else {
                // 根据sessionId获取会员
                Cookie cookie = CookieHelper.getCookieByName(request, "JSESSIONID");
                if (cookie != null) {
                    String sessionId = cookie.getValue();
                    System.out.println("get:" + sessionId);
                    if (!StringUtil.isEmpty(sessionId)) {
//                        ApplicationContext context = WebApplicationContextUtils
//                            .getWebApplicationContext(request.getServletContext());
//                        IMemberService memberService = (IMemberService) context
//                            .getBean("memberService");
//                        ServiceResult<Member> result = memberService
//                            .getMemberBySessionId(sessionId);
//                        if (result.getSuccess() && result.getResult() != null) {
//                            Member memberApp = result.getResult();
//                            memberApp.setLoginSource(ConstantsEJS.YES_NO_0);
//                            WebFrontSession.putMemberSession(request, memberApp);
//                            filterChain.doFilter(servletRequest, servletResponse);
//                            System.out.println("会员已登录");
//                            return;
//                           
//                        }
                        Member memberApp  = WebFrontSession.getMemberBySessionId(sessionId);
                        if(memberApp != null) {
                        	memberApp.setLoginSource(ConstantsEJS.YES_NO_0);
                        	 WebFrontSession.putMemberSession(request, memberApp);
                             filterChain.doFilter(servletRequest, servletResponse);
                             System.out.println("会员已登录");
                             return;
                        }
                    }
                }

                // 用户未登录时，跳转到登录页面
                log.info("AccessFilter：用户未登录访问[" + uri + "]强制跳转到登录页面！");
                String path = request.getContextPath();
                response.sendRedirect(path + "/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init(FilterConfig arg0) throws ServletException {
    }

}
