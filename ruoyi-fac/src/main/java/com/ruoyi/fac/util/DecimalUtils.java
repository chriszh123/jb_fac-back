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
}
