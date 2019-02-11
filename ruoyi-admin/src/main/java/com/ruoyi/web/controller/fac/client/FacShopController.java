/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.service.IOrderService;
import com.ruoyi.fac.service.IProductCategoryService;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/fac/client/shop")
public class FacShopController extends BaseController {

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductService productService;

    /**
     * 参加砍价活动的商品
     *
     * @return
     */
    @PostMapping("/goods/kanjia/list")
    @ResponseBody
    public FacResult kanjiaList() {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/category/all")
    @ResponseBody
    public FacResult categoryList() {
        List<CategoryVo> categoryVos = this.productCategoryService.categoryList();
        if (CollectionUtils.isEmpty(categoryVos)) {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        } else {
            return FacResult.success(categoryVos);
        }
    }

    @PostMapping("/goods/list")
    @ResponseBody
    public FacResult goodsList(String categoryId, String nameLike, int page, int pageSize) {
        List<GoodVo> goodVos = this.productService.goodsList(categoryId, nameLike, page, pageSize);
        if (CollectionUtils.isNotEmpty(goodVos)) {
            return FacResult.success(goodVos);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }

    /**
     * 轮播图片点击或者点击商品分类里的商品
     *
     * @param id
     * @return
     */
    @PostMapping("/goods/detail")
    @ResponseBody
    public FacResult goodsDetail(String id) {
        GoodDetailVo goodDetailVo = this.productService.goodsDetail(id);
        if (goodDetailVo != null) {
            return FacResult.success(goodDetailVo);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }

    @PostMapping("/goods/reputation")
    @ResponseBody
    public FacResult reputation(String goodsId) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/pingtuan/list")
    @ResponseBody
    public FacResult pingtuanList(String goodsId) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/price")
    @ResponseBody
    public FacResult goodsPrice(String goodsId, String propertyChildIds) {
        // goodsId=114896&propertyChildIds= 10468:41285,
        GoodsPriceVo goodsPriceVo = this.productService.goodPrice(goodsId);
        if (goodsPriceVo != null) {
            return FacResult.success(goodsPriceVo);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }
}
