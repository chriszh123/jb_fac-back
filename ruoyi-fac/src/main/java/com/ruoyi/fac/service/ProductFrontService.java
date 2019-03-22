/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/3/22
 * Description: 前端html直接调用这里的服务接口
 */
package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.domain.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前端html直接调用这里的服务接口
 *
 * @author zhangguifeng
 * @create 2019-03-22 14:10
 **/
@Service("pfs")
public class ProductFrontService {
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
