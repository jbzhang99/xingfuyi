package com.yixiekeji.web.util;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.config.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 用户Session对象管理
 *                       
 * @Filename: WebFrontSession.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Component
public class WebFrontSession {
    protected static Logger       log                 = LoggerFactory
        .getLogger(WebFrontSession.class);

    private static String         MEMBER_SESSION_NAME = "memberSession";
    private static DomainUrlUtil  domainUrlUtil       = (DomainUrlUtil) SpringBeanUtil
        .getBean("domainUrlUtil");

    @Resource
    private IMemberService        memberService;

    public static WebFrontSession webFrontSession;

    @PostConstruct
    public void init() {
        webFrontSession = this;
        webFrontSession.memberService = this.memberService;
    }

    /**
     * member放入session中
     * @param request
     * @param member
     * @throws Exception
     */
    public static void putMemberSession(HttpServletRequest request, Member member) {
        //        putMemberSession(request, member, null, null, null, null);
        HttpSession session = request.getSession();
        //        if (member != null) {
        //            session.setAttribute(MEMBER_SESSION_NAME, member);
        //        }
        MemberSession memberSession = new MemberSession();
        if (member != null) {
            memberSession.setMember(member);
        }
        session.setAttribute(MEMBER_SESSION_NAME, memberSession);
    }

    public static void removeMemberSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(MEMBER_SESSION_NAME);
        }
    }

    /**
     * 对象放入session中
     * @param request
     * @param
     * @throws Exception
     */
    public static void putObjToSession(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * 从session中取出对象
     * @param request
     * @param
     * @throws Exception
     */
    public static Object getObjFromSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * 从session中移除对象
     * @param request
     * @param
     * @throws Exception
     */
    public static void delObjFromSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        session.removeAttribute(key);
        ;
    }

    /**
     * 获取用户session
     * @param request
     * @return
     * @throws Exception
     */
    public static MemberSession getMemberSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberSession memberSession = (MemberSession) session.getAttribute(MEMBER_SESSION_NAME);
        return memberSession;
    }

    /**
     * 在公共头将sessionId写入cookie
     * @param request
     * @param response
     */
    public static void addSessionIdToCookie(HttpServletRequest request,
                                            HttpServletResponse response) {
        // 从request中取回cookie，判断存sessionId的cookie对象是否存在
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (domainUrlUtil.getCookieName().equals(cookie.getName())
                    && cookie.getValue() != null) {
                    return;
                }
            }
        }
        // sessionId存入cookie
        Cookie cookie = new Cookie(domainUrlUtil.getCookieName(), request.getSession().getId());
        cookie.setMaxAge(30 * 24 * 60 * 60); // cookie缓存一个月
        cookie.setDomain(domainUrlUtil.getCookieDomain());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取用户验证码
     * @param request
     * @return
     * @throws Exception
     */
    public static String getVerifyNumber(HttpServletRequest request) {
        String verify_number = (String) request.getSession()
            .getAttribute(ConstantsEJS.VERIFY_NUMBER_NAME);
        return verify_number;
    }

    //    public static Member getLoginedUser(HttpServletRequest request) {
    //            Member member = new Member();
    //            try {
    //                MemberSession memberSession = getMemberSession(request);
    //                if (memberSession == null) {
    //                    return null;
    //                    // throw new BusinessException("会员信息获取失败，请重试！");
    //                }
    //                member = memberSession.getMember();
    //                if (member == null) {
    //                    return null;
    //                    // throw new BusinessException("会员信息获取失败，请重试！");
    //                }
    //    
    //            } catch (Exception e) {
    //                log.error(e.getMessage(), e);
    //            }
    //            return member;
    //        }

    /**
     * 获取用户
     * @param request
     * @return
     */
    public static Member getLoginedUser(HttpServletRequest request) {
        Member member = null;

        String token = request.getHeader("Token");
        if (StringUtil.isEmpty(token)) {
            return null;
        }

        //        IMemberService memberService = (IMemberService) SpringBeanUtil.getBean("memberService");

        //        ApplicationContext context = WebApplicationContextUtils
        //            .getWebApplicationContext(request.getServletContext());
        //        IMemberService memberService = (IMemberService) context.getBean("memberService");
        //        ServiceResult<Member> result = memberService.getMemberByToken(token);
        ServiceResult<Member> result = webFrontSession.memberService.getMemberByToken(token);
        if (!result.getSuccess()) {
            return null;
        }
        member = result.getResult();

        return member;
    }

    /**
     * 生成token
     * @return
     */
    public static String genToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

}
