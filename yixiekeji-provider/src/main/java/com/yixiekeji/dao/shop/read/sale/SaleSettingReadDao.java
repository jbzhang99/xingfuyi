package com.yixiekeji.dao.shop.read.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleSetting;

@Mapper
public interface SaleSettingReadDao {

    SaleSetting get(java.lang.Integer id);

    /**
     * 查询最新的分销设置的一条记录
     * @return
     */
    SaleSetting getSaleSettingDesc();
}