package com.yixiekeji.dao.shop.write.member;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductBackLog;

@Mapper
public interface MemberProductBackLogWriteDao {

    MemberProductBackLog get(java.lang.Integer id);

    Integer insert(MemberProductBackLog memberProductBackLog);

    Integer update(MemberProductBackLog memberProductBackLog);

    Integer delete(java.lang.Integer id);

}