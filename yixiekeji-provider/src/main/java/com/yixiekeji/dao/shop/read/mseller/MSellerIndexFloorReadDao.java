package com.yixiekeji.dao.shop.read.mseller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexFloor;

@Mapper
public interface MSellerIndexFloorReadDao {

    MSellerIndexFloor get(java.lang.Integer id);

    Integer getMSellerIndexFloorsCount(@Param("queryMap") Map<String, String> queryMap);

    List<MSellerIndexFloor> getMSellerIndexFloors(@Param("queryMap") Map<String, String> queryMap,
                                                  @Param("start") Integer start,
                                                  @Param("size") Integer size);

    /**
     * 为展示页面取数据，如果status不为空则加条件，为空则取所有数据，数据order by order_no
     * @param status
     * @return
     */
    List<MSellerIndexFloor> getMSellerIndexFloorsForView(@Param("sellerId") Integer sellerId,
                                                         @Param("status") String status);
}