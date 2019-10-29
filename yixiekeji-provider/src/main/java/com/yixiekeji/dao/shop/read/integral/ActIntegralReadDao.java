package com.yixiekeji.dao.shop.read.integral;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.yixiekeji.entity.integral.ActIntegral;

@Mapper
public interface ActIntegralReadDao {

    ActIntegral get(java.lang.Integer id);

    int count(@Param("param1") Map<String, String> queryMap);

    List<ActIntegral> getActIntegrals(@Param("param1") Map<String, String> queryMap,
                                      @Param("start") Integer start, @Param("size") Integer size);

    /**
     * 统计积分商城前台查看
     * @param type
     * @param channel
     * @param grade
     * @return
     */
    int countFront(@Param("type") int type, @Param("channel") int channel, @Param("grade") int grade);

    /**
     * 积分商城前台查看列表页
     * @param type
     * @param channel
     * @param start
     * @param size
     * @param grade
     * @param sort 0：默认；1、最新；2、销量；3、价格从低到高；4、价格从高到低
     * @return
     */
    List<ActIntegral> getActIntegralsFront(@Param("type") int type, @Param("channel") int channel,
                                           @Param("start") int start, @Param("size") int size,
                                           @Param("grade") int grade, @Param("sort") int sort);

    /**
     * 根据类型查询topNum条积分商城
     * @param type
     * @param topNum
     * @return
     */
    List<ActIntegral> getActIntegralsByType(@Param("type") Integer type,
                                            @Param("topNum") Integer topNum);
}