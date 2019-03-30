/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: 配置项相关请求参数
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * 配置项相关请求参数
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:22
 **/
public class ConfigReq implements Serializable {
    private static final long serialVersionUID = -8653677032869614504L;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
