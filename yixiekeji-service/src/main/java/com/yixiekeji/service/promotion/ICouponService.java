package com.yixiekeji.service.promotion;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.seller.SellerUser;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "coupon")
@Service(value = "couponService")
public interface ICouponService {

    /**
     * 根据id取得优惠券
     * @param  couponId
     * @return
     */
    @RequestMapping(value = "getCouponById", method = RequestMethod.GET)
    ServiceResult<Coupon> getCouponById(@RequestParam("couponId") Integer couponId);

    /**
     * 保存优惠券
     * @param  coupon
     * @return
     */
    @RequestMapping(value = "saveCoupon", method = RequestMethod.POST)
    ServiceResult<Boolean> saveCoupon(Coupon coupon);

    /**
     * 更新优惠券
     * @param coupon
     * @return
     */
    @RequestMapping(value = "updateCoupon", method = RequestMethod.POST)
    ServiceResult<Boolean> updateCoupon(Coupon coupon);

    /**
     * 更新优惠券状态（只修改状态、审核意见、修改者信息）
     * @param coupon
     * @return
     */
    @RequestMapping(value = "updateCouponStatus", method = RequestMethod.POST)
    ServiceResult<Boolean> updateCouponStatus(Coupon coupon);

    /**
     * 删除优惠券
     * @param couponId
     * @param userId 删除人ID
     * @param userName 删除人名称
     * @return
     */
    @RequestMapping(value = "deleteCoupon", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteCoupon(@RequestParam("couponId") Integer couponId,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("userName") String userName);

    /**
     * 根据条件取得优惠券
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCoupons", method = RequestMethod.POST)
    ServiceResult<List<Coupon>> getCoupons(FeignUtil feignUtil);

    /**
     * 根据商家ID、渠道取得优惠券（当前时间在发放时间内、上架、未超发放限额、在线领取类型的优惠券）
     * 
     * @param sellerId
     * @param channel 渠道
     * @return
     */
    @RequestMapping(value = "getEffectiveCoupon", method = RequestMethod.GET)
    ServiceResult<List<Coupon>> getEffectiveCoupon(@RequestParam("sellerId") Integer sellerId,
                                                   @RequestParam("channel") Integer channel);

    /**
     * 根据优惠券ID导出exportNum数量的优惠券信息，用于线下发放
     * 
     * @param couponId 优惠券ID
     * @param exportNum 导出数量
     * @param sellerId 商家ID
     * @param sellerUser 店铺管理员
     * @return
     */
    @RequestMapping(value = "exportCoupon", method = RequestMethod.POST)
    ServiceResult<List<CouponUser>> exportCoupon(@RequestParam("couponId") Integer couponId,
                                                 @RequestParam("exportNum") Integer exportNum,
                                                 @RequestParam("sellerId") Integer sellerId,
                                                 @RequestBody SellerUser sellerUser);

    /**
     * 根据条件取得优惠券用户表
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCouponUsers", method = RequestMethod.POST)
    ServiceResult<List<CouponUser>> getCouponUsers(FeignUtil feignUtil);

    /**
     * 根据用户ID取得优惠券用户表
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCouponUserByMemberId", method = RequestMethod.POST)
    ServiceResult<List<CouponUser>> getCouponUserByMemberId(@RequestParam("memberId") Integer memberId,
                                                            @RequestBody PagerInfo pager);

    /**
     * 根据用户ID和是否使用取得优惠券用户表
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getCouponUserByMemberIdAndUse", method = RequestMethod.POST)
    ServiceResult<List<CouponUser>> getCouponUserByMemberIdAndUse(@RequestParam("memberId") Integer memberId,
                                                                  @RequestBody PagerInfo pager,
                                                                  @RequestParam("canUse") Integer canUse);

    /**
     * 用户领取优惠券
     * @param couponId 优惠券ID
     * @param memberId 用户ID
     * @return
     */
    @RequestMapping(value = "receiveCoupon", method = RequestMethod.GET)
    ServiceResult<Boolean> receiveCoupon(@RequestParam("couponId") Integer couponId,
                                         @RequestParam("memberId") Integer memberId);

    /**
     * 根据id取得优惠券用户表
     * @param  couponUserId
     * @return
     */
    @RequestMapping(value = "getCouponUserById", method = RequestMethod.GET)
    ServiceResult<CouponUser> getCouponUserById(@RequestParam("couponUserId") Integer couponUserId);

    /**
     * 根据用户ID、商家ID获取当前时间有效可用的优惠券
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getEffectiveByMemberIdAndSellerId", method = RequestMethod.GET)
    ServiceResult<List<CouponUser>> getEffectiveByMemberIdAndSellerId(@RequestParam("memberId") Integer memberId,
                                                                      @RequestParam("sellerId") Integer sellerId);

    /**
     * 根据优惠码序列号取得优惠券用户表
     * @param couponSn
     * @return
     */
    @RequestMapping(value = "getCouponUserOnlyByCouponSn", method = RequestMethod.GET)
    ServiceResult<CouponUser> getCouponUserOnlyByCouponSn(@RequestParam("couponSn") String couponSn);

    /**
     * 优惠券领取列表获取优惠券（当前时间在发放时间内、上架、在线领取类型的优惠券）
     * @param memberId 会员ID（未登录传0）
     * @param sort 排序：0、默认（按创建时间倒排） 1、即将过期 2、面值最大
     * @param channel 渠道
     * @param pager 分页
     * @return
     */
    @RequestMapping(value = "getCouponsForList", method = RequestMethod.POST)
    ServiceResult<List<Coupon>> getCouponsForList(@RequestParam("memberId") Integer memberId,
                                                  @RequestParam("sort") Integer sort,
                                                  @RequestParam("channel") Integer channel,
                                                  @RequestBody PagerInfo pager);

    /**
     * 根据用户ID统计优惠劵的数量
     * @param memberId
     * @return
     */
    @RequestMapping(value = "countCouponUserByMemberId", method = RequestMethod.GET)
    ServiceResult<Integer> countCouponUserByMemberId(@RequestParam("memberId") Integer memberId);
}