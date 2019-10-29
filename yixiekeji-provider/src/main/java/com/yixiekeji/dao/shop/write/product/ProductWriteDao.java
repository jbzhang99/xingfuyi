package com.yixiekeji.dao.shop.write.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.product.Product;

@Mapper
public interface ProductWriteDao {

    /**
     * 冻结或解冻商家下的商品
     * @param sellerId 商家ID
     * @param state 商品状态，冻结时传入5，解冻时传入 （1、刚创建；2、提交审核；3、审核通过；4、申请驳回；5、商品删除；6、上架；7、下架）
     * @param sellerState 商家状态，解冻（1、店铺正常），冻结（2、店铺关闭）
     * @return
     */
    Integer freezeProductsBySellerId(@Param("sellerId") Integer sellerId,
                                     @Param("sellerState") Integer sellerState);

    //    /**
    //     * 修改商品库存<br>
    //     * set `product_stock`= `product_stock` - #{number}
    //     * @param id 商品ID
    //     * @param number 修改的数量（如果是负数，则表示可能由于退货等原因还原库存）
    //     * @return
    //     */
    //    Integer updateStock(@Param("id") Integer id, @Param("number") Integer number);
    //
    //    /**
    //     * 修改商品实际销量<br>
    //     * set `actual_sales`= `actual_sales` + #{number}
    //     * @param id 商品ID
    //     * @param number 修改的数量（如果是负数，则表示可能由于退货等原因还原销量）
    //     * @return
    //     */
    //    Integer updateActualSales(@Param("id") Integer id, @Param("number") Integer number);

    /**
     * 修改商品评论数<br>
     * set `comments_number`= `comments_number` + #{number}
     * @param id 商品ID
     * @param number 修改的数量
     * @return
     */
    Integer updateCommentsNumber(@Param("id") Integer id, @Param("number") Integer number);

    Integer insert(Product product);

    Integer update(Product product);

    Integer del(Integer id);

    Product get(Integer id);

    Integer count(@Param("param1") Map<String, String> queryMap);

    /**
     * state in 方式查询
     * @param queryMap
     * @return
     */
    Integer count1(@Param("param1") Map<String, String> queryMap);

    List<Product> page(@Param("param1") Map<String, String> queryMap, @Param("start") Integer start,
                       @Param("size") Integer size);

    /**
     * state in 方式查询
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<Product> page1(@Param("param1") Map<String, String> queryMap,
                        @Param("start") Integer start, @Param("size") Integer size);

    List<Product> getProductsBySellerId(Integer sellerid);

    /**
     * 根据平台分类ID统计在售商品
     * @param productCateId
     * @return
     */
    int countByCateId(@Param("productCateId") Integer productCateId);

    /**
     * 根据商家分类ID统计在售商品
     * @param sellerCateId
     * @return
     */
    int countBySellerCateId(@Param("sellerCateId") Integer sellerCateId);

    Integer updateByIds(@Param("param1") Map<String, Object> queryMap,
                        @Param("ids") List<Integer> ids);

    /**
     * 根据商品ID修改状态
     * @param id
     * @param state
     * @return
     */
    int updateState(@Param("id") Integer id, @Param("state") Integer state);

    /**
     * 根据商品ID修改推荐状态
     * @param id
     * @param isTop
     * @return
     */
    int updateRecommend(@Param("id") Integer id, @Param("isTop") Integer isTop);

    /**
     * 根据商家ID、spu、商品ID查询
     * @param sellerid
     * @param productCode
     * @param productId
     * @return
     */
    Integer getByIdAndSellerIdAndSpu(@Param("sellerId") Integer sellerId,
                                     @Param("productCode") String productCode,
                                     @Param("productId") Integer productId);

    /**
     * 修改商品实际销量以及库存<br>
     * set `product_stock` = `stock` - #{number}
     *     `actual_sales`= `actual_sales` + #{number}
     * @param id 商品ID
     * @param number 修改的数量（如果是负数，则表示可能由于取消订单等原因还原销量和库存）
     * @return
     */
    Integer updateActualSalesAndStock(@Param("id") Integer id, @Param("number") Integer number);

    /**
     * 更新分容比例
     * @param saleScale1
     * @param saleScale2
     * @return
     */
    Integer updateSaleScale(@Param("id") Integer productId,
                            @Param("saleScale1") BigDecimal saleScale1,
                            @Param("saleScale2") BigDecimal saleScale2);
}
