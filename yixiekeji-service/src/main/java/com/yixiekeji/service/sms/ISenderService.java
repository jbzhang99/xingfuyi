package com.yixiekeji.service.sms;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sender")
@Service(value = "senderService")
public interface ISenderService {

    /**
     * 发送短信,此方法不会返回短信发送结果
     * @param map
     * @return
     */
    @RequestMapping(value = "sendSMS", method = RequestMethod.POST)
    ServiceResult<Boolean> sendSMS(Map<String, Object> map);

    /**
     * 发送模板短信,返回发送结果
     * @param map
     * @return
     */
    @RequestMapping(value = "sendSMSWidthCallable", method = RequestMethod.POST)
    ServiceResult<Object> sendSMSWidthCallable(Map<String, Object> map);

    /**
     * 发送验证码,并将结果返回
     * @param mobile
     * @return
     */
    @RequestMapping(value = "sendVerifyCode", method = RequestMethod.GET)
    ServiceResult<Object> sendVerifyCode(@RequestParam("mobile") String mobile);
}
