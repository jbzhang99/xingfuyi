package com.yixiekeji.dao.shop.read.settlement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.settlement.Settlement;

@Mapper
public interface SettlementReadDao {

    Settlement get(java.lang.Integer id);

    /**
     * 根据条件获取结算账单信息
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<Settlement> getSettlements(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据条件获取结算账单数量
     * @param queryMap
     * @return
     */
    Integer getSettlementsCount(@Param("queryMap") Map<String, String> queryMap);
}