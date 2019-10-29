package com.yixiekeji.web.controller.analysis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.CookieHelper;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.analysis.BrowseLogMobile;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 记录浏览记录
 *                       
 * @Filename: AnalysisLogController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class AnalysisLogController extends BaseController {

    @Resource
    private IAnalysisService analysisService;

    @Resource
    private DomainUrlUtil    domainUrlUtil;

    /**
     * 记录用户查浏览记录
     * @param request
     * @param response
     * @param productId
     * @param sku
     */
    @RequestMapping(value = "/browse_Logs.html", method = RequestMethod.GET)
    public void browseLog(HttpServletRequest request, HttpServletResponse responsed) {
        String ref = request.getParameter("ref");
        String memberId = request.getParameter("memberId");
        String hrf = request.getParameter("hrf");
        String browser = request.getParameter("browser");
        String verinfo = request.getParameter("verinfo");
        String ebi = request.getParameter("ebi");

        Cookie cookie = CookieHelper.getCookieByName(request, domainUrlUtil.getCookieName());
        String cookieValue = cookie == null ? "" : cookie.getValue();

        Cookie cookieSession = CookieHelper.getCookieByName(request, "JSESSIONID");
        String cookieValueSession = cookieSession == null ? "" : cookieSession.getValue();

        BrowseLogMobile browseLog = new BrowseLogMobile();
        browseLog.setSiteCookie(StringUtil.nullSafeString(cookieValue));
        browseLog.setSessionId(StringUtil.nullSafeString(cookieValueSession));
        browseLog.setBrowseName(StringUtil.nullSafeString(browser));
        browseLog.setBrowserVersion(StringUtil.nullSafeString(verinfo));
        browseLog.setUserAgent(StringUtil.nullSafeString(request.getHeader("user-agent")));
        browseLog.setIpAddress(StringUtil.nullSafeString(request.getRemoteAddr()));
        browseLog.setUrlReferer(StringUtil.nullSafeString(ref));
        browseLog.setAccessedPage(StringUtil.nullSafeString(hrf));
        browseLog.setEbi(StringUtil.nullSafeString(ebi));
        browseLog.setMemberId(ConvertUtil.toInt(memberId, ConvertUtil.toInt(memberId, 0)));

        ServiceResult<Integer> servletResult = analysisService.saveBrowseLogMobile(browseLog);
        if (!servletResult.getSuccess()) {
            log.error("[Mobile AnalysisLogController][browseLog]记录用户行为轨迹出现异常");
        }
    }

}
