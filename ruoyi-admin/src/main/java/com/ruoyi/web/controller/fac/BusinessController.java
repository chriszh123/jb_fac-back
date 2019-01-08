package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.service.IBusinessService;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 商家 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/business")
public class BusinessController extends BaseController {
    private String prefix = "fac/business";

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("fac:business:view")
    @GetMapping()
    public String business() {
        return prefix + "/business";
    }

    /**
     * 查询商家列表
     */
    @RequiresPermissions("fac:business:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Business business) {
        startPage();
        List<Business> list = businessService.selectBusinessList(business);
        return getDataTable(list);
    }


    /**
     * 导出商家列表
     */
    @RequiresPermissions("fac:business:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Business business) {
        List<Business> list = businessService.selectBusinessList(business);
        ExcelUtil<Business> util = new ExcelUtil<Business>(Business.class);
        return util.exportExcel(list, "所有商家");
    }

    /**
     * 新增商家
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商家
     */
    @RequiresPermissions("fac:business:add")
    @Log(title = "商家", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Business business) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            business.setCreateBy(user.getUserName());
            business.setOperatorId(user.getUserId());
            business.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        Date nowDate = new Date();
        business.setCreateTime(nowDate);
        business.setUpdateTime(nowDate);
        // 登录密码加密
        String salt = ShiroUtils.randomSalt();
        String loginPwd = passwordService.encryptPassword(business.getLoginName(), business.getLoginPwd(), salt);
        business.setLoginPwd(loginPwd);
        return toAjax(businessService.insertBusiness(business));
    }

    /**
     * 修改商家
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Business business = businessService.selectBusinessById(id);
        mmap.put("business", business);
        return prefix + "/edit";
    }

    /**
     * 修改保存商家
     */
    @RequiresPermissions("fac:business:edit")
    @Log(title = "商家", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Business business) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            business.setCreateBy(user.getUserName());
            business.setOperatorId(user.getUserId());
            business.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        Date nowDate = new Date();
        business.setCreateTime(nowDate);
        business.setUpdateTime(nowDate);
        // 登录密码加密
        String salt = ShiroUtils.randomSalt();
        String loginPwd = passwordService.encryptPassword(business.getLoginName(), business.getLoginPwd(), salt);
        business.setLoginPwd(loginPwd);
        return toAjax(businessService.updateBusiness(business));
    }

    /**
     * 删除商家
     */
    @RequiresPermissions("fac:business:remove")
    @Log(title = "商家", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(businessService.deleteBusinessByIds(ids));
    }
}
