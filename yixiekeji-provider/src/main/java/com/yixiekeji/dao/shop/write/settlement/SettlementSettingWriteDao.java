package com.yixiekeji.dao.shop.write.settlement;

import com.yixiekeji.entity.settlement.Settlement;
import com.yixiekeji.entity.settlement.SettlementSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettlementSettingWriteDao {


    Integer insert(SettlementSetting settlementSetting);

    Integer update(SettlementSetting settlementSetting);

    Integer delete(SettlementSetting settlementSetting);

}