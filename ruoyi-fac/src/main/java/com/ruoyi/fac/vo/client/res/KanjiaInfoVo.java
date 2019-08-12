package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaInfoVo implements Serializable{
    private static final long serialVersionUID = -8157934769324530502L;

    private JoinerVo joiner;
    private KjInfoVo kanjiaInfo;
}
