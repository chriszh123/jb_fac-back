package com.ruoyi.fac.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
        BigDecimal result = src.add(dst);
        return result;
    }

    /**
     * 两者之和
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

}
