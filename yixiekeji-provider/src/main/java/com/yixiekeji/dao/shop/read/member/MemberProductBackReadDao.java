package com.yixiekeji.dao.shop.read.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductBack;

/**
 * 退货申请
 *                       
 * @Filename: MemberProductBackReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberProductBackReadDao {

    MemberProductBack get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<MemberProductBack> queryList(Map<String, Object> map);

    /**
     * 根据店铺ID获取在时间段内已退过款得退货申请（退款状态是2和3）
     * @param sellerId 商家ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<MemberProductBack> getSettleBacks(@Param("sellerId") Integer sellerId,
                                           @Param("startTime") String startTime,
                                           @Param("endTime") String endTime,
                                           @Param("start") Integer start,
                                           @Param("size") Integer size);

    Integer getSettleBacksCount(@Param("sellerId") Integer sellerId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime);

    Integer getCount(@Param("param1") Map<String, String> queryMap);

    List<MemberProductBack> page(@Param("param1") Map<String, String> queryMap,
                                 @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 获取在时间段内已退过款得退货申请（退款状态是2和3）,比saleOrderTime大的退款
     * @param saleOrderTime
     * @return
     */
    int countByBackMoneyTime(@Param("backMoneyTime") Date saleOrderTime);

    /**
     * 获取在时间段内已退过款得退货申请（退款状态是2和3）,比saleOrderTime大的退款
     * @param saleOrderTime
     * @param start
     * @param size
     * @return
     */
    List<MemberProductBack> getByBackMoneyTime(@Param("backMoneyTime") Date saleOrderTime,
                                               @Param("start") int start, @Param("size") int size);
}
