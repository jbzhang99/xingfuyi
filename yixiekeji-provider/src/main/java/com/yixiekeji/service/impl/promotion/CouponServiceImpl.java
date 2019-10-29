package com.yixiekeji.service.impl.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.model.promotion.CouponModel;
import com.yixiekeji.model.promotion.CouponUserModel;
import com.yixiekeji.service.promotion.ICouponService;

@RestController
public class CouponServiceImpl implements ICouponService {
    private static Logger   log = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Resource
    private CouponModel     couponModel;
    @Resource
    private CouponUserModel couponUserModel;

    @Override
    public ServiceResult<Coupon> getCouponById(@RequestParam("couponId") Integer couponId) {
        ServiceResult<Coupon> result = new ServiceResult<Coupon>();
        try {
            result.setResult(couponModel.getCouponById(couponId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][getCouponById]根据id[" + couponId + "]取得优惠券时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponById]根据id[" + couponId + "]取得优惠券时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveCoupon(@RequestBody Coupon coupon) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(couponModel.saveCoupon(coupon));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][saveCoupon]保存优惠券时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][saveCoupon]保存优惠券时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateCoupon(@RequestBody Coupon coupon) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(couponModel.updateCoupon(coupon));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][updateCoupon]更新优惠券时发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][updateCoupon]更新优惠券时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateCouponStatus(@RequestBody Coupon coupon) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(couponModel.updateCouponStatus(coupon));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][updateCouponStatus]更新优惠券状态时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][updateCouponStatus]更新优惠券状态时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteCoupon(@RequestParam("couponId") Integer couponId,
                                               @RequestParam("userId") Integer userId,
                                               @RequestParam("userName") String userName) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(couponModel.deleteCoupon(couponId, userId, userName));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][deleteCoupon]删除优惠券时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][deleteCoupon]删除优惠券时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<Coupon>> getCoupons(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<Coupon>> serviceResult = new ServiceResult<List<Coupon>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(couponModel.getCouponsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(couponModel.getCoupons(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][getCoupons]根据条件取得优惠券时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCoupons]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[ICouponService][getCoupons]根据条件取得优惠券时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Coupon>> getEffectiveCoupon(@RequestParam("sellerId") Integer sellerId,
                                                          @RequestParam("channel") Integer channel) {
        ServiceResult<List<Coupon>> result = new ServiceResult<List<Coupon>>();
        try {
            result.setResult(couponModel.getEffectiveCoupon(sellerId, channel));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][getEffectiveCoupon]根据商家ID和渠道取得优惠券时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getEffectiveCoupon]根据商家ID和渠道取得优惠券时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<CouponUser>> exportCoupon(@RequestParam("couponId") Integer couponId,
                                                        @RequestParam("exportNum") Integer exportNum,
                                                        @RequestParam("sellerId") Integer sellerId,
                                                        @RequestBody SellerUser sellerUser) {
        ServiceResult<List<CouponUser>> result = new ServiceResult<List<CouponUser>>();
        try {
            result
                .setResult(couponUserModel.exportCoupon(couponId, exportNum, sellerId, sellerUser));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[ICouponService][exportCoupon]根据优惠券ID导出exportNum数量的优惠券信息时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][exportCoupon]根据优惠券ID导出exportNum数量的优惠券信息时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<CouponUser>> getCouponUsers(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<CouponUser>> serviceResult = new ServiceResult<List<CouponUser>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(couponUserModel.getCouponUsersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(couponUserModel.getCouponUsers(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][getCouponUsers]根据条件取得优惠券用户时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponUsers]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[ICouponService][getCouponUsers]根据条件取得优惠券用户时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<CouponUser>> getCouponUserByMemberId(@RequestParam("memberId") Integer memberId,
                                                                   @RequestBody PagerInfo pager) {
        ServiceResult<List<CouponUser>> serviceResult = new ServiceResult<List<CouponUser>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(couponUserModel.getCouponUserByMemberIdCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(couponUserModel.getCouponUserByMemberId(memberId, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[ICouponService][getCouponUserByMemberId]根据用户ID取得优惠券用户时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponUserByMemberId]param1:" + memberId + " &param2:"
                      + JSON.toJSONString(pager));
            log.error("[ICouponService][getCouponUserByMemberId]根据用户ID取得优惠券用户时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> receiveCoupon(@RequestParam("couponId") Integer couponId,
                                                @RequestParam("memberId") Integer memberId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(couponUserModel.receiveCoupon(couponId, memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][receiveCoupon]用户领取优惠券时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[ICouponService][receiveCoupon]couponId:" + couponId + " &memberId:" + memberId);
            log.error("[ICouponService][receiveCoupon]用户领取优惠券时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<CouponUser> getCouponUserById(@RequestParam("couponUserId") Integer couponUserId) {
        ServiceResult<CouponUser> result = new ServiceResult<CouponUser>();
        try {
            result.setResult(couponUserModel.getCouponUserById(couponUserId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][getCouponUserById]根据id[" + couponUserId + "]取得优惠券用户表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponUserById]根据id[" + couponUserId + "]取得优惠券用户表时发生异常:",
                e);
        }
        return result;
    }

    /**
     * 根据用户ID和是否使用取得优惠券用户表
     * @param memberId
     * @param pager
     * @param canUse
     * @return
     * @see com.yixiekeji.service.promotion.ICouponService#getCouponUserByMemberIdAndUse(java.lang.Integer, com.yixiekeji.core.PagerInfo, java.lang.Integer)
     */
    @Override
    public ServiceResult<List<CouponUser>> getCouponUserByMemberIdAndUse(@RequestParam("memberId") Integer memberId,
                                                                         @RequestBody PagerInfo pager,
                                                                         @RequestParam("canUse") Integer canUse) {
        ServiceResult<List<CouponUser>> serviceResult = new ServiceResult<List<CouponUser>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(
                    couponUserModel.getCouponUserByMemberIdAndUseCount(memberId, canUse));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(
                couponUserModel.getCouponUserByMemberIdAndUse(memberId, canUse, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][getCouponUserByMemberIdAndUse]根据用户ID和是否使用取得优惠券用户时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponUserByMemberId]param1:" + memberId + " &param2:"
                      + JSON.toJSONString(pager));
            log.error("[ICouponService][getCouponUserByMemberIdAndUse]根据用户ID和是否使用取得优惠券用户时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<CouponUser>> getEffectiveByMemberIdAndSellerId(@RequestParam("memberId") Integer memberId,
                                                                             @RequestParam("sellerId") Integer sellerId) {
        ServiceResult<List<CouponUser>> serviceResult = new ServiceResult<List<CouponUser>>();
        try {
            serviceResult
                .setResult(couponUserModel.getEffectiveByMemberIdAndSellerId(memberId, sellerId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[ICouponService][getEffectiveByMemberIdAndSellerId]根据用户ID、商家ID获取当前时间有效可用的优惠券时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getEffectiveByMemberIdAndSellerId]memberId:" + memberId
                      + " &sellerId:" + sellerId);
            log.error(
                "[ICouponService][getEffectiveByMemberIdAndSellerId]根据用户ID、商家ID获取当前时间有效可用的优惠券时发生异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<CouponUser> getCouponUserOnlyByCouponSn(@RequestParam("couponSn") String couponSn) {
        ServiceResult<CouponUser> serviceResult = new ServiceResult<CouponUser>();
        try {
            serviceResult.setResult(couponUserModel.getCouponUserOnlyByCouponSn(couponSn));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][getCouponUserOnlyByCouponSn]根据优惠码序列号取得优惠券用户表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponUserOnlyByCouponSn]couponSn:" + couponSn);
            log.error("[ICouponService][getCouponUserOnlyByCouponSn]根据优惠码序列号取得优惠券用户表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Coupon>> getCouponsForList(@RequestParam("memberId") Integer memberId,
                                                         @RequestParam("sort") Integer sort,
                                                         @RequestParam("channel") Integer channel,
                                                         @RequestBody PagerInfo pager) {
        ServiceResult<List<Coupon>> result = new ServiceResult<List<Coupon>>();
        result.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(couponModel.getCouponsForListCount(channel));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            result.setResult(couponModel.getCouponsForList(memberId, sort, channel, start, size));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[ICouponService][getCouponsForList]优惠券领取列表获取优惠券时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][getCouponsForList]优惠券领取列表获取优惠券时发生异常:", e);
        }
        return result;
    }

    /**
     * 根据用户ID统计优惠劵的数量
     * @param memberId
     * @return
     * @see com.yixiekeji.service.promotion.ICouponService#countCouponUserByMemberId(java.lang.Integer)
     */
    @Override
    public ServiceResult<Integer> countCouponUserByMemberId(@RequestParam("memberId") Integer memberId) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(couponUserModel.getCouponUserByMemberIdCount(memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[ICouponService][getCouponUserOnlyByCouponSn]根据用户ID统计优惠劵的数量出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ICouponService][countCouponUserByMemberId]根据用户ID统计优惠劵的数量:" + memberId);
            log.error("[ICouponService][countCouponUserByMemberId]根据用户ID统计优惠劵的数量时发生异常:", e);
        }
        return serviceResult;
    }

}