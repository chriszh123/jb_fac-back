package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.fac.model.FacKanjia;
import com.ruoyi.fac.service.IFacKanjiaService;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 商品砍价设置 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-30
 */
@Controller
@RequestMapping("/fac/facKanjia")
public class FacKanjiaController extends BaseController
{
    private String prefix = "fac/facKanjia";
	
	@Autowired
	private IFacKanjiaService facKanjiaService;
	
	@RequiresPermissions("fac:facKanjia:view")
	@GetMapping()
	public String facKanjia(FacKanjia kanjia)
	{
	    return prefix + "/facKanjia";
	}
	
	/**
	 * 查询商品砍价设置列表
	 */
	@RequiresPermissions("fac:facKanjia:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FacKanjia kanjia)
	{
//		startPage();
//        List<FacKanjia> list = facKanjiaService.selectFacKanjiaList(facKanjia);
//		return getDataTable(list);
		return null;
	}
	
	
	/**
	 * 导出商品砍价设置列表
	 */
	@RequiresPermissions("fac:facKanjia:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacKanjia facKanjia)
    {
//    	List<FacKanjia> list = facKanjiaService.selectFacKanjiaList(facKanjia);
//        ExcelUtil<FacKanjia> util = new ExcelUtil<FacKanjia>(FacKanjia.class);
//        return util.exportExcel(list, "facKanjia");
		return null;
    }
	
	/**
	 * 新增商品砍价设置
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品砍价设置
	 */
	@RequiresPermissions("fac:facKanjia:add")
	@Log(title = "商品砍价设置", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(FacKanjia facKanjia)
	{		
//		return toAjax(facKanjiaService.insertFacKanjia(facKanjia));
		return null;
	}

	/**
	 * 修改商品砍价设置
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
//		FacKanjia facKanjia = facKanjiaService.selectFacKanjiaById(id);
//		mmap.put("facKanjia", facKanjia);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品砍价设置
	 */
	@RequiresPermissions("fac:facKanjia:edit")
	@Log(title = "商品砍价设置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(FacKanjia kanjia)
	{		
//		return toAjax(facKanjiaService.updateFacKanjia(facKanjia));
		return null;
	}
	
	/**
	 * 删除商品砍价设置
	 */
	@RequiresPermissions("fac:facKanjia:remove")
	@Log(title = "商品砍价设置", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
//		return toAjax(facKanjiaService.deleteFacKanjiaByIds(ids));
		return null;
	}
	
}
