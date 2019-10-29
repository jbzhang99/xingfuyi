package com.yixiekeji.dao.shop.read.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.seller.SellerApply;

@Mapper
public interface SellerApplyReadDao {

    SellerApply get(java.lang.Integer id);

    /**
     * 以用户获取其商家申请
     * @param userid
     * @return
     */
    SellerApply getSellerApplyByUser(Integer userid);

    List<SellerApply> getSellerApplyByCondition(Map<String, Object> map);

    /**
     * 根据条件查询count
     * @param queryMap
     * @return
     */
    Integer getSellerApplysCount(Map<String, String> queryMap);
}
