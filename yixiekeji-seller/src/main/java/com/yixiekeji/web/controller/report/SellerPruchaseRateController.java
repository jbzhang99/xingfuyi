package com.yixiekeji.web.controller.report;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.dto.PhurchaseRateDto;
import com.yixiekeji.echarts.util.EchartsDataProvider;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.report.IStatisticsService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 购买率统计Controller
 *                       
 * @Filename: SellerPruchaseRateController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Controller
@RequestMapping(value = "seller/report/product")
public class SellerPruchaseRateController extends BaseController {
    @Resource
    private IOrdersProductService ordersProductService;
    @Resource
    private IStatisticsService    statisticsService;

    /**
     * 购买率统计
     * 购买率=有效订单数/总访客数
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "phurchaseRate", method = RequestMethod.GET)
    public String phurchaseRate(HttpServletRequest request, ModelMap dataMap, String model,
                                Integer year, Integer month) {
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

        Map<String, String> map = new HashMap<String, String>();
        if (model.equals("year"))
            map.put("year", year + "");
        if (model.equals("month")) {
            year = cal.get(Calendar.YEAR);
            map.put("year", year + "");
            map.put("month", month + "");
        }
        map.put("model", model);
        map.put("q_sellerId", WebSellerSession.getSellerUser(request).getSellerId() + "");
        ServiceResult<PhurchaseRateDto> dtolist = statisticsService.getPhurchaseRate(map);

        dataMap.put("model", model);
        dataMap.put("currentYear", year);
        dataMap.put("currentMonth", month < 10 ? "0" + month : month);
        dataMap.put("option",
            EchartsDataProvider.getPhurchaseRateData(dtolist.getResult(), model, year, month));
        return "seller/report/product/phurchaseRate";
    }
}
