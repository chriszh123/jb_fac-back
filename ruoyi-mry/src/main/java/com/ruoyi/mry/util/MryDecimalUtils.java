package com.ruoyi.mry.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by zgf
 * Date 2019/2/21 14:10
 * Description
 */
public class MryDecimalUtils {
    private static DecimalFormat df = new DecimalFormat("0");

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
        BigDecimal result = src.add(dst);
        return result;
    }

    /**
     * 两者之差
     *
     * @param src
     * @param dst
     * @return
     */
    public static BigDecimal subtract(BigDecimal src, BigDecimal dst) {
        BigDecimal result = src.subtract(dst);
        return result;
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
     * 格式化指定BigDecimal值:保留两位小数
     *
     * @param data
     * @return
     */
    public static BigDecimal formatDecimal(BigDecimal data) {
        DecimalFormat format = new DecimalFormat("0.00");
        String dataStr = format.format(data);
        return new BigDecimal(dataStr);
    }

    /**
     * 返回一个默认值
     *
     * @return BigDecimal
     */
    public static BigDecimal getDefaultDecimal() {
        BigDecimal init = new BigDecimal("0.00");
        return init;
    }

    public static BigDecimal convert(Object object) {
        BigDecimal data = new BigDecimal(String.valueOf(object));
        return data;
    }

    /**
     * 两个整数相除，保留两位小数
     *
     * @param data1
     * @param data2
     * @return
     */
    public static BigDecimal division(int data1, int data2) {
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format((float) data1 / data2);
        return new BigDecimal(result);
    }

    public static int convertFen(BigDecimal amount) {
        amount = amount.multiply(new BigDecimal(100));
        String amountStr = df.format(amount);
        int result = Integer.valueOf(amountStr);

        return result;
    }
}
