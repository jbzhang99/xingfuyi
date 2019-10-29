package com.yixiekeji.web.controller.index;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.mindex.MIndexBanner;
import com.yixiekeji.entity.mindex.MIndexFloor;
import com.yixiekeji.entity.mindex.MIndexFloorData;
import com.yixiekeji.entity.mindex.MRecommend;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.mindex.IMIndexService;
import com.yixiekeji.service.mindex.IMRecommendService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 首页controller
 * 
 * @Filename: MIndexController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class MIndexController extends BaseController {

    @Resource
    private IMIndexService              mIndexService;
    
    @Resource
    private IProductFrontService        productFrontService;
    @Resource
    private IMRecommendService          mRecommendService;

    @Resource
    private ICouponService              couponService;
    @Resource
    private ISellerService              sellerService;
   
    @Resource
    private IActSingleService           actSingleService;
    @Resource
    private IActFullService             actFullService;
  

    /**
     * 首页
     * @param request
     * @param response
     * @param stack
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public String indexRedirect(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> stack) throws IOException {

        // 取得定时任务存入ServletContext中的首页缓存html字符串
        Object indexObj = request.getServletContext().getAttribute(ConstantsEJS.M_INDEX_HTML_CACHE);
        if (indexObj != null && indexObj.toString().length() > 0) {
            log.info("-------------缓存取得首页html");
            // 如果对象不为空，直接打印内容
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println(indexObj.toString());
            return null;
        } else {
            log.error("-------------直接打开页面");
            // 如果对象为空，说明ServletContext中还未缓存，则直接取数据库数据返回页面打开首页
            initIndex(stack, true);
            return "h5/index/index";
        }

    }

    /**
     * 首页
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/index.html", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> stack) {
        return "redirect:/";
    }

    /**
     * 首页缓存调用
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/cacheIndex.html", method = { RequestMethod.GET })
    public String cacheIndex(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> stack) {
        initIndex(stack, false);
        return "h5/index/index";
    }

    /**
     * 首页预览
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/previewindex.html", method = { RequestMethod.GET })
    public String previewIndex(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> stack) {
        initIndex(stack, true);
        return "h5/index/index";
    }

    /**
     * 首页初始化方法
     * @param dataMap
     * @param isPreview
     */
    private void initIndex(Map<String, Object> stack, boolean isPreview) {
        ServiceResult<List<MIndexBanner>> bannerResult = mIndexService
            .getMIndexBannerForView(isPreview);
        stack.put("banners", bannerResult.getResult());

        Map<String, Integer> mapFull = new HashMap<String, Integer>();
        Map<String, Integer> mapCoupon = new HashMap<String, Integer>();

        ServiceResult<List<MIndexFloor>> floorResult = mIndexService
            .getMIndexFloorsWithData(isPreview);
        List<MIndexFloor> listMIndexFloor = floorResult.getResult();
        //如果为空 初始化该集合
        if(listMIndexFloor==null)
           listMIndexFloor= new ArrayList<MIndexFloor>();
                
        for (MIndexFloor mIndexFloor : listMIndexFloor) {
            List<MIndexFloorData> datas = mIndexFloor.getDatas();
            for (MIndexFloorData mIndexFloorData : datas) {
                if (mIndexFloorData.getDataType().intValue() == MIndexFloorData.DATA_TYPE_1) {
                    Product product = mIndexFloorData.getProduct();
                    actAllProduct(mapFull, mapCoupon, product);
                }
            }
        }
        stack.put("floors", floorResult.getResult());

        // 首页固定取3个
        PagerInfo pager = new PagerInfo(3, 1);
        // 首页推荐
        ServiceResult<List<MRecommend>> hotRecommendResult = mRecommendService
            .getMRecommendForView(MRecommend.RECOMMEND_TYPE_1, isPreview, pager);
        if (!hotRecommendResult.getSuccess()) {
            log.error(hotRecommendResult.getMessage());
        }
        stack.put("hotList", hotRecommendResult.getResult());

       

        //取优惠券信息
        Coupon coupon = initIndexCoupon(stack, isPreview);
        stack.put("coupon", coupon);

        

       

        //取销量前10的商品
        ServiceResult<List<Product>> serviceResultProduct = productFrontService
            .getProductMemberByTop(16);
        List<Product> products = serviceResultProduct.getResult();
        for (Product product : products) {
            actAllProduct(mapFull, mapCoupon, product);
        }
        stack.put("products", products);
    }

    /**
     * 商品活动的处理
     * @param mapFull
     * @param mapCoupon
     * @param product
     */
    private void actAllProduct(Map<String, Integer> mapFull, Map<String, Integer> mapCoupon,
                               Product product) {
     
            product.setSpecial(0);
        

        //判断单品立减
        ServiceResult<ActSingle> singleResult = actSingleService
            .getEffectiveActSingle(product.getSellerId(), ConstantsEJS.CHANNEL_2, product.getId());
        ActSingle actSingle = singleResult.getResult();
        if (actSingle != null) {
            product.setSingle(1);
        } else {
            product.setSingle(0);
        }

        //判断订单满减
        Integer sellerId = mapFull.get(product.getSellerId().toString());
        if (sellerId != null) {
            product.setFull(1);
        } else {
            ServiceResult<ActFull> fullResult = actFullService
                .getEffectiveActFull(product.getSellerId(), ConstantsEJS.CHANNEL_2);
            ActFull actFull = fullResult.getResult();
            if (actFull != null) {
                product.setFull(1);
                mapFull.put(product.getSellerId().toString(), product.getSellerId());
            } else {
                product.setFull(0);
            }
        }

        //判断优惠券
        Integer sellerIdCoupon = mapCoupon.get(product.getSellerId().toString());
        if (sellerIdCoupon != null) {
            product.setCoupon(1);
        } else {
            ServiceResult<List<Coupon>> couponResult = couponService
                .getEffectiveCoupon(product.getSellerId(), ConstantsEJS.CHANNEL_2);
            List<Coupon> listCoupon = couponResult.getResult();
            if (listCoupon != null && listCoupon.size() > 0) {
                product.setCoupon(1);
                mapCoupon.put(product.getSellerId().toString(), product.getSellerId());
            } else {
                product.setCoupon(0);
            }
        }
    }

    /**
     * 取优惠券信息，取第一个展示到首页
     * @param dataMap
     * @param isPreview
     */
    private Coupon initIndexCoupon(Map<String, Object> dataMap, boolean isPreview) {
        //取自营的优惠券 sellerID = 1的是自营
        Coupon coupon = null;
        int sellerId = 1;
        ServiceResult<List<Product>> productResult1 = productFrontService
            .getProductListBySellerCateId(0, 1, 0, sellerId, 0);
        List<Product> products1 = productResult1.getResult();
        if (products1.size() > 0) {
            Product product = productResult1.getResult().get(0);
            ServiceResult<List<Coupon>> couponResult = couponService.getEffectiveCoupon(sellerId,
                ConstantsEJS.CHANNEL_2);
            List<Coupon> couponList = couponResult.getResult();
            if (couponList.size() > 0) {
                coupon = couponList.get(0);
                coupon.setProductImage(product.getMasterImg());
                coupon.setProductId(product.getId());
            }
        }
        if (coupon != null) {
            return coupon;
        }

        PagerInfo pager = new PagerInfo(20, 1);
        List<Coupon> coupons = new ArrayList<Coupon>();
        Map<String, String> queryMap = new HashMap<String, String>();
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<Seller>> sellerResult = sellerService.getSellers(feignUtil);
        List<Seller> sellers = sellerResult.getResult();
        for (Seller seller : sellers) {
            ServiceResult<List<Product>> productResult = productFrontService
                .getProductListBySellerCateId(0, 1, 0, seller.getId(), 0);
            List<Product> products = productResult.getResult();
            if (products.size() > 0) {
                Product product = productResult.getResult().get(0);
                ServiceResult<List<Coupon>> couponResult = couponService
                    .getEffectiveCoupon(seller.getId(), ConstantsEJS.CHANNEL_2);
                List<Coupon> couponList = couponResult.getResult();
                if (couponList.size() > 0) {
                    coupon = couponList.get(0);
                    coupon.setProductImage(product.getMasterImg());
                    coupon.setProductId(product.getId());
                    coupon.setSellerName(seller.getSellerName());
                    coupons.add(coupon);
                }
            }

            if (coupons.size() == 1) {
                break;
            }
        }
        return coupon;
    }

 

}
