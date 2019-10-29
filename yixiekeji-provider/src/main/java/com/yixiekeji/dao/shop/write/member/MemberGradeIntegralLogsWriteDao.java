package com.yixiekeji.dao.shop.write.member;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberGradeIntegralLogs;

/**
 * 会员经验积分日志表
 * 
 * @Filename: MemberGradeIntegralLogsWriteDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberGradeIntegralLogsWriteDao {

    MemberGradeIntegralLogs get(java.lang.Integer id);

    Integer save(MemberGradeIntegralLogs memberGradeIntegralLogs);

    Integer update(MemberGradeIntegralLogs memberGradeIntegralLogs);
}
