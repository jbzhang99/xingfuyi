package com.yixiekeji.dao.shop.read.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberGradeIntegralLogs;

/**
 * 会员经验积分日志表
 * 
 * @Filename: MemberGradeIntegralLogsReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberGradeIntegralLogsReadDao {

    MemberGradeIntegralLogs get(java.lang.Integer id);

    /**
     * 根据会员ID和类型获取会员经验值积分值变更日志
     * @param memberId
     * @param type
     * @param start
     * @param size
     * @return
     */
    List<MemberGradeIntegralLogs> getMemberGradeIntegralLogs(@Param("memberId") Integer memberId,
                                                             @Param("type") Integer type,
                                                             @Param("start") Integer start,
                                                             @Param("size") Integer size);

    /**
     * 根据会员ID和类型获取会员经验值积分值变更日志数量
     * @param memberId
     * @param type
     * @return
     */
    Integer getMemberGradeIntegralLogsCount(@Param("memberId") Integer memberId,
                                            @Param("type") Integer type);

    /**
     * 根据用户ID、关联code、操作类型获取积分或经验值日志
     * @param memberId 用户ID
     * @param optType 操作类型
     * @param refCode 关联code
     * @param type 1-经验值，2-积分
     * @return
     */
    MemberGradeIntegralLogs getIntLogByMIdAndOrderSnAndOptType(@Param("memberId") Integer memberId,
                                                               @Param("optType") Integer optType,
                                                               @Param("refCode") String refCode,
                                                               @Param("type") Integer type);
}
