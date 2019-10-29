package com.yixiekeji.service.order;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.dto.OrderDayDto;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.order.OrderCommitVO;
import com.yixiekeji.vo.order.OrderSuccessVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单服务
 *                       
 * @Filename: IOrdersService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "orders")
@Service(value = "ordersService")
public interface IOrdersService {

    /**
     * 根据id取得订单
     * @param  ordersId
     * @return
     */
    @RequestMapping(value = "getOrdersById", method = RequestMethod.GET)
    ServiceResult<Orders> getOrdersById(@RequestParam("ordersId") Integer ordersId);

    /**
     * 根据orderSn取得订单
     * @param  orderSn
     * @return
     */
    @RequestMapping(value = "getOrdersBySn", method = RequestMethod.GET)
    ServiceResult<Orders> getOrdersBySn(@RequestParam("orderSn") String orderSn);

    /**
     * 根据订单ID获取订单号
     * @param id 订单ID
     * @return
     */
    @RequestMapping(value = "getOrderSnById", method = RequestMethod.GET)
    ServiceResult<String> getOrderSnById(@RequestParam("ordersId") Integer ordersId);

    /**
     * 根据条件查询子订单，PagerInfo传null返回所有符合条件的数据
     * @param queryMap key以q_开头的属性名称，比如q_orderSn
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSonOrders", method = RequestMethod.POST)
    ServiceResult<List<Orders>> getSonOrders(FeignUtil feignUtil);

    /**
     * 更新订单
     * @param orders
     * @param type
     * @param seller
     * @return
     */
    @RequestMapping(value = "updateOrdersBySeller", method = RequestMethod.POST)
    ServiceResult<Integer> updateOrdersBySeller(@RequestBody FeignObjectUtil feignObjectUtil,
                                                @RequestParam("type") int type);

    /**
     * 商家确认订单
     * @param orderId
     * @param seller
     * @return
     */
    @RequestMapping(value = "confirmOrdersBySeller", method = RequestMethod.POST)
    ServiceResult<Boolean> confirmOrdersBySeller(@RequestParam("orderId") Integer orderId,
                                                 @RequestBody SellerUser sellerUser);

    /**
     * 更新订单
     * @param orders
     * @param type
     * @param systemAdmin
     * @return
     */
    @RequestMapping(value = "updateOrdersByAdmin", method = RequestMethod.POST)
    ServiceResult<Integer> updateOrdersByAdmin(@RequestBody FeignObjectUtil feignObjectUtil,
                                               @RequestParam("type") int type);

    /**
     * 根据会员ID，订单状态获取 子订单 数量
     * @param memberId
     * @param orderState
     * @return
     */
    @RequestMapping(value = "getOrderNumByMIdAndState", method = RequestMethod.GET)
    ServiceResult<Integer> getOrderNumByMIdAndState(@RequestParam("memberId") Integer memberId,
                                                    @RequestParam("orderState") Integer orderState);

    /**
     * 根据用户ID等条件获取用户订单，Order对象封装了该订单下的网单
     * <br>用户中心订单列表页用
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getShowOrderWithOrderProduct", method = RequestMethod.POST)
    ServiceResult<List<Orders>> getShowOrderWithOrderProduct(FeignUtil feignUtil);

    /**
     * 取消订单 目前只有订单状态为 1、2、3的可以取消（其中3的只能用户自己取消，不能由商家取消）
     * @param ordersId 订单ID
     * @param optId 操作人ID
     * @param optName 操作人名称
     */
    @RequestMapping(value = "cancelOrder", method = RequestMethod.GET)
    public ServiceResult<Boolean> cancelOrder(@RequestParam("ordersId") Integer ordersId,
                                              @RequestParam("optId") Integer optId,
                                              @RequestParam("optName") String optName);

    /**
     * 根据订单id 取订单、网单及产品图片信息
     * @param orderId
     * @return
     */
    @RequestMapping(value = "getOrderWithOPById", method = RequestMethod.GET)
    ServiceResult<Orders> getOrderWithOPById(@RequestParam("orderId") Integer orderId);

    /**
     * 用户提交订单<br>
     * 1、判断是否使用余额、判断支付密码<br>
     * 2、按商家拆分订单<br>
     * 3、保存网单<br>
     * 4、清除购物车<br>
     * 5、如果使用余额，并且余额足够支付所有订单，修改支付状态、修改库存<br>
     * @param orderCommitVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderCommit", method = RequestMethod.POST)
    ServiceResult<OrderSuccessVO> orderCommit(OrderCommitVO orderCommitVO);

    /**
     * 确认收货
     * @param member
     * @param orderId
     * @return
     */
    @RequestMapping(value = "goodsReceipt", method = RequestMethod.POST)
    ServiceResult<Boolean> goodsReceipt(@RequestBody Member member,
                                        @RequestParam("ordersId") Integer ordersId);

    /**
     * 统计每天订单量
     * @param queryMap
     * @return
     */
    @RequestMapping(value = "getOrderDayDto", method = RequestMethod.POST)
    ServiceResult<List<OrderDayDto>> getOrderDayDto(Map<String, String> queryMap);

    /**
     * 系统自动完成订单<br>
     * <li>对已发货状态的订单发货时间超过15个自然日的订单进行自动完成处理
     * @return
     */
    @RequestMapping(value = "jobSystemFinishOrder", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSystemFinishOrder();

    /**
     * 系统自动取消24小时没有付款订单<br>
     * @return
     */
    @RequestMapping(value = "jobSystemCancelOrder", method = RequestMethod.GET)
    ServiceResult<Boolean> jobSystemCancelOrder();

    /**
     * 根据父订单号查询订单
     * @param orderPsn
     * @return
     */
    @RequestMapping(value = "getOrdersByOrderPsn", method = RequestMethod.GET)
    ServiceResult<List<Orders>> getOrdersByOrderPsn(@RequestParam("orderPsn") String orderPsn);

    /**
     * 支付之前的调用，获取订单列表，以及用余额支付等逻辑<br/>
     * 假如余额够支付，那么直接更改订单的状态，返回支付成功页面
     * @param orderSn 订单号
     * @param isBalancePay 是否用余额支付
     * @param balancePassword 余额密码，未加密
     * @param member
     * @return
     */
    @RequestMapping(value = "orderPayBefore", method = RequestMethod.POST)
    ServiceResult<OrderSuccessVO> orderPayBefore(@RequestParam("orderSn") String orderSn,
                                                 @RequestParam("isBalancePay") boolean isBalancePay,
                                                 @RequestParam("balancePassword") String balancePassword,
                                                 @RequestBody Member member);

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
    @RequestMapping(value = "orderPayAfter", method = RequestMethod.GET)
    ServiceResult<Boolean> orderPayAfter(@RequestParam("trade_no") String trade_no,
                                         @RequestParam("total_fee") String total_fee,
                                         @RequestParam("paycode") String paycode,
                                         @RequestParam("payname") String payname,
                                         @RequestParam("tradeSn") String tradeSn,
                                         @RequestParam("tradeContent") String tradeContent);

   

    /**
     * 用户提交积分换购订单<br>
     * @param orderCommitVO
     * @return
     */
    @RequestMapping(value = "orderCommitForIntegral", method = RequestMethod.POST)
    ServiceResult<OrderSuccessVO> orderCommitForIntegral(OrderCommitVO orderCommitVO);

    /**
     * 当天待确认订单数
     * @return
     */
    @RequestMapping(value = "getReconfOrdersCount", method = RequestMethod.GET)
    ServiceResult<Integer> getReconfOrdersCount();

    /**
     * 获取待评价订单数
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getOrderNumByMIdAndEvaluateState", method = RequestMethod.GET)
    ServiceResult<Integer> getOrderNumByMIdAndEvaluateState(@RequestParam("memberId") Integer memberId);



    /**
     * 根据会员ID 查找当下会员指定状态的订单列表
     * @param memberId
     * @param orderState
     * @return
     *
     * Author lihonglin
     */
    @GetMapping("getOrdersByMemberIdAndState")
    ServiceResult<List<Orders>> getOrdersByMemberIdAndState(
            @RequestParam("memberId") Integer memberId,
            @RequestParam("orderState") Integer orderState);


}