package com.yixiekeji.web.controller.member;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yixiekeji.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String toReciptAddress(HttpServletRequest request, HttpServletResponse response,
                                  ModelMap map) {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<List<MemberAddress>> serviceResult = memberAddressService
            .getMemberAddressByMId(member.getId());
        map.put("addressList", serviceResult.getResult());

        String message = request.getParameter("message");
        map.put("message", message);

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

        return "h5/member/address/addresslist";
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
    public String editAddress(HttpServletRequest request, HttpServletResponse response,
                              ModelMap map, Integer id) {
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

        return "h5/member/address/addressedit";
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
    public String newAddress(HttpServletRequest request, HttpServletResponse response,
                             ModelMap map) {
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

        return "h5/member/address/addressedit";
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
                                                                     Map<String, Object> stack,
                                                                     MemberAddress memberAddress) throws Exception {

        Member member = WebFrontSession.getLoginedUser(request);

        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();

        memberAddress.setMemberId(member.getId());

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
     * @param membervo
     * @throws Exception
     */
    @RequestMapping(value = "/setdefaultaddress.html", method = { RequestMethod.GET })
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "id", required = true) Integer id) throws Exception {
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Boolean> serviceResult = memberAddressService.defaultMemberAddress(id,
            member.getId());
        String message = "";
        if (!serviceResult.getSuccess()) {
            message = serviceResult.getMessage();
        }
        return "redirect:/member/address.html?message=" + message;
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
        Member member = WebFrontSession.getLoginedUser(request);
        ServiceResult<Boolean> serviceResult = memberAddressService.deleteMemberAddress(id,
            member.getId());

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

    /**
    * @Description: 添加/修改收货地址
    * @Author: mofan
    * @Date: 2019/10/23
    */
    @PostMapping(value = "/saveAddress")
    @ResponseBody
    public ServiceResult saveAddress(MemberAddress memberAddress,HttpServletRequest request,ModelMap map) throws Exception {
        //如果id值不为空，则更新，否则添加。前台如果为空，转成Integer时为变为0
        if (memberAddress != null && memberAddress.getId() != null && memberAddress.getId() != 0) {
            ServiceResult<Boolean> booleanServiceResult = memberAddressService.updateMemberAddress(memberAddress);
            if(memberAddress.getState() == 1){
                memberAddressService.defaultMemberAddress(memberAddress.getId(),memberAddress.getMemberId());
            }
            if(booleanServiceResult.getResult()){
                String isFromOrder = request.getParameter("isFromOrder");
                map.put("isFromOrder", isFromOrder);
                String orderType = request.getParameter("orderType");
                if (!StringUtil.isEmpty(orderType, true)) {
                    map.put("orderType", orderType);
                    String actInfo = request.getParameter("actInfo");
                    map.put("actInfo", actInfo);
                }
                return new ServiceResult(true, StatusCode.OK,"修改成功!");
            }
            return new ServiceResult(false, StatusCode.ERROR,"修改失败!");
        } else {
            Integer booleanServiceResult =memberAddressService.saveAddress(memberAddress);
            if(booleanServiceResult>0){
                MemberAddress maxId = memberAddressService.getMaxId();
                if(memberAddress.getState() == 1){
                    memberAddressService.defaultMemberAddress(maxId.getId(),memberAddress.getMemberId());
                }
                String isFromOrder = request.getParameter("isFromOrder");
                map.put("isFromOrder", isFromOrder);
                String orderType = request.getParameter("orderType");
                if (!StringUtil.isEmpty(orderType, true)) {
                    map.put("orderType", orderType);
                    String actInfo = request.getParameter("actInfo");
                    map.put("actInfo", actInfo);
                }
                return new ServiceResult(true, StatusCode.OK,maxId,"新增成功!");
            }
            return new ServiceResult(false, StatusCode.ERROR,"新增失败!");
        }
    }

    /**
    * @Description: 根据会员id查询地址列表
    * @Author: mofan
    * @Date: 2019/10/23
    */
    @GetMapping(value = "getAddressByMId")
    @ResponseBody
    public ServiceResult getAddressByMId(Integer memberId){
        ServiceResult<List<MemberAddress>> memberAddressByMId = memberAddressService.getMemberAddressByMId(memberId);
        if(memberAddressByMId.getResult() != null){
            return new ServiceResult(true,StatusCode.OK,memberAddressByMId.getResult(),"查询成功!");
        }
        return new ServiceResult(false,StatusCode.ERROR,"查询失败!");
    }

    /**
     * @Description: 根据id查询地址详情
     * @Author: mofan
     * @Date: 2019/10/23
     */
    @GetMapping(value = "getAddressById")
    @ResponseBody
    public ServiceResult getAddressById(Integer id){
        ServiceResult<MemberAddress> memberAddressById = memberAddressService.getMemberAddressById(id);
        if(memberAddressById.getResult() != null){
            return new ServiceResult(true,StatusCode.OK,memberAddressById.getResult(),"查询成功!");
        }
        return new ServiceResult(false,StatusCode.ERROR,"查询失败!");
    }

}
