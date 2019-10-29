package com.yixiekeji.dao.shop.read.operate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.operate.CourierCompany;

@Mapper
public interface CourierCompanyReadDao {

    CourierCompany get(Integer id);

    Integer getCount(@Param("param1") Map<String, String> queryMap);

    List<CourierCompany> page(@Param("param1") Map<String, String> queryMap,
                              @Param("start") Integer start, @Param("size") Integer size);

    List<CourierCompany> list();

}
