package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.Seller;

@Mapper
public interface SellerReadDao {

    Seller get(java.lang.Integer id);

    /**
     * 获取结算商家
     * @return
     */
    List<Seller> getSettlementSeller();

    /**
     * 根据用户ID获取商家
     * @param memberId
     * @return
     */
    Seller getByMemberId(@Param("memberId") Integer memberId);

    Integer getSellersCount(@Param("queryMap") Map<String, String> queryMap);

    List<Seller> getSellers(@Param("queryMap") Map<String, String> queryMap,
                            @Param("start") Integer start, @Param("size") Integer size);

}
