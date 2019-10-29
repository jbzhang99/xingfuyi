package com.yixiekeji.model.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.member.MemberProductExchangeLogReadDao;
import com.yixiekeji.dao.shop.write.member.MemberProductExchangeLogWriteDao;
import com.yixiekeji.entity.member.MemberProductExchangeLog;

@Component
public class MemberProductExchangeLogModel {

    @Resource
    private MemberProductExchangeLogWriteDao memberProductExchangeLogWriteDao;
    @Resource
    private MemberProductExchangeLogReadDao  memberProductExchangeLogReadDao;

    /**
     * 根据id取得换货日志表
     * @param  memberProductExchangeLogId
     * @return
     */
    public MemberProductExchangeLog getMemberProductExchangeLogById(Integer memberProductExchangeLogId) {
        return memberProductExchangeLogWriteDao.get(memberProductExchangeLogId);
    }

    /**
     * 保存换货日志表
     * @param  memberProductExchangeLog
     * @return
     */
    public Integer saveMemberProductExchangeLog(MemberProductExchangeLog memberProductExchangeLog) {

        return memberProductExchangeLogWriteDao.insert(memberProductExchangeLog);
    }

    /**
    * 更新换货日志表
    * @param  memberProductExchangeLog
    * @return
    */
    public Integer updateMemberProductExchangeLog(MemberProductExchangeLog memberProductExchangeLog) {

        return memberProductExchangeLogWriteDao.update(memberProductExchangeLog);
    }

    /**
     * 根据memberProductExchangeLogId查询换货日志信息
     * @param memberProductExchangeId
     * @return
     */
    public List<MemberProductExchangeLog> getMemberProductExchangeLogByMemberProductExchangeId(Integer memberProductExchangeId) {

        return memberProductExchangeLogReadDao
            .getMemberProductExchangeLogByMemberProductExchangeId(memberProductExchangeId);
    }
}