package com.yixiekeji.dao.shop.write.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberLoginLogs;

/**
 * 会员登录日志
 * 
 * @Filename: MemberLoginLogsWriteDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberLoginLogsWriteDao {

    MemberLoginLogs get(java.lang.Integer id);

    Integer save(MemberLoginLogs memberLoginLogs);

    Integer update(MemberLoginLogs memberLoginLogs);

    /**
     * 根据会员ID获取会员某个时间段的登录日志
     * @param memberId
     * @param startTime
     * @param endTime
     * @return
     */
    List<MemberLoginLogs> getByMemberIdAndTime(@Param("memberId") Integer memberId,
                                               @Param("startTime") String startTime,
                                               @Param("endTime") String endTime);
}
