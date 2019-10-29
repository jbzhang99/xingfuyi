package com.yixiekeji.dao.shop.read.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.OrderPayLog;

@Mapper
public interface OrderPayLogReadDao {

    OrderPayLog get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<OrderPayLog> queryList(Map<String, Object> map);

}
