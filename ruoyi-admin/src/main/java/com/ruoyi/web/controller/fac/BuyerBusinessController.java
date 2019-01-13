package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.domain.BuyerBusiness;
import com.ruoyi.fac.service.IBuyerBusinessService;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 买者与商家商品关联 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/buyerBusiness")
public class BuyerBusinessController extends BaseController {
    private String prefix = "fac/buyerBusiness";

    @Autowired
    private IBuyerBusinessService buyerBusinessService;

    @RequiresPermissions("fac:buyerBusiness:view")
    @GetMapping()
    public String buyerBusiness() {
        return prefix + "/buyerBusiness";
    }

    /**
     * 查询买者与商家商品关联列表
     */
    @RequiresPermissions("fac:buyerBusiness:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BuyerBusiness buyerBusiness) {
        startPage();
        List<BuyerBusiness> list = buyerBusinessService.selectBuyerBusinessList(buyerBusiness);
        return getDataTable(list);
    }


    /**
     * 导出买者与商家商品关联列表
     */
    @RequiresPermissions("fac:buyerBusiness:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BuyerBusiness buyerBusiness) {
        List<BuyerBusiness> list = buyerBusinessService.selectBuyerBusinessList(buyerBusiness);
        ExcelUtil<BuyerBusiness> util = new ExcelUtil<BuyerBusiness>(BuyerBusiness.class);
        return util.exportExcel(list, "buyerBusiness");
    }

    /**
     * 新增买者与商家商品关联
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存买者与商家商品关联
     */
    @RequiresPermissions("fac:buyerBusiness:add")
    @Log(title = "买者与商家商品关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BuyerBusiness buyerBusiness) {
        return toAjax(buyerBusinessService.insertBuyerBusiness(buyerBusiness));
    }

    /**
     * 修改买者与商家商品关联
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        BuyerBusiness buyerBusiness = buyerBusinessService.selectBuyerBusinessById(id);
        mmap.put("buyerBusiness", buyerBusiness);
        return prefix + "/edit";
    }

    /**
     * 修改保存买者与商家商品关联
     */
    @RequiresPermissions("fac:buyerBusiness:edit")
    @Log(title = "买者与商家商品关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BuyerBusiness buyerBusiness) {
        return toAjax(buyerBusinessService.updateBuyerBusiness(buyerBusiness));
    }

    /**
     * 删除买者与商家商品关联
     */
    @RequiresPermissions("fac:buyerBusiness:remove")
    @Log(title = "买者与商家商品关联", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(buyerBusinessService.deleteBuyerBusinessByIds(ids));
    }

}
