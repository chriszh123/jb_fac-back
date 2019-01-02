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
import com.ruoyi.fac.domain.Product;
import com.ruoyi.fac.service.IProductService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 商品 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/product")
public class ProductController extends BaseController {
    private String prefix = "fac/product";

    @Autowired
    private IProductService productService;

    @RequiresPermissions("fac:product:view")
    @GetMapping()
    public String product() {
        return prefix + "/product";
    }

    /**
     * 查询商品列表
     */
    @RequiresPermissions("fac:product:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Product product) {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }


    /**
     * 导出商品列表
     */
    @RequiresPermissions("fac:product:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Product product) {
        List<Product> list = productService.selectProductList(product);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 新增商品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品
     */
    @RequiresPermissions("fac:product:add")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Product product) {
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改商品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Product product = productService.selectProductById(id);
        mmap.put("product", product);
        return prefix + "/edit";
    }

    /**
     * 修改保存商品
     */
    @RequiresPermissions("fac:product:edit")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Product product) {
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除商品
     */
    @RequiresPermissions("fac:product:remove")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(productService.deleteProductByIds(ids));
    }

}