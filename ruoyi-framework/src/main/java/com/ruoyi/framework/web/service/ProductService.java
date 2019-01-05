package com.ruoyi.framework.web.service;

import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.service.IBusinessService;
import com.ruoyi.fac.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品相关附件信息
 * Created by zgf
 * Date 2019/1/4 23:34
 * Description
 */
@Service("ps")
public class ProductService {

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IBusinessService businessService;

    /**
     * 查询当前存在的商品类目
     *
     * @return List<ProductCategory>
     */
    public List<ProductCategory> getProductCategorys() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setIsDeleted(0);
        List<ProductCategory> pcs = this.productCategoryService.selectProductCategoryList(productCategory);
        return pcs;
    }

    /**
     * 获取当前有效商家列表
     *
     * @return List<Business>
     */
    public List<Business> getBusinesses() {
        Business business = new Business();
        business.setIsDeleted(0);
        List<Business> businesses = this.businessService.selectBusinessList(business);
        return businesses;
    }

}
