package com.yixiekeji.dao.shop.read.mindex;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.mindex.MRecommend;

@Mapper
public interface MRecommendReadDao {

    MRecommend get(java.lang.Integer id);

    Integer getMRecommendsCount(@Param("queryMap") Map<String, String> queryMap);

    List<MRecommend> getMRecommends(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 为展示页面取数据，数据order by `order_no`，默认取当前时间在展示时间之内的数据
     * 
     * @param isPreview 0:取status为1的数据，1：预览，取所有的数据
     * @return
     */
    List<MRecommend> getMRecommendsForView(@Param("recommendType") Integer recommendType,
                                           @Param("isPreview") Integer isPreview,
                                           @Param("start") Integer start,
                                           @Param("size") Integer size);

    Integer getMRecommendsForViewCount(@Param("recommendType") Integer recommendType,
                                       @Param("isPreview") Integer isPreview);
}