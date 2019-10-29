package com.yixiekeji.model.pcindex;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.pcindex.PcIndexImageReadDao;
import com.yixiekeji.dao.shop.write.pcindex.PcIndexImageWriteDao;
import com.yixiekeji.entity.pcindex.PcIndexImage;

@Component(value = "pcIndexImageModel")
public class PcIndexImageModel {

    @Resource
    private PcIndexImageReadDao  pcIndexImageReadDao;
    @Resource
    private PcIndexImageWriteDao pcIndexImageWriteDao;

    /**
     * 根据id取得PC端首页的一些图片
     * @param  pcIndexImageId
     * @return
     */
    public PcIndexImage getPcIndexImageById(Integer pcIndexImageId) {
        return pcIndexImageReadDao.get(pcIndexImageId);
    }

    /**
     * 保存PC端首页的一些图片
     * @param  pcIndexImage
     * @return
     */
    public boolean savePcIndexImage(PcIndexImage pcIndexImage) {
        return pcIndexImageWriteDao.insert(pcIndexImage) > 0;
    }

    /**
     * 更新PC端首页的一些图片
     * @param pcIndexImage
     * @return
     */
    public boolean updatePcIndexImage(PcIndexImage pcIndexImage) {
        return pcIndexImageWriteDao.update(pcIndexImage) > 0;
    }

    /**
     * 删除PC端首页的一些图片
     * @param pcIndexImage
     * @return
     */
    public boolean deletePcIndexImage(Integer pcIndexImageId) {
        return pcIndexImageWriteDao.delete(pcIndexImageId) > 0;
    }

    public Integer getPcIndexImagesCount(Map<String, String> queryMap) {
        return pcIndexImageReadDao.getPcIndexImagesCount(queryMap);
    }

    public List<PcIndexImage> getPcIndexImages(Map<String, String> queryMap, Integer start,
                                               Integer size) {
        return pcIndexImageReadDao.getPcIndexImages(queryMap, start, size);
    }

    /**
     * 取得PC端首页的一些图片（当前时间在展示时间范围内的轮播图）<br>
     * <li>如果isPreview=true取使用和预使用状态的轮播图
     * <li>如果isPreview=false取使用状态的轮播图
     * 
     * @param isPreview
     * @param type
     * @return
     */
    public List<PcIndexImage> getPcIndexImageForView(Boolean isPreview, int type) {
        Integer isPreviewInt = 0;
        if (isPreview != null && isPreview) {
            isPreviewInt = 1;
        }
        return pcIndexImageReadDao.getPcIndexImagesForView(isPreviewInt, type);
    }
}