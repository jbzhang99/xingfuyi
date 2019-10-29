package com.yixiekeji.dao.shop.write.sale;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.sale.SaleMember;

@Mapper
public interface SaleMemberWriteDao {

    SaleMember get(java.lang.Integer id);

    Integer insert(SaleMember saleMember);

    Integer update(SaleMember saleMember);

    SaleMember getSaleMemberByMemberId(Integer memberId);
}