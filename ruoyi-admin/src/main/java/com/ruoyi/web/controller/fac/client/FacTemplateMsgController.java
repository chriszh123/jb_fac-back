/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.client.req.TemplateMsgPutReq;
import com.ruoyi.fac.vo.client.req.TemplateMsgReq;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 消息模板相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/client/template-msg")
public class FacTemplateMsgController extends BaseController {

    @PostMapping("/wxa/formId")
    @ResponseBody
    public FacResult wxaFormId(@RequestBody TemplateMsgReq req) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/put")
    @ResponseBody
    public FacResult put(@RequestBody TemplateMsgPutReq req) {
        return FacResult.success("");
    }
}
