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
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralBanner;
import com.yixiekeji.entity.integral.ActIntegralType;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberGradeConfig;
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

    private final static int          PAGE_NUMBER = 20;

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

            //积分是否已经领取
            ServiceResult<Boolean> servletSignLogs = memberSignLogsService
                .isMemberSignToday(member.getId());
            if (servletSignLogs.getResult() != null && servletSignLogs.getResult() == true) {
                isSign = 1;
            }

            dataMap.put("gradeValue", gradeValue);
        }
        dataMap.put("isSign", isSign);

        ServiceResult<MemberRule> resultRult = memberService
            .getMemberRule(ConstantsEJS.MEMBER_RULE_INTEGRAL_ID, MemberRule.STATE_YES);
        MemberRule memberRule = resultRult.getResult();
        dataMap.put("memberRule", memberRule);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);
        String typeStr = request.getParameter("type");
        int type = ConvertUtil.toInt(typeStr, 0);

        String gradeStr = request.getParameter("grade");
        int grade = ConvertUtil.toInt(gradeStr, 1);

        // 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
        String sortStr = request.getParameter("sort");
        int sort = ConvertUtil.toInt(sortStr, 0);

        ServiceResult<List<ActIntegral>> serviceResultIntegral = actIntegralService
            .getActIntegralsFront(pager, type, ConstantsEJS.CHANNEL_2, grade, sort);
        List<ActIntegral> actIntegrals = serviceResultIntegral.getResult();
        pager = serviceResultIntegral.getPager();
        for (ActIntegral actIntegral : actIntegrals) {
            ServiceResult<Product> resultProduct = productFrontService
                .getProductById(actIntegral.getProductId());
            actIntegral.setProductName(resultProduct.getResult().getName1());
        }

        ServiceResult<List<ActIntegralBanner>> serviceResult = actIntegralBannerService
            .getActIntegralBannersPcMobile(ConstantsEJS.PC_MOBILE_PC);
        List<ActIntegralBanner> actIntegralBanners = serviceResult.getResult();

        ServiceResult<List<ActIntegralType>> serviceResultType = actIntegralService
            .getActIntegralTypesFront();
        List<ActIntegralType> actIntegralTypes = serviceResultType.getResult();

        StringBuilder url = new StringBuilder();
        url.append(request.getRequestURI());
        url.append("?type=");
        url.append(type);
        url.append("&grade=");
        url.append(grade);
        url.append("&sort=");
        url.append(sort);

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url.toString());

        dataMap.put("actIntegrals", actIntegrals);
        dataMap.put("actIntegralBanners", actIntegralBanners);
        dataMap.put("actIntegralTypes", actIntegralTypes);
        dataMap.put("page", pm);
        dataMap.put("type", type);
        dataMap.put("grade", grade);
        dataMap.put("sort", sort);

        return "front/promotion/actintegrallist";
    }
}
