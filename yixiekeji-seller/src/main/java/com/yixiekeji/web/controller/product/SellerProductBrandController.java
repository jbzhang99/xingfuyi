package com.yixiekeji.web.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.seller.SellerApplyBrand;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.Code;
import com.yixiekeji.service.product.ISellerApplyBrandService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商家商品品牌管理
 */
@Controller
@RequestMapping(value = "seller/product/brand")
public class SellerProductBrandController extends BaseController {

    @Resource
    private ISellerApplyBrandService sellerApplyBrandService;

    @Resource
    private DomainUrlUtil            domainUrlUtil;

    private static Logger            log = LoggerFactory
        .getLogger(SellerProductBrandController.class);

    /**
     * 默认，显示全部状态的品牌
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "seller/product/brand/brandlist";
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<SellerApplyBrand>> list(HttpServletRequest request,
                                                                     Map<String, Object> dataMap) {
        HttpJsonResult<List<SellerApplyBrand>> jsonResult = new HttpJsonResult<List<SellerApplyBrand>>();
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);

        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        queryMap.put("q_sellerId", String.valueOf(sellerUser.getSellerId()));
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<SellerApplyBrand>> serviceResult = sellerApplyBrandService
            .page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        jsonResult.setRows((List<SellerApplyBrand>) serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) {
        Code code = new Code();
        dataMap.put("code", code);
        return "seller/product/brand/brandadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> create(SellerApplyBrand brand,
                                         MultipartHttpServletRequest request,
                                         Map<String, Object> dataMap) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        //上传品牌图片
        MultipartFile multipartFile = request.getFile("imageFile");
        if (null != multipartFile && multipartFile.getSize() > 0) {
            String originalFilename = multipartFile.getOriginalFilename();
            File tmpFile = new File(
                buildImgPath(request) + "/" + UUID.randomUUID() + originalFilename
                    .substring(originalFilename.lastIndexOf("."), originalFilename.length()));
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(tmpFile));
                stream.write(bytes);
                stream.close();
            }

            //String url = DomainUrlUtil.getEJS_IMAGE_RESOURCES();
            String url = UploadUtil.getInstance().brandUploaderImage(tmpFile, request, true,
                domainUrlUtil.getImageResources() + "/imageUtilBrand");
            tmpFile.deleteOnExit();
            brand.setImage(url);
        }

        brand.setCreateId(sellerUser.getId());
        brand.setCreateName(sellerUser.getName());
        brand.setUpdateId(sellerUser.getId());
        brand.setUpdateName(sellerUser.getName());
        brand.setSellerId(sellerUser.getSellerId());

        brand.setState(ProductBrand.Status.SUCCESS.getValue());
        ServiceResult<Boolean> serviceResult = sellerApplyBrandService.save(brand);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    private String buildImgPath(HttpServletRequest request) {
        String path = "upload";
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        path = request.getRealPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
        return path;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(HttpServletRequest request,
                       @RequestParam(value = "id", required = true) Integer id,
                       Map<String, Object> dataMap) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        ServiceResult<SellerApplyBrand> serviceResult = sellerApplyBrandService.getById(id);
        if (!serviceResult.getSuccess()) {
            return "seller/500";
        }
        SellerApplyBrand sellerApplyBrand = serviceResult.getResult();
        if (sellerApplyBrand == null) {
            return "seller/404";
        }
        if (!sellerApplyBrand.getSellerId().equals(sellerUser.getSellerId())) {
            return "seller/unauth";
        }
        dataMap.put("brand", sellerApplyBrand);
        return "seller/product/brand/brandedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> update(SellerApplyBrand brand,
                                         MultipartHttpServletRequest request) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        ServiceResult<SellerApplyBrand> selResult = sellerApplyBrandService.getById(brand.getId());
        if (!selResult.getSuccess()) {
            return new HttpJsonResult<Object>(selResult.getMessage());
        }
        SellerApplyBrand sellerApplyBrand = selResult.getResult();
        if (sellerApplyBrand == null) {
            return new HttpJsonResult<Object>("获取品牌信息失败，请重试。");
        }
        if (!sellerApplyBrand.getSellerId().equals(sellerUser.getSellerId())) {
            return new HttpJsonResult<Object>("您无权操作该数据。");
        }
        //品牌图片
        MultipartFile multipartFile = request.getFile("imageFile");
        if (null != multipartFile && multipartFile.getSize() > 0) {
            String originalFilename = multipartFile.getOriginalFilename();
            File tmpFile = new File(
                buildImgPath(request) + "/" + UUID.randomUUID() + originalFilename
                    .substring(originalFilename.lastIndexOf("."), originalFilename.length()));
            if (!multipartFile.isEmpty()) {
                byte[] bytes = multipartFile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(tmpFile));
                stream.write(bytes);
                stream.close();
            }

            String url = UploadUtil.getInstance().brandUploaderImage(tmpFile, request, true,
                domainUrlUtil.getImageResources() + "/imageUtilBrand");
            tmpFile.deleteOnExit();
            brand.setImage(url);
        }

        brand.setUpdateId(sellerUser.getId());
        brand.setUpdateName(sellerUser.getName());
        ServiceResult<Boolean> serviceResult = sellerApplyBrandService.update(brand);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        if (null == sellerUser) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/seller/login.html");
            return jsonResult;
        }
        ServiceResult<Boolean> serviceResult = sellerApplyBrandService.del(id,
            sellerUser.getSellerId());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 商家提交平台审核品牌
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "commit", method = RequestMethod.POST)
    public @ResponseBody HttpJsonResult<Object> commit(HttpServletRequest request,
                                                       @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>(true);
        ServiceResult<Boolean> serviceResult = sellerApplyBrandService.commit(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

}
