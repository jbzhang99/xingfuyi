package com.yixiekeji.web.controller.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.core.ConvertUtil;
import com.yixiekeji.core.HttpJsonResult;
import com.yixiekeji.core.PagerInfo;
import com.yixiekeji.core.PaginationFrontUtil;
import com.yixiekeji.core.ServiceResult;
import com.yixiekeji.entity.product.Product;
import com.yixiekeji.entity.product.ProductBrand;
import com.yixiekeji.service.product.IProductBrandService;
import com.yixiekeji.service.product.IProductFrontService;
import com.yixiekeji.web.controller.BaseController;

/**
 * 品牌页controller
 * 
 * @Filename: ProductBrandController.java
 * @Version: 1.0
 * @Author: 陈万海
 * @Email: chenwanhai@sina.com
 *
 */
@Controller
public class ProductBrandController extends BaseController {

    @Resource
    private IProductFrontService productFrontService;
    @Resource
    private IProductBrandService productBrandService;

    /**
     * 品牌列表页
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(value = "/brand.html", method = { RequestMethod.GET })
    public String brandList(HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> dataMap) {

        // 获取推荐品牌
        ServiceResult<List<ProductBrand>> hotResult = productBrandService.getHotBrands();
        dataMap.put("hotBrands", hotResult.getResult());

        // 获取所有按字母分组的品牌
        ServiceResult<Map<String, List<ProductBrand>>> allResult = productBrandService
            .getBrandsLetterGrouping();
        dataMap.put("groupBrands", allResult.getResult());

        return "front/brand/brand";
    }

    /**
     * 品牌页访问主函数，为了SEO，URL为数字的拼装
     * 完整URL规则www.yixiekeji.com/品牌ID-分页-排序-自营非自营－有货无货.html<br/>
     *                 排序sort 0:默认排序；1销量；2评论；3价格从低到高；4、价格从高到低<br/>
     *                 自营非自营：0所有商品；1自营商品<br/>
     *                 有货无货：0所有商品；1有货商品
     * @param visitPath
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "/brand/{visitPath}.html", method = RequestMethod.GET)
    public String productList(@PathVariable String visitPath, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> dataMap) {
        String[] arrVisitPath = visitPath.split("-");
        int arrVisitPathLength = arrVisitPath.length;
        // 长度不等于1并且不等于5 url错误
        if (arrVisitPathLength != 1 && arrVisitPathLength != 5) {
            return "redirect:/error.html";
        }
        // 品牌
        int brandId = ConvertUtil.toInt(arrVisitPath[0], 0);
        dataMap.put("brandId", brandId);

        // 分页
        int page = 1;
        // 排序
        int sort = 0;
        // 自营非自营
        int doSelf = 0;
        // 有货无货
        int store = 0;
        if (arrVisitPathLength == 5) {
            page = ConvertUtil.toInt(arrVisitPath[1], 1);
            page = page < 1 ? 1 : page;
            sort = ConvertUtil.toInt(arrVisitPath[2], 0);
            doSelf = ConvertUtil.toInt(arrVisitPath[3], 0);
            store = ConvertUtil.toInt(arrVisitPath[4], 0);
        }
        dataMap.put("page", page);
        dataMap.put("sort", sort);
        dataMap.put("doSelf", doSelf);
        dataMap.put("store", store);

        String sbUrlPath = brandId + "-" + page + "-" + sort + "-" + doSelf + "-" + store;

        dataMap.put("urlPath", sbUrlPath.toString());

        PagerInfo pager = new PagerInfo(ConstantsEJS.DEFAULT_PAGE_SIZE, page);

        ServiceResult<List<Product>> productResult = productFrontService
            .getProductsByBrandId(brandId, sort, doSelf, store, pager);

        if (!productResult.getSuccess()) {
            dataMap.put("info", productResult.getMessage());
            return "front/commons/error";
        }
        dataMap.put("productList", productResult.getResult());

        pager = productResult.getPager();

        //分页对象
        PaginationFrontUtil pm = new PaginationFrontUtil(page == 0 ? 1 : page, pager.getPageSize(),
            pager.getRowsCount());

        dataMap.put("page", pm);

        return "front/brand/brandproductlist";
    }

    /**
     * 根据品牌查询列表页推荐的头部商品
     * @param brandId
     * @param request
     * @param response
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "brandtop.html", method = { RequestMethod.GET })
    public @ResponseBody HttpJsonResult<List<Product>> brandTop(Integer brandId,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> dataMap) {
        HttpJsonResult<List<Product>> jsonResult = new HttpJsonResult<List<Product>>();

        ServiceResult<List<Product>> serviceResult = productFrontService
            .getProductsByBrandIdTop(brandId);

        List<Product> productsTop = new ArrayList<Product>();
        if (serviceResult.getSuccess()) {
            productsTop = serviceResult.getResult();
        }

        jsonResult.setData(productsTop);
        jsonResult.setTotal(productsTop.size());
        return jsonResult;
    }
}
