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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.analysis.ProductLookLog;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.web.controller.BaseController;

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
public class AppMemberViewController extends BaseController {

    @Resource
    private IAnalysisService analysisService;
    @Resource
    private IMemberService   memberService;

    /**
     * 跳转到 商品浏览界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-viewed.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> viewed(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    Map<String, Object> dataMap,
                                                                    Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = memberService.getMemberById(memberId).getResult();

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

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * ajax异步加载更多
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-moreviewed.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductLookLog>> moreViewed(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> dataMap,
                                                                         Integer pageIndex,
                                                                         Integer memberId) {
        HttpJsonResult<List<ProductLookLog>> jsonResult = new HttpJsonResult<List<ProductLookLog>>();

        Member member = memberService.getMemberById(memberId).getResult();

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId() + "");
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);

        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductLookLog>> serviceResult = analysisService
            .getProductLookLogs(feignUtil);

        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }
}
