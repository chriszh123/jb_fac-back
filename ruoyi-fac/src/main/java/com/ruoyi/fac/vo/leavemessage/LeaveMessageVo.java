package com.ruoyi.fac.vo.leavemessage;

import lombok.Data;

import java.io.Serializable;


@Data
public class LeaveMessageVo implements Serializable{
    private static final long serialVersionUID = 1574492468110149747L;

    /**
     * 用户留言记录数据wid
     */
    private String leaveMsgWid;
}
