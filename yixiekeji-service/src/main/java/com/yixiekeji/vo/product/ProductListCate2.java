package com.yixiekeji.vo.product;

import java.io.Serializable;
import java.util.List;

import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductCate;

/**
 * 二级列表页，商品和分类的VO对象
 *                       
 * @Filename: ProductListCate2.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
public class ProductListCate2 implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 5288152041793584255L;
    private ProductCate       productCate;
    private List<Product>     products;

    public ProductCate getProductCate() {
        return productCate;
    }

    public void setProductCate(ProductCate productCate) {
        this.productCate = productCate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
