package com.yixiekeji.dao.shop.write.member;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberBalanceLogs;

/**
 * MemberBalanceLogs
 * 
 * @Filename: MemberBalanceLogsWriteDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberBalanceLogsWriteDao {

    MemberBalanceLogs get(java.lang.Integer id);

    Integer save(MemberBalanceLogs memberBalanceLogs);

    Integer update(MemberBalanceLogs memberBalanceLogs);

    Integer updateNotNull(MemberBalanceLogs memberBalanceLogs);

    Integer del(Integer id);
}
