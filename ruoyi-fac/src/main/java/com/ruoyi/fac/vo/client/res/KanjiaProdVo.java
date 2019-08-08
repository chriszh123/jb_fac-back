package com.ruoyi.fac.vo.client.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class KanjiaProdVo implements Serializable {
    private static final long serialVersionUID = 2040226095047087569L;

    private String name;
    private String pic;
    private String characteristic;
}
