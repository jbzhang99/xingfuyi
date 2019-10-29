package com.yixiekeji.model.operate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.dao.shop.read.system.SystemAdminReadDao;
import com.yixiekeji.dao.shop.write.operate.NoticeClickSituationWriteDao;
import com.yixiekeji.dao.shop.write.operate.SystemNoticeWriteDao;
import com.yixiekeji.entity.operate.NoticeClickSituation;
import com.yixiekeji.entity.operate.SystemNotice;
import com.yixiekeji.entity.system.SystemAdmin;

@Component
public class SystemNoticeModel {

    @Resource
    private SystemNoticeWriteDao         systemNoticeWriteDao;
    @Resource
    private SystemAdminReadDao           systemAdminReadDao;
    @Resource
    private NoticeClickSituationWriteDao noticeClickSituationWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得商城公告
     * @param  systemNoticeId
     * @return
     */
    public SystemNotice getSystemNoticeById(Integer systemNoticeId) {
        return systemNoticeWriteDao.get(systemNoticeId);
    }

    /**
     * 保存商城公告
     * @param  systemNotice
     * @return
     */
    public Integer saveSystemNotice(SystemNotice systemNotice) {
        this.dbConstrains(systemNotice);
        return systemNoticeWriteDao.save(systemNotice);
    }

    /**
    * 更新商城公告
    * @param  systemNotice
    * @return
    */
    public Integer updateSystemNotice(SystemNotice systemNotice) {
        this.dbConstrains(systemNotice);
        return systemNoticeWriteDao.update(systemNotice);
    }

    private void dbConstrains(SystemNotice systemNotice) {
        systemNotice.setTitle(StringUtil.dbSafeString(systemNotice.getTitle(), false, 32));
        systemNotice.setDescribe(StringUtil.dbSafeString(systemNotice.getDescribe(), false, 64));
        systemNotice.setContent(StringUtil.dbSafeString(systemNotice.getContent(), false, 65535));
    }

    public List<SystemNotice> page(Map<String, String> queryMap, PagerInfo pager) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>(queryMap);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(systemNoticeWriteDao.getCount(param));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        param.put("start", start);
        param.put("size", size);
        List<SystemNotice> list = systemNoticeWriteDao.page(param);
        for (SystemNotice notice : list) {
            SystemAdmin admin = systemAdminReadDao.get(notice.getCreateUserId());
            if (admin != null)
                notice.setCreateUserName(admin.getName());
        }
        return list;
    }

    public Integer del(Integer id) {
        return systemNoticeWriteDao.del(id);
    }

    public SystemNotice toDetail(Integer id, Integer sellerId) {
        //公告详情
        SystemNotice notice = systemNoticeWriteDao.get(id);

        //已查看
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("sellerId", sellerId);
        queryMap.put("noticeId", notice.getId());
        queryMap.put("state", NoticeClickSituation.STATE_READ);
        List<NoticeClickSituation> nslist = noticeClickSituationWriteDao.page(queryMap);
        //如果没有已读记录，插入一条
        if (nslist == null || nslist.size() < 1) {
            NoticeClickSituation ns = new NoticeClickSituation();
            ns.setSellerId(sellerId);
            ns.setNoticeId(notice.getId());
            ns.setState(NoticeClickSituation.STATE_READ);
            ns.setViewTime(new Date());
            noticeClickSituationWriteDao.save(ns);
        }
        return notice;
    }

    /**
     * 商家未读的公告
     * @param sellerId
     * @return
     */
    public List<SystemNotice> getUnreadNotice(Integer sellerId, Integer start, Integer size) {
        Map<String, Object> param = new HashMap<>();
        param.put("sellerId", sellerId);
        param.put("start", start);
        param.put("size", size);
        return systemNoticeWriteDao.getUnreadNotice(param);
    }

    public int getUnreadNoticeCount(Integer sellerId) {
        return systemNoticeWriteDao.getUnreadNoticeCount(sellerId);
    }

}