package com.yixiekeji.service.impl.member;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberCollectionSeller;
import com.yixiekeji.model.member.MemberCollectionSellerModel;
import com.yixiekeji.service.member.IMemberCollectionSellerService;
import com.yixiekeji.vo.member.FrontMemberCollectionSellerVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员收藏店铺
 *
 */
@RestController
public class MemberCollectionSellerServiceImpl implements IMemberCollectionSellerService {
    private static Logger               log = LoggerFactory
        .getLogger(MemberCollectionSellerServiceImpl.class);

    @Resource
    private MemberCollectionSellerModel memberCollectionSellerModel;

    @Override
    public ServiceResult<Boolean> collectionSeller(@RequestParam("sellerId") Integer sellerId,
                                                   @RequestParam("memberId") Integer memberId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberCollectionSellerModel.collectionSeller(sellerId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error(
                "[MemberCollectionSellerService][collectionSeller]会员收藏商铺时发生异常:" + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberCollectionSellerService][collectionSeller]会员收藏商铺时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> cancelCollectionSeller(@RequestParam("sellerId") Integer sellerId,
                                                         @RequestParam("memberId") Integer memberId) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            serviceResult
                .setResult(memberCollectionSellerModel.cancelCollectionSeller(sellerId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[MemberCollectionSellerService][cancelCollectionSeller]取消收藏商铺表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberCollectionSellerService][cancelCollectionSeller]取消收藏商铺表时发生异常:", e);
        }
        return serviceResult;
    }

    /**
    * 根据id取得会员收藏商铺表
    * @param  memberCollectionSellerId
    * @return
    */
    @Override
    public ServiceResult<MemberCollectionSeller> getMemberCollectionSellerById(@RequestParam("memberCollectionSellerId") Integer memberCollectionSellerId) {
        ServiceResult<MemberCollectionSeller> result = new ServiceResult<MemberCollectionSeller>();
        try {
            result.setResult(memberCollectionSellerModel
                .getMemberCollectionSellerById(memberCollectionSellerId));
        } catch (Exception e) {
            log.error("根据id[" + memberCollectionSellerId + "]取得会员收藏商铺表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + memberCollectionSellerId + "]取得会员收藏商铺表时出现未知异常");
        }
        return result;
    }

    /**
     * 根据会员id取得会员收藏商铺表
     * @param  memberId
     * @return
     */
    @Override
    public ServiceResult<List<FrontMemberCollectionSellerVO>> getCollectionSellerByMemberid(@RequestParam("memberId") Integer memberId,
                                                                                            @RequestBody PagerInfo pager) {
        ServiceResult<List<FrontMemberCollectionSellerVO>> serviceResult = new ServiceResult<List<FrontMemberCollectionSellerVO>>();
        serviceResult.setPager(pager);

        try {
            serviceResult.setResult(
                memberCollectionSellerModel.getCollectionSellerByMemberid(memberId, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[MemberCollectionSellerService][getCollectionSellerByMemberid]获取会员收藏商铺列表时发生异常:",
                e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MemberCollectionSeller>> getBySellerIdAndMId(@RequestParam("sellerId") Integer sellerId,
                                                                           @RequestParam("memberId") Integer memberId) {
        ServiceResult<List<MemberCollectionSeller>> serviceResult = new ServiceResult<List<MemberCollectionSeller>>();
        try {
            serviceResult
                .setResult(memberCollectionSellerModel.getBySellerIdAndMId(sellerId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[MemberCollectionSellerService][getBySellerIdAndMId]获取会员收藏商铺列表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public Integer getCollectionSeller(@RequestParam("memberId") Integer memberId) {
        return memberCollectionSellerModel.getCollectionSeller(memberId);
    }

}