package com.yixiekeji.dao.shop.write.sale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleOrder;

@Mapper
public interface SaleOrderWriteDao {

    SaleOrder get(java.lang.Integer id);

    Integer insert(SaleOrder saleOrder);

    Integer update(SaleOrder saleOrder);

    /**
     * 根据网单ID统计
     * @return
     */
    int countByOrdersProductId(Integer ordersProductId);

    /**
     * 根据网单ID查询
     * @return
     */
    List<SaleOrder> getByOrdersProductId(Integer ordersProductId);

    /**
     * 把比date时间大的并且sale_state状态为1的修改为2
     * 预计收益修改为可以体现
     * @param date
     * @return
     */
    int updateBySaleStateAndCreateTime(Date date);

    /**
     * 根据用户ID和状态统计金额
     * @param saleState
     * @param memberId
     * @return
     */
    BigDecimal sumSaleOrderBySaleStateAndMemberId(@Param("saleState") int saleState,
                                                  @Param("memberId") Integer memberId);

    /**
     * 把可以体现修改为提现中，并添加提款ID
     * @param id
     * @return
     */
    int updateBySaleStateAndApplyMoney(@Param("saleApplyMoneyId") Integer saleApplyMoneyId,
                                       @Param("memberId") Integer memberId);

    /**
     * 把saleState1修改为saleState2
     * @param saleState1
     * @param saleState2
     * @param memberId
     * @return
     */
    int updateBySaleState(@Param("saleState1") int saleState1, @Param("saleState2") int saleState2,
                          @Param("memberId") Integer memberId);

}