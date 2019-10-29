package com.yixiekeji.web.controller.operate;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.seller.SellerTransport;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.service.seller.ISellerTransportService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.vo.seller.TransportJson;
import com.yixiekeji.vo.system.RegionsVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商家运费模板相关action
 *                       
 *
 */
@Controller
@RequestMapping(value = "seller/operate/sellerTransport")
public class SellerTransportController extends BaseController {
    @Resource
    private ISellerTransportService sellerTransportService;
    @Resource
    private IRegionsService         regionsService;

    /**
     * 默认页面
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        dataMap.put("queryMap", queryMap);

        return "seller/operate/sellertransport/list";
    }

    /**
     * gridDatalist数据
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerTransport>> list(HttpServletRequest request,
                                                                    Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("sellerId", WebSellerSession.getSellerUser(request).getSellerId().toString());
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SellerTransport>> serviceResult = sellerTransportService.page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<SellerTransport>> jsonResult = new HttpJsonResult<List<SellerTransport>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());

        return jsonResult;
    }

    /**
     * 计费切换
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "transportType", method = RequestMethod.GET)
    public String transportType(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> dataMap, Integer id, String type) {

        //新增时赋默认值
        SellerTransport np = null;

        if (id != null && id > 0) {
            np = this.sellerTransportService.getSellerTransportById(id).getResult();
        } else {
            np = new SellerTransport();
        }

        dataMap.put("obj", np);

        assembleMail(np.getTransMailInfo(), dataMap);
        assembleExpress(np.getTransExpressInfo(), dataMap);
        assembleEms(np.getTransEmsInfo(), dataMap);

        if (type != null && type.equals("weight")) {
            return "seller/operate/sellertransport/transport_weight";
        } else {
            return "seller/operate/sellertransport/transport_info";
        }
    }

    /**
     * 新增页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(HttpServletRequest request, Map<String, Object> dataMap) {
        //减少页面处理
        List<TransportJson> jsonlist = new ArrayList<TransportJson>();
        dataMap.put("mailList", jsonlist);
        dataMap.put("expressList", jsonlist);
        dataMap.put("emsList", jsonlist);
        dataMap.put("noTransList",jsonlist);
        return "seller/operate/sellertransport/edit";
    }

    /**
     * 编辑页面
     * @param request
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request, Map<String, Object> dataMap, String id) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<SellerTransport> npResult = this.sellerTransportService
            .getSellerTransportById(Integer.valueOf(id));
        if (!npResult.getSuccess()) {
            return "seller/500";
        }
        SellerTransport np = npResult.getResult();
        if (np == null) {
            return "seller/404";
        }
        if (!np.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        dataMap.put("obj", np);

        assembleMail(np.getTransMailInfo(), dataMap);
        assembleExpress(np.getTransExpressInfo(), dataMap);
        assembleEms(np.getTransEmsInfo(), dataMap);
        assembleNoTrans(np.getNoTransInfo(), dataMap);
        return "seller/operate/sellertransport/edit";
    }

    /**
     * 组装平邮信息
     * @param transMailInfo
     * @param dataMap
     */
    private void assembleMail(String transMailInfo, Map<String, Object> dataMap) {
        Gson gson = new Gson();
        List<TransportJson> jsonlist = new ArrayList<TransportJson>();
        if (!isNull(transMailInfo)) {
            List<TransportJson> list = gson.fromJson(transMailInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson tns : list) {
                //默认
                if (tns.getCity_id().equals("-1")) {
                    dataMap.put("mail", tns);
                } else {
                    //all
                    jsonlist.add(tns);
                }
            }
        }
        dataMap.put("mailList", jsonlist);
    }

    /**
     * 组装快递信息
     * @param transExpressInfo
     * @param dataMap
     */
    private void assembleExpress(String transExpressInfo, Map<String, Object> dataMap) {
        Gson gson = new Gson();
        List<TransportJson> jsonlist = new ArrayList<TransportJson>();
        if (!isNull(transExpressInfo)) {
            List<TransportJson> list = gson.fromJson(transExpressInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson tns : list) {
                //默认
                if (tns.getCity_id().equals("-1")) {
                    dataMap.put("express", tns);
                } else {
                    //all
                    jsonlist.add(tns);
                }
            }
        }
        dataMap.put("expressList", jsonlist);
    }

    /**
     * 组装EMS信息
     * @param transEmsInfo
     * @param dataMap
     */
    private void assembleEms(String transEmsInfo, Map<String, Object> dataMap) {
        Gson gson = new Gson();
        List<TransportJson> jsonlist = new ArrayList<TransportJson>();
        if (!isNull(transEmsInfo)) {
            List<TransportJson> list = gson.fromJson(transEmsInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson tns : list) {
                //默认
                if (tns.getCity_id().equals("-1")) {
                    dataMap.put("ems", tns);
                } else {
                    //all
                    jsonlist.add(tns);
                }
            }
        }
        dataMap.put("emsList", jsonlist);
    }
 /**
     * 组装不发货区域信息
     * @param noTransInfo
     * @param dataMap
     */
    private void assembleNoTrans(String noTransInfo, Map<String, Object> dataMap) {
        Gson gson = new Gson();
        List<TransportJson> jsonlist = new ArrayList<TransportJson>();
        if (!isNull(noTransInfo)) {
            List<TransportJson> list = gson.fromJson(noTransInfo,
                new TypeToken<ArrayList<TransportJson>>() {
                }.getType());
            for (TransportJson tns : list) {
                //默认
                if (tns.getCity_id().equals("-1")) {
                    dataMap.put("noTrans", tns);
                } else {
                    //all
                    jsonlist.add(tns);
                }
            }
        }
        dataMap.put("noTransList", jsonlist==null?"":jsonlist);
    }
    /**
     * 添加模板
     * @param request
     * @param response
     * @param news
     * @return
     */
    @RequestMapping(value = "doAdd", method = { RequestMethod.POST })
    public String doAdd(HttpServletRequest request, HttpServletResponse response,
                        SellerTransport transport, String mail_city_count,
                        String express_city_count, String ems_city_count,String no_trans_city_count) {

        //设置平邮信 息
        setTransMailInfo(transport, request, mail_city_count);
        //设置快递信息
        setTransExpress(transport, request, express_city_count);
        setNoTransInfo(transport, request, no_trans_city_count);
        //设置EMS信息
        setTransEms(transport, request, ems_city_count);

        if (transport.getId() != null && transport.getId() != 0) {
            SellerUser sellerUser = WebSellerSession.getSellerUser(request);
            ServiceResult<SellerTransport> npResult = this.sellerTransportService
                .getSellerTransportById(Integer.valueOf(transport.getId()));
            if (!npResult.getSuccess()) {
                return "seller/500";
            }
            SellerTransport np = npResult.getResult();
            if (np == null) {
                return "seller/404";
            }
            if (!np.getSellerId().equals(sellerUser.getSellerId())) {
                return "seller/unauth";
            }
            //更新
            this.sellerTransportService.updateSellerTransport(transport);
        } else {
            transport.setSellerId(WebSellerSession.getSellerUser(request).getSellerId());
            transport.setState(2);
            this.sellerTransportService.saveSellerTransport(transport);
        }
        System.out.println(transport);
        return "redirect:/seller/operate/sellerTransport";
    }

    /**
     * 设置EMS信息
     * @param transport
     * @param request
     * @param ems_city_count
     */
    private void setTransEms(SellerTransport transport, HttpServletRequest request,
                             String ems_city_count) {
        if (transport.getTransEms() != null && transport.getTransEms() == 1) {
            List<Map<String, Object>> trans_ems_info = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("city_id", "-1");
            map.put("city_name", "全国");
            map.put("trans_weight", Float.valueOf(request.getParameter("ems_trans_weight")));
            map.put("trans_fee", Float.valueOf(request.getParameter("ems_trans_fee")));
            map.put("trans_add_weight",
                Float.valueOf(request.getParameter("ems_trans_add_weight")));
            map.put("trans_add_fee", Float.valueOf(request.getParameter("ems_trans_add_fee")));
            trans_ems_info.add(map);
            for (int i = 0; i < Integer.valueOf(ems_city_count); i++) {
                String trans_weight_str = request.getParameter("ems_trans_weight" + i);
                String city_ids = request.getParameter("ems_city_ids" + i);
                if (StringUtil.isEmpty(trans_weight_str, true)
                    || StringUtil.isEmpty(city_ids, true)) {
                    continue;
                }
                BigDecimal trans_weight = new BigDecimal(trans_weight_str);
                if (trans_weight.compareTo(BigDecimal.ZERO) > 0) {
                    float trans_fee = Float.valueOf((request.getParameter("ems_trans_fee" + i)));
                    String trans_add_weight = request.getParameter("ems_trans_add_weight" + i);
                    float trans_add_fee = Float
                        .valueOf(request.getParameter("ems_trans_add_fee" + i));
                    String city_name = request.getParameter("ems_city_names" + i);
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("city_id", city_ids);
                    map1.put("city_name", city_name);
                    map1.put("trans_weight", Float.valueOf(trans_weight_str));
                    map1.put("trans_fee", Float.valueOf(trans_fee));
                    map1.put("trans_add_weight", Float.valueOf(trans_add_weight));
                    map1.put("trans_add_fee", Float.valueOf(trans_add_fee));
                    trans_ems_info.add(map1);
                }
            }
            Gson gson = new Gson();
            transport.setTransEmsInfo(gson.toJson(trans_ems_info));
        } else {
            transport.setTransEms(0);
            transport.setTransEmsInfo("");
        }
    }

    /**
     * 设置快递信息
     * @param transport
     * @param request
     * @param express_city_count
     */
    private void setTransExpress(SellerTransport transport, HttpServletRequest request,
                                 String express_city_count) {
        if (transport.getTransExpress() != null && transport.getTransExpress() == 1) {
            List<Map<String, Object>> trans_express_info = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("city_id", "-1");
            map.put("city_name", "全国");
            map.put("trans_weight", Float.valueOf(request.getParameter("express_trans_weight")));
            map.put("trans_fee", Float.valueOf(request.getParameter("express_trans_fee")));
            map.put("trans_add_weight",
                Float.valueOf(request.getParameter("express_trans_add_weight")));
            map.put("trans_add_fee", Float.valueOf(request.getParameter("express_trans_add_fee")));
            trans_express_info.add(map);
            for (int i = 0; i < Integer.valueOf(express_city_count); i++) {
                String trans_weight_str = request.getParameter("express_trans_weight" + i);
                String city_ids = request.getParameter("express_city_ids" + i);
                if (StringUtil.isEmpty(trans_weight_str, true)
                    || StringUtil.isEmpty(city_ids, true)) {
                    continue;
                }
                BigDecimal trans_weight = new BigDecimal(trans_weight_str);
                if (trans_weight.compareTo(BigDecimal.ZERO) > 0) {
                    float trans_fee = Float.valueOf(request.getParameter("express_trans_fee" + i));
                    String trans_add_weight = request.getParameter("express_trans_add_weight" + i);
                    float trans_add_fee = Float
                        .valueOf(request.getParameter("express_trans_add_fee" + i));
                    String city_name = request.getParameter("express_city_names" + i);
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("city_id", city_ids);
                    map1.put("city_name", city_name);
                    map1.put("trans_weight", Float.valueOf(trans_weight_str));
                    map1.put("trans_fee", Float.valueOf(trans_fee));
                    map1.put("trans_add_weight", Float.valueOf(trans_add_weight));
                    map1.put("trans_add_fee", Float.valueOf(trans_add_fee));
                    trans_express_info.add(map1);
                }
            }
            Gson gson = new Gson();
            transport.setTransExpressInfo(gson.toJson(trans_express_info));
        } else {
            transport.setTransExpress(0);
            transport.setTransExpressInfo("");
        }
    }

    /**
     * 设置平邮信息
     * @param transport
     * @param request
     * @param mail_city_count
     */
    private void setTransMailInfo(SellerTransport transport, HttpServletRequest request,
                                  String mail_city_count) {
        if (transport.getTransMail() != null && transport.getTransMail() == 1) {
            List<Map<String, Object>> trans_mail_info = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("city_id", "-1");
            map.put("city_name", "全国");
            map.put("trans_weight", Float.valueOf(request.getParameter("mail_trans_weight")));
            map.put("trans_fee", Float.valueOf(request.getParameter("mail_trans_fee")));
            map.put("trans_add_weight",
                Float.valueOf(request.getParameter("mail_trans_add_weight")));
            map.put("trans_add_fee", Float.valueOf(request.getParameter("mail_trans_add_fee")));
            trans_mail_info.add(map);
            for (int i = 0; i < Integer.valueOf(mail_city_count); i++) {
                String trans_weight_str = request.getParameter("mail_trans_weight" + i);
                String city_ids = request.getParameter("mail_city_ids" + i);
                if (StringUtil.isEmpty(trans_weight_str, true)
                    || StringUtil.isEmpty(city_ids, true)) {
                    continue;
                }
                BigDecimal trans_weight = new BigDecimal(trans_weight_str);
                if (trans_weight.compareTo(BigDecimal.ZERO) > 0) {
                    float trans_fee = Float.valueOf(request.getParameter("mail_trans_fee" + i));
                    String trans_add_weight = request.getParameter("mail_trans_add_weight" + i);
                    float trans_add_fee = Float
                        .valueOf(request.getParameter("mail_trans_add_fee" + i));
                    String city_name = request.getParameter("mail_city_names" + i);
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("city_id", city_ids);
                    map1.put("city_name", city_name);
                    map1.put("trans_weight", Float.valueOf(trans_weight_str));
                    map1.put("trans_fee", Float.valueOf(trans_fee));
                    map1.put("trans_add_weight", Float.valueOf(trans_add_weight));
                    map1.put("trans_add_fee", Float.valueOf(trans_add_fee));
                    trans_mail_info.add(map1);
                }
            }
            Gson gson = new Gson();
            transport.setTransMailInfo(gson.toJson(trans_mail_info));
        } else {
            transport.setTransMail(0);
            transport.setTransMailInfo("");
        }
    }
    
     /**
     * 设置不发货信息
     * @param transport
     * @param request
     * @param no_trans_city_count
     */
    private void setNoTransInfo(SellerTransport transport, HttpServletRequest request,
                                  String no_trans_city_count) {
        if (transport.getNoTrans()!= null && transport.getNoTrans() == 1) {
            List<Map<String, Object>>  no_trans_info = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < Integer.valueOf(no_trans_city_count); i++) {
                //String trans_weight_str = request.getParameter("no_trans_weight" + i);
                String city_ids = request.getParameter("no_trans_city_ids" + i);
                if (StringUtil.isEmpty(city_ids, true)) {
                    continue;
                }
                
                String city_name = request.getParameter("no_trans_city_names" + i);
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("city_id", city_ids);
                    map1.put("city_name", city_name);
                    no_trans_info.add(map1);
            }
            Gson gson = new Gson();
            transport.setNoTransInfo(gson.toJson(no_trans_info));
        } else {
            transport.setNoTrans(0);
            transport.setNoTransInfo("");
        }
    }
    
    @RequestMapping({ "transport_area" })
    public String transport_area(HttpServletRequest request, HttpServletResponse response,
                                 String trans_city_type, String trans_index,
                                 Map<String, Object> dataMap) {
        List<RegionsVO> allArea = this.regionsService.getAllArea();
        dataMap.put("allArea", allArea);
        dataMap.put("trans_city_type", trans_city_type);
        dataMap.put("trans_index", trans_index);
        return "seller/operate/sellertransport/all_area";
    }

    /**
     * 删除
     * @param request
     * @param response
     * @param transport
     * @return
     */
    @RequestMapping(value = "del", method = { RequestMethod.GET })
    public void del(HttpServletRequest request, HttpServletResponse response, String id) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = null;
        String msg = "";
        try {
            pw = response.getWriter();
            SellerUser sellerUser = WebSellerSession.getSellerUser(request);
            ServiceResult<Boolean> sr = this.sellerTransportService.del(sellerUser.getSellerId(),
                Integer.valueOf(id));
            if (!sr.getSuccess()) {
                if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                    throw new RuntimeException(sr.getMessage());
                } else {
                    throw new BusinessException(sr.getMessage());
                }
            }
            msg = sr.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
        }
        pw.print(msg);
    }

    @RequestMapping(value = "getTransport", method = { RequestMethod.GET })
    public void getTransport(HttpServletRequest request, HttpServletResponse response, String id) {
        if (id == null || "".equals(id))
            throw new IllegalArgumentException("运费模板id不能为空");
        Gson gson = new Gson();
        ServiceResult<SellerTransport> sr = this.sellerTransportService
            .getSellerTransportById(Integer.valueOf(id));

        if (!sr.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(sr.getCode())) {
                throw new RuntimeException(sr.getMessage());
            } else {
                throw new BusinessException(sr.getMessage());
            }
        }

        SellerTransport st = sr.getResult();
        ServiceResult<List<TransportJson>> jsonResult = sellerTransportService
            .assembleTransportInfo(st);
        HttpJsonResult<List<TransportJson>> jsonres = new HttpJsonResult<List<TransportJson>>();
        jsonres.setRows(jsonResult.getResult());
        jsonres.setTotal(jsonResult.getResult().size());
        String obj = gson.toJson(jsonres);

        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(obj);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启用运费模板
     * @param request
     * @param id
     */
    @RequestMapping(value = "inuse", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> inUse(HttpServletRequest request,
                                                       HttpServletResponse response, Integer id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<SellerTransport> npResult = this.sellerTransportService
            .getSellerTransportById(Integer.valueOf(id));
        if (!npResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(npResult.getMessage());
        }
        SellerTransport np = npResult.getResult();
        if (np == null) {
            return new HttpJsonResult<Boolean>("信息获取失败，请重试。");
        }
        if (!np.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        ServiceResult<Boolean> inuseResult = sellerTransportService.transportInUse(id,
            SellerTransport.STATE_1);
        HttpJsonResult<Boolean> jsonResult = null;
        if (inuseResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(inuseResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 禁用运费模板
     * @param request
     * @param id
     */
    @RequestMapping(value = "disable", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> disable(HttpServletRequest request,
                                                         HttpServletResponse response, Integer id) {

        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<SellerTransport> npResult = this.sellerTransportService
            .getSellerTransportById(Integer.valueOf(id));
        if (!npResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(npResult.getMessage());
        }
        SellerTransport np = npResult.getResult();
        if (np == null) {
            return new HttpJsonResult<Boolean>("信息获取失败，请重试。");
        }
        if (!np.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Boolean>("您无权操作该数据");
        }
        ServiceResult<Boolean> inuseResult = sellerTransportService.transportInUse(id,
            SellerTransport.STATE_2);
        HttpJsonResult<Boolean> jsonResult = null;
        if (inuseResult.getSuccess()) {
            jsonResult = new HttpJsonResult<Boolean>();
        } else {
            jsonResult = new HttpJsonResult<Boolean>(inuseResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 根据商品模板类型加载模板
     * @param request
     * @param response
     * @param type
     * @return
     */
    @RequestMapping(value = "/getByType", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerTransport>> getByType(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         Integer type) {
        HttpJsonResult<List<SellerTransport>> jsonResult = new HttpJsonResult<List<SellerTransport>>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        ServiceResult<List<SellerTransport>> sellerTransportResult = sellerTransportService
            .getTransportByTypeAndSellerId(type, sellerUser.getSellerId());
        if (!sellerTransportResult.getSuccess()) {
            jsonResult.setMessage(sellerTransportResult.getMessage());
        }

        jsonResult.setData(sellerTransportResult.getResult());
        return jsonResult;
    }

}
