package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerTransport;

@Mapper
public interface SellerTransportReadDao {

    SellerTransport get(java.lang.Integer id);

    Integer getCount(Map<String, Object> queryMap);

    List<SellerTransport> page(Map<String, Object> queryMap);

    /**
     * 根据sellerId获取该商家下正在使用的运费模板，如果有多个只取第一个
     * @param sellerId
     * @return
     */
    SellerTransport getInUseBySellerId(Integer sellerId);

    /**
     * 根据商家ID获取有效的运费模板
     * @param sellerId
     * @return
     */
    List<SellerTransport> getEffectTransportBySellerId(Integer sellerId);

    /**
     * 根据模板计算类型获取商家可用模板
     * @param transportType
     * @param sellerId
     * @return
     */
    List<SellerTransport> getByTransTypeAndSellerId(@Param("transportType") Integer transportType,
                                                    @Param("sellerId") Integer sellerId);
}
