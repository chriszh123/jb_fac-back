/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/17
 * Description: CKEditor返回前端vo
 */
package com.ruoyi.framework.util;

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
public class FileBean extends HashMap<String, Object> {
    Map<String, Object> msgMap = new HashMap<>();

    public String error(int code, String msg) {
        msgMap.put("message", msg);
        msgMap.put("uploaded", code);
        msgMap.put("error", msgMap);
        return JSONObject.toJSONString(msgMap);
    }

    public String success(int code, String fileName, String url, String msg) {
        if (!StringUtils.isEmpty(msg)) {
            msgMap.put("message", msg);
            msgMap.put("error", msgMap);
        }
        msgMap.put("uploaded", code);
        msgMap.put("fileName", fileName);
        msgMap.put("url", url);
        return JSONObject.toJSONString(msgMap);
    }
}
