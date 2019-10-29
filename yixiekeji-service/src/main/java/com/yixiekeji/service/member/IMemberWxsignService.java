package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberWxsign;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberWxsign")
@Service(value = "memberWxsignService")
public interface IMemberWxsignService {

    /**
     * 根据id取得微信联合登录
     * @param  memberWxsignId
     * @return
     */
    @RequestMapping(value = "getMemberWxsignById", method = RequestMethod.GET)
    ServiceResult<MemberWxsign> getMemberWxsignById(@RequestParam("memberWxsignId") Integer memberWxsignId);

    /**
     * 保存微信联合登录
     * @param  memberWxsign
     * @return
     */
    @RequestMapping(value = "saveMemberWxsign", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberWxsign(MemberWxsign memberWxsign);

    /**
    * 更新微信联合登录
    * @param  memberWxsign
    * @return
    */
    @RequestMapping(value = "updateMemberWxsign", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberWxsign(MemberWxsign memberWxsign);

    /**
    * 分页查询
    * @param queryMap
    * @param pager
    * @return
    */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    ServiceResult<List<MemberWxsign>> page(FeignUtil feignUtil);

    /**
     * 以认证用户获取member信息<br>
     * 如果用户已绑定，返回原用户，否则创建新的用户
     * @param openid 用户标识
     * @return
     */
    @RequestMapping(value = "getWxUser", method = RequestMethod.GET)
    ServiceResult<MemberWxsign> getWxUser(@RequestParam("openid") String openid);
}