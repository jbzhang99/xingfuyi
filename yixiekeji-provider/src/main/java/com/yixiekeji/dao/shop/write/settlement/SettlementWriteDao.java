package com.yixiekeji.dao.shop.write.settlement;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.settlement.Settlement;

@Mapper
public interface SettlementWriteDao {

    Settlement get(java.lang.Integer id);

    Integer insert(Settlement settlement);

    Integer update(Settlement settlement);

    Integer delete(Integer id);

    /**
     * 根据结算周期和结算商家ID删除结算数据
     * @param settleCycle
     * @param sellerId
     * @return
     */
    Integer deleteByCysleAndSellerId(@Param("settleCycle") String settleCycle,
                                     @Param("sellerId") Integer sellerId);
}