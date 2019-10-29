package com.yixiekeji.dao.shop.write.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberProductExchange;

@Mapper
public interface MemberProductExchangeWriteDao {

    MemberProductExchange get(Integer id);

    Integer save(MemberProductExchange ex);

    Integer update(MemberProductExchange ex);

    Integer queryCount(Map<String, Object> map);

    List<MemberProductExchange> queryList(Map<String, Object> map);

    Integer upState(@Param("id") Integer id, @Param("state") Integer state);

    /**
     * 根据网单ID获取该网单已经换货的数量
     * @param orderProductId
     * @return
     */
    Integer getExchangeNumByOpId(Integer orderProductId);
}
