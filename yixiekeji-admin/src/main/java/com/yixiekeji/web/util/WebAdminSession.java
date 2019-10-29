package com.yixiekeji.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.entity.system.SystemAdmin;

/**
 * 平台Session管理
 *                       
 * @Filename: WebAdminSession.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class WebAdminSession {

    public static final String SESSION_ADMIN_USER = "SESSION_ADMIN_USER";

    /**
     * 放入Session
     * @param request
     * @param adminUser
     */
    public static void putAdminUser(HttpServletRequest request, SystemAdmin adminUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_ADMIN_USER, adminUser);
    }

    /**
     * 获取用户验证码
     * @param request
     * @return
     * @throws Exception
     */
    public static String getVerifyNumber(HttpServletRequest request) throws Exception {
        String verify_number = (String) request.getSession()
            .getAttribute(ConstantsEJS.VERIFY_NUMBER_NAME);
        return verify_number;
    }

    /**
     * 取得管理端Session
     * @param request
     * @return
     */
    public static SystemAdmin getAdminUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SystemAdmin adminUser = (SystemAdmin) session.getAttribute(SESSION_ADMIN_USER);
        return adminUser;
    }
}
