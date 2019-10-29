package com.yixiekeji.dao.shop.read.settlement;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.settlement.SettlementOp;

@Mapper
public interface SettlementOpReadDao {

    SettlementOp get(java.lang.Integer id);

    /**
     * 根据订单号获取结算网单
     * @param orderId
     * @return
     */
    List<SettlementOp> getByOrderId(Integer orderId);
}