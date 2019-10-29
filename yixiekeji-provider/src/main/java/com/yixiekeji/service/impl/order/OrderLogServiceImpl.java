package com.yixiekeji.service.impl.order;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.order.OrderLog;
import com.yixiekeji.model.order.OrderLogModel;
import com.yixiekeji.service.order.IOrderLogService;

@RestController
public class OrderLogServiceImpl implements IOrderLogService {
    private static Logger log = LoggerFactory.getLogger(OrderLogServiceImpl.class);

    @Resource
    private OrderLogModel orderLogModel;

    @Override
    public ServiceResult<OrderLog> getOrderLogById(@RequestParam("orderLogId") Integer orderLogId) {
        ServiceResult<OrderLog> serviceResult = new ServiceResult<OrderLog>();
        try {
            serviceResult.setResult(orderLogModel.getOrderLogById(orderLogId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrderLogService][getOrderLogById]根据id[" + orderLogId + "]取得订单操作日志表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderLogService][getOrderLogById]根据id[" + orderLogId + "]取得订单操作日志表时出现未知异常：",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> saveOrderLog(@RequestBody OrderLog orderLog) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(orderLogModel.saveOrderLog(orderLog));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrderLogService][saveOrderLog]保存订单操作日志表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderLogService][saveOrderLog] param:" + JSON.toJSONString(orderLog));
            log.error("[OrderLogService][saveOrderLog]保存订单操作日志表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<OrderLog>> getOrderLogByOrderId(@RequestParam("orderId") Integer orderId) {
        ServiceResult<List<OrderLog>> serviceResult = new ServiceResult<List<OrderLog>>();
        try {
            serviceResult.setResult(orderLogModel.getOrderLogByOrderId(orderId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[OrderLogService][getOrderLogByOrderId]根据订单ID取得订单日志时出现异常：" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderLogService][getOrderLogByOrderId]根据订单ID取得订单日志时发生异常:", e);
        }
        return serviceResult;

    }
}