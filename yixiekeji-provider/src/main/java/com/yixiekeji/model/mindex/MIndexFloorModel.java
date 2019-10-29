package com.yixiekeji.model.mindex;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.dao.shop.read.mindex.MIndexFloorDataReadDao;
import com.yixiekeji.dao.shop.read.mindex.MIndexFloorReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.write.mindex.MIndexFloorDataWriteDao;
import com.yixiekeji.dao.shop.write.mindex.MIndexFloorWriteDao;
import com.yixiekeji.entity.mindex.MIndexFloor;
import com.yixiekeji.entity.mindex.MIndexFloorData;
import com.yixiekeji.entity.product.Product;

@Component(value = "mIndexFloorModel")
public class MIndexFloorModel {

    @Resource
    private MIndexFloorReadDao           mIndexFloorReadDao;
    @Resource
    private MIndexFloorWriteDao          mIndexFloorWriteDao;
    @Resource
    private MIndexFloorDataReadDao       mIndexFloorDataReadDao;
    @Resource
    private MIndexFloorDataWriteDao      mIndexFloorDataWriteDao;
    @Resource
    private ProductReadDao               productReadDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得移动端首页楼层表
     * @param  mIndexFloorId
     * @return
     */
    public MIndexFloor getMIndexFloorById(Integer mIndexFloorId) {
        return mIndexFloorReadDao.get(mIndexFloorId);
    }

    /**
     * 保存移动端首页楼层表
     * @param  mIndexFloor
     * @return
     */
    public boolean saveMIndexFloor(MIndexFloor mIndexFloor) {
        return mIndexFloorWriteDao.insert(mIndexFloor) > 0;
    }

    /**
     * 更新移动端首页楼层表
     * @param mIndexFloor
     * @return
     */
    public boolean updateMIndexFloor(MIndexFloor mIndexFloor) {
        return mIndexFloorWriteDao.update(mIndexFloor) > 0;
    }

    /**
     * 删除移动端首页楼层表
     * @param mIndexFloor
     * @return
     */
    public boolean deleteMIndexFloor(Integer mIndexFloorId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 删除楼层
            Integer delete = mIndexFloorWriteDao.delete(mIndexFloorId);
            // 删除楼层数据
            mIndexFloorDataWriteDao.deleteByFloorId(mIndexFloorId);
            transactionManager.commit(status);
            return delete > 0;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    public Integer getMIndexFloorsCount(Map<String, String> queryMap) {
        return mIndexFloorReadDao.getMIndexFloorsCount(queryMap);
    }

    public List<MIndexFloor> getMIndexFloors(Map<String, String> queryMap, Integer start,
                                             Integer size) {
        return mIndexFloorReadDao.getMIndexFloors(queryMap, start, size);
    }

    /**
     * 取得移动端首页楼层表<br>
     * <li>如果isPreview=true取所有楼层
     * <li>如果isPreview=false只取显示状态的楼层
     * <li>封装楼层对应的数据（MIndexFloorData）
     * 
     * @param isPreview
     * @return
     */
    public List<MIndexFloor> getMIndexFloorsWithData(Boolean isPreview) {
        String status = "";
        if (!isPreview) {
            status = MIndexFloor.STATUS_1 + "";
        }
        List<MIndexFloor> list = mIndexFloorReadDao.getMIndexFloorsForView(status);

        for (MIndexFloor floor : list) {
            List<MIndexFloorData> dataList = mIndexFloorDataReadDao
                .getMIndexFloorDatasByFloorId(floor.getId());
            if (dataList != null && dataList.size() > 0) {
                for (MIndexFloorData data : dataList) {
                    if (data.getDataType() != null
                        && data.getDataType().equals(MIndexFloorData.DATA_TYPE_1)) {
                        Product product = productReadDao.get(data.getRefId());
                        product.setDescription("");
                        data.setProduct(product);
                    }
                }
            }
            floor.setDatas(dataList);
        }

        return list;
    }
}