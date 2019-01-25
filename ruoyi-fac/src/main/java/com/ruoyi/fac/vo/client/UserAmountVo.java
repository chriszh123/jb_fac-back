/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: UserAmountVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * UserAmountVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 18:01
 **/
public class UserAmountVo implements Serializable {
    private static final long serialVersionUID = -5808520393298037035L;

    private double balance;
    private double freeze;
    private int score;
    private double totleConsumed;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFreeze() {
        return freeze;
    }

    public void setFreeze(double freeze) {
        this.freeze = freeze;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getTotleConsumed() {
        return totleConsumed;
    }

    public void setTotleConsumed(double totleConsumed) {
        this.totleConsumed = totleConsumed;
    }
}
