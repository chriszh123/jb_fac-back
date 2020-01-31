/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 员工休假管理
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
import com.ruoyi.mry.model.MryStaff;
import com.ruoyi.mry.model.MryStaffLeave;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.mry.service.MryStaffService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工考勤管理
 * @author zhangguifeng
 * @create 2020-01-17 17:16
 **/
@Controller
@RequestMapping("/mry/staffleave")
public class MryStaffLeaveController extends BaseController {

    private String prefix = "mry/staffleave";

    @Autowired
    private MryStaffService staffService;

    @RequiresPermissions("mry:staffleave:view")
    @GetMapping()
    public String staffleave() {
        return prefix + "/staffleave";
    }

    @RequiresPermissions("mry:staffleave:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryStaffLeave staffLeave) {
        startPage();
        List<MryStaffLeave> list = staffService.selectStaffLeaves(staffLeave);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:staffleave:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryStaffLeave staffLeave) {
        List<MryStaffLeave> list = staffService.selectStaffLeaves(staffLeave);
        ExcelUtil<MryStaffLeave> util = new ExcelUtil<>(MryStaffLeave.class);
        return util.exportExcel(list, "员工考勤");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:staffleave:add")
    @Log(title = "员工", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryStaffLeave staffLeave) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            staffLeave.setOperatorId(user.getUserId());
            staffLeave.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(staffService.insertStaffLeave(staffLeave));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryStaffLeave staffleave = staffService.selectStaffLeaveById(id);
        mmap.put("staffleave", staffleave);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:staffleave:edit")
    @Log(title = "员工", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryStaffLeave staffLeave) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            staffLeave.setOperatorId(user.getUserId());
            staffLeave.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(staffService.updateStaffLeave(staffLeave));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:staffleave:remove")
    @Log(title = "员工", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(staffService.deleteStaffLeaveByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

    @PostMapping("/getStaffsByShopId")
    @ResponseBody
    public List<MryStaff> getStaffsByShopId(MryStaffLeave staffLeave) {
        List<MryStaff> staffList = this.staffService.getStaffsByShopId(staffLeave);
        return staffList;
    }
}
