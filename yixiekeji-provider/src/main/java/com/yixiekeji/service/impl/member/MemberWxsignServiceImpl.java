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
import com.yixiekeji.entity.member.MemberWxsign;
import com.yixiekeji.model.member.MemberWxsignModel;
import com.yixiekeji.service.member.IMemberWxsignService;

@RestController
public class MemberWxsignServiceImpl implements IMemberWxsignService {
    private static Logger     log = LoggerFactory.getLogger(MemberWxsignServiceImpl.class);

    @Resource
    private MemberWxsignModel memberWxsignModel;

    /**
    * 根据id取得微信联合登录
    * @param  memberWxsignId
    * @return
    */
    @Override
    public ServiceResult<MemberWxsign> getMemberWxsignById(@RequestParam("memberWxsignId") Integer memberWxsignId) {
        ServiceResult<MemberWxsign> result = new ServiceResult<MemberWxsign>();
        try {
            result.setResult(memberWxsignModel.getMemberWxsignById(memberWxsignId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberWxsignService][getMemberWxsignById]根据id[" + memberWxsignId
                      + "]取得微信联合登录时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberWxsignService][getMemberWxsignById]根据id[" + memberWxsignId
                      + "]取得微信联合登录时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存微信联合登录
     * @param  memberWxsign
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMemberWxsign(@RequestBody MemberWxsign memberWxsign) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberWxsignModel.saveMemberWxsign(memberWxsign));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMemberWxsignService][saveMemberWxsign]保存微信联合登录时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberWxsignService][saveMemberWxsign]保存微信联合登录时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新微信联合登录
    * @param  memberWxsign
    * @return
    * @see com.yixiekeji.service.MemberWxsignService#updateMemberWxsign(MemberWxsign)
    */
    @Override
    public ServiceResult<Integer> updateMemberWxsign(@RequestBody MemberWxsign memberWxsign) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(memberWxsignModel.updateMemberWxsign(memberWxsign));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error(
                "[IMemberWxsignService][updateMemberWxsign]更新微信联合登录时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMemberWxsignService][updateMemberWxsign]更新微信联合登录时出现未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MemberWxsign>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MemberWxsign>> serviceResult = new ServiceResult<List<MemberWxsign>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(memberWxsignModel.page(queryMap, pager));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberWxsignServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<MemberWxsign> getWxUser(@RequestParam("openid") String openid) {
        ServiceResult<MemberWxsign> serviceResult = new ServiceResult<>();
        try {
            serviceResult.setResult(memberWxsignModel.getWxUser(openid));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            e.printStackTrace();
            log.error("[MemberWxsignServiceImpl][getByOpenId] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}