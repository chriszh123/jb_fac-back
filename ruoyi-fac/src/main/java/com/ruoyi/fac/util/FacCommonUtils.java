package com.ruoyi.fac.util;

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

    public static void main(String[] args) {
        System.out.println(randomInt(11));
    }
}
