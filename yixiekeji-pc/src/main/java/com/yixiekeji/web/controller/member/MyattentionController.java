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
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.analysis.ProductLookLog;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberCollectionProduct;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.entity.pcseller.PcSellerIndex;
import com.yixiekeji.service.analysis.IAnalysisService;
import com.yixiekeji.service.member.IMemberCollectionProductService;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.pcseller.IPcSellerIndexService;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.vo.member.FrontMemberCollectionProductVO;
import com.yixiekeji.vo.member.FrontMemberCollectionSellerVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户中心：我的收藏、我的咨询
 * 
 * @Filename: MyattentionController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MyattentionController extends BaseController {

    @Resource
    private IMemberCollectionSellerService  memberCollectionSellerService;

    @Resource
    private IMemberCollectionProductService memberCollectionProductService;

    @Resource
    private IProductAskService              productAskService;

    @Resource
    private IAnalysisService                analysisService;

    @Resource
    private IPcSellerIndexService           pcSellerIndexService;

    /**
     * 跳转到 收藏的商品界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/collectproduct.html", method = { RequestMethod.GET })
    public String toCollectionGoods(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> dataMap) {

        Map<String, Object> queryMap = WebUtil.handlerQueryMapNoQ(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        Member member = WebFrontSession.getLoginedUser(request,response);
        FeignUtil feignUtil = FeignUtil.getFeignUtilObject(queryMap, pager);

        ServiceResult<List<FrontMemberCollectionProductVO>> serviceResult = new ServiceResult<List<FrontMemberCollectionProductVO>>();
        serviceResult = memberCollectionProductService.getCollectionProductByMemberId(feignUtil,
            member.getId());
        pager = serviceResult.getPager();

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("productList", serviceResult.getResult());
        dataMap.put("page", pm);

        return "front/member/collection/collectiongoods";
    }

    /**
     * 跳转到 收藏的店铺界面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/collectshop.html", method = { RequestMethod.GET })
    public String toCollectionShop(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> dataMap) {

        //Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);

        Member member = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<List<FrontMemberCollectionSellerVO>> serviceResult = memberCollectionSellerService
            .getCollectionSellerByMemberid(member.getId(), pager);
        pager = serviceResult.getPager();

        List<FrontMemberCollectionSellerVO> sellerList = serviceResult.getResult();
        for (FrontMemberCollectionSellerVO frontMemberCollectionSellerVO : sellerList) {
            // 店铺首页信息
            ServiceResult<PcSellerIndex> indexResult = pcSellerIndexService
                .getPcSellerIndexBySellerId(frontMemberCollectionSellerVO.getSellerId());
            PcSellerIndex pcSellerIndex = indexResult.getResult();
            if (pcSellerIndex != null) {
                frontMemberCollectionSellerVO.setSellerLogo(pcSellerIndex.getLogo());
            }
        }

        dataMap.put("sellerList", sellerList);

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(),
            request.getRequestURI() + "");

        dataMap.put("page", pm);

        return "front/member/collection/collectionshop";
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
                                                                      @RequestParam(value = "sellerId", required = true) Integer sellerId) {

        Member member = WebFrontSession.getLoginedUser(request,response);

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
                                                                                         @RequestParam(value = "productId", required = true) Integer productId) throws Exception {
        Member member = WebFrontSession.getLoginedUser(request,response);

        ServiceResult<MemberCollectionProduct> serviceResult = memberCollectionProductService
            .cancelCollectionProduct(productId, member.getId());

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
        Member member = WebFrontSession.getLoginedUser(request,response);
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_memberId", member.getId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductLookLog>> serviceResult = analysisService
            .getProductLookLogs(feignUtil);
        pager = serviceResult.getPager();

        //分页对象
        PaginationUtil pm = new PaginationUtil(pager.getPageSize(),
            String.valueOf(pager.getPageIndex()), pager.getRowsCount(), request.getRequestURI());

        dataMap.put("lookLogList", serviceResult.getResult());
        dataMap.put("page", pm);

        return "front/member/collection/productlooklog";
    }
}
