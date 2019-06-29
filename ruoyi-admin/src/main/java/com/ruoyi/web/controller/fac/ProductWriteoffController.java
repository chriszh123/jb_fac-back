package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.domain.FacProductWriteoff;
import com.ruoyi.fac.model.FacProductWriteOffBean;
import com.ruoyi.fac.service.IProductWriteoffService;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 核销记录 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/productWriteoff")
public class ProductWriteoffController extends BaseController {
    private String prefix = "fac/productWriteoff";

    @Autowired
    private IProductWriteoffService productWriteoffService;

    @RequiresPermissions("fac:productWriteoff:view")
    @GetMapping()
    public String productWriteoff() {
        return prefix + "/productWriteoff";
    }

    /**
     * 查询核销记录列表
     */
    @RequiresPermissions("fac:productWriteoff:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FacProductWriteoff productWriteoff) {
        startPage();
        List<FacProductWriteOffBean> list = productWriteoffService.selectProductWriteoffList(productWriteoff);
        return getDataTable(list);
    }


    /**
     * 导出核销记录列表
     */
    @RequiresPermissions("fac:productWriteoff:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacProductWriteoff productWriteoff) {
        List<FacProductWriteOffBean> list = productWriteoffService.selectProductWriteoffList(productWriteoff);
        ExcelUtil<FacProductWriteOffBean> util = new ExcelUtil<>(FacProductWriteOffBean.class);
        return util.exportExcel(list, ("核销记录_" + productWriteoff.getProductId()));
    }

    /**
     * 新增核销记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存核销记录
     */
    @RequiresPermissions("fac:productWriteoff:add")
    @Log(title = "核销记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FacProductWriteoff productWriteoff) {
        return toAjax(productWriteoffService.insertProductWriteoff(productWriteoff));
    }

    /**
     * 修改核销记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        FacProductWriteoff productWriteoff = productWriteoffService.selectProductWriteoffById(id);
        mmap.put("productWriteoff", productWriteoff);
        return prefix + "/edit";
    }

    /**
     * 修改保存核销记录
     */
    @RequiresPermissions("fac:productWriteoff:edit")
    @Log(title = "核销记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FacProductWriteoff productWriteoff) {
        return toAjax(productWriteoffService.updateProductWriteoff(productWriteoff));
    }

    /**
     * 删除核销记录
     */
    @RequiresPermissions("fac:productWriteoff:remove")
    @Log(title = "核销记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(productWriteoffService.deleteProductWriteoffByIds(ids));
    }

}
