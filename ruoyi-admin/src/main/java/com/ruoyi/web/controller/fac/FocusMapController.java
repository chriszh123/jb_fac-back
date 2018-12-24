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
import com.ruoyi.fac.domain.FocusMap;
import com.ruoyi.fac.service.IFocusMapService;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.utils.ExcelUtil;

/**
 * 焦点图 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/focusMap")
public class FocusMapController extends BaseController {
    private String prefix = "fac/focusMap";

    @Autowired
    private IFocusMapService focusMapService;

    @RequiresPermissions("fac:focusMap:view")
    @GetMapping()
    public String focusMap() {
        return prefix + "/focusMap";
    }

    /**
     * 查询焦点图列表
     */
    @RequiresPermissions("fac:focusMap:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FocusMap focusMap) {
        startPage();
        List<FocusMap> list = focusMapService.selectFocusMapList(focusMap);
        return getDataTable(list);
    }


    /**
     * 导出焦点图列表
     */
    @RequiresPermissions("fac:focusMap:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FocusMap focusMap) {
        List<FocusMap> list = focusMapService.selectFocusMapList(focusMap);
        ExcelUtil<FocusMap> util = new ExcelUtil<FocusMap>(FocusMap.class);
        return util.exportExcel(list, "focusMap");
    }

    /**
     * 新增焦点图
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存焦点图
     */
    @RequiresPermissions("fac:focusMap:add")
    @Log(title = "焦点图", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FocusMap focusMap) {
        return toAjax(focusMapService.insertFocusMap(focusMap));
    }

    /**
     * 修改焦点图
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        FocusMap focusMap = focusMapService.selectFocusMapById(id);
        mmap.put("focusMap", focusMap);
        return prefix + "/edit";
    }

    /**
     * 修改保存焦点图
     */
    @RequiresPermissions("fac:focusMap:edit")
    @Log(title = "焦点图", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FocusMap focusMap) {
        return toAjax(focusMapService.updateFocusMap(focusMap));
    }

    /**
     * 删除焦点图
     */
    @RequiresPermissions("fac:focusMap:remove")
    @Log(title = "焦点图", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(focusMapService.deleteFocusMapByIds(ids));
    }

}
