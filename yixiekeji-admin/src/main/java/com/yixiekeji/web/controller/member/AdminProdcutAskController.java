package com.yixiekeji.web.controller.member;

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
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductAsk;
import com.yixiekeji.service.product.IProductAskService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 产品咨询管理controller
 * 
 * @Filename: AdminProdcutAskController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/member/productask", produces = "application/json;charset=UTF-8")
public class AdminProdcutAskController extends BaseController {

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
        return "admin/member/member/productasklist";
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

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
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

    /**
     * 审核商品咨询-通过
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "pass", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> pass(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      Integer productAskId) {
        ProductAsk productAsk = new ProductAsk();
        productAsk.setId(productAskId);
        productAsk.setState(ProductAsk.STATE_3);
        ServiceResult<Boolean> serviceResult = productAskService.updateProductAsk(productAsk);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }

    /**
     * 审核商品咨询-不通过
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "reject", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> reject(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Integer productAskId) {
        ProductAsk productAsk = new ProductAsk();
        productAsk.setId(productAskId);
        productAsk.setState(ProductAsk.STATE_4);
        ServiceResult<Boolean> serviceResult = productAskService.updateProductAsk(productAsk);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }
}
