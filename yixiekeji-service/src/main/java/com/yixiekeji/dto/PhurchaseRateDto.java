package com.yixiekeji.dto;

import java.io.Serializable;
import java.util.List;

public class PhurchaseRateDto implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6997093708024633323L;
    /**
     * 订单数
     */
    private List<Object>      orderNumList;
    /**
     * pv量
     */
    private List<Object>      pvList;
    /**
     * 购买率
     */
    private List<Object>      rateList;

    public List<Object> getOrderNumList() {
        return orderNumList;
    }

    public void setOrderNumList(List<Object> orderNumList) {
        this.orderNumList = orderNumList;
    }

    public List<Object> getPvList() {
        return pvList;
    }

    public void setPvList(List<Object> pvList) {
        this.pvList = pvList;
    }

    public List<Object> getRateList() {
        return rateList;
    }

    public void setRateList(List<Object> rateList) {
        this.rateList = rateList;
    }

}
