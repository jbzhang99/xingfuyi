package com.yixiekeji.dao.shop.read.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.dto.ProductDayDto;
import com.yixiekeji.entity.order.OrdersProduct;

/**
 * 网单管理
 *                       
 * @Filename: OrdersProductReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface OrdersProductReadDao {

    OrdersProduct get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<OrdersProduct> queryList(Map<String, Object> map);

    /**
     * 根据订单ID获取网单
     * @param ordersId
     * @return
     */
    List<OrdersProduct> getByOrderId(@Param("ordersId") Integer ordersId);

    /**
     * 统计商品每天的销量
     * @param queryMap
     * @return
     */
    List<ProductDayDto> getProductDayDto(@Param("queryMap") Map<String, String> queryMap);

    /**
     * 根据父订单号获取网单
     * @param ordersPsn
     * @return
     */
    List<OrdersProduct> getByOrdersPsn(@Param("ordersPsn") String ordersPsn);
}
