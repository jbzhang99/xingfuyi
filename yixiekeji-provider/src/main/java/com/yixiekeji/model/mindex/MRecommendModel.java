package com.yixiekeji.model.mindex;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.mindex.MRecommendReadDao;
import com.yixiekeji.dao.shop.write.mindex.MRecommendWriteDao;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.mindex.MRecommend;

@Component(value = "mRecommendModel")
public class MRecommendModel {

    @Resource
    private MRecommendReadDao  mRecommendReadDao;

    @Resource
    private MRecommendWriteDao mRecommendWriteDao;

    @Resource
    private ProductReadDao     productReadDao;

    /**
     * 根据id取得M端推荐商品
     * @param  mRecommendId
     * @return
     */
    public MRecommend getMRecommendById(Integer mRecommendId) {
        MRecommend mRecommend = mRecommendReadDao.get(mRecommendId);
        Product product = productReadDao.get(mRecommend.getProductId());
        mRecommend.setProduct(product);
        return mRecommend;
    }

    /**
     * 保存M端推荐商品
     * @param  mRecommend
     * @return
     */
    public boolean saveMRecommend(MRecommend mRecommend) {
        return mRecommendWriteDao.insert(mRecommend) > 0;
    }

    /**
     * 更新M端推荐商品
     * @param mRecommend
     * @return
     */
    public boolean updateMRecommend(MRecommend mRecommend) {
        return mRecommendWriteDao.update(mRecommend) > 0;
    }

    /**
     * 删除M端推荐商品
     * @param mRecommend
     * @return
     */
    public boolean deleteMRecommend(Integer mRecommendId) {
        return mRecommendWriteDao.delete(mRecommendId) > 0;
    }

    public Integer getMRecommendsCount(Map<String, String> queryMap) {
        return mRecommendReadDao.getMRecommendsCount(queryMap);
    }

    public List<MRecommend> getMRecommends(Map<String, String> queryMap, Integer start, Integer size) {
        List<MRecommend> list = mRecommendReadDao.getMRecommends(queryMap, start, size);
        if (list != null && list.size() > 0) {
            for (MRecommend recommend : list) {
                Product product = productReadDao.get(recommend.getProductId());
                recommend.setProduct(product);
            }
        }
        return list;
    }

    /**
     * 取得M端推荐商品（当前时间在展示时间范围内的推荐商品）<br>
     * <li>如果isPreview=true取使用和预使用状态的推荐商品
     * <li>如果isPreview=false取使用状态的推荐商品
     * 
     * @param recommendType 推荐类型：固定为1：多惠部落
     * @param isPreview
     * @return
     */
    public List<MRecommend> getMRecommendForView(Integer recommendType, Boolean isPreview,
                                                 Integer start, Integer size) {
        Integer isPreviewInt = 0;
        if (isPreview != null && isPreview) {
            isPreviewInt = 1;
        }
        List<MRecommend> list = mRecommendReadDao.getMRecommendsForView(recommendType,
            isPreviewInt, start, size);
        if (list != null && list.size() > 0) {
            for (MRecommend recommend : list) {
                Product product = productReadDao.get(recommend.getProductId());
                recommend.setProduct(product);
                if (product != null) {
                    BigDecimal dis = product.getMalMobilePrice().divide(product.getMarketPrice(),
                        2, BigDecimal.ROUND_HALF_UP);
                    int disInt = 100 - (dis.multiply(new BigDecimal(100)).intValue());
                    recommend.setDiscount(disInt + "%");
                }
            }
        }
        return list;
    }

    public Integer getMRecommendForViewCount(Integer recommendType, Boolean isPreview) {
        Integer isPreviewInt = 0;
        if (isPreview != null && isPreview) {
            isPreviewInt = 1;
        }
        return mRecommendReadDao.getMRecommendsForViewCount(recommendType, isPreviewInt);
    }
}