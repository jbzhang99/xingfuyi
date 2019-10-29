package com.yixiekeji.dao.shop.write.integral;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegralType;

@Mapper
public interface ActIntegralTypeWriteDao {

    ActIntegralType get(java.lang.Integer id);

    Integer insert(ActIntegralType actIntegralType);

    Integer update(ActIntegralType actIntegralType);

    Integer audit(@Param("id") Integer id, @Param("state") Integer state);

    Integer del(Integer id);
}