package com.yixiekeji.vo.product;

import java.io.Serializable;
import java.util.List;

import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.single.ActSingle;

/**
 * 用于商品单品页异步加载活动信息
 * 
 * @Filename: ProductActVO.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
public class ProductActVO implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long   serialVersionUID = -2350521208155705102L;

    /** 活动阶段:1、抢购结束，2、正在进行，3、即将开始 */
    public static final int     STAGE_TYPE_1     = 1;
    /** 活动阶段:1、抢购结束，2、正在进行，3、即将开始 */
    public static final int     STAGE_TYPE_2     = 2;
    /** 活动阶段:1、抢购结束，2、正在进行，3、即将开始 */
    public static final int     STAGE_TYPE_3     = 3;

    // 普通商品展示需要信息
    private ActSingle           actSingle;                               // 单品立减活动
    private ActFull             actFull;                                 // 订单满减活动
    private List<Coupon>        couponList;                              // 优惠券list

   
    private Integer             stageType;                               // 活动阶段:1、抢购结束，2、正在进行，3、即将开始

    private String              flashPrice;

    public ActSingle getActSingle() {
        return actSingle;
    }

    public void setActSingle(ActSingle actSingle) {
        this.actSingle = actSingle;
    }

    public ActFull getActFull() {
        return actFull;
    }

    public void setActFull(ActFull actFull) {
        this.actFull = actFull;
    }

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    

    public Integer getStageType() {
        return stageType;
    }

    public void setStageType(Integer stageType) {
        this.stageType = stageType;
    }

    public String getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(String flashPrice) {
        this.flashPrice = flashPrice;
    }

}
