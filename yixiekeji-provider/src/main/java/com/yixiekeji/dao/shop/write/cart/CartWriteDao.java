package com.yixiekeji.dao.shop.write.cart;

import com.yixiekeji.entity.cart.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartWriteDao {

    Integer update(Cart cart);

    /**
     * 添加购物车时，对已存在的货品增加数量
     * @param id
     * @param count
     * @return
     */
    Integer addCount(@Param("id") Integer id, @Param("count") Integer count);

    Integer delete(Integer id);

    Integer deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据用户ID和使用类型取购物车数据
     * @param memberId 用户ID
     * @param useType 使用类型：1、购物车用（取所有购物车记录），2、订单结算用（只取用户勾选的记录）
     * @return
     */
    List<Cart> getByMemberId(@Param("memberId") Integer memberId,
                             @Param("useType") Integer useType);

    /**
     * 根据会员ID 删除购物车内信息，固定一个条件checked=1
     * @param memberId
     * @return
     */
    Integer deleteByMemberId(Integer memberId);

    Integer jobClearCart(@Param("clearTime") String clearTime);

    Cart get(java.lang.Integer id);

    Integer insert(Cart cart);

    /**
     * 根据用户ID、购物车ID修改购物车的选中状态
     * @param memberId
     * @param cartId
     * @param checked
     * @return
     */
    Integer updateChecked(@Param("memberId") Integer memberId, @Param("cartId") Integer cartId,
                          @Param("checked") Integer checked);

    /**
     * 根据用户ID修改购物车的选中状态
     * @param memberId
     * @param checked
     * @return
     */
    Integer updateCheckedAll(@Param("memberId") Integer memberId,
                             @Param("checked") Integer checked);

    /**
     * 根据用户ID和货品ID获取购物车
     * @param memberId
     * @param productGoodsId
     * @return
     */
    List<Cart> getByMIdAndGoodId(@Param("memberId") Integer memberId,
                                 @Param("productGoodsId") Integer productGoodsId);

    /** 
    * @Description:  修改购物车商品状态
    * @Date: 2019/9/25
    */ 
    Boolean updateStatusById(@Param("id")Integer id);

    boolean updateCartById(@Param("id")Integer id, @Param("count")Integer count);
}
