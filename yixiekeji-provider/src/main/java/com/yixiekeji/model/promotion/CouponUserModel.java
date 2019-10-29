package com.yixiekeji.model.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.EjavashopSequence;
import com.yixiekeji.core.Md5;
import com.yixiekeji.core.RandomUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.coupon.CouponReadDao;
import com.yixiekeji.dao.shop.read.coupon.CouponUserReadDao;
import com.yixiekeji.dao.shop.read.member.MemberReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.coupon.CouponOptLogWriteDao;
import com.yixiekeji.dao.shop.write.coupon.CouponUserWriteDao;
import com.yixiekeji.dao.shop.write.coupon.CouponWriteDao;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.coupon.CouponOptLog;
import com.yixiekeji.entity.coupon.CouponUser;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerUser;

@Component(value = "couponUserModel")
public class CouponUserModel {

    private static Logger                log = LoggerFactory.getLogger(CouponUserModel.class);

    @Resource
    private CouponReadDao                couponReadDao;
    @Resource
    private CouponWriteDao               couponWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;
    @Resource
    private SellerReadDao                sellerReadDao;
    @Resource
    private CouponUserReadDao            couponUserReadDao;
    @Resource
    private CouponUserWriteDao           couponUserWriteDao;
    @Resource
    private MemberReadDao                memberReadDao;
    @Resource
    private OrdersReadDao                ordersReadDao;
    @Resource
    private CouponOptLogWriteDao         couponOptLogWriteDao;

    /**
     * 根据id取得优惠券用户
     * @param  couponUserId
     * @return
     */
    public CouponUser getCouponUserById(Integer couponUserId) {
        return couponUserReadDao.get(couponUserId);
    }

    /**
     * 保存优惠券用户
     * @param  couponUser
     * @return
     */
    public boolean saveCouponUser(CouponUser couponUser) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer row = couponUserWriteDao.insert(couponUser);

            if (row > 0) {
                couponWriteDao.updateReceivedNum(couponUser.getCouponId(), 1);
            }

            transactionManager.commit(status);
            return row > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("保存优惠券用户时出现未知异常：" + e);
            throw e;
        }
    }

    /**
     * 更新优惠券用户
     * @param couponUser
     * @return
     */
    public boolean updateCouponUser(CouponUser couponUser) {
        return couponUserWriteDao.update(couponUser) > 0;
    }

    /**
     * 删除优惠券用户
     * @param couponUser
     * @return
     */
    public boolean deleteCouponUser(Integer couponUserId, Integer userId, String userName) {
        return couponUserWriteDao.delete(couponUserId) > 0;
    }

    public Integer getCouponUsersCount(Map<String, String> queryMap) {
        return couponUserReadDao.getCouponUsersCount(queryMap);
    }

    public List<CouponUser> getCouponUsers(Map<String, String> queryMap, Integer start,
                                           Integer size) {
        List<CouponUser> couponUsers = couponUserReadDao.getCouponUsers(queryMap, start, size);
        this.setExtendAttr(couponUsers);
        return couponUsers;
    }

    public Integer getCouponUserByMemberIdCount(Integer memberId) {
        return couponUserReadDao.getCouponUserByMemberIdCount(memberId);
    }

    /**
     * 根据用户ID获取用户的优惠券
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    public List<CouponUser> getCouponUserByMemberId(Integer memberId, Integer start, Integer size) {
        List<CouponUser> couponUsers = couponUserReadDao.getCouponUserByMemberId(memberId, start,
            size);
        this.setExtendAttr(couponUsers);
        return couponUsers;
    }

    /**
     * 设置扩展属性
     * @param couponUsers
     */
    private void setExtendAttr(List<CouponUser> couponUsers) {
        if (couponUsers != null && couponUsers.size() > 0) {
            // 保存取到的商家信息，避免多次读取同一条数据，增加效率(下同理)
            Map<Integer, Seller> map = new HashMap<Integer, Seller>();
            Map<Integer, Coupon> couponMap = new HashMap<Integer, Coupon>();
            Map<Integer, Member> memberMap = new HashMap<Integer, Member>();
            for (CouponUser couponUser : couponUsers) {
                // 店铺名称
                if (map.get(couponUser.getSellerId()) != null) {
                    couponUser.setSellerName(map.get(couponUser.getSellerId()).getSellerName());
                } else {
                    Seller seller = sellerReadDao.get(couponUser.getSellerId());
                    if (seller != null) {
                        map.put(seller.getId(), seller);
                        couponUser.setSellerName(seller.getSellerName());
                    }
                }

                // 用户名称
                if (couponUser.getMemberId() > 0) {
                    if (memberMap.get(couponUser.getMemberId()) != null) {
                        couponUser.setMemberName(memberMap.get(couponUser.getMemberId()).getName());
                    } else {
                        Member member = memberReadDao.get(couponUser.getMemberId());
                        if (member != null) {
                            memberMap.put(member.getId(), member);
                            couponUser.setMemberName(member.getName());
                        }
                    }

                }
                // 优惠券名称
                if (couponMap.get(couponUser.getCouponId()) != null) {
                    couponUser
                        .setCouponName(couponMap.get(couponUser.getCouponId()).getCouponName());
                    couponUser
                        .setCouponValue(couponMap.get(couponUser.getCouponId()).getCouponValue());
                    couponUser.setMinAmount(couponMap.get(couponUser.getCouponId()).getMinAmount());
                    couponUser.setChannel(couponMap.get(couponUser.getCouponId()).getChannel());
                } else {
                    Coupon coupon = couponReadDao.get(couponUser.getCouponId());
                    if (coupon != null) {
                        couponMap.put(coupon.getId(), coupon);
                        couponUser.setCouponName(coupon.getCouponName());
                        couponUser.setCouponValue(coupon.getCouponValue());
                        couponUser.setMinAmount(coupon.getMinAmount());
                        couponUser.setChannel(coupon.getChannel());
                    }
                }

                // 订单序列号
                if (couponUser.getOrderId() > 0) {
                    Orders orders = ordersReadDao.get(couponUser.getOrderId());
                    if (orders != null) {
                        couponUser.setOrderSn(orders.getOrderSn());
                    }
                }
            }
        }
    }

    /**
     * 根据优惠券ID导出exportNum数量的优惠券信息，用于线下发放
     * 
     * @param couponId 优惠券ID
     * @param exportNum 导出数量
     * @return
     */
    public List<CouponUser> exportCoupon(Integer couponId, Integer exportNum, Integer sellerId,
                                         SellerUser sellerUser) {
        Coupon coupon = couponReadDao.get(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券信息取得失败。");
        }
        if (!coupon.getSellerId().equals(sellerId)) {
            throw new BusinessException("不能操作其他店铺的优惠券。");
        }
        if (coupon.getType().intValue() != Coupon.TYPE_2) {
            throw new BusinessException("该优惠券不能执行导出操作。");
        }
        if (coupon.getStatus().intValue() != Coupon.STATUS_5) {
            throw new BusinessException("该优惠券还没有上架。");
        }
        if (coupon.getSendStartTime().after(new Date())) {
            throw new BusinessException("优惠券还没有到导出时间。");
        }
        if (coupon.getSendEndTime().before(new Date())) {
            throw new BusinessException("优惠券已经过了导出时间。");
        }
        if ((coupon.getReceivedNum() + exportNum) > coupon.getTotalLimitNum()) {
            throw new BusinessException(
                "最多还能导出" + (coupon.getTotalLimitNum() - coupon.getReceivedNum()) + "张优惠券。");
        }

        List<CouponUser> list = new ArrayList<CouponUser>();
        for (int i = 0; i < exportNum; i++) {
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {

                // 从写库读取优惠券信息，并比较已发放数量和总限制数量，如果发放数量大于等于总限制数量，则结束循环
                // 此处检查是防止两个人同时操作优惠券导出导致超量
                Coupon couponWrite = couponWriteDao.get(couponId);
                if (couponWrite.getReceivedNum() >= couponWrite.getTotalLimitNum()) {
                    transactionManager.commit(status);
                    log.error("导出数量已达到上限。");
                    break;
                }
                CouponUser couponUser = new CouponUser();

                couponUser.setMemberId(0);
                couponUser.setSellerId(sellerId);
                couponUser.setCouponId(coupon.getId());
                couponUser.setCouponSn(coupon.getPrefix() + "-" + EjavashopSequence.getKey());
                String password = RandomUtil.randomNumber(6);
                couponUser.setPassword(Md5.getMd5String(password));
                couponUser.setCanUse(1);
                couponUser.setReceiveTime(null);
                couponUser.setOrderId(0);
                couponUser.setUseTime(null);
                couponUser.setUseStartTime(coupon.getUseStartTime());
                couponUser.setUseEndTime(coupon.getUseEndTime());
                couponUser.setCreateUserId(sellerUser.getId());
                couponUser.setCreateUserName(sellerUser.getName());
                couponUser.setCreateTime(new Date());
                couponUser.setUpdateUserId(sellerUser.getId());
                couponUser.setUpdateUserName(sellerUser.getName());
                couponUser.setUpdateTime(new Date());

                Integer count = couponWriteDao.updateReceivedNum(couponId, 1);
                if (count != 1) {
                    throw new BusinessException("优惠券领取数量修改失败。");
                }

                count = couponUserWriteDao.insert(couponUser);

                // 执行成功
                if (count == 1) {
                    couponUser.setPassword(password);
                    list.add(couponUser);
                }

                transactionManager.commit(status);
            } catch (Exception e) {
                transactionManager.rollback(status);
                log.error("导出优惠券时出现未知异常：", e);
            }
        }

        return list;
    }

    /**
     * 用户领取优惠券
     * @param couponId 优惠券ID
     * @param memberId 用户ID
     * @return
     */
    public boolean receiveCoupon(Integer couponId, Integer memberId) {
        Coupon coupon = couponReadDao.get(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券信息取得失败。");
        }
        if (coupon.getType().intValue() != Coupon.TYPE_1) {
            throw new BusinessException("该优惠券不能直接领取。");
        }
        if (coupon.getStatus().intValue() != Coupon.STATUS_5) {
            throw new BusinessException("该优惠券还没有上架。");
        }
        if (coupon.getSendStartTime().after(new Date())) {
            throw new BusinessException("优惠券还没有到领取时间。");
        }
        if (coupon.getSendEndTime().before(new Date())) {
            throw new BusinessException("优惠券已经过了领取时间。");
        }
        // 默认一次领取1张
        if ((coupon.getReceivedNum() + 1) > coupon.getTotalLimitNum()) {
            throw new BusinessException("优惠券已经被领完了。");
        }
        // 读库取数据，避免数据库同步的时间差
        Integer receivedNum = couponUserWriteDao.getNumByMemberIdAndCouponId(memberId, couponId);
        if (coupon.getPersonLimitNum() > 0 && receivedNum != null
            && receivedNum >= coupon.getPersonLimitNum()) {
            throw new BusinessException("您已经领取过该优惠券了。");
        }
        Member member = memberReadDao.get(memberId);
        if (member == null) {
            throw new BusinessException("用户信息获取失败，请稍后再试。");
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            CouponUser couponUser = new CouponUser();

            couponUser.setMemberId(memberId);
            couponUser.setSellerId(coupon.getSellerId());
            couponUser.setCouponId(coupon.getId());
            couponUser.setCouponSn(coupon.getPrefix() + "-" + EjavashopSequence.getKey());
            // 在线领取默认密码123456（在线领取的密码不会用到）
            couponUser.setPassword(Md5.getMd5String("123456"));
            // 默认使用一次
            couponUser.setCanUse(1);
            couponUser.setReceiveTime(new Date());
            couponUser.setOrderId(0);
            couponUser.setUseTime(null);
            couponUser.setUseStartTime(coupon.getUseStartTime());
            couponUser.setUseEndTime(coupon.getUseEndTime());
            couponUser.setCreateUserId(member.getId());
            couponUser.setCreateUserName(member.getName());
            couponUser.setCreateTime(new Date());
            couponUser.setUpdateUserId(member.getId());
            couponUser.setUpdateUserName(member.getName());
            couponUser.setUpdateTime(new Date());

            Integer count = couponWriteDao.updateReceivedNum(couponId, 1);
            if (count != 1) {
                log.error("修改优惠券领取数量时失败：memberId=" + memberId + ",couponId=" + couponId);
                throw new BusinessException("优惠券领取失败，请稍后再试。");
            }

            count = couponUserWriteDao.insert(couponUser);

            // 记录优惠券操作日志
            CouponOptLog couponOptLog = new CouponOptLog();
            couponOptLog.setCouponUserId(couponUser.getId());
            couponOptLog.setMemberId(couponUser.getMemberId());
            couponOptLog.setSellerId(couponUser.getSellerId());
            couponOptLog.setCouponId(couponUser.getCouponId());
            couponOptLog.setOptType(CouponOptLog.OPT_TYPE_1);
            couponOptLog.setOrderId(0);
            couponOptLog.setCreateUserId(member.getId());
            couponOptLog.setCreateUserName(member.getName());
            couponOptLog.setCreateTime(new Date());
            couponOptLogWriteDao.insert(couponOptLog);

            transactionManager.commit(status);
            return count > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("领取优惠券时出现未知异常：", e);
            throw e;
        }

    }

    public int getCouponUserByMemberIdAndUseCount(Integer memberId, Integer canUse) {
        return couponUserReadDao.getCouponUserByMemberIdAndUseCount(memberId, canUse);
    }

    public List<CouponUser> getCouponUserByMemberIdAndUse(Integer memberId, Integer canUse,
                                                          Integer start, Integer size) {
        List<CouponUser> couponUsers = couponUserReadDao.getCouponUserByMemberIdAndUse(memberId,
            canUse, start, size);
        this.setExtendAttr(couponUsers);
        return couponUsers;
    }

    /**
     * 根据用户ID、商家ID获取当前时间有效可用的优惠券
     * @param memberId
     * @param sellerId
     * @return
     */
    public List<CouponUser> getEffectiveByMemberIdAndSellerId(Integer memberId, Integer sellerId) {
        List<CouponUser> couponUsers = couponUserReadDao.getEffectiveByMemberIdAndSellerId(memberId,
            sellerId);
        this.setExtendAttr(couponUsers);
        return couponUsers;
    }

    /**
     * 根据优惠码序列号取得优惠券用户表
     * @param couponSn
     * @return
     */
    public CouponUser getCouponUserOnlyByCouponSn(String couponSn) {
        CouponUser couponUser = couponUserReadDao.getCouponUserOnlyByCouponSn(couponSn);
        if (couponUser == null) {
            throw new BusinessException("优惠券不存在，请确认是否输入正确。");
        }
        List<CouponUser> list = new ArrayList<CouponUser>();
        list.add(couponUser);
        this.setExtendAttr(list);
        return couponUser;
    }

}