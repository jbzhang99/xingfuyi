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
import com.yixiekeji.entity.system.SystemResources;
import com.yixiekeji.entity.system.SystemResourcesRoles;
import com.yixiekeji.model.system.SystemResourcesRolesModel;
import com.yixiekeji.service.system.ISystemResourcesRolesService;

@RestController
public class SystemResourcesRolesServiceImpl implements ISystemResourcesRolesService {
    private static Logger             log = LoggerFactory
        .getLogger(SystemResourcesRolesServiceImpl.class);

    @Resource
    private SystemResourcesRolesModel systemResourcesRolesModel;

    @Override
    public ServiceResult<SystemResourcesRoles> getSystemResourcesRolesById(@RequestParam("systemResourcesRolesId") Integer systemResourcesRolesId) {
        ServiceResult<SystemResourcesRoles> result = new ServiceResult<SystemResourcesRoles>();
        try {
            result.setResult(
                systemResourcesRolesModel.getSystemResourcesRolesById(systemResourcesRolesId));
        } catch (Exception e) {
            log.error("根据id[" + systemResourcesRolesId + "]取得角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + systemResourcesRolesId + "]取得角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveSystemResourcesRoles(@RequestBody SystemResourcesRoles systemResourcesRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                systemResourcesRolesModel.saveSystemResourcesRoles(systemResourcesRoles));
            result.setMessage("保存成功！");
        } catch (Exception e) {
            log.error("保存角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateSystemResourcesRoles(@RequestBody SystemResourcesRoles systemResourcesRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                systemResourcesRolesModel.updateSystemResourcesRoles(systemResourcesRoles));
            result.setMessage("修改成功！");
        } catch (Exception e) {
            log.error("更新角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SystemResourcesRoles>> page(@RequestBody FeignUtil feignUtil) {
        PagerInfo pager = feignUtil.getPager();
        Map<String, String> queryMap = feignUtil.getQueryMap();

        ServiceResult<List<SystemResourcesRoles>> serviceResult = new ServiceResult<List<SystemResourcesRoles>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(systemResourcesRolesModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            queryMap.put("start", start + "");
            queryMap.put("size", size + "");
            List<SystemResourcesRoles> list = systemResourcesRolesModel.page(queryMap);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SystemResourcesRolesServiceImpl][page] param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[SystemResourcesRolesServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(systemResourcesRolesModel.del(id));
        } catch (Exception e) {
            log.error("[SystemResourcesRolesServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

    @Override
    public ServiceResult<Boolean> save(@RequestParam("roleId") String roleId,
                                       @RequestParam("resArr") String[] resArr) {
        ServiceResult<Boolean> serRes = new ServiceResult<Boolean>();
        try {

            serRes.setResult(systemResourcesRolesModel.save(roleId, resArr));
            serRes.setMessage("保存成功。");
        } catch (Exception e) {
            serRes.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, e.getMessage());
            e.printStackTrace();
        }
        return serRes;
    }

    @Override
    public ServiceResult<List<SystemResources>> getResourceByRoleId(@RequestParam("roleId") Integer roleId,
                                                                    @RequestParam("scope") Integer scope) {
        ServiceResult<List<SystemResources>> serRes = new ServiceResult<List<SystemResources>>();
        try {

            serRes.setResult(systemResourcesRolesModel.getResourceByRoleId(roleId, scope));
        } catch (Exception e) {
            serRes.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, e.getMessage());
            e.printStackTrace();
        }
        return serRes;
    }

    @Override
    public List<SystemResources> getResourceByPid(@RequestParam("pid") Integer pid,
                                                  @RequestParam("roleId") Integer roleId,
                                                  @RequestParam("scope") Integer scope) throws BusinessException {
        return systemResourcesRolesModel.getResourceByPid(pid, roleId, scope);
    }
}