/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/1/17
 * Description: 店面服务项目
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
import com.ruoyi.mry.model.MryServicePro;
import com.ruoyi.mry.service.MryServiceProService;
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
 * 店面服务项目
 * @author zhangguifeng
 * @create 2020-01-17 17:07
 **/
@Controller
@RequestMapping("/mry/servicepro")
public class MryServiceProController extends BaseController {

    private String prefix = "mry/servicepro";

    @Autowired
    private MryServiceProService serviceProService;

    @RequiresPermissions("mry:servicepro:view")
    @GetMapping()
    public String servicepro() {
        return prefix + "/servicepro";
    }

    @RequiresPermissions("mry:servicepro:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryServicePro servicePro) {
        startPage();
        List<MryServicePro> list = serviceProService.selectMryServicePros(servicePro);
        return getDataTable(list);
    }

    @RequiresPermissions("mry:servicepro:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryServicePro servicePro) {
        List<MryServicePro> list = serviceProService.selectMryServicePros(servicePro);
        ExcelUtil<MryServicePro> util = new ExcelUtil<>(MryServicePro.class);
        return util.exportExcel(list, "服务项目");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:servicepro:add")
    @Log(title = "服务项目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryServicePro servicePro) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            servicePro.setOperatorId(user.getUserId());
            servicePro.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(serviceProService.insertServicePro(servicePro));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryServicePro servicePro = serviceProService.selectServiceProById(id);
        mmap.put("servicepro", servicePro);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:servicepro:edit")
    @Log(title = "服务项目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryServicePro servicePro) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            servicePro.setOperatorId(user.getUserId());
            servicePro.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(serviceProService.updateServicePro(servicePro));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:servicepro:remove")
    @Log(title = "服务项目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(serviceProService.deleteServicePros(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

    /**
     * 加载员工列表树
     */
    @GetMapping("/staffTreeData/{id}")
    @ResponseBody
    public List<Map<String, Object>> staffTreeData(@PathVariable("id") Short id) {
        MryServicePro servicePro = new MryServicePro();
        servicePro.setId(id);
        List<Map<String, Object>> tree = this.serviceProService.staffTreeData(servicePro);
        return tree;
    }
}
