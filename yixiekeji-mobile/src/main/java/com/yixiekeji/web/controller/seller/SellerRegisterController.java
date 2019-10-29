package com.yixiekeji.web.controller.seller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.entity.member.Member;
import com.yixiekeji.entity.seller.Seller;
import com.yixiekeji.entity.seller.SellerApply;
import com.yixiekeji.entity.system.Regions;
import com.yixiekeji.service.member.IMemberService;
import com.yixiekeji.service.seller.ISellerApplyService;
import com.yixiekeji.service.seller.ISellerService;
import com.yixiekeji.service.system.IRegionsService;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebFrontSession;

/**
 * 商家入驻controller
 * 
 * @Filename: FrontSellerRegisterController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class SellerRegisterController extends BaseController {

	@Resource
	private ISellerApplyService	sellerApplyService;
	@Resource
	private ISellerService		sellerService;
	@Resource
	private IMemberService		memberService;
	@Resource
	private IRegionsService		regionsService;

	/**
	 * 商家入驻：入驻协议
	 * @param request
	 * @param response
	 * @param dataMap
	 * @return
	 */
	@RequestMapping(value = "/storeregister/apply.html", method = { RequestMethod.GET })
	public String step1(HttpServletRequest request, HttpServletResponse response,
						Map<String, Object> dataMap) {
		String returnURL = "h5/seller/selleragreement";
		Member member = WebFrontSession.getLoginedUser(request);
		//是否已申请过
		ServiceResult<SellerApply> sellerApplyResult = sellerApplyService
			.getSellerApplyByUser(member.getId());
		if (sellerApplyResult.getResult() != null) {
			SellerApply sellerApply = sellerApplyResult.getResult();
			// 如果已经申请过，显示申请的信息
			ServiceResult<Seller> sellerResult = sellerService
				.getSellerByMemberId(sellerApply.getUserId());
			Seller seller = sellerResult.getResult();
			this.setDataMap(dataMap, sellerApply, seller.getName(), seller.getSellerName());
			returnURL = "h5/seller/sellerregister";
		} else {
			WebFrontSession.putObjToSession(request, "safekey", new Date());
		}
		return returnURL;
	}

	/**
	 * 商家入驻：公司资质信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/storeregister/info.html", method = { RequestMethod.GET })
	public String step2(HttpServletRequest request, HttpServletResponse response,
						Map<String, Object> dataMap) {
		String returnURL = "h5/seller/sellerregister";
		if (request.getSession().getAttribute("safekey") == null) {
			return "redirect:/storeregister/apply.html";
		} else {
			request.getSession().removeAttribute("safekey");
		}

		ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
		dataMap.put("provinceList", provinceResult.getResult());

		return returnURL;
	}

	/**
	 * 商家入住申请保存信息<br>
	 * @param request
	 * @param response
	 * @param sellerApply
	 * @return
	 */
	@RequestMapping(value = "/storeregister/doregister.html", method = { RequestMethod.POST })
	public String step2Save(HttpServletRequest request, HttpServletResponse response,
							Map<String, Object> dataMap, SellerApply sellerApply, String userName,
							String sellerName) {

		//营业执照扫描件
		String bli = UploadUtil.getInstance()
			.sellerApplyUploadFile2ImageServer("up_bussinessLicenseImage", request);
		//身份证正面
		String pcu = UploadUtil.getInstance().sellerApplyUploadFile2ImageServer("up_personCardUp",
			request);
		//身份证反面
		String pdw = UploadUtil.getInstance().sellerApplyUploadFile2ImageServer("up_personCardDown",
			request);

		if (!StringUtil.isEmpty(bli, true)) {
			sellerApply.setBussinessLicenseImage(bli);
		}
		if (!StringUtil.isEmpty(pcu, true)) {
			sellerApply.setPersonCardUp(pcu);
		}
		if (!StringUtil.isEmpty(pdw, true)) {
			sellerApply.setPersonCardDown(pdw);
		}

		Member member = WebFrontSession.getLoginedUser(request);

		Integer state = sellerApply.getState();
		ServiceResult<Boolean> serviceResult = null;
		if (sellerApply.getId() != null && sellerApply.getId() > 0) {
			// 修改商家申请
			sellerApply.setState(null);
			serviceResult = sellerApplyService.updateSellerApplyForFront(sellerApply, userName,
				sellerName);
		} else {
			sellerApply.setState(SellerApply.STATE_1_SEND);
			sellerApply.setType(2);
			// 保存商家申请
			serviceResult = sellerApplyService.saveSellerApplyForFront(sellerApply, userName,
				sellerName, member.getId());
		}

		if (!serviceResult.getSuccess()) {
			if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
				throw new RuntimeException(serviceResult.getMessage());
			} else {
				sellerApply.setState(state);
				this.setDataMap(dataMap, sellerApply, userName, sellerName);
				dataMap.put("message", serviceResult.getMessage());
				return "h5/seller/sellerregister";
			}
		}

		dataMap.put("message", "申请保存成功，请耐心等待。");
		return "h5/seller/sellerregistermsg";

	}

	private void setDataMap(Map<String, Object> dataMap, SellerApply sellerApply, String userName,
							String sellerName) {
		ServiceResult<List<Regions>> provinceResult = regionsService.getRegionsByParentId(0);
		dataMap.put("provinceList", provinceResult.getResult());

		ServiceResult<List<Regions>> companyCityResult = regionsService
			.getRegionsByParentId(ConvertUtil.toInt(sellerApply.getCompanyProvince(), null));
		dataMap.put("companyCityList", companyCityResult.getResult());

		ServiceResult<List<Regions>> bankCityResult = regionsService
			.getRegionsByParentId(ConvertUtil.toInt(sellerApply.getBankProvince(), null));
		dataMap.put("bankCityList", bankCityResult.getResult());

		dataMap.put("sellerApply", sellerApply);
		dataMap.put("userName", userName);
		dataMap.put("sellerName", sellerName);
	}

}
