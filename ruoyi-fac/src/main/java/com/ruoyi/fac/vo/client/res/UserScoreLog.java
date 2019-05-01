package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/5/1 19:04
 * Description
 */
@Data
public class UserScoreLog implements Serializable {
    private static final long serialVersionUID = 4523438212232090553L;

    private String typeStr = "";
    private String remark = "";
    private String dateAdd;
    /**
     * 0为奖励积分，1为消费积分
     */
    private int behavior = 0;
    private int score;
}
