package com.yixiekeji.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品销量dto
 *                       
 * @Filename: ProductSaleDto.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
public class ProductSaleDto implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1076587258467937536L;

    /**
     * 网单金额
     */
    private BigDecimal        moneyAmount;

    /**
     * 商品名称
     */
    private String            productName;
    /**
     * 货品规格
     */
    private String            specInfo;
    /**
     * 是否启用规格 1-否 2-启用
     */
    private Integer           isNorm;
    /**
     * 创建时间(如果按年则是年月，按月查则是年月日)
     */
    private String            createTime;

    //按不同的维度统计的数据
    private List<Object>      data;

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecInfo() {
        return specInfo;
    }

    public void setSpecInfo(String specInfo) {
        this.specInfo = specInfo;
    }

    public Integer getIsNorm() {
        return isNorm;
    }

    public void setIsNorm(Integer isNorm) {
        this.isNorm = isNorm;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

}
