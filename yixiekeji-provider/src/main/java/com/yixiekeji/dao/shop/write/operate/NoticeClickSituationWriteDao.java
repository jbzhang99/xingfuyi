package com.yixiekeji.dao.shop.write.operate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.operate.NoticeClickSituation;

@Mapper
public interface NoticeClickSituationWriteDao {

    NoticeClickSituation get(java.lang.Integer id);

    Integer save(NoticeClickSituation noticeClickSituation);

    Integer update(NoticeClickSituation noticeClickSituation);

    Integer getCount(Map<String, Object> queryMap);

    List<NoticeClickSituation> page(Map<String, Object> queryMap);

    Integer del(Integer id);

}