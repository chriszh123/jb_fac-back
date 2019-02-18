package com.ruoyi.fac.vo.wxpay;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/2/18 17:11
 * Description
 */
public class NextAction implements Serializable{
    private static final long serialVersionUID = 3069661955057660933L;

    private Integer type;
    /**
     * 订单no
     */
    private String id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
