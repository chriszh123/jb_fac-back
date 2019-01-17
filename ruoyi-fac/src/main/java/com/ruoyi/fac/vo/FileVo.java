/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/17
 * Description: CKEditor返回前端vo
 */
package com.ruoyi.fac.vo;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * CKEditor返回前端vo
 * CKEditor4.5版本以上返回前端数据格式修改为json形式
 *
 * @author zhangguifeng
 * @create 2019-01-17 19:26
 **/
public class FileVo extends HashMap<String, Object> {
    Map<String, Object> msgMap = new HashMap<>();

    public String error(int code, String msg) {
        FileVo result = new FileVo();
        msgMap.put("message", msg);
        result.put("uploaded", code);
        result.put("error", msgMap);
        return JSONObject.toJSONString(msgMap);
    }

    public String success(int code, String fileName, String url, String msg) {
        FileVo result = new FileVo();
        if (!StringUtils.isEmpty(msg)) {
            msgMap.put("message", msg);
            result.put("error", msgMap);
        }
        result.put("uploaded", code);
        result.put("fileName", fileName);
        result.put("url", url);
        return JSONObject.toJSONString(msgMap);
    }
}
