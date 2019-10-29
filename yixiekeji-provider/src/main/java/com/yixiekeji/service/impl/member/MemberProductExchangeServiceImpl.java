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
import com.yixiekeji.entity.member.MemberProductExchange;
import com.yixiekeji.model.member.MemberProductExchangeModel;
import com.yixiekeji.service.member.IMemberProductExchangeService;
import com.yixiekeji.util.FeignObjectUtil;

/**
 * 会员换货管理                       
 *
 */
@RestController
public class MemberProductExchangeServiceImpl implements IMemberProductExchangeService {
    private static Logger              log = LoggerFactory
        .getLogger(MemberProductExchangeServiceImpl.class);

    @Resource
    private MemberProductExchangeModel memberProductExchangeModel;

    /**
    * 根据id取得用户换货
    * @param  memberProductExchangeId
    * @return
    */
    @Override
    public ServiceResult<MemberProductExchange> getMemberProductExchangeById(@RequestParam("memberProductExchangeId") Integer memberProductExchangeId) {
        ServiceResult<MemberProductExchange> result = new ServiceResult<MemberProductExchange>();
        try {
            result.setResult(
                memberProductExchangeModel.getMemberProductExchangeById(memberProductExchangeId));
        } catch (Exception e) {
            log.error("根据id[" + memberProductExchangeId + "]取得用户换货时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + memberProductExchangeId + "]取得用户换货时出现未知异常");
        }
        return result;
    }

    /**
     * 保存用户换货
     * @param  memberProductExchange
     * @return
     */
    @Override
    public ServiceResult<Boolean> saveMemberProductExchange(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductExchangeModel.saveMemberProductExchange(
                feignObjectUtil.getMemberProductExchange(), feignObjectUtil.getMember()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductExchangeService][saveMemberProductExchange]保存产品换货申请时发生异常:", e);
        }
        return serviceResult;
    }

    /**
    * 更新用户换货
    * @param  memberProductExchange
    * @return
    */
    @Override
    public ServiceResult<Boolean> updateMemberProductExchange(@RequestBody MemberProductExchange memberProductExchange) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(
                memberProductExchangeModel.updateMemberProductExchange(memberProductExchange));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductExchangeService][updateMemberProductExchange]更新用户换货申请时发生异常:",
                e);
        }
        return serviceResult;
    }

    /**
     * 根据登录用户取得用户换货列表 分页
     * @param pager
     * @param request
     * @return
     */
    @Override
    public ServiceResult<List<MemberProductExchange>> getMemberProductExchangeList(@RequestBody FeignUtil feignUtil,
                                                                                   @RequestParam("memberId") Integer memberId) {
        Map<String, Object> queryMap = feignUtil.getQueryMapObject();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberProductExchange>> serviceResult = new ServiceResult<List<MemberProductExchange>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(
                memberProductExchangeModel.getMemberProductExchangeList(queryMap, pager, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductExchangeService][getMemberProductExchangeList]取得用户换货列表时发生异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberProductExchange>> getMemberProductExchanges(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberProductExchange>> serviceResult = new ServiceResult<List<MemberProductExchange>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(memberProductExchangeModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }

            List<MemberProductExchange> list = memberProductExchangeModel.page(queryMap, start,
                size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[MemberProductBackServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[MemberProductBackServiceImpl][page] exception:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberProductExchange>> getExchangeListWithPrdAndOp(@RequestBody PagerInfo pager,
                                                                                  @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<MemberProductExchange>> serviceResult = new ServiceResult<List<MemberProductExchange>>();
        serviceResult.setPager(pager);
        try {
            serviceResult
                .setResult(memberProductExchangeModel.getExchangeListWithPrdAndOp(pager, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductExchangeService][getExchangeListWithPrdAndOp]取得用户换货列表时发生异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> audit(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(memberProductExchangeModel.audit(
                feignObjectUtil.getMemberProductExchange(), feignObjectUtil.getSellerUser()));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberProductExchangeService][getExchangeListWithPrdAndOp]商家处理换货信息时发生异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> doExchangeDeliverGoods(@RequestBody FeignObjectUtil feignObjectUtil) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(memberProductExchangeModel.doExchangeDeliverGoods(
                feignObjectUtil.getMemberProductExchange(), feignObjectUtil.getMember()));
        } catch (Exception e) {
            log.error("用户换货时退件发货时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("用户换货时退件发货时出现未知异常");
        }
        return result;
    }
}