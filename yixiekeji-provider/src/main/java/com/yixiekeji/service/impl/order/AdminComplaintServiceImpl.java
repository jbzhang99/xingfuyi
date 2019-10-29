package com.yixiekeji.service.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.model.order.ComplaintModel;
import com.yixiekeji.service.order.IAdminComplaintService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.SellerComplaintVO;

@RestController
public class AdminComplaintServiceImpl implements IAdminComplaintService {
    private static Logger  log = LoggerFactory.getLogger(AdminComplaintServiceImpl.class);

    @Resource(name = "complaintModel")
    private ComplaintModel complaintModel;

    /**
    * 根据id取得商家投诉管理
    * @param  sellerComplaintId
    * @return
    */
    @Override
    public ServiceResult<SellerComplaint> getSellerComplaintById(@RequestParam("sellerComplaintId") Integer sellerComplaintId) {
        ServiceResult<SellerComplaint> result = new ServiceResult<SellerComplaint>();
        try {
            result.setResult(complaintModel.getSellerComplaintById(sellerComplaintId));
        } catch (Exception e) {
            log.error("根据id[" + sellerComplaintId + "]取得商家投诉管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + sellerComplaintId + "]取得商家投诉管理时出现未知异常");
        }
        return result;
    }

    /**
     * 保存商家投诉管理
     * @param  sellerComplaint
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSellerComplaint(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(complaintModel.saveSellerComplaint(
                feignObjectUtil.getSellerComplaint(), feignObjectUtil.getMember()));
        } catch (Exception e) {
            log.error("保存商家投诉管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存商家投诉管理时出现未知异常");
        }
        return result;
    }

    /**
    * 更新商家投诉管理
    * @param  sellerComplaint
    * @return
    */
    @Override
    public ServiceResult<Integer> updateSellerComplaint(@RequestBody SellerComplaint sellerComplaint) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(complaintModel.updateSellerComplaint(sellerComplaint));
        } catch (Exception e) {
            log.error("更新商家投诉管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新商家投诉管理时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SellerComplaintVO>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SellerComplaintVO>> serviceResult = new ServiceResult<List<SellerComplaintVO>>();
        serviceResult.setPager(pager);
        Map<String, Object> param = new HashMap<String, Object>(queryMap);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(complaintModel.pageCount(param));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            param.put("start", start);
            param.put("size", size);
            List<SellerComplaintVO> list = complaintModel.page(param);

            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerComplaintServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SellerComplaintServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(complaintModel.del(id));
        } catch (Exception e) {
            log.error("[SellerComplaintServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
            sr.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
        }
        return sr;
    }

    @Override
    public ServiceResult<SellerComplaintVO> getById(@RequestParam("id") Integer id) {
        ServiceResult<SellerComplaintVO> res = new ServiceResult<SellerComplaintVO>();
        try {

            res.setResult(complaintModel.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerComplaintServiceImpl][getById] exception:", e);
        }
        return res;
    }

    @Override
    public ServiceResult<Boolean> resetState(@RequestParam("id") Integer id,
                                             @RequestParam("source") Integer source,
                                             @RequestParam("state") Integer state,
                                             @RequestParam("backExchangeId") Integer backExchangeId) {
        ServiceResult<Boolean> res = new ServiceResult<Boolean>();
        try {
            res.setResult(complaintModel.resetState(id, source, state, backExchangeId));
        } catch (BusinessException be) {
            res.setResult(false);
            res.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            res.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerComplaintServiceImpl][resetState] exception:", e);
        }
        return res;
    }
}