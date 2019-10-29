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
import com.yixiekeji.entity.member.MemberApplyDrawing;
import com.yixiekeji.model.member.MemberApplyDrawingModel;
import com.yixiekeji.service.member.IMemberApplyDrawingService;

@RestController
public class MemberApplyDrawingServiceImpl implements IMemberApplyDrawingService {
    private static Logger           log = LoggerFactory
        .getLogger(MemberApplyDrawingServiceImpl.class);

    @Resource
    private MemberApplyDrawingModel memberApplyDrawingModel;

    @Override
    public ServiceResult<MemberApplyDrawing> getMemberApplyDrawing(@RequestParam("memberApplyDrawingId") Integer memberApplyDrawingId) {
        ServiceResult<MemberApplyDrawing> result = new ServiceResult<MemberApplyDrawing>();
        try {
            result.setResult(memberApplyDrawingModel.getMemberApplyDrawing(memberApplyDrawingId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("根据id[" + memberApplyDrawingId + "]取得会员提款申请时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + memberApplyDrawingId + "]取得会员提款申请时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveMemberApplyDrawing(@RequestBody MemberApplyDrawing memberApplyDrawing) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberApplyDrawingModel.saveMemberApplyDrawing(memberApplyDrawing));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("保存会员提款申请时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存会员提款申请时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateMemberApplyDrawing(@RequestBody MemberApplyDrawing memberApplyDrawing) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberApplyDrawingModel.updateMemberApplyDrawing(memberApplyDrawing));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新会员提款申请时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新会员提款申请时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<MemberApplyDrawing>> getMemberApplyDrawings(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberApplyDrawing>> serviceResult = new ServiceResult<List<MemberApplyDrawing>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberApplyDrawingModel.getMemberApplyDrawingsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(memberApplyDrawingModel.getMemberApplyDrawings(queryMap, start, size));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberApplyDrawingService][getMemberApplyDrawings]查询会员退款申请信息发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> auditing(@RequestBody List<Integer> ids,
                                           @RequestParam("optId") Integer optId,
                                           @RequestParam("optName") String optName) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(memberApplyDrawingModel.auditing(ids, optId, optName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberApplyDrawingService][auditing]审核会员退款申请发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> paid(@RequestParam("id") Integer id,
                                       @RequestParam("optId") Integer optId,
                                       @RequestParam("optName") String optName) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            serviceResult.setResult(memberApplyDrawingModel.paid(id, optId, optName));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberApplyDrawingService][paid]更改打款状态时发生异常:", e);
        }
        return serviceResult;
    }
}