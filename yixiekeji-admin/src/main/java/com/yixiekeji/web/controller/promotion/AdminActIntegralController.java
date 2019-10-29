package com.yixiekeji.web.controller.promotion;

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
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.integral.ActIntegralType;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.service.promotion.IActIntegralService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebAdminSession;

@Controller
@RequestMapping(value = "admin/promotion/actintegral", produces = "application/json;charset=UTF-8")
public class AdminActIntegralController extends BaseController {

    @Resource
    private IActIntegralService actIntegralService;

    @Resource
    private IProductService     productService;

    @Resource
    private ISellerService      sellerService;

    @Resource
    private DomainUrlUtil       domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        return "admin/promotion/integral/integrallist";
    }

    /**
     * 积分商城列表页
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ActIntegral>> list(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
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

            ServiceResult<Seller> sellerResult = sellerService
                .getSellerById(actIntegral.getSellerId());
            actIntegral.setSellerName(sellerResult.getResult().getSellerName());

            ServiceResult<ActIntegralType> resultActIntegralType = actIntegralService
                .getActIntegralTypeById(actIntegral.getType());
            actIntegral.setTypeName(resultActIntegralType.getResult().getName());
        }

        jsonResult.setRows(actIntegrals);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int id, Map<String, Object> dataMap) {
        ServiceResult<ActIntegral> serviceResult = actIntegralService.getActIntegralById(id);

        ActIntegral actIntegral = serviceResult.getResult();
        ServiceResult<Product> resultProduct = productService
            .getProductById(actIntegral.getProductId());
        Product product = resultProduct.getResult();
        actIntegral.setProductName(product.getName1());

        ServiceResult<ActIntegralType> resultActIntegralType = actIntegralService
            .getActIntegralTypeById(actIntegral.getType());
        actIntegral.setTypeName(resultActIntegralType.getResult().getName());

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        ServiceResult<Seller> sellerResult = sellerService.getSellerById(actIntegral.getSellerId());
        actIntegral.setSellerName(sellerResult.getResult().getSellerName());

        dataMap.put("actIntegral", actIntegral);

        return "admin/promotion/integral/integraledit";
    }

    /**
     * 查看
     * @param id
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "look", method = { RequestMethod.GET })
    public String look(int id, Map<String, Object> dataMap) {
        ServiceResult<ActIntegral> serviceResult = actIntegralService.getActIntegralById(id);

        ActIntegral actIntegral = serviceResult.getResult();
        ServiceResult<Product> resultProduct = productService
            .getProductById(actIntegral.getProductId());
        Product product = resultProduct.getResult();
        actIntegral.setProductName(product.getName1());

        ServiceResult<List<ActIntegralType>> result = actIntegralService.getActIntegralTypesAll();
        dataMap.put("actIntegralTypes", result.getResult());

        ServiceResult<Seller> sellerResult = sellerService.getSellerById(actIntegral.getSellerId());
        actIntegral.setSellerName(sellerResult.getResult().getSellerName());

        if (!StringUtil.isEmpty(actIntegral.getDescinfo(), true)) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }

        dataMap.put("actIntegral", actIntegral);

        return "admin/promotion/integral/integrallook";
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
        ServiceResult<ActIntegral> serviceResult = actIntegralService
            .getActIntegralById(actIntegral.getId());

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);

        ActIntegral actIntegralNew = serviceResult.getResult();
        actIntegralNew.setIsBest(actIntegral.getIsBest());
        actIntegralNew.setSortNum(actIntegral.getSortNum());
        actIntegralNew.setAuditId(adminUser.getId());
        actIntegralNew.setAuditName(adminUser.getName());
        actIntegralNew.setVirtualSaleNum(actIntegral.getVirtualSaleNum());

        ServiceResult<Integer> result = actIntegralService.updateActIntegral(actIntegralNew);
        if (!result.getSuccess()) {
            return "redirect:edit";
        }

        return "redirect:/admin/promotion/actintegral";
    }

    /**
     * 审核通过
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "auditYes", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> auditYes(HttpServletRequest request,
                                                         @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String auditOpinion = request.getParameter("auditOpinion");
        if (StringUtil.isEmpty(auditOpinion, true)) {
            auditOpinion = "审核通过";
        }
        ServiceResult<Boolean> serviceResult = actIntegralService
            .updateActIntegralStateAndAuditOpinion(id, ActIntegral.STATE_3, auditOpinion);

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

        String auditOpinion = request.getParameter("auditOpinion");
        if (StringUtil.isEmpty(auditOpinion, true)) {
            return new HttpJsonResult<Object>("请填写驳回原因。");
        }

        ServiceResult<Boolean> serviceResult = actIntegralService
            .updateActIntegralStateAndAuditOpinion(id, ActIntegral.STATE_4, auditOpinion);

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

        ServiceResult<Boolean> serviceResult = actIntegralService.updateActIntegralActivityState(id,
            ActIntegral.ACTIVITY_STATE_3);

        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }
}
