package com.yixiekeji.service.impl.operate;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.operate.Config;
import com.yixiekeji.model.operate.ConfigModel;
import com.yixiekeji.service.operate.IConfigService;

@RestController
public class ConfigServiceImpl implements IConfigService {
    private static Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Resource
    private ConfigModel   configModel;

    /**
    * 根据id取得系统配置表
    * @param  configId
    * @return
    */
    @Override
    public ServiceResult<Config> getConfigById(@RequestParam("configId") Integer configId) {
        ServiceResult<Config> result = new ServiceResult<Config>();
        try {
            result.setResult(configModel.getConfigById(configId));
        } catch (Exception e) {
            log.error(
                "[ConfigServiceImpl][getConfigById]根据id[" + configId + "]取得系统配置表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage(
                "[ConfigServiceImpl][getConfigById]根据id[" + configId + "]取得系统配置表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存系统配置表
     * @param  config
     * @return
     */
    @Override
    public ServiceResult<Integer> saveConfig(@RequestBody Config config) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(configModel.saveConfig(config));
        } catch (Exception e) {
            log.error("[ConfigServiceImpl][saveConfig]保存系统配置表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[ConfigServiceImpl][saveConfig]保存系统配置表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新系统配置表
    * @param  config
    * @return
    */
    @Override
    public ServiceResult<Integer> updateConfig(@RequestBody Config config) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(configModel.updateConfig(config));
        } catch (Exception e) {
            log.error("[ConfigServiceImpl][updateConfig]更新系统配置表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("[ConfigServiceImpl][updateConfig]更新系统配置表时出现未知异常");
        }
        return result;
    }
}