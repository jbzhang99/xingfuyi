package com.yixiekeji.service.member;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.member.MemberAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收获地址接口
 *                       
 * @Filename: IMemberAddressService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberAddress")
@Service(value = "memberAddressService")
public interface IMemberAddressService {

    /**
     * 根据会员ID获取会员地址
     * @param memberId 会员ID
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getMemberAddresses", method = RequestMethod.POST)
    ServiceResult<List<MemberAddress>> getMemberAddresses(@RequestParam("memberId") Integer memberId,
                                                          @RequestBody PagerInfo pager);

    /**
     * 根据用户ID获得收货地址
     * @param memberId
     * @return
     */
    @RequestMapping(value = "getMemberAddressByMId", method = RequestMethod.GET)
    ServiceResult<List<MemberAddress>> getMemberAddressByMId(@RequestParam("memberId") Integer memberId);

    /**
     * 根据id获得收货地址信息
     * @param  id  收货地址id
     * @return
     */
    @RequestMapping(value = "getMemberAddressById", method = RequestMethod.GET)
    ServiceResult<MemberAddress> getMemberAddressById(@RequestParam("id") Integer id);

    /**
     * 保存收货地址
     * @param memberAddress
     * @return
     */
    @RequestMapping(value = "saveMemberAddress", method = RequestMethod.POST)
    ServiceResult<Boolean> saveMemberAddress(MemberAddress memberAddress);

    /**
     * 更新收货地址
     * @param  
     * @return
     */
    @RequestMapping(value = "updateMemberAddress", method = RequestMethod.POST)
    ServiceResult<Boolean> updateMemberAddress(MemberAddress memberAddress);

    /**
     * 删除收货地址
     * @param id 地址ID
     * @param memberId 用户ID
     * @return
     */
    @RequestMapping(value = "deleteMemberAddress", method = RequestMethod.GET)
    ServiceResult<Boolean> deleteMemberAddress(@RequestParam("id") Integer id,
                                               @RequestParam("memberId") Integer memberId);

    /**
     * 设置为默认地址
     * @param id 默认地址ID
     * @param memberId 用户ID
     * @return
     */
    @RequestMapping(value = "defaultMemberAddress", method = RequestMethod.GET)
    ServiceResult<Boolean> defaultMemberAddress(@RequestParam("id") Integer id,
                                                @RequestParam("memberId") Integer memberId);
    
    /** 
    * @Description: 添加地址
    * @Author: mofan 
    * @Date: 2019/10/23 
    */ 
    @RequestMapping(value = "saveAddress", method = RequestMethod.POST)
    Integer saveAddress(MemberAddress memberAddress);

    /**
     * @Description: 查询最新的地址ID
     * @Author: mofan
     * @Date: 2019/10/23
     */
    @GetMapping(value = "getMaxId")
    MemberAddress getMaxId();
}
