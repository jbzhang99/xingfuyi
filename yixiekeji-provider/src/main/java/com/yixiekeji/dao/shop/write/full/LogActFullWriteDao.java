package com.yixiekeji.dao.shop.write.full;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.full.LogActFull;

@Mapper
public interface LogActFullWriteDao {

    // LogActFull get(java.lang.Integer id);

    Integer insert(LogActFull logActFull);

    // Integer update(LogActFull logActFull);
}