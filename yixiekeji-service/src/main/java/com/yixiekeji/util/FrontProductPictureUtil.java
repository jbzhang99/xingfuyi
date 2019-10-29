package com.yixiekeji.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yixiekeji.core.ConstantsEJS;
import com.yixiekeji.entity.product.ProductPicture;

public class FrontProductPictureUtil {

    /**
     * 生成新的文件名称，如果目录不存在，则生成目录
     * @param oldName 旧的名称
     * @param pathName 在最后目录下添加 little或者middle
     * @return
     */
    public static String newFileName(String oldName, String pathName) {
        String[] strings = oldName.split("/");
        StringBuilder sb = new StringBuilder();
        int count = strings.length;
        for (int i = 0; i < count; i++) {
            sb.append(strings[i]);
            if ((i + 2) == count) {
                sb.append("/");
                sb.append(pathName);
                sb.append("/");
                File newFilePathFile = new File(sb.toString());
                if (!newFilePathFile.exists()) {
                    newFilePathFile.mkdirs();
                }
            } else if ((i + 1) != count) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    /**
     * 根据商品ID获取商品的主图（中图）
     * @param productId
     * @return
     */
    public static String getproductLeadMiddle(ProductPicture productPicture) {
        if (productPicture == null) {
            return "";
        }
        String pathPicture = productPicture.getImagePath();
        if (null == pathPicture || "".equals(pathPicture)) {
            return "";
        }

        return newFileName(pathPicture, ConstantsEJS.PRODUCT_MIDDLE);
    }

    public static String getproductLeadLittle(ProductPicture productPicture) {
        if (productPicture == null) {
            return "";
        }
        String pathPicture = productPicture.getImagePath();
        if (null == pathPicture || "".equals(pathPicture)) {
            return "";
        }

        return newFileName(pathPicture, ConstantsEJS.PRODUCT_LITTLE);
    }

    /**
     * 根据商品ID获取商品图片(大图)
     * @param productId 商品ID
     * @return
     */
    public static List<String> getByProductIds(List<ProductPicture> listPicture) {
        List<String> list = new ArrayList<String>();
        for (ProductPicture productPicture : listPicture) {
            list.add(productPicture.getImagePath());
        }
        return list;
    }

}
