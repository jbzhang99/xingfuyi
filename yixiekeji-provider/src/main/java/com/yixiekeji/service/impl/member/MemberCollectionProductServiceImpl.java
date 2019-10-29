package com.yixiekeji.service.impl.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.member.MemberCollectionProduct;
import com.yixiekeji.model.member.MemberCollectionProductModel;
import com.yixiekeji.service.member.IMemberCollectionProductService;
import com.yixiekeji.vo.member.FrontMemberCollectionProductVO;

/**
 *                       
 *
 */
@RestController
public class MemberCollectionProductServiceImpl implements IMemberCollectionProductService {
    private static Logger                log = LoggerFactory
        .getLogger(MemberCollectionProductServiceImpl.class);

    @Resource
    private MemberCollectionProductModel memberCollectionProductModel;

    /**
    * 根据id取得会员收藏商品表
    * @param  memberCollectionProductId
    * @return
    */
    @Override
    public ServiceResult<MemberCollectionProduct> getMemberCollectionProductById(@RequestParam("memberCollectionProductId") Integer memberCollectionProductId) {
        ServiceResult<MemberCollectionProduct> result = new ServiceResult<MemberCollectionProduct>();
        try {
            result.setResult(memberCollectionProductModel
                .getMemberCollectionProductById(memberCollectionProductId));
        } catch (Exception e) {
            log.error("根据id[" + memberCollectionProductId + "]取得会员收藏商品表时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + memberCollectionProductId + "]取得会员收藏商品表时出现未知异常");
        }
        return result;
    }

    /**
     * 根据会员id取得会员收藏商品表
     * @param request
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<List<FrontMemberCollectionProductVO>> getCollectionProductByMemberId(@RequestBody FeignUtil feignUtil,
                                                                                              @RequestParam("memberId") Integer memberId) {
        Map<String, Object> queryMap = feignUtil.getQueryMapObject();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<FrontMemberCollectionProductVO>> serviceResult = new ServiceResult<List<FrontMemberCollectionProductVO>>();
        serviceResult.setPager(pager);
        try {
            serviceResult.setResult(memberCollectionProductModel
                .getCollectionProductByMemberId(queryMap, memberId, pager));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[memberCollectionProductService][getCollectionProductByMemberId]获取会员收藏商品列表时发生异常:",
                e);
        }
        return serviceResult;
    }

    /**
     * 保存会员收藏商品表
     * @param productId
     * @param request
     * @return
     */
    @Override
    public ServiceResult<MemberCollectionProduct> saveMemberCollectionProduct(@RequestParam("productId") Integer productId,
                                                                              @RequestParam("memberId") Integer memberId) {
        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();
        try {
            serviceResult.setResult(
                memberCollectionProductModel.saveMemberCollectionProduct(productId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberCollectionProductService][saveMemberCollectionProduct]会员收藏商品时发生异常:",
                e);
        }
        return serviceResult;
    }

    /**
     * 取消收藏商品
     * @param productId
     * @param request
     * @return
     */
    @Override
    public ServiceResult<MemberCollectionProduct> cancelCollectionProduct(@RequestParam("productId") Integer productId,
                                                                          @RequestParam("memberId") Integer memberId) {
        ServiceResult<MemberCollectionProduct> serviceResult = new ServiceResult<MemberCollectionProduct>();
        try {
            serviceResult.setResult(
                memberCollectionProductModel.cancelCollectionProduct(productId, memberId));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[memberCollectionProductService][cancelCollectionProduct]取消收藏商品时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public Integer getCollectionProductCount(@RequestParam("memberId") Integer memberId) {
        return memberCollectionProductModel.getCollectionProductCount(memberId);
    }
}