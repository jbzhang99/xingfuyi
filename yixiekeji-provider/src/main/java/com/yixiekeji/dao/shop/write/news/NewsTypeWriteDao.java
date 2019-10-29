package com.yixiekeji.dao.shop.write.news;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.news.NewsType;

@Mapper
public interface NewsTypeWriteDao {

    NewsType get(java.lang.Integer id);

    Integer save(NewsType newsType);

    Integer update(NewsType newsType);

    Integer del(Integer id);
}