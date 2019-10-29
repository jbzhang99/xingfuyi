package com.yixiekeji.model.product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.product.ProductAttrReadDao;
import com.yixiekeji.dao.shop.read.product.ProductBrandReadDao;
import com.yixiekeji.dao.shop.read.product.ProductCateReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.product.ProductTypeAttrReadDao;
import com.yixiekeji.dao.shop.read.product.ProductTypeReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerCateReadDao;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.product.ProductTypeAttr;
import com.yixiekeji.entity.seller.SellerCate;
import com.yixiekeji.vo.product.ProductTypeAttrVO;
import com.yixiekeji.vo.search.SearchProductVO;

@Component(value = "productFrontModel")
public class ProductFrontModel {

    @Resource
    private ProductReadDao         productReadDao;
    @Resource
    private ProductCateReadDao     productCateReadDao;
    @Resource
    private ProductBrandReadDao    productBrandReadDao;
    @Resource
    private ProductAttrReadDao     productAttrReadDao;
    @Resource
    private ProductTypeReadDao     productTypeReadDao;
    @Resource
    private ProductTypeAttrReadDao productTypeAttrReadDao;

    @Resource
    private SellerCateReadDao      sellerCateReadDao;

    public List<Product> getProductsBySellerId(Integer sellerid) {
        if (sellerid == null)
            throw new BusinessException("没有商家");
        return productReadDao.getProductsBySellerId(sellerid);
    }

    /**
     * 根据分类列表页查询商品
     * @param cateId
     * @param mapCondition
     * @param stack
     */
    public void getProducts(Integer cateId, Map<String, Object> mapCondition,
                            Map<String, Object> stack) {
        //取得分类
        if (cateId == null) {
            throw new BusinessException("传入的分类路径不能为空");
        }
        ProductCate productCate = findCateAll(cateId, stack);

        //取得类型
        ProductType productType = productTypeReadDao.get(productCate.getProductTypeId());

        //查询出类型下面关联的品牌，并对首字母进行排序
        findBrandAll(stack, productType.getProductBrandIds());

        //查询出类型下面关联的查询属性
        List<ProductTypeAttr> productTypeAttrs = productTypeAttrReadDao
            .getByTypeIdAndQuery(productType.getId());
        List<ProductTypeAttr> productTypeAttrsAll = new ArrayList<ProductTypeAttr>();
        for (ProductTypeAttr productTypeAttr : productTypeAttrs) {
            productTypeAttrsAll.add(productTypeAttr);
        }
        mapCondition.put("productTypeAttrsAll", productTypeAttrsAll);

        //组装类型查询Map对象，键是ID，值是查询的对象
        Map<String, ProductTypeAttr> typeAttrMap = new HashMap<String, ProductTypeAttr>();
        for (ProductTypeAttr productTypeAttr : productTypeAttrs) {
            typeAttrMap.put(productTypeAttr.getId().toString(), productTypeAttr);
        }

        //查询出商品
        Integer sort = 0;
        try {
            sort = (Integer) mapCondition.get("sort");
        } catch (Exception e) {
            sort = 0;
        }
        if (sort == null || sort > 4) {
            sort = 0;
        }
        stack.put("sort", sort);

        Integer doSelf = (Integer) mapCondition.get("doSelf");
        Integer store = (Integer) mapCondition.get("store");

        Integer brandId = ConvertUtil.toInt(mapCondition.get("brandId"), 0);
        if (brandId > 0) {
            ProductBrand productBrand = productBrandReadDao.getById(brandId);
            stack.put("brandName", productBrand.getName());
        }

        Map<String, String> attrFilter = new HashMap<String, String>();

        String filterAll = (String) mapCondition.get("filterAll");
        String searchQuery = this.searchIndexAssembling(cateId, doSelf, store, brandId, filterAll,
            typeAttrMap, attrFilter);
        stack.put("productTypeAttrWhereAlls", attrFilter);

        SolrClient client = getSolrClient();
        SolrQuery query = new SolrQuery();
        query.setQuery(searchQuery);

        int page = ConvertUtil.toInt(mapCondition.get("page"), 1);
        if (page < 1) {
            page = 1;
        }
        int startNumber = (page - 1) * ConstantsEJS.DEFAULT_PAGE_SIZE;
        query.set("start", startNumber);
        query.set("rows", ConstantsEJS.DEFAULT_PAGE_SIZE);

        // 排序
        if (sort == 0) {
            query.set("sort", SearchProductVO.SALESNUM_ + " desc");
        } else if (sort == 1) {
            query.set("sort", SearchProductVO.SALESNUM_ + " desc");
        } else if (sort == 2) {
            query.set("sort", SearchProductVO.COMMENTSNUMBER_ + " desc");
        } else if (sort == 3) {
            query.set("sort", SearchProductVO.MALLPCPRICE_ + " asc");
        } else if (sort == 4) {
            query.set("sort", SearchProductVO.MALLPCPRICE_ + " desc");
        }

        query.setHighlight(false);

        List<Product> products = new ArrayList<Product>();
        QueryResponse response = null;
        try {
            response = client.query(query);
            SolrDocumentList docs = response.getResults();
            long count = docs.getNumFound();
            stack.put("productSize", count);
            for (SolrDocument doc : docs) {
                Integer id = (Integer) doc.getFieldValue(SearchProductVO.ID_);

                Product product = new Product();
                product.setId(id);

                product.setName1(doc.getFieldValue(SearchProductVO.NAME1_).toString()
                    .replace("[", "").replace("]", ""));

                product.setMasterImg(doc.getFieldValue(SearchProductVO.MASTERIMG_).toString());
                product.setMallPcPrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MALLPCPRICE_).toString()));
                product.setMalMobilePrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MALMOBILEPRICE_).toString()));
                product.setProductStock(
                    new Integer(doc.getFieldValue(SearchProductVO.PRODUCTSTOCK_).toString()));
                product.setCommentsNumber(
                    (Integer) doc.getFieldValue(SearchProductVO.COMMENTSNUMBER_));
                product.setSellerId((Integer) doc.getFieldValue(SearchProductVO.SELLERID_));

                product.setMarketPrice(
                    new BigDecimal(doc.getFieldValue(SearchProductVO.MARKETPRICE_).toString()));
                product.setActualSales(
                    new Integer(doc.getFieldValue(SearchProductVO.SALESNUM_).toString()));

                products.add(product);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stack.put("producListVOs", products);

        //拼装列表页查询条件的VO对象
        List<ProductTypeAttrVO> productTypeAttrVOs = installProductAttr(typeAttrMap);
        stack.put("productTypeAttrVOs", productTypeAttrVOs);
    }

    private SolrClient getSolrClient() {
        String solrUrl = "";
        String solrServer = "";
        return new HttpSolrClient(solrUrl + "/" + solrServer);
    }

    /**
     * 搜索条件的拼装
     * @param productTypeAttrValueSet 
     * @param searchKeyword
     * @return
     */
    private String searchIndexAssembling(Integer cateId, Integer doSelf, Integer store,
                                         Integer brandId, String filterAll,
                                         Map<String, ProductTypeAttr> typeAttrMap,
                                         Map<String, String> attrFilter) {
        StringBuilder sb = new StringBuilder();

        // 类目ID
        sb.append(SearchProductVO.PRODUCTCATEID_);
        sb.append(":");
        sb.append(cateId);
        if (brandId > 0) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.BRANDID_);
            sb.append(":");
            sb.append(brandId);
        }
        // 是否自营
        if (doSelf.intValue() == 1) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.ISSELF_);
            sb.append(":");
            sb.append(doSelf);
        }
        // 是否有货
        if (store.intValue() == 1) {
            // 品牌ID
            sb.append(" AND ");
            sb.append(SearchProductVO.PRODUCTSTOCK_);
            sb.append(":[1 TO *]");
        }
        // 检索属性封装
        if (!StringUtil.isEmpty(filterAll, true)) {
            // 所有查询条件：1_1-2_2-3_3，1_1表示一个查询属性和值下标
            String[] filterAlls = filterAll.split("-");
            if (filterAlls.length > 0) {
                for (String idAndIndexStr : filterAlls) {
                    String[] idAndIndex = idAndIndexStr.split("_");
                    if (idAndIndex.length == 2) {
                        String typeAttrId = idAndIndex[0];
                        String typeAttrValueIndexStr = idAndIndex[1];
                        int typeAttrValueIndex = ConvertUtil.toInt(typeAttrValueIndexStr, 0);
                        // 该查询条件对应的属性对象
                        ProductTypeAttr productTypeAttr = typeAttrMap.get(typeAttrId);
                        if (productTypeAttr != null
                            && !StringUtil.isEmpty(productTypeAttr.getValue(), true)) {
                            // 根据下标找到对应的实际值

                            String value = productTypeAttr.getValue();
                            String[] values = value.split(",");
                            if (typeAttrValueIndex < values.length) {
                                String filterValue = values[typeAttrValueIndex];
                                // 把对应的值加入到查询条件中
                                sb.append(" AND ");
                                sb.append(SearchProductVO.ATTRVALUES_);
                                sb.append(":");
                                sb.append("\"," + filterValue + ",\"");
                                attrFilter.put(idAndIndexStr,
                                    productTypeAttr.getName() + ":" + filterValue);
                                typeAttrMap.remove(typeAttrId);
                            }
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 拼装列表页查询条件的VO对象
     * @param typeAttrMap
     * @return
     */
    private List<ProductTypeAttrVO> installProductAttr(Map<String, ProductTypeAttr> typeAttrMap) {
        List<ProductTypeAttrVO> productTypeAttrVOs = new ArrayList<ProductTypeAttrVO>();
        if (typeAttrMap != null && typeAttrMap.size() > 0) {
            Iterator<String> iterator = typeAttrMap.keySet().iterator();
            for (typeAttrMap.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();
                ProductTypeAttr productTypeAttr = typeAttrMap.get(key);
                ProductTypeAttrVO productTypeAttrVO = new ProductTypeAttrVO();
                productTypeAttrVO.setId(productTypeAttr.getId());
                productTypeAttrVO.setName(productTypeAttr.getName());
                productTypeAttrVO.setValue(productTypeAttr.getValue());

                String value = productTypeAttr.getValue();
                if (value != null && !"".equals(value)) {
                    List<String> values = new ArrayList<String>();
                    String[] str = value.split(",");
                    for (int i = 0; i < str.length; i++) {
                        values.add(str[i]);
                    }
                    productTypeAttrVO.setValues(values);
                }
                productTypeAttrVOs.add(productTypeAttrVO);
            }
        }
        return productTypeAttrVOs;
    }

    /**
     * 查询分类下面所关联的品牌
     * @param stack
     * @param brandIds
     */
    private void findBrandAll(Map<String, Object> stack, String brandIds) {
        List<ProductBrand> productBrands = productBrandReadDao.getByIds(brandIds);
        stack.put("productBrands", productBrands);
        List<String> productBrandNameFirsts = new ArrayList<String>();
        for (ProductBrand productBrand : productBrands) {
            if (!productBrandNameFirsts.contains(productBrand.getNameFirst())) {
                productBrandNameFirsts.add(productBrand.getNameFirst());
            }
        }
        Collections.sort(productBrandNameFirsts);
        stack.put("productBrandNameFirsts", productBrandNameFirsts);
    }

    /**
     * 查询三级分类、二级分类、一级分类
     * @param cateId
     * @param stack
     * @return 三级分类
     */
    private ProductCate findCateAll(Integer cateId, Map<String, Object> stack) {
        ProductCate productCate = productCateReadDao.get(cateId);
        if (productCate == null) {
            throw new BusinessException("传入的类目不正确不能为空");
        }
        stack.put("productCate", productCate);
        if (productCate.getPid().intValue() != 0) {
            ProductCate productCatePid = productCateReadDao.get(productCate.getPid());
            stack.put("productCatePid", productCatePid);

            List<ProductCate> productCate3s = productCateReadDao.getByPid(productCate.getPid());
            productCate3s.remove(productCate);
            stack.put("productCate3s", productCate3s);

            if (productCatePid.getPid() != 0) {
                ProductCate productCatePidPid = productCateReadDao.get(productCatePid.getPid());
                stack.put("productCatePidPid", productCatePidPid);

                List<ProductCate> productCate2s = productCateReadDao
                    .getByPid(productCatePid.getPid());
                productCate2s.remove(productCatePid);
                stack.put("productCate2s", productCate2s);
            }
        }
        return productCate;
    }

    /**
     * 根据分类查询列表页推荐的头部商品列表
     * @param cateId
     * @return
     */
    public List<Product> getProductListByCateIdTop(int cateId) {
        return productReadDao.getByCateIdTop(cateId, 4);
    }

    /**
     * 根据分类查询列表页推荐的左侧商品列表
     * @param cateId
     * @return
     */
    public List<Product> getProductListByCateIdLeft(int cateId) {
        return productReadDao.getByCateIdLeft(cateId);
    }

    /**
     * 查询二级分类下三级分类的商品
     * @param cateId
     * @return
     */
    public List<Product> getProductListByCateId2(int cateId) {
        return productReadDao.getByCateIdTop(cateId, 5);
    }

    /**
     * 搜索页面推荐商品信息头部
     * @return
     */
    public List<Product> getProductSearchByTop() {
        return productReadDao.getByCateIdTop(0, 4);
    }

    /**
     * 用户中心首页推荐商品
     * @return
     */
    public List<Product> getProductMemberByTop(int number) {
        return productReadDao.getByCateIdTop(0, number);
    }

    /**
     * 搜索页面推荐商品信息左部
     * @return
     */
    public List<Product> getProductSearchByLeft() {
        return productReadDao.getByCateIdLeft(0);
    }

    /**
      * 查询商家列表页 商品信息
     * @param start 
     * @param size
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @param sort 排序
     * @return
     */
    public List<Product> getProductListBySellerCateId(int start, int size, int cateId, int sellerId,
                                                      int sort) {
        StringBuilder sb = new StringBuilder();
        if (cateId != 0) {
            SellerCate sellerCate = sellerCateReadDao.get(cateId);
            if (sellerCate.getPid().intValue() != 0) {
                sb.append(sellerCate.getId());
            } else {
                List<SellerCate> listCate = sellerCateReadDao.getByPid(cateId, sellerId);
                sb.append(sellerCate.getId());
                for (SellerCate sellerCate2 : listCate) {
                    sb.append(",");
                    sb.append(sellerCate2.getId());
                }
            }
        }

        return productReadDao.getProductListBySellerCateId(start, size, sb.toString(), sellerId,
            sort);
    }

    /**
     * 根据商家商品分类统计商家商品
     * @param cateId 商家分类
     * @param sellerId 商家ID
     * @return
     */
    public Integer getProductListBySellerCateIdCount(int cateId, int sellerId) {
        StringBuilder sb = new StringBuilder();

        if (cateId != 0) {
            SellerCate sellerCate = sellerCateReadDao.get(cateId);
            if (sellerCate.getPid().intValue() != 0) {
                sb.append(sellerCate.getId());
            } else {
                List<SellerCate> listCate = sellerCateReadDao.getByPid(cateId, sellerId);
                sb.append(sellerCate.getId());
                for (SellerCate sellerCate2 : listCate) {
                    sb.append(",");
                    sb.append(sellerCate2.getId());
                }
            }
        }

        return productReadDao.getProductListBySellerCateIdCount(sb.toString(), sellerId);
    }

    /**
     * 根据品牌查询商品
     * @param brandId 品牌ID
     * @param sort 0:默认；1、销量从大到小；2、评价从多到少；3、价格从低到高；4、价格从高到低
     * @param doSelf 自营非自营
     * @param store 库存
     * @param pager
     * @return
     */
    public List<Product> getProductsByBrandId(Integer brandId, Integer sort, Integer doSelf,
                                              Integer store, Integer start, Integer size) {
        return productReadDao.getByBrandId(brandId, sort, doSelf, store, start, size);
    }

    /**
     * 根据品牌查询商品数量
     * @param brandId 品牌ID
     * @return
     */
    public Integer getProductsByBrandIdCount(Integer brandId, Integer doSelf, Integer store) {
        return productReadDao.getByBrandIdCount(brandId, doSelf, store);
    }

    /**
     * 根据品牌查询销量最好的商品4个
     * @param brandId 品牌ID
     * @return
     */
    public List<Product> getProductsByBrandIdTop(Integer brandId) {
        return productReadDao.getByBrandIdTop(brandId);
    }

    /**
     * 根据商品分类路径查询商品数量
     * @param productCatePath 分类路径如：/1/2
     * @return
     */
    public Integer getProductByPathCount(String productCatePath) {
        return productReadDao.getProductByPathCount(productCatePath);
    }

    /**
     * 根据商品分类路径查询商品信息
     * @param productCatePath 分类路径如：/1/2
     * @param sort 排序：0-默认，1-价格升序，2-价格降序，3-销量降序，4-评价降序
     * @param start
     * @param size
     * @return
     */
    public List<Product> getProductByPath(String productCatePath, int sort, int start, int size) {
        return productReadDao.getProductByPath(productCatePath, sort, start, size);
    }

    /**
     * 购物车推荐商品（购买了该商品的用户还购买了）
     * @param cateId
     * @param number
     * @return
     */
    public List<Product> getProductsListCart(int cateId, int number) {
        return productReadDao.getByCateIdTop(cateId, number);
    }

    /**
     * 根据分类的名称查询分类
     * @param name
     * @return
     */
    public ProductCate getProductCateByName(String name) {
        return productCateReadDao.getProductCateByName(name);
    }

    /**
     * 根据品牌的名称查询
     * @param name
     * @return
     */
    public ProductBrand getProductBrandByName(String name) {
        return productBrandReadDao.getProductBrandByName(name);
    }

    /**
     * 根据ID获取商品
     * @param productId
     * @return
     */
    public Product getProductById(Integer productId) {
        if (null == productId || 0 == productId)
            throw new BusinessException("根据商品ID获取商品表失败，商品ID为空");
        return productReadDao.get(productId);
    }

    /**
     * 根据分类ID查询分类信息
     * @param cateId
     * @return
     */
    public ProductCate getProductCateById(Integer cateId) {
        return productCateReadDao.get(cateId);
    }

    /**
     * 取得id的分类下面的子节点信息
     * @param pid
     * @return
     */
    public List<ProductCate> getProductCateByPid(Integer pid) {
        return productCateReadDao.getByPid(pid);
    }

    /**
     * 根据类型ID查询类型信息
     * @param id
     * @return
     */
    public ProductType getProductTypeById(Integer id) {
        return productTypeReadDao.get(id);
    }

    /**
     * 根据品牌的ID集合查询出品牌
     * @param brandIds
     * @return
     */
    public List<ProductBrand> getProductBrandByIds(String brandIds) {
        return productBrandReadDao.getByIds(brandIds);
    }

    /**
     * 根据类型ID查询类型下关联的查询属性
     * @param productTypeId
     * @return
     */
    public List<ProductTypeAttr> getByTypeIdAndQuery(Integer productTypeId) {
        return productTypeAttrReadDao.getByTypeIdAndQuery(productTypeId);
    }

    /**
     * 根据品牌ID查询品牌信息
     * @param brandId
     * @return
     */
    public ProductBrand getProductBrandById(int brandId) {
        return productBrandReadDao.getById(brandId);
    }

}
