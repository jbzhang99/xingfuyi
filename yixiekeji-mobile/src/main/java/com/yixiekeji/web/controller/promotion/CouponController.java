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
    private ICouponService couponService;

    /**
     * 优惠券列表页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/coupon.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> coupon(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = WebFrontSession.getLoginedUser(request);
        Integer memberId = 0;
        if (member != null) {
            memberId = member.getId();
        }

        // 排序
        Integer sort = ConvertUtil.toInt(request.getParameter("sort"), 0);
        dataMap.put("sort", sort);

        // 分页
        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageIndex);

        ServiceResult<List<Coupon>> couponResult = couponService.getCouponsForList(memberId, sort,
            ConstantsEJS.CHANNEL_3, pager);
        if (!couponResult.getSuccess()) {
            jsonResult.setMessage(couponResult.getMessage());
            return jsonResult;
        }
        pager = couponResult.getPager();
        dataMap.put("couponList", couponResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

}
