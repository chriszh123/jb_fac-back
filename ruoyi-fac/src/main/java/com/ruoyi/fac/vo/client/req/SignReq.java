package com.ruoyi.fac.vo.client.req;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zgf
 * Date 2019/4/25 21:58
 * Description
 */
@Data
public class SignReq implements Serializable {
    private static final long serialVersionUID = -8880148128976398253L;

    private String token;
}
