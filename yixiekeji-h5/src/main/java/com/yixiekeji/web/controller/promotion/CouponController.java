package com.yixiekeji.web.controller.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 优惠券controller
 * 
 * @Filename: CouponController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class CouponController extends BaseController {

    @Resource
    private ICouponService  couponService;

    public static final int COUPON_PAGE_SIZE = 20;

    /**
     * 优惠券列表页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/coupon.html", method = { RequestMethod.GET })
    public String coupon(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> dataMap) {

        Member member = WebFrontSession.getLoginedUser(request);
        Integer memberId = 0;
        if (member != null) {
            memberId = member.getId();
        }

        // 排序
        String sortStr = request.getParameter("s");
        Integer sort = ConvertUtil.toInt(sortStr, 0);
        dataMap.put("sort", sort);

        // 分页
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, COUPON_PAGE_SIZE);

        ServiceResult<List<Coupon>> couponResult = couponService.getCouponsForList(memberId, sort,
            ConstantsEJS.CHANNEL_3, pager);
        if (!couponResult.getSuccess()) {
            dataMap.put("info", couponResult.getMessage());
            return "h5/commons/error";
        }
        pager = couponResult.getPager();
        dataMap.put("couponList", couponResult.getResult());
        dataMap.put("total", couponResult.getPager().getRowsCount());
        dataMap.put("pageSize", COUPON_PAGE_SIZE);

        return "h5/promotion/couponlist";
    }

    /**
     * 返回json结果
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/couponjson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<Coupon>> searchJson(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> dataMap) {

        Member member = WebFrontSession.getLoginedUser(request);
        Integer memberId = 0;
        if (member != null) {
            memberId = member.getId();
        }

        HttpJsonResult<List<Coupon>> jsonResult = new HttpJsonResult<List<Coupon>>();
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, COUPON_PAGE_SIZE);
        String sortStr = request.getParameter("s");
        int sort = ConvertUtil.toInt(sortStr, 0);

        ServiceResult<List<Coupon>> couponRlt = couponService.getCouponsForList(memberId, sort,
            ConstantsEJS.CHANNEL_3, pager);
        List<Coupon> coupons = couponRlt.getResult();
        jsonResult.setRows(coupons);

        return jsonResult;
    }
}
