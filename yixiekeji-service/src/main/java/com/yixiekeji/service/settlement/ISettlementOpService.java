package com.yixiekeji.service.settlement;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.settlement.SettlementOp;

/**
 * 结算网单管理
 *                       
 * @Filename: ISettlementOpService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "settlementOp")
@Service(value = "settlementOpService")
public interface ISettlementOpService {

    /**
     * 根据id取得结算网单表
     * @param  settlementOpId
     * @return
     */
    @RequestMapping(value = "getSettlementOpById", method = RequestMethod.GET)
    ServiceResult<SettlementOp> getSettlementOpById(@RequestParam("settlementOpId") Integer settlementOpId);

    /**
     * 保存结算网单表
     * @param  settlementOp
     * @return
     */
    @RequestMapping(value = "saveSettlementOp", method = RequestMethod.POST)
    ServiceResult<Integer> saveSettlementOp(SettlementOp settlementOp);

    /**
    * 更新结算网单表
    * @param  settlementOp
    * @return
    */
    @RequestMapping(value = "updateSettlementOp", method = RequestMethod.POST)
    ServiceResult<Integer> updateSettlementOp(SettlementOp settlementOp);

    /**
     * 根据订单号获取对应的结算网单
     * @param orderId
     * @return
     */
    @RequestMapping(value = "getSettlementOpByOId", method = RequestMethod.GET)
    ServiceResult<List<SettlementOp>> getSettlementOpByOId(@RequestParam("orderId") Integer orderId);
}