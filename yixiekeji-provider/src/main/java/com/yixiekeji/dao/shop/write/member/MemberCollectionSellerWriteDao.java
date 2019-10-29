package com.yixiekeji.dao.shop.write.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberCollectionSeller;

/**
 * 会员收藏商铺表
 * 
 * @Filename: MemberCollectionSellerWriteDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberCollectionSellerWriteDao {

    /**
     * 根据店铺ID和用户ID获取店铺收藏
     * @param sellerId
     * @param memberId
     * @return
     */
    List<MemberCollectionSeller> getBySellerIdAndMId(@Param("sellerId") Integer sellerId,
                                                     @Param("memberId") Integer memberId);

    Integer save(MemberCollectionSeller memberCollectionSeller);

    Integer cancelCollectionSeller(@Param("sellerId") Integer sellerId,
                                   @Param("memberId") Integer memberId);

    MemberCollectionSeller get(java.lang.Integer id);

    Integer update(MemberCollectionSeller memberCollectionSeller);
}
