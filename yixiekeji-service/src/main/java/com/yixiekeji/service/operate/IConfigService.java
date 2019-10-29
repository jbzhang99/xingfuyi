package com.yixiekeji.service.operate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.operate.Config;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "config")
@Service(value = "configService")
public interface IConfigService {

    /**
     * 根据id取得系统配置表
     * @param  configId
     * @return
     */
    @RequestMapping(value = "getConfigById", method = RequestMethod.GET)
    ServiceResult<Config> getConfigById(@RequestParam("configId") Integer configId);

    /**
     * 保存系统配置表
     * @param  config
     * @return
     */
    @RequestMapping(value = "saveConfig", method = RequestMethod.POST)
    ServiceResult<Integer> saveConfig(Config config);

    /**
    * 更新系统配置表
    * @param  config
    * @return
    */
    @RequestMapping(value = "updateConfig", method = RequestMethod.POST)
    ServiceResult<Integer> updateConfig(Config config);
}