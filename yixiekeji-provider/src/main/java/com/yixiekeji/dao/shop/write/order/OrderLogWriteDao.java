package com.yixiekeji.dao.shop.write.order;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.OrderLog;

@Mapper
public interface OrderLogWriteDao {

    OrderLog get(Integer id);

    Integer save(OrderLog orderLog);

    //    Integer update(OrderLog orderLog);

    //    Integer del(Integer id);

}
