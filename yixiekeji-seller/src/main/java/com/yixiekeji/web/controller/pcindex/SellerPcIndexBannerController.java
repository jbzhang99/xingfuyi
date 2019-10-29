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
import com.yixiekeji.entity.pcseller.PcSellerIndexBanner;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.pcseller.IPcSellerIndexBannerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * PC端商家首页轮播图管理controller
 *                       
 * @Filename: PcSellerIndexBannerController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/pcindex/banner")
public class SellerPcIndexBannerController extends BaseController {

    @Resource
    private IPcSellerIndexBannerService pcSellerIndexBannerService;

    @Resource
    private DomainUrlUtil               domainUrlUtil;

    /**
     * PC端商家首页轮播图列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/pcindex/banner/pcsellerindexbannerlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<PcSellerIndexBanner>> list(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<PcSellerIndexBanner>> serviceResult = pcSellerIndexBannerService
            .getPcSellerIndexBanners(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<PcSellerIndexBanner> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<PcSellerIndexBanner>> jsonResult = new HttpJsonResult<List<PcSellerIndexBanner>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "seller/pcindex/banner/pcsellerindexbanneradd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(PcSellerIndexBanner pcSellerIndexBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        pcSellerIndexBanner.setCreateUserId(userId);
        pcSellerIndexBanner.setCreateUserName(sellerUser.getName());
        pcSellerIndexBanner.setUpdateUserId(sellerUser.getId());
        pcSellerIndexBanner.setUpdateUserName(sellerUser.getName());

        pcSellerIndexBanner.setStatus(PcSellerIndexBanner.STATUS_2);
        pcSellerIndexBanner.setSellerId(sellerUser.getSellerId());

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcSellerIndexBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .savePcSellerIndexBanner(pcSellerIndexBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerIndexBanner", pcSellerIndexBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/banner/pcsellerindexbanneradd";
            }
        }
        return "redirect:/seller/pcindex/banner";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int pcSellerIndexBannerId,
                       Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<PcSellerIndexBanner> serviceResult = pcSellerIndexBannerService
            .getPcSellerIndexBannerById(pcSellerIndexBannerId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/banner/pcsellerindexbannerlist";
            }
        }
        PcSellerIndexBanner pcSellerIndexBanner = serviceResult.getResult();
        if (pcSellerIndexBanner == null) {
            return "seller/404";
        }
        if (!pcSellerIndexBanner.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("pcSellerIndexBanner", pcSellerIndexBanner);
        return "seller/pcindex/banner/pcsellerindexbanneredit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(PcSellerIndexBanner pcSellerIndexBanner, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<PcSellerIndexBanner> pcSellerIndexResult = pcSellerIndexBannerService
            .getPcSellerIndexBannerById(pcSellerIndexBanner.getId());

        if (!pcSellerIndexResult.getSuccess()) {
            return "seller/500";
        }
        PcSellerIndexBanner dbPcSellerIndexBanner = pcSellerIndexResult.getResult();
        if (dbPcSellerIndexBanner == null) {
            return "seller/404";
        }
        if (!dbPcSellerIndexBanner.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        pcSellerIndexBanner.setUpdateUserId(sellerUser.getId());
        pcSellerIndexBanner.setUpdateUserName(sellerUser.getName());

        // 上传图片
        String image = UploadUtil.getInstance().advUploadFile2ImageServer("imageFile", request,
            domainUrlUtil.getImageResources() + "/imageUtilAdv");
        if (image != null && !"".equals(image)) {
            pcSellerIndexBanner.setImage(image);
        }

        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .updatePcSellerIndexBanner(pcSellerIndexBanner);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerIndexBanner", pcSellerIndexBanner);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/banner/pcsellerindexbanneredit";
            }
        }
        return "redirect:/seller/pcindex/banner";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<PcSellerIndexBanner> pcSellerIndexBannerResult = pcSellerIndexBannerService
            .getPcSellerIndexBannerById(id);
        if (!pcSellerIndexBannerResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(pcSellerIndexBannerResult.getMessage());
        }
        if (pcSellerIndexBannerResult.getResult() == null) {
            return new HttpJsonResult<Boolean>("轮播图信息获取失败");
        }
        if (!pcSellerIndexBannerResult.getResult().getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        if (pcSellerIndexBannerResult.getResult().getStatus()
            .equals(PcSellerIndexBanner.STATUS_1)) {
            return new HttpJsonResult<Boolean>("正在使用的轮播图不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .deletePcSellerIndexBanner(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> up(HttpServletRequest request,
                                                   @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        PcSellerIndexBanner pcSellerIndexBanner = new PcSellerIndexBanner();
        pcSellerIndexBanner.setId(id);
        pcSellerIndexBanner.setStatus(PcSellerIndexBanner.STATUS_1);
        pcSellerIndexBanner.setUpdateUserId(sellerUser.getId());
        pcSellerIndexBanner.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .updatePcSellerIndexBanner(pcSellerIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "pre", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> pre(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        PcSellerIndexBanner pcSellerIndexBanner = new PcSellerIndexBanner();
        pcSellerIndexBanner.setId(id);
        pcSellerIndexBanner.setStatus(PcSellerIndexBanner.STATUS_2);
        pcSellerIndexBanner.setUpdateUserId(sellerUser.getId());
        pcSellerIndexBanner.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .updatePcSellerIndexBanner(pcSellerIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> down(HttpServletRequest request,
                                                     @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        PcSellerIndexBanner pcSellerIndexBanner = new PcSellerIndexBanner();
        pcSellerIndexBanner.setId(id);
        pcSellerIndexBanner.setStatus(PcSellerIndexBanner.STATUS_3);
        pcSellerIndexBanner.setUpdateUserId(sellerUser.getId());
        pcSellerIndexBanner.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = pcSellerIndexBannerService
            .updatePcSellerIndexBanner(pcSellerIndexBanner);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
