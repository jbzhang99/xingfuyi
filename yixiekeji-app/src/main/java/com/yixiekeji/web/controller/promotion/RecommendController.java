package com.yixiekeji.web.controller.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.mindex.MRecommend;
import com.yixiekeji.service.mindex.IMRecommendService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 推荐列表controller
 * 
 * @Filename: RecommendController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class RecommendController extends BaseController {

    @Resource
    private IMRecommendService mRecommendService;

    private final static int   TUAN_PAGESIZE = 20;

    /**
     * 多惠部落列表页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/recommend.html", method = { RequestMethod.GET })
    public String coupon(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> dataMap) {
        // 分页
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, TUAN_PAGESIZE);

        ServiceResult<List<MRecommend>> rmdResult = mRecommendService.getMRecommendForView(
            MRecommend.RECOMMEND_TYPE_1, false, pager);
        if (!rmdResult.getSuccess()) {
            dataMap.put("info", rmdResult.getMessage());
            return "h5/commons/error";
        }

        dataMap.put("recommendList", rmdResult.getResult());

        String url = request.getRequestURI() + "";
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(), String.valueOf(pager
            .getPageIndex()), pager.getRowsCount(), url);
        dataMap.put("page", pm);
        dataMap.put("pagesize", TUAN_PAGESIZE);

        return "h5/promotion/recommendlist";
    }

    /**
     * 返回json结果
     * @param request
     * @param stack
     * @return
     */
    @RequestMapping(value = "/recommenJson.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<MRecommend>> recommenJson(HttpServletRequest request,
                                                                       Map<String, Object> dataMap) {
        HttpJsonResult<List<MRecommend>> jsonResult = new HttpJsonResult<List<MRecommend>>();
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, TUAN_PAGESIZE);

        ServiceResult<List<MRecommend>> rmdResult = mRecommendService.getMRecommendForView(
            MRecommend.RECOMMEND_TYPE_1, false, pager);

        jsonResult.setRows(rmdResult.getResult());
        jsonResult.setTotal(rmdResult.getResult().size());
        return jsonResult;
    }

}
