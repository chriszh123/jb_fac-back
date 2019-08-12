/**
 * Copyright (C) 2006-2019 zgf All rights reserved
 * Author：zhangguifeng
 * Date：2019/1/25
 * Description: 相关配置接口
 */
package com.ruoyi.web.controller.fac.client;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fac.enums.FacCode;
import com.ruoyi.fac.exception.FacException;
import com.ruoyi.fac.service.IFacKanjiaService;
import com.ruoyi.fac.service.IProductCategoryService;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.fac.vo.client.*;
import com.ruoyi.fac.vo.client.req.KanjiaReq;
import com.ruoyi.fac.vo.client.req.ShopReq;
import com.ruoyi.fac.vo.client.res.KanjiaInfoVo;
import com.ruoyi.fac.vo.client.res.KanjiaListVo;
import com.ruoyi.fac.vo.client.res.KanjiaSetVo;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private static final Logger log = LoggerFactory.getLogger(FacShopController.class);

    @Autowired
    private IProductCategoryService productCategoryService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IFacKanjiaService facKanjiaService;


    /**
     * 参加砍价活动的商品
     *
     * @return
     */
    @PostMapping("/goods/kanjia/list")
    @ResponseBody
    public FacResult kanjiaList() {
        try {
            KanjiaListVo data = this.facKanjiaService.queryKanjiaListFromClient();
            return FacResult.success(data);
        } catch (FacException fe) {
            log.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return FacResult.error();
        }
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
    public FacResult goodsList(@RequestBody ShopReq req) {
        List<GoodVo> goodVos = this.productService.goodsList(req.getCategoryId(), req.getNameLike(), req.getPage(), req.getPageSize());
        if (CollectionUtils.isNotEmpty(goodVos)) {
            return FacResult.success(goodVos);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }

    /**
     * 轮播图片点击或者点击商品分类里的商品
     *
     * @param req
     * @return
     */
    @PostMapping("/goods/detail")
    @ResponseBody
    public FacResult goodsDetail(@RequestBody ShopReq req) {
        if (StringUtils.isBlank(req.getId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        GoodDetailVo goodDetailVo = this.productService.goodsDetail(req.getId());
        if (goodDetailVo != null) {
            return FacResult.success(goodDetailVo);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }

    /**
     * 轮播图片点击或者点击商品分类里的商品
     *
     * @param req
     * @return
     */
    @PostMapping("/goods/kanjia/set")
    @ResponseBody
    public FacResult kanjiaSet(@RequestBody ShopReq req) {
        if (StringUtils.isBlank(req.getGoodsId())) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        try {
            KanjiaSetVo kanjiaSetVo = this.facKanjiaService.queryKanjiaSetFromClient(req.getGoodsId());
            if (kanjiaSetVo != null) {
                return FacResult.success(kanjiaSetVo);
            } else {
                return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
            }
        } catch (FacException fe) {
            log.error(fe.getMessage(), fe);
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return FacResult.error();
        }
    }

    @PostMapping("/goods/kanjia/info")
    @ResponseBody
    public FacResult kanjiaInfo(@RequestBody KanjiaReq req) {
        if (req == null || req.getJoiner() == null || req.getKjid() == null) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        try {
            KanjiaInfoVo kanjiaInfoVo = this.facKanjiaService.queryKanJiaInfoFromClient(req);
            if (kanjiaInfoVo != null) {
                return FacResult.success(kanjiaInfoVo);
            } else {
                return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
            }
        } catch (FacException fe) {
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            return FacResult.error();
        }
    }

    @PostMapping("/goods/kanjia/myHelp")
    @ResponseBody
    public FacResult kanjiaMyHelp(@RequestBody KanjiaReq req) {
        if (req == null || req.getJoiner() == null || req.getKjid() == null) {
            return FacResult.error(FacCode.PARAMTER_NULL.getCode(), FacCode.PARAMTER_NULL.getMsg());
        }
        try {
            KanjiaSetVo kanjiaSetVo = this.facKanjiaService.queryKanjiaSetFromClient(req.getToken());
            if (kanjiaSetVo != null) {
                return FacResult.success(kanjiaSetVo);
            } else {
                return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
            }
        } catch (FacException fe) {
            return FacResult.error(fe.getMessage());
        } catch (Exception ex) {
            return FacResult.error();
        }
    }

    @PostMapping("/goods/reputation")
    @ResponseBody
    public FacResult reputation(@RequestBody ShopReq req) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/pingtuan/list")
    @ResponseBody
    public FacResult pingtuanList(@RequestBody ShopReq req) {
        return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
    }

    @PostMapping("/goods/price")
    @ResponseBody
    public FacResult goodsPrice(@RequestBody ShopReq req) {
        // goodsId=114896&propertyChildIds= 10468:41285,
        GoodsPriceVo goodsPriceVo = this.productService.goodPrice(req.getGoodsId());
        if (goodsPriceVo != null) {
            return FacResult.success(goodsPriceVo);
        } else {
            return FacResult.error(FacCode.HAS_NO_DATA.getCode(), FacCode.HAS_NO_DATA.getMsg());
        }
    }
}
