package com.yixiekeji.web.controller.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
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

    public static final int COUPON_PAGE_SIZE = 18;

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

        Member member = WebFrontSession.getLoginedUser(request,response);
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
            ConstantsEJS.CHANNEL_2, pager);
        pager = couponResult.getPager();
        if (!couponResult.getSuccess()) {
            dataMap.put("info", couponResult.getMessage());
            return "front/commons/error";
        }

        dataMap.put("couponList", couponResult.getResult());

        String url = request.getRequestURI() + "";
        if (sort != 0) {
            url = url + "?s=" + sort;
        }
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);
        dataMap.put("page", pm);

        return "front/promotion/couponlist";
    }

}
