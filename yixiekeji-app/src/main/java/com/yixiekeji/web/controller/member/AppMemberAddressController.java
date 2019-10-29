package com.yixiekeji.web.controller.member;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
public class AppMemberAddressController extends BaseController {

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
     * @return
     */
    @RequestMapping(value = "/app-address.html", method = { RequestMethod.GET })
    public String toReciptAddress(HttpServletRequest request,
                                                                             HttpServletResponse response,
                                                                             ModelMap map,
                                                                             Integer memberId) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(memberId);
        map.put("addressList", serviceResult.getResult());

        //        String toUrl = request.getParameter("toUrl");
        //        map.put("toUrl", toUrl);

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }

        jsonResult.setData(map);
        return "h5/member/address/addresslist";
        //return jsonResult;
    }

    @RequestMapping(value = "/app-editaddress.html", method = { RequestMethod.GET })
    public String editAddress(HttpServletRequest request,
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

        //        String toUrl = request.getParameter("toUrl");
        //        map.put("toUrl", toUrl);

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }

        jsonResult.setData(map);
        return  "h5/member/address/addressedit";

//        return jsonResult;
    }

    /**
     * 收货地址添加
     * @param request
     * @param response
     * @param map
     * @param
     * @return
     */
    @RequestMapping(value = "/app-newaddress.html", method = { RequestMethod.GET })
    public String newAddress(HttpServletRequest request,
                                                                        HttpServletResponse response,
                                                                        ModelMap map) {
        HttpJsonResult<Map<String, Object>> jsonResult = new HttpJsonResult<Map<String, Object>>();

        MemberAddress memberAddress = new MemberAddress();
        map.put("address", memberAddress);

        ServiceResult<List<Regions>> regionsByParentId = regionsService.getRegionsByParentId(0);
        map.put("provinceList", regionsByParentId.getResult());

        //        String toUrl = request.getParameter("toUrl");
        //        map.put("toUrl", toUrl);

        String isFromOrder = request.getParameter("isFromOrder");
        map.put("isFromOrder", isFromOrder);
        String orderType = request.getParameter("orderType");
        if (!StringUtil.isEmpty(orderType, true)) {
            map.put("orderType", orderType);
            String actInfo = request.getParameter("actInfo");
            map.put("actInfo", actInfo);
        }

        jsonResult.setData(map);
        return  "h5/member/address/addressedit";
//        return jsonResult;
    }

    /**
     * 收货地址信息提交或编辑
     * @param request
     * @param response
     * @param
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/app-saveaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Integer> reciptAddressSumbit(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     String memberName,
                                                                     String mobile,
                                                                     Integer provinceId,
                                                                     Integer cityId, Integer areaId,
                                                                     String addressInfo,
                                                                     String zipCode, Integer id,
                                                                     String addAll, String phone,
                                                                     String email,
                                                                     Integer memberId) throws Exception {

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        Member member = WebFrontSession.getLoginedUser(request, response);
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

        HttpJsonResult<Integer> jsonResult = new HttpJsonResult<Integer>();
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
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/app-setdefaultaddress.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<Boolean> defaultAddress(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Integer memberId,
                                                                @RequestParam(value = "id", required = true) Integer id) throws Exception {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();

        ServiceResult<Boolean> serviceResult = memberAddressService.defaultMemberAddress(id,
            memberId);
        if (!serviceResult.getSuccess()) {
            return new HttpJsonResult<Boolean>(serviceResult.getMessage());
        }
        jsonResult.setBackUrl("/member/app-address.html");
        return jsonResult;
    }

    /**
     * 删除收货地址
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping(value = "/app-deleteaddress.html", method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> deleteAddress(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Integer memberId,
                                                               @RequestParam(value = "id", required = true) Integer id) throws Exception {
        ServiceResult<Boolean> serviceResult = memberAddressService.deleteMemberAddress(id,
            memberId);

        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
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
