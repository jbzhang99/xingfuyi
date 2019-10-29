package com.yixiekeji.service.news;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.news.News;
import com.yixiekeji.entity.news.NewsType;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "news")
@Service(value = "newsService")
public interface INewsService {

    /**
     * 首页新闻分类
     * @return
     */
    @RequestMapping(value = "getNewsType", method = RequestMethod.GET)
    ServiceResult<List<NewsType>> getNewsType();

    /**
     * 获取给定分类下所有新闻
     * @param id
     * @return
     */
    @RequestMapping(value = "collectionSeller", method = RequestMethod.POST)
    ServiceResult<List<News>> getNewsByType(@RequestParam("typeId") int typeId,
                                            @RequestBody PagerInfo pager);

    @RequestMapping(value = "get", method = RequestMethod.GET)
    ServiceResult<News> get(@RequestParam("id") Integer id);

    /**
     * 首页新闻分类
     * @return
     */
    @RequestMapping(value = "getNewsType4Article", method = RequestMethod.GET)
    ServiceResult<List<NewsType>> getNewsType4Article();

    /**
     * 最新新闻
     * @return
     */
    @RequestMapping(value = "getLastedNews", method = RequestMethod.GET)
    ServiceResult<List<News>> getLastedNews();

    /**
     * 根据id取得新闻资讯
     * @param  newsId
     * @return
     */
    @RequestMapping(value = "getNewsById", method = RequestMethod.GET)
    ServiceResult<News> getNewsById(@RequestParam("newsId") Integer newsId);

    /**
     * 保存新闻资讯
     * @param  news
     * @return
     */
    @RequestMapping(value = "saveNews", method = RequestMethod.POST)
    ServiceResult<Integer> saveNews(News news);

    /**
    * 更新新闻资讯
    * @param  news
    * @return
    */
    @RequestMapping(value = "updateNews", method = RequestMethod.POST)
    ServiceResult<Integer> updateNews(News news);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<News>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}