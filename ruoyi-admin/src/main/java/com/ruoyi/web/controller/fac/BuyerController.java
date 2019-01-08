package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.ExcelUtil;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysRole;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 买者用户 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/buyer")
public class BuyerController extends BaseController {
    private String prefix = "fac/buyer";

    @Autowired
    private IBuyerService buyerService;

    @RequiresPermissions("fac:buyer:view")
    @GetMapping()
    public String buyer() {
        return prefix + "/buyer";
    }

    /**
     * 查询买者用户列表
     */
    @RequiresPermissions("fac:buyer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Buyer buyer) {
        startPage();
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        return getDataTable(list);
    }


    /**
     * 导出买者用户列表
     */
    @RequiresPermissions("fac:buyer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Buyer buyer) {
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        ExcelUtil<Buyer> util = new ExcelUtil<Buyer>(Buyer.class);
        return util.exportExcel(list, "用户");
    }

    /**
     * 新增买者用户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存买者用户
     */
    @RequiresPermissions("fac:buyer:add")
    @Log(title = "买者用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Buyer buyer) {
        return toAjax(buyerService.insertBuyer(buyer));
    }

    /**
     * 修改买者用户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Buyer buyer = buyerService.selectBuyerById(id);
        mmap.put("buyer", buyer);
        return prefix + "/edit";
    }

    /**
     * 修改保存买者用户
     */
    @RequiresPermissions("fac:buyer:edit")
    @Log(title = "买者用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Buyer buyer) {
        return toAjax(buyerService.updateBuyer(buyer));
    }

    /**
     * 删除买者用户
     */
    @RequiresPermissions("fac:buyer:remove")
    @Log(title = "买者用户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(buyerService.deleteBuyerByIds(ids));
    }

    /**
     * 加载商家商品列表树
     */
    @GetMapping("/bizProdTreeData")
    @ResponseBody
    public List<Map<String, Object>> bizProdTreeData(Buyer buyer) {
        List<Map<String, Object>> tree = new ArrayList<>();
        return tree;
    }
}
