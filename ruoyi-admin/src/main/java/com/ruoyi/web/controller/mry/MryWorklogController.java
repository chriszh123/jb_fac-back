/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 日志管理
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
import com.ruoyi.mry.model.MryWorklog;
import com.ruoyi.mry.service.MryWorklogService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志管理
 * @author zhangguifeng
 * @create 2020-01-17 17:09
 **/
@Controller
@RequestMapping("/mry/worklog")
public class MryWorklogController extends BaseController {
    private String prefix = "mry/worklog";

    @Autowired
    private MryWorklogService worklogService;

    @RequiresPermissions("mry:worklog:view")
    @GetMapping()
    public String shop() {
        return prefix + "/worklog";
    }

    @RequiresPermissions("mry:worklog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryWorklog worklog) {
        startPage();
        List<MryWorklog> list = worklogService.selectWorklogs(worklog);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:worklog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryWorklog worklog) {
        List<MryWorklog> list = worklogService.selectWorklogs(worklog);
        ExcelUtil<MryWorklog> util = new ExcelUtil<MryWorklog>(MryWorklog.class);
        return util.exportExcel(list, "工作日志");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:worklog:add")
    @Log(title = "工作日志", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryWorklog worklog) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            worklog.setOperatorId(user.getUserId());
            worklog.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(worklogService.insertWorklog(worklog));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryWorklog worklog = worklogService.selectWorklog(id);
        mmap.put("worklog", worklog);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:worklog:edit")
    @Log(title = "工作日志", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryWorklog worklog) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            worklog.setOperatorId(user.getUserId());
            worklog.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(worklogService.updateWorklog(worklog));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:worklog:remove")
    @Log(title = "工作日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(worklogService.deleteWorklogByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }
}
