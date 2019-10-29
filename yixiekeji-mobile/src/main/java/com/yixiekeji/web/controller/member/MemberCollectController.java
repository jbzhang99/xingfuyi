package com.yixiekeji.web.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberCollectionProduct;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.service.member.IMemberCollectionProductService;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.vo.member.FrontMemberCollectionProductVO;
import com.yixiekeji.vo.member.FrontMemberCollectionSellerVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户中心：我的收藏
 * 
 * @Filename: MemberCollectController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberCollectController extends BaseController {

    @Resource
    private IMemberCollectionSellerService  memberCollectionSellerService;

    @Resource
    private IMemberCollectionProductService memberCollectionProductService;

    @Resource
    private IProductAskService              productAskService;

    @Resource
    private IAnalysisService                analysisService;

    /**
     * 跳转到 收藏商品界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/collectProduct.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> collectProduct(HttpServletRequest request,
                                                                            HttpServletResponse response,
                                                                            Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        Map<String, Object> queryMap = new HashMap<String, Object>();

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);
        FeignUtil feignUtil = FeignUtil.getFeignUtilObject(queryMap, pager);
        ServiceResult<List<FrontMemberCollectionProductVO>> productResult = memberCollectionProductService
            .getCollectionProductByMemberId(feignUtil, member.getId());
        pager = productResult.getPager();

        dataMap.put("productList", productResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 跳转到 收藏店铺界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/collectSeller.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> collectSeller(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);

        ServiceResult<List<FrontMemberCollectionSellerVO>> sellerResult = memberCollectionSellerService
            .getCollectionSellerByMemberid(member.getId(), pager);
        pager = sellerResult.getPager();

        dataMap.put("sellerList", sellerResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndexSeller", pageIndex);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 关注店铺
     * @param request
     * @param response
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "/docollectshop.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> collectionShop(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @RequestParam(value = "sellerId", required = true) Integer sellerId) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = memberCollectionSellerService
            .collectionSeller(sellerId, member.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;

    }

    /**
     * 取消收藏商铺
     * @param request
     * @param response
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "/cancelcollectshop.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> cancelCollectionShop(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      @RequestParam(value = "sellerId", required = true) Integer sellerId) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = memberCollectionSellerService
            .cancelCollectionSeller(sellerId, member.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 关注商品
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/docollectproduct.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<MemberCollectionSeller> collectionProduct(HttpServletRequest request,
                                                                                  HttpServletResponse response,
                                                                                  @RequestParam(value = "productId", required = true) Integer productId) throws Exception {
        HttpJsonResult<MemberCollectionSeller> jsonResult = new HttpJsonResult<MemberCollectionSeller>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();
        serviceResult = memberCollectionProductService.saveMemberCollectionProduct(productId,
            member.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<MemberCollectionSeller>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

    /**
     * 取消收藏商品
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/cancelcollectproduct.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<MemberCollectionProduct> cancelCollectionProduct(HttpServletRequest request,
                                                                                         HttpServletResponse response,
                                                                                         @RequestParam(value = "productId", required = true) Integer productId) throws Exception {
        HttpJsonResult<MemberCollectionProduct> jsonResult = new HttpJsonResult<MemberCollectionProduct>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();

        serviceResult = memberCollectionProductService.cancelCollectionProduct(productId,
            member.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<MemberCollectionProduct>(
                    serviceResult.getMessage());
            }
        }
        return jsonResult;
    }

}
