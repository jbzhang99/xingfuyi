package com.yixiekeji.web.controller.member;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.EjavashopConfig;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WXAccessTokenUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberWxsign;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberWxsignService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 微信联合登录相关action
 *                       
 * @Filename: MemberWxsign.java
 * @Version: 1.0
 * 
 */
@Controller
@RequestMapping(value = "wechat/memberWxsign")
public class MemberWxsignController extends BaseController {
    @Resource
    private IMemberWxsignService memberWxsignService;
    @Resource
    private IMemberService       memberService;
    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 微信登录认证
     * @return
     */
    @RequestMapping(value = "auth.html", method = { RequestMethod.GET })
    public String auth(HttpServletRequest request, HttpServletResponse response) {
        try {
            //跳转至认证界面
            String redirectURI = domainUrlUtil.getUrlResources()
                                 + "/wechat/memberWxsign/callback.html";
            String url = EjavashopConfig.WXPAY_OAUTH2_URL
                .replace("APPID", EjavashopConfig.WXPAY_APPID).replace("REDIRECT_URI", redirectURI)
                .replace("SCOPE", EjavashopConfig.WXPAY_SCOPE_USERINFO)
                .replace("STATE", new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
            //发送请求
            return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 微信登录成功后回调
     * @param request
     * @param response
     * @param code 获取access_token的票据
     * @param state 自定义参数
     * @return
     */
    @RequestMapping(value = "callback.html", method = { RequestMethod.GET })
    public String callback(HttpServletRequest request, HttpServletResponse response, String code,
                           String state, ModelMap dataMap) {
        try {
            if (isNull(code)) {
                dataMap.put("info", "网站未授权");
                return "h5/commons/error";
            }

            //用户认证 
            Map<String, Object> result = WXAccessTokenUtil.oauth2(code);

            Object openid = result.get("openid");
            Object accessToken = result.get("access_token");
            if (isNull(openid) || isNull(accessToken)) {
                dataMap.put("info", "身份认证失败，请尝试账号登录");
                return "h5/commons/error";
            }

            ServiceResult<MemberWxsign> serviceResult = memberWxsignService
                .getWxUser((String) openid);

            MemberWxsign memberWxsign = serviceResult.getResult();

            //登录成功，将用户放入session
            if (memberWxsign == null) {
                // 将openid存入session
                WebFrontSession.putObjToSession(request, "openid", openid);
            } else {
                ServiceResult<Member> memberRlt = memberService
                    .getMemberById(memberWxsign.getMemberId());
                Member member = memberRlt.getResult();
                if (member == null) {
                    // 将openid存入session
                    WebFrontSession.putObjToSession(request, "openid", openid);
                } else {
                    WebFrontSession.putMemberSession(request, member);
                }

            }
        } catch (Exception e) {
            dataMap.put("info", e.getMessage());
            return "h5/commons/error";
        }
        //跳转至个人中心
        return "redirect:/member/index.html";
    }
}
