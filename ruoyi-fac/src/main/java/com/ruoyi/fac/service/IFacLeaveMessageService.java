package com.ruoyi.fac.service;

import com.ruoyi.fac.model.FacLeaveMessage;
import com.ruoyi.system.domain.SysUser;

import java.util.List;

public interface IFacLeaveMessageService {
    /**
     * 查询指定条件下的留言信息
     * @param leaveMessage FacLeaveMessage
     * @return List<FacLeaveMessage>
     */
    List<FacLeaveMessage> listLeaveMessages(FacLeaveMessage leaveMessage);

    /**
     * 管理员回复用户留言
     * @param leaveMessage FacLeaveMessage
     * @param user SysUser
     * @return 影响数据条数
     */
    int replyLeaveMessage(FacLeaveMessage leaveMessage, SysUser user);
}
