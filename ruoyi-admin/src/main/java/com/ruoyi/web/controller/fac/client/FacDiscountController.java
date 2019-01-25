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
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠卷等数据相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/discounts")
public class FacDiscountController extends BaseController {

    @PostMapping("/coupons")
    @ResponseBody
    public FacResult list() {
        List<BannerVo> bannerVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(bannerVos)) {
            return FacResult.success(bannerVos);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }
}
