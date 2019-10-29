package com.yixiekeji.web.controller.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.product.ProductType;
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.entity.seller.SellerTypeLogs;
import com.yixiekeji.entity.system.ResourceTree;
import com.yixiekeji.entity.system.SystemAdmin;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductTypeService;
import com.yixiekeji.service.product.ISellerTypeLogsService;
import com.yixiekeji.vo.product.ProductCateVO;
import com.yixiekeji.web.config.DomainUrlUtil;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.util.UploadUtil;
import com.yixiekeji.web.util.WebAdminSession;

/**
 * 商品分类
 */
@Controller
@RequestMapping(value = "admin/product/cate")
public class CateController extends BaseController {
    private String                 baseUrl = "admin/product/cate/";

    @Resource
    private IProductCateService    productCateService;
    @Resource
    private IProductTypeService    productTypeService;
    @Resource
    private ISellerTypeLogsService sellerTypeLogsService;

    @Resource
    private DomainUrlUtil          domainUrlUtil;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "catelist";
    }

    @RequestMapping(value = "audit", method = { RequestMethod.GET })
    public String audit(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "auditlist";
    }

    /**
     * 商家分类申请审核
     * @param res
     * @param id
     * @param type
     */
    @RequestMapping(value = "auditPass", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<Boolean> auditPass(HttpServletResponse res, String ids,
                                                           Integer type) {
        HttpJsonResult<Boolean> jsonResult = new HttpJsonResult<Boolean>();
        try {
            if (isNull(ids))
                throw new BusinessException("数据错误");
            for (String id : ids.split(",")) {
                SellerManageCate cate = productCateService.getSellerManageCate(Integer.valueOf(id));
                if (type == ConstantsEJS.SELLER_MANAGE_CATE_STATE_2)
                    cate.setState(ConstantsEJS.SELLER_MANAGE_CATE_STATE_2);
                else if (type == ConstantsEJS.SELLER_MANAGE_CATE_STATE_3)
                    cate.setState(ConstantsEJS.SELLER_MANAGE_CATE_STATE_3);

                productCateService.updateSellerManageCate(cate);
            }
        } catch (Exception e) {
            if (e instanceof BusinessException)
                jsonResult.setMessage(e.getMessage());
            else
                e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping(value = "list", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<List<ProductCateVO>> list(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Map<String, Object> dataMap) {

        Map<String, String> queryMap = WebUtil.handlerQueryMap(request);
        PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
        FeignUtil feignUtil = FeignUtil.getFeignUtil(queryMap, pager);

        ServiceResult<List<ProductCateVO>> serviceResult = productCateService
            .pageProductCate(feignUtil);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            } else {
                throw new BusinessException(serviceResult.getMessage());
            }
        }
        pager = serviceResult.getPager();

        HttpJsonResult<List<ProductCateVO>> jsonResult = new HttpJsonResult<List<ProductCateVO>>();
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(pager.getRowsCount());
        return jsonResult;
    }

    @RequestMapping(value = "getByPid", method = { RequestMethod.POST,
                                                   RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody List<ProductCate> getByPid(@RequestParam(value = "id", required = true) Integer pid) {
        HttpJsonResult<List<ProductCate>> jsonResult = new HttpJsonResult<List<ProductCate>>();
        ServiceResult<List<ProductCate>> serviceResult = productCateService.getByPid(pid);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return serviceResult.getResult();
    }

    @RequestMapping(value = "productCaseTree", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    public @ResponseBody List<ResourceTree> productCaseTree(HttpServletRequest request,
                                                            @RequestParam(value = "id", required = true) Integer pid) {
        List<ResourceTree> tree = new ArrayList<ResourceTree>();
        ServiceResult<List<ProductCate>> serviceResult = productCateService.getByPid(pid);
        generateTree(tree, serviceResult.getResult());
        return tree;
    }

    /**
     * 递归生成树
     * @param treelist
     * @param data
     * @return
     */
    private List<ResourceTree> generateTree(List<ResourceTree> treelist, List<ProductCate> data) {
        for (ProductCate sr : data) {
            ResourceTree tree = new ResourceTree();
            tree.setId(sr.getId());
            tree.setText(sr.getName());
            tree.setChildren(generateTree(new ArrayList<ResourceTree>(),
                productCateService.getByPid(sr.getId()).getResult()));
            tree.setState(tree.getChildren().size() > 0 ? "closed" : "open");
            treelist.add(tree);
        }
        return treelist;
    }

    @RequestMapping(value = "add", method = { RequestMethod.GET })
    public String add(Map<String, Object> dataMap) {
        ServiceResult<List<ProductCate>> serviceResult = productCateService.getByPid(0);
        if (!serviceResult.getSuccess()) {
        }
        dataMap.put("productCates", serviceResult.getResult());
        return baseUrl + "cateadd";
    }

    @RequestMapping(value = "create", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpJsonResult<Object> create(ProductCate cate, MultipartHttpServletRequest request,
                                         Map<String, Object> dataMap) throws IOException {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }
        cate.setCreateId(user.getId());
        cate.setCreater(user.getName());
        cate.setUpdateId(user.getId());
        cate.setStatus(1);
        //设置path
        cate.setPath(buildPath(cate.getPid()));

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
            cate.setImage(url);
            tmpFile.delete();
        }

        ServiceResult<Boolean> serviceResult = productCateService.saveProductCate(cate);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
            return jsonResult;
        }
        return jsonResult;
    }

    private String buildImgPath(HttpServletRequest request) throws FileNotFoundException {
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
    public String edit(Integer id, Map<String, Object> dataMap) {
        ServiceResult<ProductCate> serviceResult = productCateService.getProductCateById(id);
        if (!serviceResult.getSuccess()) {
            if (ConstantsEJS.SERVICE_RESULT_CODE_SYSERROR.equals(serviceResult.getCode())) {
                throw new RuntimeException(serviceResult.getMessage());
            }
        }
        /**1.商品分类*/
        dataMap.put("cate", serviceResult.getResult());

        /**2.商品类型名称*/
        ServiceResult<ProductType> result = productTypeService
            .getProductTypeById(serviceResult.getResult().getProductTypeId());
        if (result.getSuccess() && result.getResult() != null) {
            dataMap.put("typeName", result.getResult().getName());
        }

        /**3.构造上级分类*/
        String[] cateIds = serviceResult.getResult().getPath().split("/");
        if (cateIds.length == 0) {
            //只构造出一级分类
            ServiceResult<List<ProductCate>> result1 = productCateService.getByPid(0);//一级分类

            //删除当前分类
            List<ProductCate> list = result1.getResult();
            Iterator<ProductCate> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getId() == id)
                    iterator.remove();
            }
            dataMap.put("B", list);
        }
        if (cateIds.length > 0) {
            Map<String, Object> cateMap = new HashMap<String, Object>();
            for (int i = 0; i < cateIds.length - 1; i++) {
                ServiceResult<List<ProductCate>> result1 = new ServiceResult<List<ProductCate>>();
                if (StringUtil.isEmpty(cateIds[i])) {
                    result1 = productCateService.getByPid(0);//一级分类
                } else {
                    result1 = productCateService.getByPid(Integer.valueOf(cateIds[i]));
                }
                if (result1.getSuccess() && result1.getResult() != null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("cateId", cateIds[i + 1]);
                    map.put("cateList", result1.getResult());
                    if (i == 0) {
                        cateMap.put("B", map);//大类
                    } else if (i == 1) {
                        cateMap.put("M", map);//中类
                    } else {
                        cateMap.put("S", map);//小类
                    }
                }
            }
            dataMap.put("parentCate", cateMap);
        }
        /**4.构造商家类型修改日志表*/
        ServiceResult<List<SellerTypeLogs>> logResult = sellerTypeLogsService
            .getSellerTypeLogsByCateId(id);
        if (logResult.getSuccess() && logResult.getResult() != null) {
            dataMap.put("log", logResult.getResult());
        }
        return baseUrl + "cateedit";
    }

    @RequestMapping(value = "update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public HttpJsonResult<Object> update(ProductCate cate,
                                         MultipartHttpServletRequest request) throws Exception {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>();
        SystemAdmin user = WebAdminSession.getAdminUser(request);
        if (null == user) {
            jsonResult.setMessage("登录超时，请重新登录");
            jsonResult.setBackUrl(domainUrlUtil.getUrlResources() + "/admin/login");
            return jsonResult;
        }
        cate.setUpdateId(user.getId());
        cate.setUpdater(user.getName());
        cate.setUpdateTime(new Date());
        cate.setPath(buildPath(cate.getPid()));

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
            cate.setImage(url);
            tmpFile.delete();
        }

        ServiceResult<Boolean> serviceResult = productCateService.updateProductCate(cate);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody HttpJsonResult<Object> del(HttpServletRequest request,
                                                    @RequestParam("id") Integer id) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        ServiceResult<Boolean> serviceResult = productCateService.delProductCate(id);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    /**
     * 根据pid构造上级path
     * @param pid
     * @return
     */
    private String buildPath(Integer pid) {
        String path = "";
        ServiceResult<ProductCate> result = productCateService.getProductCateById(pid);
        if (result.getResult() == null) {
            path = "/";//一级分类
        } else {
            if ("/".equals(result.getResult().getPath())) {
                path = result.getResult().getPath() + pid;
            } else {
                path = result.getResult().getPath() + "/" + pid;
            }
        }
        return path;
    }

}
