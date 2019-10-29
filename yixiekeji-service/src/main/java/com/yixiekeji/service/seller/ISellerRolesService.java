package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerRoles;

/**
 * 商家角色
 *                       
 * @Filename: ISellerRolesService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerRoles")
@Service(value = "sellerRolesService")
public interface ISellerRolesService {

    /**
     * 根据id取得角色表
     * @param  sellerRolesId
     * @return
     */
    @RequestMapping(value = "getSellerRolesById", method = RequestMethod.GET)
    ServiceResult<SellerRoles> getSellerRolesById(@RequestParam("sellerRolesId") Integer sellerRolesId);

    /**
     * 保存角色表
     * @param  sellerRoles
     * @return
     */
    @RequestMapping(value = "saveSellerRoles", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerRoles(SellerRoles sellerRoles);

    /**
    * 更新角色表
    * @param  sellerRoles
    * @return
    */
    @RequestMapping(value = "updateSellerRoles", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerRoles(SellerRoles sellerRoles);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "getSellerRoles", method = RequestMethod.POST)
    ServiceResult<List<SellerRoles>> getSellerRoles(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteSellerRole", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteSellerRole(@RequestParam("id") Integer id);
}