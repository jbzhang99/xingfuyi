package com.yixiekeji.web.controller.product;

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
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.ISellerManageCateService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商家可以经营商品分类
 */
@Controller
@RequestMapping(value = "seller/product/manager")
public class SellerManagerCateController extends BaseController {

    @Resource
    private ISellerManageCateService sellerManageCateService;
    @Resource
    private IProductCateService      productCateService;

    private String                   baseUrlStr = "seller/product/manager/";

    /**
     * 默认，显示全部状态的品牌
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrlStr + "managerlist";
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerManageCate>> list(HttpServletRequest request,
                                                                     Map<String, Object> dataMap) {
        HttpJsonResult<List<SellerManageCate>> jsonResult = new HttpJsonResult<List<SellerManageCate>>();
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_seller", String.valueOf(sellerUser.getSellerId()));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SellerManageCate>> serviceResult = sellerManageCateService
            .pageSellerManageCate(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        jsonResult.setRows((List<SellerManageCate>) serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

}
