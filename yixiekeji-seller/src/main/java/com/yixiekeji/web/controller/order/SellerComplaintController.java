package com.yixiekeji.web.controller.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.order.IAdminComplaintService;
import com.yixiekeji.vo.seller.SellerComplaintVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 投诉管理相关action
 */
@Controller
@RequestMapping(value = "seller/order/complaint")
public class SellerComplaintController extends BaseController {
    @Resource
    IAdminComplaintService sellerComplaintService;

    @Resource
    private DomainUrlUtil  domainUrlUtil;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        SellerUser seller = WebSellerSession.getSellerUser(request);
        if (null == seller) {
            return "seller/login.html";
        }
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);
        return "seller/order/complaint/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerComplaintVO>> list(HttpServletRequest request,
                                                                      Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        SellerUser seller = WebSellerSession.getSellerUser(request);
        queryMap.put("sellerId", String.valueOf(seller.getSellerId()));
        //queryMap.put("q_state", String.valueOf(ConstantsEJS.SELLER_COMPLAINT_3));
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SellerComplaintVO>> serviceResult = sellerComplaintService
            .page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SellerComplaintVO>> jsonResult = new HttpJsonResult<List<SellerComplaintVO>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 审核页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "auditPage", method = { RequestMethod.GET })
    public String auditPage(HttpServletRequest request, Map<String, Object> dataMap, String id) {
        SellerUser user = WebSellerSession.getSellerUser(request);
        ServiceResult<SellerComplaintVO> serviceResult = sellerComplaintService
            .getById(Integer.valueOf(id));
        SellerComplaintVO sellerComplaintVO = serviceResult.getResult();
        if (sellerComplaintVO == null) {
            return "seller/404";
        }
        if (!sellerComplaintVO.getSellerId().equals(user.getSellerId())) {
            return "seller/unauth";
        }
        dataMap.put("obj", serviceResult.getResult());
        return "seller/order/complaint/audit";
    }

    /**
     * 投诉仲裁
     * @param request
     * @param dataMap
     * @param id
     * @param optContent
     * @return
     */
    @RequestMapping(value = "doAudit", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> doAudit(MultipartHttpServletRequest request,
                                          Map<String, Object> dataMap, String id, String optContent,
                                          String stateType) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        try {
            SellerUser seller = WebSellerSession.getSellerUser(request);
            ServiceResult<SellerComplaint> serviceResult = sellerComplaintService
                .getSellerComplaintById(Integer.valueOf(id));
            SellerComplaint sc = serviceResult.getResult();
            if (sc == null) {
                return new HttpJsonResult<Object>("获取申诉信息失败，请重试。");
            }
            if (!sc.getSellerId().equals(seller.getSellerId())) {
                return new HttpJsonResult<Object>("申诉发生异常,请联系管理员。");
            }
            sc.setSellerId(seller.getSellerId());
            sc.setSellerComplaintTime(new Date());
            sc.setSellerCompContent(optContent);
            sc.setSellerCompImage(UploadUtil.getInstance().advUploadFile2ImageServer("pic", request,
                domainUrlUtil.getImageResources() + "/imageUtilAdv"));
            sc.setState(ConstantsEJS.SELLER_COMPLAINT_4);
            ServiceResult<Integer> serviceResult1 = sellerComplaintService
                .updateSellerComplaint(sc);
            if (!serviceResult1.getSuccess() && serviceResult1.getResult() == 0) {
                jsonResult.setMessage("申诉发生异常,请联系管理员");
            }
        } catch (Exception e) {
            jsonResult.setMessage("申诉发生异常,请联系管理员");
        }

        return jsonResult;
    }
}
