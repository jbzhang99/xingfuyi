package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerComplaint;

/**
 * 投诉申请及审核
 *                       
 * @Filename: SellerComplaintReadDao.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Mapper
public interface SellerComplaintReadDao {

    SellerComplaint get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<SellerComplaint> queryList(Map<String, Object> map);

}
