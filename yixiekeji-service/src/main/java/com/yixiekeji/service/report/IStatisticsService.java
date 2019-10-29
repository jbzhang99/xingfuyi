package com.yixiekeji.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.dto.PhurchaseRateDto;
import com.yixiekeji.dto.ProductSaleDto;
import com.yixiekeji.echarts.component.SeriesData;

/**
 * 统计相关服务 
 *                       
 * @Filename: IStatisticsService.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "statistics")
@Service(value = "statisticsService")
public interface IStatisticsService {
    /**
     * 订单概况统计
     * @param map
     * @return
     */
    @RequestMapping(value = "getOrderOverviewData", method = RequestMethod.POST)
    ServiceResult<List<SeriesData>> getOrderOverviewData(Map<String, String> map);

    /**
     * 退货率统计<br>
     * 退货率=退货总数/订单总数<i>(统计均为已完成的订单)</i>
     * @param querymap 
     * @return
     */
    @RequestMapping(value = "goodsReturnRate", method = RequestMethod.POST)
    ServiceResult<Map<String, List<Object>>> goodsReturnRate(Map<String, String> querymap);

    /**
     * 人均消费统计<br>
     * <em>人均消费 = 订单总金额/会员数（购买商品的会员总数）</em>
     * @param map
     * @return
     */
    @RequestMapping(value = "getCPIstatistics", method = RequestMethod.POST)
    ServiceResult<Map<String, List<Object>>> getCPIstatistics(Map<String, String> map);

    /**
     * 订单销售统计<br>
     * <i>订单销量包括订单数、订单销量量、客单价</i><br>
     * <em>客单价 = 订单总金额/订单总数</em>
     * @param map
     * @return
     */
    @RequestMapping(value = "getSaleStatistics", method = RequestMethod.POST)
    ServiceResult<Map<String, List<Object>>> getSaleStatistics(Map<String, String> querymap);

    /**
     * 商品销量
     * @param queryMap
     * @return
     */
    @RequestMapping(value = "getProductSale", method = RequestMethod.POST)
    ServiceResult<List<ProductSaleDto>> getProductSale(Map<String, String> queryMap);

    /**
     * 商品购买率统计
     * @param map
     * @return
     */
    @RequestMapping(value = "getPhurchaseRate", method = RequestMethod.POST)
    ServiceResult<PhurchaseRateDto> getPhurchaseRate(Map<String, String> map);

    /**
     * 浏览量统计
     * @param map
     * @return
     */
    @RequestMapping(value = "pvStatistics", method = RequestMethod.POST)
    ServiceResult<List<Object>> pvStatistics(Map<String, String> map);
}
