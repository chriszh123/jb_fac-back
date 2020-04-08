package com.ruoyi.fac.service.impl;

import com.ruoyi.fac.model.FacLeaveMessage;
import com.ruoyi.fac.service.IFacLeaveMessageService;
import com.ruoyi.system.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("facLeaveMessageService")
public class FacLeaveMessageServiceImpl implements IFacLeaveMessageService {

    @Override
    public List<FacLeaveMessage> listLeaveMessages(FacLeaveMessage leaveMessage) {
        return null;
    }

    @Override
    public int replyLeaveMessage(FacLeaveMessage leaveMessage, SysUser user) {
        return 0;
    }
}
