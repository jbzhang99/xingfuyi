package com.yixiekeji.dao.shop.read.pcseller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcseller.PcSellerIndex;

@Mapper
public interface PcSellerIndexReadDao {

    PcSellerIndex get(java.lang.Integer id);

    PcSellerIndex getBySellerId(java.lang.Integer sellerId);

    Integer getPcSellerIndexsCount(@Param("queryMap") Map<String, String> queryMap);

    List<PcSellerIndex> getPcSellerIndexs(@Param("queryMap") Map<String, String> queryMap,
                                          @Param("start") Integer start,
                                          @Param("size") Integer size);

}