/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.BannerVo;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页轮播图片数据相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/banner")
public class FacBannerController extends BaseController {

    @PostMapping("/list")
    @ResponseBody
    public FacResult list(String key) {
        List<BannerVo> bannerVos = new ArrayList<>();
        
        return FacResult.success(bannerVos);
    }
}
