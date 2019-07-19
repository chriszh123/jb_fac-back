/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/7/19
 * Description: 缓存
 */
package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.constant.CacheKeys;
import com.ruoyi.fac.service.ICacheService;
import com.ruoyi.fac.vo.cache.CacheVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 缓存
 *
 * @author zhangguifeng
 * @create 2019-07-19 13:05
 **/
@Slf4j
@Service("cacheService")
public class CacheServiceImpl implements ICacheService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<CacheVo> findCaches(CacheVo vo) {
        final List<CacheVo> result = new ArrayList<>();
        final Set<String> keys = this.stringRedisTemplate.keys(CacheKeys.KEY_PREFIX);
        if (CollectionUtils.isNotEmpty(keys)) {
            List<String> keysList = new ArrayList<>();
            // 页面模糊查询
            if (vo != null && StringUtils.isNotBlank(vo.getId())) {
                for (String key : keys) {
                    if (key.indexOf(vo.getId()) >= 0) {
                        keysList.add(key);
                    }
                }
            } else {
                keysList.addAll(keys);
            }
            List<String> values = this.stringRedisTemplate.opsForValue().multiGet(keysList);
            if (CollectionUtils.isNotEmpty(values) && values.size() == keysList.size()) {
                log.info(String.format("[findCaches] keysList:%s, values:%s", JSON.toJSONString(keysList), JSON.toJSONString(values)));
                CacheVo cacheVo;
                for (int i = 0, size = values.size(); i < size; i++) {
                    cacheVo = new CacheVo();
                    result.add(cacheVo);
                    cacheVo.setId(keysList.get(i));
                    cacheVo.setValue(values.get(i));
                }
            } else {
                log.info(String.format("[findCaches] keys he values not same, keys:%s, values:%s", JSON.toJSONString(keys), JSON.toJSONString(values)));
            }
        }

        return result;
    }

    @Override
    public int removeCache(String keys) {
        log.info(String.format("[removeCache] start:%s", keys));
        if (StringUtils.isBlank(keys)) {
            return 0;
        }
        try {
            List<String> keyList = Arrays.asList(keys.split(","));
            Long rows = this.stringRedisTemplate.delete(keyList);
            log.info(String.format("[removeCache] rows:%s", rows));
            return rows.intValue();
        } catch (Exception ex) {
            return 0;
        }
    }
}
