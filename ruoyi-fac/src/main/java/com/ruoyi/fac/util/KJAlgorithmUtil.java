package com.ruoyi.fac.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KJAlgorithmUtil {

    /**
     * 1.总金额不超过总共可砍的价格*100  单位是分
     * 2.每次砍价都能砍到金额，最低不能低于1分，最大金额不能超过（总共可砍的价）*100
     */
    private static int MINMONEY = 1;
    private static int MAXMONEY = 0;

    /**
     * 这里为了避免某一次砍价占用大量资金，我们需要设定非最后一次砍价的最大金额，
     * 我们把他设置为砍价金额平均值的N倍
     */
    private static final double TIMES = 3.1;

    /**
     * 砍价合法性校验
     *
     * @param money
     * @param count
     * @return
     */
    private static boolean isRight(int money, int count) {
        double avg = money / count;
        //小于最小金额
        if (avg < MINMONEY) {
            return false;
        } else if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }

    /**
     * 随机分配一个金额
     *
     * @param minS：最小金额
     * @param maxS：最大金额
     * @param count
     * @return
     */
    private static int randomReducePrice(int money, int minS, int maxS, int count) {
        //若只有一个，直接返回
        if (count == 1) {
            return money;
        }
        //如果最大金额和最小金额相等，直接返回金额
        if (minS == maxS) {
            return minS;
        }
        int max = maxS > money ? money : maxS;
        //分配砍价正确情况，允许砍价的最大值
        int maxY = money - (count - 1) * minS;
        //分配砍价正确情况，允许砍价最小值
        int minY = money - (count - 1) * maxS;
        //随机产生砍价的最小值
        int min = minS > minY ? minS : minY;
        //随机产生砍价的最大值
        max = max > maxY ? maxY : max;
        //随机产生一个砍价
        return (int) Math.rint(Math.random() * (max - min) + min);
    }

    /**
     * 砍价
     *
     * @param toCutMoney 待砍金额 : 分
     * @param minMoney   砍价范围min：分
     * @param minMoney   砍价范围max：分
     * @param count      个数 : 砍价过程 多少次砍价可以结束
     * @return
     */
    public static String splitReducePrice(int toCutMoney, int minMoney, int maxMoney, int count) {
        MAXMONEY = maxMoney;
        MINMONEY = minMoney;
        //红包合法性分析
        if (!isRight(toCutMoney, count)) {
            return "";
        }
        //红包列表
        List<Double> list = new ArrayList<>();
        //每个红包的最大的金额为平均金额的TIMES倍
        int max = (int) (toCutMoney * TIMES / count);
        max = max > MAXMONEY ? MAXMONEY : max;
        int sum = 0;
        for (int i = 0; i < count; i++) {
            int one = randomReducePrice(toCutMoney, MINMONEY, max, count - i);
            list.add(one / 100.0);
            toCutMoney -= one;
            sum += one;
        }
        log.info("---------[splitReducePrice] 待砍价总额(分),sum = " + sum);

        return StringUtils.join(list, ",");
    }

    public static void main(String[] args) {
        String result = splitReducePrice(3900, 120, 2000, 11);
        System.out.println("result = " + result);
    }
}
