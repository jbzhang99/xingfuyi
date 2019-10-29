package com.yixiekeji.service.sale;

import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.sale.SaleMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "saleMember")
@Service(value = "saleMemberService")
public interface ISaleMemberService {

    /**
     * 根据id取得用户推广表
     * @param  saleMemberId
     * @return
     */
    @RequestMapping(value = "getSaleMemberById", method = RequestMethod.GET)
    ServiceResult<SaleMember> getSaleMemberById(@RequestParam("saleMemberId") Integer saleMemberId);

    /**
     * 保存用户推广表
     * @param  saleMember
     * @return
     */
    @RequestMapping(value = "saveSaleMember", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleMember(SaleMember saleMember);

    /**
     * 更新用户推广表
     * @param  saleMember
     * @return
     */
    @RequestMapping(value = "updateSaleMember", method = RequestMethod.POST)
    ServiceResult<Integer> updateSaleMember(SaleMember saleMember);

    /**
     * 根据用户ID查询用户推广表
     * @param
     * @return
     */
    @RequestMapping(value = "getSaleMemberByMemberId", method = RequestMethod.GET)
    ServiceResult<SaleMember> getSaleMemberByMemberId(@RequestParam("saleMemberId") Integer saleMemberId);

    /**
     * 通过用户ID开启推广员
     * @param  saleMember
     * @return
     */
    @RequestMapping(value = "saveSaleMemberByMemberId", method = RequestMethod.POST)
    ServiceResult<Integer> saveSaleMemberByMemberId(SaleMember saleMember);

    /**
     * 查询推广用户列表
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "getSaleMembers", method = RequestMethod.POST)
    ServiceResult<List<SaleMember>> getSaleMembers(FeignUtil feignUtil);

    /**
     * 根据推广码来获取推广用户
     * @param salecode
     * @return
     */
    @RequestMapping(value = "getSaleMemberBySaleCode", method = RequestMethod.GET)
    ServiceResult<SaleMember> getSaleMemberBySaleCode(@RequestParam("salecode") String salecode);

    /**
     * @Description: 校验推广码是否存在
     * @Author: mofan
     * @Date: 2019/10/9
     */
    @GetMapping(value = "/checkSaleCode")
    List<SaleMember> checkSaleCode(@RequestParam("saleCode") String saleCode);

    /** 
    * @Description: 根据id获取残疾人/监护人身份证认证信息 
    * @Author: mofan 
    * @Date: 2019/10/21 
    */
    @GetMapping(value = "/getAuthInfoById")
    SaleMember getAuthInfoById(@RequestParam("saleMemberId")Integer saleMemberId, @RequestParam("state")Integer state);
}