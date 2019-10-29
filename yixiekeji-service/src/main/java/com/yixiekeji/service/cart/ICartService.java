package com.yixiekeji.service.cart;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.cart.Cart;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.vo.cart.CartInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 购物车service
 *
 * @Filename: ICartService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "cart")
@Service(value = "cartService")
public interface ICartService {

    /**
     * 添加到购物车
     *
     * @param cart
     * @return 购物车ID
     */
    @RequestMapping(value = "addToCart", method = RequestMethod.POST)
    ServiceResult<Integer> addToCart(Cart cart);

    /**
     * 根据登录用户取得购物车信息，用于购物车列表页，所有数据都从写库获取
     *
     * @param memberId
     * @param memberAddress 用户选择的地址信息，用于计算运费，如果是null表示不计算运费
     * @param source        来源，1、pc；2、H5；3、Android；4、IOS；
     * @param useType       使用类型：1、购物车用（取所有购物车记录），2、订单结算用（取用户勾选的记录）
     * @return
     */
    @RequestMapping(value = "getCartInfoByMId", method = RequestMethod.POST)
    public ServiceResult<CartInfoVO> getCartInfoByMId(@RequestParam("memberId") Integer memberId,
                                                      @RequestBody MemberAddress memberAddress,
                                                      @RequestParam("source") Integer source,
                                                      @RequestParam("useType") Integer useType);

    /**
     * 根据登录用户取得购物车信息，用于购物车列表页，所有数据都从写库获取，不计算运费
     *
     * @param memberId
     * @param source   来源，1、pc；2、H5；3、Android；4、IOS；
     * @param useType  使用类型：1、购物车用（取所有购物车记录），2、订单结算用（取用户勾选的记录）
     * @return
     */
    @RequestMapping(value = "getCartByMId", method = RequestMethod.GET)
    public ServiceResult<CartInfoVO> getCartByMId(@RequestParam("memberId") Integer memberId,
                                                  @RequestParam("source") Integer source,
                                                  @RequestParam("useType") Integer useType);

    /**
     * 根据购物车id更新商城购物车，只更新商品数量
     *
     * @param id
     * @param number
     * @return
     */
    @RequestMapping(value = "updateCartNumber", method = RequestMethod.GET)
    ServiceResult<Boolean> updateCartNumber(@RequestParam("id") Integer id,
                                            @RequestParam("number") Integer number);

    /**
     * 批量删除购物车商品
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteCartByIds", method = RequestMethod.POST)
    ServiceResult<Boolean> deleteCartByIds(List<Integer> ids);

    /**
     * 根据id取得商城购物车
     *
     * @param cartId
     * @return
     */
    @RequestMapping(value = "getCartById", method = RequestMethod.GET)
    ServiceResult<Cart> getCartById(@RequestParam("cartId") Integer cartId);

    /**
     * 系统定时任务清除7天之前添加的购物车数据
     *
     * @return
     */
    @RequestMapping(value = "jobClearCart", method = RequestMethod.GET)
    ServiceResult<Boolean> jobClearCart();

    /**
     * 根据用户ID获取用户购物车数量
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getCartNumberByMId", method = RequestMethod.GET)
    ServiceResult<Integer> getCartNumberByMId(@RequestParam("memberId") Integer memberId);

    /**
     * 根据用户ID、购物车ID修改购物车的选中状态，cartId为0时表示全部选中或不选中
     *
     * @param memberId
     * @param cartId
     * @param checked
     * @return
     */
    @RequestMapping(value = "updateChecked", method = RequestMethod.GET)
    ServiceResult<Boolean> updateChecked(@RequestParam("memberId") Integer memberId,
                                         @RequestParam("cartId") Integer cartId,
                                         @RequestParam("checked") Integer checked);

    /**
     * 根据用户ID取得此用户购物车中最后加入的一个商品
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getCartByLastOne", method = RequestMethod.GET)
    ServiceResult<Cart> getCartByLastOne(@RequestParam("memberId") Integer memberId);

    @RequestMapping(value = "updateCheckedAll", method = RequestMethod.POST)
    ServiceResult<Boolean> updateCheckedAll(@RequestParam("memberId") Integer memberId,
                                            @RequestParam("checked") Integer checked);

    /**
     * @Description: 修改购物车商品状态
     * @Date: 2019/9/25
     */
    @RequestMapping(value = "updateStatusById", method = RequestMethod.GET)
    ServiceResult<Boolean> updateStatusById(@RequestParam("id") Integer id);

    @RequestMapping(value = "getCartByPid", method = RequestMethod.GET)
    Integer getCartByPid(@RequestParam("productId")Integer productId);
}