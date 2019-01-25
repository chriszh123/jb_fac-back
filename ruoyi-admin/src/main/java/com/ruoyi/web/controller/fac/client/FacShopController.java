/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.vo.client.CategoryVo;
import com.ruoyi.fac.vo.client.FacResult;
import com.ruoyi.framework.web.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品相关接口
 *
 * @author zhangguifeng
 * @create 2019-01-25 13:49
 **/
@Controller
@RequestMapping("/fac/shop")
public class FacShopController extends BaseController {

    @PostMapping("/goods/kanjia/list")
    @ResponseBody
    public FacResult kanjiaList() {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/category/all")
    @ResponseBody
    public FacResult categoryList() {
        List<CategoryVo> categoryVos = new ArrayList<>();

        return FacResult.success(categoryVos);
    }
}
