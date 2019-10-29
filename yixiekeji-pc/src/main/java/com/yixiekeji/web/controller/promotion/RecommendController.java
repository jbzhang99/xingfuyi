package com.yixiekeji.web.controller.promotion;

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
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.entity.pcindex.PcRecommend;
import com.yixiekeji.service.pcindex.IPcRecommendService;
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
    private IPcRecommendService pcRecommendService;

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
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap,
            ConstantsEJS.DEFAULT_PAGE_SIZE);

        ServiceResult<List<PcRecommend>> rmdResult = pcRecommendService
            .getPcRecommendForView(PcRecommend.RECOMMEND_TYPE_1, false, pager);
        if (!rmdResult.getSuccess()) {
            dataMap.put("info", rmdResult.getMessage());
            return "front/commons/error";
        }
        pager = rmdResult.getPager();

        dataMap.put("recommendList", rmdResult.getResult());

        String url = request.getRequestURI() + "";
        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), url);
        dataMap.put("page", pm);

        return "front/promotion/recommendlist";
    }

}
