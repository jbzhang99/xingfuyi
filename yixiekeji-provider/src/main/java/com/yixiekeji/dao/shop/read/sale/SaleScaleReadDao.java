package com.yixiekeji.dao.shop.read.sale;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleScale;

@Mapper
public interface SaleScaleReadDao {

    SaleScale get(java.lang.Integer id);

    SaleScale getSaleScaleBySellerId(@Param("sellerId") Integer sellerId);

    /**
     * 查询正在上架的商家的分佣
     * @return
     */
    List<SaleScale> getSaleScalesByState();

}