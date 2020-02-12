/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 店面消费卡管理
 */
package com.ruoyi.web.controller.mry;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.mry.service.MryShopCardService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 店面消费卡管理
 * @author zhangguifeng
 * @create 2020-01-17 17:12
 **/
@Controller
@RequestMapping("/mry/shopcard")
public class MryShopCardController extends BaseController {

    private String prefix = "mry/shopcard";

    @Autowired
    private MryShopCardService shopCardService;

    @RequiresPermissions("mry:shopcard:view")
    @GetMapping()
    public String shopcard() {
        return prefix + "/shopcard";
    }

    @RequiresPermissions("mry:shopcard:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryShopCard shopCard) {
        startPage();
        List<MryShopCard> list = shopCardService.selectShopCards(shopCard);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:shopcard:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryShopCard shopCard) {
        List<MryShopCard> list = shopCardService.selectShopCards(shopCard);
        ExcelUtil<MryShopCard> util = new ExcelUtil<>(MryShopCard.class);
        return util.exportExcel(list, "消费卡定义");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:shopcard:add")
    @Log(title = "店面消费卡", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryShopCard shopCard) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shopCard.setOperatorId(user.getUserId());
            shopCard.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(shopCardService.insertShopCard(shopCard));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryShopCard shopCard = shopCardService.selectShopCard(id);
        mmap.put("shopcard", shopCard);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:shopcard:edit")
    @Log(title = "店面消费卡", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryShopCard shopCard) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shopCard.setOperatorId(user.getUserId());
            shopCard.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(shopCardService.updateShopCard(shopCard));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:shopcard:remove")
    @Log(title = "店面消费卡", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(shopCardService.deleteShopCards(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

}
