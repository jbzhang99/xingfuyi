package com.yixiekeji.web.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.yixiekeji.core.StringUtil;

public class ResourceCheckFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String url = getPathWithinApplication(request);
        if (url != null && url.endsWith("/")) {
            // 截去url最后一个/
            url = url.substring(0, url.length() - 1);
        }

        if (url == null || "".equals(url)) {
            url = "/admin/index.html";
        }

        return subject.isAuthenticated() && subject.isPermitted(url);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletResponse hsp = (HttpServletResponse) response;
        HttpServletRequest hReq = (HttpServletRequest) request;
        Subject subject = getSubject(request, response);

        String url = getPathWithinApplication(request);
        if (url != null && url.endsWith("/")) {
            // 截去url最后一个/
            url = url.substring(0, url.length() - 1);
        }
        if ((StringUtil.isEmpty(url))) {
            hsp.getWriter().print("<script>top.window.location.href='" + hReq.getContextPath()
                                  + "/admin/index.html'</script>");
        } else if (!subject.isAuthenticated()) {
            // 如果没有登录，去登录页
            hsp.getWriter().print("<script>top.window.location.href='" + hReq.getContextPath()
                                  + "/admin/login.html'</script>");
        } else {
            // 如果已登录，提示没有权限
            hsp.sendRedirect(hReq.getContextPath() + "/admin/unauth.html");
        }

        return false;
    }

}
