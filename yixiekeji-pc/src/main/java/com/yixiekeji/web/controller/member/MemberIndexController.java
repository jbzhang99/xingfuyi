package com.yixiekeji.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberGradeConfig;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户中心
 *                       
 * @Filename: MemberIndexController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberIndexController extends BaseController {

    @Resource
    private IMemberService       memberService;

    @Resource
    private IOrdersService       ordersService;

    @Resource
    private ICouponService       couponService;

    @Resource
    private IProductFrontService productFrontService;

    @RequestMapping(value = "/index.html", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) {

        Member member = WebFrontSession.getLoginedUser(request, response);
        Integer memberId = member.getId();
        ServiceResult<Member> result = memberService.getMemberById(memberId);
        dataMap.put("member", result.getResult());

        ServiceResult<MemberGradeConfig> gradeConfigResult = memberService
            .getMemberGradeConfig(ConstantsEJS.MEMBER_GRADE_CONFIG_ID);
        MemberGradeConfig memberGradeConfig = gradeConfigResult.getResult();
        int gradeValue = 0;
        if (member.getGrade().intValue() == Member.GRADE_1) {
            gradeValue = memberGradeConfig.getGrade2().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_2) {
            gradeValue = memberGradeConfig.getGrade3().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_3) {
            gradeValue = memberGradeConfig.getGrade4().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_4) {
            gradeValue = memberGradeConfig.getGrade5().intValue() - member.getGradeValue();
        } else if (member.getGrade().intValue() == Member.GRADE_5) {
        }
        dataMap.put("gradeValue", gradeValue);

        //待支付订单数
        ServiceResult<Integer> numResult = ordersService.getOrderNumByMIdAndState(memberId,
            Orders.ORDER_STATE_1);
        dataMap.put("toBepaidOrders", numResult.getResult());

        //待收货订单数
        numResult = ordersService.getOrderNumByMIdAndState(memberId, Orders.ORDER_STATE_4);
        dataMap.put("toBeReceivedOrders", numResult.getResult());

        //待评价订单数
        numResult = ordersService.getOrderNumByMIdAndEvaluateState(memberId);
        dataMap.put("toBeEvaluateOrders", numResult.getResult());

        //优惠劵
        ServiceResult<Integer> couResult = couponService.countCouponUserByMemberId(memberId);
        dataMap.put("couponNum", couResult.getResult());

        Map<String, String> queryMap = new HashMap<String, String>();
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        pager.setPageSize(ConstantsEJS.DEFAULT_ORDER_PAGE_SIZE);
        queryMap.put("q_memberId", String.valueOf(member.getId()));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<Orders>> serviceResult = ordersService
            .getShowOrderWithOrderProduct(feignUtil);
        dataMap.put("orders", serviceResult.getResult());

        ServiceResult<List<Product>> serviceResultLeft = productFrontService
            .getProductMemberByTop(10);
        dataMap.put("products", serviceResultLeft.getResult());

        return "front/member/userindex";
    }
}
