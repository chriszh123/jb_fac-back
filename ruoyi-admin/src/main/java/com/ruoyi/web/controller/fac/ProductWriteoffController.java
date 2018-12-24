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
import com.ruoyi.fac.domain.ProductWriteoff;
import com.ruoyi.fac.service.IProductWriteoffService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

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
    public TableDataInfo list(ProductWriteoff productWriteoff) {
        startPage();
        List<ProductWriteoff> list = productWriteoffService.selectProductWriteoffList(productWriteoff);
        return getDataTable(list);
    }


    /**
     * 导出核销记录列表
     */
    @RequiresPermissions("fac:productWriteoff:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProductWriteoff productWriteoff) {
        List<ProductWriteoff> list = productWriteoffService.selectProductWriteoffList(productWriteoff);
        ExcelUtil<ProductWriteoff> util = new ExcelUtil<ProductWriteoff>(ProductWriteoff.class);
        return util.exportExcel(list, "productWriteoff");
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
    public AjaxResult addSave(ProductWriteoff productWriteoff) {
        return toAjax(productWriteoffService.insertProductWriteoff(productWriteoff));
    }

    /**
     * 修改核销记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        ProductWriteoff productWriteoff = productWriteoffService.selectProductWriteoffById(id);
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
    public AjaxResult editSave(ProductWriteoff productWriteoff) {
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
