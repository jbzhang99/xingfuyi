package com.yixiekeji.dao.shop.write.news;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.news.NewsPartner;

@Mapper
public interface NewsPartnerWriteDao {

    NewsPartner get(java.lang.Integer id);

    Integer save(NewsPartner newsPartner);

    Integer update(NewsPartner newsPartner);

    Integer del(Integer id);
}