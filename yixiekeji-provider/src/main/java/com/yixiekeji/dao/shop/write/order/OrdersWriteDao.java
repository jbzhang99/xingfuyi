package com.yixiekeji.dao.shop.write.order;

import com.yixiekeji.entity.order.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersWriteDao {

    Orders get(Integer id);

    Orders getByOrderSn(String orderSn);

    Integer insert(Orders orders);

    Integer update(Orders orders);

    Integer updateMoneyBack(@Param("id") Integer id, @Param("moneyBack") String moneyBack);

    /**
     * 关闭所有没有付款的定金订单 
     * @param id
     * @return
     */
    Integer updateCloseActBidding(@Param("actBiddingId") Integer actBiddingId);

    /**
     * 查询所有已经付款的定金订
     * @param id
     * @return
     */
    List<Orders> getActBiddingState5(@Param("actBiddingId") Integer actBiddingId);

    /**
     * 根据父订单号获取订单
     * @param orderPsn 父单号
     * @return
     */
    List<Orders> getByOrderPsn(@Param("orderPsn") String orderPsn);


    List<Orders> getOrdersByMemberIdAndState(@Param("memberId") Integer memberId, @Param("orderState") Integer orderState);


    /**
     * 修改订单评价状态
     * @param ordersId
     * @param evaluateState2
     * @return
     */
    Integer updateEvaluateStateById(@Param("id") Integer id,
                                    @Param("evaluateState") Integer evaluateState);

    /**
     * 获取已付款的定金订单的商品总数量
     * @param id
     * @return
     */
    Integer getPaidDepositNumber(@Param("actBiddingId") Integer actBiddingId);

    /**
     * 根据父订单号取消子订单
     * @param orderPsn
     * @return
     */
    Integer cancelByPsn(@Param("orderPsn") String orderPsn);
}
