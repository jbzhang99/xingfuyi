package com.yixiekeji.dao.shop.read.sale;

import com.yixiekeji.entity.sale.SaleMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SaleMemberReadDao {

    SaleMember get(java.lang.Integer id);

    /**
     * 根据用户ID查询用户推广表
     * @param memberId
     * @return
     */
    SaleMember getSaleMemberByMemberId(Integer memberId);

    int getSaleMembersCount(@Param("queryMap") Map<String, String> queryMap);

    List<SaleMember> getSaleMembers(@Param("queryMap") Map<String, String> queryMap,
                                    @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 根据推广码来获取推广用户
     * @param saleCode
     * @return
     */
    SaleMember getSaleMemberBySaleCode(String saleCode);

    /**
     * @Description:  校验推广码是否存在
     * @Author: mofan
     * @Date: 2019/10/10
     */
    List<SaleMember> checkSaleCode(@Param("saleCode") String saleCode);

    /**
     * @Description: 根据id获取残疾人/监护人身份证认证信息
     * @Author: mofan
     * @Date: 2019/10/21
     */
    SaleMember getAuthInfoById(@Param("saleMemberId")Integer saleMemberId, @Param("state")Integer state);
}