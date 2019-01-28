package com.ruoyi.fac.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.fac.enums.FocusStatus;
import com.ruoyi.fac.util.TimeUtils;
import com.ruoyi.fac.vo.client.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.ProductCategoryMapper;
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.service.IProductCategoryService;
import com.ruoyi.common.support.Convert;
import org.springframework.util.CollectionUtils;

/**
 * 商品类目 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 查询商品类目信息
     *
     * @param id 商品类目ID
     * @return 商品类目信息
     */
    @Override
    public ProductCategory selectProductCategoryById(Integer id) {
        return productCategoryMapper.selectProductCategoryById(id);
    }

    /**
     * 查询商品类目列表
     *
     * @param productCategory 商品类目信息
     * @return 商品类目集合
     */
    @Override
    public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory) {
        productCategory.setIsDeleted(0);
        return productCategoryMapper.selectProductCategoryList(productCategory);
    }

    /**
     * 新增商品类目
     *
     * @param productCategory 商品类目信息
     * @return 结果
     */
    @Override
    public int insertProductCategory(ProductCategory productCategory) {
        productCategory.setPicture(productCategory.getImgPath());
        productCategory.setStatus(FocusStatus.VISIBLE.getValue());
        return productCategoryMapper.insertProductCategory(productCategory);
    }

    /**
     * 修改商品类目
     *
     * @param productCategory 商品类目信息
     * @return 结果
     */
    @Override
    public int updateProductCategory(ProductCategory productCategory) {
        productCategory.setPicture(productCategory.getImgPath());
        return productCategoryMapper.updateProductCategory(productCategory);
    }

    /**
     * 删除商品类目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductCategoryByIds(String ids) {
        return productCategoryMapper.deleteProductCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询当前商品分类信息
     *
     * @return
     */
    @Override
    public List<CategoryVo> categoryList() {
        List<CategoryVo> categoryVos = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setIsDeleted(0);
        List<ProductCategory> categories = productCategoryMapper.selectProductCategoryList(productCategory);
        if (!CollectionUtils.isEmpty(categories)) {
            ProductCategory category = null;
            for (int i = 0, size = categories.size(); i < size; i++) {
                category = categories.get(i);
                CategoryVo categoryVo = new CategoryVo();
                categoryVos.add(categoryVo);
                categoryVo.setDateAdd(TimeUtils.date2Str(category.getCreateTime(), TimeUtils.DEFAULT_DATE_TIME_FORMAT_HH_MM_SS));
                categoryVo.setIcon(category.getPicture());
                categoryVo.setId(category.getId());
                categoryVo.setUse(category.getStatus().intValue() == 1);
                categoryVo.setKey("");
                categoryVo.setLevel(1);
                categoryVo.setName(category.getName());
                categoryVo.setPaixu(category.getSort());
                categoryVo.setPid(0);
                categoryVo.setType("");
                categoryVo.setUserId(category.getOperatorId());
            }
        }

        return categoryVos;
    }

}
