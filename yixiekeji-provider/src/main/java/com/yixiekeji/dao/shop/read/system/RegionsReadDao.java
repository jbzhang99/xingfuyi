package com.yixiekeji.dao.shop.read.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.system.Regions;

@Mapper
public interface RegionsReadDao {

    Regions get(java.lang.Integer id);

    List<Regions> getProvince();

    List<Regions> getChildrenArea(Map<String, Object> map);

    List<Regions> getByParentId(Integer parentId);

}
