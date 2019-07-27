/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/7/19
 * Description: 緩存vo
 */
package com.ruoyi.fac.vo.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 緩存vo
 *
 * @author zhangguifeng
 * @create 2019-07-19 13:07
 **/
@Data
public class CacheVo implements Serializable {
    private static final long serialVersionUID = -2921705644648767203L;

    /**
     * 缓存key
     */
    private String id;
    /**
     * 缓存value
     */
    private String value;
}
