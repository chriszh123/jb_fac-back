/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: UserDetailVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * UserDetailVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:59
 **/
public class UserDetailVo implements Serializable {
    private static final long serialVersionUID = 6609937416087725393L;

    private UserBaseVo base;

    public UserBaseVo getBase() {
        return base;
    }

    public void setBase(UserBaseVo base) {
        this.base = base;
    }
}
