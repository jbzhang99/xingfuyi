package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleApplyMoney;

@Mapper
public interface SaleApplyMoneyWriteDao {

    SaleApplyMoney get(java.lang.Integer id);

    Integer insert(SaleApplyMoney saleApplyMoney);

    Integer update(SaleApplyMoney saleApplyMoney);

    int countSaleApplyMoneyByMemberIdAndState(@Param("memberId") Integer memberId,
                                              @Param("state") int state);
}