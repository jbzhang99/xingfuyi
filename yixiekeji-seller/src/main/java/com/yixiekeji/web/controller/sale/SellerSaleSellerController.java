package com.yixiekeji.web.controller.sale;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleScaleProductLog;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.sale.ISaleScaleService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 设置三级分销
 *                       
 * @Filename: AdminSaleSellerController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "seller/sale/salescale")
public class SellerSaleSellerController extends BaseController {

    @Resource
    private ISellerService    sellerService;

    @Resource
    private ISaleScaleService saleScaleService;

    @Resource
    private IProductService   productService;

    /**
     * 设置商家三级分销
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        ServiceResult<Seller> resultSeller = sellerService
            .getSellerById(WebSellerSession.getSellerUser(request).getSellerId());
        Seller seller = resultSeller.getResult();

        String message = request.getParameter("message");
        if ("success".equals(message)) {
            dataMap.put("message", "操作成功");
        }

        ServiceResult<SaleScale> resultSaleScale = saleScaleService
            .getSaleScaleBySellerId(seller.getId());
        SaleScale saleScale = null;
        if (resultSaleScale.getSuccess()) {
            saleScale = resultSaleScale.getResult();
        } else {
            saleScale = new SaleScale();
            saleScale.setSaleScale1(new BigDecimal(0));
            saleScale.setSaleScale2(new BigDecimal(0));
            saleScale.setState(ConstantsEJS.USE_YN_N);
        }
        if (saleScale == null) {
            saleScale = new SaleScale();
            saleScale.setSaleScale1(new BigDecimal(0));
            saleScale.setSaleScale2(new BigDecimal(0));
            saleScale.setState(ConstantsEJS.USE_YN_N);
        }

        seller.setSaleScale1(saleScale.getSaleScale1());
        seller.setSaleScale2(saleScale.getSaleScale2());
        seller.setState(saleScale.getState());
        dataMap.put("seller", seller);
        return "seller/sale/salescale";
    }

    /**
     * 设置商家三级分销
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "saleproduct", method = { RequestMethod.GET })
    public String saleproduct(HttpServletRequest request, Map<String, Object> dataMap, Integer id) {
        //查询商品信息 
        ServiceResult<Product> productServiceResult = productService.getProductById(id);
        if (!productServiceResult.getSuccess()) {
            return "seller/500";
        }
        Product product = productServiceResult.getResult();
        if (null == product) {
            return "seller/404";
        }

        ServiceResult<SaleScale> serviceResultSales = saleScaleService
            .getSaleScaleBySellerId(WebSellerSession.getSellerUser(request).getSellerId());
        SaleScale saleScale = serviceResultSales.getResult();

        if (null == product.getSaleScale1()) {
            if (null != saleScale) {
                product.setSaleScale1(saleScale.getSaleScale1());
            }
        }

        if (null == product.getSaleScale2()) {
            if (null != saleScale) {
                product.setSaleScale2(saleScale.getSaleScale2());
            }
        }

        dataMap.put("product", product);
        return "seller/sale/salescale2";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(Seller seller, HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<SaleScale> resultSaleScale = saleScaleService
            .getSaleScaleBySellerId(sellerUser.getSellerId());
        SaleScale saleScale = null;
        if (resultSaleScale.getSuccess()) {
            saleScale = resultSaleScale.getResult();
        }
        if (saleScale == null) {
            saleScale = new SaleScale();
            saleScale.setCreateId(sellerUser.getId());
            saleScale.setCreateName(sellerUser.getName());
            saleScale.setUpdateId(sellerUser.getId());
            saleScale.setUpdateName(sellerUser.getName());
            saleScale.setSaleScale1(seller.getSaleScale1());
            saleScale.setSaleScale2(seller.getSaleScale2());
            saleScale.setState(seller.getState());
            saleScale.setSellerId(sellerUser.getId());
        } else {
            saleScale.setUpdateId(sellerUser.getId());
            saleScale.setUpdateName(sellerUser.getName());
            saleScale.setSaleScale1(seller.getSaleScale1());
            saleScale.setSaleScale2(seller.getSaleScale2());
            saleScale.setState(seller.getState());
        }

        ServiceResult<Integer> serviceResult = saleScaleService
            .saveSaleScaleOrUpdateBySellerId(saleScale);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("seller", seller);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/sale/salescale/salescaleedit";
            }
        }
        return "redirect:/seller/sale/salescale?message=success";
    }

    /**
     * 设置商品的三级分销
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "salescaleproduct", method = { RequestMethod.POST })
    public String salescaleproduct(HttpServletRequest request, HttpServletResponse response) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        String opt_productId = request.getParameter("productId");
        String opt_saleScale1 = StringUtil.nullSafeString(request.getParameter("opt_saleScale1"));
        String opt_saleScale2 = StringUtil.nullSafeString(request.getParameter("opt_saleScale2"));
        if ("".equals(opt_saleScale1)) {
            opt_saleScale1 = "0";
        }
        if ("".equals(opt_saleScale2)) {
            opt_saleScale2 = "0";
        }

        int productId = ConvertUtil.toInt(opt_productId, 0);
        SaleScaleProductLog saleScaleProductLog = new SaleScaleProductLog();
        saleScaleProductLog.setCreateId(sellerUser.getId());
        saleScaleProductLog.setCreateName(sellerUser.getName());
        saleScaleProductLog.setProductId(productId);
        saleScaleProductLog.setSaleScale1(new BigDecimal(opt_saleScale1));
        saleScaleProductLog.setSaleScale2(new BigDecimal(opt_saleScale2));

        if (productId != 0) {
            saleScaleProductLog.setSellerId(sellerUser.getSellerId());
        } else {
            saleScaleProductLog.setSellerId(0);
        }
        ServiceResult<Integer> serviceResultSale = saleScaleService
            .saveSaleScaleProductLog(saleScaleProductLog);
        if (serviceResultSale.getSuccess()) {
            return "redirect:/seller/product/onSale?message=success";
        }
        return "redirect:/seller/product/onSale";
    }

}
