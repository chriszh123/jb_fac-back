/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 相关配置接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/config")
public class FacConfigController extends BaseController {

    @PostMapping("/get-value")
    @ResponseBody
    public FacResult getValue(String key) {
        if (StringUtils.equals("mallName", key)) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("remark", "商城的名称");
            data.put("value", "江北便利站");
            return FacResult.success(data);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }

    }
}
