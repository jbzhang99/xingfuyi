package com.yixiekeji.dao.shop.write.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerComplaint;

@Mapper
public interface SellerComplaintWriteDao {

    SellerComplaint get(java.lang.Integer id);

    Integer save(SellerComplaint sellerComplaint);

    Integer update(SellerComplaint sellerComplaint);

    Integer queryCount(Map<String, Object> map);

    List<SellerComplaint> queryList(Map<String, Object> map);

    Integer del(Integer id);
}
