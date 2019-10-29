package com.yixiekeji.vo.search;

import java.io.Serializable;

public class SearchProductVO implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = -7265668108099137831L;

    private String             id;                                      //商品ID
    private String             brandId;                                 //商品品牌
    private String             brandName;
    private String             sellerId;                                //商品商家
    private String             sellerName;
    private String             productCateId;
    private String             productCatePath;
    private String             cate;                                    //商品分类
    private String             name1;                                   //标题
    private String             content;                                 //描述相关，需要分词其他不需要分词

    private String             masterImg;
    private String             mallPcPrice;
    private String             malMobilePrice;
    private String             marketPrice;
    private String             productStock;
    private String             commentsNumber;

    private String             attrIds;
    private String             attrValues;
    private String             salesNum;
    private String             isSelf;
    private String             sellerCateId;                            //商家分类ID

    public final static String ID_              = "id";
    public final static String BRANDID_         = "brandId";
    public final static String BRANDNAME_       = "brandName";
    public final static String SELLERID_        = "sellerId";
    public final static String SELLERNAME_      = "sellerName";
    public final static String PRODUCTCATEID_   = "productCateId";
    public final static String PRODUCTCATEPATH_ = "productCatePath";
    public final static String CATE_            = "cate";
    public final static String NAME1_           = "name1";
    public final static String CONTENT_         = "content";

    public final static String MASTERIMG_       = "masterImg";
    public final static String MALLPCPRICE_     = "mallPcPrice";
    public final static String MALMOBILEPRICE_  = "malMobilePrice";
    public final static String MARKETPRICE_     = "marketPrice";
    public final static String PRODUCTSTOCK_    = "productStock";
    public final static String COMMENTSNUMBER_  = "commentsNumber";

    public final static String ATTRIDS_         = "attrIds";
    public final static String ATTRVALUES_      = "attrValues";
    public final static String SALESNUM_        = "salesNum";
    public final static String ISSELF_          = "isSelf";
    public final static String SELLERCATEID_    = "sellerCateId";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProductCateId() {
        return productCateId;
    }

    public void setProductCateId(String productCateId) {
        this.productCateId = productCateId;
    }

    public String getProductCatePath() {
        return productCatePath;
    }

    public void setProductCatePath(String productCatePath) {
        this.productCatePath = productCatePath;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMasterImg() {
        return masterImg;
    }

    public void setMasterImg(String masterImg) {
        this.masterImg = masterImg;
    }

    public String getMallPcPrice() {
        return mallPcPrice;
    }

    public void setMallPcPrice(String mallPcPrice) {
        this.mallPcPrice = mallPcPrice;
    }

    public String getMalMobilePrice() {
        return malMobilePrice;
    }

    public void setMalMobilePrice(String malMobilePrice) {
        this.malMobilePrice = malMobilePrice;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public String getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(String commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(String attrIds) {
        this.attrIds = attrIds;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }

    public String getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(String salesNum) {
        this.salesNum = salesNum;
    }

    public String getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public String getSellerCateId() {
        return sellerCateId;
    }

    public void setSellerCateId(String sellerCateId) {
        this.sellerCateId = sellerCateId;
    }

    /**
     * 搜索条件的拼装
     * @param searchKeyword
     * @return
     */
    public static String searchIndexAssembling(String searchKeyword) {
        StringBuilder sb = new StringBuilder();
        sb.append(SearchProductVO.CONTENT_);
        sb.append(":");
        sb.append(searchKeyword);
        sb.append(" OR ");
        sb.append(SearchProductVO.NAME1_);
        sb.append(":");
        sb.append(searchKeyword);
        sb.append(" OR ");
        sb.append(SearchProductVO.BRANDNAME_);
        sb.append(":");
        sb.append(searchKeyword);
        sb.append(" OR ");
        sb.append(SearchProductVO.SELLERNAME_);
        sb.append(":");
        sb.append(searchKeyword);
        sb.append(" OR ");
        sb.append(SearchProductVO.CATE_);
        sb.append(":");
        sb.append(searchKeyword);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "SearchProductVO [id=" + id + ", brandId=" + brandId + ", sellerId=" + sellerId
               + ", cate=" + cate + ", name1=" + name1 + ", content=" + content + ", masterImg="
               + masterImg + ", mallPcPrice=" + mallPcPrice + ", malMobilePrice=" + malMobilePrice
               + ", productStock=" + productStock + ", commentsNumber=" + commentsNumber
               + ", sellerId=" + sellerId + "]";
    }

}
