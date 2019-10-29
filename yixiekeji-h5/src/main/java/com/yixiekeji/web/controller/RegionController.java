package com.yixiekeji.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.system.IRegionsService;

/**
 * 区域controller
 *
 * @Filename: RegionController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class RegionController extends BaseController {

    protected static Logger log = LoggerFactory.getLogger(RegionController.class);

    @Resource
    private IRegionsService regionsService;

    @RequestMapping(value = "getRegionByParentId", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Regions>> getRegionByParentId(Integer parentId,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response,
                                                                           Map<String, Object> dataMap) {

        ServiceResult<List<Regions>> serviceResult = regionsService.getRegionsByParentId(parentId);
        if (!serviceResult.getSuccess()) {
            log.error("[RegionController][getRegionByParentId]根据父ID获取区域信息失败："
                      + serviceResult.getMessage());
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<Regions>> jsonResult = new HttpJsonResult<List<Regions>>();
        jsonResult.setData(serviceResult.getResult());
        return jsonResult;
    }
}
