package com.yixiekeji.model.seller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.seller.SellerUserReadDao;
import com.yixiekeji.dao.shop.write.seller.SellerRolesWriteDao;
import com.yixiekeji.dao.shop.write.seller.SellerUserLoginLogWriteDao;
import com.yixiekeji.dao.shop.write.seller.SellerUserWriteDao;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.seller.SellerUserLoginLog;

@Component(value = "sellerUserModel")
public class SellerUserModel {

    @Resource
    private SellerUserWriteDao         sellerUserWriteDao;
    @Resource
    private SellerUserReadDao          sellerUserReadDao;
    @Resource
    private SellerRolesWriteDao        sellerRolesWriteDao;
    @Resource
    private SellerUserLoginLogWriteDao sellerUserLoginLogWriteDao;

    /**
    * 根据id取得系统管理员表
    * @param  sellerUserId
    * @return
    */
    public SellerUser getSellerUserById(Integer sellerUserId) {
        return sellerUserWriteDao.get(sellerUserId);
    }

    /**
     * 保存系统管理员表
     * @param  sellerUser
     * @return
     */
    public Integer saveSellerUser(SellerUser sellerUser) {
        return sellerUserWriteDao.insert(sellerUser);
    }

    /**
    * 更新系统管理员表
    * @param  sellerUser
    * @return
    */
    public Integer updateSellerUser(SellerUser sellerUser) {
        return sellerUserWriteDao.update(sellerUser);
    }

    public Integer pageCount(Map<String, String> queryMap) {
        return sellerUserReadDao.getCount(queryMap);
    }

    public List<SellerUser> page(Map<String, String> queryMap, Integer start, Integer size) {
        List<SellerUser> list = sellerUserReadDao.page(queryMap, start, size);

        for (SellerUser admin : list) {
            admin.setRoleName(sellerRolesWriteDao.get(admin.getRoleId()).getRolesName());
        }

        return list;
    }

    public Boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除系统管理员表[" + id + "]时出错");
        return this.sellerUserWriteDao.del(id) > 0;
    }

    public SellerUser getSellerUserByNamePwd(String name, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", password);
        return sellerUserReadDao.getByNamePwd(map);
    }

    public List<SellerUser> getSellerUserByName(String name) {
        return sellerUserReadDao.getByName(name);
    }

    /**
     * 保存商家管理员登录日志
     * @param sellerUserLoginLog
     * @return
     */
    public boolean saveSellerUserLoginLog(SellerUserLoginLog sellerUserLoginLog) {
        return sellerUserLoginLogWriteDao.insert(sellerUserLoginLog) > 0;
    }
}
