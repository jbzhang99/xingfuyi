package com.yixiekeji.dao.shop.write.member;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductExchangeLog;

@Mapper
public interface MemberProductExchangeLogWriteDao {

    MemberProductExchangeLog get(java.lang.Integer id);

    Integer insert(MemberProductExchangeLog memberProductExchangeLog);

    Integer update(MemberProductExchangeLog memberProductExchangeLog);

    Integer delete(java.lang.Integer id);

}