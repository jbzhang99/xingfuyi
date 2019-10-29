package com.yixiekeji.web.controller.product;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.util.FeignProjectUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商品管理新增修改外其他操作controller
 *                       
 * @Filename: SellerProductOptController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/product")
public class SellerProductOptController extends BaseController {
    @Resource
    private IProductService      productService;
    @Resource
    private IProductGoodsService productGoodsService;
    private String               baseUrl = "seller/product/pdt/";

    /**
     * 库存价格设置
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "goodsSet", method = { RequestMethod.GET })
    public String goodsSet(HttpServletRequest request, Map<String, Object> dataMap, Integer id,
                           String from) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        //查询商品信息 
        ServiceResult<Product> productServiceResult = productService.getProductById(id);
        if (!productServiceResult.getSuccess()) {
            return "seller/500";
        }
        Product product = productServiceResult.getResult();
        if (null == product) {
            return "seller/404";
        }

        if (!product.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        ServiceResult<List<ProductGoods>> productGoodsServiceResult = productGoodsService
            .getGoodSByProductId(id);
        List<ProductGoods> goodslist = productGoodsServiceResult.getResult();

        dataMap.put("product", product);
        dataMap.put("goodslist", goodslist);
        dataMap.put("from", from);

        return baseUrl + "goodsset";
    }

    @RequestMapping(value = "goodssetSumit", method = { RequestMethod.POST })
    public String goodssetSumit(HttpServletRequest request, Map<String, Object> dataMap,
                                Product productinfo, String from) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Product productDb = productService.getProductById(productinfo.getId()).getResult();
        if (productDb == null) {
            return "seller/404";
        }
        if (!productDb.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        Product product = new Product();
        product.setId(productinfo.getId());
        product.setName2(productinfo.getName2());
        product.setMallPcPrice(productinfo.getMallPcPrice());
        product.setMalMobilePrice(productinfo.getMalMobilePrice());
        product.setProductStock(productinfo.getProductStock());
        //更新此商品价格库存信息
        List<ProductGoods> goodslist = new ArrayList<ProductGoods>();

        String goodsnum = request.getParameter("goodsnum");
        if (!isNull(goodsnum) && Integer.valueOf(goodsnum) > 0) {
            for (int i = 0; i < Integer.valueOf(goodsnum); i++) {
                if (isNull(request.getParameter("goodsid_" + i))) {
                    continue;
                }
                Integer goodsId = Integer.valueOf(request.getParameter("goodsid_" + i));
                String stock = request.getParameter("inventory_details_stock_" + i);
                String pprice = request.getParameter("inventory_details_pprice_" + i);
                String mprice = request.getParameter("inventory_details_mprice_" + i);

                ProductGoods goods = new ProductGoods();
                goods.setId(goodsId);
                goods.setProductStock(Integer.valueOf(stock));
                goods.setMallPcPrice(new BigDecimal(pprice.trim()));
                goods.setMallMobilePrice(new BigDecimal(mprice.trim()));

                goodslist.add(goods);
            }
        } else {
            //单个货品
            List<ProductGoods> progoods = productGoodsService
                .getGoodSByProductId(productinfo.getId()).getResult();
            ProductGoods goods = new ProductGoods();
            goods.setId(progoods.get(0).getId());
            goods.setProductStock(product.getProductStock());
            goods.setMallMobilePrice(product.getMalMobilePrice());
            goods.setMallPcPrice(product.getMallPcPrice());
            goodslist.add(goods);
        }
        product.setGoodsList(goodslist);
        productGoodsService.updateProductAndGoods(product);

        //根据from重定向至相应页面
        return "redirect:/seller/product/" + from;
    }

    /**
     * 提交审核
     * @param request
     * @return
     */
    @RequestMapping(value = "commit", method = { RequestMethod.POST })
    public void commit(HttpServletRequest request, HttpServletResponse response, String ids) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "提交成功,请耐心等待,审核通过后商品才能上架";
        PrintWriter pw = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("state", Product.STATE_2);
        try {
            FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
            feignProjectUtil.setMap(map);
            feignProjectUtil.setIntegerList(StringUtil.string2IntegerList(ids));

            productService.updateByIds(feignProjectUtil);
            pw = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            msg = "提交失败,请稍后重试";
        }
        pw.print(msg);
    }

    /**
     * 上下架操作
     *
     * @param request
     * @param response
     * @param ids
     */
    @RequestMapping(value = "handler", method = { RequestMethod.POST })
    public void handler(HttpServletRequest request, HttpServletResponse response, String ids,
                        Integer type) {
        response.setContentType("text/plain;charset=utf-8");
        String msg = "";
        PrintWriter pw = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", Product.STATE_2);
            if (type == Product.STATE_6) {
                map.put("state", Product.STATE_6);
                msg = "上架成功";
            } else if (type == Product.STATE_7) {
                map.put("state", Product.STATE_7);
                msg = "下架成功";
            } else {
                throw new BusinessException("未知操作");
            }
            FeignProjectUtil feignProjectUtil = FeignProjectUtil.getFeignProjectUtil();
            feignProjectUtil.setMap(map);
            feignProjectUtil.setIntegerList(StringUtil.string2IntegerList(ids));

            productService.updateByIds(feignProjectUtil);
            pw = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        pw.print(msg);
    }

    /**
     * 删除商品
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<Boolean> serviceResult = productService.delProduct(id,
            sellerUser.getSellerId());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }
}
