package com.ruoyi.fac.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fac.mapper.ChannelMapper;
import com.ruoyi.fac.domain.Channel;
import com.ruoyi.fac.service.IChannelService;
import com.ruoyi.common.support.Convert;

/**
 * 渠道 服务层实现
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Service
public class ChannelServiceImpl implements IChannelService {
    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 查询渠道信息
     *
     * @param id 渠道ID
     * @return 渠道信息
     */
    @Override
    public Channel selectChannelById(Integer id) {
        return channelMapper.selectChannelById(id);
    }

    /**
     * 查询渠道列表
     *
     * @param channel 渠道信息
     * @return 渠道集合
     */
    @Override
    public List<Channel> selectChannelList(Channel channel) {
        channel.setIsDeleted(0);
        return channelMapper.selectChannelList(channel);
    }

    /**
     * 新增渠道
     *
     * @param channel 渠道信息
     * @return 结果
     */
    @Override
    public int insertChannel(Channel channel) {
        Date now = new Date();
        channel.setUpdateTime(now);
        channel.setUpdateTime(now);
        channel.setIsDeleted(0);
        return channelMapper.insertChannel(channel);
    }

    /**
     * 修改渠道
     *
     * @param channel 渠道信息
     * @return 结果
     */
    @Override
    public int updateChannel(Channel channel) {
        Date now = new Date();
        channel.setUpdateTime(now);
        return channelMapper.updateChannel(channel);
    }

    /**
     * 删除渠道对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteChannelByIds(String ids) {
        return channelMapper.deleteChannelByIds(Convert.toStrArray(ids));
    }

}
