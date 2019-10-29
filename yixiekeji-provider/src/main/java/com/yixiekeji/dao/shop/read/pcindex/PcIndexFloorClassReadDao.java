package com.yixiekeji.dao.shop.read.pcindex;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexFloorClass;

@Mapper
public interface PcIndexFloorClassReadDao {

    PcIndexFloorClass get(java.lang.Integer id);

    Integer getPcIndexFloorClasssCount(@Param("queryMap") Map<String, String> queryMap);

    List<PcIndexFloorClass> getPcIndexFloorClasss(@Param("queryMap") Map<String, String> queryMap,
                                                  @Param("start") Integer start,
                                                  @Param("size") Integer size);

    //    /**
    //     * 为展示页面取数据，数据order by `order_no`
    //     * 
    //     * @param isPreview 0:不是预览，取status为1的数据，1：预览，取status小于3的数据
    //     * @return
    //     */
    //    List<PcIndexFloorClass> getPcIndexFloorClasssForView(@Param("isPreview") Integer isPreview);

    /**
     * 根据楼层ID取分类
     * @param floorId
     * @param isPreview 0:不是预览，取status为1的数据，1：预览，取status小于3的数据
     * @return
     */
    List<PcIndexFloorClass> getPcIndexFloorClasssForView(@Param("floorId") Integer floorId,
                                                         @Param("isPreview") Integer isPreview);
}