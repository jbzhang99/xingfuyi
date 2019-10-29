package com.yixiekeji.dao.shop.read.mindex;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MIndexFloorData;

@Mapper
public interface MIndexFloorDataReadDao {

    MIndexFloorData get(java.lang.Integer id);

    Integer getMIndexFloorDatasCount(@Param("queryMap") Map<String, String> queryMap);

    List<MIndexFloorData> getMIndexFloorDatas(@Param("queryMap") Map<String, String> queryMap,
                                              @Param("start") Integer start,
                                              @Param("size") Integer size);

    /**
     * 根据楼层ID取得首页楼层数据表
     * @param mIndexFloorId
     * @return
     */
    List<MIndexFloorData> getMIndexFloorDatasByFloorId(Integer mIndexFloorId);

}