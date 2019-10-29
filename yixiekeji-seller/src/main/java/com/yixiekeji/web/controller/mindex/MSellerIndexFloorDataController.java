package com.yixiekeji.web.controller.mindex;

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
import com.yixiekeji.entity.mseller.MSellerIndexFloor;
import com.yixiekeji.entity.mseller.MSellerIndexFloorData;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.mseller.IMSellerIndexService;
import com.yixiekeji.service.product.IProductService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 移动端首页楼层数据管理controller
 *                       
 * @Filename: MSellerIndexFloorDataController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/mindex/floordata")
public class MSellerIndexFloorDataController extends BaseController {

    @Resource
    private IMSellerIndexService mSellerIndexService;

    @Resource
    private IProductService      productService;

    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 移动端首页楼层数据列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<MSellerIndexFloor>> serviceResult = mSellerIndexService
            .getMSellerIndexFloors(feignUtil);
        dataMap.put("floors", serviceResult.getResult());

        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/mindex/floordata/floordatalist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MSellerIndexFloorData>> list(HttpServletRequest request,
                                                                          HttpServletResponse response,
                                                                          Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MSellerIndexFloorData>> serviceResult = mSellerIndexService
            .getMSellerIndexFloorDatas(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<MSellerIndexFloorData> list = serviceResult.getResult();
        pager = serviceResult.getPager();

        HttpJsonResult<List<MSellerIndexFloorData>> jsonResult = new HttpJsonResult<List<MSellerIndexFloorData>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<MSellerIndexFloor>> serviceResult = mSellerIndexService
            .getMSellerIndexFloors(feignUtil);
        dataMap.put("floors", serviceResult.getResult());

        dataMap.put("sellerId", sellerUser.getSellerId());

        return "seller/mindex/floordata/floordataadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(MSellerIndexFloorData mSellerIndexFloorData, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        mSellerIndexFloorData.setCreateUserId(userId);
        mSellerIndexFloorData.setCreateUserName(sellerUser.getName());
        mSellerIndexFloorData.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloorData.setUpdateUserName(sellerUser.getName());
        mSellerIndexFloorData.setSellerId(sellerUser.getSellerId());

        if (mSellerIndexFloorData.getDataType() == MSellerIndexFloorData.DATA_TYPE_1) {
            mSellerIndexFloorData.setImage(null);
            mSellerIndexFloorData.setLinkUrl(null);
        } else if (mSellerIndexFloorData.getDataType() == MSellerIndexFloorData.DATA_TYPE_2) {
            // 上传图片
            String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("imageFile",
                request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
            if (image != null && !"".equals(image)) {
                mSellerIndexFloorData.setImage(image);
            }
            mSellerIndexFloorData.setRefId(0);
        }

        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .saveMSellerIndexFloorData(mSellerIndexFloorData);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("mSellerIndexFloorData", mSellerIndexFloorData);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floordata/floordataadd";
            }
        }
        return "redirect:/seller/mindex/floordata";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(int mSellerIndexFloorDataId, Map<String, Object> dataMap,
                       HttpServletRequest request) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        dataMap.put("sellerId", sellerUser.getSellerId());
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, null);

        ServiceResult<List<MSellerIndexFloor>> floorResult = mSellerIndexService
            .getMSellerIndexFloors(feignUtil);
        dataMap.put("floors", floorResult.getResult());

        ServiceResult<MSellerIndexFloorData> serviceResult = mSellerIndexService
            .getMSellerIndexFloorDataById(mSellerIndexFloorDataId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floordata/floordatalist";
            }
        }
        MSellerIndexFloorData mSellerIndexFloorData = serviceResult.getResult();
        if (mSellerIndexFloorData == null) {
            return "seller/404";
        }
        if (!mSellerIndexFloorData.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        if (mSellerIndexFloorData.getDataType() == MSellerIndexFloorData.DATA_TYPE_1) {
            ServiceResult<Product> productResult = productService
                .getProductById(mSellerIndexFloorData.getRefId());
            mSellerIndexFloorData.setProduct(productResult.getResult());
        }

        dataMap.put("mSellerIndexFloorData", mSellerIndexFloorData);
        return "seller/mindex/floordata/floordataedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(MSellerIndexFloorData mSellerIndexFloorData, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<MSellerIndexFloorData> floorDataResult = mSellerIndexService
            .getMSellerIndexFloorDataById(mSellerIndexFloorData.getId());

        if (!floorDataResult.getSuccess()) {
            return "seller/500";
        }
        MSellerIndexFloorData dbMSellerIndexFloorData = floorDataResult.getResult();
        if (dbMSellerIndexFloorData == null) {
            return "seller/404";
        }
        if (!dbMSellerIndexFloorData.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        mSellerIndexFloorData.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloorData.setUpdateUserName(sellerUser.getName());
        mSellerIndexFloorData.setSellerId(sellerUser.getSellerId());

        if (mSellerIndexFloorData.getDataType() == MSellerIndexFloorData.DATA_TYPE_1) {
            mSellerIndexFloorData.setImage("");
            mSellerIndexFloorData.setLinkUrl("");
        } else if (mSellerIndexFloorData.getDataType() == MSellerIndexFloorData.DATA_TYPE_2) {
            // 上传图片
            String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("imageFile",
                request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
            if (image != null && !"".equals(image)) {
                mSellerIndexFloorData.setImage(image);
            }
            mSellerIndexFloorData.setRefId(0);
        }

        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .updateMSellerIndexFloorData(mSellerIndexFloorData);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("mSellerIndexFloorData", mSellerIndexFloorData);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floordata/floordataedit";
            }
        }
        return "redirect:/seller/mindex/floordata";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<MSellerIndexFloorData> floorDataResult = mSellerIndexService
            .getMSellerIndexFloorDataById(id);

        if (!floorDataResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(floorDataResult.getMessage());
        }
        MSellerIndexFloorData dbMSellerIndexFloorData = floorDataResult.getResult();
        if (dbMSellerIndexFloorData == null) {
            return new HttpJsonResult<Boolean>("获取数据失败，请重试");
        }
        if (!dbMSellerIndexFloorData.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = mSellerIndexService.deleteMSellerIndexFloorData(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
