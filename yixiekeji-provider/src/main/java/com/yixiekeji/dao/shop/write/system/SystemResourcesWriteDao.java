package com.yixiekeji.dao.shop.write.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.system.SystemResources;

@Mapper
public interface SystemResourcesWriteDao {

    SystemResources get(java.lang.Integer id);

    Integer save(SystemResources systemResources);

    Integer update(SystemResources systemResources);

    Integer getCount(Map<String, String> queryMap);

    List<SystemResources> page(Map<String, String> queryMap);

    List<SystemResources> list(Map<String, Object> queryMap);

    Integer del(Integer id);

}
