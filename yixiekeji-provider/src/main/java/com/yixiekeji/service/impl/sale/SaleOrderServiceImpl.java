package com.yixiekeji.service.impl.sale;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.sale.SaleOrder;
import com.yixiekeji.model.sale.SaleOrderModel;
import com.yixiekeji.service.sale.ISaleOrderService;

@RestController
public class SaleOrderServiceImpl implements ISaleOrderService {
    private static Logger  log = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

    @Resource
    private SaleOrderModel saleOrderModel;

    /**
    * 根据id取得收入流水表
    * @param  saleOrderId
    * @return
    */
    @Override
    public ServiceResult<SaleOrder> getSaleOrderById(@RequestParam("saleOrderId") Integer saleOrderId) {
        ServiceResult<SaleOrder> result = new ServiceResult<SaleOrder>();
        try {
            result.setResult(saleOrderModel.getSaleOrderById(saleOrderId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleOrderService][getSaleOrderById]根据id[" + saleOrderId
                      + "]取得收入流水表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error(
                "[ISaleOrderService][getSaleOrderById]根据id[" + saleOrderId + "]取得收入流水表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 保存收入流水表
     * @param  saleOrder
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSaleOrder(@RequestBody SaleOrder saleOrder) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleOrderModel.saveSaleOrder(saleOrder));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleOrderService][saveSaleOrder]保存收入流水表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleOrderService][saveSaleOrder]保存收入流水表时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新收入流水表
    * @param  saleOrder
    * @return
    * @see com.yixiekeji.service.SaleOrderService#updateSaleOrder(SaleOrder)
    */
    @Override
    public ServiceResult<Integer> updateSaleOrder(@RequestBody SaleOrder saleOrder) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(saleOrderModel.updateSaleOrder(saleOrder));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleOrderService][updateSaleOrder]更新收入流水表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleOrderService][updateSaleOrder]更新收入流水表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 每天执行一次，定时器定时根据已经完成的订单，生成对应的佣金
     * @return
     * @see com.yixiekeji.service.sale.ISaleOrderService#jobSaveSaleOrder()
     */
    @Override
    public ServiceResult<Boolean> jobSaveSaleOrder() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(saleOrderModel.jobSaveSaleOrder());
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[ISaleOrderService][jobSaveSaleOrder]更新收入流水表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleOrderService][jobSaveSaleOrder]更新收入流水表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 分页查询佣金流水
     * @param queryMap
     * @param pager
     * @return
     * @see com.yixiekeji.service.sale.ISaleOrderService#getSaleOrders(java.util.Map, com.yixiekeji.core.PagerInfo)
     */
    @Override
    public ServiceResult<List<SaleOrder>> getSaleOrders(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SaleOrder>> serviceResult = new ServiceResult<List<SaleOrder>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(saleOrderModel.getSaleOrders(queryMap, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ISaleMemberService][getSaleOrders]查询分页查询佣金流水表发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据用户ID和状态统计数量
     * @param saleState
     * @param memberId
     * @return
     * @see com.yixiekeji.service.sale.ISaleOrderService#countSaleOrderBySaleStateAndMemberId(int, java.lang.Integer)
     */
    @Override
    public ServiceResult<Integer> countSaleOrderBySaleStateAndMemberId(@RequestParam("saleState") int saleState,
                                                                       @RequestParam("memberId") Integer memberId) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                saleOrderModel.countSaleOrderBySaleStateAndMemberId(saleState, memberId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleOrderService][countSaleOrderBySaleStateAndMemberId]根据用户ID和状态统计数量：", e);
        }
        return result;
    }

    /**
     * 根据用户ID和状态统计金额
     * @param saleState
     * @param memberId
     * @return
     * @see com.yixiekeji.service.sale.ISaleOrderService#sumSaleOrderBySaleStateAndMemberId(int, java.lang.Integer)
     */
    @Override
    public ServiceResult<BigDecimal> sumSaleOrderBySaleStateAndMemberId(@RequestParam("saleState") int saleState,
                                                                        @RequestParam("memberId") Integer memberId) {
        ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
        try {
            result
                .setResult(saleOrderModel.sumSaleOrderBySaleStateAndMemberId(saleState, memberId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[ISaleOrderService][sumSaleOrderBySaleStateAndMemberId]根据用户ID和状态统计金额：", e);
        }
        return result;
    }
}