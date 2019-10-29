package com.yixiekeji.service.impl.news;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.news.NewsType;
import com.yixiekeji.model.news.NewsTypeModel;
import com.yixiekeji.service.news.INewsTypeService;

@RestController
public class NewsTypeServiceImpl implements INewsTypeService {
    private static Logger log = LoggerFactory.getLogger(NewsTypeServiceImpl.class);

    @Resource
    private NewsTypeModel newsTypeModel;

    /**
    * 根据id取得文章分类
    * @param  newsTypeId
    * @return
    */
    @Override
    public ServiceResult<NewsType> getNewsTypeById(@RequestParam("newsTypeId") Integer newsTypeId) {
        ServiceResult<NewsType> result = new ServiceResult<NewsType>();
        try {
            result.setResult(newsTypeModel.get(newsTypeId));
        } catch (Exception e) {
            log.error("根据id[" + newsTypeId + "]取得文章分类时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + newsTypeId + "]取得文章分类时出现未知异常");
        }
        return result;
    }

    /**
     * 保存文章分类
     * @param  newsType
     * @return
     */
    @Override
    public ServiceResult<Integer> saveNewsType(@RequestBody NewsType newsType) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();

        try {
            result.setResult(newsTypeModel.save(newsType));
        } catch (Exception e) {
            log.error("保存文章分类时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存文章分类时出现未知异常");
        }
        return result;
    }

    /**
    * 更新文章分类
    * @param  newsType
    * @return
    */
    @Override
    public ServiceResult<Integer> updateNewsType(@RequestBody NewsType newsType) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();

        try {
            result.setResult(newsTypeModel.update(newsType));
        } catch (Exception e) {
            log.error("更新文章分类时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新文章分类时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<NewsType>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<NewsType>> serviceResult = new ServiceResult<List<NewsType>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(newsTypeModel.getCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<NewsType> list = newsTypeModel.page(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[NewsTypeServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[NewsTypeServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<NewsType>> list() {

        ServiceResult<List<NewsType>> serviceResult = new ServiceResult<List<NewsType>>();
        try {
            serviceResult.setResult(newsTypeModel.list());
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[NewsTypeServiceImpl][list]exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            if (id == null)
                throw new BusinessException("删除文章类型[" + id + "]时出错");
            sr.setResult(this.newsTypeModel.del(id) > 0);
        } catch (Exception e) {
            log.error("[NewsTypeServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

}