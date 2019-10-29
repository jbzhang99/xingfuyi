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
import com.yixiekeji.entity.analysis.ProductLookLog;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 
 * 记录用户浏览记录                      
 * @Filename: ProductLookLogController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductLookLogController extends BaseController {

    @Resource
    private IAnalysisService analysisService;
    @Resource
    private DomainUrlUtil    domainUrlUtil;

    /**
     * 记录用户浏览记录
     * @param request
     * @param responsed
     */
    @RequestMapping(value = "/product_look_log.html", method = RequestMethod.GET)
    public void productLookLog(HttpServletRequest request, HttpServletResponse responsed) {
        String memberId = request.getParameter("memberId");
        String productId = request.getParameter("productId");

        Cookie cookie = CookieHelper.getCookieByName(request, domainUrlUtil.getCookieName());
        String cookieValue = cookie == null ? "" : cookie.getValue();

        ProductLookLog productLookLog = new ProductLookLog();
        productLookLog.setSiteCookie(StringUtil.nullSafeString(cookieValue));
        productLookLog.setProductId(ConvertUtil.toInt(productId, ConvertUtil.toInt(productId, 0)));
        productLookLog.setMemberId(ConvertUtil.toInt(memberId, ConvertUtil.toInt(memberId, 0)));
        ServiceResult<Integer> servletResult = analysisService.saveProductLookLog(productLookLog);
        if (!servletResult.getSuccess()) {
            log.error("[AnalysisLogController][productLookLog]记录用户访问单品页日志出现异常");
        }
    }
}
