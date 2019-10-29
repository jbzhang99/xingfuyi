package com.yixiekeji.dao.shop.write.pcindex;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.pcindex.PcIndexImage;

@Mapper
public interface PcIndexImageWriteDao {

    PcIndexImage get(java.lang.Integer id);

    Integer insert(PcIndexImage pcIndexImage);

    Integer update(PcIndexImage pcIndexImage);

    Integer delete(Integer id);
}