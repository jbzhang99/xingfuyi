package com.yixiekeji.dao.shop.write.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberCollectionProduct;

/**
 * 会员收藏商品表
 * 
 * @Filename: MemberCollectionProductWriteDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberCollectionProductWriteDao {

    MemberCollectionProduct get(java.lang.Integer id);

    Integer save(MemberCollectionProduct memberCollectionProduct);

    Integer update(MemberCollectionProduct memberCollectionProduct);

    /**
     * 根据会员ID和商品ID获取会员收藏商品
     * @param memberId
     * @param productId
     * @return
     */
    List<MemberCollectionProduct> getByMIdAndPId(@Param("memberId") Integer memberId,
                                                 @Param("productId") Integer productId);
}
