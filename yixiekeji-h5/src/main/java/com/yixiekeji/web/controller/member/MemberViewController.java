package com.yixiekeji.web.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.analysis.ProductLookLog;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 我的浏览记录
 * 
 * @Filename: MemberViewController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberViewController extends BaseController {

    @Resource
    private IAnalysisService analysisService;

    /**
     * 跳转到 商品浏览界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/viewed.html", method = { RequestMethod.GET })
    public String viewed(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request);
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId() + "");
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductLookLog>> serviceResult = analysisService
            .getProductLookLogs(feignUtil);
        pager = serviceResult.getPager();

        dataMap.put("lookLogList", serviceResult.getResult());
        dataMap.put("logCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());

        return "h5/member/person/productlooklog";
    }

    /**
     * ajax异步加载更多
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/moreviewed.html", method = { RequestMethod.GET })
    public String moreViewed(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> dataMap, Integer pageIndex) {
        Member member = WebFrontSession.getLoginedUser(request);
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId() + "");
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductLookLog>> serviceResult = analysisService
            .getProductLookLogs(feignUtil);

        dataMap.put("lookLogList", serviceResult.getResult());

        return "h5/member/person/productlookloglist";
    }
}
