package com.yixiekeji.service.impl.seller;

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
import com.yixiekeji.entity.seller.SellerResourcesRoles;
import com.yixiekeji.entity.system.SystemResources;
import com.yixiekeji.model.seller.SellerResourcesRolesModel;
import com.yixiekeji.service.seller.ISellerResourcesRolesService;

@RestController
public class SellerResourcesRolesServiceImpl implements ISellerResourcesRolesService {
    private static Logger             log = LoggerFactory
        .getLogger(SellerResourcesRolesServiceImpl.class);

    @Resource
    private SellerResourcesRolesModel sellerResourcesRolesModel;

    @Override
    public ServiceResult<SellerResourcesRoles> getSellerResourcesRolesById(@RequestParam("sellerResourcesRolesId") Integer sellerResourcesRolesId) {
        ServiceResult<SellerResourcesRoles> result = new ServiceResult<SellerResourcesRoles>();
        try {
            result.setResult(
                sellerResourcesRolesModel.getSellerResourcesRolesById(sellerResourcesRolesId));
        } catch (Exception e) {
            log.error("根据id[" + sellerResourcesRolesId + "]取得角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + sellerResourcesRolesId + "]取得角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> saveSellerResourcesRoles(@RequestBody SellerResourcesRoles sellerResourcesRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                sellerResourcesRolesModel.saveSellerResourcesRoles(sellerResourcesRoles));
        } catch (Exception e) {
            log.error("保存角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("保存角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateSellerResourcesRoles(@RequestBody SellerResourcesRoles sellerResourcesRoles) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(
                sellerResourcesRolesModel.updateSellerResourcesRoles(sellerResourcesRoles));
        } catch (Exception e) {
            log.error("更新角色资源对应表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新角色资源对应表时出现未知异常");
        }
        return result;
    }

    @Override
    public ServiceResult<List<SellerResourcesRoles>> page(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<SellerResourcesRoles>> serviceResult = new ServiceResult<List<SellerResourcesRoles>>();
        serviceResult.setPager(pager);

        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(sellerResourcesRolesModel.pageCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            queryMap.put("start", start + "");
            queryMap.put("size", size + "");
            List<SellerResourcesRoles> list = sellerResourcesRolesModel.page(queryMap);
            serviceResult.setResult(list);
        } catch (BusinessException e) {
            serviceResult.setMessage(e.getMessage());
            serviceResult.setSuccess(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR,
                ConstantsEJS.SERVICE_RESULT_EXCEPTION_SYSERROR);
            log.error("[SellerResourcesRolesServiceImpl][page] param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[SellerResourcesRolesServiceImpl][page] exception:" + e.getMessage());
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> del(@RequestParam("id") Integer id) {

        ServiceResult<Boolean> sr = new ServiceResult<Boolean>();
        try {
            sr.setResult(sellerResourcesRolesModel.del(id));
        } catch (Exception e) {
            log.error("[SellerResourcesRolesServiceImpl][del] exception:" + e.getMessage());
            e.printStackTrace();
        }
        return sr;
    }

    @Override
    public ServiceResult<Boolean> save(@RequestParam("sellerResourcesRolesId") String roleId,
                                       @RequestBody String[] resArr) {
        ServiceResult<Boolean> serRes = new ServiceResult<Boolean>();
        try {
            serRes.setResult(sellerResourcesRolesModel.save(roleId, resArr));
            serRes.setMessage("保存成功！");
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

            serRes.setResult(sellerResourcesRolesModel.getResourceByRoleId(roleId, scope));
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
        return sellerResourcesRolesModel.getResourceByPid(pid, roleId, scope);
    }
}