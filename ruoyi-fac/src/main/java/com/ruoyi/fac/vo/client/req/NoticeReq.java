/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: NoticeReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * NoticeReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:44
 **/
public class NoticeReq implements Serializable {
    private static final long serialVersionUID = -4051711338937300298L;

    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
