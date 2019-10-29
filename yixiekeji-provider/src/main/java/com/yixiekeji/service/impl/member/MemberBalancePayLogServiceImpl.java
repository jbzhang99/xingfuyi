package com.yixiekeji.service.impl.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberBalancePayLog;
import com.yixiekeji.model.member.MemberBalancePayLogModel;
import com.yixiekeji.service.member.IMemberBalancePayLogService;

@RestController
public class MemberBalancePayLogServiceImpl implements IMemberBalancePayLogService {
    private static Logger            log = LoggerFactory
        .getLogger(MemberBalancePayLogServiceImpl.class);

    @Resource
    private MemberBalancePayLogModel memberBalancePayLogModel;

    /**
    * 根据id取得会员充值记录
    * @param  memberBalancePayLogId
    * @return
    */
    @Override
    public ServiceResult<MemberBalancePayLog> getMemberBalancePayLogById(@RequestParam("memberBalancePayLogId") Integer memberBalancePayLogId) {
        ServiceResult<MemberBalancePayLog> result = new ServiceResult<MemberBalancePayLog>();
        try {
            result.setResult(
                memberBalancePayLogModel.getMemberBalancePayLogById(memberBalancePayLogId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberBalancePayLogService][getMemberBalancePayLogById]根据id["
                      + memberBalancePayLogId + "]取得会员充值记录时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberBalancePayLogService][getMemberBalancePayLogById]根据id["
                      + memberBalancePayLogId + "]取得会员充值记录时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存会员充值记录
     * @param  memberBalancePayLog
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMemberBalancePayLog(@RequestBody MemberBalancePayLog memberBalancePayLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberBalancePayLogModel.saveMemberBalancePayLog(memberBalancePayLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberBalancePayLogService][saveMemberBalancePayLog]保存会员充值记录时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberBalancePayLogService][saveMemberBalancePayLog]保存会员充值记录时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新会员充值记录
    * @param  memberBalancePayLog
    * @return
    * @see com.yixiekeji.service.MemberBalancePayLogService#updateMemberBalancePayLog(MemberBalancePayLog)
    */
    @Override
    public ServiceResult<Integer> updateMemberBalancePayLog(@RequestBody MemberBalancePayLog memberBalancePayLog) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result
                .setResult(memberBalancePayLogModel.updateMemberBalancePayLog(memberBalancePayLog));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberBalancePayLogService][updateMemberBalancePayLog]更新会员充值记录时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberBalancePayLogService][updateMemberBalancePayLog]更新会员充值记录时出现未知异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MemberBalancePayLog>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberBalancePayLog>> serviceResult = new ServiceResult<List<MemberBalancePayLog>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(memberBalancePayLogModel.page(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberBalancePayLogServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberBalancePayLogModel.del(id) > 0);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberBalancePayLogServiceImpl][del] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<MemberBalancePayLog> getByOrderSn(@RequestParam("orderNo") String orderNo) {
        ServiceResult<MemberBalancePayLog> serviceResult = new ServiceResult<MemberBalancePayLog>();
        try {
            serviceResult.setResult(memberBalancePayLogModel.getByOrderSn(orderNo));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberBalancePayLogServiceImpl][getByOrderSn] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> payAfter(@RequestParam("orderNo") String orderNo,
                                           @RequestParam("amount") String amount,
                                           @RequestParam("tradeSn") String tradeSn) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberBalancePayLogModel.payAfter(orderNo, amount, tradeSn));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberBalancePayLogServiceImpl][payAfter] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> payBefore(@RequestParam("optionsRadios") String optionsRadios,
                                            @RequestParam("amount") String amount,
                                            @RequestParam("ordersn") String ordersn,
                                            @RequestBody Member member) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                memberBalancePayLogModel.payBefore(optionsRadios, amount, ordersn, member));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberBalancePayLogServiceImpl][payBefore] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}