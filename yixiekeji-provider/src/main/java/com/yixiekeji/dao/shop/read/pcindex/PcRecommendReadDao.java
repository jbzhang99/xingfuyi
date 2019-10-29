package com.yixiekeji.dao.shop.read.pcindex;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcRecommend;

@Mapper
public interface PcRecommendReadDao {

    PcRecommend get(java.lang.Integer id);

    Integer getPcRecommendsCount(@Param("queryMap") Map<String, String> queryMap);

    List<PcRecommend> getPcRecommends(@Param("queryMap") Map<String, String> queryMap,
                                      @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 为展示页面取数据，数据order by `order_no`，默认取当前时间在展示时间之内的数据
     * 
     * @param isPreview 0:不是预览，取status为1的数据，1：预览，取status小于3的数据
     * @return
     */
    List<PcRecommend> getPcRecommendsForView(@Param("recommendType") Integer recommendType,
                                             @Param("isPreview") Integer isPreview,
                                             @Param("start") Integer start,
                                             @Param("size") Integer size);

    Integer getPcRecommendsForViewCount(@Param("recommendType") Integer recommendType,
                                        @Param("isPreview") Integer isPreview);
}