/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2019/2/11
 * Description: TemplateMsgReq
 */
package com.ruoyi.fac.vo.client.req;

import java.io.Serializable;

/**
 * TemplateMsgReq
 *
 * @author zhangguifeng
 * @create 2019-02-11 14:55
 **/
public class TemplateMsgReq implements Serializable {
    private static final long serialVersionUID = 6450217576367472629L;

    private String token;
    private String type;
    private String formId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }
}
