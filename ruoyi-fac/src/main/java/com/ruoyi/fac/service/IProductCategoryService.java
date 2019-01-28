package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.vo.client.CategoryVo;

import java.util.List;

/**
 * 商品类目 服务层
 *
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IProductCategoryService {
    /**
     * 查询商品类目信息
     *
     * @param id 商品类目ID
     * @return 商品类目信息
     */
    ProductCategory selectProductCategoryById(Integer id);

    /**
     * 查询商品类目列表
     *
     * @param productCategory 商品类目信息
     * @return 商品类目集合
     */
    List<ProductCategory> selectProductCategoryList(ProductCategory productCategory);

    /**
     * 新增商品类目
     *
     * @param productCategory 商品类目信息
     * @return 结果
     */
    int insertProductCategory(ProductCategory productCategory);

    /**
     * 修改商品类目
     *
     * @param productCategory 商品类目信息
     * @return 结果
     */
    int updateProductCategory(ProductCategory productCategory);

    /**
     * 删除商品类目信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteProductCategoryByIds(String ids);

    /**
     * 查询当前商品分类信息
     *
     * @return
     */
    List<CategoryVo> categoryList();
}
