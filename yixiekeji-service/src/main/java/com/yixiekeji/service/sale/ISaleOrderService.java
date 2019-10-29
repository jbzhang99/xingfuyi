package com.yixiekeji.service.sale;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.sale.SaleOrder;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "saleOrder")
@Service(value = "saleOrderService")
public interface ISaleOrderService {

    /**
     * 根据id取得收入流水表
     * @param  saleOrderId
     * @return
     */
    @RequestMapping(value = "getSaleOrderById", method = RequestMethod.GET)
    ServiceResult<SaleOrder> getSaleOrderById(@RequestParam("saleOrderId") Integer saleOrderId);

    /**
     * 保存收入流水表
     * @param  saleOrder
     * @return
     */
    @RequestMapping(value = "saveSaleOrder", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleOrder(SaleOrder saleOrder);

    /**
    * 更新收入流水表
    * @param  saleOrder
    * @return
    */
    @RequestMapping(value = "updateSaleOrder", method = RequestMethod.POST)
    ServiceResult<Integer> updateSaleOrder(SaleOrder saleOrder);

    /**
     * 每天执行一次，定时器定时根据已经完成的订单，生成对应的佣金
     * @return
     */
    @RequestMapping(value = "jobSaveSaleOrder", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSaveSaleOrder();

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSaleOrders", method = RequestMethod.POST)
    ServiceResult<List<SaleOrder>> getSaleOrders(FeignUtil feignUtil);

    /**
     * 根据用户ID和状态统计数量
     * @param saleState
     * @param memberId
     * @return
     */
    @RequestMapping(value = "countSaleOrderBySaleStateAndMemberId", method = RequestMethod.GET)
    ServiceResult<Integer> countSaleOrderBySaleStateAndMemberId(@RequestParam("saleState") int saleState,
                                                                @RequestParam("memberId") Integer memberId);

    /**
     * 根据用户ID和状态统计金额
     * @param saleState
     * @param memberId
     * @return
     */
    @RequestMapping(value = "sumSaleOrderBySaleStateAndMemberId", method = RequestMethod.GET)
    ServiceResult<BigDecimal> sumSaleOrderBySaleStateAndMemberId(@RequestParam("saleState") int saleState,
                                                                 @RequestParam("memberId") Integer memberId);

}