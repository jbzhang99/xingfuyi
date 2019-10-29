package com.yixiekeji.web.controller.member;

import com.yixiekeji.core.*;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品咨询管理controller
 * 
 * @Filename: SellerProdcutAskController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/member/productask")
public class SellerProdcutAskController extends BaseController {

    @Resource
    private IProductAskService productAskService;

    /**
     * 产品咨询管理页面初始化controller接口
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/member/productasklist";
    }

    /**
     * 产品咨询管理页面查询按钮controller接口
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ProductAsk>> list(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductAsk>> serviceResult = productAskService
            .getProductAsksWithInfo(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductAsk>> jsonResult = new HttpJsonResult<List<ProductAsk>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "reply", method = { RequestMethod.GET })
    public String reply(HttpServletRequest request, Integer id, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<ProductAsk> serviceResult = productAskService.getProductAskById(id);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/member/productasklist";
            }
        }
        ProductAsk productAsk = serviceResult.getResult();
        if (productAsk == null) {
            return "seller/404";
        }
        if (!productAsk.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("productAsk", productAsk);
        return "seller/member/productaskedit";
    }

    @RequestMapping(value = "doreply", method = { RequestMethod.POST })
    public String doReply(ProductAsk productAsk, HttpServletRequest request,
                          Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<ProductAsk> productAskResult = productAskService
            .getProductAskById(productAsk.getId());

        if (!productAskResult.getSuccess()) {
            return "seller/500";
        }
        ProductAsk dbProductAskNew = productAskResult.getResult();
        if (dbProductAskNew == null) {
            return "seller/404";
        }
        if (!dbProductAskNew.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        ProductAsk productAskNew = new ProductAsk();
        productAskNew.setId(productAsk.getId());
        productAskNew.setReplyContent(productAsk.getReplyContent());
        productAskNew.setReplyId(sellerUser.getId());
        productAskNew.setReplyName(sellerUser.getName());
        productAskNew.setReplyTime(new Date());
        productAskNew.setState(3);

        ServiceResult<Boolean> serviceResult = productAskService.updateProductAsk(productAskNew);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("productAsk", productAsk);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/member/productaskedit";
            }
        }
        return "redirect:/seller/member/productask";
    }
}
