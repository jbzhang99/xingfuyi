package com.yixiekeji.vo.product;

import java.io.Serializable;

public class ProductInfoConvert implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1841894775616292867L;
    private Integer           productCateId;
    private Integer           productId;

    public Integer getProductCateId() {
        return productCateId;
    }

    public void setProductCateId(Integer productCateId) {
        this.productCateId = productCateId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
