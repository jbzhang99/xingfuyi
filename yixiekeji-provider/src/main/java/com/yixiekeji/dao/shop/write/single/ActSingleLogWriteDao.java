package com.yixiekeji.dao.shop.write.single;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.single.ActSingleLog;

@Mapper
public interface ActSingleLogWriteDao {

    ActSingleLog get(java.lang.Integer id);

    Integer insert(ActSingleLog actSingleLog);

    Integer update(ActSingleLog actSingleLog);

    /**
     * 批量新增单品立减参与日志
     * @param actSingleLogList
     * @return
     */
    Integer batchInsertActSingleLog(List<ActSingleLog> actSingleLogList);
}