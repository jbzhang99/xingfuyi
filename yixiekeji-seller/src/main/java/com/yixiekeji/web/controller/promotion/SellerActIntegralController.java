package com.yixiekeji.web.controller.promotion;

import java.math.BigDecimal;
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

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralType;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.promotion.IActIntegralService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 积分商城相关的操作
 *                       
 * @Filename: SellerActIntegralController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "seller/promotion/actintegral")
public class SellerActIntegralController extends BaseController {

    @Resource
    private IActIntegralService actIntegralService;

    @Resource
    private IProductService     productService;

    @Resource
    private DomainUrlUtil       domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        return "seller/promotion/integral/actintegrallist";
    }

    /**
     * 集合竞价列表页
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ActIntegral>> list(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ActIntegral>> serviceResult = actIntegralService
            .getActIntegrals(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ActIntegral>> jsonResult = new HttpJsonResult<List<ActIntegral>>();
        List<ActIntegral> actIntegrals = serviceResult.getResult();
        for (ActIntegral actIntegral : actIntegrals) {
            ServiceResult<Product> resultProduct = productService
                .getProductById(actIntegral.getProductId());
            actIntegral.setProductName(resultProduct.getResult().getName1());

            ServiceResult<ActIntegralType> resultActIntegralType = actIntegralService
                .getActIntegralTypeById(actIntegral.getType());
            actIntegral.setTypeName(resultActIntegralType.getResult().getName());
        }

        jsonResult.setRows(actIntegrals);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 跳转到添加页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) throws Exception {
        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());
        return "seller/promotion/integral/actintegraladd";
    }

    /**
     * 新加
     * @param activityBidding
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(ActIntegral actIntegral, HttpServletRequest request,
                         Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        actIntegral.setSellerId(sellerUser.getSellerId());
        actIntegral.setVirtualSaleNum(0);
        actIntegral.setSaleNum(0);
        actIntegral.setSortNum(0);
        if (actIntegral.getIsWithMoney() == ActIntegral.IS_WITH_MONEY_0) {
            actIntegral.setPriceMoney(BigDecimal.ZERO);
        }

        actIntegral.setIsBest(ActIntegral.IS_BEST_0);
        actIntegral.setActivityState(ActIntegral.ACTIVITY_STATE_1);
        actIntegral.setState(ActIntegral.STATE_1);
        actIntegral.setTypeState(ActIntegral.TYPE_STATE_1);

        // 上传图片
        String image = UploadUtil.getInstance().productSellerIndexUploadFileImageServer("imageFile",
            request, domainUrlUtil.getImageResources() + "/imageUtilProduct");
        if (image != null && !"".equals(image)) {
            actIntegral.setImage(image);
        }

        if (!isNull(actIntegral.getDescinfo())) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace(domainUrlUtil.getImageResources(), "${(domainUrlUtil.imageResources)!}"));
        }

        ServiceResult<Integer> serviceResult = actIntegralService.saveActIntegral(actIntegral);
        if (!serviceResult.getSuccess()) {
            dataMap.put("actIntegral", actIntegral);
            dataMap.put("message", serviceResult.getMessage());
            ServiceResult<List<ActIntegralType>> result = actIntegralService
                .getActIntegralTypesAll();
            dataMap.put("actIntegralTypes", result.getResult());
            return "seller/promotion/integral/actintegraladd";
        }
        return "redirect:/seller/promotion/actintegral";
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int id, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<ActIntegral> serviceResult = actIntegralService.getActIntegralById(id);
        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }

        ActIntegral actIntegral = serviceResult.getResult();
        if (actIntegral == null) {
            return "seller/404";
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        ServiceResult<Product> resultProduct = productService
            .getProductById(actIntegral.getProductId());
        Product product = resultProduct.getResult();
        actIntegral.setProductName(product.getName1());
        if (!isNull(actIntegral.getDescinfo())) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }

        dataMap.put("actIntegral", actIntegral);

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        return "seller/promotion/integral/actintegraledit";
    }

    /**
     * 查看
     * @param id
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "look", method = { RequestMethod.GET })
    public String look(HttpServletRequest request, int id, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<ActIntegral> serviceResult = actIntegralService.getActIntegralById(id);
        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        ActIntegral actIntegral = serviceResult.getResult();
        if (actIntegral == null) {
            return "seller/404";
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        ServiceResult<Product> resultProduct = productService
            .getProductById(actIntegral.getProductId());
        Product product = resultProduct.getResult();
        actIntegral.setProductName(product.getName1());

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        if (!isNull(actIntegral.getDescinfo())) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }

        dataMap.put("actIntegral", actIntegral);

        return "seller/promotion/integral/actintegrallook";
    }

    /**
     * 更新
     * @param activityBidding
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(ActIntegral actIntegral, HttpServletRequest request,
                         Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<ActIntegral> actIntegralResult = actIntegralService
            .getActIntegralById(actIntegral.getId());
        if (!actIntegralResult.getSuccess()) {
            return "seller/500";
        }
        ActIntegral dbActIntegral = actIntegralResult.getResult();
        if (dbActIntegral == null) {
            return "seller/404";
        }
        if (!dbActIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        if (actIntegral.getIsWithMoney() == ActIntegral.IS_WITH_MONEY_0) {
            actIntegral.setPriceMoney(BigDecimal.ZERO);
        }

        // 上传图片
        String image = UploadUtil.getInstance().productSellerIndexUploadFileImageServer("imageFile",
            request, domainUrlUtil.getImageResources() + "/imageUtilProduct");
        if (image != null && !"".equals(image)) {
            actIntegral.setImage(image);
        } else {
            actIntegral.setImage(null);
        }

        if (!isNull(actIntegral.getDescinfo())) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace(domainUrlUtil.getImageResources(), "${(domainUrlUtil.imageResources)!}"));
        }

        ServiceResult<Integer> serviceResult = actIntegralService.updateActIntegral(actIntegral);
        if (!serviceResult.getSuccess()) {
            return "redirect:edit";
        }
        return "redirect:/seller/promotion/actintegral";
    }

    /**
     * 提交审核
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "auditYes", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> auditYes(HttpServletRequest request,
                                                         @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<ActIntegral> resultActIntegral = actIntegralService.getActIntegralById(id);
        if (!resultActIntegral.getSuccess()) {
            return new HttpJsonResult<Object>(resultActIntegral.getMessage());
        }
        ActIntegral actIntegral = resultActIntegral.getResult();
        if (actIntegral == null) {
            return new HttpJsonResult<Object>("获取数据失败，请重试。");
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权操作该数据。");
        }
        if (actIntegral.getState().intValue() == ActIntegral.STATE_2
            || actIntegral.getState().intValue() == ActIntegral.STATE_3) {
            return null;
        }

        ServiceResult<Boolean> serviceResult = actIntegralService.updateActIntegralState(id,
            ActIntegral.STATE_2);

        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 审核撤回
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "auditNo", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> auditNo(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<ActIntegral> resultActIntegral = actIntegralService.getActIntegralById(id);
        if (!resultActIntegral.getSuccess()) {
            return new HttpJsonResult<Object>(resultActIntegral.getMessage());
        }
        ActIntegral actIntegral = resultActIntegral.getResult();
        if (actIntegral == null) {
            return new HttpJsonResult<Object>("获取数据失败，请重试。");
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权操作该数据。");
        }
        if (actIntegral.getState().intValue() != ActIntegral.STATE_2) {
            return null;
        }

        ServiceResult<Boolean> serviceResult = actIntegralService.updateActIntegralState(id,
            ActIntegral.STATE_1);

        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 发布
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "release", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> release(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<ActIntegral> resultActIntegral = actIntegralService.getActIntegralById(id);
        if (!resultActIntegral.getSuccess()) {
            return new HttpJsonResult<Object>(resultActIntegral.getMessage());
        }
        ActIntegral actIntegral = resultActIntegral.getResult();
        if (actIntegral == null) {
            return new HttpJsonResult<Object>("获取数据失败，请重试。");
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权操作该数据。");
        }

        if (actIntegral.getActivityState().intValue() != ActIntegral.ACTIVITY_STATE_1) {
            return null;
        }

        ServiceResult<Boolean> serviceResult = actIntegralService.updateActIntegralActivityState(id,
            ActIntegral.ACTIVITY_STATE_2);

        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 结束
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "releaseFinal", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> releaseFinal(HttpServletRequest request,
                                                             @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<ActIntegral> resultActIntegral = actIntegralService.getActIntegralById(id);
        if (!resultActIntegral.getSuccess()) {
            return new HttpJsonResult<Object>(resultActIntegral.getMessage());
        }
        ActIntegral actIntegral = resultActIntegral.getResult();
        if (actIntegral == null) {
            return new HttpJsonResult<Object>("获取数据失败，请重试。");
        }
        if (!actIntegral.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权操作该数据。");
        }

        if (actIntegral.getActivityState().intValue() != ActIntegral.ACTIVITY_STATE_2) {
            return null;
        }

        ServiceResult<Boolean> serviceResult = actIntegralService.updateActIntegralActivityState(id,
            ActIntegral.ACTIVITY_STATE_3);

        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
