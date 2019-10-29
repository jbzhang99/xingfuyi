package com.yixiekeji.dao.shop.write.operate;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.operate.Config;

@Mapper
public interface ConfigWriteDao {

    Config get(java.lang.Integer id);

    Integer insert(Config config);

    Integer update(Config config);
}