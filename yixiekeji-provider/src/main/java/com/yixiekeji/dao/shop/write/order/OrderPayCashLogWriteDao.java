package com.yixiekeji.dao.shop.write.order;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.OrderPayCashLog;

@Mapper
public interface OrderPayCashLogWriteDao {

    //	OrderPayCashLog get(java.lang.Integer id);

    Integer insert(OrderPayCashLog orderPayCashLog);

    //	Integer update(OrderPayCashLog orderPayCashLog);

    //	Integer delete(java.lang.Integer id);

}