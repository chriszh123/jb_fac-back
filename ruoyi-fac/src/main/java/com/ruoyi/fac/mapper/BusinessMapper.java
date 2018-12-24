package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Business;
import java.util.List;	

/**
 * 商家 数据层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface BusinessMapper 
{
	/**
     * 查询商家信息
     * 
     * @param id 商家ID
     * @return 商家信息
     */
	public Business selectBusinessById(Integer id);
	
	/**
     * 查询商家列表
     * 
     * @param business 商家信息
     * @return 商家集合
     */
	public List<Business> selectBusinessList(Business business);
	
	/**
     * 新增商家
     * 
     * @param business 商家信息
     * @return 结果
     */
	public int insertBusiness(Business business);
	
	/**
     * 修改商家
     * 
     * @param business 商家信息
     * @return 结果
     */
	public int updateBusiness(Business business);
	
	/**
     * 删除商家
     * 
     * @param id 商家ID
     * @return 结果
     */
	public int deleteBusinessById(Integer id);
	
	/**
     * 批量删除商家
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBusinessByIds(String[] ids);
	
}