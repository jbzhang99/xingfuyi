package com.yixiekeji.web.controller.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 
 * 我的优惠劵                      
 * @Filename: CouponUserController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class CouponUserController extends BaseController {

    @Resource
    private ICouponService couponService;

    @Resource
    private IMemberService memberService;

    @RequestMapping(value = "/coupon-use.html", method = { RequestMethod.GET })
    public String couponUse(HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> dataMap) {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, 5);
        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getCouponUserByMemberIdAndUse(sessionMember.getId(), pager, 1);
        pager = serviceResult.getPager();
        List<CouponUser> couponUsers = serviceResult.getResult();
        for (CouponUser couponUser : couponUsers) {
            if (couponUser.getUseEndTime().getTime() > new Date().getTime()) {
                couponUser.setTimeout(false);
            } else {
                couponUser.setTimeout(true);
            }
        }

        String url = request.getRequestURI() + "";

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);

        dataMap.put("couponUsers", couponUsers);
        dataMap.put("page", pm);

        return "front/member/ordercenter/couponuser";
    }

    @RequestMapping(value = "/coupon-use-yes.html", method = { RequestMethod.GET })
    public String couponUseYes(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> dataMap) {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, 5);
        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getCouponUserByMemberIdAndUse(sessionMember.getId(), pager, 0);
        List<CouponUser> couponUsers = serviceResult.getResult();
        pager = serviceResult.getPager();

        String url = request.getRequestURI() + "";

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);

        dataMap.put("couponUsers", couponUsers);
        dataMap.put("page", pm);

        return "front/member/ordercenter/couponuseryes";
    }

    /**
     * 用户在线领取优惠券
     * @param request
     * @param response
     * @param couponId
     * @return
     */
    @RequestMapping(value = "/reveivecoupon.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> reveiveCoupon(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestParam(value = "couponId", required = true) Integer couponId) {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Boolean> receiveCoupon = couponService.receiveCoupon(couponId,
            sessionMember.getId());
        if (!receiveCoupon.getSuccess()) {
            return new HttpJsonResult<Boolean>(receiveCoupon.getMessage());
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        return jsonResult;
    }

}
