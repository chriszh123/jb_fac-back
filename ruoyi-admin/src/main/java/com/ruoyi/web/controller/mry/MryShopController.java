/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 店面管理
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
import com.ruoyi.mry.model.MryShop;
import com.ruoyi.mry.service.MryShopService;
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
 * 店面管理
 * @author zhangguifeng
 * @create 2020-01-17 17:05
 **/
@Controller
@RequestMapping("/mry/shop")
public class MryShopController extends BaseController {

    private String prefix = "mry/shop";

    @Autowired
    private MryShopService shopService;


    @RequiresPermissions("mry:shop:view")
    @GetMapping()
    public String shop() {
        return prefix + "/shop";
    }

    /**
     * 查询店面列表
     */
    @RequiresPermissions("mry:shop:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryShop shop) {
        startPage();
        List<MryShop> list = shopService.selectShops(shop);
        return getDataTable(list);
    }

    /**
     * 导出
     */
    @RequiresPermissions("mry:shop:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryShop shop) {
        List<MryShop> list = shopService.selectShops(shop);
        ExcelUtil<MryShop> util = new ExcelUtil<MryShop>(MryShop.class);
        return util.exportExcel(list, "所有店面");
    }

    /**
     * 新增
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增
     */
    @RequiresPermissions("mry:shop:add")
    @Log(title = "店面", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryShop shop) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shop.setOperatorId(user.getUserId());
            shop.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(shopService.insertShop(shop));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryShop shop = shopService.selectShopById(id);
        mmap.put("shop", shop);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     */
    @RequiresPermissions("mry:shop:edit")
    @Log(title = "店面", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryShop shop) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shop.setOperatorId(user.getUserId());
            shop.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(shopService.updateShop(shop));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    /**
     * 删除
     */
    @RequiresPermissions("mry:shop:remove")
    @Log(title = "店面", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(shopService.deleteShopByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }
}
