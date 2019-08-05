package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.model.FacKanjiaHelper;
import com.ruoyi.fac.service.IFacKanjiaHelperService;
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
 * 帮砍价人员明细 信息操作处理
 *
 * @author ruoyi
 * @date 2019-07-30
 */
@Controller
@RequestMapping("/fac/facKanjiaHelper")
public class FacKanjiaHelperController extends BaseController {
    private String prefix = "fac/facKanjiaHelper";

    @Autowired
    private IFacKanjiaHelperService facKanjiaHelperService;

    @RequiresPermissions("fac:facKanjiaHelper:view")
    @GetMapping()
    public String facKanjiaHelper() {
        return prefix + "/facKanjiaHelper";
    }

    /**
     * 查询帮砍价人员明细列表
     */
    @RequiresPermissions("fac:facKanjiaHelper:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FacKanjiaHelper facKanjiaHelper) {
        startPage();
        List<FacKanjiaHelper> list = facKanjiaHelperService.selectFacKanjiaHelperList(facKanjiaHelper);
        return getDataTable(list);
    }


    /**
     * 导出帮砍价人员明细列表
     */
    @RequiresPermissions("fac:facKanjiaHelper:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacKanjiaHelper facKanjiaHelper) {
        List<FacKanjiaHelper> list = facKanjiaHelperService.selectFacKanjiaHelperList(facKanjiaHelper);
        ExcelUtil<FacKanjiaHelper> util = new ExcelUtil<>(FacKanjiaHelper.class);
        return util.exportExcel(list, "砍价明细");
    }

    /**
     * 新增帮砍价人员明细
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存帮砍价人员明细
     */
    @RequiresPermissions("fac:facKanjiaHelper:add")
    @Log(title = "帮砍价人员明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FacKanjiaHelper facKanjiaHelper) {
//		return toAjax(facKanjiaHelperService.insertFacKanjiaHelper(facKanjiaHelper));
        return null;
    }

    /**
     * 修改帮砍价人员明细
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
//		FacKanjiaHelper facKanjiaHelper = facKanjiaHelperService.selectFacKanjiaHelperById(id);
//		mmap.put("facKanjiaHelper", facKanjiaHelper);
        return prefix + "/edit";
    }

    /**
     * 修改保存帮砍价人员明细
     */
    @RequiresPermissions("fac:facKanjiaHelper:edit")
    @Log(title = "帮砍价人员明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FacKanjiaHelper facKanjiaHelper) {
//		return toAjax(facKanjiaHelperService.updateFacKanjiaHelper(facKanjiaHelper));
        return null;
    }

    /**
     * 删除帮砍价人员明细
     */
    @RequiresPermissions("fac:facKanjiaHelper:remove")
    @Log(title = "帮砍价人员明细", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        SysUser user = ShiroUtils.getSysUser();
        return toAjax(facKanjiaHelperService.deleteFacKanjiaHelperByIds(ids, user));
    }

}
