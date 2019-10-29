package com.yixiekeji.dao.shop.write.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberGradeDownLogs;

@Mapper
public interface MemberGradeDownLogsWriteDao {

    //	MemberGradeDownLogs get(java.lang.Integer id);

    /**
     * 根据时间获取日志
     * @param excuteTime，形如yyyy-MM-dd
     * @return
     */
    List<MemberGradeDownLogs> getByExcuteTime(@Param("excuteTime") String excuteTime);

    Integer insert(MemberGradeDownLogs memberGradeDownLogs);

    //	Integer update(MemberGradeDownLogs memberGradeDownLogs);

    //	Integer delete(java.lang.Integer id);

}