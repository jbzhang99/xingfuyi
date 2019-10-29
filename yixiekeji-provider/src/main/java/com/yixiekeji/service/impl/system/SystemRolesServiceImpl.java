package com.yixiekeji.service.impl.system;

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
import com.yixiekeji.entity.system.SystemRoles;
import com.yixiekeji.model.system.SystemRolesModel;
import com.yixiekeji.service.system.ISystemRolesService;

@RestController
public class SystemRolesServiceImpl implements ISystemRolesService {
    private static Logger    log = LoggerFactory.getLogger(SystemRolesServiceImpl.class);

    @Resource
    private SystemRolesModel systemRolesModel;

    /**
     * 根据id取得角色表
     * @param  systemRolesId
     * @return
     */
    @Override
    public ServiceResult<SystemRoles> getSystemRolesById(@RequestParam("systemRolesId") Integer systemRolesId) {
        ServiceResult<SystemRoles> result = new ServiceResult<SystemRoles>();
        try {
            result.setResult(systemRolesModel.getSystemRolesById(systemRolesId));
        } catch (Exception e) {
            log.error("根据id[" + systemRolesId + "]取得角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemRolesId + "]取得角色表时出现未知异常");
        }
        return result;
    }

    /**
     * 保存角色表
     * @param  systemRoles
     * @return
     */
    @Override
    public ServiceResult<Integer> saveSystemRoles(@RequestBody SystemRoles systemRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemRolesModel.saveSystemRoles(systemRoles));
            result.setMessage("保存成功");
        } catch (Exception e) {
            log.error("保存角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存角色表时出现未知异常");
        }
        return result;
    }

    /**
    * 更新角色表
    * @param  systemRoles
    * @return
    */
    @Override
    public ServiceResult<Integer> updateSystemRoles(@RequestBody SystemRoles systemRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(systemRolesModel.updateSystemRoles(systemRoles));
            result.setMessage("修改成功");
        } catch (Exception e) {
            log.error("更新角色表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新角色表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemRoles>> page(@RequestBody FeignUtil feignUtil) {
        PagerInfo pager = feignUtil.getPager();
        Map<String, String> queryMap = feignUtil.getQueryMap();

        ServiceResult<List<SystemRoles>> serviceResult = new ServiceResult<List<SystemRoles>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemRolesModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            List<SystemRoles> list = systemRolesModel.page(queryMap, start, size);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemRolesServiceImpl][page] param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemRolesServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult.setResult(systemRolesModel.del(id));
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemRolesServiceImpl][del] exception:" + e.getMessage());
        }
        return serviceResult;
    }
}