package com.ruoyi.fac.vo.client;

import lombok.Data;

@Data
public class AccessToken {

    private String access_token;
    // accessToken过期时间
    private Long expires_in;
}
