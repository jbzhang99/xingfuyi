package com.yixiekeji.model.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.system.SystemAdminReadDao;
import com.yixiekeji.dao.shop.write.system.SystemAdminWriteDao;
import com.yixiekeji.dao.shop.write.system.SystemRolesWriteDao;
import com.yixiekeji.entity.system.SystemAdmin;

@Component(value = "systemAdminModel")
public class SystemAdminModel {

    @Resource
    private SystemAdminReadDao           systemAdminReadDao;
    @Resource
    private SystemAdminWriteDao          systemAdminWriteDao;
    @Resource
    private SystemRolesWriteDao          systemRolesWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
    * 根据id取得系统管理员表
    * @param  systemAdminId
    * @return
    */
    public SystemAdmin getSystemAdminById(Integer systemAdminId) {
        return systemAdminWriteDao.get(systemAdminId);
    }

    /**
     * 保存系统管理员表
     * @param  systemAdmin
     * @return
     */
    public Integer saveSystemAdmin(SystemAdmin systemAdmin) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            SystemAdmin systemAdminDb = systemAdminWriteDao.checkName(systemAdmin.getName());
            if (systemAdminDb != null) {
                throw new BusinessException("用户名已存在！");
            }
            Integer row = systemAdminWriteDao.save(systemAdmin);
            if (row == 0) {
                throw new BusinessException("添加管理员信息失败，请重试！");
            }

            transactionManager.commit(status);
            return row;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    /**
    * 更新系统管理员表
    * @param  systemAdmin
    * @return
    */

    public Integer updateSystemAdmin(SystemAdmin systemAdmin) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            SystemAdmin systemAdminDb = systemAdminWriteDao.checkName(systemAdmin.getName());
            if (systemAdminDb != null) {
                if (systemAdmin.getId() != systemAdminDb.getId()) {
                    throw new BusinessException("用户名已存在！");
                }
            }

            Integer row = systemAdminWriteDao.update(systemAdmin);
            if (row == 0) {
                throw new BusinessException("修改管理员信息失败，请重试！");
            }
            transactionManager.commit(status);
            return row;
        } catch (BusinessException be) {
            transactionManager.rollback(status);
            throw be;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    public Integer pageCount(Map<String, String> queryMap) {
        return systemAdminWriteDao.getCount(queryMap);
    }

    public List<SystemAdmin> page(Map<String, String> queryMap, Integer start, Integer size) {
        List<SystemAdmin> list = systemAdminWriteDao.page(queryMap, start, size);

        for (SystemAdmin admin : list) {
            admin.setRoleName(systemRolesWriteDao.get(admin.getRoleId()).getRolesName());
        }

        return list;
    }

    public Boolean del(Integer id) {
        if (id == null)
            throw new BusinessException("删除系统管理员表[" + id + "]时出错");
        return this.systemAdminWriteDao.del(id) > 0;
    }

    public SystemAdmin getSystemAdminByNamePwd(String name, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("password", password);
        return systemAdminWriteDao.getByNamePwd(map);
    }

    /**
     * 根据用户名取用户
     * @param name
     * @return
     */
    public List<SystemAdmin> getSystemAdminByName(String name) {
        return systemAdminReadDao.getByName(name);
    }

}
