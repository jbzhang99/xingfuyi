package com.yixiekeji.model.member;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.member.MemberBalanceLogsReadDao;
import com.yixiekeji.dao.shop.write.member.MemberBalanceLogsWriteDao;
import com.yixiekeji.entity.member.MemberBalanceLogs;

@Service(value = "memberBalanceLogsModel")
public class MemberBalanceLogsModel {

    @Resource
    private MemberBalanceLogsWriteDao memberBalanceLogsWriteDao;

    @Resource
    private MemberBalanceLogsReadDao  memberBalanceLogsReadDao;

    /**
     * 根据会员ID获取会员账户余额变化日志数量
     * @param memberId
     * @return
     */
    public Integer getMemberBalanceLogsCount(Integer memberId) {
        return memberBalanceLogsReadDao.getMemberBalanceLogsCount(memberId);
    }

    /**
     * 根据会员ID获取会员账户余额变化日志
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    public List<MemberBalanceLogs> getMemberBalanceLogs(Integer memberId, Integer start,
                                                        Integer size) {
        return memberBalanceLogsReadDao.getMemberBalanceLogs(memberId, start, size);
    }

    /**
    * 根据id取得会员账户余额变化日志表
    * @param  memberBalanceLogsId
    * @return
    */
    public MemberBalanceLogs getMemberBalanceLogsById(Integer memberBalanceLogsId) {
        return memberBalanceLogsReadDao.get(memberBalanceLogsId);
    }

    /**
     * 保存会员账户余额变化日志表
     * @param  memberBalanceLogs
     * @return
     */
    public Integer saveMemberBalanceLogs(MemberBalanceLogs memberBalanceLogs) {
        return memberBalanceLogsWriteDao.save(memberBalanceLogs);
    }

    /**
    * 更新会员账户余额变化日志表
    * @param  memberBalanceLogs
    * @return
    */
    public Integer updateMemberBalanceLogs(MemberBalanceLogs memberBalanceLogs) {
        return memberBalanceLogsWriteDao.update(memberBalanceLogs);
    }

    public boolean del(Integer id) {

        if (id == null)
            throw new BusinessException("删除会员账户余额变化日志表[" + id + "]时出错");
        return memberBalanceLogsWriteDao.del(id) > 0;
    }
}
