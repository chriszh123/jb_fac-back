/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/7/19
 * Description: Redis缓存数据处理
 */
package com.ruoyi.fac.service;

import com.ruoyi.fac.vo.cache.CacheVo;

import java.util.List;

/**
 * Redis缓存数据处理
 *
 * @author zhangguifeng
 * @create 2019-07-19 11:25
 **/
public interface ICacheService {

    List<CacheVo> findCaches();

    int removeCache(String keys);
}
