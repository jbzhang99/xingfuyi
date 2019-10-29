package com.yixiekeji.model.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.dao.shop.write.member.MemberWriteDao;
import com.yixiekeji.dao.shop.write.member.MemberWxsignWriteDao;
import com.yixiekeji.entity.member.MemberWxsign;

@Component
public class MemberWxsignModel {

    @Resource
    private MemberWxsignWriteDao         memberWxsignWriteDao;
    @Resource
    private MemberWriteDao               memberWriteDao;
    @Resource
    private DataSourceTransactionManager transactionManager;

    /**
     * 根据id取得微信联合登录
     * @param  memberWxsignId
     * @return
     */
    public MemberWxsign getMemberWxsignById(Integer memberWxsignId) {
        return memberWxsignWriteDao.get(memberWxsignId);
    }

    /**
     * 保存微信联合登录
     * @param  memberWxsign
     * @return
     */
    public Integer saveMemberWxsign(MemberWxsign memberWxsign) {
        Integer row = 0;
        MemberWxsign member = memberWxsignWriteDao.getByOpenId(memberWxsign.getOpenid());
        if (member != null) {
            memberWxsign.setId(member.getId());
            row = memberWxsignWriteDao.update(memberWxsign);
        } else {
            row = memberWxsignWriteDao.save(memberWxsign);
        }
        return row;
    }

    /**
    * 更新微信联合登录
    * @param  memberWxsign
    * @return
    */
    public Integer updateMemberWxsign(MemberWxsign memberWxsign) {
        return memberWxsignWriteDao.update(memberWxsign);
    }

    public List<MemberWxsign> page(Map<String, String> queryMap, PagerInfo pager) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>(queryMap);
        Integer start = 0, size = 0;
        if (pager != null) {
            pager.setRowsCount(memberWxsignWriteDao.getCount(param));
            start = pager.getStart();
            size = pager.getPageSize();
        }
        param.put("start", start);
        param.put("size", size);
        List<MemberWxsign> list = memberWxsignWriteDao.page(param);
        return list;
    }

    public Integer del(Integer id) {
        return memberWxsignWriteDao.del(id);
    }

    /**
     * 以openid获取用户信息
     * @param openid 用户标识
     * @return
     */
    public MemberWxsign getWxUser(String openid) {
        return memberWxsignWriteDao.getByOpenId(openid);
    }

}