package com.ruoyi.fac.service;

import com.ruoyi.fac.model.FacBuyerSign;

import java.util.List;

/**
 * 用户签到等积分 服务层
 * 
 * @author ruoyi
 * @date 2019-05-26
 */
public interface IFacBuyerSignService 
{
	/**
     * 查询用户签到等积分列表
     * 
     * @param facBuyerSign 用户签到等积分信息
     * @return 用户签到等积分集合
     */
	public List<FacBuyerSign> selectFacBuyerSignList(FacBuyerSign facBuyerSign);
}
