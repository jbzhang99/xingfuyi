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
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "member/coupon")
public class MemberCouponController extends BaseController {

    @Resource
    private ICouponService   couponService;

    private final static int number = 20;

    /**
     * 优惠券列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list.html", method = { RequestMethod.GET })
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Map<String, Object> dataMap, Integer rownum) {

        Member sessionMember = WebFrontSession.getLoginedUser(request);

        Integer pageSize = rownum == null || rownum == 0 ? number : rownum + number;
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, pageSize);
        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getCouponUserByMemberId(sessionMember.getId(), pager);
        List<CouponUser> couponUsers = serviceResult.getResult();
        for (CouponUser couponUser : couponUsers) {
            if (couponUser.getUseEndTime().getTime() > new Date().getTime()) {
                couponUser.setTimeout(false);
            } else {
                couponUser.setTimeout(true);
            }
            if (couponUser.getCanUse() != null && couponUser.getCanUse().intValue() == 1) {
                //未使用
                couponUser.setIsuse(false);
            } else {
                couponUser.setIsuse(true);
            }
        }

        String url = request.getRequestURI() + "";
        pager = serviceResult.getPager();
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);

        dataMap.put("couponUsers", couponUsers);
        dataMap.put("page", pm);
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("couponCount", pager.getRowsCount());

        return "h5/member/coupon/membercoupon";
    }

    @RequestMapping(value = "listmore.html", method = { RequestMethod.GET })
    public String listMore(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap, Integer pageIndex) {
        Member sessionMember = WebFrontSession.getLoginedUser(request);

        PagerInfo pager = new PagerInfo(number, pageIndex);
        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getCouponUserByMemberId(sessionMember.getId(), pager);
        List<CouponUser> couponUsers = serviceResult.getResult();
        for (CouponUser couponUser : couponUsers) {
            if (couponUser.getUseEndTime().getTime() > new Date().getTime()) {
                couponUser.setTimeout(false);
            } else {
                couponUser.setTimeout(true);
            }
            if (couponUser.getCanUse() != null && couponUser.getCanUse().intValue() == 1) {
                //未使用
                couponUser.setIsuse(false);
            } else {
                couponUser.setIsuse(true);
            }
        }
        dataMap.put("couponUsers", couponUsers);
        return "h5/member/coupon/membercouponmore";
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
        Member sessionMember = WebFrontSession.getLoginedUser(request);
        ServiceResult<Boolean> receiveCoupon = couponService.receiveCoupon(couponId,
            sessionMember.getId());
        if (!receiveCoupon.getSuccess()) {
            return new HttpJsonResult<Boolean>(receiveCoupon.getMessage());
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        return jsonResult;
    }
}
