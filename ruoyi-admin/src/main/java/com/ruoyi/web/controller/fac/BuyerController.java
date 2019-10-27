package com.ruoyi.web.controller.fac;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.fac.domain.Buyer;
import com.ruoyi.fac.model.FacBuyerAddress;
import com.ruoyi.fac.service.IBuyerService;
import com.ruoyi.fac.vo.UserDiagramVo;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 买者用户 信息操作处理
 *
 * @author ruoyi
 * @date 2018-12-24
 */
@Controller
@RequestMapping("/fac/buyer")
public class BuyerController extends BaseController {
    private String prefix = "fac/buyer";
    private String prefix_analysis = "fac/dataAnalysis";

    @Autowired
    private IBuyerService buyerService;

    @RequiresPermissions("fac:buyer:view")
    @GetMapping()
    public String buyer() {
        return prefix + "/buyer";
    }

    /**
     * 查询买者用户列表
     */
    @RequiresPermissions("fac:buyer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Buyer buyer) {
        startPage();
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        return getDataTable(list);
    }


    /**
     * 导出买者用户列表
     */
    @RequiresPermissions("fac:buyer:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Buyer buyer) {
        List<Buyer> list = buyerService.selectBuyerList(buyer);
        ExcelUtil<Buyer> util = new ExcelUtil<Buyer>(Buyer.class);
        return util.exportExcel(list, "用户");
    }

    /**
     * 新增买者用户
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存买者用户
     */
    @RequiresPermissions("fac:buyer:add")
    @Log(title = "买者用户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Buyer buyer) {
        return toAjax(buyerService.insertBuyer(buyer));
    }

    /**
     * 修改买者用户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Buyer buyer = buyerService.selectBuyerById(id);
        mmap.put("buyer", buyer);
        return prefix + "/edit";
    }

    /**
     * 修改保存买者用户
     */
    @RequiresPermissions("fac:buyer:edit")
    @Log(title = "买者用户", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Buyer buyer) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            buyer.setOperatorName(user.getUserName());
            buyer.setOperatorId(user.getUserId());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(buyerService.updateBuyer(buyer));
    }

    /**
     * 删除买者用户
     */
    @RequiresPermissions("fac:buyer:remove")
    @Log(title = "买者用户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(buyerService.deleteBuyerByIds(ids));
    }

    /**
     * 加载商家商品列表树
     */
    @GetMapping("/bizProdTreeData/{id}")
    @ResponseBody
    public List<Map<String, Object>> bizProdTreeData(@PathVariable("id") Long id) {
        Buyer buyer = new Buyer();
        buyer.setId(id);
        List<Map<String, Object>> tree = this.buyerService.bizProdTreeData(buyer);
        return tree;
    }

    /**
     * 新增用户统计
     */
    @RequiresPermissions("fac:buyer:userAnalysis")
    @GetMapping("/userAnalysis")
    public String userAnalysis() {
        return prefix_analysis + "/userAnalysis";
    }

    @PostMapping("/queryRecentUserInfo")
    @ResponseBody
    public UserDiagramVo queryRecentUserInfo(String startDate, String endDate) {
        // 默认当前一周日期内(Basic area chart)
        UserDiagramVo vo = this.buyerService.queryRecentUserInfo(startDate, endDate);
        return vo;
    }

    @RequiresPermissions("fac:buyer:address")
    @GetMapping("/address")
    public String address() {
        return prefix + "/address";
    }

    @RequiresPermissions("fac:buyer:listAddress")
    @PostMapping("/listAddress")
    @ResponseBody
    public TableDataInfo listAddress(FacBuyerAddress address) {
        startPage();
        List<FacBuyerAddress> list = buyerService.listBuyerAddresses(address);
        return getDataTable(list);
    }

    @RequiresPermissions("fac:buyer:removeAddress")
    @Log(title = "买者用户地址-删除", businessType = BusinessType.DELETE)
    @PostMapping("/removeAddress")
    @ResponseBody
    public AjaxResult removeAddress(String ids) {
        SysUser user = ShiroUtils.getSysUser();
        return toAjax(buyerService.deleteUserAddress(ids, user));
    }

    @GetMapping("/toEditAddress/{id}")
    public String toEditAddress(@PathVariable("id") Long id, ModelMap mmap) {
        FacBuyerAddress address = buyerService.selectAddress(id);
        mmap.put("address", address);
        return prefix + "/editaddress";
    }

    @RequiresPermissions("fac:buyer:editAddress")
    @Log(title = "买者用户地址-编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/editAddress")
    @ResponseBody
    public AjaxResult editAddress(FacBuyerAddress address) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            address.setOperatorName(user.getUserName());
            address.setOperatorId(user.getUserId());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(buyerService.editAddress(address));
    }
}
