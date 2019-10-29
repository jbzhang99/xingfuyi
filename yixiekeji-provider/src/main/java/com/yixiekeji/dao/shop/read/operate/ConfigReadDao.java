package com.yixiekeji.dao.shop.read.operate;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.operate.Config;

@Mapper
public interface ConfigReadDao {

    Config get(java.lang.Integer id);

}