package com.ruoyi.fac.mapper;

import com.ruoyi.fac.domain.Cash;
import java.util.List;	

/**
 * 买家提现记录 数据层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface CashMapper 
{
	/**
     * 查询买家提现记录信息
     * 
     * @param id 买家提现记录ID
     * @return 买家提现记录信息
     */
	public Cash selectCashById(Integer id);
	
	/**
     * 查询买家提现记录列表
     * 
     * @param cash 买家提现记录信息
     * @return 买家提现记录集合
     */
	public List<Cash> selectCashList(Cash cash);
	
	/**
     * 新增买家提现记录
     * 
     * @param cash 买家提现记录信息
     * @return 结果
     */
	public int insertCash(Cash cash);
	
	/**
     * 修改买家提现记录
     * 
     * @param cash 买家提现记录信息
     * @return 结果
     */
	public int updateCash(Cash cash);
	
	/**
     * 删除买家提现记录
     * 
     * @param id 买家提现记录ID
     * @return 结果
     */
	public int deleteCashById(Integer id);
	
	/**
     * 批量删除买家提现记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCashByIds(String[] ids);
	
}