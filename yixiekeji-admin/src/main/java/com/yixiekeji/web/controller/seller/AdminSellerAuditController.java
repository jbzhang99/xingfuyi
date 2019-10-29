package com.yixiekeji.web.controller.seller;

import com.yixiekeji.core.*;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerApply;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.seller.ISellerApplyService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理端商家申请审核controller
 *                       
 * @Filename: AdminSellerAuditController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/seller/audit", produces = "application/json;charset=UTF-8")
public class AdminSellerAuditController extends BaseController {

    @Resource
    private ISellerApplyService sellerApplyService;

    @Resource
    private ISellerService      sellerService;

    @Resource
    private IRegionsService     regionsService;

    @Resource
    private DomainUrlUtil       domainUrlUtil;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/seller/audit/sellerapplylist";
    }

    /**
     * 商家申请列表
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerApply>> list(HttpServletRequest request,
                                                                Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SellerApply>> serviceResult = sellerApplyService
            .getSellerApplys(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SellerApply>> jsonResult = new HttpJsonResult<List<SellerApply>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 删除商家申请
     * @param dataMap
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletResponse response,
                                                        Map<String, Object> dataMap, Integer id,
                                                        Integer userId) throws Exception {

        ServiceResult<Boolean> serviceResult = sellerApplyService.deleteSellerApply(id, userId);
        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }

        return jsonResult;
    }

    /**
     * 跳往审核页面
     * @param dataMap
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "audit.html", method = { RequestMethod.GET })
    public String audit(Map<String, Object> dataMap, Integer id) throws Exception {
        ServiceResult<SellerApply> sr = sellerApplyService.getSellerApplyById(id);
        dataMap.put("app", sr.getResult());
        dataMap.put("stringUtil", new StringUtil());
        return "admin/seller/audit/sellerapplyaudit";
    }

    /**
     * 审核通过
     * @param request
     * @param response
     * @param dataMap
     * @param id
     */
    @RequestMapping(value = "pass", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> pass(HttpServletRequest request,
                                                      HttpServletResponse response,
                                                      Map<String, Object> dataMap, Integer id) {

        ServiceResult<Boolean> serviceResult = sellerApplyService.auditSellerApply(id,
            SellerApply.STATE_2_DONE, WebAdminSession.getAdminUser(request).getId());
        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 申请被驳回
     * @param request
     * @param response
     * @param dataMap
     * @param id
     */
    @RequestMapping(value = "reject", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> reject(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Map<String, Object> dataMap, Integer id) {

        ServiceResult<Boolean> serviceResult = sellerApplyService.auditSellerApply(id,
            SellerApply.STATE_4_FAIL, WebAdminSession.getAdminUser(request).getId());
        HttpJsonResult<Boolean> jsonResult = null;
        if (serviceResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 跳往审核页面
     * @param dataMap
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) throws Exception {

        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        dataMap.put("provinceList", provinceResult.getResult());

        return "admin/seller/audit/sellerapplyadd";
    }

    /*
     * 商家申请添加
     */
    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(SellerApply sellerApply, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        //营业执照扫描件
        //        String bli = UploadUtil.getInstance()
        //            .sellerApplyUploadFile2ImageServer("up_bussinessLicenseImage", request);

        Map<String, String> param = new HashMap<String, String>();
        param.put("seller", "apply");

        String bli = UploadUtil.getInstance().uploadFile2ImageServer("up_bussinessLicenseImage",
            request, param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");

        if (!StringUtil.isEmpty(bli)) {
            sellerApply.setBussinessLicenseImage(bli);
        }

        //身份证正面
        String pcu = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardUp", request,
            param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        if (!StringUtil.isEmpty(pcu)) {
            sellerApply.setPersonCardUp(pcu);
        }

        //身份证反面
        String pdw = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardDown", request,
            param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        if (!StringUtil.isEmpty(pdw)) {
            sellerApply.setPersonCardDown(pdw);
        }

        sellerApply.setState(SellerApply.STATE_1_SEND);
        sellerApply.setType(2);

        String userName = request.getParameter("userName");
        String sellerName = request.getParameter("sellerName");

        if (StringUtil.isEmpty(userName, true)) {
            dataMap.put("userName", userName);
            dataMap.put("sellerName", sellerName);
            dataMap.put("sellerApply", sellerApply);
            dataMap.put("message", "商家账号不能为空！");
            ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
            dataMap.put("provinceList", provinceResult.getResult());
            return "admin/seller/audit/sellerapplyadd";
        }
        if (StringUtil.isEmpty(sellerName, true)) {
            dataMap.put("userName", userName);
            dataMap.put("sellerName", sellerName);
            dataMap.put("sellerApply", sellerApply);
            dataMap.put("message", "商家店铺名称不能为空！");
            ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
            dataMap.put("provinceList", provinceResult.getResult());
            return "admin/seller/audit/sellerapplyadd";
        }

        String ip = WebUtil.getIpAddr(request);

        ServiceResult<Boolean> serviceResult = sellerApplyService
            .saveSellerApplyForAdmin(sellerApply, userName, sellerName, ip);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("userName", userName);
                dataMap.put("sellerName", sellerName);
                dataMap.put("sellerApply", sellerApply);
                dataMap.put("message", serviceResult.getMessage());
                ServiceResult<List<Regions>> provinceResult = regionsService
                    .getRegionsByParentId(0);
                dataMap.put("provinceList", provinceResult.getResult());
                return "admin/seller/audit/sellerapplyadd";
            }
        }
        return "redirect:/admin/seller/audit";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int sellerApplyId, Map<String, Object> dataMap) {
        ServiceResult<SellerApply> serviceResult = sellerApplyService
            .getSellerApplyById(sellerApplyId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/seller/audit/sellerapplylist";
            }
        }

        SellerApply sellerApply = serviceResult.getResult();

        dataMap.put("sellerApply", sellerApply);

        ServiceResult<Seller> sellerResult = sellerService
            .getSellerByMemberId(sellerApply.getUserId());
        if (!sellerResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sellerResult.getCode())) {
                throw new RuntimeException(sellerResult.getMessage());
            } else {
                dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
                dataMap.put("message", sellerResult.getMessage());
                return "admin/seller/audit/sellerapplylist";
            }
        }
        Seller seller = sellerResult.getResult();
        if (seller != null) {
            dataMap.put("userName", seller.getName());
            dataMap.put("sellerName", seller.getSellerName());
        }

        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        dataMap.put("provinceList", provinceResult.getResult());

        ServiceResult<List<Regions>> companyCityResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getCompanyProvince(), null));
        dataMap.put("companyCityList", companyCityResult.getResult());

        ServiceResult<List<Regions>> bankCityResult = regionsService
            .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getBankProvince(), null));
        dataMap.put("bankCityList", bankCityResult.getResult());

        return "admin/seller/audit/sellerapplyedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(SellerApply sellerApply, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        Map<String, String> param = new HashMap<String, String>();
        param.put("seller", "apply");

        //营业执照扫描件
        String bli = UploadUtil.getInstance().uploadFile2ImageServer("up_bussinessLicenseImage",
            request, param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        if (!StringUtil.isEmpty(bli)) {
            sellerApply.setBussinessLicenseImage(bli);
        }

        //身份证正面
        String pcu = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardUp", request,
            param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        if (!StringUtil.isEmpty(pcu)) {
            sellerApply.setPersonCardUp(pcu);
        }

        //身份证反面
        String pdw = UploadUtil.getInstance().uploadFile2ImageServer("up_personCardDown", request,
            param, domainUrlUtil.getImageResources() + "/commonImageUploadServlet");
        if (!StringUtil.isEmpty(pdw)) {
            sellerApply.setPersonCardDown(pdw);
        }

        String userName = request.getParameter("userName");
        String sellerName = request.getParameter("sellerName");

        if (StringUtil.isEmpty(userName, true)) {
            dataMap.put("userName", userName);
            dataMap.put("sellerName", sellerName);
            dataMap.put("sellerApply", sellerApply);
            dataMap.put("message", "商家账号不能为空！");
            ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
            dataMap.put("provinceList", provinceResult.getResult());
            ServiceResult<List<Regions>> companyCityResult = regionsService
                .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getCompanyProvince(), null));
            dataMap.put("companyCityList", companyCityResult.getResult());
            ServiceResult<List<Regions>> bankCityResult = regionsService
                .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getBankProvince(), null));
            dataMap.put("bankCityList", bankCityResult.getResult());
            return "admin/seller/audit/sellerapplyedit";
        }
        if (StringUtil.isEmpty(sellerName, true)) {
            dataMap.put("userName", userName);
            dataMap.put("sellerName", sellerName);
            dataMap.put("sellerApply", sellerApply);
            dataMap.put("message", "商家店铺名称不能为空！");
            ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
            dataMap.put("provinceList", provinceResult.getResult());
            ServiceResult<List<Regions>> companyCityResult = regionsService
                .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getCompanyProvince(), null));
            dataMap.put("companyCityList", companyCityResult.getResult());
            ServiceResult<List<Regions>> bankCityResult = regionsService
                .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getBankProvince(), null));
            dataMap.put("bankCityList", bankCityResult.getResult());
            return "admin/seller/audit/sellerapplyedit";
        }

        ServiceResult<Boolean> serviceResult = sellerApplyService
            .updateSellerApplyForAdmin(sellerApply, userName, sellerName);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("userName", userName);
                dataMap.put("sellerName", sellerName);
                dataMap.put("sellerApply", sellerApply);
                dataMap.put("message", serviceResult.getMessage());
                ServiceResult<List<Regions>> provinceResult = regionsService
                    .getRegionsByParentId(0);
                dataMap.put("provinceList", provinceResult.getResult());
                ServiceResult<List<Regions>> companyCityResult = regionsService
                    .getRegionsByParentId(
                        ConvertUtil.toInt(sellerApply.getCompanyProvince(), null));
                dataMap.put("companyCityList", companyCityResult.getResult());
                ServiceResult<List<Regions>> bankCityResult = regionsService
                    .getRegionsByParentId(ConvertUtil.toInt(sellerApply.getBankProvince(), null));
                dataMap.put("bankCityList", bankCityResult.getResult());
                return "admin/seller/audit/sellerapplyedit";
            }
        }
        return "redirect:/admin/seller/audit";
    }
}
