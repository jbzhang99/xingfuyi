package com.yixiekeji.web.controller.seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.ISellerCateService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;

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
@RequestMapping("/store")
public class SellerCateController extends BaseController {

    @Resource
    private ISellerCateService   sellerCateService;
    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private ISellerService       sellerService;

    //    /**
    //     * 店铺分类
    //     * @param request
    //     * @param response
    //     * @param dataMap
    //     * @param sellerId
    //     * @return
    //     */
    //    @RequestMapping(value = "/cateList.html", method = { RequestMethod.GET })
    //    public @ResponseBody HttpJsonResult<List<SellerCate>> getShopCateList(HttpServletRequest request,
    //                                                                          HttpServletResponse response,
    //                                                                          Map<String, Object> dataMap,
    //                                                                          int sellerId) {
    //
    //        HttpJsonResult<List<SellerCate>> jsonResult = new HttpJsonResult<List<SellerCate>>();
    //        ServiceResult<List<SellerCate>> serviceResult = sellerCateService
    //            .getOnuseSellerCate(sellerId);
    //        if (!serviceResult.getSuccess()) {
    //            throw new BusinessException(serviceResult.getMessage());
    //        }
    //        List<SellerCate> sellerCates = serviceResult.getResult();
    //
    //        ServiceResult<Seller> sellerResult = sellerService.getSellerById(sellerId);
    //        if (!sellerResult.getSuccess()) {
    //            throw new BusinessException(sellerResult.getMessage());
    //        }
    //        Seller seller = sellerResult.getResult();
    //        dataMap.put("seller", seller);
    //        dataMap.put("cateList", sellerCates);
    //        return "h5/seller/sellercate";
    //    }
    @RequestMapping(value = "/cateSeller.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Seller> cateSeller(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           Map<String, Object> dataMap,
                                                           Integer sellerId) throws Exception {

        HttpJsonResult<Seller> jsonResult = new HttpJsonResult<Seller>();
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
            jsonResult.setMessage(sellerResult.getMessage());
        }

        jsonResult.setData(seller);
        return jsonResult;
    }

    @RequestMapping(value = "/cateList.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerCate>> getShopCateList(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          Map<String, Object> dataMap,
                                                                          int sellerId) {

        HttpJsonResult<List<SellerCate>> jsonResult = new HttpJsonResult<List<SellerCate>>();
        ServiceResult<List<SellerCate>> serviceResult = sellerCateService
            .getOnuseSellerCate(sellerId);
        if (!serviceResult.getSuccess()) {
            throw new BusinessException(serviceResult.getMessage());
        }
        List<SellerCate> sellerCates = serviceResult.getResult();

        ServiceResult<Seller> sellerResult = sellerService.getSellerById(sellerId);
        if (!sellerResult.getSuccess()) {
            throw new BusinessException(sellerResult.getMessage());
        }
        jsonResult.setData(sellerCates);
        return jsonResult;
    }

    /**
     * 拼装URL
     * @param request
     * @param response
     * @param sellerId
     * @param cateId
     * @return
     */
    @RequestMapping(value = "/cate/{cateId}.html", method = RequestMethod.GET)
    public ModelAndView cate(HttpServletRequest request, HttpServletResponse response, int sellerId,
                             @PathVariable String cateId) {

        ServiceResult<SellerCate> result = sellerCateService
            .getSellerCateById(ConvertUtil.toInt(cateId, 0));

        if (result.getSuccess()) {
            SellerCate sellerCate = result.getResult();
            if (sellerCate == null) {
                if (log.isInfoEnabled()) {
                    log.info("[SellerCateController]:获取分类时，分类不存在或已删除，已跳转至错误页面。当前页面访问路径："
                             + request.getRequestURL() + "?" + request.getQueryString()
                             + ";页面referer:" + request.getHeader("referer"));
                }
                return new ModelAndView("redirect:/error.html");
            }
            String urlPath = urlProductList(sellerId, sellerCate.getId(), 1, 0) + ".html";
            return new ModelAndView("redirect:/store/" + urlPath);
        } else {
            log.error(
                "[SellerCateController]:根据分类ID获取分类时Called Service info:ProductCateService.getProductCateById()");
            return new ModelAndView("redirect:/error.html");
        }
    }

    /**
     * 根据店铺分类查看商品列表
     * @param visitPath
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/1-{visitPath}.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<Map<String, Object>> productList(@PathVariable String visitPath,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Map<String, Object> stack) {

        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength < 4) { //长度小于4url错误
            jsonResult.setMessage("数据不匹配");
        }

        int sellerId = ConvertUtil.toInt(arrVisitPath[0], 0); //店铺ID
        //stack.put("sellerId", sellerId);

        int cateId = ConvertUtil.toInt(arrVisitPath[1], 0); //分类
        //stack.put("cateId", cateId);

        int page = ConvertUtil.toInt(arrVisitPath[2], 1);//分页
        // stack.put("pageNumber", page);

        int sort = ConvertUtil.toInt(arrVisitPath[3], 0);//排序
        stack.put("sort", sort);

        List<String> listFilters = new ArrayList<String>();
        for (int i = 4; i < arrVisitPath.length; i++) {
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

        StringBuilder sbUrlPathAll = new StringBuilder(
            urlProductList(sellerId, cateId, page, sort));
        if (sbUrlPath.toString() != null && !"".equals(sbUrlPath.toString())) {
            sbUrlPathAll.append("-");
            sbUrlPathAll.append(sbUrlPath.toString());
        }

        stack.put("urlPath", sbUrlPathAll.toString());

        // 查询店铺分类
        ServiceResult<SellerCate> sellerResult = sellerCateService.getSellerCateById(cateId);
        SellerCate sellerCate = sellerResult.getResult();
        stack.put("sellerCate", sellerCate);

        // 查询商品
        int start = 0, size = 0;
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, page);
        start = pager.getStart();
        size = pager.getPageSize();

        ServiceResult<List<Product>> result = productFrontService
            .getProductListBySellerCateId(start, size, cateId, sellerId, sort);
        List<Product> productList = result.getResult();
        stack.put("producListVOs", productList);

        stack.put("pagesize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        jsonResult.setData(stack);
        return jsonResult;
    }

    /**
     * 拼装列表页访问的URL
     * @param sellerId 店铺
     * @param cateId 分类
     * @param page 页码
     * @param sort 排序
     * @return
     */
    private String urlProductList(int sellerId, int cateId, int page, int sort) {
        StringBuilder sb = new StringBuilder();
        sb.append("1-");
        sb.append(sellerId);

        sb.append("-");
        sb.append(cateId);

        sb.append("-");
        sb.append(page);

        sb.append("-");
        sb.append(sort);

        return sb.toString();
    }

    /**
     * 返回商品列表页 json 数据
     * @param visitPath
     * @param request
     * @param response
     * @param stack
     * @return
     */
    @RequestMapping(value = "/listjson-1-{visitPath}.html", method = RequestMethod.GET)
    public @ResponseBody HttpJsonResult<List<Product>> cateTop(@PathVariable String visitPath,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> stack) {
        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();

        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        //长度小于4url错误
        if (arrVisitPathLength < 4) {
            return jsonResult;
        }

        int sellerId = ConvertUtil.toInt(arrVisitPath[0], 0); //店铺ID
        int cateId = ConvertUtil.toInt(arrVisitPath[1], 0); //分类

        int page = ConvertUtil.toInt(arrVisitPath[2], 1);//分页

        int sort = ConvertUtil.toInt(arrVisitPath[3], 0);//排序

        List<String> listFilters = new ArrayList<String>();
        for (int i = 4; i < arrVisitPath.length; i++) {
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

        // 查询商品
        int start = 0, size = 0;
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, page);
        start = pager.getStart();
        size = pager.getPageSize();

        ServiceResult<List<Product>> result = productFrontService
            .getProductListBySellerCateId(start, size, cateId, sellerId, sort);
        List<Product> listProduct = result.getResult();

        jsonResult.setData(listProduct);
        jsonResult.setTotal(listProduct.size());
        return jsonResult;
    }

}
