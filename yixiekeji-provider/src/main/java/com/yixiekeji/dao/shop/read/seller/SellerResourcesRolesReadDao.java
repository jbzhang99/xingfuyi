package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerResourcesRoles;
import com.yixiekeji.entity.system.SystemResources;

@Mapper
public interface SellerResourcesRolesReadDao {

    SellerResourcesRoles get(java.lang.Integer id);

    Integer getCount(Map<String, String> queryMap);

    List<SellerResourcesRoles> page(Map<String, String> queryMap);

    /**
     * 此角色下的资源
     * @param roleId
     * @return
     */
    List<SystemResources> getResourceByRoleId(@Param("roleId") Integer roleId,
                                              @Param("scope") Integer scope);

    /**
     * 此资源下的有权限的子资源
     * @param queryMap
     * @return
     */
    List<SystemResources> getResourceByPid(Map<String, Object> queryMap);

}