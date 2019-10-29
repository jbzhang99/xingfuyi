package com.yixiekeji.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.entity.member.Member;

/**
 * 用户Session对象管理
 *                       
 * @Filename: WebFrontSession.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class WebFrontSession {

    private static String MEMBER_SESSION_NAME = "memberSession";

    /**
     * member放入session中
     * @param request
     * @param member
     * @throws Exception
     */
    public static void putMemberSession(HttpServletRequest request, Member member) {
        HttpSession session = request.getSession();
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
     * @param member
     * @throws Exception
     */
    public static void putObjToSession(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }

    /**
     * 从session中取出对象
     * @param request
     * @param member
     * @throws Exception
     */
    public static Object getObjFromSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return session.getAttribute(key);
    }

    /**
     * 从session中移除对象
     * @param request
     * @param member
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

    //    public static String getSompleShop(HttpServletRequest request) {
    //        Cookie cookie = CookieHelper.getCookieByName(request, DomainUrlUtil.getEJS_COOKIE_NAME());
    //        if (cookie == null) {
    //            return null;
    //        }
    //        return cookie.getValue();
    //    }
    //
    //    public static String getSessionId(HttpServletRequest request) {
    //        String sessionId = null;
    //        Cookie[] cookies = request.getCookies();
    //        if (cookies != null) {
    //            for (Cookie cookie : cookies) {
    //                if (DomainUrlUtil.getEJS_COOKIE_NAME().equals(cookie.getName())
    //                    && cookie.getValue() != null) {
    //                    sessionId = cookie.getValue();
    //                    break;
    //                }
    //            }
    //        } else {
    //            sessionId = request.getSession().getId();
    //        }
    //        return sessionId;
    //    }
    //
    //    /**
    //     * 在公共头将sessionId写入cookie
    //     * @param request
    //     * @param response
    //     */
    //    public static void addSessionIdToCookie(HttpServletRequest request,
    //                                            HttpServletResponse response) {
    //        // 从request中取回cookie，判断存sessionId的cookie对象是否存在
    //        Cookie[] cookies = request.getCookies();
    //        if (cookies != null) {
    //            for (Cookie cookie : cookies) {
    //                if (DomainUrlUtil.getEJS_COOKIE_NAME().equals(cookie.getName())
    //                    && cookie.getValue() != null) {
    //                    return;
    //                }
    //            }
    //        }
    //        // sessionId存入cookie
    //        Cookie cookie = new Cookie(DomainUrlUtil.getEJS_COOKIE_NAME(),
    //            request.getSession().getId());
    //        cookie.setMaxAge(30 * 24 * 60 * 60); // cookie缓存一个月
    //        cookie.setDomain(DomainUrlUtil.getEJS_COOKIE_DOMAIN());
    //        cookie.setPath("/");
    //        response.addCookie(cookie);
    //    }

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

    public static Member getLoginedUser(HttpServletRequest request) {
        Member member = new Member();
        try {
            MemberSession memberSession = getMemberSession(request);
            if (memberSession == null) {
                return null;
            }
            member = memberSession.getMember();
            if (member == null) {
                return null;
            }
        } catch (Exception e) {
        }
        return member;
    }
}
