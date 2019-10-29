package com.yixiekeji.dao.shop.read.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.order.Invoice;

@Mapper
public interface InvoiceReadDao {

    Invoice get(java.lang.Integer id);

    Integer queryCount(Map<String, Object> map);

    List<Invoice> queryList(Map<String, Object> map);

}
