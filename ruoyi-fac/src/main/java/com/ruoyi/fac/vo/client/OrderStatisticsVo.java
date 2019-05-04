/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: OrderStatisticsVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderStatisticsVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:29
 **/
public class OrderStatisticsVo implements Serializable {
    private static final long serialVersionUID = -2342187099640678784L;

    private List<String> tabClass = new ArrayList<>();
    private int count_id_no_reputation = 0;
    private int count_id_no_transfer = 0;
    private int count_id_close = 0;
    private int count_id_no_pay = 0;
    private int count_id_no_confirm = 0;
    private int count_id_success = 0;
    // 待付款
    private int paying = 0;
    // 去核销
    private int toWriteoff = 0;
    // 待评价
    private int toEvaluate = 0;
    // 已完成
    private int complete = 0;
    // 已取消
    private int cancel = 0;
    // 待核销：商家要核销的自己的商品
    private int writeoffing = 0;
    // 用户类型：0-普通购买用户,1-商家
    private int userType = 0;

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

    public List<String> getTabClass() {
        return tabClass;
    }

    public void setTabClass(List<String> tabClass) {
        this.tabClass = tabClass;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getPaying() {
        return paying;
    }

    public void setPaying(int paying) {
        this.paying = paying;
    }

    public int getToWriteoff() {
        return toWriteoff;
    }

    public void setToWriteoff(int toWriteoff) {
        this.toWriteoff = toWriteoff;
    }

    public int getToEvaluate() {
        return toEvaluate;
    }

    public void setToEvaluate(int toEvaluate) {
        this.toEvaluate = toEvaluate;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }

    public int getWriteoffing() {
        return writeoffing;
    }

    public void setWriteoffing(int writeoffing) {
        this.writeoffing = writeoffing;
    }
}
