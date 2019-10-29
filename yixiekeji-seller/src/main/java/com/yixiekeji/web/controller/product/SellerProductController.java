package com.yixiekeji.web.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.HttpJsonResultForAjax;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductNormAttrOpt;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductAttrService;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.product.IProductNormAttrOptService;
import com.yixiekeji.service.product.IProductNormService;
import com.yixiekeji.service.product.IProductPictureService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.product.IProductTypeAttrService;
import com.yixiekeji.service.product.IProductTypeService;
import com.yixiekeji.service.product.ISellerApplyBrandService;
import com.yixiekeji.service.product.ISellerCateService;
import com.yixiekeji.service.product.ISellerManageCateService;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.csrf.CsrfTokenManager;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商品管理新增、修改controller
 *                       
 * @Filename: SellerProductController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/product")
public class SellerProductController extends BaseController {
    @Resource
    private ISellerManageCateService   sellerManageCateService;
    @Resource
    private ISellerApplyBrandService   sellerApplyBrandService;
    @Resource
    private IProductCateService        productCateService;
    @Resource
    private IProductTypeAttrService    sellerProductTypeAttrService;
    @Resource
    private IProductTypeService        sellerProductTypeService;
    @Resource
    private IProductNormService        sellerProductNormService;
    @Resource
    private IProductBrandService       productBrandService;
    @Resource
    private ISellerCateService         sellerCateService;
    @Resource
    private IProductService            productService;
    @Resource
    private IProductPictureService     productPicService;
    @Resource
    private IProductAttrService        productAttrService;
    @Resource
    private IProductGoodsService       productGoodsService;
    @Resource
    private IProductNormAttrOptService productNormAttrOptService;
    @Resource
    private IProductPictureService     productPictureService;
    @Resource
    private ISellerTransportService    sellerTransportService;

    private String                     baseUrl = "seller/product/pdt/";

    @Resource
    private DomainUrlUtil              domainUrlUtil;

    private static Logger              log     = LoggerFactory
        .getLogger(SellerProductController.class);

    /**
     * 发布商品-选择分类
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "chooseCate", method = { RequestMethod.GET })
    public String chooseCate(HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<List<ProductCate>> serviceResult = productCateService
            .getCateBySellerId(sellerUser.getSellerId());
        if (serviceResult.getSuccess() == true && serviceResult.getResult() != null) {
            dataMap.put("cate", serviceResult.getResult());
        }
        return baseUrl + "choosecate";
    }

    /**
     * ajax获取二级、三级分类
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "getCateById", method = { RequestMethod.GET })
    @ResponseBody
    public Object getCateById(HttpServletRequest request, @RequestParam("id") Integer id) {
        HttpJsonResult<List<ProductCate>> jsonResult = new HttpJsonResult<List<ProductCate>>();
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<List<ProductCate>> serviceResult = productCateService
            .getCateBySellerIdAndPid(user.getSellerId(), id);
        if (serviceResult.getSuccess() == true && serviceResult.getResult() != null) {
            jsonResult.setRows(serviceResult.getResult());
        }

        return jsonResult;
    }

    /**
     * 根据商品分类id组装商品类型属性、商品规格属性
     *
     * @param productCateId
     * @param dataMap
     * @param optlist 
     */
    private void assembleProp(Integer productCateId, Map<String, Object> dataMap, Integer productId,
                              List<ProductNormAttrOpt> optlist) {
        ServiceResult<ProductCate> cate = productCateService.getProductCateById(productCateId);
        if (cate.getSuccess() == true && cate.getResult() != null) {

            Integer typeId = cate.getResult().getProductTypeId();
            ServiceResult<List<ProductTypeAttr>> typeAttr = sellerProductTypeAttrService
                .getProductTypeAttrByTypeId(typeId);

            /**组装商品类型属性*/
            if (typeAttr.getSuccess() == true && typeAttr.getResult() != null
                && typeAttr.getResult().size() > 0) {
                List<ProductTypeAttr> attrList = typeAttr.getResult();
                List<Map<String, Object>> queryTypeAttr = new ArrayList<Map<String, Object>>();
                List<Map<String, Object>> custTypeAttr = new ArrayList<Map<String, Object>>();

                List<ProductAttr> productAttrList = null;
                if (null != productId) {
                    //编辑商品时候使用
                    ServiceResult<List<ProductAttr>> attrServiceResult = productAttrService
                        .getProductAttrByProductId(productId);
                    if (attrServiceResult.getSuccess() && attrServiceResult.getResult() != null) {
                        productAttrList = attrServiceResult.getResult();
                    }
                }
                for (ProductTypeAttr attr1 : attrList) {
                    Map<String, Object> attrMap = new HashMap<String, Object>();
                    if (attr1.getType() == 1) {
                        processAttr(attr1, attrMap, productAttrList);
                        queryTypeAttr.add(attrMap);//查询属性
                    } else {
                        processAttr(attr1, attrMap, productAttrList);
                        custTypeAttr.add(attrMap);//自定义属性
                    }
                }
                dataMap.put("queryTypeAttr", queryTypeAttr);
                dataMap.put("custTypeAttr", custTypeAttr);
            }

            /**组装商品属性信息**/
            ServiceResult<ProductType> serviceResult = sellerProductTypeService
                .getProductTypeById(typeId);
            if (serviceResult.getSuccess() && serviceResult.getResult() != null) {
                ProductType productType = serviceResult.getResult();
                //                String productNormIds = productType.getProductNormIds();

                String productNormIds = getProductTypeIds(optlist, productType);

                if (!StringUtil.isEmpty(productNormIds)) {
                    String[] normIds = productNormIds.split(",");
                    List<Map<String, Object>> normList = new ArrayList<Map<String, Object>>(
                        normIds.length);
                    for (String normId : normIds) {
                        Map<String, Object> attrMap = null;
                        Integer id = Integer.valueOf(normId);
                        ServiceResult<ProductNorm> normResult = sellerProductNormService
                            .getNormById(id);

                        if (normResult.getSuccess() && normResult.getResult() != null) {
                            attrMap = new HashMap<String, Object>(2);
                            ProductNorm result = normResult.getResult();
                            List<ProductNormAttr> list = result.getAttrList();
                            List<Map<String, Object>> attrList = new ArrayList<Map<String, Object>>(
                                result.getAttrList().size());

                            /**构造货品属性**/
                            List<String> normAttrIdList = new ArrayList<String>();
                            if (null != productId) {
                                Map<String, String> queryMap = new HashMap<String, String>(1);
                                queryMap.put("q_productId", String.valueOf(productId));
                                FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

                                ServiceResult<List<ProductGoods>> listServiceResult = productGoodsService
                                    .pageProductGoods(feignUtil);
                                if (listServiceResult.getSuccess()
                                    && listServiceResult.getResult() != null
                                    && listServiceResult.getResult().size() > 0) {
                                    List<ProductGoods> goodsList = listServiceResult.getResult();

                                    processGoods(goodsList, dataMap);//货品信息

                                    for (ProductGoods goods : goodsList) {
                                        String normAttrId = goods.getNormAttrId();
                                        if (!StringUtil.isEmpty(normAttrId)) {
                                            String[] split = normAttrId.split(",");
                                            for (String str : split) {
                                                if (!StringUtil.isEmpty(str)) {
                                                    normAttrIdList.add(str);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (normAttrIdList.size() > 0) {
                                Set<String> set = new HashSet<String>();
                                set.addAll(normAttrIdList);
                                normAttrIdList.clear();
                                normAttrIdList.addAll(set);
                            }

                            attrMap.put("id", result.getId());
                            attrMap.put("name", result.getName());
                            attrMap.put("attrList", attrList);
                            if (null != list && list.size() > 0) {
                                Map<String, Object> map = null;
                                for (ProductNormAttr attr : list) {
                                    map = new HashMap<String, Object>(3);
                                    map.put("id", attr.getId());
                                    map.put("name", attr.getName());
                                    attrList.add(map);
                                }
                            }
                            normList.add(attrMap);
                        }
                    }
                    dataMap.put("normList", normList);
                }
            }
        }
    }

    /**
     * 商品原有分类id
     * @param optlist
     * @param productType 
     */
    private String getProductTypeIds(List<ProductNormAttrOpt> optlist, ProductType productType) {
        if (isNull(optlist)) {
            return productType.getProductNormIds();
        }
        //如果此商品的类型关联的规格发生改变，则确保此商品在原有分类下
        Set<Integer> optids = new HashSet<>();
        for (ProductNormAttrOpt opt : optlist) {
            optids.add(opt.getProductNormId());
        }
        //optids => 10,11  normids => 1,3
        if (isNull(productType.getProductNormIds())) {
            return null;
        }
        String[] normids = productType.getProductNormIds().split(",");
        if (normids == null || normids.length < 1) {
            return null;
        }
        List<Integer> productNormIds = null;
        boolean contains = false;

        //如果该分类所关联的规格下无此选中商品的规格，则返回原商品选中的规格
        Iterator<Integer> it = optids.iterator();
        List<String> normidlist = Arrays.asList(normids);
        while (it.hasNext()) {
            Integer normid = it.next();
            //只要有一个规格发生改变，则使用原商品的规格
            if (contains || !normidlist.contains(normid.toString())) {
                if (isNull(productNormIds)) {
                    productNormIds = new ArrayList<>();
                }
                productNormIds.add(normid);
                contains = true;
            }
        }

        if (contains) {
            return StringUtil.listToString(productNormIds, ",");
        } else {
            return productType.getProductNormIds();
        }
    }

    /**
     * 构造货品信息
     *
     * @param goodsList
     * @param dataMap
     */
    private void processGoods(List<ProductGoods> goodsList, Map<String, Object> dataMap) {
        if (null != goodsList && goodsList.size() > 0) {
            List<Map<String, Object>> goods = new ArrayList<Map<String, Object>>(goodsList.size());
            List<Integer> goodsAttrIds = new ArrayList<>();
            Map<String, Object> goodMap = null;
            Set<String> normname = new LinkedHashSet<String>();

            for (ProductGoods good : goodsList) {
                goodMap = new HashMap<String, Object>();
                String normAttrId = good.getNormAttrId();
                if (!StringUtil.isEmpty(normAttrId)) {
                    String[] split = normAttrId.split(",");

                    if (null != split && split.length == 1) {//一列规格
                        goodMap.put("normId1", normAttrId);
                        String normName = good.getNormName();
                        goodMap.put("normName1", normName.split(",")[1]);
                        normname.add(normName.split(",")[0]);

                        goodsAttrIds.add(Integer.valueOf(normAttrId));
                    } else if (null != split && split.length == 2) {//两列规格
                        goodMap.put("normId1", split[0]);
                        goodMap.put("normId2", split[1]);
                        String normName = good.getNormName();
                        String column1 = normName.split(";")[0];
                        column1 = column1.substring(0, column1.indexOf(","));
                        String column2 = normName.split(";")[1];
                        column2 = column2.substring(0, column2.indexOf(","));
                        String normName1 = normName.split(";")[0];
                        normName1 = normName1.substring(normName1.indexOf(",") + 1,
                            normName1.length());
                        String normName2 = normName.split(";")[1];
                        normName2 = normName2.substring(normName2.indexOf(",") + 1,
                            normName2.length());

                        goodMap.put("normName1", normName1);
                        goodMap.put("normName2", normName2);
                        normname.add(column1);
                        normname.add(column2);

                        goodsAttrIds.add(Integer.valueOf(split[0]));
                        goodsAttrIds.add(Integer.valueOf(split[1]));
                    }

                    goodMap.put("sku", good.getSku());
                    goodMap.put("stock", good.getProductStock());
                    goodMap.put("mobilePrice", good.getMallMobilePrice());
                    goodMap.put("pcPrice", good.getMallPcPrice());
                    goodMap.put("state", good.getState());
                    goodMap.put("weight", good.getWeight());
                    goodMap.put("length", good.getLength());
                    goodMap.put("width", good.getWidth());
                    goodMap.put("height", good.getHeight());

                    goods.add(goodMap);

                }
            }
            dataMap.put("goods", goods);
            dataMap.put("column", normname);
            dataMap.put("goodsAttrIds", goodsAttrIds);
        }
    }

    private void processAttr(ProductTypeAttr attr1, Map<String, Object> attrMap,
                             List<ProductAttr> productAttrList) {
        List<String> attrValList = new ArrayList<String>();
        String attrVal = attr1.getValue();
        if (!StringUtil.isEmpty(attrVal)) {
            String[] split = attrVal.split(",");
            for (String str : split) {
                if (StringUtil.isEmpty(str))
                    continue;
                attrValList.add(str);
            }
        }

        attrMap.put("attrId", attr1.getId());
        attrMap.put("attrName", attr1.getName());
        attrMap.put("attrValList", attrValList);

        //编辑商品时候使用
        if (null != productAttrList && productAttrList.size() > 0) {
            for (ProductAttr attr : productAttrList) {
                if (attr.getProductTypeAttrId().equals(attr1.getId())) {
                    attrMap.put("dbAttr", attr.getValue());
                }
            }
        }
    }

    /**
     * 添加商品详细信息
     *
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        String rtnPath = baseUrl + "productadd";
        /**1.判断是否登录*/
        SellerUser user = WebSellerSession.getSellerUser(request);
        /**2.判断是否选择商品分类*/
        String cateId = request.getParameter("cateId");
        if (StringUtil.isEmpty(cateId)) {
            return "redirect:/seller/product/chooseCate";
        }
        /**3.构造商品分类路径*/
        ServiceResult<String> catePathResult = productCateService
            .getCatePathStrById(Integer.valueOf(cateId));
        if (catePathResult.getSuccess() && catePathResult.getResult() != null) {
            dataMap.put("catePath", catePathResult.getResult());
            dataMap.put("cateId", cateId);
        }

        /**4.获取分类信息**/
        ServiceResult<ProductCate> cateResult = productCateService
            .getProductCateById(Integer.valueOf(cateId));
        if (!cateResult.getSuccess() || cateResult.getResult() == null) {
            dataMap.put("message", "分类信息获取失败，请联系商城管理员");
            return "redirect:/seller/product/chooseCate";
        }
        dataMap.put("productCatePath", cateResult.getResult().getPath());

        /**5.获取商家可以经营的品牌**/
        ServiceResult<List<ProductBrand>> brandResult = productBrandService
            .getBrandByTypeId(cateResult.getResult().getProductTypeId());
        if (brandResult.getSuccess() && brandResult.getResult() != null) {
            dataMap.put("brand", brandResult.getResult());
        }
        /**6.组装商品类型信息（单品页商品介绍下的内容，商品属性）、组装商品规格信息（单品页商品图片右侧选择信息）*/
        assembleProp(Integer.valueOf(cateId), dataMap, null, null);

        /**7.本店分类(1级)**/
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(0,
            user.getSellerId());
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            dataMap.put("sellerCate", serviceResult.getResult());
        }

        /**8.判断自营还是商家 1自营**/
        if (user.getSellerId() == 1) {
            dataMap.put("seller", 1);
        }

        /**9.初始化运费模板(按件数)  */
        ServiceResult<List<SellerTransport>> sellerTransportResult = sellerTransportService
            .getTransportByTypeAndSellerId(Product.TRANSPORT_TYPE_1, user.getSellerId());
        if (!sellerTransportResult.getSuccess()) {
            dataMap.put("message", "运费模板信息获取失败，请联系商城管理员");
            return "redirect:/seller/settlement";
        }
        dataMap.put("tansports", sellerTransportResult.getResult());

        return rtnPath;
    }

    /**
     * 保存商品
     *
     * @param product
     * @param request
     * @param dataMap
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(Product product, HttpServletRequest request,
                                         Map<String, Object> dataMap) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>(null,
            CsrfTokenManager.getTokenForSession(CsrfTokenManager.getMemkeyFromRequest(request),
                request.getSession()));
        SellerUser user = WebSellerSession.getSellerUser(request);

        ServiceResult<Boolean> serviceResult = createOrUpdateProduct(product, request,
            user.getSeller(), "C", user);
        if (serviceResult.getSuccess() && serviceResult.getResult() == true) {
        } else {
            jsonResult.setSuccess(serviceResult.getSuccess());
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 编辑商品
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request,
                       @RequestParam(value = "id", required = true) Integer id,
                       Map<String, Object> dataMap) {
        /**1.判断是否登录*/
        SellerUser user = WebSellerSession.getSellerUser(request);

        /**查询商品信息**/
        ServiceResult<Product> productServiceResult = productService.getProductById(id);
        if (!productServiceResult.getSuccess()) {
            return "/seller/500";
        }
        Product product = productServiceResult.getResult();
        if (null == product) {
            return "/seller/404";
        }
        if (!product.getSellerId().equals(user.getSellerId())) {
            return "/seller/unauth";
        }
        int state = product.getState();
        if (state != Product.STATE_1 && state != Product.STATE_4 && state != Product.STATE_7) {
            // 如果状态不对则返回列表页
            return "redirect:/seller/product/waitSale";
        }
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }

        dataMap.put("product", product);

        /**分类名称**/
        ServiceResult<String> catePathResult = productCateService
            .getCatePathStrById(product.getProductCateId());
        if (catePathResult.getSuccess() && catePathResult.getResult() != null) {
            dataMap.put("catePath", catePathResult.getResult());
        }

        /**获取商家可以经营的品牌**/
        ServiceResult<ProductCate> cateResult = productCateService
            .getProductCateById(product.getProductCateId());
        if (!cateResult.getSuccess() || cateResult.getResult() == null) {
            dataMap.put("message", "该分类下无可以经营的品牌，请重新选择分类，或者联系商城管理员");
            return "redirect:/seller/product/waitSale";
        }
        ServiceResult<List<ProductBrand>> brandResult = productBrandService
            .getBrandByTypeId(cateResult.getResult().getProductTypeId());
        if (brandResult.getSuccess() && brandResult.getResult() != null) {
            dataMap.put("brand", brandResult.getResult());
        }

        /**选中的颜色规格*/
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("sellerId", WebSellerSession.getSellerUser(request).getSellerId() + "");
        queryMap.put("productId", id + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<ProductNormAttrOpt>> optservice = productNormAttrOptService
            .page(feignUtil);
        List<ProductNormAttrOpt> optlist = optservice.getResult();

        /**商品类型*/
        assembleProp(product.getProductCateId(), dataMap, product.getId(), optlist);

        /**商品图片**/
        ServiceResult<List<ProductPicture>> pictureServiceResult = productPicService
            .getProductPictureByProductId(product.getId());
        if (pictureServiceResult.getSuccess() && pictureServiceResult.getResult() != null) {
            for (ProductPicture pic : pictureServiceResult.getResult()) {
                String path = pic.getImagePath();
                path = domainUrlUtil.getImageResources() + path;
                pic.setImagePath(path);
            }
            dataMap.put("pic", pictureServiceResult.getResult());
        }

        Integer sellerCateId = product.getSellerCateId();
        boolean isFirst = false;

        /**本店分类**/
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService.getByPid(0,
            user.getSellerId());
        if (serviceResult.getSuccess() && null != serviceResult.getResult()) {
            dataMap.put("sellerCate", serviceResult.getResult());//一级分类
            //过滤一级目录
            for (SellerCate sellerCate : serviceResult.getResult()) {
                if (sellerCate.getId() == sellerCateId) {
                    dataMap.put("cateId", sellerCate.getId());//一级分类id
                    isFirst = true;
                }
            }
        }

        if (!isFirst) {
            ServiceResult<SellerCate> sellerCateResult = sellerCateService
                .getSellerCateById(sellerCateId);
            if (sellerCateResult.getSuccess() && sellerCateResult.getResult() != null) {
                SellerCate sellerCate = sellerCateResult.getResult();//当前分类信息
                ServiceResult<List<SellerCate>> secondCateResult = sellerCateService
                    .getByPid(sellerCate.getPid(), user.getSellerId());//二级分类
                if (secondCateResult.getSuccess() && secondCateResult.getResult() != null) {
                    dataMap.put("secondSellerCate", secondCateResult.getResult());
                    dataMap.put("cateId", sellerCate.getPid());//一级分类id
                    dataMap.put("secondCateId", sellerCateId);//二级分类id
                }
            }
        }

        /**判断自营还是商家 1自营**/
        if (user.getSellerId() == 1) {
            dataMap.put("seller", 1);
        }

        //颜色规格
        List<ProductNormAttrOpt> colorAttr = new ArrayList<ProductNormAttrOpt>();
        List<Integer> goodsAttrIds = (List<Integer>) dataMap.get("goodsAttrIds");

        Object norm = dataMap.get("normList");
        if (!isNull(norm)) {
            //默认选中
            List<Map<String, Object>> normlist = (List<Map<String, Object>>) norm;
            for (Map<String, Object> normmap : normlist) {
                Object attrobj = normmap.get("attrList");
                Integer normid = (Integer) normmap.get("id");
                if (!isNull(attrobj)) {
                    List<Map<String, Object>> attrlist = (List<Map<String, Object>>) attrobj;
                    List<Map<String, Object>> selectedAttrList = new ArrayList<Map<String, Object>>(
                        attrlist);

                    //所有选中的规格
                    for (ProductNormAttrOpt opt : optlist) {
                        //所有查询属性
                        for (Map<String, Object> attr : attrlist) {
                            Integer attrid = (Integer) attr.get("id");
                            if (opt.getAttrId().intValue() == attrid) {
                                //选中的查询属性
                                attr.put("checked", true);
                                attr.put("image", opt.getImage());
                            } else {
                                if (normid == 1 && opt.getTypeAttr() == 2) {
                                    //自定义颜色属性
                                    Map<String, Object> newattr = new HashMap<String, Object>();
                                    newattr.put("id", opt.getAttrId());
                                    newattr.put("name", opt.getName());
                                    newattr.put("checked", true);
                                    newattr.put("image", opt.getImage());
                                    //添加至属性集合
                                    selectedAttrList.add(newattr);
                                    //找到选中规格后跳出
                                    break;
                                }
                            }
                        }
                        //颜色规格
                        if (normid == 1 && opt.getProductNormId() == 1) {
                            colorAttr.add(opt);
                        }
                    }

                    //将属性值重新放入规格集合
                    //兼容旧版,选中的规格的sku信息不匹配的，取sku规格
                    Iterator<Map<String, Object>> it = selectedAttrList.iterator();
                    while (it.hasNext()) {
                        Map<String, Object> stl = it.next();
                        Iterator<Entry<String, Object>> allnormattr = stl.entrySet().iterator();
                        while (allnormattr.hasNext()) {
                            Entry<String, Object> entry = allnormattr.next();
                            if (entry.getKey().equals("id")) {
                                Integer value = (Integer) entry.getValue();
                                if (!goodsAttrIds.contains(value)) {
                                    stl.put("checked", false);
                                    break;
                                }
                            }
                        }
                    }
                    normmap.put("attrList", selectedAttrList);

                }
            }

            //兼容旧版,选中的规格的sku信息不匹配的，取sku规格
            List<ProductNormAttrOpt> selectedColorAttr = new ArrayList<ProductNormAttrOpt>(
                colorAttr);
            for (ProductNormAttrOpt opt : colorAttr) {
                if (!goodsAttrIds.contains(opt.getAttrId())) {
                    selectedColorAttr.remove(opt);
                }
            }

            //颜色属性
            dataMap.put("colorAttr", selectedColorAttr);
        }

        dataMap.put("edit", "edit");

        /**9.初始化运费模板  */
        ServiceResult<List<SellerTransport>> sellerTransportResult = sellerTransportService
            .getTransportByTypeAndSellerId(product.getTransportType(), user.getSellerId());
        if (!sellerTransportResult.getSuccess()) {
            dataMap.put("message", "运费模板信息获取失败，请联系商城管理员");
            return "redirect:/seller/settlement";
        }
        dataMap.put("tansports", sellerTransportResult.getResult());

        return baseUrl + "productedit";
    }

    /**
     * 更新商品
     *
     * @param product
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(Product product,
                                         HttpServletRequest request) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>(null,
            CsrfTokenManager.getTokenForSession(CsrfTokenManager.getMemkeyFromRequest(request),
                request.getSession()));
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<Product> selResult = productService.getProductById(product.getId());
        if (!selResult.getSuccess()) {
            jsonResult.setMessage(selResult.getMessage());
            return jsonResult;
        }
        Product productDB = selResult.getResult();
        if (productDB == null) {
            return new HttpJsonResult<Object>("商品信息获取失败，请重试");
        }
        if (!productDB.getSellerId().equals(user.getSellerId())) {
            return new HttpJsonResult<Object>("您无权修改该商品。");
        }

        ServiceResult<Boolean> serviceResult = createOrUpdateProduct(product, request,
            user.getSeller(), "U", user);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * ajax商品图片上传
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "uploadFiles", method = { RequestMethod.POST })
    public @ResponseBody Object uploadImage(MultipartHttpServletRequest request,
                                            HttpServletResponse response, String fileIndex) {
        log.info("UploadImageController uploadImage start");
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile mpf : fileList) {
                        String originalFilename = mpf.getOriginalFilename();
                        File tmpFile = new File(buildImgPath(request) + "/" + UUID.randomUUID()
                                                + originalFilename.substring(
                                                    originalFilename.lastIndexOf("."),
                                                    originalFilename.length()));
                        if (!mpf.isEmpty()) {
                            byte[] bytes = mpf.getBytes();
                            BufferedOutputStream stream = new BufferedOutputStream(
                                new FileOutputStream(tmpFile));
                            stream.write(bytes);
                            stream.close();
                        }

                        String url = UploadUtil.getInstance().productUploaderImage(tmpFile, request,
                            domainUrlUtil.getImageResources() + "/imageUtilProduct");

                        System.err.println("当前上传图片的路径:"+url);
                        //规范路径,以避免浏览器兼容问题
                        if(!StringUtil.isEmpty(url))
                            url = url.replaceAll("\\\\", "/");
                        
                        result.put("url", domainUrlUtil.getImageResources() + url);
                        result.put("fileIndex", fileIndex);
                        jsonResult.setData(result);

                        log.debug("url==================" + url);
                        log.debug("fileIndex==================" + fileIndex);

                        return jsonResult;
                    }
                }
            }
        } catch (Exception e) {
            log.error("[SellerProductController][uploadImage] exception:", e);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }
        return null;
    }

    /**
     * SKU图片上传
     * @param request
     * @param response
     * @param attrid 属性id
     * @param colortype 颜色类型 custom-自定义
     * @param uploadtype 上传类型 1-创建 2-更新
     * @param productId 商品id（仅更新时有效）
     * @return
     */
    @RequestMapping(value = "uploadSKUImage", method = { RequestMethod.POST })
    public @ResponseBody Object uploadSKUImage(MultipartHttpServletRequest request,
                                               HttpServletResponse response, Integer attrid,
                                               String colortype, Integer uploadtype,
                                               Integer normindex, Integer attrindex,
                                               Integer productId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Map<String, Object> result = new HashMap<String, Object>();
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        try {
            if (map != null) {
                Iterator<String> iter = map.keySet().iterator();
                while (iter.hasNext()) {
                    String str = iter.next();
                    List<MultipartFile> fileList = map.get(str);
                    for (MultipartFile mpf : fileList) {
                        String originalFilename = mpf.getOriginalFilename();
                        File tmpFile = new File(buildImgPath(request) + "/" + UUID.randomUUID()
                                                + originalFilename.substring(
                                                    originalFilename.lastIndexOf("."),
                                                    originalFilename.length()));
                        String url = null;
                        try {
                            if (!mpf.isEmpty()) {
                                byte[] bytes = mpf.getBytes();
                                BufferedOutputStream stream = new BufferedOutputStream(
                                    new FileOutputStream(tmpFile));
                                stream.write(bytes);
                                stream.close();
                            }

                            url = UploadUtil.getInstance().productUploaderImage(tmpFile, request,
                                domainUrlUtil.getImageResources() + "/imageUtilProduct");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tmpFile.deleteOnExit();
                        //删除目录
                        UploadUtil.deleteDir(tmpFile.getParentFile().getParentFile());

                        //返回值
                        result.put("attrid", attrid);
                        result.put("colortype", colortype);
                        result.put("normindex", normindex);
                        result.put("attrindex", attrindex);
                        jsonResult.setData(result);

                        //上传失败
                        if (isNull(url))
                            throw new BusinessException("上传失败");
                        //规范路径,以避免浏览器兼容问题
                        url = url.replaceAll("\\\\", "/");
                        result.put("url", url);

                    }
                }
            }
        } catch (Exception e) {
            jsonResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 保存或者更新商品
     *
     * @param product
     * @param request
     * @param seller
     * @param type    C:保存 U:更新
     * @return
     */
    private ServiceResult<Boolean> createOrUpdateProduct(Product product,
                                                         HttpServletRequest request, Seller seller,
                                                         String type, SellerUser sellerUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription()
                .replace(domainUrlUtil.getImageResources(), "${(domainUrlUtil.imageResources)!}"));
        }
        try {
            if (seller.getId() == 1) {
                product.setIsSelf(Product.IS_SELF_1);//自营
                product.setVirtualSales(Integer.valueOf(request.getParameter("virtualSales")));
            } else {
                product.setIsSelf(Product.IS_SELF_2);//商家
            }
            Integer productBrandId = ConvertUtil.toInt(request.getParameter("productBrandId"), 0);
//            if (0 == productBrandId) {
//                result.setMessage("请选择品牌");
//                result.setSuccess(Boolean.FALSE);
//                return result;
////                //请选择品牌
////                throw new BusinessException("请选择品牌");
//            }
            Integer sellerCateId = ConvertUtil.toInt(request.getParameter("sellerCateId"), 0);
            if (0 == sellerCateId) {
                result.setMessage("请选择商家分类");
                result.setSuccess(Boolean.FALSE);
                return result;
//                //请选择商家分类
//                throw new BusinessException("请选择商家分类");
            }
            String isNormStr = request.getParameter("isNorm");
            if (isNormStr == null) {
                // 编辑商品时表单为disabled，取不到值
                isNormStr = request.getParameter("isNormHidden");
            }
            Integer isNorm = ConvertUtil.toInt(isNormStr, 1);
            product.setIsNorm(isNorm);

            if (2 == isNorm) {
                List<ProductGoods> goodslist = processGoods(request);
                product.setGoodsList(goodslist);
            } else {
                product.setSku(request.getParameter("sku"));
            }

            product.setProductBrandId(productBrandId);
            product.setKeyword(request.getParameter("keyword1"));
            Integer auditStatus = seller.getAuditStatus();
            if (auditStatus.intValue() == Seller.AUDIT_STATE_2_DONE) {
                auditStatus = Product.SELLER_STATE_1;
            } else {
                auditStatus = Product.SELLER_STATE_2;
            }
            product.setSellerState(auditStatus);// 店铺状态
            product.setCommentsNumber(ConvertUtil.toInt(request.getParameter("commentsNumber"), 0));
            product.setSellerId(seller.getId());
            product.setSellerCateId(sellerCateId);
            product.setVirtualSales(ConvertUtil.toInt(request.getParameter("virtualSales"), 0));
            product.setActualSales(ConvertUtil.toInt(request.getParameter("actualSales"), 0));
            product.setCreateId(sellerUser.getId());
            product.setKeyword(request.getParameter("keyword1"));
            product.setProductCateState(Product.PRODUCT_CATE_STATE_1);//分类正常
            product.setIsTop(Product.IS_TOP_1);//不推荐
            if (!StringUtil.isEmpty(product.getDescription())) {
                String description = product.getDescription();
                description = description.replaceAll(System.getProperty("line.separator"), "");
                product.setDescription(description);
            }
            // 是否是虚拟商品
            product.setIsInventedProduct(
                ConvertUtil.toInt(request.getParameter("isInventedProduct"), 1));

            List<ProductPicture> picList = new ArrayList<ProductPicture>();
            List<ProductAttr> attrList = new ArrayList<ProductAttr>();
            String pics = request.getParameter("imageSrc");
            if (!StringUtil.isEmpty(pics)) {
                String[] split = pics.split(";");
                for (int i = 0; i < split.length; i++) {
                    ProductPicture picture = new ProductPicture();
                    String img = split[i];
                    img = img.replace(domainUrlUtil.getImageResources(), "");
                    picture.setImagePath(img);
                    picture.setSort(i);
                    picture.setCreateId(sellerUser.getId());
                    picture.setState(1);
                    picture.setSellerId(seller.getId());
                    if (i == 0) {
                        picture.setProductLead(1);
                        product.setMasterImg(img);
                    } else {
                        picture.setProductLead(2);
                    }
                    picList.add(picture);
                }
            }
            //商品查询属性
            String queryType = request.getParameter("queryType");
            if (!StringUtil.isEmpty(queryType)) {
                String[] split = queryType.split(";");//productTypeAttrId,name,value
                for (String str : split) {
                    String[] split1 = str.split(",");
                    if (split1.length != 3)
                        continue;
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductTypeAttrId(Integer.valueOf(split1[0]));
                    productAttr.setName(split1[1]);
                    if ("-1".equals(split1[2])) {
                        productAttr.setValue("");
                    } else {
                        productAttr.setValue(split1[2]);
                    }
                    productAttr.setState(ProductAttr.STATE_1);//检索属性
                    attrList.add(productAttr);
                }
            }
            //自定义属性
            String custAttr = request.getParameter("custAttr");
            if (!StringUtil.isEmpty(custAttr)) {
                String[] split = custAttr.split(";");//productTypeAttrId,name,value
                for (String str : split) {
                    String[] split1 = str.split(",");
                    ProductAttr productAttr = new ProductAttr();
                    productAttr.setProductTypeAttrId(Integer.valueOf(split1[0]));
                    productAttr.setName(split1[1]);
                    if (split1.length == 2) {
                        productAttr.setValue("");
                    } else {
                        productAttr.setValue(split1[2]);
                    }
                    productAttr.setState(ProductAttr.STATE_2);//自定义属性
                    attrList.add(productAttr);
                }
            }

            if ("C".equals(type)) {
                
                product.setProductCode(getNumberForPK("SPU-CLRK","1"));
                product.setState(Product.STATE_1);
                FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
                feignProjectUtil.setParammap(request.getParameterMap());
                feignProjectUtil.setProduct(product);
                feignProjectUtil.setProductPictureList(picList);
                feignProjectUtil.setProductAttrList(attrList);
                result = productService.saveProduct(feignProjectUtil);
            }
            if ("U".equals(type)) {
                FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
                feignProjectUtil.setParammap(request.getParameterMap());
                feignProjectUtil.setProduct(product);
                feignProjectUtil.setProductPictureList(picList);
                feignProjectUtil.setProductAttrList(attrList);

                result = productService.updateProduct(feignProjectUtil);
            }
        } catch (BusinessException e) {
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    //生成数字SPU唯一值
    public  String getNumberForPK(String prefixStr,String index){

            String id=prefixStr+index;

            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

            String temp = sf.format(new Date());

            int random=(int) (Math.random()*10000);

            if(random<1000) {

            String random1 = "0"+random;

            id=id+temp+random1;

            }

            else { id=id+temp+random; }

            return id;

        }
    private List<ProductGoods> processGoods(HttpServletRequest request) {
        String skunumStr = request.getParameter("skunum");
        if (isNull(skunumStr)) {
            //启用规格，但没有生成货品
            throw new BusinessException("请选择一个规格");
        }

        Integer skunum = Integer.valueOf(skunumStr);
        List<ProductGoods> goodslist = new ArrayList<>(skunum);

        for (int i = 0; i < skunum; i++) {
            //最多两个规格
            String attrid1 = request.getParameter("normAttrId_" + i + "_0");
            String attrid2 = request.getParameter("normAttrId_" + i + "_1");

            String normname1 = request.getParameter("normName_" + i + "_0");
            String normname2 = request.getParameter("normName_" + i + "_1");

            String normvalue1 = request.getParameter("normValue_" + i + "_0");
            String normvalue2 = request.getParameter("normValue_" + i + "_1");

            String sku = request.getParameter("inventory_details_sku_" + i);
            String stock = request.getParameter("inventory_details_stock_" + i);
            String pprice = request.getParameter("inventory_details_pprice_" + i);
            String mprice = request.getParameter("inventory_details_mprice_" + i);
            String state = request.getParameter("goods_enable_" + i);
            String weight = request.getParameter("inventory_details_weight_" + i);
            String length = request.getParameter("inventory_details_length_" + i);
            String width = request.getParameter("inventory_details_width_" + i);
            String height = request.getParameter("inventory_details_height_" + i);

            ProductGoods pg = new ProductGoods();

            //规格属性值ID
            if (isNull(attrid2) && isNull(normname2) && isNull(normvalue2)) {
                pg.setNormAttrId(attrid1);
                pg.setNormName(normname1 + "," + normvalue1);
            } else {
                pg.setNormAttrId(attrid1 + "," + attrid2);
                pg.setNormName(normname1 + "," + normvalue1 + ";" + normname2 + "," + normvalue2);
            }

            pg.setSku(sku);
            //库存
            if (Integer.valueOf(stock) < 0) {
                throw new BusinessException("库存输入有误,请重新输入");
            }
            pg.setProductStock(Integer.valueOf(stock));
            try {
                BigDecimal pcprice = new BigDecimal(pprice.trim());
                pg.setMallPcPrice(pcprice);//PC价格
            } catch (Exception e) {
                throw new BusinessException("PC价格输入有误,请重新输入");
            }
            try {
                BigDecimal mobprice = new BigDecimal(mprice.trim());
                pg.setMallMobilePrice(mobprice);//mobile价格
            } catch (Exception e) {
                throw new BusinessException("mobile价格输入有误,请重新输入");
            }

            try {
                BigDecimal weightdb = new BigDecimal(weight.trim());
                pg.setWeight(weightdb);//mobile价格
            } catch (Exception e) {
                throw new BusinessException("重量输入有误,请重新输入");
            }

            if (!StringUtils.isEmpty(length)) {
                pg.setLength(Integer.parseInt(length));
            } else {
                pg.setLength(0);
            }

            if (!StringUtils.isEmpty(width)) {
                pg.setWidth(Integer.parseInt(width));
            } else {
                pg.setWidth(0);
            }

            if (!StringUtils.isEmpty(height)) {
                pg.setHeight(Integer.parseInt(height));
            } else {
                pg.setHeight(0);
            }

            if (!isNull(state) && Integer.valueOf(state) == ProductGoods.ENABLE) {
                pg.setState(ProductGoods.ENABLE);
            } else {
                pg.setState(ProductGoods.DISABLE);
            }

            //默认值
            pg.setProductStockWarning(-1);
            pg.setActualSales(0);

            goodslist.add(pg);
        }
        return goodslist;
    }

    @SuppressWarnings("deprecation")
    private String buildImgPath(HttpServletRequest request) {
        String path = "upload";
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        path = request.getRealPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        return path;
    }

    @RequestMapping(value = "addsuccess", method = { RequestMethod.GET })
    public String createSuc(HttpServletRequest request) {
        return baseUrl + "addsuccess";
    }

    /**
     * spu重复校验
     * @param request
     * @param response
     * @param roleCode
     */
    @RequestMapping(value = "validateSPU", method = { RequestMethod.POST })
    public void validateSPU(HttpServletRequest request, HttpServletResponse response,
                            String productCode) {
        response.setContentType("text/plain");
        boolean isValid = true;
        PrintWriter pw = null;
        JSONObject json = new JSONObject();

        try {
            //当前商家spu不能重复
            ServiceResult<Boolean> serviceResult = productService
                .isUnique(WebSellerSession.getSellerUser(request).getSellerId(), productCode, 0);
            if (!serviceResult.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                    throw new RuntimeException(serviceResult.getMessage());
                } else {
                    throw new BusinessException(serviceResult.getMessage());
                }
            }
            isValid = serviceResult.getResult();

            pw = response.getWriter();
        } catch (Exception e) {
            isValid = false;
            if (e instanceof BusinessException) {
                log.error(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
        json.put("valid", isValid);
        pw.print(json);
    }

    /**
     * sku重复校验
     * @param request
     * @param response
     * @param roleCode
     */
    @RequestMapping(value = "validateSKU", method = { RequestMethod.POST })
    public void validateSKU(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        boolean isValid = true;
        PrintWriter pw = null;
        JSONObject json = new JSONObject();

        try {
            Iterator<Entry<String, String[]>> params = request.getParameterMap().entrySet()
                .iterator();
            Pattern r = Pattern.compile("inventory_details_sku_[0-9]+");
            String sku = null;
            while (params.hasNext()) {
                Entry<String, String[]> entry = params.next();
                if (r.matcher(entry.getKey()).matches()) {
                    sku = entry.getValue()[0];
                    log.debug("匹配参数：" + entry.getKey());
                    break;
                }
            }
            //当前商家sku不能重复
            ServiceResult<Boolean> serviceResult = productGoodsService
                .isUnique(WebSellerSession.getSellerUser(request).getSellerId(), sku);
            if (!serviceResult.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                    throw new RuntimeException(serviceResult.getMessage());
                } else {
                    throw new BusinessException(serviceResult.getMessage());
                }
            }
            isValid = serviceResult.getResult();

            pw = response.getWriter();
        } catch (Exception e) {
            isValid = false;
            if (e instanceof BusinessException) {
                log.error(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
        json.put("valid", isValid);
        pw.print(json);
    }

    /**
     * 商品详情
     * @param request
     * @param response
     * @param dataMap
     * @param productId
     * @return
     */
    @RequestMapping(value = "details")
    public String details(HttpServletRequest request, HttpServletResponse response,
                          Map<String, Object> dataMap, Integer productId) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        if (productId == null || productId == 0) {
            dataMap.put("info", "商品id不能为空，请重试");
            return "seller/500";
        }

        //获取商品信息
        ServiceResult<Product> productResult = productService.getProductById(productId);
        if (!productResult.getSuccess() || productResult.getResult() == null) {
            dataMap.put("info", "获取商品信息失败，请重试");
            return "seller/500";
        }
        Product product = productResult.getResult();

        if (!product.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        //设置商品分类名称
        ServiceResult<ProductCate> productCateResult = productCateService
            .getProductCateById(product.getProductCateId());
        if (!productCateResult.getSuccess() || productCateResult.getResult() == null) {
            dataMap.put("info", "获取商品对应的分类信息失败，请重试");
            return "seller/500";
        }
        product.setProductCateName(productCateResult.getResult().getName());

        //设置商品品牌名称
        ServiceResult<ProductBrand> productBrandResult = productBrandService
            .getById(product.getProductBrandId());
        if (!productBrandResult.getSuccess() || productBrandResult.getResult() == null) {
            dataMap.put("info", "获取商品品牌信息失败，请重试");
            return "seller/500";
        }
        product.setProductBrandName(productBrandResult.getResult().getName());

        //设置商品商家分类信息
        ServiceResult<SellerCate> sellerCateResult = sellerCateService
            .getSellerCateById(product.getSellerCateId());
        if (!sellerCateResult.getSuccess() || sellerCateResult.getResult() == null) {
            dataMap.put("info", "获取商家分类信息失败，请重试");
            return "seller/500";
        }
        product.setSellerCateName(sellerCateResult.getResult().getName());

        //获取商品图片信息
        ServiceResult<List<ProductPicture>> productPictureResult = productPictureService
            .getProductPictureByProductId(productId);
        if (!productPictureResult.getSuccess() || productPictureResult.getResult() == null) {
            dataMap.put("info", "获取商品图片信息失败，请重试");
            return "seller/500";
        }

        if (!StringUtil.isEmpty(product.getDescription(), true)) {
            product.setDescription(product.getDescription()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }

        if (product.getTransportType() != null && product.getTransportId() != null) {
            //设置商品运费模板名称
            ServiceResult<SellerTransport> sellerTransportResult = sellerTransportService
                .getSellerTransportById(product.getTransportId());
            if (!sellerTransportResult.getSuccess() || sellerTransportResult.getResult() == null) {
                dataMap.put("info", "获取运费模板错误，请重试");
                return "admin/500";
            }
            product.setSellerTransportName(sellerTransportResult.getResult().getTransName());
        }
        dataMap.put("pro", product);
        dataMap.put("proPic", productPictureResult.getResult());
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        return "seller/product/pdt/productdetails";
    }
}
