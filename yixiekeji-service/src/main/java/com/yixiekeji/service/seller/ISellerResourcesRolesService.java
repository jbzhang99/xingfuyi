package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerResourcesRoles;
import com.yixiekeji.entity.system.SystemResources;

/**
 * 商家角色权限
 *                       
 * @Filename: ISellerResourcesRolesService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerResourcesRoles")
@Service(value = "sellerResourcesRolesService")
public interface ISellerResourcesRolesService {

    /**
     * 根据id取得角色资源对应表
     * @param  sellerResourcesRolesId
     * @return
     */
    @RequestMapping(value = "getSellerResourcesRolesById", method = RequestMethod.GET)
    ServiceResult<SellerResourcesRoles> getSellerResourcesRolesById(@RequestParam("sellerResourcesRolesId") Integer sellerResourcesRolesId);

    /**
     * 保存角色资源对应表
     * @param  sellerResourcesRoles
     * @return
     */
    @RequestMapping(value = "saveSellerResourcesRoles", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerResourcesRoles(SellerResourcesRoles sellerResourcesRoles);

    /**
    * 更新角色资源对应表
    * @param  sellerResourcesRoles
    * @return
    */
    @RequestMapping(value = "updateSellerResourcesRoles", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerResourcesRoles(SellerResourcesRoles sellerResourcesRoles);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerResourcesRoles>> page(FeignUtil feignUtil);

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
    @RequestMapping(value = "save", method = RequestMethod.POST)
    ServiceResult<Boolean> save(@RequestParam("sellerResourcesRolesId") String roleId,
                                @RequestBody String[] resArr);

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