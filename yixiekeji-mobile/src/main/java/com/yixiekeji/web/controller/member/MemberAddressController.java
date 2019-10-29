package com.yixiekeji.web.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.member.MemberAddress;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.member.IMemberAddressService;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 用户地址管理
 *                       
 * @Filename: MemberAddressController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
@RequestMapping(value = "member")
public class MemberAddressController extends BaseController {

    @Resource
    private IMemberAddressService memberAddressService;
    @Resource
    private IMemberService        memberService;
    @Resource
    private IRegionsService       regionsService;

    /**
     * 跳转到 收货地址管理界面  取所有收货地址
     * @param request
     * @param response
     * @param map
     * @param id  收货地址id
     * @return
     */
    @RequestMapping(value = "/address.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> toReciptAddress(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             ModelMap map) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(member.getId());
        map.put("addressList", serviceResult.getResult());

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }

        jsonResult.setData(map);
        return jsonResult;
    }

    /**
     * 收货地址编辑
     * @param request
     * @param response
     * @param map
     * @param id
     * @return
     */
    @RequestMapping(value = "/editaddress.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> editAddress(HttpServletRequest request,
                                                                         HttpServletResponse response,
                                                                         ModelMap map, Integer id) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        ServiceResult<MemberAddress> serviceResult = new ServiceResult<MemberAddress>();
        if (id != null && !id.equals(0)) {
            serviceResult = memberAddressService.getMemberAddressById(id);
        }
        MemberAddress memberAddress = new MemberAddress();
        if (serviceResult.getSuccess()) {
            memberAddress = serviceResult.getResult();
        }
        map.put("address", memberAddress);

        ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
        map.put("provinceList", provinceResult.getResult());

        ServiceResult<List<Regions>> cityResult = regionsService
            .getRegionsByParentId(memberAddress.getProvinceId());
        map.put("cityList", cityResult.getResult());

        ServiceResult<List<Regions>> areaResult = regionsService
            .getRegionsByParentId(memberAddress.getCityId());
        map.put("areaList", areaResult.getResult());

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }
        jsonResult.setData(map);
        return jsonResult;
    }

    /**
     * 收货地址添加
     * @param request
     * @param response
     * @param map
     * @param id
     * @return
     */
    @RequestMapping(value = "/newaddress.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Map<String, Object>> newAddress(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        ModelMap map) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();
        MemberAddress memberAddress = new MemberAddress();
        map.put("address", memberAddress);

        ServiceResult<List<Regions>> regionsByParentId = regionsService.getRegionsByParentId(0);
        map.put("provinceList", regionsByParentId.getResult());

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }

        jsonResult.setData(map);
        return jsonResult;
    }

    /**
     * 收货地址信息提交或编辑
     * @param request
     * @param response
     * @param stack
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/saveaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Integer> reciptAddressSumbit(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     String memberName,
                                                                     String mobile,
                                                                     Integer provinceId,
                                                                     Integer cityId, Integer areaId,
                                                                     String addressInfo,
                                                                     String zipCode, Integer id,
                                                                     String addAll, String phone,
                                                                     String email) throws Exception {
        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();

        MemberAddress memberAddress = new MemberAddress();
        memberAddress.setId(id);
        memberAddress.setMemberId(member.getId());
        memberAddress.setMemberName(memberName);
        memberAddress.setMobile(mobile);
        memberAddress.setProvinceId(provinceId);
        memberAddress.setCityId(cityId);
        memberAddress.setAreaId(areaId);
        memberAddress.setAddAll(addAll);
        memberAddress.setAddressInfo(addressInfo);
        memberAddress.setZipCode(zipCode);
        memberAddress.setPhone(phone);
        memberAddress.setEmail(email);

        //如果id值不为空，则更新，否则添加。前台如果为空，转成Integer时为变为0
        if (memberAddress != null && memberAddress.getId() != null && memberAddress.getId() != 0) {
            serviceResult = memberAddressService.updateMemberAddress(memberAddress);
        } else {
            memberAddress.setState(MemberAddress.STATE_2);
            serviceResult = memberAddressService.saveMemberAddress(memberAddress);
        }

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Integer>(serviceResult.getMessage());
            }
        }
        jsonResult.setData(memberAddress.getId());
        return jsonResult;

    }

    /**
     * 收获地址设为默认
     * @param request
     * @param response
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/setdefaultaddress.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> defaultAddress(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @RequestParam(value = "id", required = true) Integer id) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = memberAddressService.defaultMemberAddress(id,
            member.getId());
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 删除收货地址
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/deleteaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> deleteAddress(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               @RequestParam(value = "id", required = true) Integer id) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request);
        if (member == null) {
            jsonResult.setMessage("亲爱的用户，请先登录后再操作。");
            return jsonResult;
        }

        ServiceResult<Boolean> serviceResult = memberAddressService.deleteMemberAddress(id,
            member.getId());

        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                jsonResult = new HttpJsonResult<Boolean>(serviceResult.getMessage());
            }
        }
        return jsonResult;
    }
}
