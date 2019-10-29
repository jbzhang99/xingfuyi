package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MobileVerifyCode;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "mobileVerifyCode")
@Service(value = "mobileVerifyCodeService")
public interface IMobileVerifyCodeService {

    /**
     * 根据id取得小程序验证码表
     * @param  mobileVerifyCodeId
     * @return
     */
    @RequestMapping(value = "getMobileVerifyCodeById", method = RequestMethod.GET)
    ServiceResult<MobileVerifyCode> getMobileVerifyCodeById(@RequestParam("mobileVerifyCodeId") Integer mobileVerifyCodeId);

    /**
     * 保存小程序验证码表
     * @param  mobileVerifyCode
     * @return
     */
    @RequestMapping(value = "saveMobileVerifyCode", method = RequestMethod.POST)
    ServiceResult<Integer> saveMobileVerifyCode(MobileVerifyCode mobileVerifyCode);

    /**
    * 更新小程序验证码表
    * @param  mobileVerifyCode
    * @return
    */
    @RequestMapping(value = "updateMobileVerifyCode", method = RequestMethod.POST)
    ServiceResult<Integer> updateMobileVerifyCode(MobileVerifyCode mobileVerifyCode);

    /**
     * 根据uid取得小程序验证码表
     * @param  mobileVerifyCodeId
     * @return
     */
    @RequestMapping(value = "getMobileVerifyCodeByUid", method = RequestMethod.GET)
    ServiceResult<List<MobileVerifyCode>> getMobileVerifyCodeByUid(@RequestParam("uid") String uid);

    /**
     * 根据uid删除小程序验证码表记录
     * @param  mobileVerifyCodeId
     * @return
     */
    @RequestMapping(value = "delMobileVerifyCodeByUid", method = RequestMethod.GET)
    ServiceResult<Integer> delMobileVerifyCodeByUid(@RequestParam("uid") String uid);

    /**
     * 系统定时任务清除1天之前的数据
     * @return
     */
    @RequestMapping(value = "jobClearSmsCode", method = RequestMethod.GET)
    ServiceResult<Boolean> jobClearSmsCode();
}