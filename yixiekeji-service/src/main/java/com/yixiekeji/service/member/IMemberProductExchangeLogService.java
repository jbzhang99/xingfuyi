package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberProductExchangeLog;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberProductExchangeLog")
@Service(value = "memberProductExchangeLogService")
public interface IMemberProductExchangeLogService {

    /**
     * 根据id取得换货日志表
     * @param  memberProductExchangeLogId
     * @return
     */
    @RequestMapping(value = "getMemberProductExchangeLogById", method = RequestMethod.GET)
    ServiceResult<MemberProductExchangeLog> getMemberProductExchangeLogById(@RequestParam("memberProductExchangeLogId") Integer memberProductExchangeLogId);

    /**
     * 保存换货日志表
     * @param  memberProductExchangeLog
     * @return
     */
    @RequestMapping(value = "saveMemberProductExchangeLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberProductExchangeLog(MemberProductExchangeLog memberProductExchangeLog);

    /**
    * 更新换货日志表
    * @param  memberProductExchangeLog
    * @return
    */
    @RequestMapping(value = "updateMemberProductExchangeLog", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberProductExchangeLog(MemberProductExchangeLog memberProductExchangeLog);

    /**
     * 根据memberProductExchangeLogId查询换货日志信息
     * @param exchangeid
     * @return
     */
    @RequestMapping(value = "getMemberProductExchangeLogByMemberProductExchangeId", method = RequestMethod.GET)
    ServiceResult<List<MemberProductExchangeLog>> getMemberProductExchangeLogByMemberProductExchangeId(@RequestParam("memberProductExchangeId") Integer memberProductExchangeId);
}