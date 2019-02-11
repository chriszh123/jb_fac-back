/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: BannerReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * BannerReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:35
 **/
public class BannerReq implements Serializable {
    private static final long serialVersionUID = 8557394593901655313L;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
