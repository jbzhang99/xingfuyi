package com.yixiekeji.service.impl.seller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerComplaint;
import com.yixiekeji.model.seller.SellerComplaintModel;
import com.yixiekeji.service.seller.ISellerComplaintService;
import com.yixiekeji.util.FeignObjectUtil;
import com.yixiekeji.vo.seller.FrontSellerComplaintVO;

/**
 * 投诉管理
 *
 */
@RestController
public class SellerComplaintServiceImpl implements ISellerComplaintService {
    private static Logger        log = LoggerFactory.getLogger(SellerComplaintServiceImpl.class);

    @Resource
    private SellerComplaintModel sellerComplaintModel;

    /**
    * 根据id取得商家投诉管理
    * @param  sellerComplaintId
    * @return
    */
    @Override
    public ServiceResult<SellerComplaint> getSellerComplaintById(@RequestParam("sellerComplaintId") Integer sellerComplaintId) {
        ServiceResult<SellerComplaint> result = new ServiceResult<SellerComplaint>();
        try {
            result.setResult(sellerComplaintModel.getSellerComplaintById(sellerComplaintId));
        } catch (Exception e) {
            log.error("根据id[" + sellerComplaintId + "]取得商家投诉管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + sellerComplaintId + "]取得商家投诉管理时出现未知异常");
        }
        return result;
    }

    /**
     * 保存商家投诉管理
     * @param sellerComplaint
     * @return
     */
    @Override
    public ServiceResult<SellerComplaint> saveSellerComplaint(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<SellerComplaint> serviceResult = new ServiceResult<SellerComplaint>();
        try {
            serviceResult.setResult(sellerComplaintModel.saveSellerComplaint(
                feignObjectUtil.getMember(), feignObjectUtil.getSellerComplaint()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[sellerComplaintService][saveSellerComplaint]保存申诉申请时发生异常:", e);
        }
        return serviceResult;
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
            result.setResult(sellerComplaintModel.updateSellerComplaint(sellerComplaint));
        } catch (Exception e) {
            log.error("更新商家投诉管理时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新商家投诉管理时出现未知异常");
        }
        return result;
    }

    /**
     * 根据登录用户获得申诉列表
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<List<FrontSellerComplaintVO>> getSellerComplaintList(@RequestBody PagerInfo pager,
                                                                              @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<FrontSellerComplaintVO>> serviceResult = new ServiceResult<List<FrontSellerComplaintVO>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(sellerComplaintModel.getSellerComplaintList(pager, memberId));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[sellerComplaintService][getSellerComplaintList]取得用户申诉列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<FrontSellerComplaintVO>> getComplaintListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                                    @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<FrontSellerComplaintVO>> serviceResult = new ServiceResult<List<FrontSellerComplaintVO>>();
        serviceResult.setPager(pager);
        try {
            serviceResult
                .setResult(sellerComplaintModel.getComplaintListWithPrdAndOp(pager, memberId));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[sellerComplaintService][getComplaintListWithPrdAndOp]取得用户申诉列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public Integer addSellerComplaint(@RequestBody SellerComplaint sellerComplaint) {
        return sellerComplaintModel.addSellerComplaint(sellerComplaint);
    }
}