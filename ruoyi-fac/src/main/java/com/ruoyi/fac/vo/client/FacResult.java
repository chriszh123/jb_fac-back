/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: FacResult
 */
package com.ruoyi.fac.vo.client;

import com.ruoyi.fac.enums.FacCode;

import java.io.Serializable;
import java.util.HashMap;

/**
 * FacResult
 *
 * @author zhangguifeng
 * @create 2019-01-25 14:03
 **/
public class FacResult extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 2650521893078485334L;

    /**
     * 初始化一个新创建的 Message 对象
     */
    public FacResult() {
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static FacResult error() {
        return error(1, "操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static FacResult error(String msg) {
        return error(500, msg);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static FacResult success(Object data) {
        FacResult json = new FacResult();
        json.put("msg", FacCode.SUCCESS.getMsg());
        json.put("code", FacCode.SUCCESS.getCode());
        json.put("data", data);
        return json;
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg  内容
     * @return 错误消息
     */
    public static FacResult error(int code, String msg) {
        FacResult json = new FacResult();
        json.put("code", code);
        json.put("msg", msg);
        return json;
    }

    /**
     * 返回成功消息
     *
     * @param key   键值
     * @param value 内容
     * @return 成功消息
     */
    @Override
    public FacResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }


}
