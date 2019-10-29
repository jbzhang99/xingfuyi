package com.yixiekeji.service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.product.ProductNormAttrOpt;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.util.FeignProjectUtil;

/**
 * 商品规格、规格属性管理
 * @Version: 1.0
 * @Author: zhaozhx
 * @Email: zhaozhx@sina.cn
 */
@FeignClient(name = FeignUtil.FEIGN_NAME)
@RequestMapping(value = "productNorm")
@Service(value = "productNormService")
public interface IProductNormService {

    /**
     * 保存商品规格
     * @param norm
     * @return
     */
    @RequestMapping(value = "saveNorm", method = RequestMethod.POST)
    ServiceResult<Boolean> saveNorm(ProductNorm norm);

    /**
     * 保存商品规格、规格属性
     * @param map key:norm、attr
     * @return
     */
    @RequestMapping(value = "saveNormMap", method = RequestMethod.POST)
    ServiceResult<Boolean> saveNormMap(FeignProjectUtil feignProjectUtil);

    /**
     * 根据id查询商品规格
     * @param id
     * @return
     */
    @RequestMapping(value = "getNormById", method = RequestMethod.GET)
    ServiceResult<ProductNorm> getNormById(@RequestParam("id") Integer id);

    /**
     * 根据ids查询商品规格
     * @param ids
     * @return
     */
    @RequestMapping(value = "getNormByIds", method = RequestMethod.GET)
    ServiceResult<List<ProductNorm>> getNormByIds(@RequestParam("ids") String ids);

    /**
     * 分页展示商品规格
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "pageNorm", method = RequestMethod.POST)
    ServiceResult<List<ProductNorm>> pageNorm(FeignUtil feignUtil);

    /**
     * 更新商品规格
     * @param norm
     * @return
     */
    @RequestMapping(value = "updateNorm", method = RequestMethod.POST)
    ServiceResult<Boolean> updateNorm(ProductNorm norm);

    /**
     * 更新商品规格
     * @param map key:norm attr
     * @return
     */
    @RequestMapping(value = "updateNormMap", method = RequestMethod.POST)
    ServiceResult<Boolean> updateNormMap(FeignProjectUtil feignProjectUtil);

    /**
     * 根据规格id查询商品类型,检查规格是否被占用
     * @param id
     * @return
     */
    @RequestMapping(value = "chkHasUsed", method = RequestMethod.GET)
    ServiceResult<List<ProductType>> chkHasUsed(@RequestParam("id") Integer id);

    /**
     * 商城商品规格
     * @param id
     * @return
     */
    @RequestMapping(value = "collectionSeller", method = RequestMethod.GET)
    ServiceResult<Boolean> delNorm(@RequestParam("id") Integer id);

    /**
     * 保存商品规格属性
     * @param attr
     * @return
     */
    @RequestMapping(value = "saveNormAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> saveNormAttr(ProductNormAttr attr);

    /**
     * 根据商品规格id查询商品规格属性
     * @param id
     * @return
     */
    @RequestMapping(value = "getNormAttrById", method = RequestMethod.GET)
    ServiceResult<ProductNormAttr> getNormAttrById(@RequestParam("id") Integer id);

    /**
     * 根据normId获取attr
     * @param id
     * @return
     */
    @RequestMapping(value = "getAttrByNormId", method = RequestMethod.GET)
    ServiceResult<List<ProductNormAttr>> getAttrByNormId(@RequestParam("id") Integer id);

    /**
     * 根据normId获取attr
     * @param ids
     * @return
     */
    @RequestMapping(value = "getAttrByNormIds", method = RequestMethod.GET)
    ServiceResult<List<ProductNormAttr>> getAttrByNormIds(@RequestParam("ids") String ids);

    /**
     * 分页列表展示商品规格属性
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "pageNormAttr", method = RequestMethod.POST)
    ServiceResult<List<ProductNormAttr>> pageNormAttr(FeignUtil feignUtil);

    /**
     * 修改商品规格属性
     * @param attr
     * @return
     */
    @RequestMapping(value = "updateNormAttr", method = RequestMethod.POST)
    ServiceResult<Boolean> updateNormAttr(ProductNormAttr attr);

    /**
     * 删除商品规格属性
     * @param id
     * @return
     */
    @RequestMapping(value = "delNormAttr", method = RequestMethod.GET)
    ServiceResult<Boolean> delNormAttr(@RequestParam("id") Integer id);

    /**********************商品选定的规格属性操作************************/

    /**
     * 保存商品选定的规格属性
     * @param opt
     * @return
     */
    @RequestMapping(value = "saveNormAttrOpt", method = RequestMethod.POST)
    ServiceResult<Boolean> saveNormAttrOpt(ProductNormAttrOpt opt);

    /**
     * 修改商品选定的规格属性
     * @param opt
     * @return
     */
    @RequestMapping(value = "updateNormAttrOpt", method = RequestMethod.POST)
    ServiceResult<Boolean> updateNormAttrOpt(ProductNormAttrOpt opt);

    /**
     * 根据id删除商品选定的规格属性
     * @param id
     * @return
     */
    @RequestMapping(value = "delNormAttrOpt", method = RequestMethod.GET)
    ServiceResult<Boolean> delNormAttrOpt(@RequestParam("id") Integer id);

    /**
     * 分页获取商品选定的规格属性
     * @param queryMap
     * @param pager
     * @return
     */
    @RequestMapping(value = "pageNormAttrOpt", method = RequestMethod.POST)
    ServiceResult<List<ProductNormAttrOpt>> pageNormAttrOpt(FeignUtil feignUtil);

    /**
     * 根据id获取商品选定的规格属性
     * @param id
     * @return
     */
    @RequestMapping(value = "getNormAttrOptById", method = RequestMethod.GET)
    ServiceResult<ProductNormAttrOpt> getNormAttrOptById(@RequestParam("id") Integer id);

    /**
     * 查询所有的规格
     * @return
     */
    @RequestMapping(value = "listNoPage", method = RequestMethod.GET)
    ServiceResult<List<ProductNorm>> listNoPage();

}
