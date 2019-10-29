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
import com.yixiekeji.entity.pcindex.PcIndexImage;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.pcindex.IPcIndexImageService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * PC端首页轮播图管理controller
 *                       
 * @Filename: PcIndexImageController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "admin/pcindex/image", produces = "application/json;charset=UTF-8")
public class PcIndexImageController extends BaseController {

    @Resource
    private IPcIndexImageService pcIndexImageService;

    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * PC端首页轮播图列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/pcindex/image/pcindeximagelist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<List<PcIndexImage>> list(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<PcIndexImage>> serviceResult = pcIndexImageService
            .getPcIndexImages(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<PcIndexImage> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<PcIndexImage>> jsonResult = new HttpJsonResult<List<PcIndexImage>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "admin/pcindex/image/pcindeximageadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(PcIndexImage pcIndexImage, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        Integer userId = adminUser.getId();
        pcIndexImage.setCreateUserId(userId);
        pcIndexImage.setCreateUserName(adminUser.getName());
        pcIndexImage.setUpdateUserId(adminUser.getId());
        pcIndexImage.setUpdateUserName(adminUser.getName());

        pcIndexImage.setStatus(PcIndexImage.STATUS_2);

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcIndexImage.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcIndexImageService.savePcIndexImage(pcIndexImage);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcIndexImage", pcIndexImage);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/image/pcindeximageadd";
            }
        }
        return "redirect:/admin/pcindex/image";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int pcIndexImageId, Map<String, Object> dataMap) {
        ServiceResult<PcIndexImage> serviceResult = pcIndexImageService
            .getPcIndexImageById(pcIndexImageId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/image/pcindeximagelist";
            }
        }
        PcIndexImage pcIndexImage = serviceResult.getResult();

        dataMap.put("pcIndexImage", pcIndexImage);
        return "admin/pcindex/image/pcindeximageedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(PcIndexImage pcIndexImage, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        pcIndexImage.setUpdateUserId(adminUser.getId());
        pcIndexImage.setUpdateUserName(adminUser.getName());

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcIndexImage.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcIndexImageService.updatePcIndexImage(pcIndexImage);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcIndexImage", pcIndexImage);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/pcindex/image/pcindeximageedit";
            }
        }
        return "redirect:/admin/pcindex/image";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        ServiceResult<PcIndexImage> pcIndexImageResult = pcIndexImageService
            .getPcIndexImageById(id);
        if (!pcIndexImageResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(pcIndexImageResult.getMessage());
        }
        if (pcIndexImageResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("轮播图信息获取失败");
        }
        if (pcIndexImageResult.getResult().getStatus().equals(PcIndexImage.STATUS_1)) {
            return new HttpJsonResult<Boolean>("正在使用的轮播图不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = pcIndexImageService.deletePcIndexImage(id);
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
        PcIndexImage pcIndexImage = new PcIndexImage();
        pcIndexImage.setId(id);
        pcIndexImage.setStatus(PcIndexImage.STATUS_1);
        pcIndexImage.setUpdateUserId(adminUser.getId());
        pcIndexImage.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexImageService.updatePcIndexImage(pcIndexImage);
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
        PcIndexImage pcIndexImage = new PcIndexImage();
        pcIndexImage.setId(id);
        pcIndexImage.setStatus(PcIndexImage.STATUS_2);
        pcIndexImage.setUpdateUserId(adminUser.getId());
        pcIndexImage.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexImageService.updatePcIndexImage(pcIndexImage);
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

        PcIndexImage pcIndexImage = new PcIndexImage();
        pcIndexImage.setId(id);
        pcIndexImage.setStatus(PcIndexImage.STATUS_3);
        pcIndexImage.setUpdateUserId(adminUser.getId());
        pcIndexImage.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = pcIndexImageService.updatePcIndexImage(pcIndexImage);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
