package com.yixiekeji.web.controller.product;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.CookieHelper;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.search.SearchLogs;
import com.yixiekeji.entity.search.SearchRecord;
import com.yixiekeji.service.search.ISearchLogsService;
import com.yixiekeji.service.search.ISearchRecordService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 搜索关键字日志相关的处理
 *                       
 * @Filename: ProductSearchLogsController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductSearchLogsController extends BaseController {

    @Resource
    private ISearchLogsService   searchLogsService;

    @Resource
    private ISearchRecordService searchRecordService;

    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 根据keyword获取搜索最近N条记录
     * @return
     */
    @RequestMapping(value = "/get_search_record.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<SearchRecord>> getSearchRecord(HttpServletRequest request,
                                                                            HttpServletResponse responsed,
                                                                            String keyword) {
        HttpJsonResult<List<SearchRecord>> jsonResult = new HttpJsonResult<List<SearchRecord>>();

        if (keyword != null && !"".equals(keyword.trim())) {
            ServiceResult<List<SearchRecord>> servletResult = searchRecordService
                .getSearchRecordByKeyword(keyword, 8);
            if (!servletResult.getSuccess()) {
                log.error("[ProductSearchLogsController][getSearchLogs]根据cookie获取搜索最近N条记录出现异常");
            }

            List<SearchRecord> searchLogss = servletResult.getResult();
            jsonResult.setData(searchLogss);

            if (searchLogss != null) {
                jsonResult.setTotal(searchLogss.size());
            }

        }
        return jsonResult;
    }

    /**
     * 根据cookie获取搜索最近N条记录
     * @return
     */
    @RequestMapping(value = "/get_search_logs.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<SearchLogs>> getSearchLogs(HttpServletRequest request,
                                                                        HttpServletResponse responsed) {
        HttpJsonResult<List<SearchLogs>> jsonResult = new HttpJsonResult<List<SearchLogs>>();

        Cookie cookie = CookieHelper.getCookieByName(request, domainUrlUtil.getCookieName());
        String cookieValue = cookie == null ? "" : cookie.getValue();

        ServiceResult<List<SearchLogs>> servletResult = searchLogsService
            .getSearchLogsByCookie(cookieValue, 8);
        if (!servletResult.getSuccess()) {
            log.error("[ProductSearchLogsController][getSearchLogs]根据cookie获取搜索最近N条记录出现异常");
        }

        List<SearchLogs> searchLogss = servletResult.getResult();
        jsonResult.setData(searchLogss);

        if (searchLogss != null) {
            jsonResult.setTotal(searchLogss.size());
        }
        return jsonResult;
    }

    /**
     * 根据cookie获取搜索最近N条记录
     * @return
     */
    @RequestMapping(value = "/save_search_logs.html", method = RequestMethod.GET)
    public void saveSearchLogs(HttpServletRequest request, HttpServletResponse responsed,
                               String keyword) {
        Cookie cookie = CookieHelper.getCookieByName(request, domainUrlUtil.getCookieName());
        String cookieValue = cookie == null ? "" : cookie.getValue();

        String memberId = "0";
        Member member = WebFrontSession.getLoginedUser(request, responsed);
        if (member != null && member.getId() != null) {
            memberId = member.getId().toString();
        }

        if (keyword != null && !"".equals(keyword.trim())) {
            SearchLogs searchLogs = new SearchLogs();
            searchLogs.setKeyword(keyword);
            searchLogs.setSiteCookie(cookieValue);
            searchLogs.setIp(WebUtil.getIpAddr(request));
            searchLogs.setMemberId(ConvertUtil.toInt(memberId, ConvertUtil.toInt(memberId, 0)));
            searchLogsService.saveSearchLogs(searchLogs);
        }

    }
}
