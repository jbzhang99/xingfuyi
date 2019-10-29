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
import com.yixiekeji.entity.seller.SellerApply;

/**
 * 商家申请
 *                       
 * @Filename: ISellerApplyService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "sellerApply")
@Service(value = "sellerApplyService")
public interface ISellerApplyService {

    /**
     * 根据id取得商家申请表
     * @param  sellerApplyId
     * @return
     */
    @RequestMapping(value = "getSellerApplyById", method = RequestMethod.GET)
    ServiceResult<SellerApply> getSellerApplyById(@RequestParam("sellerApplyId") Integer sellerApplyId);

    /**
     * 保存商家申请表
     * @param  sellerApply
     * @return
     */
    @RequestMapping(value = "saveSellerApply", method = RequestMethod.POST)
    ServiceResult<Integer> saveSellerApply(SellerApply sellerApply);

    /**
     * 修改商家申请
     * @param sellerApply
     * @return
     */
    @RequestMapping(value = "updateSellerApply", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerApply(SellerApply sellerApply);

    /**
     * 删除该商家申请,同时删除该商家账号
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteSellerApply", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteSellerApply(@RequestParam("id") Integer id,
                                             @RequestParam("memberId") Integer memberId);

    /**
     * 根据条件分页查询商家申请，PagerInfo传null取全部数据
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "getSellerApplys", method = RequestMethod.POST)
    ServiceResult<List<SellerApply>> getSellerApplys(FeignUtil feignUtil);

    /**
     * 根据用户ID获取其商家入驻申请
     * @param userid
     * @return
     */
    @RequestMapping(value = "getSellerApplyByUser", method = RequestMethod.GET)
    ServiceResult<SellerApply> getSellerApplyByUser(@RequestParam("memberId") Integer memberId);

    /**
     * 审核商家申请，审核通过同步更新商家状态为审核通过
     * @param sellerApplyId 商家申请ID
     * @param state 申请状态
     * @param optUserId 操作人ID
     * @return
     */
    @RequestMapping(value = "auditSellerApply", method = RequestMethod.GET)
    ServiceResult<Boolean> auditSellerApply(@RequestParam("sellerApplyId") Integer sellerApplyId,
                                            @RequestParam("state") Integer state,
                                            @RequestParam("optUserId") Integer optUserId);

    /**
     * 
     * 保存商家申请(平台添加商家申请用)<br>
     * <li>新增一个普通用户表（兼容用户端申请）
     * <li>添加商家申请表数据
     * <li>添加商家数据
     * 
     * @param sellerApply 商家申请
     * @param userName 商家登录名称
     * @param sellerName 商家店铺名称
     * @param ip 操作IP
     * @return
     */
    @RequestMapping(value = "saveSellerApplyForAdmin", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerApplyForAdmin(@RequestBody SellerApply sellerApply,
                                                   @RequestParam("userName") String userName,
                                                   @RequestParam("sellerName") String sellerName,
                                                   @RequestParam("ip") String ip);

    /**
     * 
     * 修改商家申请(平台修改商家申请用)
     * 
     * @param sellerApply 商家申请
     * @param userName 商家登录名称
     * @param sellerName 商家店铺名称
     * @return
     */
    @RequestMapping(value = "updateSellerApplyForAdmin", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerApplyForAdmin(@RequestBody SellerApply sellerApply,
                                                     @RequestParam("userName") String userName,
                                                     @RequestParam("sellerName") String sellerName);

    /**
     * 
     * 保存商家申请(用户端商家申请用)<br>
     * <li>添加商家申请表数据
     * <li>添加商家数据
     * 
     * @param sellerApply 商家申请
     * @param userName 商家登录名称
     * @param sellerName 商家店铺名称
     * @param memberId
     * @return
     */
    @RequestMapping(value = "saveSellerApplyForFront", method = RequestMethod.POST)
    ServiceResult<Boolean> saveSellerApplyForFront(@RequestBody SellerApply sellerApply,
                                                   @RequestParam("userName") String userName,
                                                   @RequestParam("sellerName") String sellerName,
                                                   @RequestParam("memberId") Integer memberId);

    /**
     * 
     * 修改商家申请(用户端修改商家申请用)
     * 
     * @param sellerApply 商家申请
     * @param userName 商家登录名称
     * @param sellerName 商家店铺名称
     * @return
     */
    @RequestMapping(value = "updateSellerApplyForFront", method = RequestMethod.POST)
    ServiceResult<Boolean> updateSellerApplyForFront(@RequestBody SellerApply sellerApply,
                                                     @RequestParam("userName") String userName,
                                                     @RequestParam("sellerName") String sellerName);

    /**
     * 根据公司名称查询入驻申请
     * @param company
     * @return
     */
    @RequestMapping(value = "getSellerApplyByCompany", method = RequestMethod.GET)
    ServiceResult<List<SellerApply>> getSellerApplyByCompany(@RequestParam("company") String company);

    /**
     * 当天商家申请数
     * @return
     */
    @RequestMapping(value = "getSellerApplyCount", method = RequestMethod.GET)
    ServiceResult<Integer> getSellerApplyCount();
}