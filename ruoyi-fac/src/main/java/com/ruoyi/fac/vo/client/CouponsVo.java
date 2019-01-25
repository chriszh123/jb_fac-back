/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 优惠卷
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * 优惠卷
 *
 * @author zhangguifeng
 * @create 2019-01-25 15:53
 **/
public class CouponsVo implements Serializable {
    private static final long serialVersionUID = -3721022020449452165L;

    private String dateAdd;
    private String dateEnd;
    private int dateEndType;
    private int dateStartType;
    private long id;
    /**
     * 39.90
     */
    private double moneyHreshold;
    private double moneyMax;
    private double moneyMin;
    private String name;
    private int needScore;
    private int needSignedContinuous;
    private int numberGit;
    private int numberGitNumber;
    private int numberLeft;
    private int numberPersonMax;
    private int numberTotle;
    private int numberUsed;
    private int status;
    private String statusStr;
    private String type;

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getDateEndType() {
        return dateEndType;
    }

    public void setDateEndType(int dateEndType) {
        this.dateEndType = dateEndType;
    }

    public int getDateStartType() {
        return dateStartType;
    }

    public void setDateStartType(int dateStartType) {
        this.dateStartType = dateStartType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoneyHreshold() {
        return moneyHreshold;
    }

    public void setMoneyHreshold(double moneyHreshold) {
        this.moneyHreshold = moneyHreshold;
    }

    public double getMoneyMax() {
        return moneyMax;
    }

    public void setMoneyMax(double moneyMax) {
        this.moneyMax = moneyMax;
    }

    public double getMoneyMin() {
        return moneyMin;
    }

    public void setMoneyMin(double moneyMin) {
        this.moneyMin = moneyMin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeedScore() {
        return needScore;
    }

    public void setNeedScore(int needScore) {
        this.needScore = needScore;
    }

    public int getNeedSignedContinuous() {
        return needSignedContinuous;
    }

    public void setNeedSignedContinuous(int needSignedContinuous) {
        this.needSignedContinuous = needSignedContinuous;
    }

    public int getNumberGit() {
        return numberGit;
    }

    public void setNumberGit(int numberGit) {
        this.numberGit = numberGit;
    }

    public int getNumberGitNumber() {
        return numberGitNumber;
    }

    public void setNumberGitNumber(int numberGitNumber) {
        this.numberGitNumber = numberGitNumber;
    }

    public int getNumberLeft() {
        return numberLeft;
    }

    public void setNumberLeft(int numberLeft) {
        this.numberLeft = numberLeft;
    }

    public int getNumberPersonMax() {
        return numberPersonMax;
    }

    public void setNumberPersonMax(int numberPersonMax) {
        this.numberPersonMax = numberPersonMax;
    }

    public int getNumberTotle() {
        return numberTotle;
    }

    public void setNumberTotle(int numberTotle) {
        this.numberTotle = numberTotle;
    }

    public int getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(int numberUsed) {
        this.numberUsed = numberUsed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
