package com.yixiekeji.service.impl.order;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dto.OrderDayDto;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.model.order.OrdersModel;
import com.yixiekeji.service.order.IOrdersService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.order.OrderCommitVO;
import com.yixiekeji.vo.order.OrderSuccessVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class OrdersServiceImpl implements IOrdersService {
    private static Logger log = LoggerFactory.getLogger(OrdersServiceImpl.class);

    @Resource
    private OrdersModel   ordersModel;

    @Override
    public ServiceResult<Orders> getOrdersById(@RequestParam("ordersId") Integer ordersId) {
        ServiceResult<Orders> serviceResult = new ServiceResult<Orders>();
        try {
            serviceResult.setResult(ordersModel.getOrdersById(ordersId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[OrdersService][getOrdersById]根据id[" + ordersId + "]取得订单表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][getOrdersById]根据id[" + ordersId + "]取得订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Orders> getOrdersBySn(@RequestParam("orderSn") String orderSn) {
        ServiceResult<Orders> serviceResult = new ServiceResult<Orders>();
        try {
            serviceResult.setResult(ordersModel.getOrdersBySn(orderSn));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][getOrdersBySn]根据orderSn[" + orderSn + "]取得订单表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][getOrdersBySn]根据orderSn[" + orderSn + "]取得订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> getOrderSnById(@RequestParam("ordersId") Integer ordersId) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            serviceResult.setResult(ordersModel.getOrderSnById(ordersId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][getOrderSnById]根据id[" + ordersId + "]取得订单号时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][getOrderSnById]根据id[" + ordersId + "]取得订单号时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Orders>> getSonOrders(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<Orders>> serviceResult = new ServiceResult<List<Orders>>();
        serviceResult.setPager(pager);
        try {
            queryMap.put("q_isParent", "0");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(ordersModel.getOrdersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<Orders> list = ordersModel.getOrders(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][getOrders]根据条件取得订单表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][getOrders] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[OrdersService][getOrders]根据条件取得订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> updateOrdersBySeller(@RequestBody FeignObjectUtil feignObjectUtil,
                                                       @RequestParam("type") int type) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(ordersModel.updateOrdersBySeller(feignObjectUtil.getOrders(),
                type, feignObjectUtil.getSellerUser()));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][updateOrdersBySeller]更新订单表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][updateOrdersBySeller]更新订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> confirmOrdersBySeller(@RequestParam("orderId") Integer orderId,
                                                        @RequestBody SellerUser sellerUser) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.confirmOrdersBySeller(orderId, sellerUser));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][confirmOrdersBySeller]商家确认订单表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][confirmOrdersBySeller]商家确认订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> updateOrdersByAdmin(@RequestBody FeignObjectUtil feignObjectUtil,
                                                      @RequestParam("type") int type) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(ordersModel.updateOrdersByAdmin(feignObjectUtil.getOrders(),
                type, feignObjectUtil.getSystemAdmin()));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][updateOrdersByAdmin]更新订单表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][updateOrdersByAdmin]更新订单表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getOrderNumByMIdAndState(@RequestParam("memberId") Integer memberId,
                                                           @RequestParam("orderState") Integer orderState) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(ordersModel.getOrderNumByMIdAndState(memberId, orderState));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[OrdersService][getOrderNumByMIdAndState]根据会员ID，订单状态获取子订单数量时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrdersService][getOrderNumByMIdAndState]根据会员ID，订单状态获取子订单数量时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Orders>> getShowOrderWithOrderProduct(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<Orders>> serviceResult = new ServiceResult<List<Orders>>();
        serviceResult.setPager(pager);
        try {
            queryMap.put("q_isShow", "1");
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(ordersModel.getOrdersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<Orders> returnList = ordersModel.getOrderWithOrderProduct(queryMap, start, size);
            serviceResult.setResult(returnList);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[OrderService][getOrderWithOrderProduct]根据用户ID获取用户订单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][getOrderWithOrderProduct]根据用户ID获取用户订单时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> cancelOrder(@RequestParam("ordersId") Integer ordersId,
                                              @RequestParam("optId") Integer optId,
                                              @RequestParam("optName") String optName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.cancelOrder(ordersId, optId, optName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][cancelOrder]根据ID取消用户订单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][cancalOrder]根据ID取消用户订单时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Orders> getOrderWithOPById(@RequestParam("orderId") Integer orderId) {
        ServiceResult<Orders> serviceResult = new ServiceResult<Orders>();
        try {
            serviceResult.setResult(ordersModel.getOrderWithOPById(orderId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[OrderService][getOrderWithOPById]根据订单id取订单、网单及产品图片信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][getOrderWithOPById]根据订单id 取订单、网单及产品图片信息时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<OrderSuccessVO> orderCommit(@RequestBody OrderCommitVO orderCommitVO) {
        ServiceResult<OrderSuccessVO> serviceResult = new ServiceResult<OrderSuccessVO>();
        try {
            serviceResult.setResult(ordersModel.orderCommit(orderCommitVO));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][orderCommit]会员下单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][orderCommit]会员下单时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 确认收货
     * @param ordersId
     * @param
     * @return
     */
    @Override
    public ServiceResult<Boolean> goodsReceipt(@RequestBody Member member,
                                               @RequestParam("ordersId") Integer ordersId) {

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.goodsReceipt(member, ordersId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][goodsReceipt]订单确认收货时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][goodsReceipt]订单确认收货时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<OrderDayDto>> getOrderDayDto(@RequestBody Map<String, String> queryMap) {
        ServiceResult<List<OrderDayDto>> serviceResult = new ServiceResult<List<OrderDayDto>>();
        try {
            serviceResult.setResult(ordersModel.getOrderDayDto(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][getOrderDayDto]统计每天订单量时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][getOrderDayDto]统计每天订单量时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> jobSystemFinishOrder() {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.jobSystemFinishOrder());
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][jobSystemFinishOrder]系统完成订单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][jobSystemFinishOrder]系统完成订单时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> jobSystemCancelOrder() {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.jobSystemCancelOrder());
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][jobSystemCancelOrder]系统取消订单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][jobSystemCancelOrder]系统取消订单时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 根据支付订单号查询订单信息，计算支付金额等
     * @param
     * @param
     * @return
     */
    @Override
    public ServiceResult<List<Orders>> getOrdersByOrderPsn(@RequestParam("orderPsn") String orderPsn) {
        ServiceResult<List<Orders>> serviceResult = new ServiceResult<List<Orders>>();
        try {
            serviceResult.setResult(ordersModel.getOrdersByOrderPsn(orderPsn));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][getOrdersByOrderPsn]查询订单时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 支付之前的调用，获取订单列表，以及用余额支付等逻辑<br/>
     * 假如余额够支付，那么直接更改订单的状态，返回支付成功页面
     * @param orderSn 订单号
     * @param isBalancePay 是否用余额支付
     * @param balancePassword 余额密码，未加密
     * @param member
     * @return
     */
    @Override
    public ServiceResult<OrderSuccessVO> orderPayBefore(@RequestParam("orderSn") String orderSn,
                                                        @RequestParam("isBalancePay") boolean isBalancePay,
                                                        @RequestParam("balancePassword") String balancePassword,
                                                        @RequestBody Member member) {
        ServiceResult<OrderSuccessVO> serviceResult = new ServiceResult<OrderSuccessVO>();
        try {
            serviceResult.setResult(
                ordersModel.orderPayBefore(orderSn, isBalancePay, balancePassword, member));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[OrderService][orderPayBefore]支付之前的调用，获取订单列表，以及用余额支付等逻辑发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][orderPayBefore]支付之前的调用，获取订单列表，以及用余额支付等逻辑发生异常:", e);
            e.printStackTrace();
        }
        return serviceResult;
    }

    /**
     * 支付成功之后更改订单的状态
     * @param trade_no 订单
     * @param total_fee 金额
     * @param paycode 支付方式
     * @param payname 支付方式
     * @param tradeSn 交易流水号
     * @param tradeContent 交易返回信息
     * @return
     */
    @Override
    public ServiceResult<Boolean> orderPayAfter(@RequestParam("trade_no") String trade_no,
                                                @RequestParam("total_fee") String total_fee,
                                                @RequestParam("paycode") String paycode,
                                                @RequestParam("payname") String payname,
                                                @RequestParam("tradeSn") String tradeSn,
                                                @RequestParam("tradeContent") String tradeContent) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(ordersModel.orderPayAfter(trade_no, total_fee, paycode, payname,
                tradeSn, tradeContent));
        } catch (BusinessException be) {
            be.printStackTrace();
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][orderPayAfter]支付成功之后更改订单的状态发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][orderPayAfter]支付成功之后更改订单的状态辑发生异常:", e);
        }
        return serviceResult;
    }


    @Override
    public ServiceResult<OrderSuccessVO> orderCommitForIntegral(@RequestBody OrderCommitVO orderCommitVO) {
        ServiceResult<OrderSuccessVO> serviceResult = new ServiceResult<OrderSuccessVO>();
        try {
            serviceResult.setResult(ordersModel.orderCommitForIntegral(orderCommitVO));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[OrderService][orderCommitForIntegral]会员提交积分换购订单时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[OrderService][orderCommitForIntegral]会员提交积分换购订单时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getReconfOrdersCount() {
        ServiceResult<Integer> serviceResult = new ServiceResult<>();
        try {
            Integer res = ordersModel.getReconfOrdersCount();
            serviceResult.setResult(res);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }
        return serviceResult;
    }


    @Override
    public ServiceResult<Integer> getOrderNumByMIdAndEvaluateState(@RequestParam("memberId") Integer memberId) {
        ServiceResult<Integer> serviceResult = new ServiceResult<>();
        try {
            Integer res = ordersModel.getOrderNumByMIdAndEvaluateState(memberId);
            serviceResult.setResult(res);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }
        return serviceResult;
    }


    /**
     * 根据会员ID 查找当下会员指定状态的订单列表
     * @param memberId
     * @param orderState
     * @return
     *
     * Author lihonglin
     */
    @Override
    public ServiceResult<List<Orders>> getOrdersByMemberIdAndState(Integer memberId, Integer orderState) {

        ServiceResult<List<Orders>> serviceResult = new ServiceResult<List<Orders>>();
        try {
            List<Orders> orders = ordersModel.getOrdersByMemberIdAndState(memberId, orderState);
            serviceResult.setResult(orders);
        }catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                    ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }

        return serviceResult;
    }
}