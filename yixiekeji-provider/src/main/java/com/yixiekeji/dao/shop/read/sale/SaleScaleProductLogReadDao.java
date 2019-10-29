package com.yixiekeji.dao.shop.read.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScaleProductLog;

@Mapper
public interface SaleScaleProductLogReadDao {

    SaleScaleProductLog get(java.lang.Integer id);

}