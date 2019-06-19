/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: UserBaseVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;

/**
 * UserBaseVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:54
 **/
public class UserBaseVo implements Serializable {
    private static final long serialVersionUID = -2688743261487341710L;

    /**
     * "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK8fyGFaEeep5zVTBpibZNL9CicvicuSTiaI25CwZy8AeKPmpmdkRCOxuWtjwwyc3ibQpJeIymEU5UXMcw/132"
     */
    private String avatarUrl;
    /**
     * "Nanjing"
     */
    private String city;
    private String dateAdd;
    /**
     * "2019-01-24 22:41:27"
     */
    private String dateLogin;
    private long id;
    /**
     * "221.226.41.179"
     */
    private String ipAdd;
    /**
     * "180.110.103.108"
     */
    private String ipLogin;
    private boolean isIdcardCheck = false;
    private boolean isSeller = false;
    private String nick;
    /**
     * "Jiangsu"
     */
    private String province;
    private int source = 0;
    /**
     * "小程序"
     */
    private String sourceStr = "小程序";
    private int status = 0;
    /**
     * "默认"
     */
    private String statusStr = "默认";
    // 用户类型：0-普通购买用户,1-商家
    private int userType = 0;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(String dateLogin) {
        this.dateLogin = dateLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd = ipAdd;
    }

    public String getIpLogin() {
        return ipLogin;
    }

    public void setIpLogin(String ipLogin) {
        this.ipLogin = ipLogin;
    }

    public boolean isIdcardCheck() {
        return isIdcardCheck;
    }

    public void setIdcardCheck(boolean idcardCheck) {
        isIdcardCheck = idcardCheck;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getSourceStr() {
        return sourceStr;
    }

    public void setSourceStr(String sourceStr) {
        this.sourceStr = sourceStr;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
