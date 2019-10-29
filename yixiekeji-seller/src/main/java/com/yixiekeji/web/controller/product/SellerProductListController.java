package com.yixiekeji.web.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.sale.ISaleScaleService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商品管理列表controller
 *                       
 * @Filename: SellerProductListController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/product")
public class SellerProductListController extends BaseController {
    @Resource
    private IProductService      productService;

    @Resource
    private IProductGoodsService productGoodsService;

    @Resource
    private ISaleScaleService    saleScaleService;

    private String               baseUrl = "seller/product/pdt/";

    //待售商品(刚创建、提交审核、审核通过、下架)
    @RequestMapping(value = "waitSale", method = { RequestMethod.GET })
    public String waitSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "1,2,3,4,7");//1、刚创建；2、提交审核；3、审核通过；4、申请驳回；7、下架
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listwaitsale";
    }

    @RequestMapping(value = "saleAll", method = { RequestMethod.GET })
    public String saleAll(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", "1,2,3,4,5,6,7");//1、刚创建；2、提交审核；3、审核通过；4、申请驳回；7、下架
        return baseUrl + "listall";
    }

    //在售商品(上架商品) 可以下架
    @RequestMapping(value = "onSale", method = { RequestMethod.GET })
    public String onSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "6");//6、上架；
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listonsale";
    }

    //已经删除商品(删除) 只读
    @RequestMapping(value = "delSale", method = { RequestMethod.GET })
    public String delSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("q_state", "5");//5、商品删除；
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "listdelsale";
    }

    /**
     * 商品列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> list(HttpServletRequest request,
                                                            Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        String state = request.getParameter("q_state1");
        if (!StringUtil.isEmpty(state)) {
            queryMap.put("q_state", state);
        }
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        queryMap.put("q_sellerId", WebSellerSession.getSellerUser(request).getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Product>> serviceResult = productService.pageProduct(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();
        List<Product> products = serviceResult.getResult();
        //查询分佣比例 
        if ("6".equals(queryMap.get("q_state"))) {
            Map<String, SaleScale> mapSaleScale = new HashMap<String, SaleScale>();
            ServiceResult<List<SaleScale>> serviceResultSales = saleScaleService.getSaleScales();
            if (serviceResultSales.getSuccess()) {
                List<SaleScale> saleScales = serviceResultSales.getResult();
                if (null != saleScales) {
                    for (SaleScale saleScale : saleScales) {
                        mapSaleScale.put(saleScale.getSellerId().toString(), saleScale);
                    }
                }
            }

            if (mapSaleScale.size() > 0) {
                for (Product product : products) {
                    if (null == product.getSaleScale1()) {
                        SaleScale saleScale = mapSaleScale.get(product.getSellerId().toString());
                        if (null != saleScale) {
                            product.setSaleScale1(saleScale.getSaleScale1());
                        }
                    }

                    if (null == product.getSaleScale2()) {
                        SaleScale saleScale = mapSaleScale.get(product.getSellerId().toString());
                        if (null != saleScale) {
                            product.setSaleScale2(saleScale.getSaleScale2());
                        }
                    }
                }
            }
        }

        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();
        jsonResult.setRows(products);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 根据商品ID查询货品
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list_goods", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductGoods>> listGoods(Integer productId,
                                                                      HttpServletRequest request,
                                                                      Map<String, Object> dataMap) {
        ServiceResult<List<ProductGoods>> serviceResult = productGoodsService
            .getGoodSByProductId(productId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<ProductGoods>> jsonResult = new HttpJsonResult<List<ProductGoods>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * 商品列表无分页
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "listnopage", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> listNoPage(HttpServletRequest request,
                                                                  Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<Product>> serviceResult = productService.pageProduct(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();
        jsonResult.setRows(serviceResult.getResult());
        return jsonResult;
    }

}
