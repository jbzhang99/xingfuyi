package com.yixiekeji.web.controller.index;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
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
   
   

    @RequestMapping(value = "/indexBanner.html", method = { RequestMethod.GET })
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

    @RequestMapping(value = "/indexFloor.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MIndexFloor>> indexAppRecommend(HttpServletRequest request,
                                                                             HttpServletResponse response) throws Exception {
        ServiceResult<List<MIndexFloor>> serviceResult = mIndexService
            .getMIndexFloorsWithData(false);

        HttpJsonResult<List<MIndexFloor>> jsonResult = new HttpJsonResult<List<MIndexFloor>>();
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        } else {
        	List<MIndexFloor> listMIndexFloor = serviceResult.getResult();
        	 Map<String, Integer> mapFull = new HashMap<String, Integer>();
             Map<String, Integer> mapCoupon = new HashMap<String, Integer>();
        	 for (MIndexFloor mIndexFloor : listMIndexFloor) {
                 List<MIndexFloorData> datas = mIndexFloor.getDatas();
                 for (MIndexFloorData mIndexFloorData : datas) {
                     if (mIndexFloorData.getDataType().intValue() == MIndexFloorData.DATA_TYPE_1) {
                         Product product = mIndexFloorData.getProduct();
                         actAllProduct(mapFull, mapCoupon, product);
                     }
                 }
             }
            jsonResult.setData(listMIndexFloor);
        }
        return jsonResult;
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
    
    @RequestMapping(value = "/indexRecommend.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MRecommend>> indexAppFloor(HttpServletRequest request,
                                                                        HttpServletResponse response) throws Exception {
        // 首页固定取3个
        PagerInfo pager = new PagerInfo(3, 1);
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
    
    
  
    
   
    
    /**
     * 取优惠券
     * */
    @RequestMapping(value ="/indexCoupon.html",method = {RequestMethod.GET})
    public @ResponseBody HttpJsonResult<Coupon> indexCoupon(HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	HttpJsonResult<Coupon> jsonResult = new HttpJsonResult<Coupon>();
    	
    	 //取自营的优惠券 sellerID = 1的是自营
        Coupon coupon = null;
        int sellerId = 1;
        ServiceResult<List<Product>> productResult1 = productFrontService
            .getProductListBySellerCateId(0, 1, 0, sellerId, 0);
//        if (!productResult1.getSuccess()) {
//    		jsonResult.setMessage(productResult1.getMessage());
//    	} else {
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
    	        	jsonResult.setData(coupon);
    	            return jsonResult;
    	        }
//    	}

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
        jsonResult.setData(coupon);
    	
    	return jsonResult;
    }


}
