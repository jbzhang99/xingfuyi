package com.yixiekeji.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberWxsign;

@Mapper
public interface MemberWxsignWriteDao {

    MemberWxsign get(java.lang.Integer id);

    Integer save(MemberWxsign memberWxsign);

    Integer update(MemberWxsign memberWxsign);

    Integer getCount(Map<String, Object> queryMap);

    List<MemberWxsign> page(Map<String, Object> queryMap);

    Integer del(Integer id);

    MemberWxsign getByOpenId(String openid);

}