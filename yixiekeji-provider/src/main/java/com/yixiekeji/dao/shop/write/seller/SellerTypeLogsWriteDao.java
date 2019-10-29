package com.yixiekeji.dao.shop.write.seller;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerTypeLogs;

@Mapper
public interface SellerTypeLogsWriteDao {

    Integer insert(SellerTypeLogs sellerTypeLogs);

    Integer update(SellerTypeLogs sellerTypeLogs);

    Integer del(Integer id);

    SellerTypeLogs get(Integer id);

    List<SellerTypeLogs> getByCateId(Integer id);

}
