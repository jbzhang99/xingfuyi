package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScaleLog;

@Mapper
public interface SaleScaleLogWriteDao {

    SaleScaleLog get(java.lang.Integer id);

    Integer insert(SaleScaleLog saleScaleLog);

    Integer update(SaleScaleLog saleScaleLog);
}