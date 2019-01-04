package com.ruoyi.framework.web.service;

import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zgf
 * Date 2019/1/4 23:34
 * Description
 */
@Service("pcs")
public class ProductCategoryService {

    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 查询当前存在的商品类目
     *
     * @return
     */
    public List<ProductCategory> getProductCategorys() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setIsDeleted(0);
        List<ProductCategory> pcs = productCategoryService.selectProductCategoryList(productCategory);
        return pcs;
    }
}
