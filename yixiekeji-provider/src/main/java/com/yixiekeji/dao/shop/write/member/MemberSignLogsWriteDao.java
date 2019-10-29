package com.yixiekeji.dao.shop.write.member;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberSignLogs;

@Mapper
public interface MemberSignLogsWriteDao {

    //	MemberSignLogs get(java.lang.Integer id);

    Integer getCountByMemberIdAndDate(@Param("memberId") Integer memberId,
                                      @Param("start") String start, @Param("end") String end);

    Integer insert(MemberSignLogs memberSignLogs);

    //	Integer update(MemberSignLogs memberSignLogs);

    //	Integer delete(java.lang.Integer id);

}