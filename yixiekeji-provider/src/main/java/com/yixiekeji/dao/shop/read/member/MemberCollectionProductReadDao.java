package com.yixiekeji.dao.shop.read.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.member.MemberCollectionProduct;

/**
 * 会员收藏商品
 *                       
 * @Filename: MemberCollectionProductReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface MemberCollectionProductReadDao {

    MemberCollectionProduct get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<MemberCollectionProduct> queryList(Map<String, Object> map);

}
