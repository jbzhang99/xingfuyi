package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberProductBackLog;

@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberProductBackLog")
@Service(value = "memberProductBackLogService")
public interface IMemberProductBackLogService {

    /**
     * 根据id取得退货日志表
     * @param  memberProductBackLogId
     * @return
     */
    @RequestMapping(value = "getMemberProductBackLogById", method = RequestMethod.GET)
    ServiceResult<MemberProductBackLog> getMemberProductBackLogById(@RequestParam("memberProductBackLogId") Integer memberProductBackLogId);

    /**
     * 保存退货日志表
     * @param  memberProductBackLog
     * @return
     */
    @RequestMapping(value = "saveMemberProductBackLog", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberProductBackLog(MemberProductBackLog memberProductBackLog);

    /**
    * 更新退货日志表
    * @param  memberProductBackLog
    * @return
    */
    @RequestMapping(value = "updateMemberProductBackLog", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberProductBackLog(MemberProductBackLog memberProductBackLog);

    /**
     * 根据退货ID获取退货日志表
     * @param memberProductBackLogId
     * @return
     */
    @RequestMapping(value = "getMemberProductBackLogByMemberProductBackId", method = RequestMethod.GET)
    ServiceResult<List<MemberProductBackLog>> getMemberProductBackLogByMemberProductBackId(@RequestParam("memberProductBackLogId") Integer memberProductBackLogId);
}