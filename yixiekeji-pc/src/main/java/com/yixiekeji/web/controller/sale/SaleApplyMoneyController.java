package com.yixiekeji.web.controller.sale;

import java.math.BigDecimal;
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
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleApplyMoney;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleApplyMoneyService;
import com.yixiekeji.service.sale.ISaleOrderService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "member")
public class SaleApplyMoneyController extends BaseController {

    @Resource
    private IMemberService         memberService;

    @Resource
    private ISaleOrderService      saleOrderService;

    @Resource
    private ISaleApplyMoneyService saleApplyMoneyService;

    private final static int       PAGE_NUMBER = 10;

    /**
     * 查询所有分佣列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale-apply-money.html", method = { RequestMethod.GET })
    public String saleApplyMoney(HttpServletRequest request, HttpServletResponse response,
                                 ModelMap dataMap) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_memberId", sessionMember.getId().toString());
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SaleApplyMoney>> resultSaleApplyMoney = saleApplyMoneyService
            .getSaleApplyMoneys(feignUtil);
        pager = resultSaleApplyMoney.getPager();

        if (resultSaleApplyMoney.getSuccess()) {
            dataMap.put("saleApplyMoneys", resultSaleApplyMoney.getResult());
            PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
                String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
                request.getRequestURI());
            dataMap.put("page", pm);
        }

        //预计收入
        ServiceResult<Integer> resultCount1 = saleOrderService
            .countSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_1, sessionMember.getId());
        if (resultCount1.getSuccess()) {
            int countState1 = resultCount1.getResult();
            dataMap.put("countState1", countState1);
        }
        ServiceResult<BigDecimal> resultSum1 = saleOrderService
            .sumSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_1, sessionMember.getId());
        if (resultSum1.getSuccess()) {
            BigDecimal sumState1 = resultSum1.getResult();
            dataMap.put("sumState1", sumState1);
        }

        //可以体现
        ServiceResult<Integer> resultCount2 = saleOrderService
            .countSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_2, sessionMember.getId());
        if (resultCount2.getSuccess()) {
            int countState2 = resultCount2.getResult();
            dataMap.put("countState2", countState2);
        }
        ServiceResult<BigDecimal> resultSum2 = saleOrderService
            .sumSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_2, sessionMember.getId());
        if (resultSum2.getSuccess()) {
            BigDecimal sumState2 = resultSum2.getResult();
            dataMap.put("sumState2", sumState2);
        }

        //提现中
        ServiceResult<Integer> resultCount3 = saleOrderService
            .countSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_3, sessionMember.getId());
        if (resultCount3.getSuccess()) {
            int countState3 = resultCount3.getResult();
            dataMap.put("countState3", countState3);
        }
        ServiceResult<BigDecimal> resultSum3 = saleOrderService
            .sumSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_3, sessionMember.getId());
        if (resultSum3.getSuccess()) {
            BigDecimal sumState3 = resultSum3.getResult();
            dataMap.put("sumState3", sumState3);
        }

        //提现完成
        ServiceResult<Integer> resultCount4 = saleOrderService
            .countSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_4, sessionMember.getId());
        if (resultCount4.getSuccess()) {
            int countState4 = resultCount4.getResult();
            dataMap.put("countState4", countState4);
        }
        ServiceResult<BigDecimal> resultSum4 = saleOrderService
            .sumSaleOrderBySaleStateAndMemberId(SaleOrder.SALE_STATE_4, sessionMember.getId());
        if (resultSum4.getSuccess()) {
            BigDecimal sumState4 = resultSum4.getResult();
            dataMap.put("sumState4", sumState4);
        }

        return "front/member/sale/saleapplymoney";
    }

    /**
     * 申请成为推广员
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/sale-apply-money-save.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> saleApplyMoneySave(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    ModelMap map) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<Member> serviceResult = new ServiceResult<Member>();
        serviceResult = memberService.getMemberById(sessionMember.getId());

        Member member = null;
        if (serviceResult.getSuccess()) {
            member = serviceResult.getResult();
        }

        SaleApplyMoney saleApplyMoney = new SaleApplyMoney();
        saleApplyMoney.setMemberId(member.getId());
        saleApplyMoney.setMemberName(member.getName());
        saleApplyMoney.setState(ConstantsEJS.YES_NO_0);

        ServiceResult<Integer> resultSaleApplyMoney = saleApplyMoneyService
            .saveSaleApplyMoney(saleApplyMoney);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        if (!resultSaleApplyMoney.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(resultSaleApplyMoney.getCode())) {
                throw new RuntimeException(resultSaleApplyMoney.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(resultSaleApplyMoney.getMessage());
            }
        }
        return jsonResult;
    }

}
