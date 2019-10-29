package com.yixiekeji.dao.shop.read.mseller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mseller.MSellerIndexFloorData;

@Mapper
public interface MSellerIndexFloorDataReadDao {

    MSellerIndexFloorData get(java.lang.Integer id);

    Integer getMSellerIndexFloorDatasCount(@Param("queryMap") Map<String, String> queryMap);

    List<MSellerIndexFloorData> getMSellerIndexFloorDatas(@Param("queryMap") Map<String, String> queryMap,
                                                          @Param("start") Integer start,
                                                          @Param("size") Integer size);

    /**
     * 根据楼层ID取得首页楼层数据表
     * @param mIndexFloorId
     * @return
     */
    List<MSellerIndexFloorData> getMSellerIndexFloorDatasByFloorId(Integer mIndexFloorId);

}