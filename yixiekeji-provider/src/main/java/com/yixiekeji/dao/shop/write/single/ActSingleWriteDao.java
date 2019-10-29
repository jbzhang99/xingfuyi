package com.yixiekeji.dao.shop.write.single;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.single.ActSingle;

@Mapper
public interface ActSingleWriteDao {

    ActSingle get(java.lang.Integer id);

    Integer insert(ActSingle actSingle);

    Integer update(ActSingle actSingle);

    /**
     * 只修改活动状态、审核意见、修改者信息
     * @param actFull
     * @return
     */
    Integer updateStatus(ActSingle actSingle);

    Integer delete(Integer id);
}