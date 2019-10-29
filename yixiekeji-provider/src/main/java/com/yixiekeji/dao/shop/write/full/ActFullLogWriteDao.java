package com.yixiekeji.dao.shop.write.full;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.full.ActFullLog;

@Mapper
public interface ActFullLogWriteDao {

    ActFullLog get(java.lang.Integer id);

    Integer insert(ActFullLog actFullLog);

    Integer update(ActFullLog actFullLog);

    /**
     * 批量新增订单满减参与日志
     * @param actFullLogList
     * @return
     */
    Integer batchInsertActFullLog(List<ActFullLog> actFullLogList);
}