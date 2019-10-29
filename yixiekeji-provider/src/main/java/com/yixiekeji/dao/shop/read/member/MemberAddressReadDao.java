package com.yixiekeji.dao.shop.read.member;

import com.yixiekeji.entity.member.MemberAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收货地址
 *                       
 * @Filename: MemberAddressReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberAddressReadDao {

    MemberAddress get(java.lang.Integer id);

    /**
     * 根据会员ID获取会员地址
     * @param memberId
     * @param start
     * @param size
     * @return
     */
    List<MemberAddress> getMemberAddresses(@Param("memberId") Integer memberId,
                                           @Param("start") Integer start,
                                           @Param("size") Integer size);

    /**
     * 根据会员ID获取会员地址数量
     * @param memberId
     * @return
     */
    Integer getMemberAddressesCount(@Param("memberId") Integer memberId);


    MemberAddress getMaxId();
}
