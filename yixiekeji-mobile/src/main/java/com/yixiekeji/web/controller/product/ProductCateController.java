package com.yixiekeji.web.controller.product;

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

import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.vo.product.FrontProductCateVO;
import com.yixiekeji.web.controller.BaseController;

/**
 * 
 *                       
 * @Filename: ProductCateController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductCateController extends BaseController {

    @Resource
    private IProductCateService productCateService;

    /**
     * 导航所有商品分类 
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/catelist.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<FrontProductCateVO>> getProductCateList(HttpServletRequest request,
                                                                                     HttpServletResponse response,
                                                                                     Map<String, Object> dataMap) {
        HttpJsonResult<List<FrontProductCateVO>> jsonResult = new HttpJsonResult<List<FrontProductCateVO>>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        ServiceResult<List<FrontProductCateVO>> serviceResult = new ServiceResult<List<FrontProductCateVO>>();
        serviceResult = productCateService.getProductCateList(queryMap);
        jsonResult.setData(serviceResult.getResult());

        return jsonResult;
    }

}
