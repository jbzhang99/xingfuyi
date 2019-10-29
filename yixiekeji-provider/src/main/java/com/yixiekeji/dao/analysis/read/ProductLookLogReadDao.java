package com.yixiekeji.dao.analysis.read;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.analysis.ProductLookLog;

@Mapper
public interface ProductLookLogReadDao {

    ProductLookLog get(java.lang.Integer id);

    /**
     * 根据条件获取用户商品浏览信息
     * @param queryMap
     * @param start
     * @param size
     * @return
     */
    List<ProductLookLog> getProductLookLogs(@Param("queryMap") Map<String, String> queryMap,
                                            @Param("start") Integer start,
                                            @Param("size") Integer size);

    /**
     * 根据条件获取用户商品浏览数量
     * @param queryMap
     * @return
     */
    Integer getProductLookLogsCount(@Param("queryMap") Map<String, String> queryMap);
}