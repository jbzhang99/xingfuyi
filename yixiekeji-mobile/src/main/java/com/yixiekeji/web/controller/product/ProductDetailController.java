package com.yixiekeji.web.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.entity.product.ProductComments;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.cart.ICartService;
import com.yixiekeji.service.member.IMemberAddressService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.product.IFrontProductPictureService;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.service.product.IProductAttrService;
import com.yixiekeji.service.product.IProductCommentsService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.util.FrontProductPictureUtil;
import com.yixiekeji.vo.member.FrontMemberProductBehaveStatisticsVO;
import com.yixiekeji.vo.product.ProductActVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 单品页controller
 * 
 * @Filename: ProductDetailController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class ProductDetailController extends BaseController {

    @Resource
    private IMemberService              memberService;
    @Resource
    private IProductFrontService        productFrontService;
    @Resource
    private ISellerService              sellerService;
    @Resource
    private IProductGoodsService        productGoodsService;

    @Resource
    private IProductAttrService         productAttrService;
    @Resource
    private IProductCommentsService     productCommentsService;
    @Resource
    private IProductAskService          productAskService;
    @Resource
    private ICartService                cartService;
    @Resource
    private IActSingleService           actSingleService;
    @Resource
    private IActFullService             actFullService;
    @Resource
    private ICouponService              couponService;
    @Resource
    private IRegionsService             regionsService;
    @Resource
    private IMemberAddressService       memberAddressService;
    @Resource
    private ISellerTransportService     sellerTransportService;

    @Resource
    private DomainUrlUtil               domainUrlUtil;
    @Resource
    private IFrontProductPictureService frontProductPictureService;

    /**
     * 跳转到单品页 
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/{productId}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> productDetail(HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           Map<String, Object> dataMap,
                                                                           @PathVariable Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        dataMap.put("productId", productId);
        Integer goodId = ConvertUtil.toInt(request.getParameter("goodId"), 0);

        Member member = WebFrontSession.getLoginedUser(request);

        //获得商品信息
        ServiceResult<Product> productResult = productFrontService.getProductById(productId);
        if (!productResult.getSuccess()) {
            throw new BusinessException("获得商品信息失败！");
        }
        if (productResult.getResult() == null) {
            throw new BusinessException("获得商品信息为空！");
        }
        Product product = productResult.getResult();
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription().replace(
                "${(domainUrlUtil.EJS_IMAGE_RESOURCES)!}", domainUrlUtil.getImageResources()));
        }
        dataMap.put("product", product);

        // 取是否预览标志，1表示是预览
        String preview = request.getParameter("preview");
        if (!"1".equals(preview)) {
            // 如果不是预览，则根据属性修改商品的状态
            // 上架时间在现在之后
            if (product.getUpTime().after(new Date())) {
                product.setState(Product.STATE_7);
            }
            // 店铺被冻结
            if (product.getSellerState().intValue() != Product.SELLER_STATE_1) {
                product.setState(Product.STATE_7);
            }
        } else {
            product.setState(Product.STATE_6);
        }

        //获得产品对应的大图
        ServiceResult<List<ProductPicture>> result = frontProductPictureService
            .getByProductIds(productId);
        List<ProductPicture> listPicture = result.getResult();
        List<String> productLeadPicList = FrontProductPictureUtil.getByProductIds(listPicture);
        dataMap.put("productLeadPicList", productLeadPicList);

        //获得用户评价数及占比
        ServiceResult<FrontMemberProductBehaveStatisticsVO> serviceResult = new ServiceResult<FrontMemberProductBehaveStatisticsVO>();
        Integer memberId = 0;
        if (member != null) {
            memberId = member.getId();
        }
        serviceResult = memberService.getBehaveStatisticsByProductId(productId, memberId);
        dataMap.put("statisticsVO", serviceResult.getResult());

        //获得货品信息,默认取第一个 包含某规格商品的库存及价格信息 
        ServiceResult<List<ProductGoods>> goodsServiceResult = productGoodsService
            .getGoodSByProductId(productId);
        List<ProductGoods> goods = goodsServiceResult.getResult();

        if (goods == null || goods.size() == 0) {
            jsonResult.setMessage("货品信息为空。");
            return jsonResult;
        }

        //运费相关
        if (member != null) {
            //获取用户地址信息
            ServiceResult<List<MemberAddress>> memberAddressListResult = memberAddressService
                .getMemberAddressByMId(member.getId());
            MemberAddress memberAddressNew = null;
            if (memberAddressListResult.getSuccess() && memberAddressListResult.getResult() != null
                && memberAddressListResult.getResult().size() > 0) {
                for (MemberAddress memberAddress : memberAddressListResult.getResult()) {
                    if (memberAddress.getState().intValue() == MemberAddress.STATE_1) {
                        memberAddressNew = memberAddress;
                    }
                }
                if (memberAddressNew == null) {
                    memberAddressNew = memberAddressListResult.getResult().get(0);
                }
            }
            if (memberAddressNew != null) {
                dataMap.put("address", memberAddressNew);
                ServiceResult<Regions> province = regionsService
                    .getRegionsById(memberAddressNew.getProvinceId());
                dataMap.put("province", province.getResult());

                ServiceResult<Regions> city = regionsService
                    .getRegionsById(memberAddressNew.getCityId());
                dataMap.put("city", city.getResult());

                ServiceResult<Regions> area = regionsService
                    .getRegionsById(memberAddressNew.getAreaId());
                dataMap.put("area", area.getResult());
                dataMap.put("areaId", memberAddressNew.getAreaId());

                ServiceResult<List<Regions>> cityResult = regionsService
                    .getRegionsByParentId(memberAddressNew.getProvinceId());
                dataMap.put("cityList", cityResult.getResult());

                ServiceResult<List<Regions>> areaResult = regionsService
                    .getRegionsByParentId(memberAddressNew.getCityId());
                dataMap.put("areaList", areaResult.getResult());

                ServiceResult<BigDecimal> freeResult = sellerTransportService.getTransFee(productId,
                    memberAddressNew.getCityId(), 1);
                dataMap.put("free", freeResult.getResult());
            }
        }

        //初始化加载省市区集合
        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        dataMap.put("provinceList", provinceResult.getResult());

        //获取商品物流信息
        if (product.getTransportId() != null && product.getTransportId().intValue() != 0
            && product.getTransportId().intValue() != -1) {
            ServiceResult<SellerTransport> sellerTransportResult = sellerTransportService
                .getSellerTransportById(product.getTransportId());
            if (sellerTransportResult.getSuccess() && sellerTransportResult.getResult() != null) {
                dataMap.put("transport", sellerTransportResult.getResult());
            }
        }

        // 组装商品规格信息
        List<ProductNorm> normList = new ArrayList<ProductNorm>();
        Map<String, ProductNorm> normMap = new HashMap<String, ProductNorm>();
        Map<String, ProductNormAttr> attrMap = new HashMap<String, ProductNormAttr>();
        // 记录有效的规格组合
        List<String> effectAttr = new ArrayList<>();
        boolean def = false;
        boolean defWithId = false;
        for (ProductGoods good : goods) {
            String normName = good.getNormName(); // 如：颜色,红色;内存,4G
            String normAttrId = good.getNormAttrId(); // 如：1,17

            if (StringUtil.isEmpty(normName, true)) {
                // 规则属性为空则表示该商品没有启用规格（只有一个货品）
                // 设置默认货品，只有一个货品时设定该货品
                dataMap.put("goods", good);
                continue;
            }

            if (good.getState() == null || good.getState().intValue() == ProductGoods.DISABLE) {
                // 货品没有启用则规格不参与组装
                continue;
            }

            // 获取默认显示的货品，不管id为goodId的货品存在与否、启用与否，必须设置一个默认的货品
            if (!defWithId) {
                if (good.getId().equals(goodId)) {
                    defWithId = true;
                    def = true;
                    // 如果有指定ID的货品，设定指定ID的货品为默认货品
                    dataMap.put("goods", good);
                } else if (!def) {
                    // 设置默认货品，有多个货品时，第一个有效货品为默认货品
                    dataMap.put("goods", good);
                    def = true;
                }
            }

            effectAttr.add(normAttrId);

            String[] normNameSplit = normName.split(";");
            String[] normAttrIdSplit = normAttrId.split(",");

            if (normNameSplit.length > 0) {
                // 循环
                for (int i = 0; i < normNameSplit.length; i++) {
                    String name = normNameSplit[i];

                    // 得到类似：颜色,红色的值，颜色为规格名称，红色为规格的值
                    String[] cellName = name.split(",");

                    if (normMap.get(cellName[0]) == null) {
                        // 如果规格map中没有当前规格，则添加规格和对应的规格值
                        ProductNorm norm = new ProductNorm();
                        norm.setName(cellName[0]);
                        // 保存规则名称
                        normList.add(norm);
                        normMap.put(cellName[0], norm);
                        // 保存规则值
                        List<ProductNormAttr> attrList = new ArrayList<ProductNormAttr>();
                        ProductNormAttr attr = new ProductNormAttr();
                        attr.setId(ConvertUtil.toInt(normAttrIdSplit[i], 0));
                        attr.setName(cellName[1]);
                        attrList.add(attr);
                        norm.setAttrList(attrList);

                        // 记录到map中防止重复添加
                        attrMap.put(cellName[1], attr);
                    } else {
                        // 如果规格map中有当前规格，则不添加规格，对应的规格值再判断是否已经存在决定是否添加
                        ProductNorm norm = normMap.get(cellName[0]);

                        // 判断是否已有规则值，如果没有则添加，有则不再添加
                        if (attrMap.get(cellName[1]) == null) {
                            // 规则值
                            List<ProductNormAttr> attrList = norm.getAttrList();
                            ProductNormAttr attr = new ProductNormAttr();
                            attr.setId(ConvertUtil.toInt(normAttrIdSplit[i], 0));
                            attr.setName(cellName[1]);
                            attrList.add(attr);
                            norm.setAttrList(attrList);
                            // 记录到map中防止重复添加
                            attrMap.put(cellName[1], attr);
                        }
                    }
                }
            }
        }

        // 有效货品包含的规格
        dataMap.put("norms", normList);
        // 规格数量
        dataMap.put("normsNum", normList.size());
        // 有效的规格组合
        dataMap.put("effectAttr", new Gson().toJson(effectAttr));

        // 获得商家信息、商家详细信息
        ServiceResult<Seller> sellerServiceResult = sellerService
            .getSellerById(productResult.getResult().getSellerId());
        if (sellerServiceResult.getSuccess() && sellerServiceResult.getResult() != null) {
            // 商家基本信息
            Seller seller = sellerServiceResult.getResult();
            dataMap.put("seller", seller);
        }

        // 购物车数量
        if (member != null && member.getId() != null) {
            ServiceResult<Integer> cartResult = cartService.getCartNumberByMId(member.getId());
            dataMap.put("cartNumber", cartResult.getResult());
        }

        // 获取类型参数：为1时表示打开限时抢购页面
      
            jsonResult.setData(dataMap);
            // 类型参数为空则打开普通单品页
            jsonResult.setBackUrl("productdetail");
            return jsonResult;
       

    }

    /**
     * 跳转到商品详细介绍页 
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/info/{productId}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> productDetailInfo(HttpServletRequest request,
                                                                               HttpServletResponse response,
                                                                               Map<String, Object> dataMap,
                                                                               @PathVariable Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        dataMap.put("productId", productId);
        //获得商品信息
        ServiceResult<Product> productResult = productFrontService.getProductById(productId);
        if (!productResult.getSuccess()) {
            throw new BusinessException("获得商品信息失败！");
        }
        if (productResult.getResult() == null) {
            throw new BusinessException("获得商品信息为空！");
        }
        Product product = productResult.getResult();
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription().replace(
                "${(domainUrlUtil.EJS_IMAGE_RESOURCES)!}", domainUrlUtil.getImageResources()));
        }
        dataMap.put("product", product);
        // 跳转类型（type=1跳转到秒杀单品页）
        String type = request.getParameter("type");
        dataMap.put("type", type);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 跳转到单品规格页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/spec/{productId}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> productDetailSpec(HttpServletRequest request,
                                                                               HttpServletResponse response,
                                                                               Map<String, Object> dataMap,
                                                                               @PathVariable Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        dataMap.put("productId", productId);

        //获得商品信息
        ServiceResult<Product> productResult = productFrontService.getProductById(productId);
        if (!productResult.getSuccess()) {
            throw new BusinessException("获得商品信息失败！");
        }
        if (productResult.getResult() == null) {
            throw new BusinessException("获得商品信息为空！");
        }
        Product product = productResult.getResult();
        dataMap.put("product", product);

        //取得属性值
        ServiceResult<List<ProductAttr>> attrResult = productAttrService
            .getProductAttrByProductId(productId);
        if (!attrResult.getSuccess()) {
            throw new BusinessException("获得商品属性信息失败！");
        }
        if (attrResult.getResult() == null) {
            throw new BusinessException("获得商品属性信息为空！");
        }
        dataMap.put("productAttr", attrResult.getResult());

        // 获得商家信息、商家详细信息及 商家qq信息  以及活动信息 
        ServiceResult<Seller> sellerServiceResult = sellerService
            .getSellerById(productResult.getResult().getSellerId());
        if (sellerServiceResult.getSuccess() && sellerServiceResult.getResult() != null) {
            // 商家基本信息
            Seller seller = sellerServiceResult.getResult();
            dataMap.put("seller", seller);
        }

        // 跳转类型（type=1跳转到秒杀单品页）
        String type = request.getParameter("type");
        dataMap.put("type", type);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 跳转到单品评论页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/comment/{productId}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> productDetailComment(HttpServletRequest request,
                                                                                  HttpServletResponse response,
                                                                                  Map<String, Object> dataMap,
                                                                                  @PathVariable Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        dataMap.put("productId", productId);

        Member member = WebFrontSession.getLoginedUser(request);

        //获得用户评价数及占比
        ServiceResult<FrontMemberProductBehaveStatisticsVO> serviceResult = new ServiceResult<FrontMemberProductBehaveStatisticsVO>();
        Integer memberId = 0;
        if (member != null) {
            memberId = member.getId();
        }
        serviceResult = memberService.getBehaveStatisticsByProductId(productId, memberId);
        dataMap.put("statisticsVO", serviceResult.getResult());

        // 好评 grade = 4或5，中评：3，差评：1或2
        // 全部评论
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        //查找所有的评价和咨询   状态为审核通过
        Map<String, String> queryMap = new HashMap<String, String>();

        //查找某个产品的 状态为前台显示的
        queryMap.put("q_productId", productId + "");
        queryMap.put("q_state", String.valueOf(ProductComments.STATE_2));
        queryMap.put("q_grades", "all");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> allCmtResult = productCommentsService
            .getProductComments(feignUtil);
        pager = allCmtResult.getPager();
        // 全部评论数量和数据
        dataMap.put("allNumber", pager.getRowsCount());
        dataMap.put("allCommentList", allCmtResult.getResult());

        // 好评
        pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        queryMap.put("q_grades", "high");
        FeignUtil feignUtil2 = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> highCmtResult = productCommentsService
            .getProductComments(feignUtil2);
        pager = highCmtResult.getPager();
        // 数量和数据
        dataMap.put("highNumber", pager.getRowsCount());
        dataMap.put("highCommentList", highCmtResult.getResult());

        // 中评
        pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        queryMap.put("q_grades", "middle");
        FeignUtil feignUtil3 = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> middleCmtResult = productCommentsService
            .getProductComments(feignUtil3);
        pager = middleCmtResult.getPager();
        // 数量和数据
        dataMap.put("middleNumber", pager.getRowsCount());
        dataMap.put("middleCommentList", middleCmtResult.getResult());

        // 差评
        pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, 1);
        queryMap.put("q_grades", "low");
        FeignUtil feignUtil4 = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> lowCmtResult = productCommentsService
            .getProductComments(feignUtil4);
        pager = lowCmtResult.getPager();
        // 数量和数据
        dataMap.put("lowNumber", pager.getRowsCount());
        dataMap.put("lowCommentList", lowCmtResult.getResult());
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE_10);

        // 跳转类型（type=1跳转到秒杀单品页）
        String type = request.getParameter("type");
        dataMap.put("type", type);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 单品页 评价列表，异步调用加载更多评论
     * @param request
     * @param response
     * @param dataMap
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/morecomment.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> commentsListForProcutPage(HttpServletRequest request,
                                                                                       HttpServletResponse response,
                                                                                       Map<String, Object> dataMap) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        // 商品ID
        String productIdStr = request.getParameter("productId");

        String type = request.getParameter("type");

        Integer pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);

        //查找所有的评价和咨询   状态为审核通过
        Map<String, String> queryMap = new HashMap<String, String>();
        //查找某个产品的 状态为前台显示的
        queryMap.put("q_productId", productIdStr);
        queryMap.put("q_state", String.valueOf(ProductComments.STATE_2));
        //好评  grade = 4或5 ,中评：3，差评：1或2
        queryMap.put("q_grades", type);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductComments>> serviceResult = productCommentsService
            .getProductComments(feignUtil);

        dataMap.put("commentList", serviceResult.getResult());
        dataMap.put("type", type);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 跳转到单品咨询页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/ask/{productId}.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> productDetailAsk(HttpServletRequest request,
                                                                              HttpServletResponse response,
                                                                              Map<String, Object> dataMap,
                                                                              @PathVariable Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        dataMap.put("productId", productId);

        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE_10, pageIndex);

        Map<String, String> queryMap = new HashMap<String, String>();
        //查找某个产品的 状态为前台显示的
        queryMap.put("q_productId", productId + "");
        queryMap.put("q_state", String.valueOf(ProductAsk.STATE_3));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);
        ServiceResult<List<ProductAsk>> serviceResult = productAskService.getProductAsks(feignUtil);
        pager = serviceResult.getPager();

        // 数量和数据
        dataMap.put("askList", serviceResult.getResult());
        dataMap.put("rowsCount", pager.getRowsCount());
        dataMap.put("pageSize", pager.getPageSize());
        dataMap.put("pageIndex", pageIndex);

        // 跳转类型（type=1跳转到秒杀单品页）
        String type = request.getParameter("type");
        dataMap.put("type", type);

        jsonResult.setData(dataMap);
        return jsonResult;
    }

    /**
     * 根据产品id及规格id查询货品价格及库存
     * @param request
     * @param response
     * @param map
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/getGoodsInfo.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<ProductGoods> getGoodsInfo(HttpServletRequest request,
                                                                   HttpServletResponse response,
                                                                   @RequestParam(value = "productId", required = true) Integer productId,
                                                                   String normAttrId) {

        Map<String, String> queryMap = new HashMap<String, String>();
        //获得货品信息 包含某规格商品的库存及价格信息
        queryMap.put("q_productId", productId + "");
        queryMap.put("q_normAttrId", normAttrId);
        ServiceResult<ProductGoods> serviceResult = productGoodsService
            .getProductGoodsByCondition(queryMap);

        HttpJsonResult<ProductGoods> jsonResult = new HttpJsonResult<ProductGoods>();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<ProductGoods>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 商品页异步加载立减、满减、优惠券信息
     * @param request
     * @param response
     * @param productId
     * @param sellerId
     * @return
     */
    @RequestMapping(value = "/getproductactinfo.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<ProductActVO> getProductActInfo(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        @RequestParam(value = "productId", required = true) Integer productId,
                                                                        @RequestParam(value = "sellerId", required = true) Integer sellerId) {

        ProductActVO productActVO = new ProductActVO();
        // 如果获取限时抢购的结果为空，则加载商品的单品立减、订单满减、优惠券信息
        ServiceResult<ActSingle> singleResult = actSingleService.getEffectiveActSingle(sellerId,
            ConstantsEJS.CHANNEL_3, productId);
        // 满减
        ServiceResult<ActFull> fullResult = actFullService.getEffectiveActFull(sellerId,
            ConstantsEJS.CHANNEL_3);
        // 优惠券
        ServiceResult<List<Coupon>> couponResult = couponService.getEffectiveCoupon(sellerId,
            ConstantsEJS.CHANNEL_3);
        productActVO.setActSingle(singleResult.getResult());
        productActVO.setActFull(fullResult.getResult());
        productActVO.setCouponList(couponResult.getResult());

        HttpJsonResult<ProductActVO> jsonResult = new HttpJsonResult<ProductActVO>();
        jsonResult.setData(productActVO);
        return jsonResult;
    }

    /**
     * 获取商品运费价格
     * @param request
     * @param response
     * @param productId
     * @param cityId
     * @return
     */
    @RequestMapping(value = "getTransportPrice", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<BigDecimal> getTransportPrice(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      @RequestParam(value = "productId", required = true) Integer productId,
                                                                      @RequestParam(value = "cityId", required = true) Integer cityId,
                                                                      @RequestParam(value = "num", required = true) Integer num) {
        HttpJsonResult<BigDecimal> jsonResult = new HttpJsonResult<BigDecimal>();
        ServiceResult<BigDecimal> serviceResult = sellerTransportService.getTransFee(productId,
            cityId, num);
        if (!serviceResult.getSuccess()) {
            new HttpJsonResult<BigDecimal>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

}
