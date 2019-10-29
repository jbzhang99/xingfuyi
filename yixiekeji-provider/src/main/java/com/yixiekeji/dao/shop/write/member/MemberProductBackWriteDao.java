package com.yixiekeji.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductBack;

@Mapper
public interface MemberProductBackWriteDao {

    MemberProductBack get(java.lang.Integer id);

    Integer save(MemberProductBack memberProductBack);

    Integer update(MemberProductBack memberProductBack);

    Integer del(Integer id);

    Integer queryCount(Map<String, Object> map);

    List<MemberProductBack> queryList(Map<String, Object> map);

    Integer upStateReturn(@Param("id") Integer id,@Param("state") Integer state);
}
