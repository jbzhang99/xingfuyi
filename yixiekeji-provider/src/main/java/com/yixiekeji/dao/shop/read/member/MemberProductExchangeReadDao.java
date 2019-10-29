package com.yixiekeji.dao.shop.read.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductExchange;

/**
 * 换货申请
 *                       
 * @Filename: MemberProductExchangeReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberProductExchangeReadDao {

    MemberProductExchange get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<MemberProductExchange> queryList(Map<String, Object> map);

    Integer getCount(@Param("param1") Map<String, String> queryMap);

    List<MemberProductExchange> page(@Param("param1") Map<String, String> queryMap,
                                     @Param("start") Integer start, @Param("size") Integer size);

}
