package com.ruoyi.web.controller.mry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.model.MryShopCost;
import com.ruoyi.mry.service.MryShopCostService;
import com.ruoyi.system.domain.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mry/shopcost")
public class MryShopCostController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(MryShopCostController.class);

    private String prefix = "mry/shopcost";

    @Autowired
    private MryShopCostService shopCostService;

    @RequiresPermissions("mry:shopcost:view")
    @GetMapping()
    public String shop() {
        return prefix + "/shopcost";
    }

    @RequiresPermissions("mry:shopcost:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryShopCost shopCost) {
        // 这里偷懒了，不分页去查当前条件下的数据金额总和
        String totalAmount = "-";
        List<MryShopCost> list = shopCostService.selectShopCosts(shopCost);
        if (CollectionUtils.isNotEmpty(list)) {
            totalAmount = list.get(0).getTotalAmount();
        }
        log.info(String.format("-----------------[list] totalAmount:%s, shopCost:%s", totalAmount, JSONObject.toJSONString(shopCost)));
        // 下面是分页的结果
        startPage();
        list = shopCostService.selectShopCosts(shopCost);
        if (CollectionUtils.isNotEmpty(list)) {
            list.get(0).setTotalAmount(totalAmount);
        }
        return getDataTable(list);
    }

    @RequiresPermissions("mry:shopcost:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryShopCost shopCost) {
        List<MryShopCost> list = shopCostService.selectShopCosts(shopCost);
        ExcelUtil<MryShopCost> util = new ExcelUtil<MryShopCost>(MryShopCost.class);
        return util.exportExcel(list, "店面消费一览");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:shopcost:add")
    @Log(title = "店面话费", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryShopCost shopCost) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shopCost.setOperatorId(user.getUserId());
            shopCost.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(shopCostService.insertShopCost(shopCost));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryShopCost shop = shopCostService.selectShopCostById(id);
        mmap.put("shopcost", shop);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:shopcost:edit")
    @Log(title = "店面花费", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryShopCost shopcost) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            shopcost.setOperatorId(user.getUserId());
            shopcost.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(shopCostService.updateShopCost(shopcost));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:shopcost:remove")
    @Log(title = "店面花费", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(this.shopCostService.deleteShopCostByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }
}
