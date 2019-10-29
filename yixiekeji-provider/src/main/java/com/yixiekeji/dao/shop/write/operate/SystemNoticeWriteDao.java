package com.yixiekeji.dao.shop.write.operate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.operate.SystemNotice;

@Mapper
public interface SystemNoticeWriteDao {

    SystemNotice get(java.lang.Integer id);

    Integer save(SystemNotice systemNotice);

    Integer update(SystemNotice systemNotice);

    Integer getCount(Map<String, Object> queryMap);

    List<SystemNotice> page(Map<String, Object> queryMap);

    Integer del(Integer id);

    List<SystemNotice> getUnreadNotice(Map<String, Object> param);

    Integer getUnreadNoticeCount(Integer sellerId);

}