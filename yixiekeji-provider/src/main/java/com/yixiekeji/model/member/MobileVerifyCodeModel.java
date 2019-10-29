package com.yixiekeji.model.member;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.dao.shop.write.member.MobileVerifyCodeWriteDao;
import com.yixiekeji.entity.member.MobileVerifyCode;

@Component
public class MobileVerifyCodeModel {

    protected static Logger          log = LoggerFactory.getLogger(MobileVerifyCodeModel.class);

    @Resource
    private MobileVerifyCodeWriteDao mobileVerifyCodeWriteDao;

    /**
     * 根据id取得小程序验证码表
     * @param  mobileVerifyCodeId
     * @return
     */
    public MobileVerifyCode getMobileVerifyCodeById(Integer mobileVerifyCodeId) {
        return mobileVerifyCodeWriteDao.get(mobileVerifyCodeId);
    }

    /**
     * 根据uid取得小程序验证码表
     * @param  uid
     * @return
     */
    public List<MobileVerifyCode> getMobileVerifyCodeByUid(String uid) {
        return mobileVerifyCodeWriteDao.getByUid(uid);
    }

    /**
     * 根据uid删除小程序验证码表记录
     * @param  uid
     * @return
     */
    public Integer delByUid(String uid) {
        return mobileVerifyCodeWriteDao.deleteByUid(uid);
    }

    /**
     * 保存小程序验证码表
     * @param  mobileVerifyCode
     * @return
     */
    public Integer saveMobileVerifyCode(MobileVerifyCode mobileVerifyCode) {
        this.dbConstrains(mobileVerifyCode);
        return mobileVerifyCodeWriteDao.insert(mobileVerifyCode);
    }

    /**
    * 更新小程序验证码表
    * @param  mobileVerifyCode
    * @return
    */
    public Integer updateMobileVerifyCode(MobileVerifyCode mobileVerifyCode) {
        this.dbConstrains(mobileVerifyCode);
        return mobileVerifyCodeWriteDao.update(mobileVerifyCode);
    }

    /**
     * 定时任务清除1天之前添加的短信数据
     * @return
     */
    public boolean jobClearSmsCode() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String clearTime = TimeUtil.getDateTimeString(calendar.getTime());
        mobileVerifyCodeWriteDao.jobClearSmsCode(clearTime);
        return true;
    }

    private void dbConstrains(MobileVerifyCode mobileVerifyCode) {
        mobileVerifyCode.setUid(StringUtil.dbSafeString(mobileVerifyCode.getUid(), false, 50));
        mobileVerifyCode.setCode(StringUtil.dbSafeString(mobileVerifyCode.getCode(), false, 50));
    }
}