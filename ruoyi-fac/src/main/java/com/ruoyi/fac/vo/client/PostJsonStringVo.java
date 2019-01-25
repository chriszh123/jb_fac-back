/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: PostJsonStringVo
 */
package com.ruoyi.fac.vo.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * PostJsonStringVo
 *
 * @author zhangguifeng
 * @create 2019-01-25 17:17
 **/
public class PostJsonStringVo implements Serializable {
    private static final long serialVersionUID = -6761374075663448641L;

    private List<KeywordVo> postJsonString = new ArrayList<>();

    public List<KeywordVo> getPostJsonString() {
        return postJsonString;
    }

    public void setPostJsonString(List<KeywordVo> postJsonString) {
        this.postJsonString = postJsonString;
    }
}
