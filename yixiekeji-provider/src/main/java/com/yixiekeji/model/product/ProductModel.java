package com.yixiekeji.model.product;

import com.alibaba.fastjson.JSON;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.product.ProductBrandReadDao;
import com.yixiekeji.dao.shop.read.product.ProductPictureReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.product.*;
import com.yixiekeji.dao.shop.write.seller.SellerCateWriteDao;
import com.yixiekeji.entity.product.*;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.util.FrontProductPictureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;

@Service(value = "productModel")
public class ProductModel {
    private static Logger                log = LoggerFactory.getLogger(ProductModel.class);

    @Resource
    private ProductWriteDao              productWriteDao;
    @Resource
    private ProductReadDao               productReadDao;
    @Resource
    private ProductPictureWriteDao       productPictureWriteDao;
    @Resource
    private ProductPictureReadDao        productPictureReadDao;
    @Resource
    private ProductAttrWriteDao          productAttrWriteDao;
    @Resource
    private ProductGoodsWriteDao         productGoodsWriteDao;
    @Resource(name = "transactionManager")
    private DataSourceTransactionManager transactionManager;
    @Resource
    private ProductBrandWriteDao         productBrandWriteDao;
    @Resource
    private ProductCateWriteDao          productCateWriteDao;
    @Resource
    private SellerCateWriteDao           sellerCateWriteDao;
    @Resource
    private ProductNormWriteDao          productNormWriteDao;
    @Resource
    private SellerReadDao                sellerReadDao;
    @Resource
    private ProductNormAttrOptWriteDao   productNormAttrOptWriteDao;
    @Resource
    private ProductBrandReadDao          productBrandReadDao;

    public List<Product> getByCateIdTop(Integer cateId, Integer limit) {
        return productReadDao.getByCateIdTop(cateId, limit);
    }

    /**
     * 获取size个商家推荐商品
     * 
     * @param sellerId
     *            商家ID
     * @param size
     *            获取条数
     * @return
     */
    public List<Product> getSellerRecommendProducts(Integer sellerId, Integer size) {
        List<Product> list = productReadDao.getSellerRecommendProducts(sellerId, size);
        this.setProductMiddleImg(list);
        return list;
    }

    /**
     * 获取size个商家新品
     * 
     * @param sellerId
     *            商家ID
     * @param size
     *            获取条数
     * @return
     */
    public List<Product> getSellerNewProducts(Integer sellerId, Integer size) {
        List<Product> list = productReadDao.getSellerNewProducts(sellerId, size);
        this.setProductMiddleImg(list);
        return list;
    }

    /**
     * 查询商家所有在售商品（商家列表页）
     * 
     * @param sellerId
     * @param sort
     *            0:默认；1、销量从大到小；2、评价从多到少；3、价格从低到高；4、价格从高到低
     * @param sellerCateId
     * @param start
     * @param size
     * @return
     */
    public List<Product> getProductForSellerList(Integer sellerId, Integer sort,
                                                 Integer sellerCateId, Integer start,
                                                 Integer size) {
        // 排序支持0到4，其他一律按0处理
        if (sort == null || sort > 4) {
            sort = 0;
        }
        List<Product> products = productReadDao.getProductForSellerList(sellerId, sort,
            sellerCateId, start, size);
        this.setProductMiddleImg(products);
        return products;
    }

    public Integer getProductForSellerListCount(Integer sellerId, Integer sort,
                                                Integer sellerCateId) {
        // 排序支持0到4，其他一律按0处理
        if (sort == null || sort > 4) {
            sort = 0;
        }
        return productReadDao.getProductForSellerListCount(sellerId, sort, sellerCateId);
    }

    /**
     * 给商品设置中图路径
     * 
     * @param list
     */
    private void setProductMiddleImg(List<Product> list) {
        if (list != null && list.size() > 0) {
            for (Product product : list) {
                ProductPicture productPicture = productPictureReadDao
                    .getproductLead(product.getId());
                String productLeadMiddle = FrontProductPictureUtil
                    .getproductLeadMiddle(productPicture);

                product.setImagePath(productLeadMiddle);
            }
        }
    }

    /**
     * 获取size个推荐商品
     * 
     * @return
     */
    public List<Product> getRecommendProducts(Integer size) {
        List<Product> list = productReadDao.getRecommendProducts(size);
        this.setProductMiddleImg(list);
        return list;
    }

    public Boolean saveProduct(Map<String, String[]> parammap, Product product,
                               List<ProductPicture> productPictureList,
                               List<ProductAttr> productAttrList) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {

            // 1.check
            // 校验spu唯一，新增时商品ID为空
            Boolean unique = this.isUnique(product.getSellerId(), product.getProductCode(), 0);
            if (!unique) {
                throw new BusinessException("SPU不能重复，请重新输入");
            }
            // 校验sku唯一
            if (null != product.getGoodsList() && product.getGoodsList().size() > 0) {
                // 使用write库防止数据不同步
                HashSet<String> set = new HashSet<String>();
                for (ProductGoods goods : product.getGoodsList()) {
                    // 校验与自己的重复
                    if (!set.add(goods.getSku())) {
                        throw new BusinessException("SKU[" + goods.getSku() + "]不能重复，请重新输入");
                    }
                    // 校验与数据库的重复
                    List<ProductGoods> prolist = productGoodsWriteDao
                        .getBySellerIdAndSku(product.getSellerId(), goods.getSku());
                    if (prolist != null && prolist.size() > 0) {
                        throw new BusinessException("SKU[" + goods.getSku() + "]已存在，请重新输入");
                    }
                }
            } else {
                // 校验与数据库的重复
                List<ProductGoods> prolist = productGoodsWriteDao
                    .getBySellerIdAndSku(product.getSellerId(), product.getSku());
                if (prolist != null && prolist.size() > 0) {
                    throw new BusinessException("SKU[" + product.getSku() + "]已存在，请重新输入");
                }
            }

            // 2.save product
            this.dbConstrainsProduct(product);
            productWriteDao.insert(product);
            // 3.save productPicture
            if (null != productPictureList && productPictureList.size() > 0) {
                for (ProductPicture picture : productPictureList) {
                    picture.setProductId(product.getId());
                    productPictureWriteDao.insert(picture);
                }
            }
            // 4.save productAttr
            if (null != productAttrList && productAttrList.size() > 0) {
                for (ProductAttr attr : productAttrList) {
                    attr.setProductId(product.getId());
                    productAttrWriteDao.insert(attr);
                }
            }
            // 5.save productGood
            if (null != product.getGoodsList() && product.getGoodsList().size() > 0) {
                for (ProductGoods goods : product.getGoodsList()) {
                    goods.setProductId(product.getId());
                    this.dbConstrainsProductGood(goods);
                    productGoodsWriteDao.insert(goods);
                }
            } else {
                // 默认货品
                ProductGoods goods = new ProductGoods();
                goods.setProductId(product.getId());
                goods.setMallPcPrice(product.getMallPcPrice());
                goods.setMallMobilePrice(product.getMalMobilePrice());
                goods.setProductStock(product.getProductStock());
                goods.setProductStockWarning(-1);
                goods.setActualSales(0);
                goods.setSku(product.getSku());
                goods.setState(ProductGoods.ENABLE);
                goods.setImages("");
                goods.setWeight(product.getWeight());
                goods.setLength(product.getLength());
                goods.setWidth(product.getWidth());
                goods.setHeight(product.getHeight());
                this.dbConstrainsProductGood(goods);
                productGoodsWriteDao.insert(goods);
            }

            // 6.保存选中的sku信息
            // 只有启用规格且货品不为空时保存选中的sku规格
            if (product.getIsNorm() == Product.IS_NORM_2 && product.getGoodsList() != null) {
                saveSelectedNorm(parammap, product, 1);
            }

            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("ProductServiceImpl saveProduct param:" + JSON.toJSONString(product));
            log.error("ProductServiceImpl saveProduct exception:", e);
            throw e;
        }
    }

    /**
     * 保存选择的规格属性
     * 
     * @param parammap
     * @param product
     * @param type
     * @throws Exception
     */
    private void saveSelectedNorm(Map<String, String[]> parammap, Product product, int type) {
        String normNum = getRequestValue(parammap, "normNum");
        String normAttrNum = getRequestValue(parammap, "normAttrNum");
        if (StringUtil.isEmpty(normNum) || Integer.valueOf(normNum) < 1)
            return;
        if (StringUtil.isEmpty(normAttrNum) || Integer.valueOf(normAttrNum) < 1)
            return;

        // 编辑
        if (type == 2) {
            // 当前商品选中的属性
            Map<String, Object> param = new HashMap<>();
            param.put("productId", product.getId() + "");
            param.put("sellerId", product.getSellerId() + "");
            List<ProductNormAttrOpt> optlist = productNormAttrOptWriteDao.page(param);

            for (int i = 0; i <= Integer.valueOf(normNum); i++) {
                for (int j = 0; j <= Integer.valueOf(normAttrNum); j++) {
                    if (optlist == null || optlist.size() < 1) {
                        // 可能是旧商品数据
                        createnew(parammap, product.getSellerId(), product, i, j);
                    } else {
                        // 更新图片
                        for (ProductNormAttrOpt opt : optlist) {
                            String attr = getRequestValue(parammap, "attr_id_" + i + "_" + j);
                            if (StringUtil.isEmpty(attr)
                                || opt.getAttrId().intValue() != Integer.valueOf(attr)) {
                                continue;
                            }
                            String img = getRequestValue(parammap, "image_" + i + "_" + j);
                            log.debug(opt.getId() + "更新图片：" + img);
                            if (!StringUtil.isEmpty(img)) {
                                opt.setImage(img);
                                productNormAttrOptWriteDao.update(opt);

                                // 更新对应货品图片
                                saveGoodsPic(opt, product.getGoodsList());
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i <= Integer.valueOf(normNum); i++) {
                for (int j = 0; j <= Integer.valueOf(normAttrNum); j++) {
                    String normid = getRequestValue(parammap, "norm_id_" + i + "_" + j);
                    // 只有用户点击某属性才会有此值
                    String attrid = getRequestValue(parammap, "attr_id_" + i + "_" + j);
                    // 没有选择此规格
                    if (StringUtil.isEmpty(normid) || StringUtil.isEmpty(attrid))
                        continue;
                    createnew(parammap, product.getSellerId(), product, i, j);
                }
            }
        }

    }

    private void saveGoodsPic(ProductNormAttrOpt opt, List<ProductGoods> pglist) {
        for (ProductGoods pg : pglist) {
            if (StringUtil.isEmpty(pg.getNormAttrId()))
                continue;
            String[] normattrids = pg.getNormAttrId().split(",");
            for (String attrid : normattrids) {
                if (Integer.valueOf(attrid).intValue() == Integer.valueOf(opt.getAttrId())
                    .intValue()) {
                    pg.setImages(opt.getImage());
                    productGoodsWriteDao.update(pg);
                    break;
                }
            }
        }
    }

    private String getRequestValue(Map<String, String[]> parammap, String key) {
        if (parammap == null || parammap.size() < 1)
            return null;
        String[] values = parammap.get(key);
        if (values == null) {
            return null;
        }
        return values[0];
    }

    private void createnew(Map<String, String[]> parammap, Integer sellerId, Product product, int i,
                           int j) {
        // 新增

        ProductNormAttrOpt opt = new ProductNormAttrOpt();
        opt.setSellerId(sellerId);
        opt.setCreateId(product.getCreateId());
        String colortype = getRequestValue(parammap, "type_attr_" + i + "_" + j);
        opt.setTypeAttr(!StringUtil.isEmpty(colortype) && "custom".equals(colortype) ? 2 : 1);
        opt.setCreateTime(new Date());
        // 类型固定图片
        opt.setType(2);
        // 属性值
        String attrid = getRequestValue(parammap, "attr_id_" + i + "_" + j);
        if (!StringUtil.isEmpty(attrid))
            opt.setAttrId(Integer.valueOf(attrid));

        String normid = getRequestValue(parammap, "norm_id_" + i + "_" + j);
        if (!StringUtil.isEmpty(normid))
            opt.setProductNormId(Integer.valueOf(normid));

        String normname = getRequestValue(parammap, "norm_name_" + i + "_" + j);
        if (!StringUtil.isEmpty(normname))
            opt.setProductNormName(normname);

        String image = getRequestValue(parammap, "image_" + i + "_" + j);
        if (!StringUtil.isEmpty(image))
            opt.setImage(image);

        String attrname = getRequestValue(parammap, "attr_name_" + i + "_" + j);
        if (!StringUtil.isEmpty(attrname))
            opt.setName(attrname);
        opt.setProductId(product.getId());
        productNormAttrOptWriteDao.save(opt);

        // 更新货品图片
        for (ProductGoods pg : product.getGoodsList()) {
            if (StringUtil.isEmpty(pg.getNormAttrId()))
                continue;
            String[] normattrids = pg.getNormAttrId().split(",");
            for (String normattid : normattrids) {
                if (Integer.valueOf(normattid).intValue() == Integer.valueOf(opt.getAttrId())
                    .intValue()) {
                    pg.setImages(opt.getImage());
                    productGoodsWriteDao.update(pg);
                    break;
                }
            }
        }

    }

    public Boolean updateProduct(Map<String, String[]> parammap, Product product,
                                 List<ProductPicture> productPictureList,
                                 List<ProductAttr> productAttrList) {
        // 如果商品被删除,取消推荐
        if (product != null && null != product.getState()
            && product.getState() == Product.STATE_5) {
            product.setIsTop(ConstantsEJS.PRODUCT_IS_TOP_1);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            if (null == product)
                throw new BusinessException("更新商品失败，商品信息为空");
            if (null == product.getId() || 0 == product.getId())
                throw new BusinessException("更新商品失败，商品id为空");

            // spu不能重复
            Boolean unique = this.isUnique(product.getSellerId(), product.getProductCode(),
                product.getId());
            if (!unique) {
                throw new BusinessException("SPU已存在，请重新输入");
            }

            /** 更新商品 **/
            productWriteDao.update(product);

            /** 更新商品图片 **/
            if (null != productPictureList && productPictureList.size() > 0) {
                productPictureWriteDao.delByProductId(product.getId());
                for (ProductPicture picture : productPictureList) {
                    picture.setProductId(product.getId());
                    productPictureWriteDao.insert(picture);
                }
            }

            /** 更新商品 **/
            if (null != productAttrList && productAttrList.size() > 0) {
                productAttrWriteDao.delByProductId(product.getId());
                for (ProductAttr attr : productAttrList) {
                    attr.setProductId(product.getId());
                    productAttrWriteDao.insert(attr);
                }
            }

            /** 更新货品 **/
            if (null != product.getGoodsList() && product.getGoodsList().size() > 0) {
                // 使用write库防止数据不同步
                // 校验sku唯一
                HashSet<String> set = new HashSet<String>();
                for (ProductGoods goods : product.getGoodsList()) {
                    // goods.setNormName();颜色:黄色,尺码:XL
                    // normName:normAttrName,normName:normAttrName,
                    goods.setProductId(product.getId());
                    this.dbConstrainsProductGood(goods);
                    ProductGoods dbGood = productGoodsWriteDao
                        .getByProductIdAndAttrId(product.getId(), goods.getNormAttrId());
                    if (null != dbGood) {
                        goods.setId(dbGood.getId());
                        productGoodsWriteDao.update(goods);
                    } else {
                        productGoodsWriteDao.insert(goods);
                    }

                    // 校验与自己的重复
                    if (!set.add(goods.getSku())) {
                        throw new BusinessException("SKU[" + goods.getSku() + "]不能重复，请重新输入");
                    }
                    // 校验与数据库的重复
                    List<ProductGoods> prolist = productGoodsWriteDao
                        .getBySellerIdAndSku(product.getSellerId(), goods.getSku());
                    if (prolist != null && prolist.size() > 1) {
                        throw new BusinessException("SKU[" + goods.getSku() + "]已存在，请重新输入");
                    } else if (prolist != null && prolist.size() == 1) {
                        // 如果只有一个，则判断id是否一样
                        if (!goods.getId().equals(prolist.get(0).getId())) {
                            throw new BusinessException("SKU[" + goods.getSku() + "]已存在，请重新输入");
                        }
                    }
                }
            } else {

                // 默认货品
                ProductGoods goods = productGoodsWriteDao.getByProductId(product.getId());

                // 校验与数据库的重复
                List<ProductGoods> prolist = productGoodsWriteDao
                    .getBySellerIdAndSku(product.getSellerId(), product.getSku());
                if (prolist != null && prolist.size() > 1) {
                    throw new BusinessException("SKU[" + product.getSku() + "]已存在，请重新输入");
                } else if (prolist != null && prolist.size() == 1) {
                    // 如果只有一个，则判断id是否一样
                    if (!goods.getId().equals(prolist.get(0).getId())) {
                        throw new BusinessException("SKU[" + product.getSku() + "]已存在，请重新输入");
                    }
                }

                goods.setProductId(product.getId());
                goods.setMallPcPrice(product.getMallPcPrice());
                goods.setMallMobilePrice(product.getMalMobilePrice());
                goods.setProductStock(product.getProductStock());
                goods.setProductStockWarning(-1);
                goods.setSku(product.getSku());
                goods.setImages("");
                goods.setWeight(product.getWeight());
                goods.setLength(product.getLength());
                goods.setWidth(product.getWidth());
                goods.setHeight(product.getHeight());
                this.dbConstrainsProductGood(goods);
                productGoodsWriteDao.update(goods);
            }

            /** 更新选中的sku信息 */
            // 只有启用规格且货品不为空时保存选中的sku规格
            if (product.getIsNorm() == 2 && product.getGoodsList() != null) {
                saveSelectedNorm(parammap, product, 2);
            }

            transactionManager.commit(status);
            return true;
        } catch (Exception e) {
            transactionManager.rollback(status);
            log.error("ProductServiceImpl updateProduct param:" + JSON.toJSONString(product));
            log.error("ProductServiceImpl updateProduct exception:", e);
            throw e;
        }
    }

    public Boolean delProduct(Integer productId, Integer sellerId) {
        if (null == productId || 0 == productId) {
            throw new BusinessException("根据id删除商品表失败，id为空");
        }
        Product product = productWriteDao.get(productId);
        if (product == null) {
            throw new BusinessException("根据id删除商品表失败，商品不存在");
        }
        if (!product.getSellerId().equals(sellerId)) {
            throw new BusinessException("您无权限删除该商品。");
        }
        if (product.getState().intValue() == Product.STATE_6) {
            throw new BusinessException("已经上架的商品不能删除");
        }
        return productWriteDao.updateState(productId, Product.STATE_5) > 0;
    }

    public Product getProductById(Integer productId) {
        if (null == productId || 0 == productId) {
            throw new BusinessException("根据id获取商品表失败，id为空");
        }

        Product product = productWriteDao.get(productId);
        if (product.getIsNorm() != 2) {
            ProductGoods goods = productGoodsWriteDao.getByProductId(productId);
            product.setSku(goods.getSku());
            product.setWeight(goods.getWeight());
            product.setLength(goods.getLength());
            product.setWidth(goods.getWidth());
            product.setHeight(goods.getHeight());
        }
        return product;
    }

    public List<Product> pageProduct(Map<String, String> queryMap,
                                     PagerInfo pager) throws Exception {
        List<Product> list = new ArrayList<Product>();
        try {
            Integer start = 0, size = 0;
            String state = queryMap.get("q_state");
            if (!StringUtil.isEmpty(state) && state.indexOf(",") != -1) {
                if (pager != null) {
                    pager.setRowsCount(productWriteDao.count1(queryMap));
                    start = pager.getStart();
                    size = pager.getPageSize();
                }
                list = productWriteDao.page1(queryMap, start, size);
            } else {
                if (pager != null) {
                    pager.setRowsCount(productWriteDao.count(queryMap));
                    start = pager.getStart();
                    size = pager.getPageSize();
                }
                list = productWriteDao.page(queryMap, start, size);
            }

            for (Product pro : list) {
                ProductCate pcate = productCateWriteDao.get(pro.getProductCateId());
                pro.setProductCateName(pcate == null ? null : pcate.getName());
                ProductBrand productBrand = productBrandWriteDao.getById(pro.getProductBrandId());
                if(productBrand != null) {
                    pro.setProductBrandName(
                            productBrand.getName());
                }
                SellerCate cate = sellerCateWriteDao.get(pro.getSellerCateId());
                pro.setSellerCateName(cate == null ? null : cate.getName());

                Seller seller = sellerReadDao.get(pro.getSellerId());
                if (null != seller && !StringUtil.isEmpty(seller.getSellerName())) {
                    pro.setSeller(seller.getSellerName());
                }
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return list;
    }

    public List<Product> getProductsBySellerId(Integer sellerid) {
        if (sellerid == null)
            throw new BusinessException("没有商家");
        return productWriteDao.getProductsBySellerId(sellerid);
    }

    public Integer updateByIds(Map<String, Object> param, List<Integer> ids) {
        return productWriteDao.updateByIds(param, ids);
    }

    /**
     * 根据商品ID修改商品状态
     * 
     * @param id
     * @param state
     * @return
     */
    public boolean updateProductState(Integer id, Integer state) {
        return productWriteDao.updateState(id, state) > 0;
    }

    /**
     * 根据商品ID修改商品推荐状态
     * 
     * @param id
     * @param isTop
     * @return
     */
    public boolean updateProductRecommend(Integer id, Integer isTop) {
        return productWriteDao.updateRecommend(id, isTop) > 0;
    }

    /**
     * 以商品id字符串获取商品
     * 
     * @param ids
     * @return
     */
    public List<Product> getProductsByIds(List<Integer> ids) {
        return productReadDao.getProductsByIds(ids);
    }

    private void dbConstrainsProduct(Product product) {
        product.setName1(StringUtil.dbSafeString(product.getName1(), false, 200));
        product.setName2(StringUtil.dbSafeString(product.getName2(), false, 200));
        product.setKeyword(StringUtil.dbSafeString(product.getKeyword(), false, 200));
        product.setNormIds(StringUtil.dbSafeString(product.getNormIds(), false, 255));
        product.setNormName(StringUtil.dbSafeString(product.getNormName(), false, 255));
        product.setMasterImg(StringUtil.dbSafeString(product.getMasterImg(), false, 255));

    }

    private void dbConstrainsProductGood(ProductGoods productGoods) {
        productGoods
            .setNormAttrId(StringUtil.dbSafeString(productGoods.getNormAttrId(), false, 255));
        productGoods.setNormName(StringUtil.dbSafeString(productGoods.getNormName(), false, 255));
        productGoods.setSku(StringUtil.dbSafeString(productGoods.getSku(), false, 50));
        productGoods.setImages(StringUtil.dbSafeString(productGoods.getImages(), false, 255));
    }

    /**
     * 查询最大的商品
     * 
     * @return
     */
    public Product getProductByMax() {
        return productReadDao.getProductByMax();
    }

    /**
     * 根据商家ID和SPU判断是否已有商品存在
     * @param sellerId
     * @param productCode
     * @param productId 商品ID，新增时传0，修改时传实际ID
     * @return
     */
    public Boolean isUnique(Integer sellerId, String productCode, Integer productId) {
        productId = productId == null ? 0 : productId;
        Integer count = productWriteDao.getByIdAndSellerIdAndSpu(sellerId, productCode, productId);
        return count == 0;
    }

    /**
     * 获取销量最好的size个商品
     * @param size
     * @return
     */
    public List<Product> getSalesProducts(Integer size) {

        List<Product> list = productReadDao.getSalesProducts(size);
        for (Product product : list) {
            ProductBrand productBrand = productBrandReadDao.getById(product.getProductBrandId());
            product.setProductBrandName(productBrand.getName());
        }

        this.setProductMiddleImg(list);

        return list;
    }
}
