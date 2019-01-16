/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/16
 * Description: 商品图片上传响应数据对象
 */
package com.ruoyi.fac.vo;

import java.io.Serializable;

/**
 * 商品图片上传响应数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-16 10:23
 **/
public class ProductImgVo implements Serializable {
    private static final long serialVersionUID = 3517604790015695567L;

    private String code;
    private String msg;
    private String fileName;
    private String imgPath;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
