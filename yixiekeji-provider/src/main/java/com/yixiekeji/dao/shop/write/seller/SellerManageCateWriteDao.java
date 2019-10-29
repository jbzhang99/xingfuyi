package com.yixiekeji.dao.shop.write.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerManageCate;

@Mapper
public interface SellerManageCateWriteDao {

    Integer insert(SellerManageCate sellerManageCate);

    Integer update(SellerManageCate sellerManageCate);

    Integer del(Integer id);

    SellerManageCate get(Integer id);

    SellerManageCate getCateBySellerId(@Param("cateId") Integer cateId,
                                       @Param("seller") Integer seller);

    Integer count(@Param("param1") Map<String, String> queryMap);

    List<SellerManageCate> page(@Param("param1") Map<String, String> queryMap,
                                @Param("start") Integer start, @Param("size") Integer size);

    Integer updateState(@Param("id") Integer id, @Param("state") Integer state);
}
