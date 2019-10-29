package com.yixiekeji.service.impl.member;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MobileVerifyCode;
import com.yixiekeji.model.member.MobileVerifyCodeModel;
import com.yixiekeji.service.member.IMobileVerifyCodeService;

@RestController
public class MobileVerifyCodeServiceImpl implements IMobileVerifyCodeService {
    protected static Logger       log = LoggerFactory.getLogger(MobileVerifyCodeServiceImpl.class);
    @Resource
    private MobileVerifyCodeModel mobileVerifyCodeModel;

    /**
    * 根据id取得小程序验证码表
    * @param  mobileVerifyCodeId
    * @return
    */
    @Override
    public ServiceResult<MobileVerifyCode> getMobileVerifyCodeById(@RequestParam("mobileVerifyCodeId") Integer mobileVerifyCodeId) {
        ServiceResult<MobileVerifyCode> result = new ServiceResult<MobileVerifyCode>();
        try {
            result.setResult(mobileVerifyCodeModel.getMobileVerifyCodeById(mobileVerifyCodeId));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMobileVerifyCodeService][getMobileVerifyCodeById]根据id["
                      + mobileVerifyCodeId + "]取得小程序验证码表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMobileVerifyCodeService][getMobileVerifyCodeById]根据id["
                      + mobileVerifyCodeId + "]取得小程序验证码表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 保存小程序验证码表
     * @param  mobileVerifyCode
     * @return
     */
    @Override
    public ServiceResult<Integer> saveMobileVerifyCode(@RequestBody MobileVerifyCode mobileVerifyCode) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mobileVerifyCodeModel.saveMobileVerifyCode(mobileVerifyCode));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMobileVerifyCodeService][saveMobileVerifyCode]保存小程序验证码表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMobileVerifyCodeService][saveMobileVerifyCode]保存小程序验证码表时出现未知异常：", e);
        }
        return result;
    }

    /**
    * 更新小程序验证码表
    * @param  mobileVerifyCode
    * @return
    * @see com.ejavashop.service.MobileVerifyCodeService#updateMobileVerifyCode(MobileVerifyCode)
    */
    @Override
    public ServiceResult<Integer> updateMobileVerifyCode(@RequestBody MobileVerifyCode mobileVerifyCode) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mobileVerifyCodeModel.updateMobileVerifyCode(mobileVerifyCode));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMobileVerifyCodeService][updateMobileVerifyCode]更新小程序验证码表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMobileVerifyCodeService][updateMobileVerifyCode]更新小程序验证码表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 根据uid取得小程序验证码表
     * @param  uid
     * @return
     */
    @Override
    public ServiceResult<List<MobileVerifyCode>> getMobileVerifyCodeByUid(@RequestParam("uid") String uid) {
        ServiceResult<List<MobileVerifyCode>> result = new ServiceResult<List<MobileVerifyCode>>();
        try {
            result.setResult(mobileVerifyCodeModel.getMobileVerifyCodeByUid(uid));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMobileVerifyCodeService][getMobileVerifyCodeByUid]根据uid[" + uid
                      + "]取得小程序验证码表时出现未知异常：" + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMobileVerifyCodeService][getMobileVerifyCodeByUid]根据uid[" + uid
                      + "]取得小程序验证码表时出现未知异常：",
                e);
        }
        return result;
    }

    /**
     * 根据uid删除小程序验证码表记录
     * @param  uid
     * @return
     */
    @Override
    public ServiceResult<Integer> delMobileVerifyCodeByUid(@RequestParam("uid") String uid) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(mobileVerifyCodeModel.delByUid(uid));
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[IMobileVerifyCodeService][delMobileVerifyCodeByUid]删除小程序验证码表时出现未知异常："
                      + e.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[IMobileVerifyCodeService][delMobileVerifyCodeByUid]删除小程序验证码表时出现未知异常：", e);
        }
        return result;
    }

    /**
     * 系统定时任务清除1天之前的短信数据
     * @return
     */
    @Override
    public ServiceResult<Boolean> jobClearSmsCode() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mobileVerifyCodeModel.jobClearSmsCode());
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IMobileVerifyCodeService][jobClearSmsCode]系统定时清理短信数据时出现未知异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMobileVerifyCodeService][jobClearSmsCode]系统定时清理短信数据时出现未知异常:", e);
        }
        return result;
    }
}