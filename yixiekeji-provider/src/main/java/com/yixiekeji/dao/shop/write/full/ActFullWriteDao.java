package com.yixiekeji.dao.shop.write.full;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.full.ActFull;

@Mapper
public interface ActFullWriteDao {

    ActFull get(java.lang.Integer id);

    Integer insert(ActFull actFull);

    Integer update(ActFull actFull);

    /**
     * 只修改活动状态、审核意见、修改者信息
     * @param actFull
     * @return
     */
    Integer updateStatus(ActFull actFull);

    Integer delete(Integer id);
}