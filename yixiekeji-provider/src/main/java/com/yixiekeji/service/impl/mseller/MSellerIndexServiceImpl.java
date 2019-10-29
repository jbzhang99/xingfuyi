package com.yixiekeji.service.impl.mseller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.mseller.MSellerIndexBanner;
import com.yixiekeji.entity.mseller.MSellerIndexFloor;
import com.yixiekeji.entity.mseller.MSellerIndexFloorData;
import com.yixiekeji.model.mseller.MSellerIndexBannerModel;
import com.yixiekeji.model.mseller.MSellerIndexFloorDataModel;
import com.yixiekeji.model.mseller.MSellerIndexFloorModel;
import com.yixiekeji.service.mseller.IMSellerIndexService;

@RestController
public class MSellerIndexServiceImpl implements IMSellerIndexService {
    private static Logger              log = LoggerFactory.getLogger(MSellerIndexServiceImpl.class);

    @Resource
    private MSellerIndexBannerModel    mSellerIndexBannerModel;
    @Resource
    private MSellerIndexFloorModel     mSellerIndexFloorModel;
    @Resource
    private MSellerIndexFloorDataModel mSellerIndexFloorDataModel;

    @Override
    public ServiceResult<MSellerIndexBanner> getMSellerIndexBannerById(@RequestParam("mSellerIndexBannerId") Integer mSellerIndexBannerId) {
        ServiceResult<MSellerIndexBanner> result = new ServiceResult<MSellerIndexBanner>();
        try {
            result
                .setResult(mSellerIndexBannerModel.getMSellerIndexBannerById(mSellerIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexBannerById]根据id["
                      + mSellerIndexBannerId + "]取得移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexBannerById]根据id["
                      + mSellerIndexBannerId + "]取得移动端首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMSellerIndexBanner(@RequestBody MSellerIndexBanner mSellerIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mSellerIndexBannerModel.saveMSellerIndexBanner(mSellerIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IMSellerIndexService][saveMSellerIndexBanner]保存移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][saveMSellerIndexBanner]保存移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMSellerIndexBanner(@RequestBody MSellerIndexBanner mSellerIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mSellerIndexBannerModel.updateMSellerIndexBanner(mSellerIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][updateMSellerIndexBanner]更新移动端首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][updateMSellerIndexBanner]更新移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMSellerIndexBanner(@RequestParam("mSellerIndexBannerId") Integer mSellerIndexBannerId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result
                .setResult(mSellerIndexBannerModel.deleteMSellerIndexBanner(mSellerIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][deleteMSellerIndexBanner]删除移动端首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][deleteMSellerIndexBanner]删除移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MSellerIndexBanner>> getMSellerIndexBanners(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MSellerIndexBanner>> serviceResult = new ServiceResult<List<MSellerIndexBanner>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(mSellerIndexBannerModel.getMSellerIndexBannersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(mSellerIndexBannerModel.getMSellerIndexBanners(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexBanners]根据条件取得移动端首页轮播图时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexBanners]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMSellerIndexService][getMSellerIndexBanners]根据条件取得移动端首页轮播图时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MSellerIndexBanner>> getMSellerIndexBannerForView(@RequestParam("sellerId") Integer sellerId,
                                                                                @RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<MSellerIndexBanner>> result = new ServiceResult<List<MSellerIndexBanner>>();
        try {
            result.setResult(
                mSellerIndexBannerModel.getMSellerIndexBannerForView(sellerId, isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexBannerForView]取得移动端首页轮播图时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexBannerForView]取得移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<MSellerIndexFloor> getMSellerIndexFloorById(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId) {
        ServiceResult<MSellerIndexFloor> result = new ServiceResult<MSellerIndexFloor>();
        try {
            result.setResult(mSellerIndexFloorModel.getMSellerIndexFloorById(mSellerIndexFloorId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexFloorById]根据id[" + mSellerIndexFloorId
                      + "]取得移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexFloorById]根据id[" + mSellerIndexFloorId
                      + "]取得移动端首页楼层表时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMSellerIndexFloor(@RequestBody MSellerIndexFloor mSellerIndexFloor) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mSellerIndexFloorModel.saveMSellerIndexFloor(mSellerIndexFloor));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IMSellerIndexService][saveMSellerIndexFloor]保存移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][saveMSellerIndexFloor]保存移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMSellerIndexFloor(@RequestBody MSellerIndexFloor mSellerIndexFloor) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mSellerIndexFloorModel.updateMSellerIndexFloor(mSellerIndexFloor));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][updateMSellerIndexFloor]更新移动端首页楼层表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][updateMSellerIndexFloor]更新移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMSellerIndexFloor(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mSellerIndexFloorModel.deleteMSellerIndexFloor(mSellerIndexFloorId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][deleteMSellerIndexFloor]删除移动端首页楼层表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][deleteMSellerIndexFloor]删除移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MSellerIndexFloor>> getMSellerIndexFloors(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MSellerIndexFloor>> serviceResult = new ServiceResult<List<MSellerIndexFloor>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(mSellerIndexFloorModel.getMSellerIndexFloorsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(mSellerIndexFloorModel.getMSellerIndexFloors(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexFloors]根据条件取得移动端首页楼层表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexFloors]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMSellerIndexService][getMSellerIndexFloors]根据条件取得移动端首页楼层表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MSellerIndexFloor>> getMSellerIndexFloorsWithData(@RequestParam("sellerId") Integer sellerId,
                                                                                @RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<MSellerIndexFloor>> result = new ServiceResult<List<MSellerIndexFloor>>();
        try {
            result.setResult(
                mSellerIndexFloorModel.getMSellerIndexFloorsWithData(sellerId, isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexFloorForView]取得移动端首页楼层表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexFloorForView]取得移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<MSellerIndexFloorData> getMSellerIndexFloorDataById(@RequestParam("mSellerIndexFloorDataId") Integer mSellerIndexFloorDataId) {
        ServiceResult<MSellerIndexFloorData> result = new ServiceResult<MSellerIndexFloorData>();
        try {
            result.setResult(
                mSellerIndexFloorDataModel.getMSellerIndexFloorDataById(mSellerIndexFloorDataId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexFloorDataById]根据id["
                      + mSellerIndexFloorDataId + "]取得移动端首页楼层数据表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexFloorDataById]根据id["
                      + mSellerIndexFloorDataId + "]取得移动端首页楼层数据表时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMSellerIndexFloorData(@RequestBody MSellerIndexFloorData mSellerIndexFloorData) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                mSellerIndexFloorDataModel.saveMSellerIndexFloorData(mSellerIndexFloorData));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][saveMSellerIndexFloorData]保存移动端首页楼层数据表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][saveMSellerIndexFloorData]保存移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMSellerIndexFloorData(@RequestBody MSellerIndexFloorData mSellerIndexFloorData) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                mSellerIndexFloorDataModel.updateMSellerIndexFloorData(mSellerIndexFloorData));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][updateMSellerIndexFloorData]更新移动端首页楼层数据表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][updateMSellerIndexFloorData]更新移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMSellerIndexFloorData(@RequestParam("mSellerIndexFloorDataId") Integer mSellerIndexFloorDataId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(
                mSellerIndexFloorDataModel.deleteMSellerIndexFloorData(mSellerIndexFloorDataId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMSellerIndexService][deleteMSellerIndexFloorData]删除移动端首页楼层数据表时发生异常:"
                      + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][deleteMSellerIndexFloorData]删除移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MSellerIndexFloorData>> getMSellerIndexFloorDatas(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();
        ServiceResult<List<MSellerIndexFloorData>> serviceResult = new ServiceResult<List<MSellerIndexFloorData>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(
                    mSellerIndexFloorDataModel.getMSellerIndexFloorDatasCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(
                mSellerIndexFloorDataModel.getMSellerIndexFloorDatas(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMSellerIndexService][getMSellerIndexFloorDatas]根据条件取得首页楼层数据表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMSellerIndexService][getMSellerIndexFloorDatas]param1:"
                      + JSON.toJSONString(queryMap) + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMSellerIndexService][getMSellerIndexFloorDatas]根据条件取得首页楼层数据表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MSellerIndexFloorData>> getMSellerIndexFloorDatasByFloorId(@RequestParam("mSellerIndexFloorId") Integer mSellerIndexFloorId) {
        ServiceResult<List<MSellerIndexFloorData>> serviceResult = new ServiceResult<List<MSellerIndexFloorData>>();
        try {
            serviceResult.setResult(
                mSellerIndexFloorDataModel.getMSellerIndexFloorDatasByFloorId(mSellerIndexFloorId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error(
                "[IMSellerIndexService][getMSellerIndexFloorDatasByFloorId]根据楼层ID取得首页楼层数据表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IMSellerIndexService][getMSellerIndexFloorDatasByFloorId]根据楼层ID取得首页楼层数据表时发生异常:",
                e);
        }
        return serviceResult;
    }

}