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

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
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

    /**
     * 多惠部落列表页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/recommend.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> coupon(HttpServletRequest request,
                                                                    HttpServletResponse response,
                                                                    Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        // 分页
        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageIndex);

        ServiceResult<List<MRecommend>> rmdResult = mRecommendService
            .getMRecommendForView(MRecommend.RECOMMEND_TYPE_1, false, pager);
        if (!rmdResult.getSuccess()) {
            jsonResult.setMessage(rmdResult.getMessage());
            return jsonResult;
        }
        pager = rmdResult.getPager();

        dataMap.put("recommendList", rmdResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

}
