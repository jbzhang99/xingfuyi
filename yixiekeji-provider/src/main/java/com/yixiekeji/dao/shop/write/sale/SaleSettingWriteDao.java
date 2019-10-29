package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleSetting;

@Mapper
public interface SaleSettingWriteDao {

    SaleSetting get(java.lang.Integer id);

    Integer insert(SaleSetting saleSetting);

    Integer insertSaleOrderTime(SaleSetting saleSetting);

    Integer update(SaleSetting saleSetting);

    /**
     * 查询最新的分销设置的一条记录
     * @return
     */
    SaleSetting getSaleSettingDesc();

}