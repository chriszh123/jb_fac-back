package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Buyer;
import java.util.List;	

/**
 * 买者用户 数据层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface BuyerMapper 
{
	/**
     * 查询买者用户信息
     * 
     * @param id 买者用户ID
     * @return 买者用户信息
     */
	public Buyer selectBuyerById(Integer id);
	
	/**
     * 查询买者用户列表
     * 
     * @param buyer 买者用户信息
     * @return 买者用户集合
     */
	public List<Buyer> selectBuyerList(Buyer buyer);
	
	/**
     * 新增买者用户
     * 
     * @param buyer 买者用户信息
     * @return 结果
     */
	public int insertBuyer(Buyer buyer);
	
	/**
     * 修改买者用户
     * 
     * @param buyer 买者用户信息
     * @return 结果
     */
	public int updateBuyer(Buyer buyer);
	
	/**
     * 删除买者用户
     * 
     * @param id 买者用户ID
     * @return 结果
     */
	public int deleteBuyerById(Integer id);
	
	/**
     * 批量删除买者用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteBuyerByIds(String[] ids);
	
}