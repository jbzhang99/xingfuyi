package com.yixiekeji.dao.shop.write.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.coupon.CouponOptLog;

@Mapper
public interface CouponOptLogWriteDao {

    CouponOptLog get(java.lang.Integer id);

    Integer insert(CouponOptLog couponOptLog);

    Integer update(CouponOptLog couponOptLog);

    /**
     * 批量新增优惠券用户操作日志
     * @param couponOptLogList
     * @return
     */
    Integer batchInsertCouponOptLog(List<CouponOptLog> couponOptLogList);
}