package com.yixiekeji.dao.shop.write.settlement;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.settlement.SettlementOp;

@Mapper
public interface SettlementOpWriteDao {

    SettlementOp get(java.lang.Integer id);

    Integer insert(SettlementOp settlementOp);

    Integer update(SettlementOp settlementOp);

    /**
     * 根据结算周期和结算商家ID删除结算网单表
     * @param settleCycle
     * @param sellerId
     * @return
     */
    Integer deleteByCysleAndSellerId(@Param("settleCycle") String settleCycle,
                                     @Param("sellerId") Integer sellerId);
}