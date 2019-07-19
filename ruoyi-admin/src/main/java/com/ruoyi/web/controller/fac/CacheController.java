package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Business;
import com.ruoyi.fac.service.IBusinessService;
import com.ruoyi.fac.service.ICacheService;
import com.ruoyi.fac.vo.cache.CacheVo;
import com.ruoyi.framework.shiro.service.SysPasswordService;
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
 * redis缓存数据处理
 *
 * @author ruoyi
 * @date 2019-7-19
 */
@Controller
@RequestMapping("/fac/cache")
public class CacheController extends BaseController {
    private String prefix = "fac/cache";

    @Autowired
    private ICacheService cacheService;

    @RequiresPermissions("fac:cache:view")
    @GetMapping()
    public String business() {
        return prefix + "/cache";
    }

    /**
     * 查询缓存列表
     */
    @RequiresPermissions("fac:cache:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CacheVo vo) {
        startPage();
        List<CacheVo> list = this.cacheService.findCaches(vo);
        return getDataTable(list);
    }

    /**
     * 删除缓存
     */
    @RequiresPermissions("fac:cache:remove")
    @Log(title = "缓存", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(this.cacheService.removeCache(ids));
    }
}
