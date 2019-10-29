package com.yixiekeji.service.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.SystemRoles;

/**
 * 平台角色管理
 *                       
 * @Filename: ISystemRolesService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "systemRoles")
@Service(value = "systemRolesService")
public interface ISystemRolesService {

    /**
     * 根据id取得角色表
     * @param  systemRolesId
     * @return
     */
    @RequestMapping(value = "getSystemRolesById", method = RequestMethod.GET)
    ServiceResult<SystemRoles> getSystemRolesById(@RequestParam("systemRolesId") Integer systemRolesId);

    /**
     * 保存角色表
     * @param  systemRoles
     * @return
     */
    @RequestMapping(value = "saveSystemRoles", method = RequestMethod.POST)
    ServiceResult<Integer> saveSystemRoles(SystemRoles systemRoles);

    /**
    * 更新角色表
    * @param  systemRoles
    * @return
    */
    @RequestMapping(value = "updateSystemRoles", method = RequestMethod.POST)
    ServiceResult<Integer> updateSystemRoles(SystemRoles systemRoles);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SystemRoles>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}