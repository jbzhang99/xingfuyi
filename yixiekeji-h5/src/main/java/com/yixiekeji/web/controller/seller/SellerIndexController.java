package com.yixiekeji.web.controller.seller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.entity.mseller.MSellerIndexBanner;
import com.yixiekeji.entity.mseller.MSellerIndexFloor;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.mseller.IMSellerIndexService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 店铺首页controller
 * 
 * @Filename: SellerIndexController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class SellerIndexController extends BaseController {

    @Resource
    private ISellerService                 sellerService;

    @Resource
    private IMSellerIndexService           mSellerIndexService;

    @Resource
    private IMemberCollectionSellerService memberCollectionSellerService;

    /**
     * 跳转到店铺首页
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/store/{sellerId}.html", method = { RequestMethod.GET })
    public String toStoresIndex(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap, @PathVariable Integer sellerId) {
        return initIndex(request, response, dataMap, sellerId, false);
    }

    /**
     * 跳转到店铺首页(店铺预览调用)
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/previewstore/{sellerId}.html", method = { RequestMethod.GET })
    public String previewStoresIndex(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> dataMap, @PathVariable Integer sellerId) {
        return initIndex(request, response, dataMap, sellerId, true);
    }

    /**
     * 店铺首页初始化
     * @param request
     * @param dataMap
     * @param sellerId
     * @param isPreview
     * @return
     */
    private String initIndex(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> dataMap, Integer sellerId, boolean isPreview) {
        // 查询商户基本信息
        ServiceResult<Seller> sellerResult = sellerService.getSellerById(sellerId);
        if (!sellerResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sellerResult.getCode())) {
                throw new RuntimeException(sellerResult.getMessage());
            } else {
                throw new BusinessException(sellerResult.getMessage());
            }
        }

        Seller seller = sellerResult.getResult();
        // 店铺不存在，或者店铺状态不是审核通过状态
        if (seller == null || seller.getAuditStatus().intValue() != Seller.AUDIT_STATE_2_DONE) {
            return "redirect:/error500.html";
        }
        dataMap.put("seller", seller);

        ServiceResult<List<MSellerIndexBanner>> bannerResult = mSellerIndexService
            .getMSellerIndexBannerForView(sellerId, isPreview);
        dataMap.put("banners", bannerResult.getResult());

        ServiceResult<List<MSellerIndexFloor>> floorResult = mSellerIndexService
            .getMSellerIndexFloorsWithData(sellerId, isPreview);
        dataMap.put("floors", floorResult.getResult());

        String collected = "false";
        Member loginedUser = WebFrontSession.getLoginedUser(request);
        if (loginedUser != null && loginedUser.getId() > 0) {
            ServiceResult<List<MemberCollectionSeller>> collectionRlt = memberCollectionSellerService
                .getBySellerIdAndMId(sellerId, loginedUser.getId());
            if (collectionRlt.getResult() != null) {
                for (MemberCollectionSeller collectionSeller : collectionRlt.getResult()) {
                    if (collectionSeller.getState() != MemberCollectionSeller.STATE_2) {
                        collected = "true";
                        break;
                    }
                }
            }
        }
        dataMap.put("collected", collected);

        return "h5/seller/sellerindex";
    }
}
