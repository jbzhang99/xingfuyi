package com.yixiekeji.dao.shop.write.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yixiekeji.entity.member.MobileVerifyCode;

@Mapper
public interface MobileVerifyCodeWriteDao {

    MobileVerifyCode get(java.lang.Integer id);

    Integer insert(MobileVerifyCode mobileVerifyCode);

    Integer update(MobileVerifyCode mobileVerifyCode);

    List<MobileVerifyCode> getByUid(@Param("uid") String uid);

    Integer deleteByUid(@Param("uid") String uid);

    Integer jobClearSmsCode(@Param("clearTime") String clearTime);
}