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
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.pcindex.PcIndexBanner;
import com.yixiekeji.entity.pcindex.PcIndexFloor;
import com.yixiekeji.entity.pcindex.PcIndexImage;
import com.yixiekeji.entity.pcindex.PcRecommend;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.cart.ICartService;
import com.yixiekeji.service.pcindex.IPcIndexBannerService;
import com.yixiekeji.service.pcindex.IPcIndexFloorService;
import com.yixiekeji.service.pcindex.IPcIndexImageService;
import com.yixiekeji.service.pcindex.IPcRecommendService;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.vo.cart.CartInfoVO;
import com.yixiekeji.vo.product.FrontProductCateVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 首页controller
 * 首页初始化，以及一些公共的url
 * @Filename: IndexController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class IndexController extends BaseController {

    @Resource
    private IProductCateService         productCateService;
    @Resource
    private ICartService                cartService;
    @Resource
    private IPcIndexBannerService       pcIndexBannerService;
    @Resource
    private IPcIndexFloorService        pcIndexFloorService;
    @Resource
    private IPcRecommendService         pcRecommendService;

    @Resource
    private IProductFrontService        productFrontService;

    @Resource
    private IPcIndexImageService        pcIndexImageService;

    @Resource
    private ICouponService              couponService;
    @Resource
    private ISellerService              sellerService;
    @Resource
    private IActSingleService           actSingleService;
    @Resource
    private IActFullService             actFullService;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> dataMap) throws IOException {
        // 取得定时任务存入ServletContext中的首页缓存html字符串
        Object indexObj = request.getServletContext()
            .getAttribute(ConstantsEJS.PC_INDEX_HTML_CACHE);
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
            initIndex(dataMap, false);
            return "front/index/index";
        }

    }

    @RequestMapping(value = "index.html", method = { RequestMethod.GET })
    public String def(HttpServletRequest request, HttpServletResponse response,
                      Map<String, Object> dataMap) {
        return "redirect:/";
    }

    /**
     * 缓存时调用
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "cacheIndex.html", method = { RequestMethod.GET })
    public String cacheIndex(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> dataMap) {
        initIndex(dataMap, false);
        return "front/index/index";
    }

    @RequestMapping(value = "previewindex.html", method = { RequestMethod.GET })
    public String previewIndex(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> dataMap) {
        initIndex(dataMap, true);
        return "front/index/index";
    }

    /**
     * 首页初始化方法
     * @param dataMap
     * @param isPreview
     */
    private void initIndex(Map<String, Object> dataMap, boolean isPreview) {
        // 首页轮播图
        ServiceResult<List<PcIndexBanner>> bannerResult = pcIndexBannerService
            .getPcIndexBannerForView(isPreview);
        if (!bannerResult.getSuccess()) {
            log.error(bannerResult.getMessage());
        }
        dataMap.put("bannerList", bannerResult.getResult());

        // 首页固定取6个
        PagerInfo pager = new PagerInfo(4, 1);
        // 首页推荐
        ServiceResult<List<PcRecommend>> hotRecommendResult = pcRecommendService
            .getPcRecommendForView(PcRecommend.RECOMMEND_TYPE_1, isPreview, pager);
        if (!hotRecommendResult.getSuccess()) {
            log.error(hotRecommendResult.getMessage());
        }
        dataMap.put("hotList", hotRecommendResult.getResult());

        //首页楼层
        ServiceResult<List<PcIndexFloor>> floorResult = pcIndexFloorService
            .getPcIndexFloorForView(isPreview);
        if (!floorResult.getSuccess()) {
            log.error(floorResult.getMessage());
        }
        dataMap.put("floorList", floorResult.getResult());

        //首页头部图片去符合条件的第一个图片
        ServiceResult<List<PcIndexImage>> pcIndexImageResultTop = pcIndexImageService
            .getPcIndexImageForView(isPreview, PcIndexImage.TYPE_1);
        if (!pcIndexImageResultTop.getSuccess()) {
            log.error(pcIndexImageResultTop.getMessage());
        }
        List<PcIndexImage> pcIndexImageTops = pcIndexImageResultTop.getResult();
        if (pcIndexImageTops != null && pcIndexImageTops.size() > 0) {
            dataMap.put("pcIndexImageTop", pcIndexImageTops.get(0));
        }

        //首页头部轮播图上浮动图片符合条件的第一个图片
        ServiceResult<List<PcIndexImage>> pcIndexImageResultFloat = pcIndexImageService
            .getPcIndexImageForView(isPreview, PcIndexImage.TYPE_2);
        if (!pcIndexImageResultFloat.getSuccess()) {
            log.error(pcIndexImageResultFloat.getMessage());
        }
        List<PcIndexImage> pcIndexImageFloats = pcIndexImageResultFloat.getResult();
        if (pcIndexImageFloats != null && pcIndexImageFloats.size() > 0) {
            dataMap.put("pcIndexImageFloat", pcIndexImageFloats.get(0));
        }
        if (pcIndexImageFloats != null && pcIndexImageFloats.size() > 1) {
            dataMap.put("pcIndexImageFloat2", pcIndexImageFloats.get(1));
        }

        //首页头部轮播图下面图片
        ServiceResult<List<PcIndexImage>> pcIndexImageResultDown = pcIndexImageService
            .getPcIndexImageForView(isPreview, PcIndexImage.TYPE_3);
        if (!pcIndexImageResultDown.getSuccess()) {
            log.error(pcIndexImageResultDown.getMessage());
        }
        List<PcIndexImage> pcIndexImageDowns = pcIndexImageResultDown.getResult();
        if (pcIndexImageDowns != null && pcIndexImageDowns.size() > 0) {
            dataMap.put("pcIndexImageDowns", pcIndexImageDowns);
        }

        // 分类
        Map<String, Object> queryMap = new HashMap<String, Object>();
        ServiceResult<List<FrontProductCateVO>> serviceResult = productCateService
            .getProductCateList(queryMap);

        dataMap.put("cateList", serviceResult.getResult());

        //取优惠券信息
        initIndexCoupon(dataMap, isPreview);

        //取得竞价列表
       

        //取销量前30的商品
        ServiceResult<List<Product>> serviceResultProduct = productFrontService
            .getProductMemberByTop(30);
        List<Product> products = serviceResultProduct.getResult();
        Map<String, Integer> mapFull = new HashMap<String, Integer>();
        Map<String, Integer> mapCoupon = new HashMap<String, Integer>();
        for (Product product : products) {
           
                product.setSpecial(0);
            

            //判断单品立减
            ServiceResult<ActSingle> singleResult = actSingleService.getEffectiveActSingle(
                product.getSellerId(), ConstantsEJS.CHANNEL_2, product.getId());
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
        dataMap.put("products", products);
    }

    /**
     * 取优惠券信息
     * @param dataMap
     * @param isPreview
     */
    private void initIndexCoupon(Map<String, Object> dataMap, boolean isPreview) {
        PagerInfo pager = new PagerInfo(20, 1);

        List<Coupon> coupons = new ArrayList<Coupon>();
        Map<String, String> queryMap = new HashMap<String, String>();
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<Seller>> sellerResult = sellerService.getSellers(feignUtil);
        List<Seller> sellers = sellerResult.getResult();
        //如果为空 初始化该集合
        if(sellers==null)
           sellers= new ArrayList<Seller>();
        
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
                    Coupon coupon = couponList.get(0);
                    coupon.setProductImage(product.getMasterImg());
                    coupon.setProductId(product.getId());
                    coupon.setSellerName(seller.getSellerName());
                    coupons.add(coupon);
                }
            }

            if (coupons.size() == 3) {
                break;
            }

        }
        dataMap.put("couponList", coupons);
    }

    /**
     * 导航所有商品分类 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/cateList.html", method = { RequestMethod.GET })
    public String getProductCateList(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> dataMap) {

        Map<String, Object> queryMap = new HashMap<String, Object>();
        ServiceResult<List<FrontProductCateVO>> serviceResult = new ServiceResult<List<FrontProductCateVO>>();
        serviceResult = productCateService.getProductCateList(queryMap);

        dataMap.put("cateList", serviceResult.getResult());

        return "front/commons/cateList";
    }

    /**
     * 右上角 我的购物车
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/previewMyCart.html", method = { RequestMethod.GET })
    public String previewMyCart(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap) {
        Member member = WebFrontSession.getLoginedUser(request, response);
        //取购物车信息  产品价格 按照商家来区分
        //查询购物车
        if (member != null) {
            ServiceResult<CartInfoVO> serviceResult = cartService.getCartByMId(member.getId(),
                ConstantsEJS.SOURCE_1_PC, 1);
            dataMap.put("cartInfoVO", serviceResult.getResult());
        }
        return "front/cart/previewcart";
    }


}
