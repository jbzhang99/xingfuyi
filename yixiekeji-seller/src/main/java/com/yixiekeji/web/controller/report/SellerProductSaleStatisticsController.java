package com.yixiekeji.web.controller.report;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dto.ProductSaleDto;
import com.yixiekeji.echarts.util.EchartsDataProvider;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.service.report.IStatisticsService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商品销量统计Controller
 *                       
 * @Filename: SellerProductSaleStatisticsController.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Controller
@RequestMapping(value = "seller/report/product")
public class SellerProductSaleStatisticsController extends BaseController {
    @Resource
    private ISellerService     sellerService;
    @Resource
    private IOrdersService     ordersService;
    @Resource
    private IStatisticsService statisticsService;

    @RequestMapping(value = "productSale", method = RequestMethod.GET)
    public String productSale(HttpServletRequest request, ModelMap dataMap, String model,
                              Integer year, Integer month, Integer productId, String proName) {
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

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        if (model.equals("year"))
            queryMap.put("year", year + "");
        if (model.equals("month")) {
            year = cal.get(Calendar.YEAR);
            queryMap.put("year", year + "");
            queryMap.put("month", month + "");
        }

        queryMap.put("model", model);
        queryMap.put("status", "3,4,5");
        if (productId == null || productId.intValue() == 0) {
            dataMap.put("option",
                EchartsDataProvider.getProductSale(new ArrayList<ProductSaleDto>(), model));
        } else {
            queryMap.put("productId", productId + "");
            ServiceResult<List<ProductSaleDto>> serviceResult = statisticsService
                .getProductSale(queryMap);
            if (!serviceResult.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                    throw new RuntimeException(serviceResult.getMessage());
                } else {
                    throw new BusinessException(serviceResult.getMessage());
                }
            }
            dataMap.put("option",
                EchartsDataProvider.getProductSale(serviceResult.getResult(), model));
        }

        dataMap.put("currentYear", year);
        dataMap.put("currentMonth", month < 10 ? "0" + month : month);
        dataMap.put("proName", proName);
        dataMap.put("productId", productId);
        dataMap.put("model", model);
        dataMap.put("seller", WebSellerSession.getSellerUser(request).getSellerId());
        return "seller/report/product/productSale";
    }
}
