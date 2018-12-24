package com.ruoyi.fac.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.BusinessMapper;
import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.service.IBusinessService;
import com.ruoyi.common.support.Convert;

/**
 * 商家 服务层实现
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class BusinessServiceImpl implements IBusinessService 
{
	@Autowired
	private BusinessMapper businessMapper;

	/**
     * 查询商家信息
     * 
     * @param id 商家ID
     * @return 商家信息
     */
    @Override
	public Business selectBusinessById(Integer id)
	{
	    return businessMapper.selectBusinessById(id);
	}
	
	/**
     * 查询商家列表
     * 
     * @param business 商家信息
     * @return 商家集合
     */
	@Override
	public List<Business> selectBusinessList(Business business)
	{
	    return businessMapper.selectBusinessList(business);
	}
	
    /**
     * 新增商家
     * 
     * @param business 商家信息
     * @return 结果
     */
	@Override
	public int insertBusiness(Business business)
	{
	    return businessMapper.insertBusiness(business);
	}
	
	/**
     * 修改商家
     * 
     * @param business 商家信息
     * @return 结果
     */
	@Override
	public int updateBusiness(Business business)
	{
	    return businessMapper.updateBusiness(business);
	}

	/**
     * 删除商家对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteBusinessByIds(String ids)
	{
		return businessMapper.deleteBusinessByIds(Convert.toStrArray(ids));
	}
	
}
