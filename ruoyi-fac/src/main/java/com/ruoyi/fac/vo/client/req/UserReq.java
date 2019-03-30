/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: UserReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * UserReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:58
 **/
public class UserReq implements Serializable {
    private static final long serialVersionUID = -5400411188505569564L;

    private String token;
    private String id;
    private String code;
    private Integer type;
    private String encryptedData;
    private String iv;
    // 推荐人
    private String referrer;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
