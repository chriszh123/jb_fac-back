package com.ruoyi.web.controller.fac;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fac.domain.Menu;
import com.ruoyi.fac.service.IMenuService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 菜单管理 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/menu")
public class MenuController extends BaseController {
    private String prefix = "fac/menu";

    @Autowired
    private IMenuService menuService;

    @RequiresPermissions("fac:menu:view")
    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    /**
     * 查询菜单管理列表
     */
    @RequiresPermissions("fac:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Menu menu) {
        startPage();
        List<Menu> list = menuService.selectMenuList(menu);
        return getDataTable(list);
    }


    /**
     * 导出菜单管理列表
     */
    @RequiresPermissions("fac:menu:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Menu menu) {
        List<Menu> list = menuService.selectMenuList(menu);
        ExcelUtil<Menu> util = new ExcelUtil<Menu>(Menu.class);
        return util.exportExcel(list, "menu");
    }

    /**
     * 新增菜单管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存菜单管理
     */
    @RequiresPermissions("fac:menu:add")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Menu menu) {
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        Menu menu = menuService.selectMenuById(id);
        mmap.put("menu", menu);
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单管理
     */
    @RequiresPermissions("fac:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Menu menu) {
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单管理
     */
    @RequiresPermissions("fac:menu:remove")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(menuService.deleteMenuByIds(ids));
    }

}
