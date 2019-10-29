package com.yixiekeji.web.controller.sale;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 三级营销进入商城
 *                       
 * @Filename: SaleEnterController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class SaleEnterController extends BaseController {

    @Resource
    private DomainUrlUtil domainUrlUtil;

    /**
     * 跳转到注册页面
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/sale_enter.html", method = { RequestMethod.GET })
    public String saleEnter(HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> stack) {
        String salecode = request.getParameter("salecode");

        // 存入cookie
        Cookie cookie = new Cookie(SaleMember.EJS_SALECODE, salecode);
        cookie.setMaxAge(30 * 24 * 60 * 60); // cookie缓存一个月
        cookie.setDomain(domainUrlUtil.getCookieName());
        cookie.setPath("/");
        response.addCookie(cookie);

        stack.put("salecode", salecode);
        return "h5/member/register";
    }

}
