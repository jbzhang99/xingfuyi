package com.yixiekeji.dao.shop.read.coupon;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.coupon.CouponUser;

@Mapper
public interface CouponUserReadDao {

    CouponUser get(java.lang.Integer id);

    Integer getCouponUsersCount(@Param("queryMap") Map<String, String> queryMap);

    List<CouponUser> getCouponUsers(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据用户ID获取用户的优惠券数量
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    Integer getCouponUserByMemberIdCount(@Param("memberId") Integer memberId);

    /**
     * 根据用户ID获取用户的优惠券
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    List<CouponUser> getCouponUserByMemberId(@Param("memberId") Integer memberId,
                                             @Param("start") Integer start,
                                             @Param("size") Integer size);

    /**
     * 根据优惠券序列号获取优惠券
     * @param couponSn 优惠券序列号
     * @param sellerId 商家ID
     * @return
     */
    List<CouponUser> getCouponUserByCouponSn(@Param("couponSn") String couponSn,
                                             @Param("sellerId") Integer sellerId);

    /**
     * 根据用户ID、商家ID获取当前时间有效可用的优惠券
     * @param memberId
     * @param sellerId
     * @return
     */
    List<CouponUser> getEffectiveByMemberIdAndSellerId(@Param("memberId") Integer memberId,
                                                       @Param("sellerId") Integer sellerId);

    int getCouponUserByMemberIdAndUseCount(@Param("memberId") Integer memberId,
                                           @Param("canUse") Integer canUse);

    List<CouponUser> getCouponUserByMemberIdAndUse(@Param("memberId") Integer memberId,
                                                   @Param("canUse") Integer canUse,
                                                   @Param("start") Integer start,
                                                   @Param("size") Integer size);

    /**
     * 根据优惠码序列号取得优惠券用户表
     * @param couponSn
     * @return
     */
    CouponUser getCouponUserOnlyByCouponSn(@Param("couponSn") String couponSn);

    /**
     * 根据会员ID和优惠券ID获取该会员已领取了该优惠券的数量
     * @param memberId
     * @param couponId
     * @return
     */
    Integer getCountByMemberIdAndCouponId(@Param("memberId") Integer memberId,
                                          @Param("couponId") Integer couponId);
}