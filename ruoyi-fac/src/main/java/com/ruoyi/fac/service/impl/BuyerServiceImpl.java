package com.ruoyi.fac.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.BuyerMapper;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.common.support.Convert;

/**
 * 买者用户 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class BuyerServiceImpl implements IBuyerService 
{
	@Autowired
	private BuyerMapper buyerMapper;

	/**
     * 查询买者用户信息
     * 
     * @param id 买者用户ID
     * @return 买者用户信息
     */
    @Override
	public Buyer selectBuyerById(Long id)
	{
	    return buyerMapper.selectBuyerById(id);
	}
	
	/**
     * 查询买者用户列表
     * 
     * @param buyer 买者用户信息
     * @return 买者用户集合
     */
	@Override
	public List<Buyer> selectBuyerList(Buyer buyer)
	{
	    return buyerMapper.selectBuyerList(buyer);
	}
	
    /**
     * 新增买者用户
     * 
     * @param buyer 买者用户信息
     * @return 结果
     */
	@Override
	public int insertBuyer(Buyer buyer)
	{
	    return buyerMapper.insertBuyer(buyer);
	}
	
	/**
     * 修改买者用户
     * 
     * @param buyer 买者用户信息
     * @return 结果
     */
	@Override
	public int updateBuyer(Buyer buyer)
	{
	    return buyerMapper.updateBuyer(buyer);
	}

	/**
     * 删除买者用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBuyerByIds(String ids)
	{
		return buyerMapper.deleteBuyerByIds(Convert.toStrArray(ids));
	}
	
}
