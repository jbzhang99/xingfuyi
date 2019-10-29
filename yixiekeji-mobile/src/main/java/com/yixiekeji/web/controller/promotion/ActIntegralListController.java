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
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralBanner;
import com.yixiekeji.entity.integral.ActIntegralType;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberRule;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.member.IMemberSignLogsService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActIntegralBannerService;
import com.yixiekeji.service.promotion.IActIntegralService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 积分商城
 *                       
 * @Filename: ActIntegralListController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ActIntegralListController extends BaseController {

    @Resource
    private IActIntegralService       actIntegralService;

    @Resource
    private IActIntegralBannerService actIntegralBannerService;

    @Resource
    private IProductFrontService      productFrontService;

    @Resource
    private IMemberService            memberService;

    @Resource
    private IMemberSignLogsService    memberSignLogsService;

    @RequestMapping(value = "/jifen.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Map<String, Object>> integral(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageIndex);

        // 积分商城分类id
        int type = ConvertUtil.toInt(request.getParameter("type"), 0);
        // 会员等级
        int grade = ConvertUtil.toInt(request.getParameter("grade"), 1);
        // 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
        int sort = ConvertUtil.toInt(request.getParameter("sort"), 0);

        ServiceResult<List<ActIntegral>> serviceResultIntegral = actIntegralService
            .getActIntegralsFront(pager, type, ConstantsEJS.CHANNEL_3, grade, sort);
        List<ActIntegral> actIntegrals = serviceResultIntegral.getResult();
        for (ActIntegral actIntegral : actIntegrals) {
            ServiceResult<Product> resultProduct = productFrontService
                .getProductById(actIntegral.getProductId());
            actIntegral.setProductName(resultProduct.getResult().getName1());
        }
        pager = serviceResultIntegral.getPager();

        dataMap.put("actIntegrals", actIntegrals);
        dataMap.put("type", type);
        dataMap.put("grade", grade);
        dataMap.put("sort", sort);
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 积分商城轮播图
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/integral-banner.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<ActIntegralBanner>> integralBanner(HttpServletRequest request,
                                                                                HttpServletResponse response) {
        HttpJsonResult<List<ActIntegralBanner>> jsonResult = new HttpJsonResult<List<ActIntegralBanner>>();

        ServiceResult<List<ActIntegralBanner>> serviceResult = actIntegralBannerService
            .getActIntegralBannersPcMobile(ConstantsEJS.PC_MOBILE_MOBILE);
        List<ActIntegralBanner> actIntegralBanners = serviceResult.getResult();

        jsonResult.setData(actIntegralBanners);
        return jsonResult;
    }

    /**
     * 积分商城分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/integral-type.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<ActIntegralType>> integralType(HttpServletRequest request,
                                                                            HttpServletResponse response) {
        HttpJsonResult<List<ActIntegralType>> jsonResult = new HttpJsonResult<List<ActIntegralType>>();

        ServiceResult<List<ActIntegralType>> serviceResultType = actIntegralService
            .getActIntegralTypesFront();
        List<ActIntegralType> actIntegralTypes = serviceResultType.getResult();

        jsonResult.setData(actIntegralTypes);
        return jsonResult;
    }

    /**
     * 积分经验值规则
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/integral-rule.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<MemberRule> integralRule(HttpServletRequest request,
                                                                 HttpServletResponse response) {
        HttpJsonResult<MemberRule> jsonResult = new HttpJsonResult<MemberRule>();

        ServiceResult<MemberRule> resultRult = memberService
            .getMemberRule(ConstantsEJS.MEMBER_RULE_INTEGRAL_ID, MemberRule.STATE_YES);
        MemberRule memberRule = resultRult.getResult();

        jsonResult.setData(memberRule);
        return jsonResult;
    }

    /**
     * 会员是否签到
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/is-sign.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Map<String, Object>> isSign(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member memberSession = WebFrontSession.getLoginedUser(request);
        // 是否签到：1、已签；0、未签
        int isSign = 0;
        if (memberSession != null && memberSession.getId() != null) {
            Integer memberId = memberSession.getId();
            ServiceResult<Member> result = memberService.getMemberById(memberId);
            Member member = result.getResult();
            dataMap.put("member", member);

            //积分是否已经领取
            ServiceResult<Boolean> servletSignLogs = memberSignLogsService
                .isMemberSignToday(member.getId());
            if (servletSignLogs.getResult() != null && servletSignLogs.getResult() == true) {
                isSign = 1;
            }
        }
        dataMap.put("isSign", isSign);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

}
