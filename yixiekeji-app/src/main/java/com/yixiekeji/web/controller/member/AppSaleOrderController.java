package com.yixiekeji.web.controller.member;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.sale.ISaleOrderService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "member")
public class AppSaleOrderController extends BaseController {

    @Resource
    private IMemberService    memberService;

    @Resource
    private ISaleOrderService saleOrderService;

    private final static int  PAGE_NUMBER = 10;

    /**
     * 查询所有分佣列表
     * @param request
     * @param response
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app-sale-orders.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> saleOrder(HttpServletRequest request,
                                                                       HttpServletResponse response,
                                                                       ModelMap dataMap,
                                                                       Integer memberId) throws Exception {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_memberId", memberId.toString());

        dataMap.put("q_orderSn", StringUtil.nullSafeString(queryMap.get("q_orderSn")));
        dataMap.put("q_saleState", StringUtil.nullSafeString(queryMap.get("q_saleState")));
        dataMap.put("q_buyName", StringUtil.nullSafeString(queryMap.get("q_buyName")));

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleOrder>> resultSaleOrder = saleOrderService.getSaleOrders(feignUtil);

        if (resultSaleOrder.getSuccess()) {
            dataMap.put("saleOrders", resultSaleOrder.getResult());
            //            dataMap.put("pagesize", PAGE_NUMBER);
        }

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 返回分容列表JSON
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/app-sale-orders-json.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<SaleOrder>> saleApplyMoneyJson(HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            Integer memberId) {
        HttpJsonResult<List<SaleOrder>> jsonResult = new HttpJsonResult<List<SaleOrder>>();
        Member member = memberService.getMemberById(memberId).getResult();

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_memberId", member.getId().toString());

        queryMap.put("q_orderSn", StringUtil.nullSafeString(queryMap.get("q_orderSn")));
        queryMap.put("q_saleState", StringUtil.nullSafeString(queryMap.get("q_saleState")));
        queryMap.put("q_buyName", StringUtil.nullSafeString(queryMap.get("q_buyName")));

        String pageNumStr = request.getParameter("pageNum");
        int pageNum = ConvertUtil.toInt(pageNumStr, 1);
        PagerInfo pager = new PagerInfo(PAGE_NUMBER, pageNum);

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<SaleOrder>> serviceResult = saleOrderService.getSaleOrders(feignUtil);

        if (!serviceResult.getSuccess()) {
            return jsonResult;
        }

        jsonResult.setData(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    @RequestMapping(value = "/app-sale-orders-{id}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<SaleOrder> saleIndex(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             @PathVariable Integer id,
                                                             Integer memberId) throws Exception {
        HttpJsonResult<SaleOrder> jsonResult = new HttpJsonResult<SaleOrder>();
        ServiceResult<SaleOrder> resultSaleOrder = saleOrderService.getSaleOrderById(id);
        if (resultSaleOrder.getSuccess()) {
            SaleOrder saleOrder = resultSaleOrder.getResult();
            if (memberId.intValue() == saleOrder.getMemberId()) {
                jsonResult.setData(saleOrder);
            }
        }

        return jsonResult;
    }

}
