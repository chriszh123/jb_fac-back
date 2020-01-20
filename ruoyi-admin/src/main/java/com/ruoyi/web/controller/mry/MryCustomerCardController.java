/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 客户消费卡管理
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
import com.ruoyi.mry.model.MryCustomer;
import com.ruoyi.mry.model.MryCustomerCard;
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.model.MryShopCard;
import com.ruoyi.mry.service.MryCustomerCardService;
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
import java.util.Map;

/**
 * 客户消费卡管理
 * @author zhangguifeng
 * @create 2020-01-17 17:14
 **/
@Controller
@RequestMapping("/mry/customercard")
public class MryCustomerCardController extends BaseController {

    private String prefix = "mry/customercard";

    @Autowired
    private MryCustomerCardService customerCardService;

    @RequiresPermissions("mry:customercard:view")
    @GetMapping()
    public String customercard() {
        return prefix + "/customercard";
    }

    @RequiresPermissions("mry:customercard:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryCustomerCard customerCard) {
        startPage();
        List<MryCustomerCard> list = customerCardService.selectCustomerCards(customerCard);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:customercard:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryCustomerCard customerCard) {
        List<MryCustomerCard> list = customerCardService.selectCustomerCards(customerCard);
        ExcelUtil<MryCustomerCard> util = new ExcelUtil<>(MryCustomerCard.class);
        return util.exportExcel(list, "客户办卡资料");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:customercard:add")
    @Log(title = "客户办卡资料", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryCustomerCard customerCard) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerCard.setOperatorId(user.getUserId());
            customerCard.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(customerCardService.insertMryCustomerCard(customerCard));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryCustomerCard customerCard = customerCardService.selectCustomerCardById(id);
        mmap.put("customerCard", customerCard);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:customercard:edit")
    @Log(title = "客户办卡资料", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryCustomerCard customerCard) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerCard.setOperatorId(user.getUserId());
            customerCard.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(customerCardService.updateMryCustomerCard(customerCard));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:customercard:remove")
    @Log(title = "客户办卡资料", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(customerCardService.deleteMryCustomerCardByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

    @PostMapping("/getCustomersByShopId")
    @ResponseBody
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        List<MryCustomer> customers = this.customerCardService.getCustomersByShopId(customerCard);
        return customers;
    }

    @PostMapping("/getShopCardsByShopId")
    @ResponseBody
    public List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard) {
        List<MryShopCard> shopCards = this.customerCardService.getShopCardsByShopId(customerCard);
        return shopCards;
    }

    @PostMapping("/getShopCardsByShopId")
    @ResponseBody
    public List<MryServicePro> getServiceProsByShopId(MryCustomerCard customerCard) {
        List<MryServicePro> servicePros = this.customerCardService.getServiceProsByShopId(customerCard);
        return servicePros;
    }
}
