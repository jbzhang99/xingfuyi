package com.yixiekeji.dao.shop.read.settlement;

import com.yixiekeji.entity.settlement.SettlementSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SettlementSettingReadDao {


    /**
     * 根据条件获取结算账单信息
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<SettlementSetting> getList(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);



    SettlementSetting getBeanByRuntime(@Param("runtime") Date runtime);
}