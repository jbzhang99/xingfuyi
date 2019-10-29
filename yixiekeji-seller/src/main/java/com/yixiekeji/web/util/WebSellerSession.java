package com.yixiekeji.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.entity.seller.SellerUser;

/**
 * 商家Session管理
 *                       
 * @Filename: WebSellerSession.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class WebSellerSession {

    public static final String SESSION_SELLER_USER = "SESSION_SELLER_USER";

    /**
     * 放入Session
     * @param request
     * @param sellerUser
     */
    public static void putSellerUser(HttpServletRequest request, SellerUser sellerUser) {
        HttpSession session = request.getSession();
        session.setAttribute(SESSION_SELLER_USER, sellerUser);
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
    public static SellerUser getSellerUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SellerUser sellerUser = (SellerUser) session.getAttribute(SESSION_SELLER_USER);
        return sellerUser;
    }
}
