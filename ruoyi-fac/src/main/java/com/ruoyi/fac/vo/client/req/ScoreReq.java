/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: ScoreReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * ScoreReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:49
 **/
public class ScoreReq implements Serializable {
    private static final long serialVersionUID = -5813508260053769441L;

    private String token;
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
