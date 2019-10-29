package com.yixiekeji.service.order;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.order.OrderLog;

/**
 * 订单日志接口
 *
 * @Filename: IOrderLogService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "orderLog")
@Service(value = "orderLogService")
public interface IOrderLogService {

    /**
     * 根据id取得订单操作日志表
     * @param  orderLogId
     * @return
     */
    @RequestMapping(value = "getOrderLogById", method = RequestMethod.GET)
    ServiceResult<OrderLog> getOrderLogById(@RequestParam("orderLogId") Integer orderLogId);

    /**
     * 保存订单操作日志表
     * @param  orderLog
     * @return
     */
    @RequestMapping(value = "saveOrderLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveOrderLog(OrderLog orderLog);

    /**
     * 根据订单ID 取得日志
     * @param orderId
     * @return
     */
    @RequestMapping(value = "getOrderLogByOrderId", method = RequestMethod.GET)
    ServiceResult<List<OrderLog>> getOrderLogByOrderId(@RequestParam("orderId") Integer orderId);
}