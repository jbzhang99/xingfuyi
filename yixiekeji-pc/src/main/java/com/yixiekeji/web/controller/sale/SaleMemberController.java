package com.yixiekeji.web.controller.sale;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleMember;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleMemberService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "member")
public class SaleMemberController extends BaseController {

    @Resource
    private IMemberService     memberService;

    @Resource
    private ISaleMemberService saleMemberService;

    @Resource
    private DomainUrlUtil      domainUrlUtil;

    private final static int   PAGE_NUMBER = 20;

    /**
     * 跳转到 个人资料界面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-index.html", method = { RequestMethod.GET })
    public String saleIndex(HttpServletRequest request, HttpServletResponse response,
                            ModelMap map) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        ServiceResult<SaleMember> resultSaleMember = saleMemberService
            .getSaleMemberByMemberId(member.getId());
        SaleMember saleMember = null;
        if (resultSaleMember.getSuccess()) {
            saleMember = resultSaleMember.getResult();
        }
        if (saleMember == null) {
            saleMember = new SaleMember();
            saleMember.setReferrerName("无");
            saleMember.setSaleCode("无");
            saleMember.setIsSale(ConstantsEJS.YES_NO_0);
        }

        map.put("saleMember", saleMember);

        return "front/member/sale/saleindex";
    }

    /**
     * 申请成为推广员
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-apply.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> saleApply(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           ModelMap map) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }

        SaleMember saleMember = new SaleMember();
        saleMember.setMemberId(member.getId());
        saleMember.setMemberName(member.getName());
        saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
        saleMember.setState(SaleMember.STATE_1);
        saleMember.setGrade(SaleMember.GRADE_1);
        saleMember.setIsSale(ConstantsEJS.YES_NO_1);

        ServiceResult<Integer> resultSaleMember = saleMemberService
            .saveSaleMemberByMemberId(saleMember);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 跳转到 个人资料界面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-finance-info.html", method = { RequestMethod.GET })
    public String saleFinanceInfo(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap map) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        map.put("member", member);

        ServiceResult<SaleMember> resultSaleMember = saleMemberService
            .getSaleMemberByMemberId(member.getId());
        SaleMember saleMember = null;
        if (resultSaleMember.getSuccess()) {
            saleMember = resultSaleMember.getResult();
        }
        if (saleMember == null) { //没有创建，创建记录
            saleMember = new SaleMember();
            saleMember.setMemberId(member.getId());
            saleMember.setMemberName(member.getName());
            saleMember.setCertificateType(SaleMember.CERTIFICATETYPE_CODE);
            saleMember.setState(SaleMember.STATE_1);
            saleMember.setGrade(SaleMember.GRADE_1);
            saleMember.setIsSale(ConstantsEJS.YES_NO_0);

            ServiceResult<Integer> resultSale = saleMemberService
                .saveSaleMemberByMemberId(saleMember);
            if (resultSale.getSuccess()) {
                saleMember = saleMemberService.getSaleMemberById(member.getId()).getResult();
            }
        }

        map.put("saleMember", saleMember);

        return "front/member/sale/salefinance";
    }

    /**
     * 保存财务信息
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-finance.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> saleFinance(HttpServletRequest request,
                                                             HttpServletResponse response) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        int id = ConvertUtil.toInt(request.getParameter("id"), 0);
        if (id == 0) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();
        if (null == saleMember) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }
        if (member.getId().intValue() != saleMember.getMemberId().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，只能操作自己的数据，请勿越权！");
            return jsonResult;
        }

        saleMember
            .setCertificateType(ConvertUtil.toInt(request.getParameter("certificateType"), 1));
        saleMember.setCertificateCode(request.getParameter("certificateCode"));
        saleMember.setBankAdd(request.getParameter("bankAdd"));
        saleMember.setBankCode(request.getParameter("bankCode"));
        saleMember.setBankName(request.getParameter("bankName"));
        saleMember.setBankType(request.getParameter("bankType"));
        saleMember.setState(SaleMember.STATE_1);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 提交审核
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-submit.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> saleSubmit(HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }
        int id = ConvertUtil.toInt(request.getParameter("id"), 0);
        if (id == 0) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        ServiceResult<SaleMember> resultSaleMemberOld = saleMemberService.getSaleMemberById(id);
        if (!resultSaleMemberOld.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }

        SaleMember saleMember = resultSaleMemberOld.getResult();
        if (null == saleMember) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，请重试");
            return jsonResult;
        }
        if (member.getId().intValue() != saleMember.getMemberId().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，只能操作自己的数据，请勿越权！");
            return jsonResult;
        }

        if (SaleMember.STATE_3 == saleMember.getState().intValue()
            || SaleMember.STATE_2 == saleMember.getState().intValue()) {
            jsonResult = new HttpJsonResult<Boolean>("操作失败，审核通过不能重新提交！");
            return jsonResult;
        }
        saleMember.setState(SaleMember.STATE_2);

        ServiceResult<Integer> resultSaleMember = saleMemberService.updateSaleMember(saleMember);

        if (!resultSaleMember.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleMember.getCode())) {
                throw new RuntimeException(resultSaleMember.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleMember.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 查询我的推广用户
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale-member1.html", method = { RequestMethod.GET })
    public String saleMember1(HttpServletRequest request, HttpServletResponse response,
                              ModelMap dataMap) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerId", sessionMember.getId().toString());
        dataMap.put("q_memberName", StringUtil.nullSafeString(queryMap.get("q_memberName")));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SaleMember>> resultSaleMember = saleMemberService
            .getSaleMembers(feignUtil);
        if (resultSaleMember.getSuccess()) {
            dataMap.put("saleMembers", resultSaleMember.getResult());
            pager = resultSaleMember.getPager();
            PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
                String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
                request.getRequestURI());
            dataMap.put("page", pm);
        }

        return "front/member/sale/salemember1";
    }

    /**
     * 查询我的间接推广用户
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale-member2.html", method = { RequestMethod.GET })
    public String saleMember2(HttpServletRequest request, HttpServletResponse response,
                              ModelMap dataMap) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_referrerPid", sessionMember.getId().toString());
        dataMap.put("q_memberName", StringUtil.nullSafeString(queryMap.get("q_memberName")));
        dataMap.put("q_referrerName", StringUtil.nullSafeString(queryMap.get("q_referrerName")));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SaleMember>> resultSaleMember = saleMemberService
            .getSaleMembers(feignUtil);
        if (resultSaleMember.getSuccess()) {
            dataMap.put("saleMembers", resultSaleMember.getResult());
            pager = resultSaleMember.getPager();
            PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
                String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
                request.getRequestURI());

            StringBuilder sb = new StringBuilder();
            sb.append(domainUrlUtil.getUrlResources());
            sb.append("/member/sale-member2.html");
            sb.append("?q_memberName=");
            sb.append(StringUtil.nullSafeString(queryMap.get("q_memberName")));
            sb.append("&q_referrerName=");
            sb.append(StringUtil.nullSafeString(queryMap.get("q_referrerName")));
            pm.setUrl(sb.toString());

            dataMap.put("page", pm);
        }

        return "front/member/sale/salemember2";
    }

}
