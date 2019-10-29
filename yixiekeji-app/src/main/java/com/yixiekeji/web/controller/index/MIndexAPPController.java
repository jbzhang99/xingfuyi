package com.yixiekeji.web.controller.index;

import java.util.Iterator;
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
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.entity.mindex.MIndexBanner;
import com.yixiekeji.entity.mindex.MIndexFloor;
import com.yixiekeji.entity.mindex.MRecommend;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.service.mindex.IMIndexService;
import com.yixiekeji.service.mindex.IMRecommendService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.web.controller.BaseController;

@Controller
public class MIndexAPPController extends BaseController {

    @Resource
    private IMIndexService              mIndexService;
    @Resource
    private IProductFrontService        productFrontService;
    @Resource
    private IMRecommendService          mRecommendService;

    @RequestMapping(value = "/index-app-banner.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MIndexBanner>> indexAppBanner(HttpServletRequest request,
                                                                           HttpServletResponse response) throws Exception {
        ServiceResult<List<MIndexBanner>> serviceResult = mIndexService
            .getMIndexBannerForView(false);

        HttpJsonResult<List<MIndexBanner>> jsonResult = new HttpJsonResult<List<MIndexBanner>>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        } else {
            jsonResult.setData(serviceResult.getResult());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/index-app-floor.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MIndexFloor>> indexAppRecommend(HttpServletRequest request,
                                                                             HttpServletResponse response) throws Exception {
        ServiceResult<List<MIndexFloor>> serviceResult = mIndexService
            .getMIndexFloorsWithData(false);

        HttpJsonResult<List<MIndexFloor>> jsonResult = new HttpJsonResult<List<MIndexFloor>>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        } else {
            jsonResult.setData(serviceResult.getResult());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/index-app-recommend.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MRecommend>> indexAppFloor(HttpServletRequest request,
                                                                        HttpServletResponse response) throws Exception {
        // 首页固定取6个
        PagerInfo pager = new PagerInfo(6, 1);
        ServiceResult<List<MRecommend>> serviceResult = mRecommendService
            .getMRecommendForView(MRecommend.RECOMMEND_TYPE_1, false, pager);

        HttpJsonResult<List<MRecommend>> jsonResult = new HttpJsonResult<List<MRecommend>>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        } else {
            jsonResult.setData(serviceResult.getResult());
        }
        return jsonResult;
    }

  

    @RequestMapping(value = "/index.html", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "h5/index/mindex";
    }

}
