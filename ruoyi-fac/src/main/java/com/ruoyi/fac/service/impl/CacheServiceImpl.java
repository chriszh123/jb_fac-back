/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/7/19
 * Description: 缓存
 */
package com.ruoyi.fac.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
    public List<CacheVo> findCaches() {
        final List<CacheVo> result = new ArrayList<>();
        final Set<String> keys = this.stringRedisTemplate.keys(CacheKeys.KEY_PREFIX);
        if (CollectionUtils.isNotEmpty(keys)) {
            List<String> keysList = new ArrayList<>(keys);
            List<String> values = this.stringRedisTemplate.opsForValue().multiGet(keysList);
            if (CollectionUtils.isNotEmpty(values) && values.size() == keys.size()) {
                log.info(String.format("[findCaches] keys:%s, values:%s", JSON.toJSONString(keysList), JSON.toJSONString(values)));
                CacheVo cacheVo;
                JSONObject object;
                for (int i = 0, size = values.size(); i < size; i++) {
                    cacheVo = new CacheVo();
                    result.add(cacheVo);
                    cacheVo.setId(keysList.get(i));
                    if (StringUtils.isNotBlank(values.get(i))) {
                        object = JSONObject.parseObject(values.get(i));
                        // 格式化redis字符串值json串
                        String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue
                                , SerializerFeature.WriteDateUseDateFormat);
                        cacheVo.setValue(pretty);
                    }
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
