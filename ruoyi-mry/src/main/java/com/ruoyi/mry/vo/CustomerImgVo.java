/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/16
 * Description: 商品图片上传响应数据对象
 */
package com.ruoyi.mry.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 图片上传响应数据对象
 *
 * @author zhangguifeng
 * @create 2020-01-16 10:23
 **/
@Data
public class CustomerImgVo implements Serializable {
    private static final long serialVersionUID = 3517604790015695567L;

    private String code;
    private String msg;
    private String fileName;
    private String imgPath;
    private String[] imgPaths;

    /**
     * [{caption: "People-1.jpg", size: 576237, width: "120px", url: "/site/file-delete", key: 1}]
     */
    private List<Map<String, Object>> cfg = new ArrayList<>();
}
