package com.yixiekeji.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;

public class FeignProjectUtil implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long       serialVersionUID = -4615150437537727586L;

    public static final String      FEIGN_NAME       = "YIXIEKEJI";

    private static FeignProjectUtil feignProjectUtil = new FeignProjectUtil();

    private FeignProjectUtil() {
    }

    public static FeignProjectUtil getFeignProjectUtil() {
        if (feignProjectUtil == null) {
            feignProjectUtil = new FeignProjectUtil();
        }
        return feignProjectUtil;
    }

    private Map<String, String[]> parammap;
    private Product               product;
    private ProductGoods          productGoods;
    private List<ProductPicture>  productPictureList;
    private List<ProductAttr>     productAttrList;

    private Map<String, Object>   map;
    private List<Integer>         integerList;

    private ProductNorm           productNorm;
    private List<ProductNormAttr> productNormAttrList;

    private ProductType           productType;
    private List<ProductTypeAttr> productTypeAttrList;

    public Map<String, String[]> getParammap() {
        return parammap;
    }

    public void setParammap(Map<String, String[]> parammap) {
        this.parammap = parammap;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductPicture> getProductPictureList() {
        return productPictureList;
    }

    public void setProductPictureList(List<ProductPicture> productPictureList) {
        this.productPictureList = productPictureList;
    }

    public List<ProductAttr> getProductAttrList() {
        return productAttrList;
    }

    public void setProductAttrList(List<ProductAttr> productAttrList) {
        this.productAttrList = productAttrList;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public ProductGoods getProductGoods() {
        return productGoods;
    }

    public void setProductGoods(ProductGoods productGoods) {
        this.productGoods = productGoods;
    }

    public ProductNorm getProductNorm() {
        return productNorm;
    }

    public void setProductNorm(ProductNorm productNorm) {
        this.productNorm = productNorm;
    }

    public List<ProductNormAttr> getProductNormAttrList() {
        return productNormAttrList;
    }

    public void setProductNormAttrList(List<ProductNormAttr> productNormAttrList) {
        this.productNormAttrList = productNormAttrList;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public List<ProductTypeAttr> getProductTypeAttrList() {
        return productTypeAttrList;
    }

    public void setProductTypeAttrList(List<ProductTypeAttr> productTypeAttrList) {
        this.productTypeAttrList = productTypeAttrList;
    }

}
