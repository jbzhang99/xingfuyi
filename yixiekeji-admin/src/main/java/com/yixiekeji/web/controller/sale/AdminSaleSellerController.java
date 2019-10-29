package com.yixiekeji.web.controller.sale;

import java.math.BigDecimal;
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
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.sale.SaleScaleProductLog;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.sale.ISaleScaleService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

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
@RequestMapping(value = "admin/sale/salescale", produces = "application/json;charset=UTF-8")
public class AdminSaleSellerController extends BaseController {

    @Resource
    private ISellerService    sellerService;

    @Resource
    private ISaleScaleService saleScaleService;

    @Resource
    private IProductService   productService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/sale/salescale/salescalelist";
    }

    /**
     * 商家列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Seller>> list(HttpServletRequest request,
                                                           Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Seller>> serviceResult = sellerService.getSellers(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        List<Seller> sellers = serviceResult.getResult();
        SaleScale saleScale = null;
        for (Seller seller : sellers) {
            ServiceResult<SaleScale> resultSaleScale = saleScaleService
                .getSaleScaleBySellerId(seller.getId());
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
            seller.setUpdateName(saleScale.getUpdateName());
            seller.setUpdateTime(saleScale.getUpdateTime());
        }

        HttpJsonResult<List<Seller>> jsonResult = new HttpJsonResult<List<Seller>>();
        jsonResult.setRows(sellers);
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 冻结
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "freeze", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> freeze(Integer awardId, HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Integer sellerId) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;
        ServiceResult<Seller> serviceResult = sellerService.getSellerById(sellerId);
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            return jsonResult;
        }
        if (serviceResult.getResult() == null) {
            jsonResult = new HttpJsonResult<Boolean>("该商家不存在！");
            return jsonResult;
        }
        Seller seller = serviceResult.getResult();

        ServiceResult<SaleScale> resultSaleScale = saleScaleService
            .getSaleScaleBySellerId(seller.getId());
        if (!resultSaleScale.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("该商家不存在！");
            return jsonResult;
        }
        SaleScale saleScale = resultSaleScale.getResult();
        if (saleScale == null) {
            saleScale = new SaleScale();
            saleScale.setCreateId(adminUser.getId());
            saleScale.setCreateName(adminUser.getName());
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
            saleScale.setSaleScale1(new BigDecimal(0));
            saleScale.setSaleScale2(new BigDecimal(0));
            saleScale.setState(ConstantsEJS.USE_YN_N);
            saleScale.setSellerId(seller.getId());
        } else {
            saleScale.setState(ConstantsEJS.USE_YN_N);
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
        }

        // 执行冻结操作
        ServiceResult<Integer> serviceResultSale = saleScaleService
            .saveSaleScaleOrUpdateBySellerId(saleScale);
        if (!serviceResultSale.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

    /**
     * 解冻
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "unfreeze", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> unFreeze(Integer awardId,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          Integer sellerId) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;
        ServiceResult<Seller> serviceResult = sellerService.getSellerById(sellerId);
        if (!serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            return jsonResult;
        }
        if (serviceResult.getResult() == null) {
            jsonResult = new HttpJsonResult<Boolean>("该商家不存在！");
            return jsonResult;
        }
        Seller seller = serviceResult.getResult();

        ServiceResult<SaleScale> resultSaleScale = saleScaleService
            .getSaleScaleBySellerId(seller.getId());
        if (!resultSaleScale.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>("该商家不存在！");
            return jsonResult;
        }
        SaleScale saleScale = resultSaleScale.getResult();
        if (saleScale == null) {
            saleScale = new SaleScale();
            saleScale.setCreateId(adminUser.getId());
            saleScale.setCreateName(adminUser.getName());
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
            saleScale.setSaleScale1(new BigDecimal(0));
            saleScale.setSaleScale2(new BigDecimal(0));
            saleScale.setState(ConstantsEJS.USE_YN_Y);
            saleScale.setSellerId(seller.getId());
        } else {
            saleScale.setState(ConstantsEJS.USE_YN_Y);
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
        }

        // 执行冻结操作
        ServiceResult<Integer> serviceResultSale = saleScaleService
            .saveSaleScaleOrUpdateBySellerId(saleScale);
        if (!serviceResultSale.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

    /**
     * 设置商家三级分销
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap, int id) {
        ServiceResult<Seller> resultSeller = sellerService.getSellerById(id);
        Seller seller = resultSeller.getResult();

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
        return "admin/sale/salescale/salescaleedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(Seller seller, HttpServletRequest request, Map<String, Object> dataMap) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);

        ServiceResult<SaleScale> resultSaleScale = saleScaleService
            .getSaleScaleBySellerId(seller.getId());
        SaleScale saleScale = null;
        if (resultSaleScale.getSuccess()) {
            saleScale = resultSaleScale.getResult();
        }
        if (saleScale == null) {
            saleScale = new SaleScale();
            saleScale.setCreateId(adminUser.getId());
            saleScale.setCreateName(adminUser.getName());
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
            saleScale.setSaleScale1(seller.getSaleScale1());
            saleScale.setSaleScale2(seller.getSaleScale2());
            saleScale.setState(seller.getState());
            saleScale.setSellerId(seller.getId());
        } else {
            saleScale.setUpdateId(adminUser.getId());
            saleScale.setUpdateName(adminUser.getName());
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
        return "redirect:/admin/sale/salescale";
    }

    /**
     * 设置商品的三级分销
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "salescaleproduct", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> salescaleproduct(Integer awardId,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Integer sellerId) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        HttpJsonResult<Boolean> jsonResult = null;
        String opt_productId = request.getParameter("opt_productId");
        String opt_saleScale1 = request.getParameter("opt_saleScale1");
        String opt_saleScale2 = request.getParameter("opt_saleScale2");

        int productId = ConvertUtil.toInt(opt_productId, 0);
        SaleScaleProductLog saleScaleProductLog = new SaleScaleProductLog();
        saleScaleProductLog.setCreateId(adminUser.getId());
        saleScaleProductLog.setCreateName(adminUser.getName());
        saleScaleProductLog.setProductId(productId);
        saleScaleProductLog.setSaleScale1(new BigDecimal(opt_saleScale1));
        saleScaleProductLog.setSaleScale2(new BigDecimal(opt_saleScale2));

        if (productId != 0) {
            ServiceResult<Product> resultProduct = productService.getProductById(productId);
            Product product = resultProduct.getResult();
            saleScaleProductLog.setSellerId(product.getSellerId());
        } else {
            saleScaleProductLog.setSellerId(0);
        }
        ServiceResult<Integer> serviceResultSale = saleScaleService
            .saveSaleScaleProductLog(saleScaleProductLog);
        if (!serviceResultSale.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>(serviceResultSale.getMessage());
            return jsonResult;
        } else {
            jsonResult = new HttpJsonResult<Boolean>();
        }

        return jsonResult;
    }

}
