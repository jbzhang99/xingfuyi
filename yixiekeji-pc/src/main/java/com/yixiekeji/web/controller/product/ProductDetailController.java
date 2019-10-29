package com.yixiekeji.web.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.core.matrix.MatrixToImageWriter;
import com.yixiekeji.entity.cart.Cart;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductComments;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerQq;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.member.IMemberAddressService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.product.IFrontProductPictureService;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.service.product.IProductAttrService;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductCommentsService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerQqService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.util.FrontProductPictureUtil;
import com.yixiekeji.vo.member.FrontMemberProductBehaveStatisticsVO;
import com.yixiekeji.vo.product.ProductActVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.PageModel;
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
    private IProductService             productService;
    @Resource
    private IProductFrontService        productFrontService;
    @Resource
    private ISellerService              sellerService;
    @Resource
    private ISellerQqService            sellerQqService;
    @Resource
    private IProductGoodsService        productGoodsService;
    @Resource
    private IProductBrandService        productBrandService;
    @Resource
    private IProductCateService         productCateService;
    @Resource
    private IProductAttrService         productAttrService;
    @Resource
    private IProductCommentsService     productCommentsService;
    @Resource
    private IProductAskService          productAskService;
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
    private IFrontProductPictureService frontProductPictureService;
    @Resource
    private DomainUrlUtil               domainUrlUtil;

    /**
     * 跳转到单品页 
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/product/{productId}.html", method = { RequestMethod.GET })
    public String toDetail(HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> dataMap, @PathVariable Integer productId) {

        dataMap.put("productId", productId);

        Member member = WebFrontSession.getLoginedUser(request,response);

        //获得商品信息
        ServiceResult<Product> productResult = productFrontService.getProductById(productId);
        if (!productResult.getSuccess()) {
            dataMap.put("info", "获得商品信息失败！");
            return "/front/commons/error";
        }
        if (productResult.getResult() == null) {
            dataMap.put("info", "获得商品信息为空！");
            return "/front/commons/error";
        }
        Product product = productResult.getResult();
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
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
        //取得商品品牌信息
        ServiceResult<ProductBrand> brandResult = productBrandService
            .getById(product.getProductBrandId());
        if (!brandResult.getSuccess()) {
            throw new BusinessException("获得商品品牌信息失败！");
        }
        if (brandResult.getResult() == null) {
            throw new BusinessException("获得商品品牌信息为空！");
        }
        dataMap.put("productBrand", brandResult.getResult());

        //取得分类名称，一级分类，二级分类
        ServiceResult<ProductCate> cateResult = productCateService
            .getProductCateById(product.getProductCateId());
        if (!cateResult.getSuccess()) {
            throw new BusinessException("获得商品分类信息失败！");
        }
        if (cateResult.getResult() == null) {
            throw new BusinessException("获得商品分类信息为空！");
        }
        ProductCate productCate = cateResult.getResult();
        dataMap.put("productCate", productCate);
        // 父分类，
        if (productCate.getPid() != null && productCate.getPid().intValue() != 0) {
            ServiceResult<ProductCate> catePResult = productCateService
                .getProductCateById(productCate.getPid());
            ProductCate productCateP = catePResult.getResult();
            dataMap.put("productCateP", productCateP);
            if (productCateP.getPid() != null && productCateP.getPid() != 0) {
                ServiceResult<ProductCate> catePPResult = productCateService
                    .getProductCateById(productCateP.getPid());
                ProductCate productCatePP = catePPResult.getResult();
                dataMap.put("productCatePP", productCatePP);
            }
        }

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

        //取得该商品所属分类下的所有TOP商品
        ServiceResult<List<Product>> topResult = productService
            .getByCateIdTop(product.getProductCateId(), ConstantsEJS.PRODUCT_PAGE_TOP_SIZE);
        if (!topResult.getSuccess()) {
            throw new BusinessException("获得推荐商品失败！");
        }
        for (Product topProduct : topResult.getResult()) {
            ServiceResult<ProductPicture> resultProductPicture = frontProductPictureService
                .getproductLead(productId);
            ProductPicture productPicture = resultProductPicture.getResult();
            topProduct.setImagePath(FrontProductPictureUtil.getproductLeadMiddle(productPicture));
        }
        dataMap.put("productTop", topResult.getResult());

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
            dataMap.put("info", "货品信息为空");
            return "front/commons/error";
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
                dataMap.put("memberAddress", memberAddressNew);
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
                dataMap.put("transpot", sellerTransportResult.getResult());
            }
        }

        // 组装商品规格信息
        List<ProductNorm> normList = new ArrayList<ProductNorm>();
        Map<String, ProductNorm> normMap = new HashMap<String, ProductNorm>();
        // 设定默认货品，第一个有效的货品
        ProductGoods defaultgoods = null;
        // 记录有效的规格组合
        List<String> effectAttr = new ArrayList<>();
        // 组装商品规格信息
        Map<String, ProductNormAttr> attrMap = new HashMap<String, ProductNormAttr>();
        for (ProductGoods good : goods) {
            String normName = good.getNormName(); // 如：颜色,红色;内存,4G
            String normAttrId = good.getNormAttrId(); // 如：1,17

            if (StringUtil.isEmpty(normName, true)) {
                // 规则属性为空则表示该商品没有启用规格（只有一个货品）
                // 设置默认货品，只有一个货品时设定该货品
                defaultgoods = good;
                dataMap.put("goods", defaultgoods);
                continue;
            }

            if (good.getState() == null || good.getState().intValue() == ProductGoods.DISABLE) {
                // 货品没有启用则规格不参与组装
                continue;
            }

            if (defaultgoods == null) {
                // 设置默认货品，有多个货品时，第一个有效货品为默认货品
                defaultgoods = good;
                dataMap.put("goods", defaultgoods);
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
                        // 货品中存的图片与product_norm_attr_opt中存的图片一样，所以使用货品的图片即可
                        // 系统规定只有颜色才有图片（product_norm表固定第一条数据是颜色，id为1，name为颜色）
                        if ("颜色".equals(cellName[0])
                            && !StringUtil.isEmpty(good.getImages(), true)) {
                            attr.setUrl(good.getImages());
                        }
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
                            // 货品中存的图片与product_norm_attr_opt中存的图片一样，所以使用货品的图片即可
                            // 因为相同颜色的图片一样，所以多次设定没有影响（product_norm表固定第一条数据是颜色，id为1，name为颜色）
                            if ("颜色".equals(cellName[0])
                                && !StringUtil.isEmpty(good.getImages(), true)) {
                                attr.setUrl(good.getImages());
                            }
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

        // 获得商家信息、商家详细信息及 商家qq信息  以及活动信息 
        ServiceResult<Seller> sellerServiceResult = sellerService
            .getSellerById(productResult.getResult().getSellerId());
        if (sellerServiceResult.getSuccess() && sellerServiceResult.getResult() != null) {
            // 商家基本信息
            Seller seller = sellerServiceResult.getResult();
            dataMap.put("seller", seller);
            // 商家QQ信息
            ServiceResult<List<SellerQq>> qqRlt = sellerQqService
                .getInusedSellerQqBySId(seller.getId());
            dataMap.put("sellerQqList", qqRlt.getResult());
            // 商家地址
            ServiceResult<String> locationRlt = sellerService
                .getSellerLocationByMId(seller.getMemberId());
            dataMap.put("sellerLocation", locationRlt.getResult());
        }

       
            // 类型参数为空则打开普通单品页
            return "front/product/productdetail";
      
    }

    /**
     * 单品页 评价列表，异步调用
     * @param request
     * @param response
     * @param dataMap
     * @param productId
     * @return
     */
    @RequestMapping(value = "/commentsList.html", method = { RequestMethod.POST })
    public String commentsListForProcutPage(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Map<String, Object> dataMap) {

        // 商品ID
        String productIdStr = request.getParameter("productId");
        Integer productId = ConvertUtil.toInt(productIdStr, 0);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        //查找所有的评价和咨询   状态为审核通过
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);

        //查找某个产品的 状态为前台显示的
        queryMap.put("q_productId", productIdStr);
        queryMap.put("q_state", String.valueOf(ProductComments.STATE_2));
        //好评  grade = 4或5 ,中评：3，差评：1或2
        String type = request.getParameter("type");
        queryMap.put("q_grades", type);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductComments>> serviceResult = productCommentsService
            .getProductComments(feignUtil);
        pager = serviceResult.getPager();
        //分页对象
        PageModel pm = new PageModel();
        int rowsCount = pager.getRowsCount();
        ProductComments productComments = new ProductComments();
        productComments.setProductId(productId);
        pm.init(request, rowsCount, null, productComments);
        dataMap.put("pm", pm);

        dataMap.put("commentsList", serviceResult.getResult());

        return "front/product/commentsList";
    }

    /**
     * 单品页 咨询列表，异步调用
     * @param request
     * @param response
     * @param dataMap
     * @param productId
     * @return
     */
    @RequestMapping(value = "/productAskList.html", method = { RequestMethod.POST })
    public String productAskListForProcutPage(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Map<String, Object> dataMap) {

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        //查找所有的评价和咨询   状态为审核通过
        Map<String, String> queryMap = new HashMap<String, String>();

        String productIdStr = request.getParameter("productId");

        if (productIdStr == null || productIdStr.length() == 0) {
            throw new BusinessException("产品id不能为空");
        }
        //查找某个产品的 状态为前台显示的
        queryMap.put("q_productId", productIdStr);
        queryMap.put("q_state", String.valueOf(ProductAsk.STATE_3));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductAsk>> serviceResult = productAskService.getProductAsks(feignUtil);
        pager = serviceResult.getPager();

        //分页对象
        PageModel pm = new PageModel();
        int rowsCount = pager.getRowsCount();
        ProductAsk ask = new ProductAsk();
        ask.setProductId(ConvertUtil.toInt(productIdStr, 0));
        pm.init(request, rowsCount, null, ask);

        dataMap.put("pm", pm);
        dataMap.put("askList", serviceResult.getResult());

        return "front/product/productasklist";
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
    public @ResponseBody HttpJsonResult<ProductGoods> collectionShop(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     Cart cart,
                                                                     @RequestParam(value = "productId", required = true) Integer productId) {

        Map<String, String> queryMap = new HashMap<String, String>();
        //获得货品信息 包含某规格商品的库存及价格信息
        queryMap.put("q_productId", productId + "");
        queryMap.put("q_normAttrId", cart.getSpecId());
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
     * 生成二维码，移到用户至移动端
     * @param request
     * @param response
     * @param productId
     * @return
     * @throws IOException 
     * @throws WriterException 
     */
    @RequestMapping(value = "/mbuy.html", method = { RequestMethod.GET })
    public void mBuy(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "productId", required = true) Integer productId) throws IOException,
                                                                                            WriterException {

        String text = domainUrlUtil.getH5Url() + "/product/" + productId + ".html";
        int width = 300;
        int height = 300;
        //二维码的图片格式
        String format = "gif";
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        //内容所使用编码  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width,
            height, hints);
        //生成二维码  
        MatrixToImageWriter.writeToStream(bitMatrix, format, response.getOutputStream());
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
            ConstantsEJS.CHANNEL_2, productId);
        // 满减
        ServiceResult<ActFull> fullResult = actFullService.getEffectiveActFull(sellerId,
            ConstantsEJS.CHANNEL_2);
        // 优惠券
        ServiceResult<List<Coupon>> couponResult = couponService.getEffectiveCoupon(sellerId,
            ConstantsEJS.CHANNEL_2);
        productActVO.setActSingle(singleResult.getResult());
        productActVO.setActFull(fullResult.getResult());
        productActVO.setCouponList(couponResult.getResult());

        HttpJsonResult<ProductActVO> jsonResult = new HttpJsonResult<ProductActVO>();
        jsonResult.setData(productActVO);
        return jsonResult;
    }


    /**
     * 加载省市区信息
     * @param request
     * @param response
     * @param parentId
     * @return
     */
    @RequestMapping(value = "getRegionByParentId", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Regions>> getRegions(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  @RequestParam(value = "parentId", required = true) Integer parentId) {
        HttpJsonResult<List<Regions>> jsonResult = new HttpJsonResult<List<Regions>>();

        ServiceResult<List<Regions>> serviceResult = regionsService.getRegionsByParentId(parentId);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<List<Regions>>(serviceResult.getMessage());
        }
        jsonResult.setData(serviceResult.getResult());
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
