package com.yixiekeji.dao.shop.write.single;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.single.LogActSingle;

@Mapper
public interface LogActSingleWriteDao {

    // LogActSingle get(java.lang.Integer id);

    Integer insert(LogActSingle logActSingle);

    // Integer update(LogActSingle logActSingle);
}