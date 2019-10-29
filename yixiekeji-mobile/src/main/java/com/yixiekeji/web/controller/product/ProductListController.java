package com.yixiekeji.web.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.coupon.Coupon;
import com.yixiekeji.entity.full.ActFull;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.single.ActSingle;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.promotion.IActFullService;
import com.yixiekeji.service.promotion.IActSingleService;
import com.yixiekeji.service.promotion.ICouponService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.vo.product.ProductTypeAttrVO;
import com.yixiekeji.vo.search.SearchProductVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 商品列表页
 *                       
 * @Filename: ProductListController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductListController extends BaseController {

    @Resource
    private IProductFrontService productFrontService;

    @Resource
    private IProductCateService  productCateService;
    @Resource
    private DomainUrlUtil        domainUrlUtil;
    @Resource
    private ISellerService       sellerService;
    @Resource
    private IActSingleService    actSingleService;
    @Resource
    private IActFullService      actFullService;
    @Resource
    private ICouponService       couponService;

    /**
     * 列表页第一次跳转，验证cate并组织URL，有利于百度SEO
     * @param request
     * @param response
     * @param cateId 分类ID
     * @return
     */
    @RequestMapping(value = "/cate/{cateId}.html", method = RequestMethod.GET)
    public ModelAndView cate(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable String cateId) {
        ServiceResult<ProductCate> prodCatesResult = productCateService
            .getProductCateById(ConvertUtil.toInt(cateId, 0));

        if (prodCatesResult.getSuccess()) {
            ProductCate prodCate = prodCatesResult.getResult();
            if (prodCate == null) {
                if (log.isInfoEnabled()) {
                    log.info("[ProductListController]:获取分类时，分类不存在或已删除，已跳转至错误页面。当前页面访问路径："
                             + request.getRequestURL() + "?" + request.getQueryString()
                             + ";页面referer:" + request.getHeader("referer"));
                }
                return new ModelAndView("redirect:/error.html");
            }
            String urlPath = urlProductList(prodCate.getId(), 0, 0, 0, 0, 0) + ".html";
            return new ModelAndView("redirect:/" + urlPath);
        } else {
            log.error(
                "[ProductListController]:根据分类ID获取分类时Called Service info:ProductCateService.getProductCateById()");
            return new ModelAndView("redirect:/error.html");
        }
    }

    /**
     * 列表页跳转，去除与更换查询条件，然后跳转到列表页
     * @param request
     * @param response
     * @param urlPath
     * @param filter
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/cate.html", method = RequestMethod.GET)
    public ModelAndView cate(HttpServletRequest request, HttpServletResponse response,
                             String urlPath, String filter, String brandIdFilder,
                             String sortFilter) {
        StringBuilder sbUrlPath = new StringBuilder();
        if (filter != null && !"".equals(filter)) {
            sbUrlPath.append(urlPath.replace(filter, ""));
            sbUrlPath.append(".html");
        } else if (brandIdFilder != null && !"".equals(brandIdFilder)) {
            String[] arrVisitPath = urlPath.split("-");
            int arrVisitPathLength = arrVisitPath.length;
            if (arrVisitPathLength < 9) { //长度小于9url错误
                return new ModelAndView("redirect:/error.html");
            }
            int cateId = ConvertUtil.toInt(arrVisitPath[1], 0); //分类
            int page = ConvertUtil.toInt(arrVisitPath[2], 1);//分页
            int sort = ConvertUtil.toInt(arrVisitPath[3], 0);//排序
            int doSelf = ConvertUtil.toInt(arrVisitPath[4], 0);//自营非自营
            int store = ConvertUtil.toInt(arrVisitPath[5], 0);//有货无货
            //            int brandIdStr = ConvertUtil.toInt(arrVisitPath[6], 0);//品牌

            sbUrlPath.append(urlProductList(cateId, page, sort, doSelf, store, 0));

            for (int i = 9; i < arrVisitPath.length; i++) {
                sbUrlPath.append("-");
                sbUrlPath.append(arrVisitPath[i]);
            }
            sbUrlPath.append(".html");
        } else {
            sbUrlPath.append("error.html");
        }
        return new ModelAndView("redirect:/" + sbUrlPath.toString());
    }

    /**
     * 拼装列表页访问的URL
     * @param cateId 类别
     * @param number 分页
     * @param sort 排序
     * @param doSelf 自营非自营
     * @param store 有货无货
     * @param brandId 品牌
     * @return
     */
    private String urlProductList(int cateId, int number, int sort, int doSelf, int store,
                                  int brandId) {
        StringBuilder sb = new StringBuilder();
        sb.append("0-");
        sb.append(cateId);

        sb.append("-");
        sb.append(number);

        sb.append("-");
        sb.append(sort);

        sb.append("-");
        sb.append(doSelf);

        sb.append("-");
        sb.append(store);

        sb.append("-");
        sb.append(brandId);

        sb.append("-0-0-0");
        return sb.toString();
    }

    /**
     * 列表页访问主函数，为了SEO，URL为数字的拼装
     * 完整URL规则www.yixiekeji.com/0-cate-分页-排序-自营非自营－有货无货-品牌ID-0-0-0-
     *                 filter1-filter2....html<br/>
     *                 排序sort 0:默认排序；1销量；2评论；3价格从低到高；4、价格从高到低<br/>
     *                 自营非自营：0所有商品；1自营商品<br/>
     *                 有货无货：0所有商品；1有货商品
     * @param visitPath
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/0-{visitPath}.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Map<String, Object>> productList(@PathVariable String visitPath,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> stack) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength < 9) { //长度小于9url错误
            jsonResult.setMessage("解析url错误。");
            return jsonResult;
        }
        int cateId = ConvertUtil.toInt(arrVisitPath[0], 0); //分类
        stack.put("cateId", cateId);
        //存放参数
        //        Map<String, Object> mapCondition = new HashMap<String, Object>();

        int page = ConvertUtil.toInt(arrVisitPath[1], 1);//分页
        stack.put("pageIndex", page);

        int sort = ConvertUtil.toInt(arrVisitPath[2], 0);//排序
        stack.put("sort", sort);

        int doSelf = ConvertUtil.toInt(arrVisitPath[3], 0);//自营非自营
        stack.put("doSelf", doSelf);

        int store = ConvertUtil.toInt(arrVisitPath[4], 0);//有货无货
        stack.put("store", store);

        int brandId = ConvertUtil.toInt(arrVisitPath[5], 0);//品牌
        stack.put("brandId", brandId);

        List<String> listFilters = new ArrayList<String>();
        for (int i = 9; i < arrVisitPath.length; i++) {
            listFilters.add(arrVisitPath[i]);
        }

        StringBuilder sbUrlPath = new StringBuilder("");
        int listFiltersCount = listFilters.size();
        for (int i = 0; i < listFiltersCount; i++) {
            sbUrlPath.append(listFilters.get(i));
            if (listFiltersCount != (i + 1)) {
                sbUrlPath.append("-");
            }
        }
        String filterAll = sbUrlPath.toString();

        StringBuilder sbUrlPathAll = new StringBuilder(
            urlProductList(cateId, page, sort, doSelf, store, brandId));
        if (sbUrlPath.toString() != null && !"".equals(sbUrlPath.toString())) {
            sbUrlPathAll.append("-");
            sbUrlPathAll.append(sbUrlPath.toString());
        }

        stack.put("urlPath", sbUrlPathAll.toString());

        //        productFrontService.getProducts(cateId, mapCondition, stack);

        //查询三级分类、二级分类、一级分类，返回三级分类
        ProductCate productCate = findCateAll(cateId, stack);
        if (productCate == null) {
            jsonResult.setMessage("获取商品分类失败。");
            return jsonResult;
        }

        //取得类型
        ServiceResult<ProductType> serviceResult = productFrontService
            .getProductTypeById(productCate.getProductTypeId());
        ProductType productType = serviceResult.getResult();

        //查询出类型下面关联的品牌，并对首字母进行排序
        findBrandAll(stack, productType.getProductBrandIds());

        //查询出类型下面关联的查询属性
        ServiceResult<List<ProductTypeAttr>> serviceResultProductTypeAttr = productFrontService
            .getByTypeIdAndQuery(productType.getId());
        List<ProductTypeAttr> productTypeAttrs = serviceResultProductTypeAttr.getResult();

        //组装类型查询Map对象，键是ID，值是查询的对象
        Map<String, ProductTypeAttr> typeAttrMap = new HashMap<String, ProductTypeAttr>();
        for (ProductTypeAttr productTypeAttr : productTypeAttrs) {
            typeAttrMap.put(productTypeAttr.getId().toString(), productTypeAttr);
        }

        //根据品牌筛选，查询出来品牌相关信息，该品牌ID是选中的品牌ID
        //        if (brandId > 0) {
        //            ServiceResult<ProductBrand> serviceResultsProductBrand = productFrontService
        //                .getProductBrandById(brandId);
        //            if (serviceResultsProductBrand.getSuccess()) {
        //                ProductBrand productBrand = serviceResultsProductBrand.getResult();
        //                stack.put("brandName", productBrand.getName());
        //            }
        //        }

        if (brandId != 0) {
            List<ProductBrand> ProductBrands = (List<ProductBrand>) stack.get("productBrands");
            for (ProductBrand productBrand : ProductBrands) {
                if (productBrand.getId().intValue() == brandId) {
                    stack.put("productBrandName", productBrand.getName());
                    break;
                }
            }
        }

        //查询商品
        Map<String, String> attrFilter = new HashMap<String, String>();
        String searchQuery = this.searchIndexAssembling(cateId, doSelf, store, brandId, filterAll,
            typeAttrMap, attrFilter);
        stack.put("productTypeAttrWhereAlls", attrFilter);

        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();
        SolrQuery queryCount = new SolrQuery();
        queryCount.set("q", "*:*");
        queryCount.setQuery(searchQuery);
        query.setQuery(searchQuery);

        //solr分页
        if (page < 1) {
            page = 1;
        }
        int startNumber = (page - 1) * ConstantsEJS.DEFAULT_PAGE_SIZE;
        query.set("start", startNumber);
        query.set("rows", ConstantsEJS.DEFAULT_PAGE_SIZE);

        // solr 排序
        if (sort == 0) {
            query.set("sort", SearchProductVO.SALESNUM_ + " desc");
        } else if (sort == 1) {
            query.set("sort", SearchProductVO.SALESNUM_ + " desc");
        } else if (sort == 2) {
            query.set("sort", SearchProductVO.COMMENTSNUMBER_ + " desc");
        } else if (sort == 3) {
            query.set("sort", SearchProductVO.MALLPCPRICE_ + " asc");
        } else if (sort == 4) {
            query.set("sort", SearchProductVO.MALLPCPRICE_ + " desc");
        }
        query.setHighlight(false);

        List<Product> products = new ArrayList<Product>();
        QueryResponse responseSolr = null;
        QueryResponse responseSolrCount = null;
        int rowsCount = 0;
        try {
            responseSolr = client.query(query);
            responseSolrCount = client.query(queryCount);
            SolrDocumentList docs = responseSolr.getResults();
            long count = docs.getNumFound();
            stack.put("productSize", count);
            rowsCount = (int) responseSolrCount.getResults().getNumFound();
            for (SolrDocument doc : docs) {
                Integer id = (Integer) doc.getFieldValue(SearchProductVO.ID_);

                Product product = new Product();
                product.setId(id);

                product.setName1(doc.getFieldValue(SearchProductVO.NAME1_).toString()
                    .replace("[", "").replace("]", ""));

                product.setMasterImg(doc.getFieldValue(SearchProductVO.MASTERIMG_).toString());
                product.setMallPcPrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MALLPCPRICE_).toString()));
                product.setMalMobilePrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MALMOBILEPRICE_).toString()));
                product.setProductStock(
                    new Integer(doc.getFieldValue(SearchProductVO.PRODUCTSTOCK_).toString()));
                product.setCommentsNumber(
                    (Integer) doc.getFieldValue(SearchProductVO.COMMENTSNUMBER_));
                product.setSellerId((Integer) doc.getFieldValue(SearchProductVO.SELLERID_));

                product.setMarketPrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MARKETPRICE_).toString()));
                product.setActualSales(
                    new Integer(doc.getFieldValue(SearchProductVO.SALESNUM_).toString()));

                products.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Integer> mapFull = new HashMap<String, Integer>();
        Map<String, Integer> mapCoupon = new HashMap<String, Integer>();
        Map<String, String> mapSellerName = new HashMap<String, String>();
        for (Product product : products) {
            actAllProduct(mapSellerName, mapFull, mapCoupon, product);
        }
        stack.put("producListVOs", products);

        //拼装列表页查询条件的VO对象
        installProductAttr(stack, productTypeAttrs, listFilters);

        stack.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        stack.put("rowsCount", rowsCount);

        jsonResult.setData(stack);
        return jsonResult;
    }

    /**
     * 搜索条件的拼装
     * @param productTypeAttrValueSet 
     * @param searchKeyword
     * @return
     */
    private String searchIndexAssembling(Integer cateId, Integer doSelf, Integer store,
                                         Integer brandId, String filterAll,
                                         Map<String, ProductTypeAttr> typeAttrMap,
                                         Map<String, String> attrFilter) {
        StringBuilder sb = new StringBuilder();

        // 类目ID
        sb.append(SearchProductVO.PRODUCTCATEID_);
        sb.append(":");
        sb.append(cateId);
        if (brandId > 0) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.BRANDID_);
            sb.append(":");
            sb.append(brandId);
        }
        // 是否自营
        if (doSelf.intValue() == 1) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.ISSELF_);
            sb.append(":");
            sb.append(doSelf);
        }
        // 是否有货
        if (store.intValue() == 1) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.PRODUCTSTOCK_);
            sb.append(":[1 TO *]");
        }
        // 检索属性封装
        if (!StringUtil.isEmpty(filterAll, true)) {
            // 所有查询条件：1_1-2_2-3_3，1_1表示一个查询属性和值下标
            String[] filterAlls = filterAll.split("-");
            if (filterAlls.length > 0) {
                for (String idAndIndexStr : filterAlls) {
                    String[] idAndIndex = idAndIndexStr.split("_");
                    if (idAndIndex.length == 2) {
                        String typeAttrId = idAndIndex[0];
                        String typeAttrValueIndexStr = idAndIndex[1];
                        int typeAttrValueIndex = ConvertUtil.toInt(typeAttrValueIndexStr, 0);
                        // 该查询条件对应的属性对象
                        ProductTypeAttr productTypeAttr = typeAttrMap.get(typeAttrId);
                        if (productTypeAttr != null
                            && !StringUtil.isEmpty(productTypeAttr.getValue(), true)) {
                            // 根据下标找到对应的实际值

                            String value = productTypeAttr.getValue();
                            String[] values = value.split(",");
                            if (typeAttrValueIndex < values.length) {
                                String filterValue = values[typeAttrValueIndex];
                                // 把对应的值加入到查询条件中
                                sb.append(" AND ");
                                sb.append(SearchProductVO.ATTRVALUES_);
                                sb.append(":");
                                sb.append("\"," + filterValue + ",\"");
                                attrFilter.put(idAndIndexStr,
                                    productTypeAttr.getName() + ":" + filterValue);
                                typeAttrMap.remove(typeAttrId);
                            }
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    private SolrClient getSolrClient() {
        String solrUrl = domainUrlUtil.getSearchSolrUrl();
        String solrServer = domainUrlUtil.getSearchSolrServer();
        return new HttpSolrClient(solrUrl + "/" + solrServer);
    }

    /**
     * 查询分类下面所关联的品牌
     * @param stack
     * @param brandIds
     */
    private void findBrandAll(Map<String, Object> stack, String brandIds) {

        ServiceResult<List<ProductBrand>> serviceResults = productFrontService
            .getProductBrandByIds(brandIds);

        List<ProductBrand> productBrands = serviceResults.getResult();

        stack.put("productBrands", productBrands);
        List<String> productBrandNameFirsts = new ArrayList<String>();
        for (ProductBrand productBrand : productBrands) {
            if (!productBrandNameFirsts.contains(productBrand.getNameFirst())) {
                productBrandNameFirsts.add(productBrand.getNameFirst());
            }
        }
        Collections.sort(productBrandNameFirsts);
        stack.put("productBrandNameFirsts", productBrandNameFirsts);
    }

    /**
     * 取得列表页分类相关数据
     * @param cateId
     * @param stack
     * @return
     */
    private ProductCate findCateAll(Integer cateId, Map<String, Object> stack) {

        ServiceResult<ProductCate> serviceResult = productFrontService.getProductCateById(cateId);

        ProductCate productCate = serviceResult.getResult();

        if (productCate == null) {
            return null;
        }

        stack.put("productCate", productCate);
        if (productCate.getPid().intValue() != 0) {
            ServiceResult<ProductCate> serviceResultPid = productFrontService
                .getProductCateById(productCate.getPid());

            ProductCate productCatePid = serviceResultPid.getResult();
            stack.put("productCatePid", productCatePid);

            ServiceResult<List<ProductCate>> serviceResult3s = productFrontService
                .getProductCateByPid(productCate.getPid());

            List<ProductCate> productCate3s = serviceResult3s.getResult();
            productCate3s.remove(productCate);
            stack.put("productCate3s", productCate3s);

            if (productCatePid.getPid() != 0) {
                ServiceResult<ProductCate> serviceResultPidPid = productFrontService
                    .getProductCateById(productCatePid.getPid());

                ProductCate productCatePidPid = serviceResultPidPid.getResult();
                stack.put("productCatePidPid", productCatePidPid);

                ServiceResult<List<ProductCate>> serviceResult2s = productFrontService
                    .getProductCateByPid(productCatePid.getPid());

                List<ProductCate> productCate2s = serviceResult2s.getResult();
                productCate2s.remove(productCatePid);
                stack.put("productCate2s", productCate2s);
            }
        }
        return productCate;
    }

    /**
     * 拼装列表页查询条件的VO对象
     * @param stack
     * @param productTypeAttrs
     */
    private void installProductAttr(Map<String, Object> stack,
                                    List<ProductTypeAttr> productTypeAttrs,
                                    List<String> listFilters) {

        List<ProductTypeAttrVO> productTypeAttrVOs = new ArrayList<ProductTypeAttrVO>();

        for (ProductTypeAttr productTypeAttr : productTypeAttrs) {
            ProductTypeAttrVO productTypeAttrVO = new ProductTypeAttrVO();
            productTypeAttrVO.setId(productTypeAttr.getId());
            productTypeAttrVO.setName(productTypeAttr.getName());
            productTypeAttrVO.setValue(productTypeAttr.getValue());

            if (listFilters != null && listFilters.size() > 0) {
                for (int i = 0; i < listFilters.size(); i++) {
                    String whereAll_ = listFilters.get(i);
                    String[] whereAll_s = whereAll_.split("_");
                    if (whereAll_s.length == 2) {
                        int whereAll_0 = ConvertUtil.toInt(whereAll_s[0], 0);
                        int whereAll_1 = ConvertUtil.toInt(whereAll_s[1], 0);
                        if (productTypeAttr.getId().intValue() == whereAll_0) {
                            productTypeAttrVO.setIsChoice(1);//1表示此分类依据被选中
                            productTypeAttrVO.setIsChoiceIndex(whereAll_1); //选中的索引
                            productTypeAttrVO.setChoiceAll(whereAll_); //应该去掉的条件
                            break;
                        }
                    }
                }
            }

            String value = productTypeAttr.getValue();
            if (value != null && !"".equals(value)) {
                List<String> values = new ArrayList<String>();
                String[] str = value.split(",");
                for (int i = 0; i < str.length; i++) {
                    values.add(str[i]);
                }
                productTypeAttrVO.setValues(values);
            }
            productTypeAttrVOs.add(productTypeAttrVO);
        }
        stack.put("productTypeAttrVOsAll", productTypeAttrVOs);
    }

    
    /**
     * 商品活动的处理
     * @param mapFull
     * @param mapCoupon
     * @param product
     */
    private void actAllProduct(Map<String, String> mapSellerName, Map<String, Integer> mapFull,
                               Map<String, Integer> mapCoupon, Product product) {
       
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

        //加入商家
        String sellerName = mapSellerName.get(product.getSellerId().toString());
        if (sellerName != null) {
            product.setSeller(sellerName);
        } else {
            ServiceResult<Seller> sellerResult = sellerService.getSellerById(product.getSellerId());
            Seller seller = sellerResult.getResult();
            if(seller != null) {
            	product.setSeller(seller.getSellerName());
            	mapSellerName.put(product.getSellerId().toString(), seller.getSellerName());
            }
        }
    }
}
