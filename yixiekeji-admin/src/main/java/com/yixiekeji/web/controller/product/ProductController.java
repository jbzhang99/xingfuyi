package com.yixiekeji.web.controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductPicture;
import com.yixiekeji.entity.sale.SaleScale;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.product.IProductPictureService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.product.ISellerCateService;
import com.yixiekeji.service.sale.ISaleScaleService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 商品品牌
 */
@Controller
@RequestMapping(value = "admin/product", produces = "application/json;charset=UTF-8")
public class ProductController extends BaseController {
    @Resource
    private IProductService         productService;
    @Resource
    private IProductPictureService  productPicService;
    @Resource
    private IMemberService          memberService;
    @Resource
    private IProductGoodsService    productGoodsService;
    @Resource
    private ISellerService          sellerService;
    @Resource
    private IProductPictureService  productPictureService;
    @Resource
    private IProductCateService     productCateService;
    @Resource
    private IProductBrandService    productBrandService;
    @Resource
    private ISellerCateService      sellerCateService;
    @Resource
    private ISaleScaleService       saleScaleService;
    @Resource
    private ISellerTransportService sellerTransportService;

    @Resource
    private DomainUrlUtil           domainUrlUtil;

    private String                  baseUrl = "admin/product/manager/";

    @RequestMapping(value = "waitSale", method = { RequestMethod.GET })
    public String waitSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", "1,2,3,4,7");//1、刚创建；2、提交审核；3、审核通过；4、申请驳回；7、下架
        return baseUrl + "listwaitsale";
    }

    @RequestMapping(value = "onSale", method = { RequestMethod.GET })
    public String onSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", "6");//6、上架；
        String message = request.getParameter("message");
        if ("success".equals(message)) {
            dataMap.put("message", "操作成功");
        }

        return baseUrl + "listonsale";
    }

    @RequestMapping(value = "delSale", method = { RequestMethod.GET })
    public String delSale(HttpServletRequest request, Map<String, Object> dataMap) {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", "5");//5、商品删除；
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
        if (!StringUtil.isEmpty(request.getParameter("q_state1"))) {
            queryMap.put("q_state", request.getParameter("q_state1"));
        }
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<Product>> serviceResult = productService.pageProduct(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<Product> products = serviceResult.getResult();
        pager = serviceResult.getPager();

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

    @RequestMapping(value = "auditProduct", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<String> auditProduct(HttpServletRequest request,
                                                             HttpServletResponse response,
                                                             String ids, Integer type) {
        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        String reutrnstr = "";
        try {
            for (String id : ids.split(",")) {
                String msg = "操作成功，";
                if (isNull(id))
                    throw new BusinessException("请选择要操作的商品");
                if (type == null)
                    throw new BusinessException("未知操作");
                switch (type) {
                    case 3:
                        msg += "选中商品将允许在商城上架";
                        break;
                    case 4:
                        msg += "选中商品将不允许再次上架";
                        break;
                    case 5:
                        msg += "选中商品将被冻结,并强制下架";
                        break;
                    case 7:
                        msg += "选中商品将强制下架";
                        break;
                    default:
                        msg = "操作失败,请稍后重试";
                        throw new BusinessException(msg);
                }
                productService.updateProductState(Integer.valueOf(id), type);
                reutrnstr = msg;
            }
            jsonResult.setData(reutrnstr);
        } catch (Exception e) {
            if (e instanceof BusinessException)
                jsonResult.setMessage(e.getMessage());
            else
                e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping(value = "recommond", method = { RequestMethod.GET })
    public void recommond(HttpServletRequest request, HttpServletResponse response, Integer id,
                          Boolean isRec) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "";
        PrintWriter pw = null;

        try {
            if (isRec == null)
                throw new BusinessException("未知操作");

            Integer isTop = ConstantsEJS.PRODUCT_IS_TOP_1;
            if (isRec) {
                isTop = ConstantsEJS.PRODUCT_IS_TOP_2;
                msg = "推荐成功";
            } else {
                isTop = ConstantsEJS.PRODUCT_IS_TOP_1;
                msg = "取消推荐成功";
            }
            ServiceResult<Boolean> sr = productService.updateProductRecommend(id, isTop);
            if (!sr.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                    throw new RuntimeException(sr.getMessage());
                } else {
                    throw new BusinessException(sr.getMessage());
                }
            }
            pw = response.getWriter();
        } catch (IOException e) {
            msg = e.getMessage();
        }
        pw.print(msg);
    }

    @RequestMapping(value = "del", method = { RequestMethod.GET })
    public void del(HttpServletRequest request, HttpServletResponse response, Integer id) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "删除成功";
        PrintWriter pw = null;

        try {
            ServiceResult<Boolean> sr = productService.updateProductState(id, Product.STATE_5);
            if (!sr.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                    throw new RuntimeException(sr.getMessage());
                } else {
                    throw new BusinessException(sr.getMessage());
                }
            }
            pw = response.getWriter();
        } catch (IOException e) {
            msg = e.getMessage();
        }
        pw.print(msg);
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

        if (productId == null || productId == 0) {
            dataMap.put("info", "商品id不能为空，请重试");
            return "admin/500";
        }

        //获取商品信息
        ServiceResult<Product> productResult = productService.getProductById(productId);
        if (!productResult.getSuccess() || productResult.getResult() == null) {
            dataMap.put("info", "获取商品信息失败，请重试");
            return "admin/500";
        }
        Product product = productResult.getResult();

        //设置商品分类名称
        ServiceResult<ProductCate> productCateResult = productCateService
            .getProductCateById(product.getProductCateId());
        if (!productCateResult.getSuccess() || productCateResult.getResult() == null) {
            dataMap.put("info", "获取商品对应的分类信息失败，请重试");
            return "admin/500";
        }
        product.setProductCateName(productCateResult.getResult().getName());

        //设置商品品牌名称
        ServiceResult<ProductBrand> productBrandResult = productBrandService
            .getById(product.getProductBrandId());
        if (!productBrandResult.getSuccess() || productBrandResult.getResult() == null) {
            dataMap.put("info", "获取商品品牌信息失败，请重试");
            return "admin/500";
        }
        product.setProductBrandName(productBrandResult.getResult().getName());

        //设置商品商家分类信息
        ServiceResult<SellerCate> sellerCateResult = sellerCateService
            .getSellerCateById(product.getSellerCateId());
        if (!sellerCateResult.getSuccess() || sellerCateResult.getResult() == null) {
            dataMap.put("info", "获取商家分类信息失败，请重试");
            return "admin/500";
        }
        product.setSellerCateName(sellerCateResult.getResult().getName());

        //设置商品商家信息
        ServiceResult<Seller> sellerResult = sellerService.getSellerById(product.getSellerId());
        if (!sellerResult.getSuccess() || sellerResult.getResult() == null) {
            dataMap.put("info", "获取商品所属商家信息失败，请重试");
            return "admin/500";
        }
        product.setSeller(sellerResult.getResult().getSellerName());

        //获取商品图片信息
        ServiceResult<List<ProductPicture>> productPictureResult = productPictureService
            .getProductPictureByProductId(productId);
        if (!productPictureResult.getSuccess() || productPictureResult.getResult() == null) {
            dataMap.put("info", "获取商品图片信息失败，请重试");
            return "admin/500";
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

        return "admin/product/manager/productdetails";
    }
}
