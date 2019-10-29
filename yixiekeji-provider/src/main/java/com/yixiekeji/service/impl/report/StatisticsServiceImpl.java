package com.yixiekeji.service.impl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dto.PhurchaseRateDto;
import com.yixiekeji.dto.ProductSaleDto;
import com.yixiekeji.echarts.component.SeriesData;
import com.yixiekeji.model.report.StatisticsModel;
import com.yixiekeji.service.report.IStatisticsService;

@RestController
public class StatisticsServiceImpl implements IStatisticsService {
    private static Logger   log = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    @Resource
    private StatisticsModel statisticsModel;

    @Override
    public ServiceResult<Map<String, List<Object>>> getSaleStatistics(@RequestBody Map<String, String> querymap) {
        ServiceResult<Map<String, List<Object>>> serviceResult = new ServiceResult<Map<String, List<Object>>>();
        try {
            serviceResult.setResult(statisticsModel.getSaleStatistics(querymap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getSaleStatistics]销售统计数据发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getSaleStatistics]销售统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<SeriesData>> getOrderOverviewData(@RequestBody Map<String, String> map) {
        ServiceResult<List<SeriesData>> serviceResult = new ServiceResult<List<SeriesData>>();
        try {
            serviceResult.setResult(statisticsModel.getOrderOverviewData(map));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getOrderOverviewData]订单概况统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getOrderOverviewData]订单概况统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, List<Object>>> goodsReturnRate(@RequestBody Map<String, String> map) {
        ServiceResult<Map<String, List<Object>>> serviceResult = new ServiceResult<Map<String, List<Object>>>();
        try {
            serviceResult.setResult(statisticsModel.goodsReturnRate(map));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][goodsReturnRate]退货率统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][goodsReturnRate]退货率统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Map<String, List<Object>>> getCPIstatistics(@RequestBody Map<String, String> map) {
        ServiceResult<Map<String, List<Object>>> serviceResult = new ServiceResult<Map<String, List<Object>>>();
        try {
            serviceResult.setResult(statisticsModel.getCPIstatistics(map));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getCPIstatistics]人均消费统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getCPIstatistics]人均消费统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ProductSaleDto>> getProductSale(@RequestBody Map<String, String> queryMap) {
        ServiceResult<List<ProductSaleDto>> serviceResult = new ServiceResult<List<ProductSaleDto>>();
        try {
            serviceResult.setResult(statisticsModel.getProductSale(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getCPIstatistics]商品销量统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getCPIstatistics]商品销量统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<PhurchaseRateDto> getPhurchaseRate(@RequestBody Map<String, String> queryMap) {
        ServiceResult<PhurchaseRateDto> serviceResult = new ServiceResult<PhurchaseRateDto>();
        try {
            serviceResult.setResult(statisticsModel.getPhurchaseRate(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getPhurchaseRate]商品购买率统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getPhurchaseRate]商品购买率统计数据发生异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<Object>> pvStatistics(@RequestBody Map<String, String> queryMap) {
        ServiceResult<List<Object>> serviceResult = new ServiceResult<List<Object>>();
        try {
            serviceResult.setResult(statisticsModel.pvStatistics(queryMap));
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
            log.error("[StatisticsService][getPhurchaseRate]浏览量统计发生异常:" + be.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setError(ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[StatisticsService][getPhurchaseRate]浏览量统计数据发生异常:", e);
        }
        return serviceResult;
    }

}
