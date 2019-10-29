package com.yixiekeji.service.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.SystemResources;
import com.yixiekeji.entity.system.SystemResourcesRoles;

/**
 * 平台角色管理
 *                       
 * @Filename: ISystemResourcesRolesService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "systemResourcesRoles")
@Service(value = "systemResourcesRolesService")
public interface ISystemResourcesRolesService {

    /**
     * 根据id取得角色资源对应表
     * @param  systemResourcesRolesId
     * @return
     */
    @RequestMapping(value = "getSystemResourcesRolesById", method = RequestMethod.GET)
    ServiceResult<SystemResourcesRoles> getSystemResourcesRolesById(@RequestParam("systemResourcesRolesId") Integer systemResourcesRolesId);

    /**
     * 保存角色资源对应表
     * @param  systemResourcesRoles
     * @return
     */
    @RequestMapping(value = "saveSystemResourcesRoles", method = RequestMethod.POST)
    ServiceResult<Integer> saveSystemResourcesRoles(SystemResourcesRoles systemResourcesRoles);

    /**
    * 更新角色资源对应表
    * @param  systemResourcesRoles
    * @return
    */
    @RequestMapping(value = "updateSystemResourcesRoles", method = RequestMethod.POST)
    ServiceResult<Integer> updateSystemResourcesRoles(SystemResourcesRoles systemResourcesRoles);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SystemResourcesRoles>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    /**
     * 保存角色资源关系
     * @param roleId
     * @param resArr
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.GET)
    ServiceResult<Boolean> save(@RequestParam("roleId") String roleId,
                                @RequestParam("resArr") String[] resArr);

    /**
     * 获取该角色下的所有资源列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "getResourceByRoleId", method = RequestMethod.GET)
    ServiceResult<List<SystemResources>> getResourceByRoleId(@RequestParam("roleId") Integer roleId,
                                                             @RequestParam("scope") Integer scope);

    /**
     * 根据父资源ID、角色ID、使用范围获取资源
     * @param pid
     * @param roleId
     * @param scope 资源使用范围，1商家、2平台
     * @return
     */
    @RequestMapping(value = "getResourceByPid", method = RequestMethod.GET)
    List<SystemResources> getResourceByPid(@RequestParam("pid") Integer pid,
                                           @RequestParam("roleId") Integer roleId,
                                           @RequestParam("scope") Integer scope);
}