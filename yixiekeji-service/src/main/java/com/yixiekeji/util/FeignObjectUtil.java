package com.yixiekeji.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.SystemAdmin;

public class FeignObjectUtil implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long      serialVersionUID = -4615150437537727586L;

    public static final String     FEIGN_NAME       = "YIXIEKEJI";

    private static FeignObjectUtil feignObjectUtil  = new FeignObjectUtil();

    private FeignObjectUtil() {
    }

    public static FeignObjectUtil getFeignUtil() {
        if (feignObjectUtil == null) {
            feignObjectUtil = new FeignObjectUtil();
        }
        return feignObjectUtil;
    }

    private MemberProductBack     memberProductBack;
    private Member                member;
    private SellerUser            sellerUser;
    private MemberProductExchange memberProductExchange;
    private SellerComplaint       sellerComplaint;

    private Orders                orders;
    private SystemAdmin           systemAdmin;

    private ProductAsk            productAsk;

    private Map<String, Object>   queryMapObject1;
    private Map<String, Object>   queryMapObject2;


    public MemberProductBack getMemberProductBack() {
        return memberProductBack;
    }

    public void setMemberProductBack(MemberProductBack memberProductBack) {
        this.memberProductBack = memberProductBack;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public SellerUser getSellerUser() {
        return sellerUser;
    }

    public void setSellerUser(SellerUser sellerUser) {
        this.sellerUser = sellerUser;
    }

    public MemberProductExchange getMemberProductExchange() {
        return memberProductExchange;
    }

    public void setMemberProductExchange(MemberProductExchange memberProductExchange) {
        this.memberProductExchange = memberProductExchange;
    }

    public SellerComplaint getSellerComplaint() {
        return sellerComplaint;
    }

    public void setSellerComplaint(SellerComplaint sellerComplaint) {
        this.sellerComplaint = sellerComplaint;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public SystemAdmin getSystemAdmin() {
        return systemAdmin;
    }

    public void setSystemAdmin(SystemAdmin systemAdmin) {
        this.systemAdmin = systemAdmin;
    }

    public ProductAsk getProductAsk() {
        return productAsk;
    }

    public void setProductAsk(ProductAsk productAsk) {
        this.productAsk = productAsk;
    }

    public Map<String, Object> getQueryMapObject1() {
        return queryMapObject1;
    }

    public void setQueryMapObject1(Map<String, Object> queryMapObject1) {
        this.queryMapObject1 = queryMapObject1;
    }

    public Map<String, Object> getQueryMapObject2() {
        return queryMapObject2;
    }

    public void setQueryMapObject2(Map<String, Object> queryMapObject2) {
        this.queryMapObject2 = queryMapObject2;
    }

  

}
