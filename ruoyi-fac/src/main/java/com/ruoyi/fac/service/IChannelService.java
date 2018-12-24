package com.ruoyi.fac.service;

import com.ruoyi.fac.domain.Channel;
import java.util.List;

/**
 * 渠道 服务层
 * 
 * @author ruoyi
 * @date 2018-12-24
 */
public interface IChannelService 
{
	/**
     * 查询渠道信息
     * 
     * @param id 渠道ID
     * @return 渠道信息
     */
	public Channel selectChannelById(Integer id);
	
	/**
     * 查询渠道列表
     * 
     * @param channel 渠道信息
     * @return 渠道集合
     */
	public List<Channel> selectChannelList(Channel channel);
	
	/**
     * 新增渠道
     * 
     * @param channel 渠道信息
     * @return 结果
     */
	public int insertChannel(Channel channel);
	
	/**
     * 修改渠道
     * 
     * @param channel 渠道信息
     * @return 结果
     */
	public int updateChannel(Channel channel);
		
	/**
     * 删除渠道信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteChannelByIds(String ids);
	
}
