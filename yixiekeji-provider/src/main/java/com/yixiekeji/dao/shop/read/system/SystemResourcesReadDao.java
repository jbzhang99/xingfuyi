package com.yixiekeji.dao.shop.read.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.system.SystemResources;

@Mapper
public interface SystemResourcesReadDao {

    SystemResources get(java.lang.Integer id);

    Integer getCount(Map<String, String> queryMap);

    List<SystemResources> page(Map<String, String> queryMap);

    List<SystemResources> list(Map<String, Object> queryMap);

    List<SystemResources> getByPId(Integer pid);

}
