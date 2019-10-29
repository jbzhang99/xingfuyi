package com.yixiekeji.dao.shop.read.cart;

import java.util.List;
import java.util.Map;

import com.yixiekeji.core.ServiceResult;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.cart.Cart;

@Mapper
public interface CartReadDao {

    // 移到write dao
    // List<Cart> getByMemberId(Integer memberId);

    Cart get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<Cart> queryList(Map<String, Object> map);

    /**
     * 根据用户ID获取用户购物车数量
     * @param memberId
     * @return
     */
    Integer getCartNumberByMId(Integer memberId);

    /**
     * 根据用户ID取得此用户购物车中最后加入的一个商品
     * @param memberId
     * @return
     */
    Cart getCartByLastOne(Integer memberId);

    Integer getCartByPid(Integer productId);
}
