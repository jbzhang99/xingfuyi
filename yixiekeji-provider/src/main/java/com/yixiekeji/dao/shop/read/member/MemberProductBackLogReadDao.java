package com.yixiekeji.dao.shop.read.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductBackLog;

@Mapper
public interface MemberProductBackLogReadDao {

    MemberProductBackLog get(java.lang.Integer id);

    /**
     * 根据退货ID获取退货日志信息
     * @param memberProductBackLogId
     * @return
     */
    List<MemberProductBackLog> getMemberProductBackLogByMemberProductBackId(@Param("memberProductBackLogId") Integer memberProductBackLogId);

}