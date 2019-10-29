package com.yixiekeji.dao.shop.write.news;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.news.News;

@Mapper
public interface NewsWriteDao {

    News get(java.lang.Integer id);

    Integer save(News news);

    Integer update(News news);

    Integer del(Integer id);
}