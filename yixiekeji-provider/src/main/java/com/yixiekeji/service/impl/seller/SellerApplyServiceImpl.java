package com.yixiekeji.service.impl.seller;

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
import com.yixiekeji.entity.seller.SellerApply;
import com.yixiekeji.model.seller.SellerApplyModel;
import com.yixiekeji.service.seller.ISellerApplyService;

@RestController
public class SellerApplyServiceImpl implements ISellerApplyService {

    private static Logger    log = LoggerFactory.getLogger(SellerApplyServiceImpl.class);

    @Resource
    private SellerApplyModel sellerApplyModel;

    @Override
    public ServiceResult<Boolean> updateSellerApply(@RequestBody SellerApply sellerApply) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerApplyModel.updateSellerApply(sellerApply));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerApplyService][updateSellerApply]修改商家申请时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[SellerApplyService][updateSellerApply] param:" + JSON.toJSONString(sellerApply));
            log.error("[SellerApplyService][updateSellerApply]修改商家申请时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerApply>> getSellerApplys(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SellerApply>> serviceResult = new ServiceResult<List<SellerApply>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerApplyModel.getSellerApplysCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<SellerApply> list = sellerApplyModel.getSellerApplys(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[SellerApplyService][getSellerApplys]根据条件分页查询商家申请时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerApplyService][getSellerApplys] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SellerApplyService][getSellerApplys] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> deleteSellerApply(@RequestParam("id") Integer id,
                                                    @RequestParam("memberId") Integer memberId) {

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerApplyModel.delete(id, memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerApplyService][deleteSellerApply]删除商家申请时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][deleteSellerApply]删除商家申请时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SellerApply> getSellerApplyById(@RequestParam("sellerApplyId") Integer sellerApplyId) {
        ServiceResult<SellerApply> serviceResult = new ServiceResult<SellerApply>();
        try {
            serviceResult.setResult(sellerApplyModel.getSellerApplyById(sellerApplyId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerApplyService][getSellerApplyById]根据id[" + sellerApplyId
                      + "]取得商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][getSellerApplyById]根据id[" + sellerApplyId
                      + "]取得商家申请表时出现未知异常：",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> saveSellerApply(@RequestBody SellerApply sellerApply) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(sellerApplyModel.saveSellerApply(sellerApply));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerApplyService][saveSellerApply]保存商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][saveSellerApply]保存商家申请表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<SellerApply> getSellerApplyByUser(@RequestParam("memberId") Integer memberId) {
        ServiceResult<SellerApply> serviceResult = new ServiceResult<SellerApply>();
        try {
            serviceResult.setResult(sellerApplyModel.getSellerApplyByUser(memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerApplyService][getSellerApplyByUser]根据用户ID获取其商家入驻申请时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][getSellerApplyByUser]根据用户ID获取其商家入驻申请时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> auditSellerApply(@RequestParam("sellerApplyId") Integer sellerApplyId,
                                                   @RequestParam("state") Integer state,
                                                   @RequestParam("optUserId") Integer optUserId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(sellerApplyModel.auditSellerApply(sellerApplyId, state, optUserId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[SellerApplyService][auditSellerApply]审核商家入驻申请时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][auditSellerApply]审核商家入驻申请时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveSellerApplyForAdmin(@RequestBody SellerApply sellerApply,
                                                          @RequestParam("userName") String userName,
                                                          @RequestParam("sellerName") String sellerName,
                                                          @RequestParam("ip") String ip) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                sellerApplyModel.saveSellerApplyForAdmin(sellerApply, userName, sellerName, ip));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerApplyService][saveSellerApplyForAdmin]平台保存商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][saveSellerApplyForAdmin]平台保存商家申请表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateSellerApplyForAdmin(@RequestBody SellerApply sellerApply,
                                                            @RequestParam("userName") String userName,
                                                            @RequestParam("sellerName") String sellerName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                sellerApplyModel.updateSellerApplyForAdmin(sellerApply, userName, sellerName));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerApplyService][updateSellerApplyForAdmin]平台修改商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][updateSellerApplyForAdmin]平台修改商家申请表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveSellerApplyForFront(@RequestBody SellerApply sellerApply,
                                                          @RequestParam("userName") String userName,
                                                          @RequestParam("sellerName") String sellerName,
                                                          @RequestParam("memberId") Integer memberId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(sellerApplyModel.saveSellerApplyForFront(sellerApply, userName,
                sellerName, memberId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerApplyService][saveSellerApplyForFront]用户端保存商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][saveSellerApplyForFront]用户端保存商家申请表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateSellerApplyForFront(@RequestBody SellerApply sellerApply,
                                                            @RequestParam("userName") String userName,
                                                            @RequestParam("sellerName") String sellerName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                sellerApplyModel.updateSellerApplyForFront(sellerApply, userName, sellerName));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[SellerApplyService][updateSellerApplyForFront]用户端修改商家申请表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[SellerApplyService][updateSellerApplyForFront]用户端修改商家申请表时出现未知异常：", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SellerApply>> getSellerApplyByCompany(@RequestParam("company") String company) {
        ServiceResult<List<SellerApply>> serviceResult = new ServiceResult<List<SellerApply>>();
        try {
            List<SellerApply> list = sellerApplyModel.getSellerApplyByCompany(company);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
            log.error("[SellerApplyService][getSellerApplysByCompany]根据公司名称查询入驻申请时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerApplyService][getSellerApplysByCompany] param1:" + company);
            log.error("[SellerApplyService][getSellerApplysByCompany] 根据公司名称查询入驻申请时出现未知异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getSellerApplyCount() {
        ServiceResult<Integer> serviceResult = new ServiceResult<>();
        try {
            Integer res = sellerApplyModel.getSellerApplyCount();
            serviceResult.setResult(res);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
        }
        return serviceResult;
    }
}
