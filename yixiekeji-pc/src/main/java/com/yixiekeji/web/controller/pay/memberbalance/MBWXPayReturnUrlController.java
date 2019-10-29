package com.yixiekeji.web.controller.pay.memberbalance;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalancePayLog;
import com.yixiekeji.service.member.IMemberBalancePayLogService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "member/balance/pay")
public class MBWXPayReturnUrlController extends BaseController {
    @Resource
    private IMemberBalancePayLogService memberBalancePayLogService;

    @RequestMapping(value = "returnUrl.html")
    public @ResponseBody HttpJsonResult<Boolean> returnUrl(HttpServletResponse response,
                                                           HttpServletRequest request,
                                                           String orderNo) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (StringUtil.isEmpty(orderNo, true)) {
            log.error("微信支付，订单号为空");
            jsonResult.setData(false);
            return jsonResult;
        }

        Member member = WebFrontSession.getLoginedUser(request,response);
        if (member == null) {
            log.error("用户session过期");
            jsonResult.setData(false);
            return jsonResult;
        }

        ServiceResult<MemberBalancePayLog> serviceResult = memberBalancePayLogService
            .getByOrderSn(orderNo);
        if (!serviceResult.getSuccess()) {
            log.error("获取充值记录失败");
            jsonResult.setData(false);
            return jsonResult;
        }

        MemberBalancePayLog paylog = serviceResult.getResult();
        if (isNull(paylog)) {
            log.error("获取支付记录失败");
            jsonResult.setData(false);
            return jsonResult;
        }
        if (paylog.getPayState().intValue() == MemberBalancePayLog.PAY_SUCCESS) {
            log.error("支付成功");
            jsonResult.setData(true);
            return jsonResult;
        }
        log.error("未支付");
        jsonResult.setData(false);
        return jsonResult;
    }

    @RequestMapping(value = "returnUrlSuccess.html")
    public String returnUrlSuccess(HttpServletResponse response, HttpServletRequest request,
                                   String orderNo, ModelMap data) {
        ServiceResult<MemberBalancePayLog> serviceResult = memberBalancePayLogService
            .getByOrderSn(orderNo);
        MemberBalancePayLog paylog = serviceResult.getResult();
        if (!isNull(paylog) && paylog.getPayState().intValue() == MemberBalancePayLog.PAY_SUCCESS
            && !isNull(paylog.getPayFinishTime()) && !isNull(paylog.getTradeSn())) {
            data.put("success", true);
        } else {
            data.put("success", false);
            data.put("info", "充值失败");
        }
        return "front/member/balancepay/payresult";
    }

}
