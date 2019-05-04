package com.ruoyi.fac.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zgf
 * Date 2019/4/6 10:25
 * Description
 */
public class FacCommonUtils {

    public static String randomInt(int length) {
        String sources = "0123456789";
        Random random = new Random();
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < length; i++) {
            data.append(sources.charAt(random.nextInt(9)) + "");
        }

        return data.toString();
    }

    public static String substringStr(String data, int length) {
        if (StringUtils.isBlank(data) || data.length() <= length) {
            return data;
        }
        if (data.trim().length() > length) {
            data = data.trim().substring(0, length);
        }

        return data.trim();
    }

    public static List<Long> convertToLongList(List<String> stringList) {
        List<Long> longList = new ArrayList<>();
        if (CollectionUtils.isEmpty(stringList)) {
            return longList;
        }
        Long wid = 0L;
        for (String string : stringList) {
            if (StringUtils.isNotBlank(string)) {
                wid = Long.parseLong(string);
                if (!longList.contains(wid)) {
                    longList.add(wid);
                }
            }
        }
        return longList;
    }

    /**
     * 获取指定范围内的随机值
     *
     * @param max
     * @param min
     * @return
     */
    public static int getRandomInt(int max, int min) {
        Random random = new Random();
        int value = random.nextInt(max - min) + min;
        return value;
    }

    public static void main(String[] args) {
        System.out.println(randomInt(11));
    }
}
