package com.yixiekeji.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberBalancePayLog;

@Mapper
public interface MemberBalancePayLogWriteDao {

    MemberBalancePayLog get(java.lang.Integer id);

    Integer save(MemberBalancePayLog memberBalancePayLog);

    Integer update(MemberBalancePayLog memberBalancePayLog);

    Integer getCount(Map<String, Object> queryMap);

    List<MemberBalancePayLog> page(Map<String, Object> queryMap);

    Integer del(Integer id);

    MemberBalancePayLog getByOrderSn(String orderNo);

}