/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/3/11
 * Description: WebUtils
 */
package com.ruoyi.fac.util;

import javax.servlet.http.HttpServletRequest;

/**
 * WebUtils
 *
 * @author zhangguifeng
 * @create 2019-03-11 19:37
 **/
public class WebUtils {
    /**
     * @param @param  request
     * @param @param  response
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getIpAddress
     * @Description: 获取客户端真实IP地址
     * @author yihj
     */
    public static String getIpAddress(HttpServletRequest request) {
        // 避免反向代理不能获取真实地址, 取X-Forwarded-For中第一个非unknown的有效IP字符串
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        return ip;
    }
}
