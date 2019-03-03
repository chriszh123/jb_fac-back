package com.ruoyi.fac.util;

import java.math.BigDecimal;

/**
 * Created by zgf
 * Date 2019/2/21 14:10
 * Description
 */
public class DecimalUtils {

    /**
     * 两个数值比较大小
     *
     * @param data1
     * @param data2
     * @return
     */
    public static int compare(BigDecimal data1, BigDecimal data2) {
        if (data1 == null || data2 == null) {
            return 0;
        }
        return data1.compareTo(data2);
    }

    /**
     * 两者之和
     *
     * @param src
     * @param dst
     * @return
     */
    public static BigDecimal add(BigDecimal src, BigDecimal dst) {
        BigDecimal totalAmount = src.add(new BigDecimal("" + dst.toString()));
        return totalAmount;
    }

    /**
     * 两者乘积
     *
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
        BigDecimal result = v1.multiply(v2);
        return result;
    }

    /**
     * 格式化指定BigDecimal值:保留一位小数
     *
     * @param data
     * @return
     */
    public static BigDecimal formatDecimal(BigDecimal data) {
        String priceVal = new java.text.DecimalFormat("######0.0")
                .format(data.setScale(1, BigDecimal.ROUND_UP).doubleValue());
        return new BigDecimal(priceVal);
    }

}
