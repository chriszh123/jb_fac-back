package com.ruoyi.fac.vo.client;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/5/25 17:16
 * Description
 */
@Data
public class BusinessVo implements Serializable {
    private Integer wid;
    private String phone;
    private String address;
}
