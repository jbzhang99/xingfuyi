package com.yixiekeji.dao.shop.write.integral;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegral;

@Mapper
public interface ActIntegralWriteDao {

    ActIntegral get(java.lang.Integer id);

    Integer insert(ActIntegral actIntegral);

    Integer update(ActIntegral actIntegral);

    int countByType(Integer id);

    /**
     * 积分商城分类停用或者启用的时候把积分商城分类下面的积分商城商品设置为停用或者启用
     * @param type 积分商城分类ID
     * @param typeState 积分商城分类的状态
     * @return
     */
    int updateByTypeState(@Param("type") Integer type, @Param("typeState") int typeState);

    /**
     * 更改积分商城的审核状态
     * @param id
     * @param state
     * @return
     */
    int updateState(@Param("id") Integer id, @Param("state") int state);

    /**
     * 更改积分商城的活动状态
     * @param id
     * @param activityState
     * @return
     */
    int updateActivityState(@Param("id") Integer id, @Param("activityState") int activityState);

    /**
     * 平台管理员审核，审核通过之后更改状态，并填写审核意见
     * @param id
     * @param state
     * @param auditOpinion
     * @return
     */
    int updateStateAndAuditOpinion(@Param("id") Integer id, @Param("state") int state,
                                   @Param("auditOpinion") String auditOpinion);

    /**
     * 修改活动商品的库存和销量，库存减saleNum，销量加saleNum<br>
     * `stock`= `stock` - saleNum<br>
     * `sale_num` = `sale_num` + saleNum<br>
     * @param id
     * @param saleNum
     * @return
     */
    Integer updateStockAndActualSales(@Param("id") Integer id, @Param("saleNum") Integer saleNum);
}