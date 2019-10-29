package com.yixiekeji.service.impl.member;

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
import com.yixiekeji.entity.member.MemberBalanceLogs;
import com.yixiekeji.model.member.MemberBalanceLogsModel;
import com.yixiekeji.service.member.IMemberBalanceLogsService;

@RestController
public class MemberBalanceLogsServiceImpl implements IMemberBalanceLogsService {
    private static Logger          log = LoggerFactory
        .getLogger(MemberBalanceLogsServiceImpl.class);

    @Resource
    private MemberBalanceLogsModel memberBalanceLogsModel;

    @Override
    public ServiceResult<List<MemberBalanceLogs>> getMemberBalanceLogs(@RequestParam("memberId") Integer memberId,
                                                                       @RequestBody PagerInfo pager) {
        ServiceResult<List<MemberBalanceLogs>> serviceResult = new ServiceResult<List<MemberBalanceLogs>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberBalanceLogsModel.getMemberBalanceLogsCount(memberId));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(memberBalanceLogsModel.getMemberBalanceLogs(memberId, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[MemberService][getMemberBalanceLogs]根据会员ID获取会员账户余额变化日志发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberService][getMemberBalanceLogs]根据会员ID获取会员账户余额变化日志发生异常:", e);
        }
        return serviceResult;
    }

    /**
    * 根据id取得会员账户余额变化日志表
    * @param  memberBalanceLogsId
    * @return
    */
    @Override
    public ServiceResult<MemberBalanceLogs> getMemberBalanceLogsById(@RequestParam("memberBalanceLogsId") Integer memberBalanceLogsId) {
        ServiceResult<MemberBalanceLogs> result = new ServiceResult<MemberBalanceLogs>();
        try {
            result.setResult(memberBalanceLogsModel.getMemberBalanceLogsById(memberBalanceLogsId));
        } catch (Exception e) {
            log.error("根据id[" + memberBalanceLogsId + "]取得会员账户余额变化日志表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + memberBalanceLogsId + "]取得会员账户余额变化日志表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存会员账户余额变化日志表
     * @param  memberBalanceLogs
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMemberBalanceLogs(@RequestBody MemberBalanceLogs memberBalanceLogs) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberBalanceLogsModel.saveMemberBalanceLogs(memberBalanceLogs));
        } catch (Exception e) {
            log.error("保存会员账户余额变化日志表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存会员账户余额变化日志表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新会员账户余额变化日志表
    * @param  memberBalanceLogs
    * @return
    */
    @Override
    public ServiceResult<Integer> updateMemberBalanceLogs(@RequestBody MemberBalanceLogs memberBalanceLogs) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberBalanceLogsModel.updateMemberBalanceLogs(memberBalanceLogs));
        } catch (Exception e) {
            log.error("更新会员账户余额变化日志表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新会员账户余额变化日志表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {
        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(memberBalanceLogsModel.del(id));
        } catch (Exception e) {
            log.error("[MemberBalanceLogsServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }
}