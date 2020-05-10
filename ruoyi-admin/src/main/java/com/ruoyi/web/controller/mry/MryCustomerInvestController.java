package com.ruoyi.web.controller.mry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.base.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fac.constant.FacConstant;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.framework.web.base.BaseController;
import com.ruoyi.mry.exception.MryException;
import com.ruoyi.mry.model.*;
import com.ruoyi.mry.service.MryCustomerCardService;
import com.ruoyi.mry.service.MryCustomerInvestService;
import com.ruoyi.mry.service.MryShopService;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户充值记录
 */
@Controller
@RequestMapping("/mry/customerinvest")
public class MryCustomerInvestController extends BaseController{

    private String prefix = "mry/customerinvest";

    @Autowired
    private MryCustomerInvestService customerInvestService;
    @Autowired
    private MryCustomerCardService customerCardService;
    @Autowired
    private MryShopService shopService;

    @RequiresPermissions("mry:customerinvest:view")
    @GetMapping()
    public String customerinvest() {
        return prefix + "/customerinvest";
    }

    @RequiresPermissions("mry:customerinvest:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MryCustomerInvest customerInvest) {
        final MryShop shop = new MryShop();
        final List<MryShop> shops = this.shopService.selectShops(shop);
        MryCustomerCard customerCard = new MryCustomerCard();
        if (customerInvest.getShopId() != null) {
            customerCard.setShopId(customerInvest.getShopId());
        }
        if (StringUtils.isNotBlank(customerInvest.getCustomerName()) && StringUtils.isNotBlank(customerInvest.getCustomerName().trim())) {
            customerCard.setCustomerName(customerInvest.getCustomerName());
        }
        final Map<Long, MryCustomer> customers = this.customerCardService.listCustomers(customerCard);
        List<MryCustomerInvest> list = CollUtil.newArrayList();
        startPage();
        if (MapUtil.isNotEmpty(customers)) {
            list = customerInvestService.selectCustomerInvests(customerInvest, shops, customers);
        }
        return getDataTable(list);
    }

    @RequiresPermissions("mry:customerinvest:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MryCustomerInvest customerInvest) {
        final MryShop shop = new MryShop();
        final List<MryShop> shops = this.shopService.selectShops(shop);
        MryCustomerCard customerCard = new MryCustomerCard();
        if (customerInvest.getShopId() != null) {
            customerCard.setShopId(customerInvest.getShopId());
        }
        if (StringUtils.isNotBlank(customerInvest.getCustomerName()) && StringUtils.isNotBlank(customerInvest.getCustomerName().trim())) {
            customerCard.setCustomerName(customerInvest.getCustomerName());
        }
        final Map<Long, MryCustomer> customers = this.customerCardService.listCustomers(customerCard);
        List<MryCustomerInvest> list = CollUtil.newArrayList();
        if (MapUtil.isNotEmpty(customers)) {
            list = customerInvestService.selectCustomerInvests(customerInvest, shops, customers);
        }
        ExcelUtil<MryCustomerInvest> util = new ExcelUtil<>(MryCustomerInvest.class);
        return util.exportExcel(list, "客户充值明细");
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("mry:customerinvest:add")
    @Log(title = "客户充值明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MryCustomerInvest customerInvest) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerInvest.setOperatorId(user.getUserId());
            customerInvest.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        return toAjax(customerInvestService.insertCustomerInvest(customerInvest));
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        MryCustomerInvest customerInvest = customerInvestService.selectCustomerInvest(id);
        mmap.put("customerinvest", customerInvest);
        return prefix + "/edit";
    }

    @RequiresPermissions("mry:customerinvest:edit")
    @Log(title = "客户充值明细-编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MryCustomerInvest customerInvest) {
        SysUser user = ShiroUtils.getSysUser();
        if (user != null) {
            customerInvest.setOperatorId(user.getUserId());
            customerInvest.setOperatorName(user.getUserName());
        } else {
            return AjaxResult.error(FacConstant.ERROR_MSG_LOGIN_USER_NULL);
        }
        try {
            return toAjax(customerInvestService.updateCustomerInvest(customerInvest));
        } catch (MryException me) {
            return AjaxResult.error(me.getMessage());
        } catch (Exception ex) {
            return AjaxResult.error();
        }
    }

    @RequiresPermissions("mry:customerinvest:remove")
    @Log(title = "客户充值明细-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            SysUser user = ShiroUtils.getSysUser();
            return toAjax(customerInvestService.deleteCustomerInvestByIds(ids, user));
        } catch (Exception ex) {
            return error(ex.getMessage());
        }
    }

    @PostMapping("/getCustomersByShopId")
    @ResponseBody
    public List<MryCustomer> getCustomersByShopId(MryCustomerCard customerCard) {
        List<MryCustomer> customers = this.customerCardService.getCustomersByShopId(customerCard);
        return customers;
    }

    @PostMapping("/getShopCardsByShopId")
    @ResponseBody
    public List<MryShopCard> getShopCardsByShopId(MryCustomerCard customerCard) {
        List<MryShopCard> shopCards = this.customerCardService.getShopCardsByShopId(customerCard);
        return shopCards;
    }

    @PostMapping("/getCustomerCardsByShopCustomer")
    @ResponseBody
    public List<MryCustomerCard> getCustomerCardsByShopCustomer(MryCustomerCard customerCard) {
        List<MryCustomerCard> customerCards = this.customerCardService.getCustomerCardsByShopCustomer(customerCard);
        return customerCards;
    }
}
