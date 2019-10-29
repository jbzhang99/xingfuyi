package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScaleProductLog;

@Mapper
public interface SaleScaleProductLogWriteDao {

    SaleScaleProductLog get(java.lang.Integer id);

    Integer insert(SaleScaleProductLog saleScaleProductLog);

    Integer update(SaleScaleProductLog saleScaleProductLog);
}