package com.yixiekeji.dao.shop.read.integral;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegralType;

@Mapper
public interface ActIntegralTypeReadDao {

    ActIntegralType get(java.lang.Integer id);

    int count(@Param("param1") Map<String, String> queryMap);

    List<ActIntegralType> getActIntegralTypes(@Param("param1") Map<String, String> queryMap,
                                              @Param("start") Integer start,
                                              @Param("size") Integer size);

    /**
     * 查询所有可用的积分商城分类
     * @return
     */
    List<ActIntegralType> getAll();

}