package com.yixiekeji.web.controller.member;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 会员余额充值相关action
 *                       
 * @Filename: MemberBalanceController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihailong@ejavashop.com
 *
 */
@Controller
@RequestMapping(value = "member/balance/pay")
public class AppMemberBalanceController extends BaseController {
    @Resource
    private IMemberService              memberService;

    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    /**
     * 充值
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "app-recharge.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> recharge(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          Integer memberId) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (memberId != null) {
            jsonResult.setBackUrl("/login.html");
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 跳转至支付页面
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "app-selectpay.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<BigDecimal> selectpay(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              BigDecimal amount,
                                                              Map<String, Object> dataMap) {
        HttpJsonResult<BigDecimal> jsonResult = new HttpJsonResult<BigDecimal>();

        if (isNull(WebFrontSession.getLoginedUser(request,response))) {
            jsonResult.setBackUrl("/app-login.html");
            return jsonResult;
        }
        jsonResult.setData(amount);
        return jsonResult;
    }

}
