package com.yixiekeji.model.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.member.MemberProductBackLogReadDao;
import com.yixiekeji.dao.shop.write.member.MemberProductBackLogWriteDao;
import com.yixiekeji.entity.member.MemberProductBackLog;

@Component
public class MemberProductBackLogModel {

    @Resource
    private MemberProductBackLogWriteDao memberProductBackLogWriteDao;
    @Resource
    private MemberProductBackLogReadDao  memberProductBackLogReadDao;

    /**
     * 根据id取得退货日志表
     * @param  memberProductBackLogId
     * @return
     */
    public MemberProductBackLog getMemberProductBackLogById(Integer memberProductBackLogId) {
        return memberProductBackLogReadDao.get(memberProductBackLogId);
    }

    /**
     * 保存退货日志表
     * @param  memberProductBackLog
     * @return
     */
    public Integer saveMemberProductBackLog(MemberProductBackLog memberProductBackLog) {

        return memberProductBackLogWriteDao.insert(memberProductBackLog);
    }

    /**
    * 更新退货日志表
    * @param  memberProductBackLog
    * @return
    */
    public Integer updateMemberProductBackLog(MemberProductBackLog memberProductBackLog) {

        return memberProductBackLogWriteDao.update(memberProductBackLog);
    }

    /**
     * 根据退货ID获取退货日志信息
     * @param memberProductBackLogId
     * @return
     */
    public List<MemberProductBackLog> getMemberProductBackLogByMemberProductBackId(Integer memberProductBackLogId) {

        return memberProductBackLogReadDao
            .getMemberProductBackLogByMemberProductBackId(memberProductBackLogId);
    }

}