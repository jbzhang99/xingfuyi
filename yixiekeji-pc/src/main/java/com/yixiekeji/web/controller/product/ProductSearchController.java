package com.yixiekeji.web.controller.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.CookieHelper;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.search.SearchLogs;
import com.yixiekeji.entity.search.SearchSetting;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.search.ISearchLogsService;
import com.yixiekeji.service.search.ISearchSettingService;
import com.yixiekeji.vo.search.SearchProductVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 搜索商品
 *                       
 * @Filename: ProductSearchController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductSearchController extends BaseController {

    @Resource
    private IProductFrontService  productFrontService;

    @Resource
    private ISearchSettingService searchSettingService;

    @Resource
    private ISearchLogsService    searchLogsService;

    @Resource
    private DomainUrlUtil         domainUrlUtil;

    private final static int      PAGE_NUMBER = 20;

    public SolrClient getSolrClient() {
        String solrUrl = domainUrlUtil.getSearchSolrUrl();
        String solrServer = domainUrlUtil.getSearchSolrServer();
        return new HttpSolrClient(solrUrl + "/" + solrServer);
    }

    @RequestMapping(value = "/search.html", method = RequestMethod.GET)
    public String search(HttpServletRequest request, HttpServletResponse responses, String keyword,
                         Map<String, Object> stack) {
        keyword = StringUtil.trim(StringUtil.nullSafeString(keyword));
        if ("".equals(keyword)) {
            List<String> keywords = getKeywords();
            if (keywords.size() > 0) {
                keyword = keywords.get(0);
            } else {
                keyword = "yixiekeji";
            }
        } else {
            //记录搜索日志
            Cookie cookie = CookieHelper.getCookieByName(request, domainUrlUtil.getCookieName());
            String cookieValue = cookie == null ? "" : cookie.getValue();

            String memberId = "0";
            Member member = WebFrontSession.getLoginedUser(request, responses);
            if (member != null && member.getId() != null) {
                memberId = member.getId().toString();
            }

            if (keyword != null && !"".equals(keyword.trim())) {
                SearchLogs searchLogs = new SearchLogs();
                searchLogs.setKeyword(keyword);
                searchLogs.setSiteCookie(cookieValue);
                searchLogs.setIp(WebUtil.getIpAddr(request));
                searchLogs.setMemberId(ConvertUtil.toInt(memberId, ConvertUtil.toInt(memberId, 0)));
                searchLogsService.saveSearchLogs(searchLogs);
            }
        }
        keyword = StringUtil.stringFilterSpecial(keyword);

        ServiceResult<SearchSetting> serviceResult = searchSettingService
            .getSearchSettingById(ConstantsEJS.SEARCHSETTINGID);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        //关键词过滤
        SearchSetting searchSetting = serviceResult.getResult();
        if (searchSetting.getKeywordFilter().intValue() == SearchSetting.KEYWORD_FILTER_2) {
            ServiceResult<Integer> serviceResultKeyword = searchSettingService
                .getSearchKeywordsByKeyword(keyword);
            int countKeyword = serviceResultKeyword.getResult();
            if (countKeyword > 0) {
                return "redirect:/error.html";
            }
        }

        //根据关键词查询分类，能够查询到跳转到三级列表页
        ServiceResult<ProductCate> productCateResult = productFrontService
            .getProductCateByName(keyword);
        if (productCateResult.getSuccess()) {
            ProductCate productCate = productCateResult.getResult();
            if (productCate != null) {
                if (productCate.getPid().intValue() != 0
                    && productCate.getPath().split("/").length == 3) {
                    return "redirect:/cate/" + productCate.getId() + ".html";
                }
            }
        }

        //更具关键词查询品牌
        ServiceResult<ProductBrand> productBrandResult = productFrontService
            .getProductBrandByName(keyword);
        if (productCateResult.getSuccess()) {
            ProductBrand productBrand = productBrandResult.getResult();
            if (productBrand != null) {
                return "redirect:/brand/" + productBrand.getId() + ".html";
            }
        }

        String searchKeyword = "(" + keyword + ")";
        int count = 0;
        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();

        //        int start = 0, size = 0;
        //        PaginationUtil page = WebUtil.handlerPaginationUtil(request);
        //        start = (page.getNum() - 1) * NUMBER;
        //        size = NUMBER;

        //查询商家的商品
        int start = 0, size = 0;
        PagerInfo pager = WebUtil.handlerPagerInfo(request, stack, PAGE_NUMBER);
        start = pager.getStart();
        size = pager.getPageSize();

        String searchIndexAssemblingString = SearchProductVO.searchIndexAssembling(searchKeyword);
        query.setQuery(searchIndexAssemblingString);

        query.set("start", start);
        query.set("rows", size);

        query.setHighlight(true);
        query.setHighlightSimplePre("<font color=\"red\">");
        query.setHighlightSimplePost("</font>");
        query.setParam("hl.fl", SearchProductVO.NAME1_);

        List<Product> products = new ArrayList<Product>();
        QueryResponse response = null;
        try {
            response = client.query(query);
            SolrDocumentList docs = response.getResults();
            count = new Integer(docs.getNumFound() + "");
            for (SolrDocument doc : docs) {
                Integer id = (Integer) doc.getFieldValue(SearchProductVO.ID_);

                Product product = new Product();
                product.setId(id);

                Object object = response.getHighlighting().get(id.toString())
                    .get(SearchProductVO.NAME1_);
                if (object != null) {
                    product.setName1(object.toString().replace("[", "").replace("]", ""));
                } else {
                    product.setName1(doc.getFieldValue(SearchProductVO.NAME1_).toString()
                        .replace("[", "").replace("]", ""));
                }

                product.setMasterImg(doc.getFieldValue(SearchProductVO.MASTERIMG_).toString());
                product.setMallPcPrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MALLPCPRICE_).toString()));
                product.setProductStock(
                    new Integer(doc.getFieldValue(SearchProductVO.PRODUCTSTOCK_).toString()));
                product.setCommentsNumber(
                    new Integer(doc.getFieldValue(SearchProductVO.COMMENTSNUMBER_).toString()));
                product.setSellerId(
                    new Integer(doc.getFieldValue(SearchProductVO.SELLERID_).toString()));

                products.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder url = new StringBuilder(request.getRequestURI());
        if (keyword != null) {
            url.append("?keyword=");
            url.append(keyword);
        }

        PaginationUtil pm = new PaginationUtil(PAGE_NUMBER, String.valueOf(pager.getPageIndex()),
            count, url.toString());

        stack.put("page", pm);
        stack.put("count", count);
        stack.put("keyword", keyword);
        stack.put("producListVOs", products);
        return "front/product/productsearchlist";
    }

    /**
     * 根据分类查询列表页推荐的头部商品
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "searchTop.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> searchTop(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> dataMap) {
        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();

        ServiceResult<List<Product>> serviceResult = productFrontService.getProductSearchByTop();

        List<Product> productsTop = new ArrayList<Product>();
        if (serviceResult.getSuccess()) {
            productsTop = serviceResult.getResult();
        }

        jsonResult.setData(productsTop);
        jsonResult.setTotal(productsTop.size());
        return jsonResult;
    }

    /**
     * 根据分类查询列表页推荐的左边的商品
     * @param cateId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "searchLeft.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> searchLeft(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Map<String, Object> dataMap) {
        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();

        ServiceResult<List<Product>> serviceResult = productFrontService.getProductSearchByLeft();

        List<Product> productsTop = new ArrayList<Product>();
        if (serviceResult.getSuccess()) {
            productsTop = serviceResult.getResult();
        }

        jsonResult.setData(productsTop);
        jsonResult.setTotal(productsTop.size());
        return jsonResult;
    }

    /**
     * 异步获取关键词
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "searchKeyword.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<String>> cateTop(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              Map<String, Object> dataMap) {
        HttpJsonResult<List<String>> jsonResult = new HttpJsonResult<List<String>>();
        List<String> keywords = getKeywords();

        jsonResult.setData(keywords);
        jsonResult.setTotal(keywords.size());
        return jsonResult;
    }

    /**
     * 获取设置的查询关键字
     * @return
     */
    private List<String> getKeywords() {
        ServiceResult<SearchSetting> serviceResult = searchSettingService
            .getSearchSettingById(ConstantsEJS.SEARCHSETTINGID);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        SearchSetting searchSetting = serviceResult.getResult();
        String keyword = searchSetting.getKeyword();

        keyword = StringUtil.trim(StringUtil.nullSafeString(keyword));

        List<String> keywords = new ArrayList<String>();
        if (!"".equals(keyword)) {
            String[] strings = keyword.split(",");
            for (String string : strings) {
                string = StringUtil.trim(StringUtil.nullSafeString(string));
                if (!"".equals(string)) {
                    keywords.add(string);
                }
            }
        }
        return keywords;
    }
}
