package com.yixiekeji.dao.shop.read.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductExchangeLog;

@Mapper
public interface MemberProductExchangeLogReadDao {

    MemberProductExchangeLog get(java.lang.Integer id);

    /**
     * 根据memberProductExchangeLogId查询换货日志信息
     * @param memberProductExchangeId
     * @return
     */
    List<MemberProductExchangeLog> getMemberProductExchangeLogByMemberProductExchangeId(@Param("memberProductExchangeId") Integer memberProductExchangeId);

}