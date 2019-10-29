package com.yixiekeji.vo.member;

import java.io.Serializable;

import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.entity.seller.Seller;

/**
 * 会员收藏商铺表VO
 *
 */
public class FrontMemberCollectionSellerVO extends MemberCollectionSeller implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 339204372920082704L;
    private String            sellerName;                            // 店铺名称
    private String            sellerLogo;                            // 商家信息
    private Seller            seller;                                // 店铺对象

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

}