package com.yixiekeji.dao.shop.read.member;

import com.yixiekeji.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会员表
 * 
 */
@Mapper
public interface MemberReadDao {

    Member get(java.lang.Integer id);

    /**
     * 根据条件获取用户信息
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<Member> getMembers(@Param("queryMap") Map<String, String> queryMap,
                            @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据条件获取用户数量
     * @param queryMap
     * @return
     */
    Integer getMembersCount(@Param("queryMap") Map<String, String> queryMap);

    /**
     * 根据会员注册的月、日获取用户信息
     * @param day 已类似 -03-01 的形式传入月日
     * @param start
     * @param size
     * @return
     */
    List<Member> getMembersByDay(@Param("day") String day, @Param("start") Integer start,
                                 @Param("size") Integer size);

    Integer isMobExists(String mobile);

    Member getMemberBySessionId(@Param("sessionId") String sessionId);

    Member getMemberByToken(@Param("token") String token);

    List<Member> getPromotionUser(@Param("saleCode")String saleCode);
}
