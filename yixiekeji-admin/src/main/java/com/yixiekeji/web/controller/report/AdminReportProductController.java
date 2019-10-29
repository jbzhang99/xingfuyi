package com.yixiekeji.web.controller.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.dto.ProductDayDto;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.service.order.IOrdersProductService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "admin/report", produces = "application/json;charset=UTF-8")
public class AdminReportProductController extends BaseController {

    @Resource
    private IOrdersProductService ordersProductService;
    @Resource
    private ISellerService        sellerService;

    @RequestMapping(value = "productday", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        FeignUtil feignUtil = FeignUtil.getFeignUtil(new HashMap<String, String>(), null);
        ServiceResult<List<Seller>> sellers = sellerService.getSellers(feignUtil);
        dataMap.put("sellers", sellers.getResult());
        return "admin/report/reportproductday";
    }

    @RequestMapping(value = "productday/list", method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<ProductDayDto>> productDayList(HttpServletRequest request,
                                                              Map<String, Object> dataMap) {
        HttpJsonResult<List<ProductDayDto>> jsonResult = new HttpJsonResult<List<ProductDayDto>>();
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        if (StringUtil.isEmpty(queryMap.get("q_startTime"), true)
            && StringUtil.isEmpty(queryMap.get("q_endTime"), true)) {
            String day = TimeUtil.getToday();
            queryMap.put("q_startTime", day + " 00:00:00");
            queryMap.put("q_endTime", day + " 23:59:59");
        }

        ServiceResult<List<ProductDayDto>> serviceResult = ordersProductService
            .getProductDayDto(queryMap);
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            List<ProductDayDto> productDayDtos = serviceResult.getResult();

            BigDecimal moneyAmount = new BigDecimal(0);
            Integer number = 0;

            for (ProductDayDto productDayDto : productDayDtos) {
                moneyAmount = moneyAmount.add(productDayDto.getMoneyAmount());
                number += productDayDto.getNumber();
            }

            List<ProductDayDto> listFooter = new ArrayList<ProductDayDto>();
            ProductDayDto productDayDtoFooter = new ProductDayDto();
            productDayDtoFooter.setSellerName("统计：");
            productDayDtoFooter.setMoneyAmount(moneyAmount);
            productDayDtoFooter.setNumber(number);

            listFooter.add(productDayDtoFooter);

            jsonResult.setRows(productDayDtos);
            jsonResult.setFooter(listFooter);
        }

        return jsonResult;
    }
}
