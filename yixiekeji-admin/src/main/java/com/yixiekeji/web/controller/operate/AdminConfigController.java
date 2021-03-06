package com.yixiekeji.web.controller.operate;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.operate.Config;
import com.yixiekeji.service.operate.IConfigService;
import com.yixiekeji.web.controller.BaseController;

@Controller
@RequestMapping(value = "admin/config", produces = "application/json;charset=UTF-8")
public class AdminConfigController extends BaseController {

    @Resource
    private IConfigService configService;

    /**
     * 系统配置初始化
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String gradeValueIndex(Map<String, Object> dataMap) throws Exception {
        ServiceResult<Config> result = configService.getConfigById(ConstantsEJS.CONFIG_ID);
        dataMap.put("config", result.getResult());
        return "admin/operate/config/configedit";
    }

    /**
     * 系统配置更新
     * @param memberRule
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "update", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> configUpdate(Config config,
                                                              HttpServletRequest request,
                                                              Map<String, Object> dataMap) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        ServiceResult<Integer> serviceResult = configService.updateConfig(config);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                dataMap.put("config", config);
                jsonResult.setMessage(serviceResult.getMessage());
                return jsonResult;
            }
        }
        jsonResult.setData(true);
        return jsonResult;
    }
}
