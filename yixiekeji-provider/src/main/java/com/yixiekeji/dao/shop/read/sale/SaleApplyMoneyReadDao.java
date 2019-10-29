package com.yixiekeji.dao.shop.read.sale;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleApplyMoney;

@Mapper
public interface SaleApplyMoneyReadDao {

    SaleApplyMoney get(java.lang.Integer id);

    int getSaleApplyMoneysCount(@Param("queryMap") Map<String, String> queryMap);

    List<SaleApplyMoney> getSaleApplyMoneys(@Param("queryMap") Map<String, String> queryMap,
                                            @Param("start") Integer start,
                                            @Param("size") Integer size);

}