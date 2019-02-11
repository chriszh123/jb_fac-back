/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.service.IFocusMapService;
import com.ruoyi.fac.vo.client.BannerVo;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.fac.vo.client.req.BannerReq;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 首页轮播图片数据相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/client/banner")
public class FacBannerController extends BaseController {

    @Autowired
    private IFocusMapService focusMapService;

    @PostMapping("/list")
    @ResponseBody
    public FacResult list(@RequestBody BannerReq req) {
        List<BannerVo> bannerVos = this.focusMapService.selectFocusMapList();
        if (CollectionUtils.isEmpty(bannerVos)) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        } else {
            return FacResult.success(bannerVos);
        }
    }
}
