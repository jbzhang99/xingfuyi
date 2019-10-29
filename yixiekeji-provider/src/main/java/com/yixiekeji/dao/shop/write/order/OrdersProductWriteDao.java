package com.yixiekeji.dao.shop.write.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.OrdersProduct;

@Mapper
public interface OrdersProductWriteDao {

    OrdersProduct get(Integer id);

    /**
     * 根据订单ID和商家ID查询网单
     * @param ordersId 订单ID
     * @param sellerId 商家ID
     * @return
     */
    List<OrdersProduct> getByOrderIdAndSellerId(@Param("ordersId") Integer ordersId,
                                                @Param("sellerId") Integer sellerId);

    /**
     * 根据订单ID获取网单
     * @param ordersId
     * @return
     */
    List<OrdersProduct> getByOrderId(@Param("ordersId") Integer ordersId);

    Integer insert(OrdersProduct ordersProduct);

    Integer update(OrdersProduct ordersProduct);

    /**
     * 修改网单评论状态
     * @param ordersProductId
     * @param isEvaluate1
     * @return
     */
    Integer updateIsEvaluate(@Param("id") Integer id, @Param("isEvaluate") Integer isEvaluate);

    /**
     * 修改网单的退货数量<br>
     * set `back_number`= `back_number` + #{backNumber}
     * @param id
     * @param backNumber
     * @return
     */
    Integer updateBackNumber(@Param("id") Integer id, @Param("backNumber") Integer backNumber);

    /**
     * 修改网单的换货数量<br>
     * set `exchange_number`= `exchange_number` + #{exchangeNumber}
     * @param id
     * @param exchangeNumber
     * @return
     */
    Integer updateExchangeNumber(@Param("id") Integer id,
                                 @Param("exchangeNumber") Integer exchangeNumber);

    /**
     * 根据父订单号获取网单
     * @param ordersPsn
     * @return
     */
    List<OrdersProduct> getByOrdersPsn(@Param("ordersPsn") String ordersPsn);
}
