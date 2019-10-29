package com.yixiekeji.dao.shop.read.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberLoginLogs;

/**
 * 会员登录日志
 * 
 * @Filename: MemberLoginLogsReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberLoginLogsReadDao {

    MemberLoginLogs get(java.lang.Integer id);

    /**
     * 根据会员ID获取会员登录日志
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    List<MemberLoginLogs> getMemberLoginLogs(@Param("memberId") Integer memberId,
                                             @Param("start") Integer start,
                                             @Param("size") Integer size);

    /**
     * 根据会员ID获取会员登录日志数量
     * @param memberId
     * @return
     */
    Integer getMemberLoginLogsCount(@Param("memberId") Integer memberId);
}
