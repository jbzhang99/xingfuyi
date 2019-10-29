package com.yixiekeji.web.controller.seller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.entity.pcseller.PcSellerIndex;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.service.pcseller.IPcSellerIndexBannerService;
import com.yixiekeji.service.pcseller.IPcSellerIndexService;
import com.yixiekeji.service.pcseller.IPcSellerRecommendService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.ISellerCateService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 商家商品列表
 *                       
 * @Filename: SellerCateController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class SellerCateController {

    @Resource
    private ISellerService                 sellerService;
    @Resource
    private ISellerCateService             sellerCateService;
    @Resource
    private IPcSellerIndexService          pcSellerIndexService;
    @Resource
    private IPcSellerIndexBannerService    pcSellerIndexBannerService;
    @Resource
    private IPcSellerRecommendService      pcSellerRecommendService;
    @Resource
    private IMemberCollectionSellerService memberCollectionSellerService;

    @Resource
    private IProductFrontService           productFrontService;

    private final static int               PAGE_NUMBER = 20;

    @RequestMapping(value = "/store/cate-{cateId}.html", method = { RequestMethod.GET })
    public String toCateIndex(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> dataMap, @PathVariable int cateId, int sellerId) {
        // 查询商户基本信息
        ServiceResult<Seller> sellerResult = sellerService.getSellerById(sellerId);
        if (!sellerResult.getSuccess()) {
            throw new BusinessException(sellerResult.getMessage());
        }

        Seller seller = sellerResult.getResult();
        // 店铺不存在，或者店铺状态不是审核通过状态
        if (seller == null || seller.getAuditStatus().intValue() != Seller.AUDIT_STATE_2_DONE) {
            return "redirect:/error500.html";
        }
        dataMap.put("sellerInfo", seller);

        // 计算平均分数
        BigDecimal sum = new BigDecimal(seller.getScoreService());
        sum = sum.add(new BigDecimal(seller.getScoreDeliverGoods()));
        sum = sum.add(new BigDecimal(seller.getScoreDescription()));
        BigDecimal avg = sum.divide(new BigDecimal(3), 1, BigDecimal.ROUND_HALF_UP);
        dataMap.put("sellerScoreAvg", avg);

        // 店铺首页信息
        ServiceResult<PcSellerIndex> indexResult = pcSellerIndexService
            .getPcSellerIndexBySellerId(sellerId);
        if (!indexResult.getSuccess()) {
            throw new BusinessException(indexResult.getMessage());
        }
        dataMap.put("sellerIndexInfo", indexResult.getResult());

        // 店铺分类
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService
            .getOnuseSellerCate(sellerId);
        if (!serviceResult.getSuccess()) {
            throw new BusinessException(serviceResult.getMessage());
        }
        List<SellerCate> sellerCates = serviceResult.getResult();
        dataMap.put("cateList", sellerCates);
        if (cateId == 0) { //cateId 为 0 表示查看店铺所有商品
            dataMap.put("sellerCateName", "所有商品");
        } else {
            //判断选取的哪个分类
            ServiceResult<SellerCate> resultCate = sellerCateService.getSellerCateById(cateId);
            if (!serviceResult.getSuccess()) {
                throw new BusinessException(serviceResult.getMessage());
            }
            dataMap.put("sellerCateName", resultCate.getResult().getName());
        }

        // 用户是否收藏该店铺
        String collected = "false";
        Member loginedUser = WebFrontSession.getLoginedUser(request,response);
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

        //查询商家的商品
        int start = 0, size = 0;
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap, PAGE_NUMBER);
        start = pager.getStart();
        size = pager.getPageSize();

        String sortStr = request.getParameter("sort");
        int sort = ConvertUtil.toInt(sortStr, 0); //分类
        ServiceResult<List<Product>> serviceResultProduct = productFrontService
            .getProductListBySellerCateId(start, size, cateId, sellerId, sort);
        List<Product> products = serviceResultProduct.getResult();

        ServiceResult<Integer> serviceResultProductCount = productFrontService
            .getProductListBySellerCateIdCount(cateId, sellerId);
        int count = serviceResultProductCount.getResult().intValue();

        String url = request.getRequestURI();
        if (sellerId != 0) {
            url = url + "?sellerId=" + sellerId + "&sort=" + sort;
        }

        PaginationUtil pm = new PaginationUtil(PAGE_NUMBER, String.valueOf(pager.getPageIndex()),
            count, url);

        dataMap.put("sort", sort);
        dataMap.put("page", pm);
        dataMap.put("count", count);
        dataMap.put("products", products);

        return "front/seller/sellercateindex";
    }
}
