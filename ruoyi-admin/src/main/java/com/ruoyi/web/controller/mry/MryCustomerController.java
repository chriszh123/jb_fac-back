/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 店面客户资料管理
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
import com.ruoyi.mry.service.MryCustomerService;
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
 * 店面客户资料管理
 * @author zhangguifeng
 * @create 2020-01-17 17:08
 **/
@Controller
@RequestMapping("/mry/customer")
public class MryCustomerController extends BaseController {

    private String prefix = "mry/customer";

    @Autowired
    private MryCustomerService customerService;

    @RequiresPermissions("mry:customer:view")
    @GetMapping()
    public String customer() {
        return prefix + "/customer";
    }

    @RequiresPermissions("mry:customer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryCustomer customer) {
        startPage();
        List<MryCustomer> list = customerService.selectCustomers(customer);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:customer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryCustomer customer) {
        List<MryCustomer> list = customerService.selectCustomers(customer);
        ExcelUtil<MryCustomer> util = new ExcelUtil<>(MryCustomer.class);
        return util.exportExcel(list, "客户基本资料");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:customer:add")
    @Log(title = "客户基本资料", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryCustomer customer) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customer.setOperatorId(user.getUserId());
            customer.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(customerService.insertCustomer(customer));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryCustomer customer = customerService.selectCustomerById(id);
        mmap.put("customer", customer);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:customer:edit")
    @Log(title = "客户基本资料", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryCustomer customer) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customer.setOperatorId(user.getUserId());
            customer.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(customerService.updateCustomer(customer));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:customer:remove")
    @Log(title = "客户基本资料", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(customerService.deleteCustomersByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }
}
