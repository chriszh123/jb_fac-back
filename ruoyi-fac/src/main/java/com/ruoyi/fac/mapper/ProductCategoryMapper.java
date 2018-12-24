package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.ProductCategory;
import java.util.List;	

/**
 * 商品类目 数据层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface ProductCategoryMapper 
{
	/**
     * 查询商品类目信息
     * 
     * @param id 商品类目ID
     * @return 商品类目信息
     */
	public ProductCategory selectProductCategoryById(Integer id);
	
	/**
     * 查询商品类目列表
     * 
     * @param productCategory 商品类目信息
     * @return 商品类目集合
     */
	public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory);
	
	/**
     * 新增商品类目
     * 
     * @param productCategory 商品类目信息
     * @return 结果
     */
	public int insertProductCategory(ProductCategory productCategory);
	
	/**
     * 修改商品类目
     * 
     * @param productCategory 商品类目信息
     * @return 结果
     */
	public int updateProductCategory(ProductCategory productCategory);
	
	/**
     * 删除商品类目
     * 
     * @param id 商品类目ID
     * @return 结果
     */
	public int deleteProductCategoryById(Integer id);
	
	/**
     * 批量删除商品类目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProductCategoryByIds(String[] ids);
	
}