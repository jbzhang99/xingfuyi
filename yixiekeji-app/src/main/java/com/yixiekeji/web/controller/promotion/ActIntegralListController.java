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

    private final static int          INTEGRAL_PAGESIZE = 20;

    @RequestMapping(value = "/jifen.html", method = RequestMethod.GET)
    public String integral(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap) {
        Member memberSession = WebFrontSession.getLoginedUser(request,response);
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

        ServiceResult<MemberRule> resultRult = memberService
            .getMemberRule(ConstantsEJS.MEMBER_RULE_INTEGRAL_ID, MemberRule.STATE_YES);
        MemberRule memberRule = resultRult.getResult();
        dataMap.put("memberRule", memberRule);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, INTEGRAL_PAGESIZE);
        String typeStr = request.getParameter("type");
        int type = ConvertUtil.toInt(typeStr, 0);

        String gradeStr = request.getParameter("grade");
        int grade = ConvertUtil.toInt(gradeStr, 1);

        // 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
        String sortStr = request.getParameter("sort");
        int sort = ConvertUtil.toInt(sortStr, 0);

        ServiceResult<List<ActIntegral>> serviceResultIntegral = actIntegralService
            .getActIntegralsFront(pager, type, ConstantsEJS.CHANNEL_3, grade, sort);
        List<ActIntegral> actIntegrals = serviceResultIntegral.getResult();
        for (ActIntegral actIntegral : actIntegrals) {
            ServiceResult<Product> resultProduct = productFrontService
                .getProductById(actIntegral.getProductId());
            actIntegral.setProductName(resultProduct.getResult().getName1());
        }

        ServiceResult<List<ActIntegralBanner>> serviceResult = actIntegralBannerService
            .getActIntegralBannersPcMobile(ConstantsEJS.PC_MOBILE_MOBILE);
        List<ActIntegralBanner> actIntegralBanners = serviceResult.getResult();

        ServiceResult<List<ActIntegralType>> serviceResultType = actIntegralService
            .getActIntegralTypesFront();
        List<ActIntegralType> actIntegralTypes = serviceResultType.getResult();
        String typeName = "全部";
        if (type != 0) {
            ActIntegralType actIntegralType = null;
            for (int i = 0; i < actIntegralTypes.size(); i++) {
                actIntegralType = actIntegralTypes.get(i);
                if (actIntegralType.getId().intValue() == type) {
                    typeName = actIntegralType.getName();
                }
            }
        }

        dataMap.put("actIntegrals", actIntegrals);
        dataMap.put("actIntegralBanners", actIntegralBanners);
        dataMap.put("actIntegralTypes", actIntegralTypes);
        dataMap.put("type", type);
        dataMap.put("typeName", typeName);
        dataMap.put("grade", grade);
        dataMap.put("sort", sort);
        dataMap.put("pagesize", INTEGRAL_PAGESIZE);

        return "h5/promotion/actintegrallist";
    }

    /**
     * 返回json结果
     * @param request
     * @param stack
     * @return
     */
    @RequestMapping(value = "/jifenJson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<ActIntegral>> jifenJson(HttpServletRequest request,
                                                                     Map<String, Object> dataMap) {
        HttpJsonResult<List<ActIntegral>> jsonResult = new HttpJsonResult<List<ActIntegral>>();
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, INTEGRAL_PAGESIZE);
        String typeStr = request.getParameter("type");
        int type = ConvertUtil.toInt(typeStr, 0);

        String gradeStr = request.getParameter("grade");
        int grade = ConvertUtil.toInt(gradeStr, 1);

        // 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
        String sortStr = request.getParameter("sort");
        int sort = ConvertUtil.toInt(sortStr, 0);

        ServiceResult<List<ActIntegral>> serviceResultIntegral = actIntegralService
            .getActIntegralsFront(pager, type, ConstantsEJS.CHANNEL_3, grade, sort);
        List<ActIntegral> actIntegrals = serviceResultIntegral.getResult();
        for (ActIntegral actIntegral : actIntegrals) {
            ServiceResult<Product> resultProduct = productFrontService
                .getProductById(actIntegral.getProductId());
            actIntegral.setProductName(resultProduct.getResult().getName1());
        }

        jsonResult.setRows(actIntegrals);
        jsonResult.setTotal(actIntegrals.size());
        return jsonResult;
    }
}
