package com.yixiekeji.web.controller.pcindex;

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
import com.yixiekeji.entity.pcindex.PcIndexBanner;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.pcindex.IPcIndexBannerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * PC端首页轮播图管理controller
 *                       
 * @Filename: PcIndexBannerController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/pcindex/banner", produces = "application/json;charset=UTF-8")
public class PcIndexBannerController extends BaseController {

    @Resource
    private IPcIndexBannerService pcIndexBannerService;

    @Resource
    private DomainUrlUtil         domainUrlUtil;

    /**
     * PC端首页轮播图列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/pcindex/banner/pcindexbannerlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<PcIndexBanner>> list(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<PcIndexBanner>> serviceResult = pcIndexBannerService
            .getPcIndexBanners(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<PcIndexBanner> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<PcIndexBanner>> jsonResult = new HttpJsonResult<List<PcIndexBanner>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "admin/pcindex/banner/pcindexbanneradd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(PcIndexBanner pcIndexBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        Integer userId = adminUser.getId();
        pcIndexBanner.setCreateUserId(userId);
        pcIndexBanner.setCreateUserName(adminUser.getName());
        pcIndexBanner.setUpdateUserId(adminUser.getId());
        pcIndexBanner.setUpdateUserName(adminUser.getName());

        pcIndexBanner.setStatus(PcIndexBanner.STATUS_2);

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcIndexBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcIndexBannerService
            .savePcIndexBanner(pcIndexBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcIndexBanner", pcIndexBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/banner/pcindexbanneradd";
            }
        }
        return "redirect:/admin/pcindex/banner";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int pcIndexBannerId, Map<String, Object> dataMap) {
        ServiceResult<PcIndexBanner> serviceResult = pcIndexBannerService
            .getPcIndexBannerById(pcIndexBannerId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/banner/pcindexbannerlist";
            }
        }
        PcIndexBanner pcIndexBanner = serviceResult.getResult();

        dataMap.put("pcIndexBanner", pcIndexBanner);
        return "admin/pcindex/banner/pcindexbanneredit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(PcIndexBanner pcIndexBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        pcIndexBanner.setUpdateUserId(adminUser.getId());
        pcIndexBanner.setUpdateUserName(adminUser.getName());

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcIndexBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcIndexBannerService
            .updatePcIndexBanner(pcIndexBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcIndexBanner", pcIndexBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/banner/pcindexbanneredit";
            }
        }
        return "redirect:/admin/pcindex/banner";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        ServiceResult<PcIndexBanner> pcIndexBannerResult = pcIndexBannerService
            .getPcIndexBannerById(id);
        if (!pcIndexBannerResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(pcIndexBannerResult.getMessage());
        }
        if (pcIndexBannerResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("轮播图信息获取失败");
        }
        if (pcIndexBannerResult.getResult().getStatus().equals(PcIndexBanner.STATUS_1)) {
            return new HttpJsonResult<Boolean>("正在使用的轮播图不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = pcIndexBannerService.deletePcIndexBanner(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> up(HttpServletRequest request,
                                                   @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        PcIndexBanner pcIndexBanner = new PcIndexBanner();
        pcIndexBanner.setId(id);
        pcIndexBanner.setStatus(PcIndexBanner.STATUS_1);
        pcIndexBanner.setUpdateUserId(adminUser.getId());
        pcIndexBanner.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexBannerService
            .updatePcIndexBanner(pcIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "pre", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> pre(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        PcIndexBanner pcIndexBanner = new PcIndexBanner();
        pcIndexBanner.setId(id);
        pcIndexBanner.setStatus(PcIndexBanner.STATUS_2);
        pcIndexBanner.setUpdateUserId(adminUser.getId());
        pcIndexBanner.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexBannerService
            .updatePcIndexBanner(pcIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> down(HttpServletRequest request,
                                                     @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);

        PcIndexBanner pcIndexBanner = new PcIndexBanner();
        pcIndexBanner.setId(id);
        pcIndexBanner.setStatus(PcIndexBanner.STATUS_3);
        pcIndexBanner.setUpdateUserId(adminUser.getId());
        pcIndexBanner.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexBannerService
            .updatePcIndexBanner(pcIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
