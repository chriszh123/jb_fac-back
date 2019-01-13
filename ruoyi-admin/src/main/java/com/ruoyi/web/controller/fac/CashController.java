package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Cash;
import com.ruoyi.fac.service.ICashService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 买家提现记录 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/cash")
public class CashController extends BaseController {
    private String prefix = "fac/cash";

    @Autowired
    private ICashService cashService;

    @RequiresPermissions("fac:cash:view")
    @GetMapping()
    public String cash() {
        return prefix + "/cash";
    }

    /**
     * 查询买家提现记录列表
     */
    @RequiresPermissions("fac:cash:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Cash cash) {
        startPage();
        List<Cash> list = cashService.selectCashList(cash);
        return getDataTable(list);
    }


    /**
     * 导出买家提现记录列表
     */
    @RequiresPermissions("fac:cash:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Cash cash) {
        List<Cash> list = cashService.selectCashList(cash);
        ExcelUtil<Cash> util = new ExcelUtil<Cash>(Cash.class);
        return util.exportExcel(list, "提现");
    }

    /**
     * 新增买家提现记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存买家提现记录
     */
    @RequiresPermissions("fac:cash:add")
    @Log(title = "买家提现记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Cash cash) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            cash.setOperatorName(user.getUserName());
            cash.setOperatorId(user.getUserId());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(cashService.insertCash(cash));
    }

    /**
     * 修改买家提现记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Cash cash = cashService.selectCashById(id);
        mmap.put("cash", cash);
        return prefix + "/edit";
    }

    /**
     * 修改保存买家提现记录
     */
    @RequiresPermissions("fac:cash:edit")
    @Log(title = "买家提现记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Cash cash) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            cash.setOperatorName(user.getUserName());
            cash.setOperatorId(user.getUserId());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(cashService.updateCash(cash));
    }

    /**
     * 删除买家提现记录
     */
    @RequiresPermissions("fac:cash:remove")
    @Log(title = "买家提现记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(cashService.deleteCashByIds(ids));
    }

}
