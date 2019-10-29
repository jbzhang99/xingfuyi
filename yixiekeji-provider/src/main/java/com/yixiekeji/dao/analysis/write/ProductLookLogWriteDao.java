package com.yixiekeji.dao.analysis.write;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.analysis.ProductLookLog;

@Mapper
public interface ProductLookLogWriteDao {

    ProductLookLog get(java.lang.Integer id);

    Integer insert(ProductLookLog productLookLog);

    Integer update(ProductLookLog productLookLog);

    /**
     * 统计当天有没有记录商品ID
     * @param siteCookie
     * @param productId
     * @return
     */
    int countBySiteCookieAndProductId(@Param("siteCookie") String siteCookie,
                                      @Param("productId") Integer productId);

    /**
     * 根据cookie统计用户还没有登录的情况下所录入记录
     * @param siteCookie
     * @return
     */
    int countBySiteCookie(String siteCookie);

    /**
     * 根据cookie更新memberID
     * @param siteCookie
     * @param memberId
     */
    void updateByMemberId(@Param("siteCookie") String siteCookie,
                          @Param("memberId") Integer memberId);
}