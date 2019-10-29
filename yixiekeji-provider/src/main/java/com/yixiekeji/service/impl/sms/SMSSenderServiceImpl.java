package com.yixiekeji.service.impl.sms;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.model.sms.SMSSendModel;
import com.yixiekeji.service.impl.order.AdminComplaintServiceImpl;
import com.yixiekeji.service.sms.ISenderService;

/**
 * 短信发送服务
 *                       
 * @Filename: SMSSender.java
 *
 */
@RestController
public class SMSSenderServiceImpl implements ISenderService {
    private static Logger log = LoggerFactory.getLogger(AdminComplaintServiceImpl.class);
    @Resource(name = "SMSSendModel")
    private SMSSendModel  SMSSendModel;

    @Override
    public ServiceResult<Boolean> sendSMS(@RequestBody Map<String, Object> map) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            SMSSendModel.sendSMS(map);
            result.setResult(Boolean.TRUE);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }

    @Override
    public ServiceResult<Object> sendSMSWidthCallable(@RequestBody Map<String, Object> map) {
        ServiceResult<Object> result = new ServiceResult<Object>();
        try {
            result.setResult(SMSSendModel.sendSMSWidthCallable(map));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }

    @Override
    public ServiceResult<Object> sendVerifyCode(@RequestParam("mobile") String mobile) {
        ServiceResult<Object> result = new ServiceResult<Object>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("mobile", mobile);
            result.setResult(SMSSendModel.sendVerifyCode(param));
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result.setSuccess(false);
                result.setMessage(e.getMessage());
                log.debug(e.getMessage());
            } else {
                e.printStackTrace();
                log.error("短信发送出现未知异常：" + e);
                result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
                result.setMessage("短信发送出现未知异常");
            }
        }
        return result;
    }

}
