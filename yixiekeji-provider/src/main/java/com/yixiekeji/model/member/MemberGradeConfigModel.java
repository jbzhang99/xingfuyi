package com.yixiekeji.model.member;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.dao.shop.write.member.MemberGradeConfigWriteDao;
import com.yixiekeji.entity.member.MemberGradeConfig;

@Component(value = "memberGradeConfigModel")
public class MemberGradeConfigModel {

    @Resource
    private MemberGradeConfigWriteDao memberGradeConfigWriteDao;

    /**
    * 根据id取得会员等级配置表
    * @param  memberGradeConfigId
    * @return
    */
    public MemberGradeConfig getMemberGradeConfig(Integer memberGradeConfigId) {
        return memberGradeConfigWriteDao.get(memberGradeConfigId);
    }

    //    /**
    //     * 保存会员等级配置表
    //     * @param  memberGradeConfig
    //     * @return
    //     */
    //    
    //    public Integer saveMemberGradeConfig(MemberGradeConfig memberGradeConfig) {
    //        Integer result = new Integer();
    //        try {
    //            result.setResult(memberGradeConfigWriteDao.save(memberGradeConfig));
    //        } catch (Exception e) {
    //            log.error("保存会员等级配置表时出现未知异常：" + e);
    //            result.setSuccess(false);
    //            result.setMessage("保存会员等级配置表时出现未知异常");
    //        }
    //        return result;
    //    }

    /**
    * 更新会员等级配置表
    * @param  memberGradeConfig
    * @return
    */
    public Integer updateMemberGradeConfig(MemberGradeConfig memberGradeConfig) {
        return memberGradeConfigWriteDao.update(memberGradeConfig);
    }
}
