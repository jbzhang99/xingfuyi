package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScale;

@Mapper
public interface SaleScaleWriteDao {

    SaleScale get(java.lang.Integer id);

    Integer insert(SaleScale saleScale);

    Integer update(SaleScale saleScale);
}