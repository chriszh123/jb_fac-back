package com.ruoyi.fac.vo.client;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/6/27 22:14
 * Description
 */
@Data
public class OrderProductVo implements Serializable {
    private static final long serialVersionUID = 3856739463003519513L;

    private String orderNo;
    private String prodName;
    private String prodId;
    private String number = "0";
    private String price;
    private String pic;
}
