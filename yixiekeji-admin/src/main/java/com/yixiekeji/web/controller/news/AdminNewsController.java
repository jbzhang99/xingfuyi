package com.yixiekeji.web.controller.news;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.news.News;
import com.yixiekeji.entity.news.NewsType;
import com.yixiekeji.service.news.INewsService;
import com.yixiekeji.service.news.INewsTypeService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 文章相关action
 *                       
 * @Filename: AdminNewsController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/system/news", produces = "application/json;charset=UTF-8")
public class AdminNewsController extends BaseController {

    @Resource
    private INewsService     newsService;

    @Resource
    private INewsTypeService newsTypeService;

    @Resource
    private DomainUrlUtil    domainUrlUtil;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "admin/news/news/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<News>> list(HttpServletRequest request,
                                                         Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<News>> serviceResult = newsService.page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        List<News> newsList = serviceResult.getResult();
        if (newsList != null) {
            for (News news : newsList) {
                String content = news.getContent();
                if (!StringUtil.isEmpty(content, true)) {
                    news.setContent(news.getContent().replace("${(domainUrlUtil.imageResources)!}",
                        domainUrlUtil.getImageResources()));
                }
            }
        }

        HttpJsonResult<List<News>> jsonResult = new HttpJsonResult<List<News>>();
        jsonResult.setRows(newsList);
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 新增页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        ServiceResult<List<NewsType>> typelist = this.newsTypeService.list();
        dataMap.put("typelist", typelist.getResult());

        return "admin/news/news/edit";
    }

    /**
     * 编辑页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Map<String, Object> dataMap, String id) {
        News news = this.newsService.getNewsById(Integer.valueOf(id)).getResult();

        ServiceResult<List<NewsType>> typelist = this.newsTypeService.list();
        dataMap.put("typelist", typelist.getResult());

        if (!isNull(news.getContent())) {
            news.setContent(news.getContent().replace("${(domainUrlUtil.imageResources)!}",
                domainUrlUtil.getImageResources()));
        }

        dataMap.put("obj", news);
        return "admin/news/news/edit";
    }

    /**
     * 添加新闻
     * @param request
     * @param response
     * @param news
     * @return
     */
    @RequestMapping(value = "doAdd", method = { RequestMethod.POST })
    public String doAdd(HttpServletRequest request, HttpServletResponse response, News news) {
        news.setCreateTime(new Date());
        if (!isNull(news.getContent())) {
            news.setContent(news.getContent().replace(domainUrlUtil.getImageResources(),
                "${(domainUrlUtil.imageResources)!}"));
        }

        if (news.getId() != null && news.getId() != 0) {
            news.setUpdateTime(new Date());

            if (!StringUtil.isEmpty(news.getContent())) {
                String description = news.getContent();
                description = description.replaceAll(System.getProperty("line.separator"), "");
                news.setContent(description);
            }

            this.newsService.updateNews(news);
        } else {
            news.setCreateTime(new Date());
            news.setUpdateTime(new Date());
            news.setCreateId(WebAdminSession.getAdminUser(request).getId());
            news.setAuthor(WebAdminSession.getAdminUser(request).getName());
            news.setSource("");

            if (!StringUtil.isEmpty(news.getContent())) {
                String description = news.getContent();
                description = description.replaceAll(System.getProperty("line.separator"), "");
                news.setContent(description);
            }
            this.newsService.saveNews(news);
        }

        return "redirect:/admin/system/news";
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param news
     * @return
     */
    @RequestMapping(value = "del", method = { RequestMethod.GET })
    public void del(HttpServletRequest request, HttpServletResponse response, String id) {
        this.newsService.del(Integer.valueOf(id));
    }

}
