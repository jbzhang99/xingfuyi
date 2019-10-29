package com.yixiekeji.dao.shop.read.sale;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleOrder;

@Mapper
public interface SaleOrderReadDao {

    SaleOrder get(java.lang.Integer id);

    int getSaleOrdersCount(@Param("queryMap") Map<String, String> queryMap);

    List<SaleOrder> getSaleOrders(@Param("queryMap") Map<String, String> queryMap,
                                  @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据用户ID和状态统计数量
     * @param saleState
     * @param memberId
     * @return
     */
    Integer countSaleOrderBySaleStateAndMemberId(@Param("saleState") int saleState,
                                                 @Param("memberId") Integer memberId);

    /**
     * 根据用户ID和状态统计金额
     * @param saleState
     * @param memberId
     * @return
     */
    BigDecimal sumSaleOrderBySaleStateAndMemberId(@Param("saleState") int saleState,
                                                  @Param("memberId") Integer memberId);

    /**
     * 根据商家ID和开始结束时间统计佣金
     * @param sellerId
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal sumSaleOrderBySellerId(@Param("sellerId") Integer sellerId,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime);

    /**
     * 根据网单ID统计佣金
     * @param ordersProductId
     * @return
     */
    BigDecimal sumOrdersProductId(@Param("ordersProductId") Integer ordersProductId);

}