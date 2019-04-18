package com.ruoyi.fac.util;

import com.ruoyi.common.utils.StringUtils;

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

    public static void main(String[] args) {
        System.out.println(randomInt(11));
    }
}
