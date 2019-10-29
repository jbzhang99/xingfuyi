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

/**
 * 系统资源管理
 *                       
 * @Filename: ISystemResourcesService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "systemResources")
@Service(value = "systemResourcesService")
public interface ISystemResourcesService {

    /**
     * 根据id取得资源表
     * @param  systemResourcesId
     * @return
     */
    @RequestMapping(value = "getSystemResourcesById", method = RequestMethod.POST)
    ServiceResult<SystemResources> getSystemResourcesById(@RequestParam("systemResourcesId") Integer systemResourcesId);

    /**
     * 保存资源表
     * @param  systemResources
     * @return
     */
    @RequestMapping(value = "saveSystemResources", method = RequestMethod.POST)
    ServiceResult<Integer> saveSystemResources(SystemResources systemResources);

    /**
    * 更新资源表
    * @param  systemResources
    * @return
    */
    @RequestMapping(value = "updateSystemResources", method = RequestMethod.POST)
    ServiceResult<Integer> updateSystemResources(SystemResources systemResources);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SystemResources>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    @RequestMapping(value = "getByPid", method = RequestMethod.GET)
    ServiceResult<List<SystemResources>> getByPid(@RequestParam("pid") Integer pid);
}