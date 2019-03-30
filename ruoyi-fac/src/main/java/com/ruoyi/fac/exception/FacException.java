/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/3/22
 * Description: Facc异常信息
 */
package com.ruoyi.fac.exception;

/**
 * Facc异常信息
 *
 * @author zhangguifeng
 * @create 2019-03-22 10:59
 **/
public class FacException extends Exception {

    public FacException(String msg) {
        super(msg);
    }

    public FacException(String msg, Throwable e) {
        super(msg, e);
    }
}
