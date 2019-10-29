package com.yixiekeji.dao.shop.read.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.dto.OrderDayDto;
import com.yixiekeji.dto.OrdersReturnDto;
import com.yixiekeji.dto.ProductSaleDto;
import com.yixiekeji.entity.order.Orders;

/**
 * 订单表
 * 
 * @Filename: OrdersReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@yixiekeji.com
 *
 */
@Mapper
public interface OrdersReadDao {

    /**
     * 根据会员ID，订单状态获取 子订单 数量
     * @param memberId
     * @param orderState
     * @return
     */
    Integer getOrderNumByMIdAndState(@Param("memberId") Integer memberId,
                                     @Param("orderState") Integer orderState);

    Orders get(@Param("id") java.lang.Integer id);

    /**
     * 根据商家ID及时间获取已经完成的订单
     * @param sellerId 商家ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Orders> getSettleOrders(@Param("sellerId") Integer sellerId,
                                 @Param("startTime") String startTime,
                                 @Param("endTime") String endTime);

    /**
     * 统计每天订单量
     * @param queryMap
     * @return
     */
    List<OrderDayDto> getOrderDayDto(@Param("queryMap") Map<String, String> queryMap);

    /**
     * 获取deliverTime前发货的订单
     * 
     * @param deliverTime
     * @return
     */
    List<Orders> getUnfinishedOrders(@Param("deliverTime") String deliverTime);

    /**
     * 获取deliverTime前发货的订单
     * 
     * @param cancelTime
     * @return
     */
    List<Orders> getUnPaiedOrders(@Param("cancelTime") String cancelTime);

    /**
     * 根据商家ID获取商家的销售总金额
     * @param sellerId
     * @return
     */
    OrderDayDto getSumMoneyOrderBySellerId(Integer sellerId);

    /**
     * 根据商家ID查询订单量
     * @param sellerId
     * @return
     */
    Integer getCountBySellerId(Integer sellerId);

    List<OrdersReturnDto> goodsReturnRate(Map<String, String> queryMap);

    List<ProductSaleDto> getProductSale(Map<String, Object> queryMap);

    Integer getOrdersCount(@Param("queryMap") Map<String, String> queryMap);

    List<Orders> getOrders(@Param("queryMap") Map<String, Object> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 查询用户待评价订单数量
     * @param memberId
     * @return
     */
    Integer getNumByMIdAndEvaluateState(@Param("memberId") Integer memberId);

    /**
     * 根据父订单号获取订单
     * @param orderPsn 父单号
     * @return
     */
    List<Orders> getByOrderPsn(@Param("orderPsn") String orderPsn);

    /**
     * 查询比finishTime大的时间，并且状态是orderState的订单
     * @param finishTime
     * @param orderState
     * @return
     */
    List<Orders> getByFinishTimeAndOrderState(@Param("finishTime") Date finishTime,
                                              @Param("orderState") int orderState,
                                              @Param("start") Integer start,
                                              @Param("size") Integer size);

    /**
     * 统计比finishTime大的时间，并且状态是orderState的订单
     * @param saleOrderTime
     * @param orderState5
     * @return
     */
    int countByFinishTimeAndOrderState(@Param("finishTime") Date finishTime,
                                       @Param("orderState") int orderState);
}
