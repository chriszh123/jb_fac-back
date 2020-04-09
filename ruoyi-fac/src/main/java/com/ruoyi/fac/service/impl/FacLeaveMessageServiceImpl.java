package com.ruoyi.fac.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.mapper.FacLeaveMessageMapper;
import com.ruoyi.fac.model.FacLeaveMessage;
import com.ruoyi.fac.model.FacLeaveMessageExample;
import com.ruoyi.fac.service.IFacLeaveMessageService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service("facLeaveMessageService")
public class FacLeaveMessageServiceImpl implements IFacLeaveMessageService {

    @Resource
    private FacLeaveMessageMapper leaveMessageMapper;

    @Override
    public List<FacLeaveMessage> listLeaveMessages(FacLeaveMessage leaveMessage) {
        final FacLeaveMessageExample leaveMessageExample = new FacLeaveMessageExample();
        leaveMessageExample.createCriteria().andIsDeletedEqualTo(false);
        leaveMessageExample.setOrderByClause("create_time asc");
        final List<FacLeaveMessage> leaveMessages = this.leaveMessageMapper.selectByExample(leaveMessageExample);

        return leaveMessages;
    }

    @Override
    public int replyLeaveMessage(FacLeaveMessage leaveMessage, SysUser user) {
        if (leaveMessage == null || leaveMessage.getId() == null) {
            throw new FacException("用户留言数据记录id不能为空");
        }
        if (StrUtil.isBlank(leaveMessage.getMngtRemark())) {
            throw new FacException("您的回复信息不能为空");
        }
        leaveMessage.setUpdateTime(DateUtil.date());
        if (user != null) {
            leaveMessage.setOperatorId(user.getUserId());
            leaveMessage.setOperatorName(user.getUserName());
        }
        int rows = this.leaveMessageMapper.updateByPrimaryKeySelective(leaveMessage);
        return rows;
    }
}
