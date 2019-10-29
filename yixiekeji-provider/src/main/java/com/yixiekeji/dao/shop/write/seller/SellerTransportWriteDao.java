package com.yixiekeji.dao.shop.write.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerTransport;

@Mapper
public interface SellerTransportWriteDao {

    SellerTransport get(java.lang.Integer id);

    Integer save(SellerTransport sellerTransport);

    Integer update(SellerTransport sellerTransport);

    Integer getCount(Map<String, Object> queryMap);

    List<SellerTransport> page(Map<String, Object> queryMap);

    Integer del(Integer id);

    Integer updateInUseById(@Param("id") Integer id, @Param("state") Integer state);
}
