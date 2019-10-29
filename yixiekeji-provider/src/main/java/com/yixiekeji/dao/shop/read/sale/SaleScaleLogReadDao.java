package com.yixiekeji.dao.shop.read.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScaleLog;

@Mapper
public interface SaleScaleLogReadDao {

    SaleScaleLog get(java.lang.Integer id);

}