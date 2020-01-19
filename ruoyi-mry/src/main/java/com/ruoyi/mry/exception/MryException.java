/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/3/22
 * Description: Facc异常信息
 */
package com.ruoyi.mry.exception;

/**
 * Mry异常信息
 *
 * @author zhangguifeng
 * @create 2019-03-22 10:59
 **/
public class MryException extends RuntimeException {

    public MryException(String msg) {
        super(msg);
    }

    public MryException(String msg, Throwable e) {
        super(msg, e);
    }
}
