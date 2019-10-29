package com.yixiekeji.web.controller.member;

import com.yixiekeji.core.*;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "member/coupon")
public class AppMemberCouponController extends BaseController {
    @Resource
    private ICouponService couponService;

    /**
     * 优惠券列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "app-list.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> list(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Map<String, Object> dataMap,
                                                                  Integer rownum,
                                                                  Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Integer pageSize = rownum == null || rownum == 0 ? ConstantsEJS.DEFAULT_PAGE_SIZE
            : rownum + ConstantsEJS.DEFAULT_PAGE_SIZE;
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, pageSize);
        ServiceResult<List<CouponUser>> serviceResult = couponService
            .getCouponUserByMemberId(memberId, pager);
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

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);

        dataMap.put("couponUsers", couponUsers);
        dataMap.put("page", pm);

        jsonResult.setData(dataMap);
        return jsonResult;
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
                                                               @RequestParam(value = "couponId", required = true) Integer couponId,
                                                               Integer memberId) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        ServiceResult<Boolean> receiveCoupon = couponService.receiveCoupon(couponId, member.getId());
        if (!receiveCoupon.getSuccess()) {
            return new HttpJsonResult<Boolean>(receiveCoupon.getMessage());
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        return jsonResult;
    }
}
