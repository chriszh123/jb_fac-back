/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/14
 * Description: 微信用户登录res
 */
package com.ruoyi.fac.vo.client.res;

import java.io.Serializable;

/**
 * 微信用户登录res
 *
 * @author zhangguifeng
 * @create 2019-02-14 17:50
 **/
public class LoginVo implements Serializable {
    private static final long serialVersionUID = -7797358270710840837L;

    /**
     * 用户id
     */
    private Long uid;
    /**
     * 微信用户openId
     */
    private String openid;
    /**
     * 这里token暂时与openId值相同，代表用户唯一标识
     */
    private String token;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
