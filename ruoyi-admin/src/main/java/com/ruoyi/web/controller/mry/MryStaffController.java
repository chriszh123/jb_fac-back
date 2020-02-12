/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 员工管理
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
import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.mry.service.MryStaffService;
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
 * 员工管理
 * @author zhangguifeng
 * @create 2020-01-17 17:10
 **/
@Controller
@RequestMapping("/mry/staff")
public class MryStaffController extends BaseController {

    private String prefix = "mry/staff";

    @Autowired
    private MryStaffService staffService;

    @RequiresPermissions("mry:staff:view")
    @GetMapping()
    public String staff() {
        return prefix + "/staff";
    }

    @RequiresPermissions("mry:staff:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryStaff staff) {
        startPage();
        List<MryStaff> list = staffService.selectStaffs(staff);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:staff:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryStaff staff) {
        List<MryStaff> list = staffService.selectStaffs(staff);
        ExcelUtil<MryStaff> util = new ExcelUtil<>(MryStaff.class);
        return util.exportExcel(list, "所有美容师");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:staff:add")
    @Log(title = "美容师", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryStaff staff) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            staff.setOperatorId(user.getUserId());
            staff.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(staffService.insertStaff(staff));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryStaff staff = staffService.selectStaffById(id);
        mmap.put("staff", staff);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:staff:edit")
    @Log(title = "美容师", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryStaff staff) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            staff.setOperatorId(user.getUserId());
            staff.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(staffService.updateStaff(staff));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:staff:remove")
    @Log(title = "美容师", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(staffService.deleteStaffByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }
}
