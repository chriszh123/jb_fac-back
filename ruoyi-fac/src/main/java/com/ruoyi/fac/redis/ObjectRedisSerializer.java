/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/6/24
 * Description: redis序列化
 */
package com.ruoyi.fac.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis序列化
 *
 * @author zhangguifeng
 * @create 2019-06-24 19:28
 **/
public class ObjectRedisSerializer implements RedisSerializer<Object> {
    private static final Logger log = LoggerFactory.getLogger(ObjectRedisSerializer.class);

    /**
     * 定义序列化和反序列化转化类
     */
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    /**
     * 定义转换空字节数组
     */
    private static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        byte[] byteArray = null;
        if (null == obj) {
            log.info("----------------------------->:Redis待序列化的对象为空.");
            byteArray = EMPTY_ARRAY;
        } else {
            try {
                byteArray = serializer.convert(obj);
            } catch (Exception e) {
                log.error("----------------------------->Redis序列化对象失败,异常：" + e.getMessage(), e);
                byteArray = EMPTY_ARRAY;
            }
        }

        return byteArray;
    }

    @Override
    public Object deserialize(byte[] datas) throws SerializationException {
        Object obj = null;
        if ((null == datas) || (datas.length == 0)) {
            log.info("---------------------------------->Redis待反序列化的对象为空.");
        } else {
            try {
                obj = deserializer.convert(datas);
            } catch (Exception e) {
                log.error("------------------------------------->Redis反序列化对象失败,异常：" + e.getMessage() + e.getMessage(), e);
            }
        }

        return obj;
    }
}
