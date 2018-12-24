package com.ruoyi.fac.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.ProductCategoryMapper;
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.service.IProductCategoryService;
import com.ruoyi.common.support.Convert;

/**
 * 商品类目 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ProductCategoryServiceImpl implements IProductCategoryService 
{
	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	/**
     * 查询商品类目信息
     * 
     * @param id 商品类目ID
     * @return 商品类目信息
     */
    @Override
	public ProductCategory selectProductCategoryById(Integer id)
	{
	    return productCategoryMapper.selectProductCategoryById(id);
	}
	
	/**
     * 查询商品类目列表
     * 
     * @param productCategory 商品类目信息
     * @return 商品类目集合
     */
	@Override
	public List<ProductCategory> selectProductCategoryList(ProductCategory productCategory)
	{
	    return productCategoryMapper.selectProductCategoryList(productCategory);
	}
	
    /**
     * 新增商品类目
     * 
     * @param productCategory 商品类目信息
     * @return 结果
     */
	@Override
	public int insertProductCategory(ProductCategory productCategory)
	{
	    return productCategoryMapper.insertProductCategory(productCategory);
	}
	
	/**
     * 修改商品类目
     * 
     * @param productCategory 商品类目信息
     * @return 结果
     */
	@Override
	public int updateProductCategory(ProductCategory productCategory)
	{
	    return productCategoryMapper.updateProductCategory(productCategory);
	}

	/**
     * 删除商品类目对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProductCategoryByIds(String ids)
	{
		return productCategoryMapper.deleteProductCategoryByIds(Convert.toStrArray(ids));
	}
	
}
