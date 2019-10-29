package com.yixiekeji.model.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.write.seller.SellerTypeLogsWriteDao;
import com.yixiekeji.entity.seller.SellerTypeLogs;

@Service(value = "sellerTypeLogsModel")
public class SellerTypeLogsModel {

    @Resource
    private SellerTypeLogsWriteDao sellerTypeLogsWriteDao;

    public Boolean saveSellerTypeLogs(SellerTypeLogs sellerTypeLogs) {
        return sellerTypeLogsWriteDao.insert(sellerTypeLogs) > 0;
    }

    public Boolean updateSellerTypeLogs(SellerTypeLogs sellerTypeLogs) {
        return sellerTypeLogsWriteDao.update(sellerTypeLogs) > 0;
    }

    public Boolean delSellerTypeLogs(Integer sellerTypeLogsId) {
        //1. business check
        if (null == sellerTypeLogsId || 0 == sellerTypeLogsId)
            throw new BusinessException("根据id删除商家类型修改日志表失败，id为空");

        //2. dbOper
        return sellerTypeLogsWriteDao.del(sellerTypeLogsId) > 0;
    }

    public SellerTypeLogs getSellerTypeLogsById(Integer sellerTypeLogsId) {
        if (null == sellerTypeLogsId || 0 == sellerTypeLogsId)
            throw new BusinessException("根据id获取商家类型修改日志表失败，id为空");

        return sellerTypeLogsWriteDao.get(sellerTypeLogsId);
    }

    public List<SellerTypeLogs> getSellerTypeLogsByCateId(Integer cateId) {
        if (null == cateId || 0 == cateId)
            throw new BusinessException("根据商品分类id获取商家类型修改日志表失败，id为空");
        return sellerTypeLogsWriteDao.getByCateId(cateId);
    }

}
