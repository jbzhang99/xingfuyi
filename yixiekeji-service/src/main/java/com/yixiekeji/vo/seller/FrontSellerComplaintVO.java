package com.yixiekeji.vo.seller;

import java.io.Serializable;

import com.yixiekeji.entity.order.OrdersProduct;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerComplaint;

public class FrontSellerComplaintVO extends SellerComplaint implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1292472741542057948L;
    private String            orderSn;                                //订单编号
    private String            productName;                            //产品名称
    private Integer           productId;                              //产品id

    private Product           product;                                // 商品
    private OrdersProduct     ordersProduct;                          // 网单

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrdersProduct getOrdersProduct() {
        return ordersProduct;
    }

    public void setOrdersProduct(OrdersProduct ordersProduct) {
        this.ordersProduct = ordersProduct;
    }

}
