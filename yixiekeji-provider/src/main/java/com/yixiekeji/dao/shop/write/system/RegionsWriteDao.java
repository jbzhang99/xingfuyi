package com.yixiekeji.dao.shop.write.system;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.system.Regions;

@Mapper
public interface RegionsWriteDao {

    Regions get(Integer id);

}
