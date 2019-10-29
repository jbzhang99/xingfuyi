package com.yixiekeji.service.impl.mindex;

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
import com.yixiekeji.entity.mindex.MIndexBanner;
import com.yixiekeji.entity.mindex.MIndexFloor;
import com.yixiekeji.entity.mindex.MIndexFloorData;
import com.yixiekeji.model.mindex.MIndexBannerModel;
import com.yixiekeji.model.mindex.MIndexFloorDataModel;
import com.yixiekeji.model.mindex.MIndexFloorModel;
import com.yixiekeji.service.mindex.IMIndexService;

@RestController
public class MIndexServiceImpl implements IMIndexService {
    private static Logger        log = LoggerFactory.getLogger(MIndexServiceImpl.class);

    @Resource
    private MIndexBannerModel    mIndexBannerModel;
    @Resource
    private MIndexFloorModel     mIndexFloorModel;
    @Resource
    private MIndexFloorDataModel mIndexFloorDataModel;

    @Override
    public ServiceResult<MIndexBanner> getMIndexBannerById(@RequestParam("mIndexBannerId") Integer mIndexBannerId) {
        ServiceResult<MIndexBanner> result = new ServiceResult<MIndexBanner>();
        try {
            result.setResult(mIndexBannerModel.getMIndexBannerById(mIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][getMIndexBannerById]根据id[" + mIndexBannerId
                      + "]取得移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IMIndexService][getMIndexBannerById]根据id[" + mIndexBannerId + "]取得移动端首页轮播图时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMIndexBanner(@RequestBody MIndexBanner mIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexBannerModel.saveMIndexBanner(mIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][saveMIndexBanner]保存移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][saveMIndexBanner]保存移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMIndexBanner(@RequestBody MIndexBanner mIndexBanner) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexBannerModel.updateMIndexBanner(mIndexBanner));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][updateMIndexBanner]更新移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][updateMIndexBanner]更新移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMIndexBanner(@RequestParam("mIndexBannerId") Integer mIndexBannerId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexBannerModel.deleteMIndexBanner(mIndexBannerId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][deleteMIndexBanner]删除移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][deleteMIndexBanner]删除移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MIndexBanner>> getMIndexBanners(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MIndexBanner>> serviceResult = new ServiceResult<List<MIndexBanner>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(mIndexBannerModel.getMIndexBannersCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(mIndexBannerModel.getMIndexBanners(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMIndexService][getMIndexBanners]根据条件取得移动端首页轮播图时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexBanners]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMIndexService][getMIndexBanners]根据条件取得移动端首页轮播图时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MIndexBanner>> getMIndexBannerForView(@RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<MIndexBanner>> result = new ServiceResult<List<MIndexBanner>>();
        try {
            result.setResult(mIndexBannerModel.getMIndexBannerForView(isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][getMIndexBannerForView]取得移动端首页轮播图时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexBannerForView]取得移动端首页轮播图时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<MIndexFloor> getMIndexFloorById(@RequestParam("mIndexFloorId") Integer mIndexFloorId) {
        ServiceResult<MIndexFloor> result = new ServiceResult<MIndexFloor>();
        try {
            result.setResult(mIndexFloorModel.getMIndexFloorById(mIndexFloorId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][getMIndexFloorById]根据id[" + mIndexFloorId
                      + "]取得移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error(
                "[IMIndexService][getMIndexFloorById]根据id[" + mIndexFloorId + "]取得移动端首页楼层表时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMIndexFloor(@RequestBody MIndexFloor mIndexFloor) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorModel.saveMIndexFloor(mIndexFloor));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][saveMIndexFloor]保存移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][saveMIndexFloor]保存移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMIndexFloor(@RequestBody MIndexFloor mIndexFloor) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorModel.updateMIndexFloor(mIndexFloor));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][updateMIndexFloor]更新移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][updateMIndexFloor]更新移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMIndexFloor(@RequestParam("mIndexFloorId") Integer mIndexFloorId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorModel.deleteMIndexFloor(mIndexFloorId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][deleteMIndexFloor]删除移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][deleteMIndexFloor]删除移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MIndexFloor>> getMIndexFloors(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MIndexFloor>> serviceResult = new ServiceResult<List<MIndexFloor>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(mIndexFloorModel.getMIndexFloorsCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult.setResult(mIndexFloorModel.getMIndexFloors(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMIndexService][getMIndexFloors]根据条件取得移动端首页楼层表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexFloors]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMIndexService][getMIndexFloors]根据条件取得移动端首页楼层表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MIndexFloor>> getMIndexFloorsWithData(@RequestParam("isPreview") Boolean isPreview) {
        ServiceResult<List<MIndexFloor>> result = new ServiceResult<List<MIndexFloor>>();
        try {
            result.setResult(mIndexFloorModel.getMIndexFloorsWithData(isPreview));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][getMIndexFloorForView]取得移动端首页楼层表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexFloorForView]取得移动端首页楼层表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<MIndexFloorData> getMIndexFloorDataById(@RequestParam("mIndexFloorDataId") Integer mIndexFloorDataId) {
        ServiceResult<MIndexFloorData> result = new ServiceResult<MIndexFloorData>();
        try {
            result.setResult(mIndexFloorDataModel.getMIndexFloorDataById(mIndexFloorDataId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][getMIndexFloorDataById]根据id[" + mIndexFloorDataId
                      + "]取得移动端首页楼层数据表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexFloorDataById]根据id[" + mIndexFloorDataId
                      + "]取得移动端首页楼层数据表时发生异常:",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> saveMIndexFloorData(@RequestBody MIndexFloorData mIndexFloorData) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorDataModel.saveMIndexFloorData(mIndexFloorData));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error("[IMIndexService][saveMIndexFloorData]保存移动端首页楼层数据表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][saveMIndexFloorData]保存移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> updateMIndexFloorData(@RequestBody MIndexFloorData mIndexFloorData) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorDataModel.updateMIndexFloorData(mIndexFloorData));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IMIndexService][updateMIndexFloorData]更新移动端首页楼层数据表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][updateMIndexFloorData]更新移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> deleteMIndexFloorData(@RequestParam("mIndexFloorDataId") Integer mIndexFloorDataId) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(mIndexFloorDataModel.deleteMIndexFloorData(mIndexFloorDataId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
            log.error(
                "[IMIndexService][deleteMIndexFloorData]删除移动端首页楼层数据表时发生异常:" + be.getMessage());
        } catch (Exception e) {
            result.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][deleteMIndexFloorData]删除移动端首页楼层数据表时发生异常:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<MIndexFloorData>> getMIndexFloorDatas(@RequestBody FeignUtil feignUtil) {
        Map<String, String> queryMap = feignUtil.getQueryMap();
        PagerInfo pager = feignUtil.getPager();

        ServiceResult<List<MIndexFloorData>> serviceResult = new ServiceResult<List<MIndexFloorData>>();
        serviceResult.setPager(pager);
        try {
            Integer start = 0, size = 0;
            if (pager != null) {
                pager.setRowsCount(mIndexFloorDataModel.getMIndexFloorDatasCount(queryMap));
                start = pager.getStart();
                size = pager.getPageSize();
            }
            serviceResult
                .setResult(mIndexFloorDataModel.getMIndexFloorDatas(queryMap, start, size));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMIndexService][getMIndexFloorDatas]根据条件取得首页楼层数据表时出现异常：" + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexFloorDatas]param1:" + JSON.toJSONString(queryMap)
                      + " &param2:" + JSON.toJSONString(pager));
            log.error("[IMIndexService][getMIndexFloorDatas]根据条件取得首页楼层数据表时发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<MIndexFloorData>> getMIndexFloorDatasByFloorId(@RequestParam("mIndexFloorId") Integer mIndexFloorId) {
        ServiceResult<List<MIndexFloorData>> serviceResult = new ServiceResult<List<MIndexFloorData>>();
        try {
            serviceResult
                .setResult(mIndexFloorDataModel.getMIndexFloorDatasByFloorId(mIndexFloorId));
        } catch (BusinessException e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(e.getMessage());
            log.error("[IMIndexService][getMIndexFloorDatasByFloorId]根据楼层ID取得首页楼层数据表时出现异常："
                      + e.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[IMIndexService][getMIndexFloorDatasByFloorId]根据楼层ID取得首页楼层数据表时发生异常:", e);
        }
        return serviceResult;
    }

}