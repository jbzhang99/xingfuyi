package com.yixiekeji.service.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.system.SystemAdmin;

/**
 * 平台管理员用户管理
 *                       
 * @Filename: ISystemAdminService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "systemAdmin")
@Service(value = "systemAdminService")
public interface ISystemAdminService {

    /**
     * 根据id取得系统管理员表
     * @param  systemAdminId
     * @return
     */
    @RequestMapping(value = "getSystemAdminById", method = RequestMethod.GET)
    ServiceResult<SystemAdmin> getSystemAdminById(@RequestParam("systemAdminId") Integer systemAdminId);

    /**
     * 保存系统管理员表
     * @param  systemAdmin
     * @return
     */
    @RequestMapping(value = "saveSystemAdmin", method = RequestMethod.POST)
    ServiceResult<Integer> saveSystemAdmin(SystemAdmin systemAdmin);

    /**
    * 更新系统管理员表
    * @param  systemAdmin
    * @return
    */
    @RequestMapping(value = "updateSystemAdmin", method = RequestMethod.POST)
    ServiceResult<Integer> updateSystemAdmin(SystemAdmin systemAdmin);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SystemAdmin>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    @RequestMapping(value = "getSystemAdminByNamePwd", method = RequestMethod.GET)
    ServiceResult<SystemAdmin> getSystemAdminByNamePwd(@RequestParam("name") String name,
                                                       @RequestParam("password") String password);

    /**
     * 根据用户名取用户
     * @param name
     * @return
     */
    @RequestMapping(value = "getSystemAdminByName", method = RequestMethod.GET)
    ServiceResult<List<SystemAdmin>> getSystemAdminByName(@RequestParam("name") String name);

}