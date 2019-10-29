package com.yixiekeji.dao.shop.read.news;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.news.NewsPartner;

@Mapper
public interface NewsPartnerReadDao {

    NewsPartner get(java.lang.Integer id);

    Integer getCount(@Param("param1") Map<String, String> queryMap);

    List<NewsPartner> page(@Param("param1") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);
}