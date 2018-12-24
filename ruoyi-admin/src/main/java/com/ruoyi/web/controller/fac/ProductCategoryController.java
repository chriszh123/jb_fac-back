package

        com.ruoyi.web.controller.fac;

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
import com.ruoyi.fac.domain.ProductCategory;
import com.ruoyi.fac.service.IProductCategoryService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 商品类目 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/productCategory")
public class ProductCategoryController extends BaseController {
    private String prefix = "fac/productCategory";

    @Autowired
    private IProductCategoryService productCategoryService;

    @RequiresPermissions("fac:productCategory:view")
    @GetMapping()
    public String productCategory() {
        return prefix + "/productCategory";
    }

    /**
     * 查询商品类目列表
     */
    @RequiresPermissions("fac:productCategory:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProductCategory productCategory) {
        startPage();
        List<ProductCategory> list = productCategoryService.selectProductCategoryList(productCategory);
        return getDataTable(list);
    }


    /**
     * 导出商品类目列表
     */
    @RequiresPermissions("fac:productCategory:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProductCategory productCategory) {
        List<ProductCategory> list = productCategoryService.selectProductCategoryList(productCategory);
        ExcelUtil<ProductCategory> util = new ExcelUtil<ProductCategory>(ProductCategory.class);
        return util.exportExcel(list, "productCategory");
    }

    /**
     * 新增商品类目
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存商品类目
     */
    @RequiresPermissions("fac:productCategory:add")
    @Log(title = "商品类目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ProductCategory productCategory) {
        return toAjax(productCategoryService.insertProductCategory(productCategory));
    }

    /**
     * 修改商品类目
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ProductCategory productCategory = productCategoryService.selectProductCategoryById(id);
        mmap.put("productCategory", productCategory);
        return prefix + "/edit";
    }

    /**
     * 修改保存商品类目
     */
    @RequiresPermissions("fac:productCategory:edit")
    @Log(title = "商品类目", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ProductCategory productCategory) {
        return toAjax(productCategoryService.updateProductCategory(productCategory));
    }

    /**
     * 删除商品类目
     */
    @RequiresPermissions("fac:productCategory:remove")
    @Log(title = "商品类目", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(productCategoryService.deleteProductCategoryByIds(ids));
    }

}
