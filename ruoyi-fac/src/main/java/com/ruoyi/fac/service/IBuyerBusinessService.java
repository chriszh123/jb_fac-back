package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.BuyerBusiness;
import java.util.List;

/**
 * 买者与商家商品关联 服务层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IBuyerBusinessService 
{
	/**
     * 查询买者与商家商品关联信息
     * 
     * @param id 买者与商家商品关联ID
     * @return 买者与商家商品关联信息
     */
	public BuyerBusiness selectBuyerBusinessById(Integer id);
	
	/**
     * 查询买者与商家商品关联列表
     * 
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 买者与商家商品关联集合
     */
	public List<BuyerBusiness> selectBuyerBusinessList(BuyerBusiness buyerBusiness);
	
	/**
     * 新增买者与商家商品关联
     * 
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 结果
     */
	public int insertBuyerBusiness(BuyerBusiness buyerBusiness);
	
	/**
     * 修改买者与商家商品关联
     * 
     * @param buyerBusiness 买者与商家商品关联信息
     * @return 结果
     */
	public int updateBuyerBusiness(BuyerBusiness buyerBusiness);
		
	/**
     * 删除买者与商家商品关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBuyerBusinessByIds(String ids);
	
}
