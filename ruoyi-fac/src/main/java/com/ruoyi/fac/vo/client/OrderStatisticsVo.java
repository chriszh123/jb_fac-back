/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderStatisticsVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * OrderStatisticsVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:29
 **/
public class OrderStatisticsVo implements Serializable {
    private static final long serialVersionUID = -2342187099640678784L;

    private int count_id_no_reputation;
    private int count_id_no_transfer;
    private int count_id_close;
    private int count_id_no_pay;
    private int count_id_no_confirm;
    private int count_id_success;

    public int getCount_id_no_reputation() {
        return count_id_no_reputation;
    }

    public void setCount_id_no_reputation(int count_id_no_reputation) {
        this.count_id_no_reputation = count_id_no_reputation;
    }

    public int getCount_id_no_transfer() {
        return count_id_no_transfer;
    }

    public void setCount_id_no_transfer(int count_id_no_transfer) {
        this.count_id_no_transfer = count_id_no_transfer;
    }

    public int getCount_id_close() {
        return count_id_close;
    }

    public void setCount_id_close(int count_id_close) {
        this.count_id_close = count_id_close;
    }

    public int getCount_id_no_pay() {
        return count_id_no_pay;
    }

    public void setCount_id_no_pay(int count_id_no_pay) {
        this.count_id_no_pay = count_id_no_pay;
    }

    public int getCount_id_no_confirm() {
        return count_id_no_confirm;
    }

    public void setCount_id_no_confirm(int count_id_no_confirm) {
        this.count_id_no_confirm = count_id_no_confirm;
    }

    public int getCount_id_success() {
        return count_id_success;
    }

    public void setCount_id_success(int count_id_success) {
        this.count_id_success = count_id_success;
    }
}
