package com.yixiekeji.service.member;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.member.MemberApplyDrawing;

/**
 * 会员提款申请接口
 * 
 * @Filename: IMemberApplyDrawingService.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "memberApplyDrawing")
@Service(value = "memberApplyDrawingService")
public interface IMemberApplyDrawingService {

    /**
     * 根据id取得会员提款申请
     * @param  memberApplyDrawingId
     * @return
     */
    @RequestMapping(value = "getMemberApplyDrawing", method = RequestMethod.GET)
    ServiceResult<MemberApplyDrawing> getMemberApplyDrawing(@RequestParam("memberApplyDrawingId") Integer memberApplyDrawingId);

    /**
     * 根据条件取得会员提款申请
     * @param  queryMap
     * @param  pager
     * @return
     */
    @RequestMapping(value = "getMemberApplyDrawings", method = RequestMethod.POST)
    ServiceResult<List<MemberApplyDrawing>> getMemberApplyDrawings(FeignUtil feignUtil);

    /**
     * 保存会员提款申请
     * @param  memberApplyDrawing
     * @return
     */
    @RequestMapping(value = "saveMemberApplyDrawing", method = RequestMethod.POST)
    ServiceResult<Integer> saveMemberApplyDrawing(MemberApplyDrawing memberApplyDrawing);

    /**
    * 更新会员提款申请
    * @param  memberApplyDrawing
    * @return
    */
    @RequestMapping(value = "updateMemberApplyDrawing", method = RequestMethod.POST)
    ServiceResult<Integer> updateMemberApplyDrawing(MemberApplyDrawing memberApplyDrawing);

    /**
     * 会员提款申请批量审核
     * @param ids 申请ID
     * @param optId 操作人ID
     * @param optName 操作人名称
     * @return
     */
    @RequestMapping(value = "auditing", method = RequestMethod.POST)
    ServiceResult<Integer> auditing(@RequestBody List<Integer> ids,
                                    @RequestParam("optId") Integer optId,
                                    @RequestParam("optName") String optName);

    /**
     * 更改打款状态为已打款
     * @param id 申请ID
     * @param optId 操作人ID
     * @param optName 操作人名称
     * @return
     */
    @RequestMapping(value = "paid", method = RequestMethod.GET)
    ServiceResult<Integer> paid(@RequestParam("id") Integer id,
                                @RequestParam("optId") Integer optId,
                                @RequestParam("optName") String optName);

}