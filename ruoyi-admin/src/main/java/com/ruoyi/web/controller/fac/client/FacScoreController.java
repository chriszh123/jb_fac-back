/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.client.req.ScoreReq;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 积分接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/client/score")
public class FacScoreController extends BaseController {

    /**
     * 积分派送规则
     *
     * @param req
     * @return
     */
    @PostMapping("/send/rule")
    @ResponseBody
    public FacResult sendRule(@RequestBody ScoreReq req) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    /**
     * 今日签到所得积分
     *
     * @param req
     * @return
     */
    @PostMapping("/today-signed")
    @ResponseBody
    public FacResult todaySigned(@RequestBody ScoreReq req) {


        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }
}
