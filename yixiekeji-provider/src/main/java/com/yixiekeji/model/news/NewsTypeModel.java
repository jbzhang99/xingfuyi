package com.yixiekeji.model.news;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.news.NewsTypeReadDao;
import com.yixiekeji.dao.shop.write.news.NewsTypeWriteDao;
import com.yixiekeji.entity.news.NewsType;

@Component(value = "newsTypeModel")
public class NewsTypeModel {

    @Resource
    private NewsTypeWriteDao newsTypeWriteDao;
    @Resource
    private NewsTypeReadDao  newsTypeReadDao;

    public NewsType get(Integer newsTypeId) {
        return newsTypeWriteDao.get(newsTypeId);
    }

    public Integer save(NewsType newsType) {
        return newsTypeWriteDao.save(newsType);
    }

    public Integer update(NewsType newsType) {
        return newsTypeWriteDao.update(newsType);
    }

    public int getCount(Map<String, String> queryMap) {
        return newsTypeReadDao.getCount(queryMap);
    }

    public List<NewsType> page(Map<String, String> queryMap, Integer start, Integer size) {
        return newsTypeReadDao.page(queryMap, start, size);
    }

    public List<NewsType> list() {
        return newsTypeReadDao.list();
    }

    public int del(Integer id) {
        return newsTypeWriteDao.del(id);
    }

}
