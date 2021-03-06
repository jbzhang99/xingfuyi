package com.yixiekeji.model.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.TimeUtil;
import com.yixiekeji.dao.shop.read.product.ProductAttrReadDao;
import com.yixiekeji.dao.shop.read.product.ProductBrandReadDao;
import com.yixiekeji.dao.shop.read.product.ProductCateReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.search.SearchRecordReadDao;
import com.yixiekeji.dao.shop.read.search.SearchSettingReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dao.shop.write.search.SearchRecordWriteDao;
import com.yixiekeji.dao.shop.write.search.SearchSettingWriteDao;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductAttr;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.search.SearchRecord;
import com.yixiekeji.entity.search.SearchSetting;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.vo.search.SearchProductVO;

@Component(value = "solrProductModel")
public class SolrProductModel {

    @Resource
    private ProductReadDao        productReadDao;
    @Resource
    private ProductBrandReadDao   productBrandReadDao;
    @Resource
    private SearchSettingReadDao  searchSettingReadDao;
    @Resource
    private SearchSettingWriteDao searchSettingWriteDao;
    @Resource
    private SellerReadDao         sellerReadDao;
    @Resource
    private ProductCateReadDao    productCateReadDao;

    @Resource
    private SearchRecordReadDao   searchRecordReadDao;

    @Resource
    private SearchRecordWriteDao  searchRecordWriteDao;

    @Resource
    private ProductAttrReadDao    productAttrReadDao;

    /**
     * 创建索引
     * @return
     * @throws Exception 
     */
    public Boolean jobCreateIndexesSolr(String solrUrl, String solrServer) throws Exception {
        SearchSetting searchSetting = searchSettingReadDao.get(ConstantsEJS.SEARCHSETTINGID);
        List<SearchProductVO> searchProductVOs;
        if (searchSetting.getIndexProductId().intValue() == SearchSetting.INDEX_PRODUCT_ID_0) {//第一次创建索引
            int page = 500;//每次取500数据
            int count = productReadDao.getProductsCount();
            int number = count / page;

            Integer start = 0, size = 0;
            if (count != 0) {
                for (int i = 0; i <= number; i++) {
                    start = page * i;
                    if (i == number) {
                        size = count % page;
                        List<Product> products = productReadDao.getProducts(start, size);
                        searchProductVOs = getSearchProductVOs(products);
                        try {
                            createIndex(solrUrl, solrServer, searchProductVOs);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                    } else {
                        size = page;
                        List<Product> products = productReadDao.getProducts(start, size);
                        searchProductVOs = getSearchProductVOs(products);
                        try {
                            createIndex(solrUrl, solrServer, searchProductVOs);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw e;
                        }
                    }
                }
                productReadDao.getProducts(start, size);
            }

            //更新索引记录表操作时间已经已经创建索引
            searchSettingWriteDao.updateIndexProductId(ConstantsEJS.SEARCHSETTINGID,
                SearchSetting.INDEX_PRODUCT_ID_1);
        } else {//更新索引
            List<Product> products = productReadDao
                .getProductsUpdateTime(searchSetting.getIndexProductTime());

            List<Product> productsAdd = new ArrayList<Product>();//新加的索引
            List<Product> productsEdit = new ArrayList<Product>();//修改的索引
            for (Product product : products) {
                if (product.getSellerState().intValue() == 1 && product.getState().intValue() == 6
                    && product.getProductCateState().intValue() == 1
                    && product.getUpTime().getTime() < new Date().getTime()) {
                    productsAdd.add(product);
                } else {
                    if (product.getCreateTime().getTime() < searchSetting.getIndexProductTime()
                        .getTime()) { //创建时间比上次更新索引时间小，是下架的商品，需要删除索引
                        productsEdit.add(product);
                    }
                }
            }

            try {
                if (productsAdd.size() != 0) {
                    searchProductVOs = getSearchProductVOs(productsAdd);
                    createIndex(solrUrl, solrServer, searchProductVOs);
                }
                if (productsEdit.size() != 0) {
                    deleteByQuery(solrUrl, solrServer, productsEdit);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

            if (productsAdd.size() != 0 || productsEdit.size() != 0) {
                //更新索引记录表操作时间
                searchSettingWriteDao.updateIndexProductTime(ConstantsEJS.SEARCHSETTINGID);
            }
        }
        return true;
    }

    /**
     * 组装SearchProductVO对象，用来创建索引
     * @param products
     * @return
     */
    private List<SearchProductVO> getSearchProductVOs(List<Product> products) {
        List<SearchProductVO> searchProductVOs = new ArrayList<SearchProductVO>();
        SearchProductVO searchProductVO;
        for (Product product : products) {
            searchProductVO = new SearchProductVO();
            searchProductVO.setId(product.getId().toString());

            ProductBrand productBrand = productBrandReadDao.getById(product.getProductBrandId());
            searchProductVO.setBrandId(productBrand.getId().toString());
            searchProductVO.setBrandName(productBrand.getName());

            Seller seller = sellerReadDao.get(product.getSellerId());
            searchProductVO.setSellerId(seller.getId().toString());
            searchProductVO.setSellerName(seller.getSellerName());

            searchProductVO.setProductCateId(product.getProductCateId().toString());
            searchProductVO.setProductCatePath(product.getProductCatePath());
            ProductCate productCate = productCateReadDao.get(product.getProductCateId());
            searchProductVO.setCate(productCate.getName());

            searchProductVO.setName1(product.getName1());
            StringBuilder sb = new StringBuilder();
            sb.append(product.getName2());
            sb.append("-");
            sb.append(product.getKeyword());
            searchProductVO.setContent(sb.toString());

            searchProductVO.setMasterImg(product.getMasterImg());
            searchProductVO.setMallPcPrice(product.getMallPcPrice().toString());
            searchProductVO.setMalMobilePrice(product.getMalMobilePrice().toString());
            searchProductVO.setMarketPrice(product.getMarketPrice().toString());
            searchProductVO.setProductStock(product.getProductStock().toString());
            searchProductVO.setCommentsNumber(product.getCommentsNumber().toString());

            List<ProductAttr> sroductAttrs = productAttrReadDao
                .getQueryAttrByProductId(product.getId());
            StringBuilder attrIds = new StringBuilder();
            StringBuilder attrValues = new StringBuilder();
            attrIds.append(",");
            attrValues.append(",");
            for (ProductAttr productAttr : sroductAttrs) {
                attrIds.append(productAttr.getProductTypeAttrId()).append(",");
                if (!StringUtil.isEmpty(productAttr.getValue(), true)) {
                    attrValues.append(productAttr.getValue()).append(",");
                }
            }
            searchProductVO.setAttrIds(attrIds.toString());
            searchProductVO.setAttrValues(attrValues.toString());

            searchProductVO
                .setSalesNum(String.valueOf(product.getVirtualSales() + product.getActualSales()));
            searchProductVO.setIsSelf(product.getIsSelf().toString());
            searchProductVO.setSellerCateId(product.getSellerCateId().toString());

            searchProductVOs.add(searchProductVO);
        }
        return searchProductVOs;
    }

    /**
     * 获取 SolrClient 对象
     * @param URL
     * @param SERVER
     * @return
     */
    private SolrClient getSolrClient(String URL, String SERVER) {
        return new HttpSolrClient(URL + "/" + SERVER);
    }

    /**
     * 新建索引
     * @param URL
     * @param SERVER
     * @param searchProductVOs
     * @throws Exception 
     */
    private void createIndex(String URL, String SERVER,
                             List<SearchProductVO> searchProductVOs) throws Exception {
        SolrClient client = getSolrClient(URL, SERVER);
        List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
        for (SearchProductVO searchProductVO : searchProductVOs) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField(SearchProductVO.ID_, searchProductVO.getId());
            doc.addField(SearchProductVO.BRANDID_, searchProductVO.getBrandId());
            doc.addField(SearchProductVO.BRANDNAME_, searchProductVO.getBrandName());
            doc.addField(SearchProductVO.SELLERID_, searchProductVO.getSellerId());
            doc.addField(SearchProductVO.SELLERNAME_, searchProductVO.getSellerName());
            doc.addField(SearchProductVO.PRODUCTCATEID_, searchProductVO.getProductCateId());
            doc.addField(SearchProductVO.PRODUCTCATEPATH_, searchProductVO.getProductCatePath());
            doc.addField(SearchProductVO.CATE_, searchProductVO.getCate());
            doc.addField(SearchProductVO.NAME1_, searchProductVO.getName1());
            doc.addField(SearchProductVO.CONTENT_, searchProductVO.getContent());

            doc.addField(SearchProductVO.MASTERIMG_, searchProductVO.getMasterImg());
            doc.addField(SearchProductVO.MALLPCPRICE_, searchProductVO.getMallPcPrice());
            doc.addField(SearchProductVO.MALMOBILEPRICE_, searchProductVO.getMalMobilePrice());
            doc.addField(SearchProductVO.MARKETPRICE_, searchProductVO.getMarketPrice());
            doc.addField(SearchProductVO.PRODUCTSTOCK_, searchProductVO.getProductStock());
            doc.addField(SearchProductVO.COMMENTSNUMBER_, searchProductVO.getCommentsNumber());

            doc.addField(SearchProductVO.ATTRIDS_, searchProductVO.getAttrIds());
            doc.addField(SearchProductVO.ATTRVALUES_, searchProductVO.getAttrValues());
            doc.addField(SearchProductVO.SALESNUM_, searchProductVO.getSalesNum());
            doc.addField(SearchProductVO.ISSELF_, searchProductVO.getIsSelf());
            doc.addField(SearchProductVO.SELLERCATEID_, searchProductVO.getSellerCateId());

            docList.add(doc);
        }
        try {
            client.add(docList);
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 删除索引
     * @param URL
     * @param SERVER
     * @param products
     * @throws Exception
     */
    private void deleteByQuery(String URL, String SERVER, List<Product> products) throws Exception {
        SolrClient client = getSolrClient(URL, SERVER);
        try {
            for (Product product : products) {
                client.deleteById(product.getId().intValue() + "");
                client.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 更新敏感词的索引值
     * @return
     */
    public Boolean jobUpdateSearchRecordIndex(String solrUrl, String solrServer) {
        List<SearchRecord> searchRecords = searchRecordWriteDao.getSearchRecordsAll();
        for (SearchRecord searchRecord : searchRecords) {
            String searchKeyword = "(" + searchRecord.getKeyword() + ")";
            int countIndex = getSearchIndexCount(searchKeyword, solrUrl, solrServer);
            searchRecord.setKeywordIndex(countIndex);
            searchRecordWriteDao.update(searchRecord);
        }

        
        return true;
    }

    /**
     * 统计关键词的索引数量
     * @param searchKeyword
     * @return
     */
    private int getSearchIndexCount(String searchKeyword, String URL, String SERVER) {
        int count = 0;
        SolrClient client = getSolrClient(URL, SERVER);
        SolrQuery query = new SolrQuery();

        String searchIndexAssemblingString = SearchProductVO.searchIndexAssembling(searchKeyword);
        query.setQuery(searchIndexAssemblingString);

        QueryResponse response = null;
        try {
            response = client.query(query);
            SolrDocumentList docs = response.getResults();
            count = new Integer(docs.getNumFound() + "");
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
