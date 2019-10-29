package com.yixiekeji.model.report;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.dao.analysis.read.BrowseLogReadDao;
import com.yixiekeji.dao.shop.read.order.OrdersReadDao;
import com.yixiekeji.dao.shop.read.product.ProductReadDao;
import com.yixiekeji.dao.shop.read.seller.SellerReadDao;
import com.yixiekeji.dto.OrdersReturnDto;
import com.yixiekeji.dto.PVDto;
import com.yixiekeji.dto.PhurchaseRateDto;
import com.yixiekeji.dto.ProductSaleDto;
import com.yixiekeji.echarts.component.SeriesData;
import com.yixiekeji.entity.order.Orders;
import com.yixiekeji.entity.product.Product;

/**
 * 统计Model
 *                       
 * @Filename: StatisticsModel.java
 * @Version: 1.0
 * @Author: zhaihl
 * @Email: zhaihl_0@126.com
 *
 */
@Component(value = "statisticsModel")
public class StatisticsModel {
    @Resource
    private OrdersReadDao    ordersReadDao;
    @Resource
    private SellerReadDao    sellerReadDao;
    @Resource
    private BrowseLogReadDao browseLogReadDao;
    @Resource
    private ProductReadDao   productReadDao;

    public List<Orders> getOrders(Map<String, String> queryMap, Integer start,
                                  Integer size) throws Exception {
        Map<String, Object> parammap = new HashMap<String, Object>(queryMap);
        List<Orders> list = ordersReadDao.getOrders(parammap, start, size);
        return list;
    }

    /**
     * 订单概况统计数据
     * @param queryMap 参数map
     * @return
     * @throws Exception
     */
    public List<SeriesData> getOrderOverviewData(Map<String, String> queryMap) throws Exception {
        //所有订单,不分页
        queryMap.put("q_isParent", "0");
        List<Orders> orderlist = getOrders(queryMap, -1, -1);
        //订单状态：1、未付款的订单；2、待确认的订单；3、待发货的订单；4、已发货的订单；5、已完成的订单；6、取消的订单
        List<SeriesData> datalist = new ArrayList<SeriesData>();
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        if (orderlist != null && orderlist.size() > 0) {
            for (Orders vo : orderlist) {
                //按订单状态分组统计
                switch (vo.getOrderState()) {
                    case Orders.ORDER_STATE_1:
                        num1++;
                        break;
                    case Orders.ORDER_STATE_2:
                        num2++;
                        break;
                    case Orders.ORDER_STATE_3:
                        num3++;
                        break;
                    case Orders.ORDER_STATE_4:
                        num4++;
                        break;
                    case Orders.ORDER_STATE_5:
                        num5++;
                        break;
                    case Orders.ORDER_STATE_6:
                        num6++;
                        break;
                    default:
                        break;
                }
            }
            SeriesData data1 = new SeriesData();
            data1.setName("未付款的订单");
            data1.setValue(num1);
            SeriesData data2 = new SeriesData();
            data2.setName("待确认的订单");
            data2.setValue(num2);
            SeriesData data3 = new SeriesData();
            data3.setName("待发货的订单");
            data3.setValue(num3);
            SeriesData data4 = new SeriesData();
            data4.setName("已发货的订单");
            data4.setValue(num4);
            SeriesData data5 = new SeriesData();
            data5.setName("已完成的订单");
            data5.setValue(num5);
            SeriesData data6 = new SeriesData();
            data6.setName("取消的订单");
            data6.setValue(num6);

            datalist.add(data1);
            datalist.add(data2);
            datalist.add(data3);
            datalist.add(data4);
            datalist.add(data5);
            datalist.add(data6);
        }
        return datalist;
    }

    /**
     * 退货率=退货总数/订单总数<i>(统计均为已完成的订单)</i>
     * @param queryMap 参数
     * @param model
     * @return
     */
    public Map<String, List<Object>> goodsReturnRate(Map<String, String> queryMap) {
        String model = queryMap.get("model");
        List<OrdersReturnDto> orderlist = ordersReadDao.goodsReturnRate(queryMap);

        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        List<Object> goodsreturnNumList = new ArrayList<Object>();
        List<Object> orderNumList = new ArrayList<Object>();
        List<Object> rateList = new ArrayList<Object>();
        DecimalFormat df = new DecimalFormat("###.##");
        Calendar cal = Calendar.getInstance();
        //最大月份/天
        int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
            : cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= max; i++) {
            int goodsreturnNum = 0;
            int orderNum = 0;
            double rate = 0;

            for (OrdersReturnDto or : orderlist) {
                //只创建一次实例
                cal.clear();
                cal.setTime(or.getFinishTime());
                int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                    : cal.get(Calendar.DAY_OF_MONTH);
                if (i == scale) {
                    //退款到账户
                    if (or.getStateMoney() != null && or.getStateMoney() == 2) {
                        goodsreturnNum += 1;
                    }
                    orderNum += 1;
                }
            }
            //退货率=退货总数/订单总数
            rate = orderNum == 0 ? 0
                : (new BigDecimal(goodsreturnNum).divide(new BigDecimal(orderNum), 2,
                    BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100)).doubleValue();

            goodsreturnNumList.add(goodsreturnNum);
            orderNumList.add(orderNum);
            rateList.add(df.format(rate));

        }
        map.put("goodsreturnNum", goodsreturnNumList);
        map.put("orderNum", orderNumList);
        map.put("rate", rateList);

        return map;
    }

    /**
     * 人均消费 = 订单总金额/会员数（购买商品的会员总数）
     * @param queryMap 参数
     * @return
     */
    public Map<String, List<Object>> getCPIstatistics(Map<String, String> queryMap) {
        String model = queryMap.get("model");

        Map<String, Object> parammap = new HashMap<String, Object>(queryMap);
        if (queryMap.get("s_status") != null) {
            parammap.put("s_status", Arrays.asList(queryMap.get("s_status").split(",")));
        }
        List<Orders> orderlist = ordersReadDao.getOrders(parammap, -1, -1);

        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        List<Object> orderMoneyList = new ArrayList<Object>();
        List<Object> userList = new ArrayList<Object>();
        List<Object> rateList = new ArrayList<Object>();
        DecimalFormat df = new DecimalFormat("###.##");

        Calendar cal = Calendar.getInstance();
        int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
            : cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //购买商品的会员只算不同会员,即同一会员下多单,则只计为一个会员 
        Map<Integer, Integer> usermap = new HashMap<Integer, Integer>();

        for (int i = 1; i <= max; i++) {
            //避免创建多个对象,每次只清空而不是new
            if (usermap.size() > 0) {
                usermap.clear();
            }
            double orderMoney = 0;
            int user = 0;
            double rate = 0;

            for (Orders or : orderlist) {
                cal.clear();
                cal.setTime(or.getFinishTime());
                int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                    : cal.get(Calendar.DAY_OF_MONTH);
                if (i == scale) {
                    orderMoney = new BigDecimal(orderMoney).add(or.getMoneyOrder()).doubleValue();
                    if (usermap.get(or.getMemberId()) == null) {
                        user += 1;
                    }
                    usermap.put(or.getMemberId(), or.getMemberId());
                }
            }
            //人均消费 = 订单总金额/会员数
            rate = user == 0 ? 0
                : (new BigDecimal(orderMoney).divide(new BigDecimal(user), 2,
                    BigDecimal.ROUND_HALF_UP)).doubleValue();

            orderMoneyList.add(orderMoney);
            userList.add(user);
            rateList.add(df.format(rate));

        }
        map.put("orderMoney", orderMoneyList);
        map.put("user", userList);
        map.put("rate", rateList);

        return map;
    }

    /**
     * 销售统计数据<br>
     * 客单价 = 订单总金额/订单总数
     * @param querymap
     * @return
     */
    public Map<String, List<Object>> getSaleStatistics(Map<String, String> queryMap) {
        String model = queryMap.get("model");

        List<Orders> orderlist = getDuleOrdersWithNoPage(queryMap);

        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        List<Object> orderMoneyList = new ArrayList<Object>();
        List<Object> orderNumList = new ArrayList<Object>();
        List<Object> kdjList = new ArrayList<Object>();
        DecimalFormat df = new DecimalFormat("###.##");
        Calendar cal = Calendar.getInstance();
        //最大月份/天
        int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
            : cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= max; i++) {
            double orderMoney = 0;
            int orderNum = 0;
            double kdj = 0;

            for (Orders or : orderlist) {
                //只创建一次实例
                cal.clear();
                cal.setTime(or.getFinishTime());
                int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                    : cal.get(Calendar.DAY_OF_MONTH);

                if (i == scale) {
                    orderNum++;
                    orderMoney = new BigDecimal(orderMoney).add(or.getMoneyOrder()).doubleValue();
                }

            }
            //客单价 = 订单总金额/订单总数
            kdj = orderMoney / orderNum;

            orderMoneyList.add(orderMoney);
            orderNumList.add(orderNum);
            kdjList.add(df.format(kdj));

        }
        map.put("orderMoney", orderMoneyList);
        map.put("orderNum", orderNumList);
        map.put("kdj", kdjList);

        return map;
    }

    /**
     * 商品销量
     * @param queryMap
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public List<ProductSaleDto> getProductSale(Map<String, String> queryMap) throws IllegalAccessException,
                                                                             InvocationTargetException,
                                                                             NoSuchMethodException {
        try {
            String model = queryMap.get("model");
            if (model == null) {
                throw new BusinessException("请选择统计模式");
            }
            if (queryMap.get("productId") == null || "".equals(queryMap.get("productId"))) {
                throw new BusinessException("请选择商品");
            }

            Map<String, Object> parammap = new HashMap<String, Object>(queryMap);
            if (queryMap.get("status") != null) {
                parammap.put("s_status", Arrays.asList(queryMap.get("status").split(",")));
            }
            List<ProductSaleDto> datalist = ordersReadDao.getProductSale(parammap);

            Calendar cal = Calendar.getInstance();
            int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
                : cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            Product product = productReadDao.get(ConvertUtil.toInt(queryMap.get("productId"), 0));

            List<ProductSaleDto> resultList = new ArrayList<>();
            if (datalist == null || datalist.size() == 0) {
                ProductSaleDto result = new ProductSaleDto();
                result.setProductName(product.getName1());
                result.setIsNorm(product.getIsNorm());
                result.setSpecInfo("");
                List<Object> data = new ArrayList<Object>(max);
                // 每个值都初始化成0
                for (int i = 0; i < max; i++) {
                    data.add(BigDecimal.ZERO);
                }
                result.setData(data);
                resultList.add(result);
                return resultList;
            }
            if (product.getIsNorm() == Product.IS_NORM_1) {
                // 没有规格则只有一条曲线
                ProductSaleDto result = new ProductSaleDto();
                result.setProductName(product.getName1());
                result.setIsNorm(product.getIsNorm());
                result.setSpecInfo("");
                List<Object> data = new ArrayList<Object>(max);
                // 每个值都初始化成0
                for (int i = 0; i < max; i++) {
                    data.add(BigDecimal.ZERO);
                }
                if (datalist != null && datalist.size() > 0) {
                    for (ProductSaleDto dto : datalist) {
                        Integer index = null;
                        // 当前的月份或者日期
                        if ("year".equals(model)) {
                            index = ConvertUtil.toInt(dto.getCreateTime().substring(5), null) - 1;
                        } else if ("month".equals(model)) {
                            index = ConvertUtil.toInt(dto.getCreateTime().substring(8), null) - 1;
                        }
                        if (index != null && index <= max) {
                            data.set(index, dto.getMoneyAmount());
                        }
                    }
                    result.setData(data);
                    resultList.add(result);
                }
            } else {
                // 如果启用了规格，则每个规格都有一条曲线
                // key为规格加createTime，值为对应的对象
                Map<String, ProductSaleDto> map = new HashMap<String, ProductSaleDto>();
                if (datalist != null && datalist.size() > 0) {
                    for (ProductSaleDto dto : datalist) {
                        Integer index = null;
                        // 当前的月份或者日期
                        if ("year".equals(model)) {
                            index = ConvertUtil.toInt(dto.getCreateTime().substring(5), null) - 1;
                        } else if ("month".equals(model)) {
                            index = ConvertUtil.toInt(dto.getCreateTime().substring(8), null) - 1;
                        }

                        if (map.get(dto.getSpecInfo() + dto.getCreateTime()) != null) {
                            // 存在则取已存在的对象
                            ProductSaleDto result = map
                                .get(dto.getSpecInfo() + dto.getCreateTime());
                            List<Object> data = result.getData();
                            if (index != null && index <= max) {
                                data.set(index, dto.getMoneyAmount());
                            }
                        } else {
                            // 还不存在则创建
                            ProductSaleDto result = new ProductSaleDto();
                            result.setProductName(product.getName1());
                            result.setIsNorm(product.getIsNorm());
                            result.setSpecInfo(dto.getSpecInfo());
                            List<Object> data = new ArrayList<Object>(max);
                            // 每个值都初始化成0
                            for (int i = 0; i < max; i++) {
                                data.add(BigDecimal.ZERO);
                            }
                            result.setData(data);
                            if (index != null && index <= max) {
                                data.set(index, dto.getMoneyAmount());
                            }
                            map.put(dto.getSpecInfo() + dto.getCreateTime(), result);
                        }
                    }

                    // 把map中的对象添加到resultList中
                    Iterator<String> iterator = map.keySet().iterator();
                    while (iterator.hasNext()) {
                        String string = (String) iterator.next();
                        resultList.add(map.get(string));
                    }
                }
            }

            return resultList;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 商品购买率
     * @param queryMap
     * @return
     */
    public PhurchaseRateDto getPhurchaseRate(Map<String, String> queryMap) {
        String model = queryMap.get("model");

        PhurchaseRateDto dto = null;
        try {
            if (model == null) {
                throw new BusinessException("请选择统计模式");
            }

            dto = new PhurchaseRateDto();

            //有效订单
            List<Orders> orderlist = getDuleOrdersWithNoPage(queryMap);
            //pv
            List<PVDto> pvlist = browseLogReadDao.getProductPV(queryMap);

            List<Object> orderNumList = new ArrayList<Object>();
            List<Object> pvList = new ArrayList<Object>();
            List<Object> rateList = new ArrayList<Object>();

            DecimalFormat df = new DecimalFormat("###.##");
            Calendar cal = Calendar.getInstance();
            //最大月份/天
            int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
                : cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int i = 1; i <= max; i++) {
                double orderNum = 0;
                double pv = 0;
                double rate = 0;

                for (Orders or : orderlist) {
                    //只创建一次实例
                    cal.clear();
                    cal.setTime(or.getFinishTime());
                    int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                        : cal.get(Calendar.DAY_OF_MONTH);

                    if (i == scale) {
                        orderNum++;
                    }

                }
                for (PVDto pvdto : pvlist) {
                    cal.clear();
                    cal.setTime(pvdto.getCreateTime());
                    int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                        : cal.get(Calendar.DAY_OF_MONTH);
                    if (i == scale) {
                        pv++;
                    }
                }
                //购买率=有效订单/pv
                rate = (pv == 0 ? 0 : orderNum / pv) * 100;

                orderNumList.add(orderNum);
                pvList.add(pv);
                rateList.add(df.format(rate));
            }
            dto.setOrderNumList(orderNumList);
            dto.setPvList(pvList);
            dto.setRateList(rateList);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw e;
            } else {
                e.printStackTrace();
            }
        }
        return dto;
    }

    /**
     * 获取交易完成的订单
     * @param queryMap
     * @return
     */
    public List<Orders> getDuleOrdersWithNoPage(Map<String, String> queryMap) {
        Map<String, Object> parammap = new HashMap<String, Object>();

        Iterator<Map.Entry<String, String>> entries = queryMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            parammap.put(entry.getKey(), entry.getValue());
        }

        parammap.put("s_statusR", ("3,4,5"));
        parammap.put("q_isParent", "0");
        return ordersReadDao.getOrders(parammap, -1, -1);
    }

    /**
     * 商品浏览量统计
     * @param queryMap
     * @return
     */
    public List<Object> pvStatistics(Map<String, String> queryMap) {
        String model = queryMap.get("model");

        List<PVDto> datalist = browseLogReadDao.getProductPV(queryMap);
        List<Object> pvlist = new ArrayList<Object>();

        Calendar cal = Calendar.getInstance();
        //最大月份/天
        int max = "year".equals(model) ? cal.getActualMaximum(Calendar.MONTH) + 1
            : cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= max; i++) {
            int pvNum = 0;

            for (PVDto pv : datalist) {
                //只创建一次实例
                cal.clear();
                cal.setTime(pv.getCreateTime());
                int scale = "year".equals(model) ? cal.get(Calendar.MONTH) + 1
                    : cal.get(Calendar.DAY_OF_MONTH);

                if (i == scale) {
                    pvNum++;
                }

            }
            pvlist.add(pvNum);
        }
        return pvlist;
    }

}
