package com.yixiekeji.web.controller.product;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationFrontUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductCate;
import com.yixiekeji.service.product.IProductCateService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 二级分类列表页
 *                       
 * @Filename: ProductCateListController.java
 * @Version: 1.0
 * @Author: 王朋
 * @Email: wpjava@163.com
 *
 */
@Controller
public class ProductCateListController extends BaseController {

    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private IProductCateService  productCateService;

    @RequestMapping(value = "/list/{visitPath}.html", method = RequestMethod.GET)
    public String cate(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String visitPath, Map<String, Object> stack) {

        // visitPath为1-1-1形式，第一个为二级分类ID，第二个为页码，第三个为排序
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        if (arrVisitPathLength != 1 && arrVisitPathLength != 3) { //长度不等于1和3url错误
            return "redirect:/error.html";
        }

        Integer cateId = 0;
        Integer pageIndex = 1;
        Integer sort = 0;
        if (arrVisitPathLength == 1) {
            String cateIdStr = arrVisitPath[0];
            cateId = ConvertUtil.toInt(cateIdStr, 0);
        } else {
            String cateIdStr = arrVisitPath[0];
            String pageIndexStr = arrVisitPath[1];
            String sortStr = arrVisitPath[2];
            cateId = ConvertUtil.toInt(cateIdStr, 0);
            pageIndex = ConvertUtil.toInt(pageIndexStr, 1);
            // 0-默认，1-价格升序，2-价格降序，3-销量降序，4-评价降序
            sort = ConvertUtil.toInt(sortStr, 0);
        }

        // 二级分类
        ServiceResult<ProductCate> prodCatesResult = productCateService.getProductCateById(cateId);
        if (!prodCatesResult.getSuccess()) {
            log.error("根据分类ID获取分类时发生异常：" + prodCatesResult.getMessage());
            return "redirect:/error.html";
        }
        ProductCate prodCate = prodCatesResult.getResult();
        if (prodCate == null) {
            log.error("根据分类ID获取分类时失败。");
            return "redirect:/error.html";
        }
        stack.put("productCate", prodCate);

        // 二级分类下的三级分类
        ServiceResult<List<ProductCate>> childCateRlt = productCateService.getByPid(cateId);
        stack.put("childCates", childCateRlt.getResult());

        // 组合分类路径查询商品
        String productCatePath = prodCate.getPath() + "/" + prodCate.getId();
        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, pageIndex);
        ServiceResult<List<Product>> prdRlt = productFrontService.getProductByPath(productCatePath,
            sort, pager);
        List<Product> list = prdRlt.getResult();
        pager = prdRlt.getPager();

        String sbUrlPath = cateId + "-" + pageIndex + "-" + sort;
        stack.put("urlPath", sbUrlPath.toString());

        //分页对象
        PaginationFrontUtil pm = new PaginationFrontUtil(pager.getPageIndex(), pager.getPageSize(),
            pager.getRowsCount());

        stack.put("productList", list);
        stack.put("page", pm);
        stack.put("sort", sort);

        return "front/product/productcatelist";
    }
}
