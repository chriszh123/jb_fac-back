package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.model.FacKanjiaJoiner;
import com.ruoyi.fac.service.IFacKanjiaJoinerService;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 参与商品砍价活动人员记录 信息操作处理
 *
 * @author ruoyi
 * @date 2019-07-30
 */
@Controller
@RequestMapping("/fac/facKanjiaJoiner")
public class FacKanjiaJoinerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(FacKanjiaJoinerController.class);

    private String prefix = "fac/facKanjiaJoiner";

    @Autowired
    private IFacKanjiaJoinerService facKanjiaJoinerService;

    @RequiresPermissions("fac:facKanjiaJoiner:view")
    @GetMapping()
    public String facKanjiaJoiner() {
        return prefix + "/facKanjiaJoiner";
    }

    /**
     * 查询参与商品砍价活动人员记录列表
     */
    @RequiresPermissions("fac:facKanjiaJoiner:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FacKanjiaJoiner facKanjiaJoiner) {
        startPage();
        List<FacKanjiaJoiner> list = this.facKanjiaJoinerService.selectFacKanjiaJoinerList(facKanjiaJoiner);
        return getDataTable(list);
    }


    /**
     * 导出参与商品砍价活动人员记录列表
     */
    @RequiresPermissions("fac:facKanjiaJoiner:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacKanjiaJoiner facKanjiaJoiner) {
        List<FacKanjiaJoiner> list = this.facKanjiaJoinerService.selectFacKanjiaJoinerList(facKanjiaJoiner);
        ExcelUtil<FacKanjiaJoiner> util = new ExcelUtil<>(FacKanjiaJoiner.class);
        return util.exportExcel(list, "砍价参与用户");
    }

    /**
     * 修改参与商品砍价活动人员记录
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
//		FacKanjiaJoiner facKanjiaJoiner = facKanjiaJoinerService.selectFacKanjiaJoinerById(id);
//		mmap.put("facKanjiaJoiner", facKanjiaJoiner);
        return prefix + "/edit";
    }

    /**
     * 修改保存参与商品砍价活动人员记录
     */
    @RequiresPermissions("fac:facKanjiaJoiner:edit")
    @Log(title = "参与商品砍价活动人员记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FacKanjiaJoiner facKanjiaJoiner) {
//		return toAjax(facKanjiaJoinerService.updateFacKanjiaJoiner(facKanjiaJoiner));
        return null;
    }

    /**
     * 删除参与商品砍价活动人员记录
     */
    @RequiresPermissions("fac:facKanjiaJoiner:remove")
    @Log(title = "参与商品砍价活动人员记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
//		return toAjax(facKanjiaJoinerService.deleteFacKanjiaJoinerByIds(ids));
        return null;
    }

}
