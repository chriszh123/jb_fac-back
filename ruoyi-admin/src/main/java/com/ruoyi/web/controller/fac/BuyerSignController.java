package com.ruoyi.web.controller.fac;

import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.model.FacBuyerSign;
import com.ruoyi.fac.service.IFacBuyerSignService;
import com.ruoyi.framework.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户签到等积分 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-05-26
 */
@Controller
@RequestMapping("/fac/buyerSign")
public class BuyerSignController extends BaseController
{
    private String prefix = "fac/buyerSign";

	@Autowired
	private IFacBuyerSignService facBuyerSignService;

	
	@RequiresPermissions("fac:buyerSign:view")
	@GetMapping()
	public String facBuyerSign()
	{
	    return prefix + "/buyerSign";
	}
	
	/**
	 * 查询用户签到等积分列表
	 */
	@RequiresPermissions("fac:facBuyerSign:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(FacBuyerSign facBuyerSign)
	{
		startPage();
        List<FacBuyerSign> list = facBuyerSignService.selectFacBuyerSignList(facBuyerSign);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出用户签到等积分列表
	 */
	@RequiresPermissions("fac:facBuyerSign:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FacBuyerSign facBuyerSign)
    {
    	List<FacBuyerSign> list = facBuyerSignService.selectFacBuyerSignList(facBuyerSign);
        ExcelUtil<FacBuyerSign> util = new ExcelUtil<FacBuyerSign>(FacBuyerSign.class);
        return util.exportExcel(list, "积分");
    }

}
