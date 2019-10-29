package com.yixiekeji.service.impl.member;

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
import com.yixiekeji.entity.member.MemberProductBack;
import com.yixiekeji.model.member.MemberProductBackModel;
import com.yixiekeji.service.member.IMemberProductBackService;
import com.yixiekeji.util.FeignObjectUtil;

/**
 * 会员退货管理
 *
 */
@RestController
public class MemberProductBackServiceImpl implements IMemberProductBackService {
    private static Logger          log = LoggerFactory
        .getLogger(MemberProductBackServiceImpl.class);

    @Resource
    private MemberProductBackModel memberProductBackModel;

    @Override
    public ServiceResult<MemberProductBack> getMemberProductBackById(@RequestParam("memberProductBackId") Integer memberProductBackId) {
        ServiceResult<MemberProductBack> serviceResult = new ServiceResult<MemberProductBack>();
        try {
            serviceResult
                .setResult(memberProductBackModel.getMemberProductBackById(memberProductBackId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[memberProductBackService][getMemberProductBackById]根据id["
                      + memberProductBackId + "]取得用户退货时出现未知异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][getMemberProductBackById]根据id["
                      + memberProductBackId + "]取得用户退货时出现未知异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateMemberProductBack(@RequestBody MemberProductBack memberProductBack) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberProductBackModel.updateMemberProductBack(memberProductBack));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][updateMemberProductBack]修改产品退货申请时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> saveMemberProductBack(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductBackModel.saveMemberProductBack(
                feignObjectUtil.getMemberProductBack(), feignObjectUtil.getMember()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][saveMemberProductBack]保存产品退货申请时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberProductBack>> getMemberProductBackList(@RequestBody PagerInfo pager,
                                                                           @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<MemberProductBack>> serviceResult = new ServiceResult<List<MemberProductBack>>();
        serviceResult.setPager(pager);
        try {
            serviceResult
                .setResult(memberProductBackModel.getMemberProductBackList(pager, memberId));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][getMemberProductList]取得用户退货列表时发生异常:", e);
        }
        return serviceResult;
    }

    /**
     * 判断是否可以发起退换货申请
     * @param orderId
     * @param orderProductId
     * @param request
     * @return
     */
    @Override
    public ServiceResult<Integer> canApplyProductBackOrExchange(@RequestParam("orderId") Integer orderId,
                                                                @RequestParam("orderProductId") Integer orderProductId,
                                                                @RequestParam("memberId") Integer memberId) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(memberProductBackModel.canApplyProductBackOrExchange(orderId,
                orderProductId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][saveMemberProductBack]保存产品退货申请时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberProductBack>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberProductBack>> serviceResult = new ServiceResult<List<MemberProductBack>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberProductBackModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<MemberProductBack> list = memberProductBackModel.page(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[MemberProductBackServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[MemberProductBackServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> backMoney(@RequestParam("memberProductBackId") Integer memberProductBackId,
                                            @RequestParam("optId") Integer optId,
                                            @RequestParam("optName") String optName) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberProductBackModel.backMoney(memberProductBackId, optId, optName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[memberProductBackService][backMoney]用户退货申请退款时出现未知异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][backMoney]用户退货申请退款时出现未知异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberProductBack>> getSettleBacks(@RequestParam("sellerId") Integer sellerId,
                                                                 @RequestParam("startTime") String startTime,
                                                                 @RequestParam("endTime") String endTime,
                                                                 @RequestBody PagerInfo pager) {

        ServiceResult<List<MemberProductBack>> serviceResult = new ServiceResult<List<MemberProductBack>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(
                    memberProductBackModel.getSettleBacksCount(sellerId, startTime, endTime));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<MemberProductBack> list = memberProductBackModel.getSettleBacks(sellerId,
                startTime, endTime, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[MemberProductBackServiceImpl][getSettleBacks] exception:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<List<MemberProductBack>> getBackListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                          @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<MemberProductBack>> serviceResult = new ServiceResult<List<MemberProductBack>>();
        serviceResult.setPager(pager);
        try {
            serviceResult
                .setResult(memberProductBackModel.getBackListWithPrdAndOp(pager, memberId));
            return serviceResult;
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][getBackListWithPrdAndOp]取得用户退货列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> audit(@RequestBody MemberProductBack memberProductBack) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductBackModel.audit(memberProductBack));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[memberProductBackService][getMemberProductBackById]审核用户退货时出现未知异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][getMemberProductBackById]审核用户退货时出现未知异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> doBackDeliverGoods(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductBackModel.doBackDeliverGoods(
                feignObjectUtil.getMemberProductBack(), feignObjectUtil.getMember()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[memberProductBackService][getMemberProductBackById]用户退货单发货时出现未知异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][getMemberProductBackById]用户退货单发货时时出现未知异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> takeDeliver(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductBackModel.takeDeliver(
                feignObjectUtil.getMemberProductBack(), feignObjectUtil.getSellerUser()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[memberProductBackService][takeDeliver]商家收取退货物件时出现未知异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductBackService][takeDeliver]根商家收取退货物件时出现未知异常:", e);
        }
        return serviceResult;
    }

}