/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 商品数据对象
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * 商品数据对象
 *
 * @author zhangguifeng
 * @create 2019-01-25 16:04
 **/
public class GoodVo implements Serializable {
    private static final long serialVersionUID = 5996920899006528824L;

    private String barCode;
    private long categoryId;
    private String characteristic;
    private double commission;
    private int commissionType;
    private String dateAdd;
    private String dateEnd;
    /**
     * 2019年
     */
    private String dateEndCountDown;
    private String dateStart;
    private String dateUpdate;
    private int gotScore;
    private int gotScoreType;
    private long id;
    private boolean kanjia = false;
    private double kanjiaPrice;
    private int logisticsId;
    private double minPrice;
    private int minScore;
    private String name;
    private int numberFav;
    private int numberGoodReputation;
    private int numberOrders;
    private int numberSells;
    private double originalPrice;
    private int paixu;
    /**
     * https://cdn.it120.cc/apifactory/2019/01/22/b959b8a0f4ed9a3cf3848c6fd6113c5d.gif
     */
    private String pic;
    private boolean pingtuan = false;
    private double pingtuanPrice;
    /**
     * ",10468,"
     */
    private String propertyIds;
    private int recommendStatus;
    private String recommendStatusStr;
    private long shopId;
    private int status;
    private String statusStr;
    private int stores;
    private long userId;
    private String videoId;
    private int views;
    private double weight;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(int commissionType) {
        this.commissionType = commissionType;
    }

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

    public String getDateEndCountDown() {
        return dateEndCountDown;
    }

    public void setDateEndCountDown(String dateEndCountDown) {
        this.dateEndCountDown = dateEndCountDown;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public int getGotScore() {
        return gotScore;
    }

    public void setGotScore(int gotScore) {
        this.gotScore = gotScore;
    }

    public int getGotScoreType() {
        return gotScoreType;
    }

    public void setGotScoreType(int gotScoreType) {
        this.gotScoreType = gotScoreType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isKanjia() {
        return kanjia;
    }

    public void setKanjia(boolean kanjia) {
        this.kanjia = kanjia;
    }

    public double getKanjiaPrice() {
        return kanjiaPrice;
    }

    public void setKanjiaPrice(double kanjiaPrice) {
        this.kanjiaPrice = kanjiaPrice;
    }

    public int getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(int logisticsId) {
        this.logisticsId = logisticsId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberFav() {
        return numberFav;
    }

    public void setNumberFav(int numberFav) {
        this.numberFav = numberFav;
    }

    public int getNumberGoodReputation() {
        return numberGoodReputation;
    }

    public void setNumberGoodReputation(int numberGoodReputation) {
        this.numberGoodReputation = numberGoodReputation;
    }

    public int getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(int numberOrders) {
        this.numberOrders = numberOrders;
    }

    public int getNumberSells() {
        return numberSells;
    }

    public void setNumberSells(int numberSells) {
        this.numberSells = numberSells;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getPaixu() {
        return paixu;
    }

    public void setPaixu(int paixu) {
        this.paixu = paixu;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isPingtuan() {
        return pingtuan;
    }

    public void setPingtuan(boolean pingtuan) {
        this.pingtuan = pingtuan;
    }

    public double getPingtuanPrice() {
        return pingtuanPrice;
    }

    public void setPingtuanPrice(double pingtuanPrice) {
        this.pingtuanPrice = pingtuanPrice;
    }

    public String getPropertyIds() {
        return propertyIds;
    }

    public void setPropertyIds(String propertyIds) {
        this.propertyIds = propertyIds;
    }

    public int getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(int recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public String getRecommendStatusStr() {
        return recommendStatusStr;
    }

    public void setRecommendStatusStr(String recommendStatusStr) {
        this.recommendStatusStr = recommendStatusStr;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
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

    public int getStores() {
        return stores;
    }

    public void setStores(int stores) {
        this.stores = stores;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
