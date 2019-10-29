package com.yixiekeji.dao.shop.read.news;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.news.NewsType;

@Mapper
public interface NewsTypeReadDao {

    NewsType get(java.lang.Integer id);

    Integer getCount(@Param("param1") Map<String, String> queryMap);

    List<NewsType> page(@Param("param1") Map<String, String> queryMap,
                        @Param("start") Integer start, @Param("size") Integer size);

    List<NewsType> list();

}