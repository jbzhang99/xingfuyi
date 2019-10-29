package com.yixiekeji.vo.product;

import java.io.Serializable;
import java.util.List;

import com.yixiekeji.entity.product.ProductCate;

public class FrontProductCateVO extends ProductCate implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long        serialVersionUID = -2741089276060934306L;
    private List<FrontProductCateVO> childs;

    public List<FrontProductCateVO> getChilds() {
        return childs;
    }

    public void setChilds(List<FrontProductCateVO> childs) {
        this.childs = childs;
    }

}
