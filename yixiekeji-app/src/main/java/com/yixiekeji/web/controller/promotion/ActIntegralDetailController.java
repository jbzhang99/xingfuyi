package com.yixiekeji.web.controller.promotion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.integral.ActIntegral;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.product.ProductNorm;
import com.yixiekeji.entity.product.ProductNormAttr;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.service.product.IProductGoodsService;
import com.yixiekeji.service.promotion.IActIntegralService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;

/**
 * 积分商城详情页controller
 * 
 * @Filename: ActIntegralDetailController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class ActIntegralDetailController extends BaseController {

    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private ISellerService       sellerService;
    @Resource
    private IProductGoodsService productGoodsService;
    @Resource
    private IActIntegralService  actIntegralService;
    @Resource
    private DomainUrlUtil        domainUrlUtil;

    /**
     * 跳转到单品页 
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/jifen/{actIntegralId}.html", method = { RequestMethod.GET })
    public String productDetail(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap, @PathVariable Integer actIntegralId) {

        dataMap.put("actIntegralId", actIntegralId);

        ServiceResult<ActIntegral> integralResult = actIntegralService
            .getActIntegralById(actIntegralId);
        if (!integralResult.getSuccess() || integralResult.getResult() == null) {
            dataMap.put("info", "积分活动信息为空。");
            return "h5/commons/error";
        }

        ActIntegral actIntegral = integralResult.getResult();
        if (!isNull(actIntegral.getDescinfo())) {
            actIntegral.setDescinfo(actIntegral.getDescinfo()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }
        //获得商品信息
        ServiceResult<Product> productResult = productFrontService
            .getProductById(actIntegral.getProductId());
        if (!productResult.getSuccess()) {
            dataMap.put("info", productResult.getMessage());
            return "h5/commons/error";
        }
        if (productResult.getResult() == null) {
            dataMap.put("info", "获得商品信息为空！");
            return "h5/commons/error";
        }
        Product product = productResult.getResult();
        if (!isNull(product.getDescription())) {
            product.setDescription(product.getDescription()
                .replace("${(domainUrlUtil.imageResources)!}", domainUrlUtil.getImageResources()));
        }
        dataMap.put("product", product);
        // actIntegral.setProductName(productResult.getResult().getName1());

        if (actIntegral.getTypeState() == null
            || actIntegral.getTypeState() != ActIntegral.TYPE_STATE_1) {
            log.error("积分活动" + actIntegral.getName() + "的分类状态为不显示，下单失败。");
            dataMap.put("info", "对不起，该积分活动已下线！");
            return "h5/commons/error";
        }
        if (actIntegral.getState() == null || actIntegral.getState() != ActIntegral.STATE_3) {
            dataMap.put("info", "对不起，积分活动不存在！");
            return "h5/commons/error";
        }
        if (actIntegral.getActivityState() == null
            || actIntegral.getActivityState() != ActIntegral.ACTIVITY_STATE_2) {
            dataMap.put("info", "对不起，该积分活动还没有发布！");
            return "h5/commons/error";
        }
        if (actIntegral.getChannel() != ConstantsEJS.CHANNEL_1
            && actIntegral.getChannel() != ConstantsEJS.CHANNEL_3) {
            dataMap.put("info", "对不起，该积分活动不在移动端进行！");
            return "h5/commons/error";
        }
        dataMap.put("actIntegral", actIntegral);

        // 获得货品信息,默认取第一个 包含某规格商品的库存及价格信息 
        ServiceResult<List<ProductGoods>> goodsServiceResult = productGoodsService
            .getGoodSByProductId(product.getId());
        List<ProductGoods> goods = goodsServiceResult.getResult();

        if (goods == null || goods.size() == 0) {
            dataMap.put("info", "货品信息为空。");
            return "h5/commons/error";
        }

        // 组装商品规格信息
        List<ProductNorm> normList = new ArrayList<ProductNorm>();
        Map<String, ProductNorm> normMap = new HashMap<String, ProductNorm>();
        Map<String, ProductNormAttr> attrMap = new HashMap<String, ProductNormAttr>();
        // 记录有效的规格组合
        List<String> effectAttr = new ArrayList<>();
        boolean def = false;
        for (ProductGoods good : goods) {
            String normName = good.getNormName(); // 如：颜色,红色;内存,4G
            String normAttrId = good.getNormAttrId(); // 如：1,17

            if (StringUtil.isEmpty(normName, true)) {
                // 规则属性为空则表示该商品没有启用规格（只有一个货品）
                // 设置默认货品，只有一个货品时设定该货品
                dataMap.put("goods", good);
                continue;
            }

            if (good.getState() == null || good.getState().intValue() == ProductGoods.DISABLE) {
                // 货品没有启用则规格不参与组装
                continue;
            }

            // 默认显示的货品
            if (!def) {
                // 设置默认货品，有多个货品时，第一个有效货品为默认货品
                dataMap.put("goods", good);
                def = true;
            }

            effectAttr.add(normAttrId);

            String[] normNameSplit = normName.split(";");
            String[] normAttrIdSplit = normAttrId.split(",");

            if (normNameSplit.length > 0) {
                // 循环
                for (int i = 0; i < normNameSplit.length; i++) {
                    String name = normNameSplit[i];

                    // 得到类似：颜色,红色的值，颜色为规格名称，红色为规格的值
                    String[] cellName = name.split(",");

                    if (normMap.get(cellName[0]) == null) {
                        // 如果规格map中没有当前规格，则添加规格和对应的规格值
                        ProductNorm norm = new ProductNorm();
                        norm.setName(cellName[0]);
                        // 保存规则名称
                        normList.add(norm);
                        normMap.put(cellName[0], norm);
                        // 保存规则值
                        List<ProductNormAttr> attrList = new ArrayList<ProductNormAttr>();
                        ProductNormAttr attr = new ProductNormAttr();
                        attr.setId(ConvertUtil.toInt(normAttrIdSplit[i], 0));
                        attr.setName(cellName[1]);
                        attrList.add(attr);
                        norm.setAttrList(attrList);

                        // 记录到map中防止重复添加
                        attrMap.put(cellName[1], attr);
                    } else {
                        // 如果规格map中有当前规格，则不添加规格，对应的规格值再判断是否已经存在决定是否添加
                        ProductNorm norm = normMap.get(cellName[0]);

                        // 判断是否已有规则值，如果没有则添加，有则不再添加
                        if (attrMap.get(cellName[1]) == null) {
                            // 规则值
                            List<ProductNormAttr> attrList = norm.getAttrList();
                            ProductNormAttr attr = new ProductNormAttr();
                            attr.setId(ConvertUtil.toInt(normAttrIdSplit[i], 0));
                            attr.setName(cellName[1]);
                            attrList.add(attr);
                            norm.setAttrList(attrList);
                            // 记录到map中防止重复添加
                            attrMap.put(cellName[1], attr);
                        }
                    }
                }
            }
        }

        // 有效货品包含的规格
        dataMap.put("norms", normList);
        // 规格数量
        dataMap.put("normsNum", normList.size());
        // 有效的规格组合
        dataMap.put("effectAttr", new Gson().toJson(effectAttr));

        // 获得商家信息、商家详细信息
        ServiceResult<Seller> sellerServiceResult = sellerService
            .getSellerById(actIntegral.getSellerId());
        if (sellerServiceResult.getSuccess() && sellerServiceResult.getResult() != null) {
            // 商家基本信息
            Seller seller = sellerServiceResult.getResult();
            dataMap.put("seller", seller);
        }

        // 记录活动进行状态
        int stageType = 0;
        Date date = new Date();
        if (date.before(actIntegral.getStartTime())) {
            // 还未开始
            // 计算开始倒计时
            long countTime = actIntegral.getStartTime().getTime() - date.getTime();
            dataMap.put("countTime", countTime / 1000);
            stageType = 1;
        } else if (date.after(actIntegral.getEndTime())) {
            // 已结束不计算时间
            stageType = 3;
        } else {
            // 计算结束倒计时
            long countTime = actIntegral.getEndTime().getTime() - date.getTime();
            dataMap.put("countTime", countTime / 1000);
            stageType = 2;
        }
        dataMap.put("stageType", stageType);
        return "h5/promotion/actintegraldetail";
    }

}
