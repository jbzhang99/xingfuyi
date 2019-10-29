package com.yixiekeji.web.controller.pcindex;

import java.util.HashMap;
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
import com.yixiekeji.entity.pcseller.PcSellerRecommend;
import com.yixiekeji.entity.pcseller.PcSellerRecommendData;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.pcseller.IPcSellerRecommendDataService;
import com.yixiekeji.service.pcseller.IPcSellerRecommendService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * PC端商家推荐数据管理controller
 *                       
 * @Filename: SellerPcRecommendDataController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/pcindex/recommenddata")
public class SellerPcRecommendDataController extends BaseController {

    @Resource
    private IPcSellerRecommendDataService pcSellerRecommendDataService;
    @Resource
    private IPcSellerRecommendService     pcSellerRecommendService;

    @Resource
    private DomainUrlUtil                 domainUrlUtil;

    /**
     * PC端商家推荐数据列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) {

        Map<String, String> queryMap = new HashMap<String, String>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<PcSellerRecommend>> serviceResult = pcSellerRecommendService
            .getPcSellerRecommends(feignUtil);
        dataMap.put("recommends", serviceResult.getResult());

        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/pcindex/recommenddata/pcsellerrecommenddatalist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<PcSellerRecommendData>> list(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<PcSellerRecommendData>> serviceResult = pcSellerRecommendDataService
            .getPcSellerRecommendDatas(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<PcSellerRecommendData> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<PcSellerRecommendData>> jsonResult = new HttpJsonResult<List<PcSellerRecommendData>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        Map<String, String> queryMap = new HashMap<String, String>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<PcSellerRecommend>> serviceResult = pcSellerRecommendService
            .getPcSellerRecommends(feignUtil);
        dataMap.put("recommends", serviceResult.getResult());

        dataMap.put("sellerId", sellerUser.getSellerId());

        return "seller/pcindex/recommenddata/pcsellerrecommenddataadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(PcSellerRecommendData pcSellerRecommendData, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        pcSellerRecommendData.setCreateUserId(userId);
        pcSellerRecommendData.setCreateUserName(sellerUser.getName());
        pcSellerRecommendData.setUpdateUserId(sellerUser.getId());
        pcSellerRecommendData.setUpdateUserName(sellerUser.getName());
        pcSellerRecommendData.setSellerId(sellerUser.getSellerId());

        if (pcSellerRecommendData.getDataType() == PcSellerRecommendData.DATA_TYPE_1) {
            pcSellerRecommendData.setImage(null);
            pcSellerRecommendData.setLinkUrl(null);
        } else if (pcSellerRecommendData.getDataType() == PcSellerRecommendData.DATA_TYPE_2) {
            // 上传图片
            String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("imageFile",
                request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
            if (image != null && !"".equals(image)) {
                pcSellerRecommendData.setImage(image);
            }
            pcSellerRecommendData.setRefId(0);
        }

        ServiceResult<Boolean> serviceResult = pcSellerRecommendDataService
            .savePcSellerRecommendData(pcSellerRecommendData);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerRecommendData", pcSellerRecommendData);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommenddata/pcsellerrecommenddataadd";
            }
        }
        return "redirect:/seller/pcindex/recommenddata";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int pcSellerRecommendDataId, Map<String, Object> dataMap,
                       HttpServletRequest request) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        dataMap.put("sellerId", sellerUser.getSellerId());
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<PcSellerRecommend>> recommendResult = pcSellerRecommendService
            .getPcSellerRecommends(feignUtil);
        dataMap.put("recommends", recommendResult.getResult());

        ServiceResult<PcSellerRecommendData> serviceResult = pcSellerRecommendDataService
            .getPcSellerRecommendDataById(pcSellerRecommendDataId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommenddata/pcsellerrecommenddatalist";
            }
        }
        PcSellerRecommendData pcSellerRecommendData = serviceResult.getResult();
        if (pcSellerRecommendData == null) {
            return "seller/404";
        }
        if (!pcSellerRecommendData.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("pcSellerRecommendData", pcSellerRecommendData);
        return "seller/pcindex/recommenddata/pcsellerrecommenddataedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(PcSellerRecommendData pcSellerRecommendData, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<PcSellerRecommendData> pcSellerRecommendDataResult = pcSellerRecommendDataService
            .getPcSellerRecommendDataById(pcSellerRecommendData.getId());

        if (!pcSellerRecommendDataResult.getSuccess()) {
            return "seller/500";
        }
        PcSellerRecommendData dbpcSellerRecommendData = pcSellerRecommendDataResult.getResult();
        if (dbpcSellerRecommendData == null) {
            return "seller/404";
        }
        if (!dbpcSellerRecommendData.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        pcSellerRecommendData.setUpdateUserId(sellerUser.getId());
        pcSellerRecommendData.setUpdateUserName(sellerUser.getName());
        pcSellerRecommendData.setSellerId(sellerUser.getSellerId());

        if (pcSellerRecommendData.getDataType() == PcSellerRecommendData.DATA_TYPE_1) {
            pcSellerRecommendData.setImage("");
            pcSellerRecommendData.setLinkUrl("");
        } else if (pcSellerRecommendData.getDataType() == PcSellerRecommendData.DATA_TYPE_2) {
            // 上传图片
            String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("imageFile",
                request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
            if (image != null && !"".equals(image)) {
                pcSellerRecommendData.setImage(image);
            }
            pcSellerRecommendData.setRefId(0);
        }

        ServiceResult<Boolean> serviceResult = pcSellerRecommendDataService
            .updatePcSellerRecommendData(pcSellerRecommendData);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("pcSellerRecommendData", pcSellerRecommendData);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/pcindex/recommenddata/pcsellerrecommenddataedit";
            }
        }
        return "redirect:/seller/pcindex/recommenddata";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = pcSellerRecommendDataService
            .deletePcSellerRecommendData(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
