package com.yixiekeji.dao.shop.write.coupon;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.coupon.LogCoupon;

@Mapper
public interface LogCouponWriteDao {

    // LogCoupon get(java.lang.Integer id);

    Integer insert(LogCoupon logCoupon);

    // Integer update(LogCoupon logCoupon);
}