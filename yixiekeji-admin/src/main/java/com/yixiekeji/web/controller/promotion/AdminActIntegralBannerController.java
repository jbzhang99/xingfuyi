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
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.integral.ActIntegralBanner;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.promotion.IActIntegralBannerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 积分商城首页轮播图管理
 *                       
 * @Filename: AdminActIntegralBannerController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
@RequestMapping(value = "admin/actintegralbanner/banner", produces = "application/json;charset=UTF-8")
public class AdminActIntegralBannerController extends BaseController {

    @Resource
    private IActIntegralBannerService actIntegralBannerService;

    @Resource
    private DomainUrlUtil             domainUrlUtil;

    /**
     * PC端首页轮播图列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/promotion/integral/integralbannerlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<ActIntegralBanner>> list(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ActIntegralBanner>> serviceResult = actIntegralBannerService
            .getActIntegralBanners(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<ActIntegralBanner> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<ActIntegralBanner>> jsonResult = new HttpJsonResult<List<ActIntegralBanner>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "admin/promotion/integral/integralbanneradd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(ActIntegralBanner actIntegralBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        Integer userId = adminUser.getId();
        actIntegralBanner.setCreateUserId(userId);
        actIntegralBanner.setCreateUserName(adminUser.getName());
        actIntegralBanner.setUpdateUserId(adminUser.getId());
        actIntegralBanner.setUpdateUserName(adminUser.getName());

        actIntegralBanner.setState(ConstantsEJS.YES_NO_0);

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            actIntegralBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = actIntegralBannerService
            .saveActIntegralBanner(actIntegralBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("actIntegralBanner", actIntegralBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/promotion/integral/integralbanneradd";
            }
        }
        return "redirect:/admin/actintegralbanner/banner";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int id, Map<String, Object> dataMap) {
        ServiceResult<ActIntegralBanner> serviceResult = actIntegralBannerService
            .getActIntegralBannerById(id);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "admin/promotion/integral/integralbannerlist";
            }
        }
        ActIntegralBanner actIntegralBanner = serviceResult.getResult();

        dataMap.put("actIntegralBanner", actIntegralBanner);
        return "admin/promotion/integral/integralbanneredit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(ActIntegralBanner actIntegralBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {
        SystemAdmin adminUser = WebAdminSession.getAdminUser(request);
        actIntegralBanner.setUpdateUserId(adminUser.getId());
        actIntegralBanner.setUpdateUserName(adminUser.getName());

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            actIntegralBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = actIntegralBannerService
            .updateActIntegralBanner(actIntegralBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("actIntegralBanner", actIntegralBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "admin/promotion/integral/integralbanneredit";
            }
        }
        return "redirect:/admin/actintegralbanner/banner";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        ServiceResult<ActIntegralBanner> actIntegralBannerResult = actIntegralBannerService
            .getActIntegralBannerById(id);
        if (!actIntegralBannerResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(actIntegralBannerResult.getMessage());
        }
        if (actIntegralBannerResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("轮播图信息获取失败");
        }
        if (actIntegralBannerResult.getResult().getState().equals(ConstantsEJS.YES_NO_1)) {
            return new HttpJsonResult<Boolean>("正在使用的轮播图不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = actIntegralBannerService.deleteActIntegralBanner(id);
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
        ActIntegralBanner actIntegralBanner = new ActIntegralBanner();
        actIntegralBanner.setId(id);
        actIntegralBanner.setState(ConstantsEJS.YES_NO_1);
        actIntegralBanner.setUpdateUserId(adminUser.getId());
        actIntegralBanner.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = actIntegralBannerService
            .updateActIntegralBanner(actIntegralBanner);
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

        ActIntegralBanner actIntegralBanner = new ActIntegralBanner();
        actIntegralBanner.setId(id);
        actIntegralBanner.setState(ConstantsEJS.YES_NO_0);
        actIntegralBanner.setUpdateUserId(adminUser.getId());
        actIntegralBanner.setUpdateUserName(adminUser.getName());
        ServiceResult<Boolean> serviceResult = actIntegralBannerService
            .updateActIntegralBanner(actIntegralBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
