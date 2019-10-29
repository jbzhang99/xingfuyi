package com.yixiekeji.dao.shop.write.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.system.SystemAdmin;

@Mapper
public interface SystemAdminWriteDao {

    SystemAdmin get(Integer id);

    Integer save(SystemAdmin systemAdmin);

    Integer update(SystemAdmin systemAdmin);

    Integer getCount(@Param("queryMap") Map<String, String> queryMap);

    List<SystemAdmin> page(@Param("queryMap") Map<String, String> queryMap,
                           @Param("start") Integer start, @Param("size") Integer size);

    SystemAdmin getByNamePwd(Map<String, Object> queryMap);

    Integer del(Integer id);

    Integer getCountByRoleId(Integer roleId);

    SystemAdmin checkName(@Param("name") String name);
}
