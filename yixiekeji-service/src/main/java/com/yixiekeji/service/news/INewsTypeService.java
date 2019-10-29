package com.yixiekeji.service.news;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.news.NewsType;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "newsType")
@Service(value = "newsTypeService")
public interface INewsTypeService {

    /**
     * 根据id取得文章分类
     * @param  newsTypeId
     * @return
     */
    @RequestMapping(value = "getNewsTypeById", method = RequestMethod.GET)
    ServiceResult<NewsType> getNewsTypeById(@RequestParam("newsTypeId") Integer newsTypeId);

    /**
     * 保存文章分类
     * @param  newsType
     * @return
     */
    @RequestMapping(value = "saveNewsType", method = RequestMethod.POST)
    ServiceResult<Integer> saveNewsType(NewsType newsType);

    /**
    * 更新文章分类
    * @param  newsType
    * @return
    */
    @RequestMapping(value = "updateNewsType", method = RequestMethod.POST)
    ServiceResult<Integer> updateNewsType(NewsType newsType);

    /**
     * 分页查询
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<NewsType>> page(FeignUtil feignUtil);

    /**
     * 所有分类列表
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    ServiceResult<List<NewsType>> list();

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}