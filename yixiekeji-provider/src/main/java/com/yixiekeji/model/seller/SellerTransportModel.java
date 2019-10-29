package com.yixiekeji.model.seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerTransportReadDao;
import com.yixiekeji.dao.shop.write.seller.SellerTransportWriteDao;
import com.yixiekeji.entity.cart.Cart;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductGoods;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.vo.seller.TransportJson;

@Component(value = "sellerTransportModel")
public class SellerTransportModel {

    @Resource
    private SellerTransportWriteDao      sellerTransportWriteDao;
    @Resource
    private SellerTransportReadDao       sellerTransportReadDao;
    @Resource
    private DataSourceTransactionManager transactionManager;
    @Resource
    private ProductReadDao               productReadDao;

    private static org.slf4j.Logger      log = org.slf4j.LoggerFactory
        .getLogger(SellerTransportModel.class);

    /**
    * 根据id取得卖家运费模板
    * @param  sellerTransportId
    * @return
    */
    public SellerTransport getSellerTransportById(Integer sellerTransportId) {
        return sellerTransportWriteDao.get(sellerTransportId);
    }

    /**
     * 保存卖家运费模板
     * @param  sellerTransport
     * @return
     */
    public Integer saveSellerTransport(SellerTransport sellerTransport) {
        return sellerTransportWriteDao.save(sellerTransport);
    }

    /**
    * 更新卖家运费模板
    * @param  sellerTransport
    * @return
    */
    public Integer updateSellerTransport(SellerTransport sellerTransport) {
        return sellerTransportWriteDao.update(sellerTransport);
    }

    public Integer pageCount(Map<String, Object> queryMap) {
        return sellerTransportWriteDao.getCount(queryMap);
    }

    public List<SellerTransport> page(Map<String, Object> queryMap) {
        return sellerTransportWriteDao.page(queryMap);
    }

    public List<SellerTransport> getEffectTransportBySellerId(Integer sellerId) {
        return sellerTransportReadDao.getEffectTransportBySellerId(sellerId);
    }

    public boolean del(Integer sellerId, Integer id) {

        if (id == null) {
            throw new BusinessException("删除卖家运费模板[" + id + "]时出错");
        }

        SellerTransport dbTransport = sellerTransportReadDao.get(id);

        if (dbTransport == null) {
            throw new BusinessException("获取运费模板失败，请重试");
        }
        if (!dbTransport.getSellerId().equals(sellerId)) {
            throw new BusinessException("您无权操作该数据");
        }

        if (dbTransport.getState() == SellerTransport.STATE_1) {
            throw new BusinessException("启用状态下的模板不能删除");
        }
        //查询该模板下关联的商品数量
        Integer productNum = productReadDao.getProNumByTransportId(id);

        if (productNum > 0) {
            throw new BusinessException("已有商品使用该运费模板，不能进行删除");
        }

        return sellerTransportWriteDao.del(id) > 0;
    }

    /**
     * 组装运费信息
     * @param st
     * @return
     */
    public List<TransportJson> assembleTransportInfo(SellerTransport st) {
        //{city_id=-1, city_name=全国, trans_add_fee=5.0, trans_fee=12.0, trans_add_weight=1.0, trans_weight=1.0, type=平邮}

        List<TransportJson> list = new ArrayList<TransportJson>();
        //平邮
        Integer mail = st.getTransMail();
        //快递
        Integer express = st.getTransExpress();
        //EMS
        Integer ems = st.getTransEms();

        if (mail != null && mail.intValue() == ConstantsEJS.YES_NO_1) {
            String mailInfo = st.getTransMailInfo();
            Gson gson = new Gson();
            List<TransportJson> data = gson.fromJson(mailInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson json : data) {
                json.setType("平邮");
                list.add(json);
            }
        }

        if (express != null && express.intValue() == ConstantsEJS.YES_NO_1) {
            String expressInfo = st.getTransExpressInfo();
            Gson gson = new Gson();
            List<TransportJson> data = gson.fromJson(expressInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson json : data) {
                json.setType("快递");
                list.add(json);
            }
        }

        if (ems != null && ems.intValue() == ConstantsEJS.YES_NO_1) {
            String emsInfo = st.getTransEmsInfo();
            Gson gson = new Gson();
            List<TransportJson> data = gson.fromJson(emsInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson json : data) {
                json.setType("EMS");
                list.add(json);
            }
        }

        return list;
    }

    /**
     * 计算购物车中的运费方法
     * @param sellercartList
     * @param cityId
     * @return
     */
    public BigDecimal calculateTransFee(List<Cart> sellercartList, Integer cityId) {
        BigDecimal tatalprice = BigDecimal.ZERO;

        // 运费模板，保存本次计算需要用到的运费模板：key=模板ID，value=模板对象
        Map<String, SellerTransport> tranMap = new HashMap<String, SellerTransport>();
        // 运费模板对应的数量（重量、件数）：key=模板ID，value=使用该模板计算运费的数量
        Map<String, BigDecimal> numMap = new HashMap<String, BigDecimal>();

        // 统计本次计算中的模板和数量
        for (Cart cart : sellercartList) {
            if (cart.getProduct() == null || cart.getProduct().getTransportId() == null
                || cart.getProduct().getTransportId().equals("")) {
                log.error("商品[" + cart.getProduct().getName1() + "]没有设置运费模板");
                continue;
            }
            String tranId = cart.getProduct().getTransportId().toString();

            SellerTransport tp = null;
            if (tranMap.get(tranId) == null) {
                // 如果tranMap中没有，则从数据库取
                tp = sellerTransportReadDao.get(cart.getProduct().getTransportId());
                tranMap.put(tranId, tp);
                // 初始化模板对应的数量
                numMap.put(tranId, BigDecimal.ZERO);
            } else {
                // 如果tranMap中已有，则直接从map中取
                tp = tranMap.get(tranId);
            }
            if (tp == null) {
                log.error("商品[" + cart.getProduct().getName1() + "]没有设置运费模板");
                continue;
            }
            if (tp.getState().intValue() == SellerTransport.STATE_2) {
                log.error("商品[" + cart.getProduct().getName1() + "]设置的运费模板为禁用状态");
                continue;
            }

            //件数/重量
            BigDecimal num = BigDecimal.ZERO;
            if (cart.getProduct().getTransportType() == null) {
                log.error("商品[" + cart.getProduct().getName1() + "]模板类型有误");
                continue;
            }
            if (cart.getProduct().getTransportType() == Product.TRANSPORT_TYPE_2) {
                // 取得的模板类型和商品的运费类型匹配
                if (tp.getTransType() != SellerTransport.TRANS_TYPE_2) {
                    log.error("sku信息错误：[" + cart.getProductGoods().getSku()
                              + "]设置的运费模板和实际模板的计算类型不一致，跳过运费计算");
                    continue;
                }
                //重量模板，取sku重量
                if (cart.getProductGoods().getWeight() == null) {
                    log.error("sku信息错误：[" + cart.getProductGoods().getSku() + "]没有设置重量，跳过运费计算");
                    continue;
                }
                num = (cart.getProductGoods().getWeight())
                    .multiply(new BigDecimal(cart.getCount()));
            } else if (cart.getProduct().getTransportType() == Product.TRANSPORT_TYPE_3) {
                // 取得的模板类型和商品的运费类型匹配
                if (tp.getTransType() != SellerTransport.TRANS_TYPE_2 || tp.getTransRatio() <= 0) {
                    log.error("sku信息错误：[" + cart.getProductGoods().getSku()
                              + "]设置的运费模板和实际模板的计算类型不一致，跳过运费计算");
                    continue;
                }

                //体积转换为重量
                BigDecimal length = new BigDecimal(cart.getProductGoods().getLength());
                BigDecimal width = new BigDecimal(cart.getProductGoods().getWidth());
                BigDecimal height = new BigDecimal(cart.getProductGoods().getHeight());
                if (length == null || width == null || height == null) {
                    log.error("sku信息错误：[" + cart.getProductGoods().getSku() + "]长宽高信息错误，跳过运费计算");
                    continue;
                }
                //长（cm）×宽(cm)×高(cm)÷运费模板换算比例=重量（KG）
                num = length.multiply(width).multiply(height)
                    .multiply(new BigDecimal(cart.getCount()))
                    .divide(new BigDecimal(tp.getTransRatio()), 3, BigDecimal.ROUND_HALF_UP);
                log.debug("[" + cart.getProductGoods().getSku() + "]长×宽×高x数量×运费模板换算比例计算结果：" + num);
            } else {
                //件数模板，即为sku数量
                num = new BigDecimal(cart.getCount());
            }

            // 记录数量和
            numMap.put(tranId, numMap.get(tranId).add(num));
        }

        // 计算每个模板的yunfe
        Iterator<String> tranItr = tranMap.keySet().iterator();
        while (tranItr.hasNext()) {
            String tranId = tranItr.next();
            SellerTransport tp = tranMap.get(tranId);
            if (tp == null) {
                continue;
            }
            BigDecimal numBig = numMap.get(tranId);
            if (numBig == null || numBig.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            // 数量向上取整
            int num = (numBig.setScale(0, BigDecimal.ROUND_UP)).intValue();

            BigDecimal currentprice = BigDecimal.ZERO;
            Gson gson = new Gson();

            List<TransportJson> feelist = null;
            Map<String, Object> mailRlt = null;
            Map<String, Object> expressRlt = null;
            Map<String, Object> emsRlt = null;

            // 运费计算标志，如果计算出来了就赋值true
            boolean feeFlg = false;

            // 价格计算优先级： 平邮 > 快递 > EMS
            if (tp.getTransMail() != null && tp.getTransMail() == 1) {
                feelist = gson.fromJson(tp.getTransMailInfo(),
                    new TypeToken<ArrayList<TransportJson>>() {
                    }.getType());
                mailRlt = calculationPrice(feelist, num, cityId);
                // 如果平邮计算出了运费信息，则返回
                String feeFlgStr = (String) mailRlt.get("feeFlg");
                if ("true".equals(feeFlgStr)) {
                    feeFlg = true;
                    currentprice = (BigDecimal) mailRlt.get("price");
                }
            } else if (tp.getTransExpress() != null && tp.getTransExpress() == 1) {
                feelist = gson.fromJson(tp.getTransExpressInfo(),
                    new TypeToken<ArrayList<TransportJson>>() {
                    }.getType());
                expressRlt = calculationPrice(feelist, num, cityId);
                // 如果快递计算出了运费信息，则返回
                String feeFlgStr = (String) expressRlt.get("feeFlg");
                if ("true".equals(feeFlgStr)) {
                    feeFlg = true;
                    currentprice = (BigDecimal) expressRlt.get("price");
                }
            } else if (tp.getTransEms() != null && tp.getTransEms() == 1) {
                feelist = gson.fromJson(tp.getTransEmsInfo(),
                    new TypeToken<ArrayList<TransportJson>>() {
                    }.getType());

                emsRlt = calculationPrice(feelist, num, cityId);
                // 如果EMS计算出了运费信息，则返回
                String feeFlgStr = (String) emsRlt.get("feeFlg");
                if ("true".equals(feeFlgStr)) {
                    feeFlg = true;
                    currentprice = (BigDecimal) emsRlt.get("price");
                }
            }

            if (!feeFlg) {
                // 如果程序运行到此处，说明还没有计算出运费，则根据 平邮 > 快递 > EMS 的优先顺序计算全国的运费
                if (mailRlt != null && mailRlt.get("allArea") != null) {
                    TransportJson json = (TransportJson) mailRlt.get("allArea");
                    currentprice = this.getFee(json, num);
                } else if (expressRlt != null && expressRlt.get("allArea") != null) {
                    TransportJson json = (TransportJson) expressRlt.get("allArea");
                    currentprice = this.getFee(json, num);
                } else if (emsRlt != null && emsRlt.get("allArea") != null) {
                    TransportJson json = (TransportJson) emsRlt.get("allArea");
                    currentprice = this.getFee(json, num);
                }
            }

            //该商品的运费
            tatalprice = tatalprice.add(currentprice);
        }

        return tatalprice;
    }

    /**
     * 计算运费
     * @param feelist
     * @param num
     * @param areaid
     * @return
     */
    private Map<String, Object> calculationPrice(List<TransportJson> feelist, int num,
                                                 Integer cityId) {
        Map<String, Object> result = new HashMap<String, Object>();
        BigDecimal price = BigDecimal.ZERO;
        // 运费计算标志，如果计算出来了就赋值true
        boolean feeFlg = false;
        for (TransportJson json : feelist) {
            String[] arr = json.getCity_id().split(",");
            for (String city : arr) {
                Integer cityid = ConvertUtil.toInt(city, 0);
                if (cityid == -1) {
                    // 全国，记录下全国的运费，如果没有区域匹配则调用全国的运费计算方法
                    result.put("allArea", json);
                    continue;
                } else if (cityid.equals(cityId)) {
                    // 如果传入的城市ID与当前的相等，表示订单地址符合当前运费信息计算方法
                    price = this.getFee(json, num);
                    feeFlg = true;
                    break;
                }
            }
            // 如果有区域匹配则跳出循环，返回运费
            if (feeFlg) {
                result.put("price", price);
                result.put("feeFlg", "true");
                break;
            }
        }
        return result;
    }

    /**
     * 根据运费信息计算运费
     * @param json
     * @param num
     * @return
     */
    private BigDecimal getFee(TransportJson json, int num) {
        BigDecimal price = BigDecimal.ZERO;
        int weight = json.getTrans_weight().intValue();
        Double fee = json.getTrans_fee();
        int addWeight = json.getTrans_add_weight().intValue();
        Double addFee = json.getTrans_add_fee();

        //多出的件数/重量
        int surplus = (num - weight) > 0 ? (num - weight) : 0;

        //首件的钱
        price = price.add(new BigDecimal(fee));
        // 增加件的钱
        BigDecimal addFeeDec = new BigDecimal(addFee);
        // 只有当增加数量大于0才计算，否则会出现是循环
        if (addWeight > 0 && surplus > 0 && addFeeDec.compareTo(BigDecimal.ZERO) > 0) {
            //计算多出的钱
            for (int i = 0; i < surplus; i += addWeight) {
                price = price.add(addFeeDec);
            }
        }
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 根据运费末班ID启用模板
     * @param id
     * @param state 
     * @return
     * @throws Exception
     */
    public Boolean transportInUse(Integer id, Integer state) throws Exception {

        //如果是禁用操作，判断该模板下是否有已经上架商品，如果存在则不能禁用
        if (state.intValue() == SellerTransport.STATE_2) {
            Integer productCount = productReadDao.getProNumByTransportIdAndState(id,
                Product.STATE_6);
            if (productCount > 0) {
                throw new BusinessException("该模板下存在上架商品，不能禁用");
            }
        }

        return sellerTransportWriteDao.updateInUseById(id, state) > 0;
    }

    /**
     * 根据商品运费类型获取运费模板
     * @param transportType
     * @param sellerId
     * @return
     */
    public List<SellerTransport> getTransportByTypeAndSellerId(Integer transportType,
                                                               Integer sellerId) {
        return sellerTransportReadDao.getByTransTypeAndSellerId(transportType, sellerId);
    }

    /**
     * 单个商品计算运费(活动)
     * @param product
     * @param productGoods
     * @param number
     * @param cityId
     * @return
     */
    public BigDecimal calculateTransFee(Product product, ProductGoods productGoods, Integer number,
                                        Integer cityId) {

        // 构造一个假的购物车用于调用同一个计算接口
        List<Cart> list = new ArrayList<Cart>();
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setProductGoods(productGoods);
        cart.setCount(number);
        list.add(cart);
        return this.calculateTransFee(list, cityId);
    }

    /**
     * 计算商品运费
     * @param productId
     * @param cityId
     * @param num 
     * @return
     */
    public BigDecimal getTransFee(Integer productId, Integer cityId, Integer productNum) {
        BigDecimal currentprice = BigDecimal.ZERO;

        //获取商品信息
        Product product = productReadDao.get(productId);
        if (product == null) {
            throw new BusinessException("获取商品信息失败，请重试");
        }

        if (product.getTransportId() == null || product.getTransportId().equals("")
            || product.getTransportId().intValue() == 0
            || product.getTransportId().intValue() == -1) {
            throw new BusinessException("没有设置运费模板");
        }

        SellerTransport tp = sellerTransportReadDao.get(product.getTransportId());
        if (tp == null) {
            throw new BusinessException("没有设置运费模板");
        }
        if (tp.getState().intValue() == SellerTransport.STATE_2) {
            throw new BusinessException("运费模板为禁用状态");
        }

        //件数/重量
        BigDecimal numBig = BigDecimal.ZERO;
        if (product.getTransportType() == null) {
            throw new BusinessException("商品模板类型有误");
        }
        if (product.getTransportType() == Product.TRANSPORT_TYPE_2) {
            // 取得的模板类型和商品的运费类型匹配
            if (tp.getTransType() != SellerTransport.TRANS_TYPE_2) {
                throw new BusinessException("商品设置的运费模板和实际模板的计算类型不一致");
            }
            //重量模板，取sku重量
            if (product.getWeight() == null) {
                throw new BusinessException("商品没有设置重量");
            }
            numBig = (product.getWeight()).multiply(new BigDecimal(productNum));
        } else if (product.getTransportType() == Product.TRANSPORT_TYPE_3) {
            // 取得的模板类型和商品的运费类型匹配
            if (tp.getTransType() != SellerTransport.TRANS_TYPE_2 || tp.getTransRatio() <= 0) {
                throw new BusinessException("商品设置的运费模板和实际模板的计算类型不一致");
            }

            //体积转换为重量
            BigDecimal length = new BigDecimal(product.getLength());
            BigDecimal width = new BigDecimal(product.getWidth());
            BigDecimal height = new BigDecimal(product.getHeight());
            if (length == null || width == null || height == null) {
                throw new BusinessException("商品设置长宽高信息错误");
            }
            //长（cm）×宽(cm)×高(cm)÷运费模板换算比例=重量（KG）
            numBig = length.multiply(width).multiply(height).multiply(new BigDecimal(productNum))
                .divide(new BigDecimal(tp.getTransRatio()), 3, BigDecimal.ROUND_HALF_UP);
        } else {
            //件数模板，即为sku数量
            numBig = new BigDecimal(productNum);
        }

        // 计算每个模板的yunfe
        if (numBig == null || numBig.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        // 数量向上取整
        int num = (numBig.setScale(0, BigDecimal.ROUND_UP)).intValue();

        Gson gson = new Gson();

        List<TransportJson> feelist = null;
        Map<String, Object> mailRlt = null;
        Map<String, Object> expressRlt = null;
        Map<String, Object> emsRlt = null;

        // 运费计算标志，如果计算出来了就赋值true
        boolean feeFlg = false;

        // 价格计算优先级： 平邮 > 快递 > EMS
        if (tp.getTransMail() != null && tp.getTransMail() == 1) {
            feelist = gson.fromJson(tp.getTransMailInfo(),
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            mailRlt = calculationPrice(feelist, num, cityId);
            // 如果平邮计算出了运费信息，则返回
            String feeFlgStr = (String) mailRlt.get("feeFlg");
            if ("true".equals(feeFlgStr)) {
                feeFlg = true;
                currentprice = (BigDecimal) mailRlt.get("price");
            }
        } else if (tp.getTransExpress() != null && tp.getTransExpress() == 1) {
            feelist = gson.fromJson(tp.getTransExpressInfo(),
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            expressRlt = calculationPrice(feelist, num, cityId);
            // 如果快递计算出了运费信息，则返回
            String feeFlgStr = (String) expressRlt.get("feeFlg");
            if ("true".equals(feeFlgStr)) {
                feeFlg = true;
                currentprice = (BigDecimal) expressRlt.get("price");
            }
        } else if (tp.getTransEms() != null && tp.getTransEms() == 1) {
            feelist = gson.fromJson(tp.getTransEmsInfo(),
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());

            emsRlt = calculationPrice(feelist, num, cityId);
            // 如果EMS计算出了运费信息，则返回
            String feeFlgStr = (String) emsRlt.get("feeFlg");
            if ("true".equals(feeFlgStr)) {
                feeFlg = true;
                currentprice = (BigDecimal) emsRlt.get("price");
            }
        }
        if (!feeFlg) {
            // 如果程序运行到此处，说明还没有计算出运费，则根据 平邮 > 快递 > EMS 的优先顺序计算全国的运费
            if (mailRlt != null && mailRlt.get("allArea") != null) {
                TransportJson json = (TransportJson) mailRlt.get("allArea");
                currentprice = this.getFee(json, num);
            } else if (expressRlt != null && expressRlt.get("allArea") != null) {
                TransportJson json = (TransportJson) expressRlt.get("allArea");
                currentprice = this.getFee(json, num);
            } else if (emsRlt != null && emsRlt.get("allArea") != null) {
                TransportJson json = (TransportJson) emsRlt.get("allArea");
                currentprice = this.getFee(json, num);
            }
        }

        return currentprice;
    }
}
