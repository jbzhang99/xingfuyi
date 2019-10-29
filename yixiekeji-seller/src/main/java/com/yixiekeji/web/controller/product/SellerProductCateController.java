package com.yixiekeji.web.controller.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.HttpJsonResultForAjax;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.entity.seller.SellerManageCate;
import com.yixiekeji.entity.seller.SellerUser;
import com.yixiekeji.entity.system.ResourceTree;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductTypeService;
import com.yixiekeji.service.product.ISellerManageCateService;
import com.yixiekeji.service.product.ISellerTypeLogsService;
import com.yixiekeji.vo.product.ProductCateVO;
import com.yixiekeji.web.controller.BaseController;
import com.yixiekeji.web.csrf.CsrfTokenManager;
import com.yixiekeji.web.util.WebSellerSession;

/**
 * 商品分类申请
 */
@Controller
@RequestMapping(value = "seller/product/cate")
public class SellerProductCateController extends BaseController {
    private String                   baseUrl = "seller/product/cate/";

    @Resource
    private IProductCateService      productCateService;
    @Resource
    private ISellerManageCateService sellerManageCateService;
    @Resource
    private IProductTypeService      productTypeService;
    @Resource
    private ISellerTypeLogsService   sellerTypeLogsService;

    @RequestMapping(value = "", method = { RequestMethod.GET })
    public String index(Map<String, Object> dataMap) throws Exception {
        dataMap.put("q_useYn", "1");
        dataMap.put("pageSize", ConstantsEJS.DEFAULT_PAGE_SIZE);
        return baseUrl + "catelist";
    }

    @RequestMapping(value = "getByPid", method = { RequestMethod.POST, RequestMethod.GET })
    public @ResponseBody List<ProductCateVO> getByPid(HttpServletRequest request,
                                                      @RequestParam(value = "id", required = true) Integer pid) {
        HttpJsonResult<List<ProductCateVO>> jsonResult = new HttpJsonResult<List<ProductCateVO>>();
        ServiceResult<List<ProductCateVO>> serviceResult = productCateService.getByPidAndSeller(pid,
            WebSellerSession.getSellerUser(request).getSellerId());
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        jsonResult.setRows(serviceResult.getResult());
        jsonResult.setTotal(serviceResult.getResult().size());
        return serviceResult.getResult();
    }

    /**
     * 提交审核
     */
    @RequestMapping(value = "commit", method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Object> commit(HttpServletRequest request, Integer cateId) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResultForAjax<Object>(null,
            CsrfTokenManager.getTokenForSession(CsrfTokenManager.getMemkeyFromRequest(request),
                request.getSession()));
        SellerUser sellerUser = WebSellerSession.getSellerUser(request);

        SellerManageCate manager = new SellerManageCate();
        manager.setCreateId(sellerUser.getId());
        manager.setSeller(sellerUser.getSellerId());
        manager.setProductCateId(cateId);
        manager.setState(SellerManageCate.Status.COMMIT.getValue());
        ServiceResult<Boolean> serviceResult = sellerManageCateService
            .saveSellerManageCate(manager);
        if (!serviceResult.getSuccess()) {
            jsonResult.setMessage(serviceResult.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "productCaseTree", method = { RequestMethod.GET })
    public @ResponseBody List<ResourceTree> productCaseTree(HttpServletRequest request,
                                                            @RequestParam(value = "id", required = true) Integer pid) {
        List<ResourceTree> tree = new ArrayList<ResourceTree>();
        //        ServiceResult<List<ProductCate>> serviceResult = productCateService.getByPid(pid);
        Map<String, Object> param = new HashMap<>();
        param.put("status", "1");
        ServiceResult<List<ProductCate>> serviceResult = productCateService.getProductCate(param);
        generateTree(tree, getByPid(serviceResult.getResult(), pid), serviceResult.getResult());
        return tree;
    }

    /**
     * 递归生成树
     * @param treelist
     * @param data
     * @param list 
     * @return
     */
    private List<ResourceTree> generateTree(List<ResourceTree> treelist, List<ProductCate> data,
                                            List<ProductCate> alldata) {
        for (ProductCate sr : data) {
            ResourceTree tree = new ResourceTree();
            tree.setId(sr.getId());
            tree.setText(sr.getName());
            //            tree.setChildren(generateTree(new ArrayList<ResourceTree>(),
            //                productCateService.getByPid(sr.getId()).getResult()));
            //所有子数据均是alldata的子集
            tree.setChildren(generateTree(new ArrayList<ResourceTree>(),
                getByPid(alldata, sr.getId().intValue()), alldata));
            tree.setState(tree.getChildren().size() > 0 ? "closed" : "open");
            treelist.add(tree);
        }
        return treelist;
    }

    private List<ProductCate> getByPid(List<ProductCate> data, int pid) {
        List<ProductCate> list = new ArrayList<>();
        for (ProductCate cate : data) {
            if (pid == cate.getPid().intValue()) {
                list.add(cate);
            }
        }
        return list;
    }
}
