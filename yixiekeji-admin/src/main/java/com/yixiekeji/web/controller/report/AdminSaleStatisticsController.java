package com.yixiekeji.web.controller.report;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.echarts.util.EchartsDataProvider;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.report.IStatisticsService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "admin/report/orders", produces = "application/json;charset=UTF-8")
public class AdminSaleStatisticsController extends BaseController {
    @Resource
    private IOrdersService     ordersService;
    @Resource
    private ISellerService     sellerService;
    @Resource
    private IStatisticsService statisticsService;

    /**
     * 订单销量统计<br>
     * <i>订单销量包括订单数、订单销量量、客单价</i>
     * @param request
     * @param dataMap
     * @param model
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "saleOverview", method = { RequestMethod.GET })
    public String storeSale(HttpServletRequest request, ModelMap dataMap, String model,
                            Integer year, Integer month, Integer byseller, Integer sel_seller) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        //默认按年统计
        if (isNull(model) || "".equals(model))
            model = "year";

        //默认当前时间
        if (isNull(year) && isNull(month)) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        }

        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> map = new HashMap<String, String>();
        if (model.equals("year"))
            map.put("year", year + "");
        if (model.equals("month")) {
            year = cal.get(Calendar.YEAR);
            map.put("year", year + "");
            map.put("month", month + "");
        }
        if (!isNull(byseller) && byseller == 1 && !isNull(sel_seller)) {
            map.put("q_sellerId", sel_seller + "");
            dataMap.put("byseller", byseller);
            dataMap.put("sel_seller", sel_seller);
        }
        map.put("model", model);
        ServiceResult<Map<String, List<Object>>> serviceResult = statisticsService
            .getSaleStatistics(map);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);

        dataMap.put("option",
            EchartsDataProvider.getStoreSaleData(serviceResult.getResult(), model));
        dataMap.put("currentYear", year);
        dataMap.put("currentMonth", month < 10 ? "0" + month : month);
        dataMap.put("model", model);

        FeignUtil feignUtil = FeignUtil.getFeignUtil(new HashMap<String, String>(), null);
        ServiceResult<List<Seller>> sellers = sellerService.getSellers(feignUtil);
        dataMap.put("sellers", sellers.getResult());
        return "admin/report/orders/saleOverview";
    }
}
