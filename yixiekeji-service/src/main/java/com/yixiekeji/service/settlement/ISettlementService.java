package com.yixiekeji.service.settlement;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.settlement.Settlement;

/**
 * 结算管理
 *                       
 * @Filename: ISettlementService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "settlement")
@Service(value = "settlementService")
public interface ISettlementService {

    /**
     * 商家结算账单生成定时任务<br>
     * <li>查询所有商家，每个商家每个结算周期生成一条结算账单
     * <li>计算周期内商家所有的订单金额合计
     * <li>计算所有订单下网单的佣金
     * <li>计算周期内发生的退货退款（当前周期结算的订单如果在下个结算周期才退款，退款结算在下个周期计算）
     * <li>每个商家一个事务，某个商家结算时发生错误不影响其他结算
     * @return
     */
    @RequestMapping(value = "jobSettlement", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSettlement();

    /**
     * 根据id取得结算表
     * @param  settlementId
     * @return
     */
    @RequestMapping(value = "getSettlementById", method = RequestMethod.GET)
    ServiceResult<Settlement> getSettlementById(@RequestParam("settlementId") Integer settlementId);

    /**
     * 保存结算表
     * @param  settlement
     * @return
     */
    @RequestMapping(value = "saveSettlement", method = RequestMethod.POST)
    ServiceResult<Integer> saveSettlement(Settlement settlement);

    /**
     * 更新结算表
     * @param settlement
     * @return
     */
    @RequestMapping(value = "updateSettlement", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSettlement(Settlement settlement);

    /**
     * 根据条件取得结算账单
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSettlements", method = RequestMethod.POST)
    ServiceResult<List<Settlement>> getSettlements(FeignUtil feignUtil);
}