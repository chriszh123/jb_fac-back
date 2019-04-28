package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/4/25 22:11
 * Description
 */
@Data
public class UserSignLog implements Serializable {
    private static final long serialVersionUID = -5953333299309253445L;

    /**
     * 打卡次数，基本上就是1次
     */
    private int continuous = 0;
    /**
     * eg: 2019-01-28 00:00:00
     */
    private String dateAdd;
    /**
     * 当前签到记录对应的打卡积分
     */
    private int point = 0;
}
