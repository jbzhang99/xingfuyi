package com.yixiekeji.dao.shop.write.order;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.OrderPayLog;

@Mapper
public interface OrderPayLogWriteDao {

    //    OrderPayLog get(java.lang.Integer id);

    Integer insert(OrderPayLog orderPayLog);

    //    Integer update(OrderPayLog orderPayLog);

    //    Integer queryCount(Map<String, Object> map);

    //    List<OrderPayLog> queryList(Map<String, Object> map);

}
