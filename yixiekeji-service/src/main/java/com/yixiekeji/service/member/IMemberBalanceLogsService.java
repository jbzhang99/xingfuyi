package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberBalanceLogs;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberBalanceLogs")
@Service(value = "memberBalanceLogsService")
public interface IMemberBalanceLogsService {

    /**
     * 根据会员ID获取会员账户余额变化日志
     * @param memberId 会员ID
     * @param pager 分页信息
     * @return
     */
    @RequestMapping(value = "getMemberBalanceLogs", method = RequestMethod.POST)
    ServiceResult<List<MemberBalanceLogs>> getMemberBalanceLogs(@RequestParam("memberId") Integer memberId,
                                                                @RequestBody PagerInfo pager);

    /**
     * 更新会员账户余额变化日志表
     * @param memberBalanceLogs
     * @return
     */
    @RequestMapping(value = "updateMemberBalanceLogs", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberBalanceLogs(MemberBalanceLogs memberBalanceLogs);

    /**
     * 根据id取得会员账户余额变化日志表
     * @param  memberBalanceLogsId
     * @return
     */
    @RequestMapping(value = "getMemberBalanceLogsById", method = RequestMethod.GET)
    ServiceResult<MemberBalanceLogs> getMemberBalanceLogsById(@RequestParam("memberBalanceLogsId") Integer memberBalanceLogsId);

    /**
     * 保存会员账户余额变化日志表
     * @param  memberBalanceLogs
     * @return
     */
    @RequestMapping(value = "saveMemberBalanceLogs", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberBalanceLogs(MemberBalanceLogs memberBalanceLogs);

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "del", method = RequestMethod.GET)
    ServiceResult<Boolean> del(@RequestParam("id") Integer id);
}