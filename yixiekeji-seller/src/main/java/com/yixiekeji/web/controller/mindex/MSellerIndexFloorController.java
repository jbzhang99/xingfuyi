package com.yixiekeji.web.controller.mindex;

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
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.mseller.IMSellerIndexService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 移动端首页楼层管理controller
 *                       
 * @Filename: MSellerIndexFloorController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "seller/mindex/floor")
public class MSellerIndexFloorController extends BaseController {

    @Resource
    private IMSellerIndexService mSellerIndexService;

    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 移动端首页楼层列表页
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/mindex/floor/floorlist";
    }

    /**
     * 分页取出数据
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<MSellerIndexFloor>> list(HttpServletRequest request,
                                                                      HttpServletResponse response,
                                                                      Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        queryMap.put("q_sellerId", sellerUser.getSellerId() + "");
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<MSellerIndexFloor>> serviceResult = mSellerIndexService
            .getMSellerIndexFloors(feignUtil);
        pager = serviceResult.getPager();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        List<MSellerIndexFloor> list = serviceResult.getResult();

        HttpJsonResult<List<MSellerIndexFloor>> jsonResult = new HttpJsonResult<List<MSellerIndexFloor>>();
        jsonResult.setRows(list);
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        return "seller/mindex/floor/flooradd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    public String create(MSellerIndexFloor mSellerIndexFloor, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        Integer userId = sellerUser.getId();
        mSellerIndexFloor.setCreateUserId(userId);
        mSellerIndexFloor.setCreateUserName(sellerUser.getName());
        mSellerIndexFloor.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloor.setUpdateUserName(sellerUser.getName());
        mSellerIndexFloor.setSellerId(sellerUser.getSellerId());

        mSellerIndexFloor.setStatus(MSellerIndexFloor.STATUS_0);

        // 上传图片
        String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("advImageFile",
            request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
        if (image != null && !"".equals(image)) {
            mSellerIndexFloor.setAdvImage(image);
        }

        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .saveMSellerIndexFloor(mSellerIndexFloor);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("mSellerIndexFloor", mSellerIndexFloor);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floor/flooradd";
            }
        }
        return "redirect:/seller/mindex/floor";
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, int mSellerIndexFloorId,
                       Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<MSellerIndexFloor> serviceResult = mSellerIndexService
            .getMSellerIndexFloorById(mSellerIndexFloorId);

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floor/floorlist";
            }
        }
        MSellerIndexFloor mSellerIndexFloor = serviceResult.getResult();
        if (mSellerIndexFloor == null) {
            return "seller/404";
        }
        if (!mSellerIndexFloor.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }

        dataMap.put("mSellerIndexFloor", mSellerIndexFloor);
        return "seller/mindex/floor/flooredit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public String update(MSellerIndexFloor mSellerIndexFloor, HttpServletRequest request,
                         Map<String, Object> dataMap) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<MSellerIndexFloor> floorResult = mSellerIndexService
            .getMSellerIndexFloorById(mSellerIndexFloor.getId());

        if (!floorResult.getSuccess()) {
            return "seller/500";
        }
        MSellerIndexFloor dbmSellerIndexFloor = floorResult.getResult();
        if (dbmSellerIndexFloor == null) {
            return "seller/404";
        }
        if (!dbmSellerIndexFloor.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        mSellerIndexFloor.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloor.setUpdateUserName(sellerUser.getName());
        mSellerIndexFloor.setSellerId(sellerUser.getSellerId());

        // 上传图片
        String image = UploadUtil.getInstance().mSellerIndexUploadFile2ImageServer("advImageFile",
            request, domainUrlUtil.getImageResources() + "/imageMobileUtilAdv");
        if (image != null && !"".equals(image)) {
            mSellerIndexFloor.setAdvImage(image);
        }

        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .updateMSellerIndexFloor(mSellerIndexFloor);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("mSellerIndexFloor", mSellerIndexFloor);
                dataMap.put("message", serviceResult.getMessage());
                return "seller/mindex/floor/flooredit";
            }
        }
        return "redirect:/seller/mindex/floor";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Boolean> delete(HttpServletRequest request,
                                                        @RequestParam("id") Integer id) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<MSellerIndexFloor> mSellerIndexFloorResult = mSellerIndexService
            .getMSellerIndexFloorById(id);
        if (!mSellerIndexFloorResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(mSellerIndexFloorResult.getMessage());
        }
        MSellerIndexFloor mSellerIndexFloor = mSellerIndexFloorResult.getResult();
        if (mSellerIndexFloor == null) {
            return new HttpJsonResult<Boolean>("楼层信息获取失败");
        }
        if (!mSellerIndexFloor.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        if (mSellerIndexFloor.getStatus().equals(MSellerIndexFloor.STATUS_1)) {
            return new HttpJsonResult<Boolean>("正在使用的楼层不能删除");
        }

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        ServiceResult<Boolean> serviceResult = mSellerIndexService.deleteMSellerIndexFloor(id);
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
        MSellerIndexFloor mSellerIndexFloor = new MSellerIndexFloor();
        mSellerIndexFloor.setId(id);
        mSellerIndexFloor.setStatus(MSellerIndexFloor.STATUS_1);
        mSellerIndexFloor.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloor.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .updateMSellerIndexFloor(mSellerIndexFloor);
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

        MSellerIndexFloor mSellerIndexFloor = new MSellerIndexFloor();
        mSellerIndexFloor.setId(id);
        mSellerIndexFloor.setStatus(MSellerIndexFloor.STATUS_0);
        mSellerIndexFloor.setUpdateUserId(sellerUser.getId());
        mSellerIndexFloor.setUpdateUserName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = mSellerIndexService
            .updateMSellerIndexFloor(mSellerIndexFloor);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }
}
