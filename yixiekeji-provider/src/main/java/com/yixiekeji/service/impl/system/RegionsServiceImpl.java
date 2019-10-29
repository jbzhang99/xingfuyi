package com.yixiekeji.service.impl.system;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.model.system.RegionsModel;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.vo.system.RegionsVO;

@RestController
public class RegionsServiceImpl implements IRegionsService {
    private static Logger log = LoggerFactory.getLogger(RegionsServiceImpl.class);

    @Resource
    private RegionsModel  regionsModel;

    /**
     * 根据id取得regions对象
     * @param  regionsId
     * @return
     */
    @Override
    public ServiceResult<Regions> getRegionsById(@RequestParam("regionsId") Integer regionsId) {
        ServiceResult<Regions> result = new ServiceResult<Regions>();
        try {
            result.setResult(regionsModel.getRegionsById(regionsId));
        } catch (Exception e) {
            log.error("根据id[" + regionsId + "]取得regions对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据id[" + regionsId + "]取得regions对象时出现未知异常");
        }
        return result;
    }

    @Override
    public List<Regions> getProvince() {
        try {
            List<Regions> list = this.regionsModel.getProvince();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取城市异常");
        }
    }

    @Override
    public List<Regions> getChildrenArea(@RequestParam("parent") Integer parent,
                                         @RequestParam("type") String type) {
        try {
            List<Regions> list = regionsModel.getChildrenArea(parent, type);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取城市异常");
        }
    }

    @Override
    public List<RegionsVO> getAllArea() {
        try {
            List<RegionsVO> list = regionsModel.getAllArea();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("获取城市异常");
        }
    }

    @Override
    public ServiceResult<List<Regions>> getRegionsByParentId(@RequestParam("parentId") Integer parentId) {
        ServiceResult<List<Regions>> result = new ServiceResult<List<Regions>>();
        try {
            result.setResult(regionsModel.getRegionsByParentId(parentId));
        } catch (Exception e) {
            log.error("根据父id[" + parentId + "]取得regions对象时出现未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据父id[" + parentId + "]取得regions对象时出现未知异常");
        }
        return result;
    }
}