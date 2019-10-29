package com.yixiekeji.dao.shop.read.search;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.search.SearchSetting;

@Mapper
public interface SearchSettingReadDao {

    SearchSetting get(java.lang.Integer id);

}