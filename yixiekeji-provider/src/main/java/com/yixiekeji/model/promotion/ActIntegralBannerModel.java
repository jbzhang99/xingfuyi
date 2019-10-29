package com.yixiekeji.model.promotion;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.integral.ActIntegralBannerReadDao;
import com.yixiekeji.dao.shop.write.integral.ActIntegralBannerWriteDao;
import com.yixiekeji.entity.integral.ActIntegralBanner;

@Component(value = "actIntegralBannerModel")
public class ActIntegralBannerModel {

    @Resource
    private ActIntegralBannerReadDao  actIntegralBannerReadDao;
    @Resource
    private ActIntegralBannerWriteDao actIntegralBannerWriteDao;

    /**
     * 根据id和渠道取得积分商城首页轮播图
     * @param  ActIntegralBannerId
     * @return
     */
    public ActIntegralBanner getActIntegralBannerById(Integer actIntegralBannerId) {
        return actIntegralBannerReadDao.get(actIntegralBannerId);
    }

    /**
     * 保存积分商城首页轮播图
     * @param  actIntegralBanner
     * @return
     */
    public boolean saveActIntegralBanner(ActIntegralBanner actIntegralBanner) {
        return actIntegralBannerWriteDao.insert(actIntegralBanner) > 0;
    }

    /**
     * 更新积分商城首页轮播图
     * @param actIntegralBanner
     * @return
     */
    public boolean updateActIntegralBanner(ActIntegralBanner actIntegralBanner) {
        return actIntegralBannerWriteDao.update(actIntegralBanner) > 0;
    }

    /**
     * 删除积分商城首页轮播图
     * @param actIntegralBanner
     * @return
     */
    public boolean deleteActIntegralBanner(Integer actIntegralBannerId) {
        return actIntegralBannerWriteDao.delete(actIntegralBannerId) > 0;
    }

    public Integer getActIntegralBannersCount(Map<String, String> queryMap) {
        return actIntegralBannerReadDao.getActIntegralBannersCount(queryMap);
    }

    public List<ActIntegralBanner> getActIntegralBanners(Map<String, String> queryMap,
                                                         Integer start, Integer size) {
        return actIntegralBannerReadDao.getActIntegralBanners(queryMap, start, size);
    }

    /**
     * 查询积分商城首页轮播图
     * @param pcMobile
     * @return
     */
    public List<ActIntegralBanner> getActIntegralBannersPcMobile(int pcMobile) {
        return actIntegralBannerReadDao.getActIntegralBannersPcMobile(pcMobile);
    }

}