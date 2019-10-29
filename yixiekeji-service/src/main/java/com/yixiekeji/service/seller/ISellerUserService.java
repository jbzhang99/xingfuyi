package com.yixiekeji.service.seller;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.seller.SellerUserLoginLog;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerUser")
@Service(value = "sellerUserService")
public interface ISellerUserService {

    /**
     * 根据id取得系统管理员表
     * @param  sellerUserId
     * @return
     */
    @RequestMapping(value = "getSellerUserById", method = RequestMethod.GET)
    ServiceResult<SellerUser> getSellerUserById(@RequestParam("sellerUserId") Integer sellerUserId);

    /**
     * 保存系统管理员表
     * @param  sellerUser
     * @return
     */
    @RequestMapping(value = "saveSellerUser", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerUser(SellerUser sellerUser);

    /**
    * 更新系统管理员表
    * @param  sellerUser
    * @return
    */
    @RequestMapping(value = "updateSellerUser", method = RequestMethod.POST)
    ServiceResult<Integer> updateSellerUser(SellerUser sellerUser);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<SellerUser>> page(FeignUtil feignUtil);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);

    /**
     * 根据用户名密码取商家用户
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "getSellerUserByNamePwd", method = RequestMethod.GET)
    ServiceResult<SellerUser> getSellerUserByNamePwd(@RequestParam("name") String name,
                                                     @RequestParam("password") String password);

    /**
     * 根据用户名取商家用户
     * @param name
     * @return
     */
    @RequestMapping(value = "getSellerUserByName", method = RequestMethod.GET)
    ServiceResult<List<SellerUser>> getSellerUserByName(@RequestParam("name") String name);

    /**
     * 保存管理员表登录日志
     * @param  sellerUserLoginLog
     * @return
     */
    @RequestMapping(value = "saveSellerUserLoginLog", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerUserLoginLog(SellerUserLoginLog sellerUserLoginLog);
}