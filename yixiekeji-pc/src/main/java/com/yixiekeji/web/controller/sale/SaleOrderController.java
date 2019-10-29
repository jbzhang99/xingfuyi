package com.yixiekeji.web.controller.sale;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleOrderService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

@Controller
@RequestMapping(value = "member")
public class SaleOrderController extends BaseController {

    @Resource
    private IMemberService    memberService;

    @Resource
    private ISaleOrderService saleOrderService;

    @Resource
    private DomainUrlUtil     domainUrlUtil;

    private final static int  PAGE_NUMBER = 10;

    /**
     * 查询所有分佣列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale-orders.html", method = { RequestMethod.GET })
    public String saleOrder(HttpServletRequest request, HttpServletResponse response,
                            ModelMap dataMap) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_memberId", sessionMember.getId().toString());

        dataMap.put("q_orderSn", StringUtil.nullSafeString(queryMap.get("q_orderSn")));
        dataMap.put("q_saleState", StringUtil.nullSafeString(queryMap.get("q_saleState")));
        dataMap.put("q_buyName", StringUtil.nullSafeString(queryMap.get("q_buyName")));

        StringBuilder sb = new StringBuilder();
        sb.append(domainUrlUtil.getUrlResources());
        sb.append("/member/sale-orders.html");
        sb.append("?q_orderSn=");
        sb.append(StringUtil.nullSafeString(queryMap.get("q_orderSn")));
        sb.append("&q_saleState=");
        sb.append(StringUtil.nullSafeString(queryMap.get("q_saleState")));
        sb.append("&q_buyName=");
        sb.append(StringUtil.nullSafeString(queryMap.get("q_buyName")));

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleOrder>> resultSaleOrder = saleOrderService.getSaleOrders(feignUtil);
        if (resultSaleOrder.getSuccess()) {
            dataMap.put("saleOrders", resultSaleOrder.getResult());
            pager = resultSaleOrder.getPager();
            PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
                String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
                request.getRequestURI());
            pm.setUrl(sb.toString());
            dataMap.put("page", pm);
        }

        return "front/member/sale/saleorder";
    }

    @RequestMapping(value = "/sale-orders-{id}.html", method = { RequestMethod.GET })
    public String saleIndex(HttpServletRequest request, HttpServletResponse response,
                            ModelMap dataMap, @PathVariable Integer id) throws Exception {
        Member sessionMember = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<SaleOrder> resultSaleOrder = saleOrderService.getSaleOrderById(id);
        if (resultSaleOrder.getSuccess()) {
            SaleOrder saleOrder = resultSaleOrder.getResult();
            if (sessionMember.getId().intValue() == saleOrder.getMemberId()) {
                dataMap.put("saleOrder", saleOrder);
            }
        }

        return "front/member/sale/saleorderinfo";
    }

}
