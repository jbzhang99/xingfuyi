package com.yixiekeji.web.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.HttpJsonResultForAjax;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.core.StringUtil;
import com.yixiekeji.core.WebUtil;
import com.yixiekeji.core.FeignUtil.FeignUtil;
import com.yixiekeji.core.exception.BusinessException;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.entity.seller.SellerApplyBrand;
import com.yixiekeji.entity.system.Code;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.product.ISellerApplyBrandService;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 商品品牌
 */
@Controller
@RequestMapping(value = "admin/product/brand")
public class BrandController extends BaseController {
    @Resource
    private IProductBrandService     productBrandService;

    @Resource
    private ISellerApplyBrandService sellerApplyBrandService;

    @Resource
    private DomainUrlUtil            domainUrlUtil;

    /**
     * 默认，显示全部状态的品牌
     *
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return "admin/product/brand/brandlist";
    }

    @RequestMapping(value = "upload", method = { RequestMethod.GET })
    public String upload() {
        return "admin/product/brand/imagetest";
    }

    /**
     * 待审核品牌
     *
     * @param dataMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "todo", method = { RequestMethod.GET })
    public String toDo(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        dataMap.put("q_state", String.valueOf(SellerApplyBrand.Status.COMMIT.getValue()));
        return "admin/product/brand/todolist";
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<List<ProductBrand>> list(HttpServletRequest request,
                                                                 Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        queryMap.put("q_state", String.valueOf(ProductBrand.Status.SUCCESS.getValue()));//只显示审核通过的品牌
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductBrand>> serviceResult = productBrandService.page(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductBrand>> jsonResult = new HttpJsonResult<List<ProductBrand>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    /**
     * 取出所有可用的品牌
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "list_no_page", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<List<ProductBrand>> list_no_page(HttpServletRequest request,
                                                                         Map<String, Object> dataMap) {
        ServiceResult<List<ProductBrand>> serviceResult = productBrandService.listNoPage();
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }

        HttpJsonResult<List<ProductBrand>> jsonResult = new HttpJsonResult<List<ProductBrand>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return jsonResult;
    }

    /**
     * 待审核列表
     *
     * @param request
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "todolist", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<List<SellerApplyBrand>> todoList(HttpServletRequest request,
                                                                         Map<String, Object> dataMap) {
        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
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

        HttpJsonResult<List<SellerApplyBrand>> jsonResult = new HttpJsonResult<List<SellerApplyBrand>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) {
        Code code = new Code();
        dataMap.put("code", code);
        return "admin/product/brand/brandadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpJsonResult<Object> create(ProductBrand brand, MultipartHttpServletRequest request,
                                         Map<String, Object> dataMap) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }

        if (!StringUtil.is_alpha(brand.getNameFirst())) {
            jsonResult.setMessage("首字母输入错误，请重新输入");
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

            String url = UploadUtil.getInstance().brandUploaderImage(tmpFile, request, true,
                domainUrlUtil.getImageResources() + "/imageUtilBrand");
            brand.setImage(url);
            tmpFile.delete();
        }
        brand.setCreateId(user.getId());
        brand.setUpdateId(user.getId());
        brand.setState(ProductBrand.Status.SUCCESS.getValue());//平台维护品牌，默认审核通过
        brand.setNameFirst(brand.getNameFirst().toUpperCase());
        ServiceResult<Boolean> serviceResult = productBrandService.save(brand);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    private String buildImgPath(HttpServletRequest request) throws FileNotFoundException {
        //        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        //        if (!path.exists())
        //            path = new File("");
        //        File upload = new File(path.getAbsolutePath(), "resources/upload/");
        //        if (!upload.exists())
        //            upload.mkdirs();
        //        return upload.getPath();

        String path = "upload";
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        path = request.getRealPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
            }
        }
        return path;
    }

    @RequestMapping(value = "edit", method = { RequestMethod.GET })
    public String edit(@RequestParam(value = "id", required = true) Integer id,
                       @RequestParam(value = "type", required = true) Integer type,
                       Map<String, Object> dataMap) {
        String path = "";
        if (type == 0) {
            //平台品牌编辑
            ServiceResult<ProductBrand> serviceResult = productBrandService.getById(id);
            if (!serviceResult.getSuccess()) {
                throw new RuntimeException(serviceResult.getMessage());
            }
            dataMap.put("brand", serviceResult.getResult());
            path = "admin/product/brand/brandedit";
        } else if (type == 1) {
            //商家品牌审核查看
            ServiceResult<SellerApplyBrand> serviceResult = sellerApplyBrandService.getById(id);
            if (!serviceResult.getSuccess()) {
                throw new RuntimeException(serviceResult.getMessage());
            }
            SellerApplyBrand brand = serviceResult.getResult();
            dataMap.put("brand", brand);
            path = "admin/product/brand/brandaudit";
        }
        return path;
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpJsonResult<Object> update(ProductBrand brand,
                                         MultipartHttpServletRequest request) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }

        if (!StringUtil.is_alpha(brand.getNameFirst())) {
            jsonResult.setMessage("首字母输入错误，请重新输入");
            return jsonResult;
        }

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
            brand.setImage(url);
        }

        brand.setUpdateId(user.getId());
        brand.setNameFirst(brand.getNameFirst().toUpperCase());
        ServiceResult<Boolean> serviceResult = productBrandService.update(brand);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>();
        ServiceResult<Boolean> serviceResult = productBrandService.del(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 平台审核商家提交的品牌信息
     *
     * @param request
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "auditing", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<Object> auditing(HttpServletRequest request,
                                                         @RequestParam("id") Integer id,
                                                         @RequestParam("status") Integer status) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        if (!SellerApplyBrand.Status.chkStatus(status)) {
            jsonResult.setMessage("无效的审核状态，请检查");
        }
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        ServiceResult<Boolean> serviceResult = productBrandService.audit(id, status, user.getId());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 商家商品类型编辑页面初始化ajax调用
     *
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping(value = "getByIds", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<Object> getByIds(HttpServletRequest request,
                                                         @RequestParam("ids") String ids) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        //多个id，构造sql的in部分
        ServiceResult<List<ProductBrand>> serviceResult = productBrandService.getByIds(ids);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        String str = "";
        for (ProductBrand brand : serviceResult.getResult()) {
            str += brand.getName() + ",";
        }
        if (str.length() > 0) {
            str = str.substring(0, str.lastIndexOf(","));
        }
        jsonResult.setRows(str);
        return jsonResult;
    }

}
