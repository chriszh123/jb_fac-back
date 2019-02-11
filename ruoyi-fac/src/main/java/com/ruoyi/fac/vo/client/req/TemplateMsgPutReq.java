/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: TemplateMsgPutReq
 */
package com.ruoyi.fac.vo.client.req;

import com.ruoyi.fac.vo.client.PostJsonStringVo;

import java.io.Serializable;

/**
 * TemplateMsgPutReq
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:15
 **/
public class TemplateMsgPutReq implements Serializable {
    private static final long serialVersionUID = -4500449616453961041L;

    private String token;
    private int type;
    private String module;
    private String business_id;
    private String trigger;
    private String template_id;
    private String form_id;
    /**
     * pages/index/index
     */
    private String url;
    private PostJsonStringVo postJsonString;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PostJsonStringVo getPostJsonString() {
        return postJsonString;
    }

    public void setPostJsonString(PostJsonStringVo postJsonString) {
        this.postJsonString = postJsonString;
    }
}
