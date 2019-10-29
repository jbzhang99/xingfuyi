package com.yixiekeji.model.promotion;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.coupon.CouponReadDao;
import com.yixiekeji.dao.shop.read.coupon.CouponUserReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.coupon.CouponWriteDao;
import com.yixiekeji.dao.shop.write.coupon.LogCouponWriteDao;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.LogCoupon;
import com.yixiekeji.entity.seller.Seller;

@Component(value = "couponModel")
public class CouponModel {

    private static Logger                log = LoggerFactory.getLogger(CouponModel.class);

    @Resource
    private CouponReadDao                couponReadDao;
    @Resource
    private CouponWriteDao               couponWriteDao;
    @Resource
    private LogCouponWriteDao            logCouponWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;
    @Resource
    private SellerReadDao                sellerReadDao;
    @Resource
    private CouponUserReadDao            couponUserReadDao;

    /**
     * 根据id取得优惠券
     * @param  couponId
     * @return
     */
    public Coupon getCouponById(Integer couponId) {
        return couponReadDao.get(couponId);
    }

    /**
     * 保存优惠券
     * @param  coupon
     * @return
     */
    public boolean saveCoupon(Coupon coupon) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer row = couponWriteDao.insert(coupon);

            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setOptType(ConstantsEJS.DATA_OPT_TYPE_C);
            logCoupon.setOptUserId(coupon.getUpdateUserId());
            logCoupon.setOptUserName(coupon.getUpdateUserName());
            logCoupon.setOptTime(new Date());
            logCoupon.setCouponId(coupon.getId());
            logCouponWriteDao.insert(logCoupon);

            transactionManager.commit(status);
            return row > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("保存优惠券时出现未知异常：" + e);
            throw e;
        }
    }

    /**
     * 更新优惠券
     * @param coupon
     * @return
     */
    public boolean updateCoupon(Coupon coupon) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            Coupon couponDb = couponReadDao.get(coupon.getId());
            if(couponDb == null){
            	throw new BusinessException("获取数据失败，请重试。");
            }
            if (coupon.getSellerId() == null
                    || couponDb.getSellerId().intValue() != coupon.getSellerId().intValue()) {
                    throw new BusinessException("只能修改自己店铺的优惠券。");
            }
            if (couponDb.getStatus().intValue() != Coupon.STATUS_1
                && couponDb.getStatus().intValue() != Coupon.STATUS_4) {
                throw new BusinessException("只能修改新建或审核失败的优惠券。");
            }
            

            Integer row = couponWriteDao.update(coupon);

            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setOptType(ConstantsEJS.DATA_OPT_TYPE_U);
            logCoupon.setOptUserId(coupon.getUpdateUserId());
            logCoupon.setOptUserName(coupon.getUpdateUserName());
            logCoupon.setOptTime(new Date());
            logCoupon.setCouponId(coupon.getId());
            logCouponWriteDao.insert(logCoupon);

            transactionManager.commit(status);
            return row > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("修改优惠券时出现未知异常：" + e);
            throw e;
        }
    }

    /**
     * 更新优惠券状态
     * @param coupon
     * @return
     */
    public boolean updateCouponStatus(Coupon coupon) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer row = couponWriteDao.updateStatus(coupon);

            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setOptType(ConstantsEJS.DATA_OPT_TYPE_U);
            // 如果是审核的情况设定修改人为审核人
            if (coupon.getStatus().intValue() == Coupon.STATUS_3
                || coupon.getStatus().intValue() == Coupon.STATUS_4) {
                logCoupon.setOptUserId(coupon.getAuditUserId());
                logCoupon.setOptUserName(coupon.getAuditUserName());
            } else {
                logCoupon.setOptUserId(coupon.getUpdateUserId());
                logCoupon.setOptUserName(coupon.getUpdateUserName());
            }
            logCoupon.setOptTime(new Date());
            logCoupon.setCouponId(coupon.getId());
            logCouponWriteDao.insert(logCoupon);

            transactionManager.commit(status);
            return row > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("修改优惠券时出现未知异常：" + e);
            throw e;
        }
    }

    /**
     * 删除优惠券
     * @param coupon
     * @return
     */
    public boolean deleteCoupon(Integer couponId, Integer userId, String userName) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            Coupon coupon = couponReadDao.get(couponId);
            if(coupon == null){
            	throw new BusinessException("获取数据失败，请重试。");
            }
            if (coupon.getStatus().intValue() != Coupon.STATUS_1
                && coupon.getStatus().intValue() != Coupon.STATUS_4) {
                throw new BusinessException("只能删除新建或审核失败的优惠券。");
            }

            LogCoupon logCoupon = new LogCoupon();
            logCoupon.setOptType(ConstantsEJS.DATA_OPT_TYPE_D);
            logCoupon.setOptUserId(userId);
            logCoupon.setOptUserName(userName);
            logCoupon.setOptTime(new Date());
            logCoupon.setCouponId(couponId);
            logCouponWriteDao.insert(logCoupon);

            Integer row = couponWriteDao.delete(couponId);

            transactionManager.commit(status);
            return row > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("删除优惠券时出现未知异常：" + e);
            throw e;
        }
    }

    public Integer getCouponsCount(Map<String, String> queryMap) {
        return couponReadDao.getCouponsCount(queryMap);
    }

    public List<Coupon> getCoupons(Map<String, String> queryMap, Integer start, Integer size) {
        List<Coupon> coupons = couponReadDao.getCoupons(queryMap, start, size);

        if (coupons != null && coupons.size() > 0) {
            // 保存取到的商家信息，避免多次读取同一条数据，增加效率
            Map<Integer, Seller> map = new HashMap<Integer, Seller>();
            for (Coupon coupon : coupons) {
                if (map.get(coupon.getSellerId()) != null) {
                    coupon.setSellerName(map.get(coupon.getSellerId()).getSellerName());
                } else {
                    Seller seller = sellerReadDao.get(coupon.getSellerId());
                    if (seller != null) {
                        map.put(seller.getId(), seller);
                        coupon.setSellerName(seller.getSellerName());
                    }
                }
            }
        }
        return coupons;
    }

    /**
     * 根据商家ID、渠道取得优惠券（当前时间在发放时间内、上架、未超发放限额、在线领取类型的优惠券）
     * 
     * @param sellerId
     * @param channel 渠道
     * @return
     */
    public List<Coupon> getEffectiveCoupon(Integer sellerId, Integer channel) {
        return couponReadDao.getEffectiveCoupon(sellerId, channel);
    }

    /**
     * 获取数量
     * @param channel
     * @return
     */
    public Integer getCouponsForListCount(Integer channel) {
        return couponReadDao.getCouponsForListCount(channel);
    }

    /**
     * 优惠券领取列表获取优惠券（当前时间在发放时间内、上架、在线领取类型的优惠券）
     * @param memberId 会员ID（未登录传0）
     * @param sort 排序：0、默认（按创建时间倒排） 1、即将过期 2、面值最大
     * @param channel 渠道
     * @param start
     * @param size
     * @return
     */
    public List<Coupon> getCouponsForList(Integer memberId, Integer sort, Integer channel,
                                          Integer start, Integer size) {
        List<Coupon> coupons = couponReadDao.getCouponsForList(sort, channel, start, size);

        if (coupons != null && coupons.size() > 0) {
            // 保存取到的商家信息，避免多次读取同一条数据，增加效率
            Map<Integer, Seller> map = new HashMap<Integer, Seller>();
            for (Coupon coupon : coupons) {
                // 用户ID不为空，则获取该会员已领取了该优惠券的数量
                if (memberId != null && memberId > 0) {
                    Integer count = couponUserReadDao.getCountByMemberIdAndCouponId(memberId,
                        coupon.getId());
                    coupon.setMemberReceivedNum(count);
                }

                if (map.get(coupon.getSellerId()) != null) {
                    coupon.setSellerName(map.get(coupon.getSellerId()).getSellerName());
                } else {
                    Seller seller = sellerReadDao.get(coupon.getSellerId());
                    if (seller != null) {
                        map.put(seller.getId(), seller);
                        coupon.setSellerName(seller.getSellerName());
                    }
                }
            }
        }
        return coupons;
    }

}