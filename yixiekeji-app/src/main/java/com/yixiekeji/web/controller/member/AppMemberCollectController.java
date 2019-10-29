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
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberCollectionProduct;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.service.member.IMemberCollectionProductService;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.member.IMemberService;
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
public class AppMemberCollectController extends BaseController {

    @Resource
    private IMemberCollectionSellerService  memberCollectionSellerService;

    @Resource
    private IMemberService                  memberService;

    @Resource
    private IMemberCollectionProductService memberCollectionProductService;

    @Resource
    private IProductAskService              productAskService;

    @Resource
    private IAnalysisService                analysisService;

    /**
     * 跳转到 收藏界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-collect.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> collect(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     Map<String, Object> dataMap,
                                                                     Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        String type = request.getParameter("type");
        if (StringUtil.isEmpty(type, true)) {
            type = "product";
        }
        dataMap.put("type", type);

        Member member = memberService.getMemberById(memberId).getResult();

        Map<String, Object> queryMap = new HashMap<String, Object>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        FeignUtil feignUtil = FeignUtil.getFeignUtilObject(queryMap, pager);

        ServiceResult<List<FrontMemberCollectionProductVO>> productResult = memberCollectionProductService
            .getCollectionProductByMemberId(feignUtil, member.getId());
        pager = productResult.getPager();
        dataMap.put("productList", productResult.getResult());
        dataMap.put("productCount", pager.getRowsCount());

        pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);

        ServiceResult<List<FrontMemberCollectionSellerVO>> sellerResult = memberCollectionSellerService
            .getCollectionSellerByMemberid(member.getId(), pager);
        dataMap.put("sellerList", sellerResult.getResult());
        dataMap.put("sellerCount", pager.getRowsCount());

        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE_10);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 加载更多收藏的商品
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-morecollectproduct.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<FrontMemberCollectionProductVO>> moreCollectProduct(HttpServletRequest request,
                                                                                                 HttpServletResponse response,
                                                                                                 Integer memberId) {
        HttpJsonResult<List<FrontMemberCollectionProductVO>> jsonResult = new HttpJsonResult<List<FrontMemberCollectionProductVO>>();

        Member member = memberService.getMemberById(memberId).getResult();

        String productPageIndexStr = request.getParameter("pageIndex");
        Integer productPageIndex = ConvertUtil.toInt(productPageIndexStr, 1);

        Map<String, Object> queryMap = new HashMap<String, Object>();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, productPageIndex);

        FeignUtil feignUtil = FeignUtil.getFeignUtilObject(queryMap, pager);
        ServiceResult<List<FrontMemberCollectionProductVO>> productResult = memberCollectionProductService
            .getCollectionProductByMemberId(feignUtil, member.getId());

        jsonResult.setData(productResult.getResult());
        return jsonResult;
    }

    /**
     * 加载更多收藏的店铺
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/app-morecollectseller.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<FrontMemberCollectionSellerVO>> moreCollectSeller(HttpServletRequest request,
                                                                                               HttpServletResponse response,
                                                                                               Integer memberId) {
        HttpJsonResult<List<FrontMemberCollectionSellerVO>> jsonResult = new HttpJsonResult<List<FrontMemberCollectionSellerVO>>();

        Member member = memberService.getMemberById(memberId).getResult();

        String sellerPageIndexStr = request.getParameter("pageIndex");
        Integer sellerPageIndex = ConvertUtil.toInt(sellerPageIndexStr, 1);

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, sellerPageIndex);

        ServiceResult<List<FrontMemberCollectionSellerVO>> sellerResult = memberCollectionSellerService
            .getCollectionSellerByMemberid(member.getId(), pager);

        jsonResult.setData(sellerResult.getResult());

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
        Member member = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<Boolean> serviceResult = memberCollectionSellerService
            .collectionSeller(sellerId, member.getId());

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
                                                                      Integer memberId,
                                                                      @RequestParam(value = "sellerId", required = true) Integer sellerId) {

        Member member = memberService.getMemberById(memberId).getResult();

        ServiceResult<Boolean> serviceResult = memberCollectionSellerService
            .cancelCollectionSeller(sellerId, member.getId());

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
        Member member = WebFrontSession.getLoginedUser(request,response);
        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();
        serviceResult = memberCollectionProductService.saveMemberCollectionProduct(productId,
            member.getId());

        HttpJsonResult<MemberCollectionSeller> jsonResult = new HttpJsonResult<MemberCollectionSeller>();
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
                                                                                         Integer memberId,
                                                                                         @RequestParam(value = "productId", required = true) Integer productId) throws Exception {
        Member member = memberService.getMemberById(memberId).getResult();

        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();

        serviceResult = memberCollectionProductService.cancelCollectionProduct(productId,
            member.getId());

        HttpJsonResult<MemberCollectionProduct> jsonResult = new HttpJsonResult<MemberCollectionProduct>();
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
